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
        data.push({ id: 'ArchivedRkk', icon: 'fas fa-folder', value: 'Архив РКК'})
        data.push({ id: 'DeletedRkk', icon: 'fas fa-trash-alt', value: 'Удаленные РКК'});
    }

    return data;
}

function getView(id) {
    let view;
    switch (id) {
        case 'Rkk': {
            view = rkkList;
            break;
        }
        case 'ArchivedRkk': {
            view = rkkArchivedList;
            break;
        }
        case 'DeletedRkk': {
            view = rkkDeletedList;
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

