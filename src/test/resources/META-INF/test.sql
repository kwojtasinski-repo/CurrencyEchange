INSERT INTO country (id_country, country_name) VALUES
(1, 'AUSTRIA'),
(2, 'UNITED KINGDOM OF GREAT BRITAIN AND NORTHERN IRELAND'),
(3, 'UNITED STATES OF AMERICA'),
(4, 'SWITZERLAND'),
(5, 'AUSTRALIA');

INSERT INTO currency (id_currency , currency_code, currency_name) VALUES
(1, 'EUR', 'euro'),
(2, 'GBP', 'funt szterling'),
(3, 'USD', 'dolar amerykaski'),
(4, 'CHF', 'frank szwajcarski'),
(5, 'AUD', 'dolar australijski');

INSERT INTO currency_country (id_currency, id_country) VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,3),
(2,1), 
(5,5); 

INSERT INTO currency_rate (id_currency_rate, currency_date, currency_rate, id_currency) VALUES
(1,		'2020-12-24',	5.0286,	2), 
(2,		'2020-12-24',	4.5084,	1), 
(3,		'2020-12-24',	3.6981,	4), 
(4,		'2020-12-24',	4.1604,	5), 
(5,		'2020-12-24',	3.8084,	3), 
(6,		'2021-02-21',	5.0512,	2),
(7,		'2021-02-19',	3.7510,	3),
(8,		'2021-02-19',	4.4892,	1),
(9,		'2009-02-20',	4.7715,	1),
(10,	'2009-02-20',	4.4196,	5),
(11,	'2009-02-20',	3.7823,	4),
(12,	'2021-01-21',	5.0212,	2),
(13,	'2021-01-22',	5.0252,	2),
(14,	'2021-01-21',	4.2212,	5);

INSERT INTO currency_exchange (id_country, id_currency_rate) VALUES
(2,	1),
(1,	2),
(4,	3),
(5,	4),
(3,	5),
(2,	6),
(3,	7),
(1,	8),
(1,	9),
(5,	10),
(4,	11),
(1,	12),
(2,	13),
(3,	14);