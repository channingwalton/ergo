<?php
	include_once("include/connect.php");
	
	if (isset($_POST['uuid']) && isset($_POST['contents'])) {
		$file = fopen("tmpOutput", "a");
		if ($file)  {
			$out = print_r($_POST, true);
			fwrite($file, $out."\n\n");
			fclose($file);
		}
		// select if already present
		$uuid = addslashes($_POST['uuid']);
		$query = "select id from ergo_users where uuid = '$uuid'";
		$result = mysql_query($query);
		$arr = mysql_fetch_array($result);
		
		$userId = null;
		if (!mysql_num_rows($result)) {
			$query = "insert into ergo_users (uuid) values('$uuid')";
			mysql_query($query);
			$userId = mysql_insert_id();
		}
		else {
			$userId = $arr['id']; 
		}
		
		
		$contents = explode("\n",$_POST['contents']);
		
		foreach ($contents as $line) {
			$line = trim($line);
			
			if ($line{0} == "#")
				continue;

			$arr = split("=",$line);
			
			$actionName = addslashes($arr[0]);
			if (!strlen($actionName))
				continue;
			$arr = split(",",$arr[1]);
			
			$shortcutInvocations = (int)$arr[0];
			$menuInvocations = (int)$arr[1];

			$name = addslashes($arr[2]);
			$desc = addslashes($arr[3]);
			$shortcut = addslashes($arr[4]);
			// see if the shortcut is in the database
			$query = "select * from ergo_commands where command_id = '$actionName'";
			$result = mysql_query($query);
			$arr = mysql_fetch_assoc($result);
			if ($arr['command_id'] && !strlen($arr['shortcut'])) {
				$query = "update ergo_commands set shortcut = '$shortcut' where id = ".$arr['id'];
				mysql_query($query);
			}
			else if (!$arr['id']) {
				$query = "insert into ergo_commands (command_id, name, description, shortcut) values "
								."('$actionName','$name','$desc','$shortcut')";
				mysql_query($query);
			}

			$query = "update ergo_actions set shortcutInvocations = $shortcutInvocations,"
						."menuInvocations = $menuInvocations where "
						." actionName = '$actionName' and userId = $userId";
			mysql_query($query);
			if (!mysql_affected_rows()) {
				// affected rows can be 0 when the data is the same
				// so we check for that too
				$query = "select * from ergo_actions where actionName='$actionName' and userId = '$userId'";
				$result2 = mysql_query($query);
				if (mysql_num_rows($result2)) {
					continue;
				}
				// brand new insert
				$query = "insert into ergo_actions (userId, actionName, shortcutInvocations, "
							."menuInvocations)"
							." values ($userId,'$actionName',$shortcutInvocations,$menuInvocations)";
				mysql_query($query);
			}
		}
		$query = "update ergo_users set lastUpdate=now() where id = $userId";
		mysql_query($query);
	}

	$query = "select count(id) as count from ergo_users";
	$arr = mysql_fetch_array(mysql_query($query));
	echo "We have {$arr['count']} user(s)!<br>";
	$query = "select UNIX_TIMESTAMP(lastUpdate) as lastUpdate from "
				."ergo_users order by lastUpdate desc limit 1";
	$arr = mysql_fetch_array(mysql_query($query));
	$delta = time()-$arr['lastUpdate'];
	$minutes = $delta == 0 ? 0: round($delta/60);
	echo "Last update $minutes minute(s) ago!<br>";
?>
<!-- form method=POST>
	<input type="text" name="uuid" value=""/>
	<textarea name="contents">#Mon Feb 04 00:32:03 EET 2008
	org.eclipse.ui.file.exit=0,4
	org.eclipse.ui.edit.text.openLocalFile=0,1
	org.eclipse.ui.window.quickAccess=1,0
	org.eclipse.ui.newWizard=1,0
	org.eclipse.ui.file.close=3,0</textarea>
	<input type="submit"/>
</form -->
