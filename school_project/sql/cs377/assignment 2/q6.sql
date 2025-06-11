-- THIS WORK WAS MY (OUR) OWN WORK. IT WAS WRITTEN WITHOUT CONSULTING
-- WORK WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES.
-- Audrey Poon, Carol Zhou

set search_path to dimedb;

-- legislators who sponsored bills
CREATE VIEW sponsoredBills AS (
    SELECT "bonica.rid", party, COUNT(DISTINCT "bill.id") AS numbills
    FROM vote
    WHERE sponsor = 1
    GROUP BY "bonica.rid", party
);

-- legislators who sponsored and voted against their own bills
CREATE VIEW sponsoredAndVotedAgainst AS (
    SELECT "bonica.rid"
    FROM Vote
    WHERE sponsor = 1 AND "vote.choice" = 6
);

-- legislators who sponsored bills but never voted against them
SELECT sb."bonica.rid", sb.party, sb.numbills
FROM sponsoredBills sb
WHERE sb."bonica.rid" NOT IN (SELECT "bonica.rid" FROM sponsoredAndVotedAgainst)
ORDER BY sb.numbills DESC;
