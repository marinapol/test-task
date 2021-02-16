CREATE TABLE bank 
(
	id UUID,
	name VARCHAR(20),
	PRIMARY KEY(id)
);

CREATE TABLE client
(
	id UUID,
	name VARCHAR(60),
	phone VARCHAR(11),
	email VARCHAR(60),
	passport_id VARCHAR(20),
	bank_id UUID,
	PRIMARY KEY(id),
	FOREIGN KEY (bank_id) REFERENCES bank(id)
);

CREATE TABLE credit
(
	id UUID,
	limit BIGINT,
	interest_rate REAL,
	bank_id UUID,
	PRIMARY KEY(id),
	FOREIGN KEY (bank_id) REFERENCES bank(id)
);

CREATE TABLE credit_offer
(
	id UUID,
	credit_amount BIGINT,
	client_id UUID,
	credit_id UUID,
	PRIMARY KEY(id),
	FOREIGN KEY (client_id) REFERENCES client(id),
	FOREIGN KEY (credit_id) REFERENCES credit(id)
);

CREATE TABLE payment
(
	id UUID,
	credit_amount REAL,
	interest_amount REAL,
	payment_amount REAL,
	credit_offer_id UUID,
	PRIMARY KEY(id),
	FOREIGN KEY (credit_offer_id) REFERENCES credit_offer(id)
);