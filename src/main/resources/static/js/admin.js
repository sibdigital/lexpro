webix.i18n.setLocale("ru-RU");

webix.ready(function() {
    let layout = webix.ui({
        rows: [
            {
                view: 'toolbar',
                autoheight: true,
                rows: [
                    {
                        responsive: 't1',
                        css: 'webix_dark',
                        cols: [
                            {
                                view: 'label',
                                width: 40,
                                template: "<img height='35px' width='35px' src = \"favicon.ico\">"
                            },
                            {
                                view: 'label',
                                minWidth: 400,
                                label: '<span style="font-size: 1.0rem">Личный кабинет администратора</span>',
                            },
                            {},
                            {
                                view: 'label',
                                label: ' (<a href="/logout" title="Выйти">' + USER_NAME + '</a>)',
                                align: 'right'
                            }
                        ]
                    }
                ]
            },
            {
                cols: [
                    {
                        view: 'sidebar',
                        id: 'sidebar',
                        css: 'webix_dark',
                        data: [
                            // Позже переименовать id
                            { id: "ID1", icon: "fas fa-globe", value: 'Управление доступом, пользователями и ролевой моделью' },
                            { id: "ID2", icon: "fas fa-globe", value: 'Ведение нормативно-справочной информации' },
                            { id: "ID3",icon: "fas fa-globe", value: 'Ведение регистрационно-контрольных карт' },
                            { id: "ID4", icon: "fas fa-globe", value: 'Согласование документов' },
                            { id: "ID5", icon: "fas fa-globe", value: 'Хранение файлов' },
                            { id: "ID6", icon: "fas fa-globe", value: 'Статистика и отчетность' },
                            { id: "ID7", icon: "fas fa-globe", value: 'Публикация документов' },
                        ],
                        // on: {
                        //     onAfterSelect: function(id) {
                        //         let view;
                        //         switch (id) {
                        //             case 'Departments': {
                        //                 view = departments;
                        //                 break;
                        //             }
                        //             case 'DepartmentUsers': {
                        //                 view = departmentUsers;
                        //                 break;
                        //             }
                        //
                        //         }
                        //         webix.ui({
                        //             id: 'content',
                        //             rows: [
                        //                 view
                        //             ]
                        //         }, $$('content'))
                        //
                        //     }
                        // }
                    },
                    {
                        id: 'content'
                    }
                ],
            }
        ]
    })

    webix.event(window, "resize", function (event) {
        layout.define("width",document.body.clientWidth);
        layout.define("height",window.innerHeight);
        layout.resize();
    });

})

