INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Субъекты права законодательной инициативы', true, false, '1');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Депутаты Народного Хурала', true, false, '1.1');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Глава Республики Бурятия', true, false, '1.2');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Правительство Республики Бурятия', true, false, '1.3');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Представительные органы местного самоуправления в Республике Бурятия', true, false, '1.4');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Верховный Суд Республики Бурятия', true, false, '1.5');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Арбитражный суд Республики Бурятия', true, false, '1.6');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Прокуратура Республики Бурятия', true, false, '1.7');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Комитеты Народного Хурала', true, false, '2');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Комитет по бюджету, налогам и финансам', true, false, '2.1');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Комитет по государственному устройству, местному самоуправлению, законности и вопросам государственной службы', true, false, '2.2');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Комитет по социальной политике', true, false, '2.3');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Комитет по земельным вопросам, аграрной политике и потребительскому рынку', true, false, '2.4');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Комитет по межрегиональным связям, национальным вопросам, молодежной политике общественным и религиозным объединениям', true, false, '2.5');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Комитет по экономической политике, природопользованию и экологии', true, false, '2.6');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Управления Аппарата Народного Хурала', true, false, '3');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Правовое управление', true, false, '3.1');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Информационно-аналитическое управление', true, false, '3.2');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Организационно-техническое управление', true, false, '3.3');
INSERT INTO cls_organization(name, is_activated, is_deleted, path) VALUES ('Организационный отдел', true, false, '3.3.1');

INSERT INTO cls_rkk_status(name, code) VALUES('В РАБОТЕ', '1');
INSERT INTO cls_rkk_status(name, code) VALUES('ОТОЗВАН', '2');
INSERT INTO cls_rkk_status(name, code) VALUES('К РАССМОТРЕНИЮ ', '3');
INSERT INTO cls_rkk_status(name, code) VALUES('ОТКЛОНЕН', '4');
INSERT INTO cls_rkk_status(name, code) VALUES('ПРИНЯТ', '5');

INSERT INTO cls_group_attachment(name, code) VALUES ('ВНЕС', '1');
INSERT INTO cls_group_attachment(name, code) VALUES ('ПРИНЯТ', '2');
INSERT INTO cls_group_attachment(name, code) VALUES ('К ПРИНЯТ', '3');
INSERT INTO cls_group_attachment(name, code) VALUES ('РЕК', '4');
INSERT INTO cls_group_attachment(name, code) VALUES ('НЕОБ', '5');
INSERT INTO cls_group_attachment(name, code) VALUES ('ВИЗ', '6');

INSERT INTO cls_role(name) VALUES ('ADMIN');
