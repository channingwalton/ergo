--
-- Table structure for table `ergo_users`
--

CREATE TABLE `ergo_users` (
  `id` int(11) NOT NULL auto_increment,
  `uuid` varchar(50) default NULL,
  `lastUpdate` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ergo_users`
--

LOCK TABLES `ergo_users` WRITE;
/*!40000 ALTER TABLE `ergo_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `ergo_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ergo_actions`
--

CREATE TABLE `ergo_actions` (
  `id` int(11) NOT NULL auto_increment,
  `userId` int(11) default NULL,
  `actionName` varchar(255) default NULL,
  `shortcutInvocations` int(11) default NULL,
  `menuInvocations` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;


--
-- Dumping data for table `ergo_actions`
--