INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (1, 'Субъекты права законодательной инициативы', null, '1', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (2, 'Депутаты Народного Хурала', 1, '1.1', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (3, 'Глава Республики Бурятия', 1, '1.2', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (4, 'Правительство Республики Бурятия', 1, '1.3', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (5, 'Представительные органы местного самоуправления в Республике Бурятия', 1, '1.4', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (6, 'Верховный Суд Республики Бурятия', 1, '1.5', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (7, 'Арбитражный суд Республики Бурятия', 1, '1.6', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (8, 'Прокуратура Республики Бурятия', 1, '1.7', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (9, 'Комитеты Народного Хурала', null, '2', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (10, 'Комитет по бюджету, налогам и финансам', 9, '2.1', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (11, 'Комитет по государственному устройству, местному самоуправлению, законности и вопросам государственной службы', 9, '2.2', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (12, 'Комитет по социальной политике', 9, '2.3', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (13, 'Комитет по земельным вопросам, аграрной политике и потребительскому рынку', 9, '2.4', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (14, 'Комитет по межрегиональным связям, национальным вопросам, молодежной политике общественным и религиозным объединениям', 9, '2.5', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (15, 'Комитет по экономической политике, природопользованию и экологии', 9, '2.6', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (16, 'Управления Аппарата Народного Хурала', null, '3', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (17, 'Правовое управление', 16, '3.1', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (18, 'Информационно-аналитическое управление', 16, '3.2', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (19, 'Организационно-техническое управление', 16, '3.3', true, false);
INSERT INTO public.cls_organization (id, name, parent_id, path, is_activated, is_deleted) VALUES (20, 'Организационный отдел', 19, '3.3.1', true, false);

INSERT INTO cls_rkk_status(name, code) VALUES('В РАБОТЕ', '1');
INSERT INTO cls_rkk_status(name, code) VALUES('ОТОЗВАН', '2');
INSERT INTO cls_rkk_status(name, code) VALUES('К РАССМОТРЕНИЮ ', '3');
INSERT INTO cls_rkk_status(name, code) VALUES('ОТКЛОНЕН', '4');
INSERT INTO cls_rkk_status(name, code) VALUES('ПРИНЯТ', '5');

INSERT INTO cls_group_attachment(name, code) VALUES ('ВНЕС', '1');
INSERT INTO cls_group_attachment(name, code) VALUES ('ПРИНЯТ', '2');
INSERT INTO cls_group_attachment(name, code) VALUES ('К ЗАСЕД', '3');
INSERT INTO cls_group_attachment(name, code) VALUES ('РЕК', '4');
INSERT INTO cls_group_attachment(name, code) VALUES ('НЕОБ', '5');
INSERT INTO cls_group_attachment(name, code) VALUES ('ВИЗ', '6');

INSERT INTO cls_role(name) VALUES ('ADMIN');
