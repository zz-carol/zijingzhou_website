-- THIS WORK WAS MY (OUR) OWN WORK. IT WAS WRITTEN WITHOUT CONSULTING
-- WORK WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES.
-- Audrey Poon, Carol Zhou

set search_path to dimedb;

CREATE VIEW maleFederal AS (
	SELECT 
		'federal' AS level, 
		"contributor.gender", 
		COUNT("transaction.id") AS count, 
		AVG(amount) AS average_amount
	FROM 
		contribution 
	WHERE 
		"contributor.gender" = 'M' 
		AND "seat" LIKE 'federal:%'
	GROUP BY 
		"contributor.gender" 
);

CREATE VIEW femaleFederal AS (
	SELECT 
		'federal' AS level, 
		"contributor.gender", 
		COUNT("transaction.id") AS count, 
		AVG(amount) AS average_amount
	FROM 
		contribution 
	WHERE 
		"contributor.gender" = 'F' 
		AND "seat" LIKE 'federal:%'
	GROUP BY 
		"contributor.gender"
);

CREATE VIEW maleLocal AS (
	SELECT 
		'local' AS level, 
		"contributor.gender", 
		COUNT("transaction.id") AS count, 
		AVG(amount) AS average_amount
	FROM 
		contribution 
	WHERE 
		"contributor.gender" = 'M' 
		AND "seat" LIKE 'local:%'
	GROUP BY 
		"contributor.gender"
);

CREATE VIEW femaleLocal AS (
	SELECT 
		'local' AS level, 
		"contributor.gender", 
		COUNT("transaction.id") AS count, 
		AVG(amount) AS average_amount
	FROM 
		contribution 
	WHERE 
		"contributor.gender" = 'F' 
		AND "seat" LIKE 'local:%'
	GROUP BY 
		"contributor.gender"
);


CREATE VIEW maleState AS (
	SELECT 
		'state' AS level, 
		"contributor.gender", 
		COUNT("transaction.id") AS count, 
		AVG(amount) AS average_amount
	FROM 
		contribution 
	WHERE 
		"contributor.gender" = 'M' 
		AND "seat" LIKE 'state:%'
	GROUP BY 
		"contributor.gender"
);

CREATE VIEW femaleState AS (
	SELECT 
		'state' AS level, 
		"contributor.gender", 
		COUNT("transaction.id") AS count, 
		AVG(amount) AS average_amount
	FROM 
		contribution 
	WHERE 
		"contributor.gender" = 'F' 
		AND "seat" LIKE 'state:%'
	GROUP BY 
		"contributor.gender"
);

SELECT * FROM maleFederal
UNION ALL
SELECT * FROM femaleFederal
UNION ALL
SELECT * FROM maleLocal
UNION ALL
SELECT * FROM femaleLocal
UNION ALL
SELECT * FROM maleState
UNION ALL
SELECT * FROM femaleState
ORDER BY level DESC, "contributor.gender" ASC;
