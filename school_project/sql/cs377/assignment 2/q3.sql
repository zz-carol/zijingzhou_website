-- THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
-- CODE WRITTEN BY OTHER STUDENTS.
-- AUDREY POON, CAROL ZHOU 

SET search_path TO dimeDB;

SELECT DISTINCT "bonica.cid", "most.recent.contributor.name" AS name, "contributor.type" AS type, "amount.2022" AS totalAmount
FROM Contributor
ORDER BY totalAmount DESC
LIMIT 10;