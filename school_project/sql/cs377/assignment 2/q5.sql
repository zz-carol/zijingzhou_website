-- THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
-- CODE WRITTEN BY OTHER STUDENTS.
-- AUDREY POON, CAROL ZHOU 

SET search_path TO dimeDB;

CREATE VIEW billCosponsors AS (
  SELECT "bill.id", COUNT(*) AS numCosponsors
  FROM Vote
  WHERE cosponsor = 1
  GROUP BY "bill.id"
);

SELECT DISTINCT b."bill.id", bc.numCosponsors, b."bill.str"
FROM Bill b
JOIN BillCosponsors bc ON b."bill.id" = bc."bill.id"
WHERE bc.numCosponsors = (SELECT MAX(numCosponsors) FROM billCosponsors)
ORDER BY b."bill.id";