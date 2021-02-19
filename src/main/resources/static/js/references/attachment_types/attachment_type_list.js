var attachmentTypeTableColumns = [
    { id: 'id',   header: 'ID',                            adjust: true, sort: 'string'},
    { id: 'name', header: 'Наименование',  fillspace: 1,   adjust: true, sort: 'string'},
]

var attachmentTypeTable = {
    id: 'attachmentTypeTableId',
    view: 'datatable',
    select: 'row',
    resizeColumn:true,
    readonly: true,
    columns: attachmentTypeTableColumns,
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
    url: 'attachment_type_list',
}

var topPanelAttachTypeListData = [
    { id: 1, value: 'Добавить', icon: 'plus-square', class: 'addAttachType'},
    { id: 2, value: 'Редактировать', icon: 'edit',   class: 'editAttachType'},
    { id: 4, value: 'Удалить', icon: 'trash-alt',    class: 'deleteAttachType'},
]

function saveAttachType(attachType) {
    webix.ajax().headers({
        'Content-Type': 'application/json'
    }).post('/save_attach_type', attachType).then(function (data) {
        if (data.text() === 'Тип сохранен') {
            webix.message({
                text: 'Сохранено',
                type: 'success'
            });

            $$('attachmentTypeTableId').clearAll();
            $$('attachmentTypeTableId').load('attachment_type_list');
        }})
}

function addAttachType() {
    webix.prompt({
        title: 'Создание нового типа документа',
        text: 'Введите название типа',
        ok: 'Сохранить',
        cancel: 'Отмена',
        input: {
            required: true,
            placeholder: 'Название типа',
        },
        width: 350,
    }).then(function (result) {
        attachType = {
            'name': result,
        }
        saveAttachType(attachType);
    })
}

function editAttachType(selectedRow) {
    let id = selectedRow.id;
    let attachType = $$('attachmentTypeTableId').getItem(id);
    webix.prompt({
        title: 'Редактирование типа документа',
        text: 'Введите новое название типа',
        ok: 'Сохранить',
        cancel: 'Отмена',
        input: {
            required: true,
            value: attachType.name,
        },
        width: 350,
    }).then(function (result) {
        attachType.name = result;
        saveAttachType(attachType);
    })
}

function deleteAttachType(selectedRow) {
    let id = selectedRow.id;
    let attachType = $$('attachmentTypeTableId').getItem(id);
    webix.ajax().headers({
        'Content-Type': 'application/json'
    }).post('/delete_attach_type', attachType).then(function (data) {
        if (data.text() === 'Тип удален') {
            webix.message({
                text: 'Удалено',
                type: 'success'
            });

            $$('attachmentTypeTableId').clearAll();
            $$('attachmentTypeTableId').load('attachment_type_list');
        }})
}

function getTopPanelAttachTypeList() {
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
        data: topPanelAttachTypeListData,
        onClick: {
            'addAttachType': function () {
                addAttachType();
            },
            'editAttachType': function () {
                var selectedRows = $$('attachmentTypeTableId').getSelectedId(true);
                if (selectedRows.length == 1) {
                    editAttachType(selectedRows[0]);
                }
            },
            'deleteAttachType': function () {
                var selectedRows = $$('attachmentTypeTableId').getSelectedId(true);
                if (selectedRows.length > 0) {
                    deleteAttachType(selectedRows[0]);
                }
            },
        }
    }
}

export const attachmentTypeList = {
    view: 'scrollview',
    id: 'attachmentTypeListId',
    scroll: 'xy',
    body: {
        type: 'space',
        rows: [
            {
                autowidth: true,
                autoheight: true,
                rows: [
                    getTopPanelAttachTypeList(),
                    attachmentTypeTable,
                ]
            }]
    }
}