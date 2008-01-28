<?php
	mysql_connect("localhost", "toomasr", "toomasr")
		or die("Unable to connect to database server");
	mysql_select_db("somedatabase")
		or die("Unable to select database");
?>