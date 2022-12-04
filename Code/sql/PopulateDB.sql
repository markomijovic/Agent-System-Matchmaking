USE matchmaking;

INSERT INTO user
VALUES ("ProviderAdmin", "pass123", "ProviderName", "ProviderLastName", "Provider", 50.0, 1, "provider@gmail.com", "provider.com"),
		("ClientAdmin", "pass123", "ClientName", "ClientLastName", "Client", 40, 1, "Client@gmail.com", null),
		("Guest", "12345", "Guest", "User", "Guest", 0, 0, "guest@test.com", null);
		("OpenProject", "12345", "Open", "Project", "Provider", 0, 1, "openproject@test.com", null);
-- INSERT INTO offer
-- VALUES (1, 50.0, "Agreed", "ProviderAdmin", "ClientAdmin");

-- INSERT into contract
-- VALUES (1, 1, "Signed", 50.0);

INSERT into project
VALUES (1, "ProviderAdmin", "ClientAdmin", 30.0, "Dec 30, 2022", 0.0, "TestProject", "Test Description", "In Progress");

INSERT into payment
VALUES (1, "ProviderAdmin", "ClientAdmin", 3050, "Paid");