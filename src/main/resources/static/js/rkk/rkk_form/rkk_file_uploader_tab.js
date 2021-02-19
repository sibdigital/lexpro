function addRkkAttach() {
    if (checkIsRkkNewWithoutId()) {
        webix.alert({
            title: "Нельзя добавить вложение в несохраненную РКК",
            text:"Для добавления вложений в новую РКК предварительно сохраните ее."
        })
    } else {
        let window = webix.ui({
            view: 'window',
            id: 'window',
            head: 'Новое вложение',
            close: true,
            width: 1000,
            height: 800,
            position: 'center',
            modal: true,
            body: rkkAttachmentForm,
            on: {
                'onShow': function () {
                }
            }
        });

        window.show();
    }
}

function deleteRkkAttachment() {
    if (!$$("attachmentDatatableId").getSelectedId()) {
        webix.message("Не выбрано вложение!", "error");
        return;
    }

    let params = $$('attachmentDatatableId').getSelectedId();
    $$('attachmentDatatableId').remove($$('attachmentDatatableId').getSelectedId());
    webix.ajax()
        .headers({'Content-type': 'application/json'})
        .post('delete_rkk_attachment', JSON.stringify(params))
        .then(function (data) {
            if (data !== null) {
                webix.message("Вложение удалено", 'success');
                $$('attachmentDatatableId').clearAll();

                let docRkkValues = $$('rkkForm').getValues();
                let rkkParams = {
                    'docRkkId': docRkkValues.id
                };
                let attachmentData = webix.ajax().get('doc_rkk_files', rkkParams);
                $$('attachmentDatatableId').parse(attachmentData);
            } else {
                webix.message("Не удалось удалить вложение", 'error');
            }
        });
}

function checkIsRkkNewWithoutId() {
    let isNew = true;
    let values = $$('rkkForm').getValues();
    if (values.id) {
        isNew = false;
    }

    return isNew;
}

function changeRkkFilesDatesFormat(obj) {
    if (obj.signingDate != null && !(obj.signingDate instanceof Date)) {
        obj.signingDate = convertToDate(obj.signingDate);
    }
}

var docFileColumn = {
    id: 'docFileColumnId',
    header: 'Файл',
    template: function (obj) {
        let imageClass = getImageClassByExtension(obj.fileExtension);
        return '<div class="'+ imageClass + '"></div>  ' + obj.originalFileName;
    },
    adjust: true,
    fillspace: true,
}

var attachmentTableColumns = [
    { id: 'group',            header: 'Группа',                     adjust: true, sort: 'string'},
    { id: 'type',             header: 'Тип',                         adjust: true, sort: 'string'},
    { id: 'participant',      header: 'Участник',              adjust: true, sort: 'string'},
    { id: 'numberAttachment', header: 'Номер',                                                  adjust: true, sort: 'string'},
    { id: 'signingDate',      header: 'Дата подписания',   format: dateFormat,                  adjust: true, sort: 'date'},
    { id: 'pageCount',        header: 'Количество страниц',      adjust: true, sort: 'string'},
    docFileColumn,
]

var attachmentTable = {
    id: 'attachmentDatatableId',
    view: 'datatable',
    select: 'row',
    resizeColumn:true,
    readonly: true,
    columns: attachmentTableColumns,
    scheme: {
        $init: function (obj) {
            changeRkkFilesDatesFormat(obj)
        },
        $update: function (obj) {
            changeRkkFilesDatesFormat(obj)
        }
    },
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
            let data = $$('attachmentDatatableId').getItem(id);
            if (data.participant) {
                data.participantId = data.participant.id;
            }

            if (data.group) {
                data.groupId = data.group.id;
            }

            if (data.type) {
                data.typeId = data.type.id;
            }

            if (data.file) {
                data.originalFileName = data.file.originalFileName;
                data.pageCount =  data.file.pageCount;
            }

            let window = webix.ui({
                view: 'window',
                id: 'window',
                head: 'Вложение (№ документа: ' + data.numberAttachment + ').',
                close: true,
                width: 1000,
                height: 800,
                position: 'center',
                modal: true,
                body: rkkAttachmentForm,
                on: {
                    'onShow': function () {
                    }
                }
            });

            $$('rkkAttachmentForm').parse(data);

            window.show();
        }
    },
}

var btnAddAttachmentRkk = {
    id: 'btnAddAttachment',
    view: 'button',
    type: 'iconTop',
    icon: 'fas fa-file-medical',
    label: 'Добавить',
    width: '100',
    align: 'left',
    click: function () {
        if (checkIsRkkNewWithoutId()) {
            webix.alert({
                title: "Нельзя добавить вложение в несохраненную РКК",
                text:"Для добавления вложений в новую РКК предварительно сохраните ее."
            })
        } else {
            let window = webix.ui({
                view: 'window',
                id: 'window',
                head: 'Новое вложение',
                close: true,
                width: 1000,
                height: 800,
                position: 'center',
                modal: true,
                body: rkkAttachmentForm,
                on: {
                    'onShow': function () {
                    }
                }
            });

            window.show();
        }
    }
}

var btnDeleteAttachmentRkk = {
    id: 'btnDeleteAttachment',
    view: 'button',
    type: 'iconTop',
    icon: 'fas fa-trash-alt',
    label: 'Удалить',
    width: '100',
    align: 'right',
    click: function () {
        webix.confirm('Вы действительно хотите удалить выбранную запись?')
            .then(
                function () {
                    deleteRkkAttachment();
                });
    }
}

var topAttachmentPanel = {
    height: 50,
    cols: [
        btnAddAttachmentRkk,
        {},
        btnDeleteAttachmentRkk
    ]
}

var topPanelRkkAttachListData = [
    { id: 1, value: 'Добавить', icon: 'plus-square', class: 'addRkkAttach'},
    // { id: 2, value: 'Редактировать', icon: 'edit',   class: 'editRkkAttach'},
    { id: 3, value: 'Удалить', icon: 'trash-alt',    class: 'deleteRkkAttach'},
]

function getTopPanelRkkAttachList() {
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
        data: topPanelRkkAttachListData,
        onClick: {
            'addRkkAttach': function () {
                addRkkAttach();
            },
            'editRkkAttach': function () {
                // var selectedRows = $$('attachmentGroupTableId').getSelectedId(true);
                // if (selectedRows.length == 1) {
                //     editRkkAttach(selectedRows[0]);
                // }
            },
            'deleteRkkAttach': function () {
                deleteRkkAttachment();
            },
        }
    }
}

export const attachmentTab = {
    id: 'attachmentTabId',
    rows: [
        // topAttachmentPanel,
        getTopPanelRkkAttachList(),
        attachmentTable,
    ]
}
