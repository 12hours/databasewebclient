INSERT INTO `children`
(`child_id`,
`address`,
`birth_date`,
`disability`,
`family_name`,
`name`,
`patr_name`,
`place_of_education`,
`special_needs`)
VALUES
(1, 'Vulka 171-3', '1999-01-13', 0, 'Shumak', 'Vika', 'Vladimirovna', 'Sad N3', 0),
(2, 'Suvorova 12-13', '2005-08-28', 0, 'Lagun', 'Nikolay', 'Olegovich', 'Sad N5', 0),
(3, 'Folush 212-102', '2008-08-01', 0, 'Lis', 'Roman', 'Olegovich', 'Sad N8', 0),
(4, 'Vulka 155-8', '2009-12-12', 0, 'Chubrik', 'Alexei', 'Anatolevich', 'Sad N5', 0),
(5, 'Kosmonavtov 20-3', '2003-07-21', 0, 'Shoka', 'Stanislav', 'Andreevich', 'Shcool 20', 0);

INSERT INTO `diagnoses`
(`id`,
`diagnosis`)
VALUES
(1, 'F80'),
(2, 'F81'),
(3, 'F84'),
(4, 'F83'),
  (5, 'F82');

INSERT INTO `disorders`
(`id`,
`disorder`)
VALUES
(1, 'Alaliya'),
(2, 'ZPR'),
(3, 'Dislaliya'),
(4, 'Dislexiya'),
(5, 'Autism');

INSERT INTO `edu_programs`
(`id`,
`program`)
VALUES
(1, 'Regular education'),
(2, 'Special education in school'),
(3, 'Special pre-school education');

INSERT INTO `recommends`
(`id`,
`recommendation`)
VALUES
(1, 'Lessons with psychologist'),
(2, 'Lessons with defectologist'),
(3, 'Special group');

INSERT INTO `surveys`
(`survey_id`,
  `survey_date`,
  `protocol_number`,
`diagnosis`,
  `determined_disorder`,
  `recommended_program`,
  `recommended_form`,
  `remarks`,
  `child_id`,
  `child_name`)
VALUES
  (1, '2016-12-08', 101, 'NA diagnosis', 'NA disorder', 'NA program', 'NA form', 'NA remaks', 1, 'Shumak Vika Vladimirovna'),
  (2, '2016-12-09', 102, 'NA diagnosis', 'NA disorder', 'NA program', 'NA form', 'NA remaks', 3, 'Lis Roman Olegovich'),
  (3, '2016-12-10', 103, 'NA diagnosis', 'NA disorder', 'NA program', 'NA form', 'NA remaks', 1, 'Shumak Vika Vladimirovna'),
  (4, '2016-12-11', 104, 'NA diagnosis', 'NA disorder', 'NA program', 'NA form', 'NA remaks', 2, 'Lagun Nikolay Olegovich'),
  (5, '2016-12-12', 105, 'NA diagnosis', 'NA disorder', 'NA program', 'NA form', 'NA remaks', 4, 'Chubrik Alexei Anatolevich'),
  (6, '2016-12-13', 106, 'NA diagnosis', 'NA disorder', 'NA program', 'NA form', 'NA remaks', 5, 'Shoka Stanislav Andreevich'),
  (7, '2016-12-14', 107, 'NA diagnosis', 'NA disorder', 'NA program', 'NA form', 'NA remaks', 3, 'Lis Roman Olegovich');

INSERT INTO `surveys_diagnoses`
(`survey_id`,
`diagnosis_id`)
VALUES
  (1, 2),
  (2, 2),
  (3, 1),
  (3, 4),
  (3, 2),
  (4, 3),
  (4, 4),
  (5, 1),
  (6, 4),
  (6, 5),
  (7, 2),
  (7 ,3);

INSERT INTO `surveys_disorders`
(`survey_id`,
`disorder_id`)
VALUES
  (1, 2),
  (2, 2),
  (3, 5),
  (3, 4),
  (4, 1),
  (4, 2),
  (4, 4),
  (5, 3),
  (5, 4),
  (6, 1),
  (7, 3),
  (7, 5);

INSERT INTO `surveys_edu_programs`
(`survey_id`,
`edu_pr_id`)
VALUES
  (1, 1),
  (1, 3),
  (2, 1),
  (2, 2),
  (3, 1),
  (4, 2),
  (5, 2),
  (5, 3),
  (6, 3),
  (7, 1),
  (7, 3);

INSERT INTO `surveys_recommends`
(`survey_id`,
`rec_id`)
VALUES
  (1, 1),
  (1, 3),
  (2, 1),
  (2, 2),
  (3, 1),
  (4, 2),
  (5, 2),
  (5, 3),
  (6, 3),
  (7, 1),
  (7, 3);






