-- THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
-- CODE WRITTEN BY OTHER STUDENTS.
-- AUDREY POON, CAROL ZHOU 

SET search_path TO dimeDB;

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
LIMIT 10;