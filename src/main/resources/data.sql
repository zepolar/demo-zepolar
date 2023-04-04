
INSERT INTO "person" ("id", "created_at", "updated_at", "name", "national_identity", "surname") VALUES
                                                                                                    (1,	'2023-04-04 08:14:38.342',	NULL,	'ONTOP',	'123456679',	'INC'),
                                                                                                    (2,	'2023-04-04 08:15:07.278',	NULL,	'TONY',	'123456679',	'STARK');

INSERT INTO "bank_account" ("id", "created_at", "updated_at", "number", "routing", "person_id") VALUES
                                                                                                    (1,	'2023-04-04 08:14:38.405',	NULL,	'0245253419',	'028444018',	1),
                                                                                                    (2,	'2023-04-04 08:15:07.284',	NULL,	'1885226711',	'211927207',	2);