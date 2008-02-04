<?php
	include_once("include/connect.php");
	?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>My Eclipse Usage Statistics</title>
<link href="styles.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table id="mytable" cellspacing="0">
<caption>My Eclipse Actions Usage Data</caption>
  <tr>
    <th scope="col"  class="nobg">Action</th>

    <th scope="col">Shortcut access</th>
    <th scope="col">Menu access</th>
	<th scope="col">Description</th>
	<th scope="col">Shortcut</th>
  </tr>

<?
	$userId = 3;
	if ((int)$_GET['id'])
		$userId = (int)$_GET['id'];
	
	if ($_GET['uuid']) {
		$uuid = addslashes($_GET['uuid']);
		$query = "select id from ergo_users where uuid = '$uuid'";
		$result = mysql_query($query);
		$arr = mysql_fetch_assoc($result);
		if ($arr['id'])
			$userId = (int)$arr['id'];
	}
	
	$query = "select distinct actionName, menuInvocations, "
				."shortcutInvocations, name, description,shortcut from "
				."ergo_actions ea right join ergo_commands ec on "
				."ea.actionName = ec.command_id where userId = $userId "
				."order by shortcutInvocations desc, menuInvocations "
				."desc";

	$result = mysql_query($query);
	while ($arr = mysql_fetch_assoc($result)) {
  		echo "<tr>";
    	echo '<th scope="row" abbr="Model" class="spec">'.
				$arr['name'].'</th>';

    	echo "<td>".$arr['shortcutInvocations']."</td>";
    	echo "<td>".$arr['menuInvocations']."</td>";
    	echo "<td>".$arr['description']."</td>";
    	echo "<td>".$arr['shortcut']."</td>";
  		echo "</tr>";
	}
?>

</table>
</body>
</html>

