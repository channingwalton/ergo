<?php
	include_once("include/connect.php");
	
	if (isset($_POST['uuid']) && isset($_POST['contents'])) {
		
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
			$arr = split(",",$arr[1]);
			
			$shortcutInvocations = (int)$arr[0];
			$menuInvocations = (int)$arr[1];

			$query = "update ergo_actions set shortuctInvocations = $shortcutInvocations,"
						."menuInvocations = $menuInvocations where "
						." actionName = '$actionName' and userId = $userId";
			mysql_query($query);
			if (mysql_affected_rows()) {
				$query = "insert into ergo_actions (userId, actionName, shortcutInvocations, "
							."menuInvocations)"
							." values ($userId,'$actionName',$shortcutInvocations,$menuInvocations)";
				mysql_query($query);
			}
		}
	}

	$query = "select count(id) as count from ergo_users";
	$arr = mysql_fetch_array(mysql_query($query));
	echo "We have {$arr['count']} user(s)!<br>";
	$query = "select lastUpdate from ergo_users order by lastUpdate desc limit 1";
	$arr = mysql_fetch_array(mysql_query($query));
	echo "Last update {$arr['lastUpdate']}!<br>";
?>