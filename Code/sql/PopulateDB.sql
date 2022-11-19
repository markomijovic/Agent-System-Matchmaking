USE matchmaking;

INSERT INTO user
VALUES ("ProviderAdmin", "pass123", "ProviderName", "ProviderLastName", "Provider", 50.0, true, "None", "provider@gmail.com", "provider.com"),
	("ClientAdmin", "pass123", "ClientName", "ClientLastName", "Client", null, true, null, "Client@gmail.com", null);
    
INSERT INTO offer
VALUES (1, 50.0, "Agreed", "ProviderAdmin", "ClientAdmin");

INSERT into contract
VALUES (1, 1, "Signed", 50.0);

INSERT into project
VALUES (1, 1, "Dec 30, 2022", 1.0, "TestProject", "Test Description", "In Progress");

INSERT into payment
VALUES (1, 1, 3050, "Paid");