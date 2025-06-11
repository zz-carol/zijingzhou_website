-- THIS WORK WAS MY (OUR) OWN WORK. IT WAS WRITTEN WITHOUT CONSULTING
-- WORK WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES.
-- Audrey Poon, Carol Zhou

set search_path to dimedb;

CREATE VIEW TopThree AS (
SELECT DISTINCT "bonica.cid", "most.recent.contributor.name" AS name, "contributor.type" AS type, 
       ("amount.1980" + "amount.1982" + "amount.1984" + 
        "amount.1986" + "amount.1988" + "amount.1990" + 
        "amount.1992" + "amount.1994" + "amount.1996" + 
        "amount.1998" + "amount.2000" + "amount.2002" + 
        "amount.2004" + "amount.2006" + "amount.2008" + 
        "amount.2010" + "amount.2012" + "amount.2014" + 
        "amount.2016" + "amount.2018" + "amount.2020" + 
        "amount.2022") AS totalAmount
FROM Contributor
ORDER BY totalAmount DESC
LIMIT 3);

-- Calculate the total donation amount for each candidate
CREATE VIEW RecipientTotalAmount AS(
SELECT "bonica.cid", "bonica.rid", "recipient.name" AS recipient_name, SUM(amount) AS max_amount
FROM contribution
WHERE "recipient.type" = 'CAND' AND 
       "bonica.cid" IN (SELECT "bonica.cid" FROM TopThree)
GROUP BY "bonica.cid", "bonica.rid", "recipient.name");

-- Identify the candidates with maximum contributions for each donor
CREATE VIEW TopCandidates AS(
SELECT r."bonica.cid", r."bonica.rid", r.recipient_name, r.max_amount
FROM RecipientTotalAmount r
JOIN (
    SELECT "bonica.cid", MAX(max_amount) AS max_amount
    FROM RecipientTotalAmount
    GROUP BY "bonica.cid"
) AS maxAmounts
ON r."bonica.cid" = maxAmounts."bonica.cid" AND r.max_amount = maxAmounts.max_amount);

-- Combine TopCandidates with donors whose max amount recipients aren't candidates
CREATE VIEW TopDonorNRecipient AS(
(SELECT * FROM TopCandidates)
UNION
(SELECT "bonica.cid", NULL AS "bonica.rid", NULL AS recipient_name, NULL AS max_amount
FROM TopThree 
WHERE "bonica.cid" IN (
(SELECT "bonica.cid"
FROM TopThree)
EXCEPT
(SELECT "bonica.cid"
FROM TopCandidates))));

SELECT "bonica.cid", name AS donor_name, "bonica.rid", recipient_name, max_amount
FROM TopDonorNRecipient t1 NATURAL JOIN TopThree t2
ORDER BY donor_name, max_amount DESC;