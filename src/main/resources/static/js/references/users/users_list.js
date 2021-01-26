var userTableColumns = [
    { id: 'login',      header: 'Логин',                     adjust: true, sort: 'string'},
    { id: 'firstname',  header: 'Фамилия',  fillspace: 1,    adjust: true, sort: 'string', },
    { id: 'lastname',   header: 'Имя',      fillspace: 1,    adjust: true, },
    { id: 'patronymic', header: 'Отчество', fillspace: 1,    adjust: true, },
]

var userTable = {
    id: 'user_table',
    view: 'datatable',
    select: 'row',
    resizeColumn:true,
    readonly: true,
    columns: userTableColumns,
    on: {
        onBeforeLoad: function () {
            this.showOverlay("Загружаю...");
        },
        onAfterLoad: function () {
            this.hideOverlay();
            if (!this.count()) {
                this.showOverlay("Отсутствуют данные")
            }
        },
        onLoadError: function () {
            this.hideOverlay();
        },
        onItemDblClick: function (id) {
            openRkkTab(id);
        }
    },
    url: 'user_list',
}

const userList = {
    view: 'scrollview',
    id: 'userListId',
    scroll: 'xy',
    body: {
        type: 'space',
        rows: [
            {
                autowidth: true,
                autoheight: true,
                rows: [
                    userTable,
                ]
            }]
    }
}