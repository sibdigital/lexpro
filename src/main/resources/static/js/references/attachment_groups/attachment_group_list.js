var attachmentGroupTableColumns = [
    { id: 'id',   header: 'ID',                            adjust: true, sort: 'string'},
    { id: 'name', header: 'Наименование',  fillspace: 1,   adjust: true, sort: 'string'},
]

var attachmentGroupTable = {
    id: 'attachmentGroupTableId',
    view: 'datatable',
    select: 'row',
    resizeColumn:true,
    readonly: true,
    columns: attachmentGroupTableColumns,
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
    url: 'attachment_group_list',
}

var topPanelAttachGroupListData = [
    { id: 1, value: 'Добавить', icon: 'plus-square', class: 'addAttachGroup'},
    { id: 2, value: 'Редактировать', icon: 'edit',   class: 'editAttachGroup'},
    { id: 4, value: 'Удалить', icon: 'trash-alt',    class: 'deleteAttachGroup'},
]

function saveAttachGroup(attachGroup) {
    webix.ajax().headers({
        'Content-Type': 'application/json'
    }).post('/save_attach_group', attachGroup).then(function (data) {
        if (data.text() === 'Группа сохранена') {
            webix.message({
                text: 'Сохранено',
                type: 'success'
            });

            $$('attachmentGroupTableId').clearAll();
            $$('attachmentGroupTableId').load('attachment_group_list');
        }})
}

function addAttachGroup() {
    webix.prompt({
        title: 'Создание новой группы для вложений',
        text: 'Введите название группы',
        ok: 'Сохранить',
        cancel: 'Отмена',
        input: {
            required: true,
            placeholder: 'Название группы',
        },
        width: 350,
    }).then(function (result) {
        attachGroup = {
            'name': result,
        }
        saveAttachGroup(attachGroup);
    })
}

function editAttachGroup(selectedRow) {
    let id = selectedRow.id;
    let attachGroup = $$('attachmentGroupTableId').getItem(id);
    webix.prompt({
        title: 'Редактирование группы',
        text: 'Введите новое название группы',
        ok: 'Сохранить',
        cancel: 'Отмена',
        input: {
            required: true,
            value: attachGroup.name,
        },
        width: 350,
    }).then(function (result) {
        attachGroup.name = result;
        saveAttachGroup(attachGroup);
    })
}

function deleteAttachGroup(selectedRow) {
    let id = selectedRow.id;
    let attachGroup = $$('attachmentGroupTableId').getItem(id);
    webix.ajax().headers({
        'Content-Type': 'application/json'
    }).post('/delete_attach_group', attachGroup).then(function (data) {
        if (data.text() === 'Группа удалена') {
            webix.message({
                text: 'Удалено',
                type: 'success'
            });

            $$('attachmentGroupTableId').clearAll();
            $$('attachmentGroupTableId').load('attachment_group_list');
        }})
}

function getTopPanelAttachGroupList() {
    return {
        view: 'list',
        scroll: false,
        width: 200,
        layout: 'x',
        template: '<div class = "#class#"><span class="webix_icon fas fa-#icon#"></span> #value# </div>',
        css: 'panelIcon',
        // select: true,
        type: {
            width: 'auto',
            height: 40,
        },
        data: topPanelAttachGroupListData,
        onClick: {
            'addAttachGroup': function () {
                addAttachGroup();
            },
            'editAttachGroup': function () {
                var selectedRows = $$('attachmentGroupTableId').getSelectedId(true);
                if (selectedRows.length == 1) {
                    editAttachGroup(selectedRows[0]);
                }
            },
            'deleteAttachGroup': function () {
                var selectedRows = $$('attachmentGroupTableId').getSelectedId(true);
                if (selectedRows.length > 0) {
                    deleteAttachGroup(selectedRows[0]);
                }
            },
        }
    }
}

export const attachmentGroupList = {
    view: 'scrollview',
    id: 'attachmentGroupListId',
    scroll: 'xy',
    body: {
        type: 'space',
        rows: [
            {
                autowidth: true,
                autoheight: true,
                rows: [
                    getTopPanelAttachGroupList(),
                    attachmentGroupTable,
                ]
            }]
    }
}