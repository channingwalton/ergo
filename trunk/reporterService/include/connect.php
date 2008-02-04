<?php
	mysql_connect("localhost", "toomas", "toomas")
		or die("Unable to connect to database server");
	mysql_select_db("toomas")
		or die("Unable to select database");
?>
