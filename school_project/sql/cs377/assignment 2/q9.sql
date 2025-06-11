-- THIS WORK WAS MY (OUR) OWN WORK. IT WAS WRITTEN WITHOUT CONSULTING
-- WORK WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES.
-- Audrey Poon, Carol Zhou

set search_path to dimedb;

-- delete all recipients who are independent 
DELETE FROM Recipient
WHERE "party" = '328';

-- delete all votes cast by independent legislators
DELETE FROM Vote
WHERE "party" = '328';

-- delete all contributions made to independent candidates
DELETE FROM Contribution
WHERE "bonica.rid" IN (
    SELECT "bonica.rid"
    FROM Recipient
    WHERE "recipient.party" = '328'
);