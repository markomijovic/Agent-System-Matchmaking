DROP DATABASE IF EXISTS matchmaking;
CREATE DATABASE matchmaking;
USE matchmaking;

DROP TABLE IF EXISTS profile;
CREATE TABLE user (
username varchar(30) not null,
password varchar(30) not null,
firstName varchar(30) not null,
lastName varchar(30) not null,
hourlyRate double,
isVerified bool not null,
subscriptionPlan varchar(30),
paymentEmail varchar(100),
portfolioLink varchar(100),
primary key(username)
);

DROP TABLE IF EXISTS offer;
CREATE TABLE offer (
offerId integer not null auto_increment,
offeredRate double not null,
offerStatus varchar(30) not null,
providerId varchar(30) not null,
clientId varchar(30) not null,
primary key(offerId),
foreign key(providerId) references user(username),
foreign key(clientId) references user(username)
);

DROP TABLE IF EXISTS contract;
CREATE TABLE contract (
contractId integer not null auto_increment,
offerId integer not null,
finalStatus varchar(30) not null,
agreedRate double not null,
primary key(contractId),
foreign key(offerId) references offer(offerId)
);

DROP TABLE IF EXISTS project;
CREATE TABLE project (
projectId integer not null auto_increment,
contractId integer,
deadline varchar(50),
progressPercentage double not null,
projectName varchar(30) not null,
projectDescription varchar(300),
projectStatus varchar(30),
primary key(projectId),
foreign key(contractId) references contract(contractId)
);


