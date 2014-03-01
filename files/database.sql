DROP TABLE IF EXISTS `properties`;

CREATE TABLE `properties` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `value` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE `master` (
  `id` varchar(36) NOT NULL,
  `value` varchar(50) NOT NULL,
  `propertyid` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `configmaster_1` (`propertyid`),
  CONSTRAINT `configmaster_1` FOREIGN KEY (`propertyid`) REFERENCES `properties` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `templates` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
   `comment` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `permvalue` int(4) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

insert into roles values(1,'Super Admin',1022);
insert into roles values(2,'Admin',1022);
insert into roles values(3,'Teacher',1022);
insert into roles values(4,'Parent',1022);
insert into roles values(5,'Student',1022);

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `userid` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `dob` bigint DEFAULT NULL,
  `emailid` varchar(40) DEFAULT NULL,
  `contactno` varchar(11) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `createdon` datetime DEFAULT NULL,
  `modifiedon` datetime DEFAULT NULL,
  `designation` varchar(60) DEFAULT NULL,
  `roleid` int(4) DEFAULT NULL COMMENT 'admin,employee'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `userlogin`;


CREATE TABLE `userlogin` (
  `userid` varchar(50),
  `username` varchar(20) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PASSWORD` varchar(50) NOT NULL DEFAULT 'dl@b1234',
  `sequerityques` varchar(50) DEFAULT NULL,
  `ans` varchar(20) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `userslogin_ihgj_123` (`userid`),
  CONSTRAINT `userslogin_ihgj_123` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
Create table class (
  schoolid     int,
  classid      int,
  name         varchar(20),
  classteacher int,
  feetemplate  int,
  comment      varchar(40)
)
INSERT INTO properties VALUES(1,'Designation');
INSERT INTO properties VALUES(2,'Course');
INSERT INTO properties VALUES(3,'PeriodNumber');
INSERT INTO properties VALUES(4,'Religion');
INSERT INTO properties VALUES(5,'Examination');
INSERT INTO properties VALUES(6,'Session');
INSERT INTO properties VALUES(7,'ExamAppearedStatus');
INSERT INTO properties VALUES(9,'Location');
INSERT INTO properties VALUES(10,'Vehicle Type');
INSERT INTO properties VALUES(11,'Notification Recipient');
INSERT INTO properties VALUES(12,'Notification Status');
INSERT INTO properties VALUES(13,'Digital Dairy Priority');
INSERT INTO properties VALUES(14,'Digital Dairy Request Type');
INSERT INTO properties VALUES(15,'Student Progress Status');
INSERT INTO properties VALUES(16,'Nationality');
INSERT INTO properties VALUES(100,'Calendar');
INSERT INTO properties VALUES(17,'AdmissionType');
INSERT INTO properties VALUES(18,'Class');
INSERT INTO properties VALUES(19,'Section');
INSERT INTO properties VALUES(20,'Addmission Request Status');
INSERT INTO properties VALUES(21,'Book Type');
INSERT INTO properties VALUES(23,'Rating');
INSERT INTO properties VALUES(24,'Teacher Type');
INSERT INTO properties VALUES(25,'Teacher Job Type');
INSERT INTO properties VALUES(27,'Department');
INSERT INTO properties VALUES(28,'Education Level');
INSERT INTO properties VALUES(29,'Qualification');
INSERT INTO properties VALUES(30,'University');
INSERT INTO properties VALUES(31,'Decipline');
INSERT INTO properties VALUES(32,'Score Type');
INSERT INTO properties VALUES(33,'School Affiliation');
INSERT INTO properties VALUES(35,'MIS Report Type');
INSERT INTO properties VALUES(36,'Hostel Type');
INSERT INTO properties VALUES(37,'Room Type');
INSERT INTO properties VALUES(38,'Floor');

ALTER TABLE userlogin MODIFY username VARCHAR(40);
ALTER TABLE student   MODIFY religion VARCHAR(40);


DROP TABLE IF EXISTS `feestructure`;

CREATE TABLE `feestructure` (
  `fee_structure_id` varchar(40) NOT NULL,
  `fee_name` varchar(40) DEFAULT NULL,
  `header` varchar(40) DEFAULT NULL,
  `fee_type` varchar(20) DEFAULT NULL,
  `fee_amount` decimal(11,0) DEFAULT NULL,
  `fee_frequency` varchar(40) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fee_structure_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `template_structure_map`(
  `tempate_id` varchar(40) NOT NULL,
  `reference_id` varchar(40) NOT NULL,
  `type` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO feestructure VALUES(1,'Tution Fee','Tution Fee','',1000,'','');
INSERT INTO feestructure VALUES(2,'Computer  Lab Fee','Tution Fee','',500,'','');
INSERT INTO feestructure VALUES(3,'Sports Fee','Tution Fee','',300,'','');
INSERT INTO feestructure VALUES(4,'Laboratory Fee','Tution Fee','',200,'','');
INSERT INTO feestructure VALUES(5,'Bus Fee','Tution Fee','',1000,'','');
INSERT INTO feestructure VALUES(6,'Chemistry Lab Fee','Tution Fee','',200,'','');
INSERT INTO feestructure VALUES(7,'Bio Lab Fee','Tution Fee','',300,'','');
INSERT INTO feestructure VALUES(8,'Lunch Fee','Tution Fee','',400,'','');

INSERT INTO template_structure_mapping VALUES(2,1,'');
INSERT INTO template_structure_mapping VALUES(2,2,'');
INSERT INTO template_structure_mapping VALUES(2,3,'');
INSERT INTO template_structure_mapping VALUES(2,4,'');
INSERT INTO template_structure_mapping VALUES(2,5,'');
INSERT INTO template_structure_mapping VALUES(2,6,'');
INSERT INTO template_structure_mapping VALUES(2,7,'');
INSERT INTO template_structure_mapping VALUES(2,8,'');
COMMIT
DROP TABLE IF EXISTS `accessgroups`;

CREATE TABLE `accessgroups` (
  `no` int(3) NOT NULL,
  `groupid` varchar(50) NOT NULL,
   `title` varchar(50) NOT NULL,
  PRIMARY KEY (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into accessgroups(no,groupid,title) values(1,'Widget_ADMIN','Administration');
insert into accessgroups(no,groupid,title) values(2,'Widget_QUICKLINKS','School Administration');
insert into accessgroups(no,groupid,title) values(3,'Widget_PAYMENT','Payment');
insert into accessgroups(no,groupid,title) values(4,'Widget_LEAVE','Leave & Attendance');
insert into accessgroups(no,groupid,title) values(5,'Widget_ALERT','Alerts');
insert into accessgroups(no,groupid,title) values(6,'Widget_PERANT','Parent Portal');
insert into accessgroups(no,groupid,title) values(7,'Widget_NOTICE','Noticeboard');
insert into accessgroups(no,groupid,title) values(8,'Widget_FORUM','Discussion Forum');

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `id` int(3) NOT NULL,
   `groupid` varchar(50) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `image` varchar(40) DEFAULT NULL,
  `tooltip` varchar(100) DEFAULT NULL,
   `callback` varchar(100) NOT NULL,
   KEY `permission_ihgj_123`(`groupid`),
  CONSTRAINT `permission_ihgj_123` FOREIGN KEY (`groupid`) REFERENCES `accessgroups` (`groupid`) on update cascade
   
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into permissions(id,groupid,`name`,image,tooltip,callback) values(1,'Widget_ADMIN','Manage Permission','reports-icon.png','','managePermission');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(2,'Widget_ADMIN','School Admin','reports-icon.png','','showSchoolAdmin');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(3,'Widget_ADMIN','Master Config','master-configuration.png','','showMasterGrid');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(4,'Widget_ADMIN','Manage Users','leads-icon.png','','showUsers');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(5,'Widget_ADMIN','Audit Trails','my-goals.png','','showAuditTrail');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(6,'Widget_ADMIN','Student Management','contact-icon.png','','showStudents');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(7,'Widget_ADMIN','Fee Structure','reports-icon.png','','showFeeStructure');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(8,'Widget_ADMIN','Fee Template','Email-Template-add.png','','showFeeTemplate');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(9,'Widget_ADMIN','Class Management','reports-icon.png','','showClasses');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(10,'Widget_ADMIN','Email Templates','Email-Template-add.png','','showEmailTpl');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(11,'Widget_ADMIN','Student Fees Module','Email-Template-add.png','','showStudentFeeModule');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(12,'Widget_ADMIN','Fee Generation','Email-Template-add.png','','showFeeGeneration');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(13,'Widget_ADMIN','Period Setting','Email-Template-add.png','','showPeriod');   
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(14,'Widget_ADMIN','Transport System','Email-Template-add.png','','showTransport');   
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(15,'Widget_ADMIN','Notice Board','Email-Template-add.png','','showNotification');   
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(16,'Widget_ADMIN','Session Management','Email-Template-add.png','','showSession');   
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(17,'Widget_ADMIN','Addmission Management','Email-Template-add.png','','showAddmission');   
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(18,'Widget_ADMIN','Library Management','library.png','','showLibrary');   
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(19,'Widget_ADMIN','Report Management','report.png','','showReport');   
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(20,'Widget_ADMIN','Hostel Management','hostel.jpg','','showHostel');  
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(21,'Widget_ADMIN','Almuni Management','hostel.jpg','','showHostel');  


insert into permissions(id,groupid,`name`,image,tooltip,callback) values(1,'Widget_QUICKLINKS','Assignments','reports-icon.png','','');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(2,'Widget_QUICKLINKS','Report Cards','reports-icon.png','','');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(3,'Widget_QUICKLINKS','Time Table','reports-icon.png','','createTimeTable');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(4,'Widget_QUICKLINKS','School Website','leads-icon.png','','');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(5,'Widget_QUICKLINKS','Home Works','my-goals.png','','showHomeWork');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(6,'Widget_QUICKLINKS','Tutorials','contact-icon.png','','');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(7,'Widget_QUICKLINKS','Holiday List','reports-icon.png','','');
insert into permissions(id,groupid,`name`,image,tooltip,callback) values(10,'Widget_QUICKLINKS','Teacher Management','reports-icon.png','','showTeachers');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(1,'Widget_LEAVE','Leaves Details','reports-icon.png','','inProgress');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(2,'Widget_LEAVE','Attendance details','reports-icon.png','','inProgress');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(3,'Widget_LEAVE','Attendance Request','reports-icon.png','','inProgress');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(4,'Widget_LEAVE','Leave Request','reports-icon.png','','inProgress');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(5,'Widget_LEAVE','Create Examination','reports-icon.png','','CreateExam');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(6,'Widget_LEAVE','Grade Setting','reports-icon.png','','gradeSetting');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(7,'Widget_LEAVE','Marks Entry','reports-icon.png','','markEntry');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(8,'Widget_LEAVE','Promote Student Module','reports-icon.png','','showPromoteStudentModule');

INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(1,'Widget_PERANT','Fee Details','reports-icon.png','','showFeeDetails');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(2,'Widget_PERANT','Make Payment','reports-icon.png','','showFeeDetailsParent');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(3,'Widget_PERANT','Exam & Report Card','reports-icon.png','','showReportCardToParent');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(4,'Widget_PERANT','Digital Dairy','reports-icon.png','','showDigitalDairy');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(5,'Widget_PERANT','Payment History','reports-icon.png','','showPaymentHistory');
INSERT INTO permissions(id,groupid,`name`,image,tooltip,callback) VALUES(6,'Widget_PERANT','Student Profile','reports-icon.png','','showStudentProfile');

UPDATE permissions SET image='admission.png' WHERE NAME='Addmission Management';
UPDATE permissions SET image='attandance.png' WHERE NAME='Attendance details';
UPDATE permissions SET image='digitaldiary.png' WHERE NAME='Digital Dairy';
UPDATE permissions SET image='email-template-add.png' WHERE NAME='Email Templates';
UPDATE permissions SET image='examination.png' WHERE NAME='Create Examination';
UPDATE permissions SET image='leads-icon.png' WHERE NAME='Teacher Management';
UPDATE permissions SET image='master-configuration.png' WHERE NAME='Master Config';
UPDATE permissions SET image='noticeboard.png' WHERE NAME='Notice Board';
UPDATE permissions SET image='reportcard.png' WHERE NAME='Report Card';
UPDATE permissions SET image='teachersdetails.png' WHERE NAME='Teacher Management';
UPDATE permissions SET image='timetable.png' WHERE NAME='Time Table';
UPDATE permissions SET callback='showTutorial' WHERE NAME='Tutorials'


DROP TABLE IF EXISTS `rolesgroupspermission`;

CREATE TABLE `rolesgroupspermission` (
  `roleid` int(3) NOT NULL,
  `groupid` varchar(30) NOT NULL,
  `readonly` int(11) DEFAULT NULL,
  `editable` int(11) DEFAULT NULL,
  KEY `rolesgroupspermission_1` (`roleid`),
  KEY `rolesgroupspermission_2` (`groupid`),
  CONSTRAINT `rolesgroupspermission_1` FOREIGN KEY (`roleid`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `rolesgroupspermission_2` FOREIGN KEY (`groupid`) REFERENCES `accessgroups` (`groupid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(1,'Widget_ADMIN',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(1,'Widget_QUICKLINKS',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(1,'Widget_PAYMENT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(1,'Widget_LEAVE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(1,'Widget_ALERT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(1,'Widget_PERANT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(1,'Widget_NOTICE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(1,'Widget_FORUM',2,2);

insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(2,'Widget_ADMIN',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(2,'Widget_QUICKLINKS',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(2,'Widget_PAYMENT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(2,'Widget_LEAVE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(2,'Widget_ALERT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(2,'Widget_PERANT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(2,'Widget_NOTICE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(2,'Widget_FORUM',2,2);

insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(3,'Widget_ADMIN',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(3,'Widget_QUICKLINKS',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(3,'Widget_PAYMENT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(3,'Widget_LEAVE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(3,'Widget_ALERT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(3,'Widget_PERANT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(3,'Widget_NOTICE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(3,'Widget_FORUM',2,2);

insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(4,'Widget_ADMIN',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(4,'Widget_QUICKLINKS',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(4,'Widget_PAYMENT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(4,'Widget_LEAVE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(4,'Widget_ALERT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(4,'Widget_PERANT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(4,'Widget_NOTICE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(4,'Widget_FORUM',2,2);

insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(5,'Widget_ADMIN',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(5,'Widget_QUICKLINKS',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(5,'Widget_PAYMENT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(5,'Widget_LEAVE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(5,'Widget_ALERT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(5,'Widget_PERANT',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(5,'Widget_NOTICE',2,2);
insert into rolesgroupspermission(roleid,groupid,readonly,editable) values(5,'Widget_FORUM',2,2);



/* 5th may by cd*/
DROP TABLE IF EXISTS `class`;

CREATE TABLE `class` (
  `schoolid` varchar(50) DEFAULT NULL,
  `classid` varchar(50) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `classteacher` varchar(50) DEFAULT NULL,
  `feetemplate` varchar(50) DEFAULT NULL,
  `comment` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO users(userid,NAME,dob,emailid,contactno,address,city,gender,createdon,modifiedon,designation,roleid)
VALUES('35d3be72-77e4-4128-ab35-02baa8ba2a4d',	'Garrison Mcknight',CURRENT_DATE,'garrison@gmail.com','949494949','pune','pune','Male',CURRENT_DATE,CURRENT_DATE,'Super Admin','1');

INSERT INTO userlogin (userid, username, ts, PASSWORD, 	sequerityques, ans, STATUS)
VALUES	('35d3be72-77e4-4128-ab35-02baa8ba2a4d','admin', CURRENT_DATE, 'admin', '', '',1);

INSERT INTO mis.users 	(userid, 	NAME, 	dob, 
	emailid, 	contactno, 	address, 
	city, 	gender, 	createdon, 	modifiedon, 
	designation, 	roleid	)
VALUES
	('10006', 	'Manish Singh',	CURRENT_DATE, 	'manish@gmail.com', 
	'949495444', 	'gomia', 	'gomia', 	'Male', 
	CURRENT_DATE, 	CURRENT_DATE, 	'Teacher', 	'3'
	);
	
INSERT INTO mis.users 	(userid, 	NAME, 	dob, 
	emailid, 	contactno, 	address, 
	city, 	gender, 	createdon, 	modifiedon, 
	designation, 	roleid	)
VALUES
	('10007', 	'Durgesh Druve',	CURRENT_DATE, 	'durgesh@gmail.com', 
	'949494949', 	'pune', 	'pune', 	'Male', 
	CURRENT_DATE, 	CURRENT_DATE, 	'Parent', 	'4'
	);
	
INSERT INTO mis.users 	(userid, 	NAME, 	dob, 
	emailid, 	contactno, 	address, 
	city, 	gender, 	createdon, 	modifiedon, 
	designation, 	roleid	)
VALUES
	('10008', 	'Sagar Yadav',	CURRENT_DATE, 	'sagar@gmail.com', 
	'949494949', 	'pune', 	'pune', 	'Male', 
	CURRENT_DATE, 	CURRENT_DATE, 	'Student', 	'5'
	);
	

INSERT INTO mis.userlogin (userid, username, ts, PASSWORD, 	sequerityques, 
	ans, 	STATUS	)
VALUES
	('10005','admin1', CURRENT_DATE, 'PASSWORD', 
	'', '',1);

INSERT INTO mis.userlogin (userid, username, ts, PASSWORD, 	sequerityques, 
	ans, 	STATUS	)
VALUES
	('10006','teacher', CURRENT_DATE, 'PASSWORD', 
	'', '','1');

INSERT INTO mis.userlogin (userid, username, ts, PASSWORD, 	sequerityques, 
	ans, 	STATUS	)
VALUES
	('10007','parent', CURRENT_DATE, 'PASSWORD', 
	'', '','1');

INSERT INTO mis.userlogin (userid, username, ts, PASSWORD, 	sequerityques, 
	ans, 	STATUS	)
VALUES
	('10008','student', CURRENT_DATE, 'PASSWORD', 
	'', '','1');

INSERT INTO mis.userlogin (userid, username, ts, PASSWORD, 	sequerityques, 
	ans, 	STATUS	)
VALUES
	('10009','superadmin', CURRENT_DATE, 'PASSWORD', 
	'', '','1');
		

CREATE TABLE monthlyfeedetails(
    monthly_fee_id   VARCHAR(40) ,
    student_id       VARCHAR(40) , 
    fee_structure_id VARCHAR(40) , 
    amount           INT
);

alter table `mis`.`templates` change `id` `id` varchar(36) NOT NULL;
alter table `mis`.`class` change `feetemplate` `feetemplate` varchar(36) NULL;
UPDATE permissions SET id=id+10, groupid='Widget_QUICKLINKS' WHERE groupid = 'Widget_PERANT'

create table `mis`.`calendar`( 
   `calendar_id` int(2) NOT NULL AUTO_INCREMENT , 
   `title` varchar(100) NOT NULL , 
   `description` varchar(250) , 
   `color` varchar(10) , 
   `hidden` bit , 
   PRIMARY KEY (`calendar_id`)
 );

create table `mis`.`events`( 
   `event_id` int(5) NOT NULL AUTO_INCREMENT UNIQUE , 
   `calendar_id` int(3) NOT NULL , 
   `title` varchar(100) , 
   `start_date` bigint , 
   `end_date` bigint , 
   `location` varchar(100) , 
   `notes` varchar(250) , 
   `url` varchar(100) , 
   `reminder` varchar(100) , 
   `isAllDay` bit , 
   `isNew` bit,
    KEY `cal-events_1112`(`calendar_id`),
    CONSTRAINT `cal-events_1112` FOREIGN KEY (`calendar_id`) REFERENCES `calendar`(`calendar_id`) ON DELETE CASCADE
 );
  CREATE TABLE `classexam` (
  `id` VARCHAR(40) NOT NULL,
  `classid` VARCHAR(40) DEFAULT NULL,
  `examtypeid` VARCHAR(40) DEFAULT NULL,
  `examname` VARCHAR(40) DEFAULT NULL,
  `sessionid` VARCHAR(40) DEFAULT NULL,
  `subjectid` VARCHAR(40) DEFAULT NULL,
  `maxmark` INT(11) DEFAULT NULL,
  `passmark` INT(11) DEFAULT NULL,
  `teacherid` VARCHAR(40) DEFAULT NULL,
  `createby` VARCHAR(40) DEFAULT NULL,
  `createdon` DATETIME DEFAULT NULL,
  `modifiedby` VARCHAR(40) DEFAULT NULL,
  `modifiedon` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE `studentexam` (
  `id` VARCHAR(40) DEFAULT NULL,
  `classexamid` VARCHAR(40) DEFAULT NULL,
  `studentid` VARCHAR(40) DEFAULT NULL,
  `appeared` INT(11) DEFAULT NULL,
  `markobtained` INT(11) DEFAULT NULL,
  `passstatus` INT(11) DEFAULT NULL,
  `createdby` VARCHAR(40) DEFAULT NULL,
  `createdon` DATETIME DEFAULT NULL,
  `modifiedby` VARCHAR(40) DEFAULT NULL,
  `modifiedon` DATETIME DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE `vendor`( 
   `vendorid` VARCHAR(40) NOT NULL , 
   `vname` VARCHAR(40) , 
   `mobile` VARCHAR(40) , 
   `emailid` VARCHAR(40) , 
   `address` VARCHAR(300) , 
   `createdby` VARCHAR(40) , 
    createdon   DATETIME  ,
   `modifiedby` VARCHAR(40) , 
   `modifiedon` TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`vendorid`)
 );
 
ALTER TABLE vendor ADD contactperson VARCHAR(40);


CREATE TABLE busdetail(
busid VARCHAR(40) , 
busnumber VARCHAR(40) ,  
vendorid VARCHAR(40) , 
vname   VARCHAR(40) ,  
drivername VARCHAR(40) , 
drivernumber  VARCHAR(40) ,  
altdrivernumber VARCHAR(40) ,  
createdby    VARCHAR(40) , 
modifiedby   VARCHAR(40) , 
createdon    DATETIME  ,
modifiedon   TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (busid)
);

ALTER TABLE busdetail ADD seat INTEGER;
ALTER TABLE busdetail ADD vtype VARCHAR(40);

DROP TABLE IF EXISTS route;
CREATE TABLE ROUTE (
routeid      VARCHAR(40) , 
sid          VARCHAR(40) , 
sname        VARCHAR(40) ,     
did          VARCHAR(40) , 
dname        VARCHAR(40) , 
createdby    VARCHAR(40) , 
modifiedby   VARCHAR(40) ,  
createdon    DATETIME    ,
modifiedon   TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (routeid)
);

ALTER TABLE route ADD NAME VARCHAR(50);

CREATE TABLE ROUTEVIYA(
routeviyaid  VARCHAR(40) , 
routeid      VARCHAR(40) , 
locationid   VARCHAR(40) , 
locationname VARCHAR(40) , 
seqnum       VARCHAR(40) , 
createdby    VARCHAR(40) , 
modifiedby   VARCHAR(40) , 
createdon    DATETIME    , 
modifiedon   TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (routeviyaid)
);


CREATE TABLE routebustran(
routebustranid VARCHAR(40) , 
routeid        VARCHAR(40) ,  
busid          VARCHAR(40) ,  
starttime      VARCHAR(40) ,  
endtime        VARCHAR(40) ,  
createdby      VARCHAR(40) , 
modifiedby     VARCHAR(40) , 
createdon      DATETIME    , 
modifiedon     TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (routebustranid)
);

create table `mis`.`TableName1`( 
   `pid` varchar(40) NOT NULL , 
   `routeid` varchar(40) , 
   `studentid` varchar(40) , 
   `fromlocation` varchar(40) , 
   `tolocation` varchar(40) , 
   `startdate` bigint , 
   `status` boolean , 
   `createdby` varchar(40) , 
   `modifiedby` varchar(40) , 
   `createdon` datetime , 
   `modifiedon` TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`pid`)
 );

CREATE TABLE studenttransport( 
   `pid` VARCHAR(40) NOT NULL , 
   `routeid` VARCHAR(40) , 
   `studentid` VARCHAR(40) , 
   `fromlocation` VARCHAR(40) , 
   `tolocation` VARCHAR(40) , 
   `startdate` BIGINT , 
   `status` BOOLEAN , 
   `createdby` VARCHAR(40) , 
   `modifiedby` VARCHAR(40) , 
   `createdon` DATETIME , 
   `modifiedon` TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`pid`)
 );

ALTER TABLE studenttransport ADD vehicleid VARCHAR(40) ;


ALTER TABLE classsubject ADD teacherid VARCHAR(40);
ALTER TABLE classsubject ADD sessionid VARCHAR(40);
ALTER TABLE classsubject ADD createdby VARCHAR(40);
ALTER TABLE classsubject ADD createdon DATETIME; 
ALTER TABLE classsubject ADD modifiedby VARCHAR(40);
ALTER TABLE classsubject ADD modifiedon TIMESTAMP DEFAULT CURRENT_TIMESTAMP;



create table `attendance_sheet`( 
   `sheet_id` int(5) NOT NULL , 
   `month` varchar(100) NOT NULL , 
   `batch_id` varchar(36) NOT NULL , 
   PRIMARY KEY (`sheet_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1;

create table `monthly_attendance`( 
   `sheet_id` int(5) , 
   `student_id` varchar(36), 
   `d1` varchar(2) , 
   `d2` varchar(2) , 
   `d3` varchar(2) , 
   `d4` varchar(2) , 
   `d5` varchar(2) , 
   `d6` varchar(2) , 
   `d7` varchar(2) , 
   `d8` varchar(2) , 
   `d9` varchar(2) , 
   `d10` varchar(2) , 
   `d11` varchar(2) , 
   `d12` varchar(2) , 
   `d13` varchar(2) , 
   `d14` varchar(2) , 
   `d15` varchar(2) , 
   `d16` varchar(2) , 
   `d17` varchar(2) , 
   `d18` varchar(2) , 
   `d19` varchar(2) , 
   `d20` varchar(2) , 
   `d21` varchar(2) , 
   `d22` varchar(2) , 
   `d23` varchar(2) , 
   `d24` varchar(2) , 
   `d25` varchar(2) , 
   `d26` varchar(2) , 
   `d27` varchar(2) , 
   `d28` varchar(2) , 
   `d29` varchar(2) , 
   `d30` varchar(2) , 
   `d31` varchar(2) ,
    KEY `attendance-1112`(`sheet_id`),
    CONSTRAINT `attendance-1112` FOREIGN KEY (`sheet_id`) REFERENCES `attendance_sheet`(`sheet_id`) ON DELETE CASCADE
 
 );
UPDATE permissions SET callback='showAttendance' WHERE id = 2 AND groupid='Widget_LEAVE';

CREATE TABLE `sessions` (
  `batch_id` varchar(40) NOT NULL,
  `session_id` varchar(40) default NULL,
  `class_id` varchar(40) default NULL,
  `year` int(4) default NULL,
   class_teacher varchar(40), 
  PRIMARY KEY  (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `student_class_map` (
   student_id varchar(40),
  `batch_id` varchar(40) NOT NULL,
   roll_no int(4)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



 ALTER TABLE sessions ADD template_id VARCHAR(40);
 ALTER TABLE classexam ADD batch_id VARCHAR(40);
 ALTER TABLE student ADD COLUMN addmission_no INTEGER;
 ALTER TABLE studentexam ADD COMMENT VARCHAR(200);

create table classsubjectteacher( 
   `pid` varchar(40) NOT NULL , 
   `batchid` varchar(40) NOT NULL , 
   `subjectid` varchar(40) NOT NULL DEFAULT '.' , 
   `teacherid` varchar(40) NOT NULL , 
   `createdby` varchar(40) , 
   `createdon` timestamp DEFAULT 'current timestamp' , 
   `modifiedby` varchar(40) , 
   `modifiedon` datetime , 
   `comment` varchar(250) 
 );

UPDATE permissions SET NAME='Settings', callback='openSettings' WHERE id=1 AND groupid='Widget_ADMIN';
DELETE FROM permissions  WHERE id=3 AND groupid='Widget_ADMIN';
DELETE FROM permissions  WHERE id=13 AND groupid='Widget_ADMIN';
DELETE FROM permissions  WHERE id=10 AND groupid='Widget_ADMIN';


CREATE TABLE filetype (
  id           VARCHAR(40),
  filetype     VARCHAR(10),
  imagename    VARCHAR(100)
);
 
INSERT INTO filetype VALUES('1','.doc','wordimage.png');
INSERT INTO filetype VALUES('2','.pdf','pdfimage.png');
INSERT INTO filetype VALUES('3','.txt','txtimage.png');
INSERT INTO filetype VALUES('4','.xls','xlsimage.png');
INSERT INTO filetype VALUES('5','.csv','csvimage.png');
INSERT INTO filetype VALUES('6','.sql','sqlimage.png');

ALTER TABLE sessions ADD COMMENT VARCHAR(200);

CREATE TABLE tutorialdocaction(
  id              VARCHAR(40),
  pid             VARCHAR(40),
  downloadedby    VARCHAR(40),
  downloadedon    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  ACTION          VARCHAR(100)
);



create table `mis`.`mail_template`( 
   `template_id` int NOT NULL AUTO_INCREMENT , 
   `name` varchar(100) , 
   `subject` varchar(512) , 
   `template` varchar(1024) , 
   `comment` varchar(100) , 
   `created_by` varchar(36) , 
   `created_on` date , 
   PRIMARY KEY (`template_id`)
 );

CREATE TABLE `misreport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL,
  `totviews` smallint(6) DEFAULT NULL,
  `totdownload` smallint(6) DEFAULT NULL,
  `createdby` varchar(40) DEFAULT NULL,
  `createdon` datetime DEFAULT NULL,
  `modifiedby` varchar(40) DEFAULT NULL,
  `modifiedon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Class Report :','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Payment','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Report Card Result','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Attendence Report','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Student Report','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Addmission Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Assigment Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Home work Report','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Teacher Report','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Digital Diary','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Examination Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Student Promotion','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Leave Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN'); 
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Time table Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Fee Generation Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Transport Report','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Library Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Holidays Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Audit Trial Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');
INSERT INTO misreport (NAME, description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) VALUES ('Fee Template Reports','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');

UPDATE misreport SET reporttype='85e8b7f9-01d6-467c-a76c-57985b361e6a' ;

INSERT INTO misreport (NAME, reporttype,description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) 
VALUES ('Student Fee Reports','2a97c973-01a7-463b-aa2e-b3871e440a0c','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');

INSERT INTO misreport (NAME, reporttype,description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) 
VALUES ('Monthly Student Attendence Reports','2a97c973-01a7-463b-aa2e-b3871e440a0c','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');


INSERT INTO misreport (NAME, reporttype,description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) 
VALUES ('Weekly Attendence Report','4548bec1-07a6-4ff7-ae45-e7b8c328b7eb','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');

INSERT INTO misreport (NAME, reporttype,description, STATUS, totviews, totdownload, createdby, createdon, modifiedby) 
VALUES ('Daily Attendence Report','17df2006-711d-44a8-9dff-9ae94923c90e','',1,0,0,'SUPER ADMIN',CURRENT_DATE,'SUPER ADMIN');

CREATE TABLE actions (
  actionid INT(5) DEFAULT '0' NOT NULL,
  actiontype VARCHAR(256) DEFAULT '' NOT NULL,
  CONSTRAINT pk_audittrailtype PRIMARY KEY  (actionid)
);

INSERT INTO actions VALUES(101,"User %s logged in successfully.");
CREATE TABLE audittrail (
  actionid INT(5) NOT NULL,
  params VARCHAR(100),
  ipaddr VARCHAR(36) DEFAULT '' NOT NULL,
  userid VARCHAR(36) DEFAULT '' NOT NULL,
  ts bigint,
  CONSTRAINT audittrail_ibfk_1 FOREIGN KEY (actionid) REFERENCES actions (actionid) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO mail_template (template_id, NAME, SUBJECT, template, COMMENT, created_by, created_on)
	VALUES(101,'SEND ADMISSION LINK TO PARENT','Online Addmision Form Link','Greeting <b>%s</b>,<br>Thank you FOR registering WITH us.<b>Welcome </b>TO Online admission PROCESS , Please click below link TO apply online & fill your student detail<br><b>%s</b>','This is for sending admision link to parent','ADMIN',CURRENT_DATE);

INSERT INTO mail_template (template_id, NAME, SUBJECT, template, COMMENT, created_by, created_on)
VALUES(102,
      'SEND INTERVIEW/EXAMINATION DATE TO PARENT','Scheduled Intervew and Examination date','Greeting <b>%s</b>,<br>Thank you for registering with us.<b>Welcome </b>to Online admission Process. Please find below examination and interview date scheduled for your child <br> Examination Date : <b>%s</b><br> Interview Date : <b>%s</b> <br> <b>Thank You .<br>Administrator</b>','Send Interview and Examination date to parent', 'ADMIN', CURRENT_DATE);

INSERT INTO mail_template (template_id, NAME, SUBJECT, template, COMMENT, created_by, created_on)
	VALUES(103,'CONTACT ADMIN','Contact Admin','Greeting Admin,
	<br><b>%s</b> , Has trying to contact You.<br>
	<b>Title :</b>%s<br>
        <b>Message :</b>%s<br>	
        <br>Thanks.
	','Email Send to admin','ADMIN',CURRENT_DATE);


INSERT INTO jasperdownloadconfig (modulename,functionname,sortfuntionname,jasperfilename,filetitle)
VALUES	('Timetable','StudentTimeTable','dstt','timetable.jrxml','Student Timetable');
