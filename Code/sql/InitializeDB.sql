DROP DATABASE IF EXISTS matchmaking;
CREATE DATABASE matchmaking;
USE matchmaking;

DROP TABLE IF EXISTS profile;
CREATE TABLE user (
username varchar(30) not null,
password varchar(30) not null,
firstName varchar(30) not null,
lastName varchar(30) not null,
userType varchar(30) not null,
hourlyRate double not null,
isVerified bool not null,
paymentEmail varchar(100) not null,
portfolioLink varchar(100),
primary key(username)
);

-- DROP TABLE IF EXISTS offer;
-- CREATE TABLE offer (
-- offerId integer not null auto_increment,
-- offeredRate double not null,
-- offerStatus varchar(30) not null,
-- providerId varchar(30) not null,
-- clientId varchar(30) not null,
-- primary key(offerId),
-- foreign key(providerId) references user(username),
-- foreign key(clientId) references user(username)
-- );

-- DROP TABLE IF EXISTS contract;
-- -- CREATE TABLE contract (
-- -- contractId integer not null auto_increment,
-- -- rate double not null,
-- -- providerId varchar(30) not null,
-- -- clientId varchar(30) not null,
-- -- primary key(contractId),
-- -- foreign key(clientId) references user(username)
-- -- );

DROP TABLE IF EXISTS project;
CREATE TABLE project (
projectId integer not null auto_increment,
providerId varchar(30) not null,
clientId varchar(30) not null,
rate double not null,
deadline varchar(50),
progressPercentage double not null,
projectName varchar(30) not null,
projectDescription varchar(300),
projectStatus varchar(30),
primary key(projectId),
foreign key(clientId) references user(username)
);

DROP TABLE IF EXISTS payment;
CREATE TABLE payment (
paymentId integer not null auto_increment,
projectId integer,
amount double not null,
paymentStatus varchar(30) not null,
primary key(paymentId),
foreign key(projectId) references project(projectId)
);


