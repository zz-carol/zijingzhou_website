-- THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
-- CODE WRITTEN BY OTHER STUDENTS.
-- AUDREY POON, CAROL ZHOU 

SET search_path TO dimeDB;

SELECT DISTINCT "bonica.rid", name, cycle, "num.givers" AS numDonors
FROM Recipient
WHERE "num.givers" = (SELECT MAX("num.givers") FROM Recipient)
ORDER BY name;