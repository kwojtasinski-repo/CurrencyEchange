INSERT INTO country (country_name) VALUES
('AUSTRIA'),
('UNITED KINGDOM OF GREAT BRITAIN AND NORTHERN IRELAND'),
('UNITED STATES OF AMERICA'),
('SWITZERLAND'),
('AUSTRALIA');

INSERT INTO currency (currency_code, currency_name) VALUES
('EUR', 'euro'),
('GBP', 'funt szterling'),
('USD', 'dolar amerykaski'),
('CHF', 'frank szwajcarski'),
('AUD', 'dolar australijski');

INSERT INTO currency_country (id_currency, id_country) VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,3),
(2,1), 
(5,5); 

INSERT INTO currency_rate (currency_date, currency_rate, id_currency) VALUES
('2020-12-24',	5.0286,	2), 
('2020-12-24',	4.5084,	1), 
('2020-12-24',	3.6981,	4), 
('2020-12-24',	4.1604,	5), 
('2020-12-24',	3.8084,	3), 
('2021-02-21',	5.0512,	2),
('2021-02-19',	3.7510,	3),
('2021-02-19',	4.4892,	1),
('2009-02-20',	4.7715,	1),
('2009-02-20',	4.4196,	5),
('2009-02-20',	3.7823,	4),
('2021-01-21',	5.0212,	2),
('2021-01-22',	5.0252,	2),
('2021-01-21',	4.2212,	5);

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