-- THIS WORK WAS MY (OUR) OWN WORK. IT WAS WRITTEN WITHOUT CONSULTING
-- WORK WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES.
-- Audrey Poon, Carol Zhou

set search_path to dimedb;

DROP TABLE IF EXISTS Influence;

-- task 1
CREATE TABLE Influence (
    "bill.id" varchar REFERENCES Bill("bill.id"),
    "vote.id" varchar REFERENCES Vote("vote.id"),
    "dw.economy" float DEFAULT 0 CHECK ("dw.economy" >= 0 AND "dw.economy" <= 1),
    "dw.environment" float DEFAULT 0 CHECK ("dw.environment" >= 0 AND "dw.environment" <= 1),
    "dw.foreign.policy" float DEFAULT 0 CHECK ("dw.foreign.policy" >= 0 AND "dw.foreign.policy" <= 1),
    "dw.womens.issues" float DEFAULT 0 CHECK ("dw.womens.issues" >= 0 AND "dw.womens.issues" <= 1),
    "dw.guns" float DEFAULT 0 CHECK ("dw.guns" >= 0 AND "dw.guns" <= 1),
    "dw.healthcare" float DEFAULT 0 CHECK ("dw.healthcare" >= 0 AND "dw.healthcare" <= 1),
    PRIMARY KEY ("bill.id", "vote.id")
);

-- task 2 
-- from Q5
CREATE VIEW billCosponsors AS (
 SELECT "bill.id", COUNT(*) AS numCosponsors
 FROM Vote
 WHERE cosponsor = 1
 GROUP BY "bill.id"
);

CREATE VIEW maxCosponsers AS (
SELECT DISTINCT b."bill.id", bc.numCosponsors, b."bill.str"
FROM Bill b
JOIN BillCosponsors bc ON b."bill.id" = bc."bill.id"
WHERE bc.numCosponsors = (SELECT MAX(numCosponsors) FROM billCosponsors)
ORDER BY b."bill.id");

INSERT INTO Influence ("bill.id", "vote.id", "dw.environment", "dw.foreign.policy") (
SELECT v."bill.id", v."vote.id", 0.3, 0.7
FROM Vote v
JOIN maxCosponsers mc ON v."bill.id" = mc."bill.id");
