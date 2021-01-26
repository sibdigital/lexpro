webix.i18n.setLocale("ru-RU");

OPERATION_READ = "READ_";
OPERATION_WRITE = "WRITE_"; //create + update
OPERATION_DELETE = "DELETE_"

function checkPrivilege(privilegeName) {
    return PRIVILEGES.includes(privilegeName);
}

function getSideBarData() {
    var data = [{ id: 'References', icon: 'fas fa-book', value: 'Справочники',}];
    if (checkPrivilege('READ_RKK')) {
        data.push({ id: 'Rkk', icon: 'fas fa-file-alt',   value: 'РКК', });
    }

    return data;
}

var sideBarData = [
    // Позже переименовать id
    // { id: "ID1", icon: "fas fa-globe",      value: 'Управление доступом, пользователями и ролевой моделью' },
    // { id: "ID2", icon: "fas fa-globe",      value: 'Ведение нормативно-справочной информации' },
    // { id: 'Rkk', icon: 'fas fa-file-alt',   value: 'РКК', },
    // { id: "ID4", icon: "fas fa-globe",      value: 'Согласование документов' },
    // { id: "ID5", icon: "fas fa-globe",      value: 'Хранение файлов' },
    // { id: "ID6", icon: "fas fa-globe",      value: 'Статистика и отчетность' },
    // { id: "ID7", icon: "fas fa-globe",      value: 'Публикация документов' },

]

function getView(id) {
    let view;
    switch (id) {
        case 'Rkk': {
            view = rkkList;
            break;
        }
        case 'References': {
            view = referenceList;
            break;
        }
    }
    return view;
}

const upperToolbar = {
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
                    id: 'labelLK',
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
}

const sideBar = {
        view: 'sidebar',
        id: 'sidebar',
        css: 'webix_dark',
        // data: sideBarData,
        data: getSideBarData(),
        on: {
            onAfterSelect: function(id) {
                let view = getView(id);
                webix.ui({
                    id: 'content',
                    rows: [
                        view
                    ]
                }, $$('content'))
            }
        }
    }

webix.ready(function() {
    let layout = webix.ui({
        rows: [
            upperToolbar,
            {
                cols: [
                    sideBar,
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

