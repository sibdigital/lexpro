webix.i18n.setLocale("ru-RU");

function changeRkkFilesDatesFormat(obj) {
    if (obj.signingDate != null && !(obj.signingDate instanceof Date)) {
        obj.signingDate = convertToDate(obj.signingDate);
    }
}
function changeRkkSpecialMarkDatesFormat(obj) {
    if (obj.date != null && !(obj.date instanceof Date)) {
        obj.date = convertToDate(obj.date);
    }
}


function btnSaveAndCloseRkkActions() {
    if ($$('rkkForm').validate()) {
        let params = $$('rkkForm').getValues();
        params.mailings     = $$('mailingRkkTableId').serialize();
        params.visas        = $$('visaRkkTableId').serialize();

        webix.ajax().headers({
            'Content-Type': 'application/json'
        }).post('/save_rkk',
            params).then(function (data) {
            if (data.text() === 'РКК сохранена') {
                webix.message({text: data.text(), type: 'success'});
                webix.ui(rkkList, $$('rkkFormId'));
                $$('rkkTableId').clearAll();
                $$('rkkTableId').load('doc_rkks');
            }
        });
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

var btnRkkPanel = {
    cols: [
        {},
        // {
        //     view: 'button',
        //     align: 'right',
        //     maxWidth: 200,
        //     css: 'webix_primary',
        //     value: 'Сохранить и закрыть',
        //     click: function () {
        //         btnSaveAndCloseRkkActions();
        //     }
        // },
        {
            view: 'button',
            align: 'right',
            maxWidth: 150,
            css: 'webix_primary',
            value: 'Сохранить',
            click: function () {
                btnSaveAndCloseRkkActions();
            }
        },
        {
            view: 'button',
            align: 'right',
            maxWidth: 150,
            css: 'webix_secondary',
            value: 'Отмена',
            click: function () {
                webix.ui(rkkList, $$('rkkFormId'));
            }
        }
    ]
}

///////////////////////////////////////// MAIN TAB ////////////////////////////////////////////////////
var mainRkkTab = {
    id: 'passportTabId',
    rows: [
        {
          cols: [
              {
                  id: 'npaType',
                  name: 'npaType',
                  view: 'richselect',
                  label: 'Тип НПА',
                  labelPosition: 'top',
                  required: true,
                  options: 'npa_type_list',
              },
              {
                  id: 'status',
                  name: 'status',
                  view: 'richselect',
                  label: 'Состояние',
                  labelPosition: 'top',
                  required: true,
                  options: 'status_list',
              },
          ]
        },
        {id: 'rkkNumber', name: 'rkkNumber', view: 'text', label: '№ РКК', labelPosition: 'top', required: true,},
        {
            cols: [{
                id: 'npaName',
                name: 'npaName',
                view: "textarea",
                label: "Наименование НПА",
                labelPosition: 'top',
                required: true,
                height: 150,
            },
                {
                    id: 'legislativeBasis',
                    name: 'legislativeBasis',
                    view: 'textarea',
                    label: 'Законодательная основа',
                    labelPosition: 'top',
                    height: 150,
                },]
        },
        {
            cols: [
                {
                    id: 'registrationDate',
                    name: 'registrationDate',
                    view: 'datepicker',
                    label: 'Дата регистрации',
                    labelPosition: 'top',
                    timepicker: false,
                },
                {
                    id: 'introductionDate',
                    name: 'introductionDate',
                    view: 'datepicker',
                    label: 'Дата внесения',
                    labelPosition: 'top',
                    timepicker: false,
                },
            ],
        },
        {
            cols: [
                {
                    id: 'lawSubject',
                    name: 'lawSubject',
                    view: 'richselect',
                    label: 'Субъект права законодательной инициативы',
                    labelPosition: 'top',
                    options: 'law_subject_list',
                },
                {
                    id: 'speaker',
                    name: 'speaker',
                    view: 'richselect',
                    label: 'Докладчик',
                    labelPosition: 'top',
                    options: 'responsible_employee_list',
                },
            ]
        },
        {
            cols: [
                {
                    id: 'responsibleOrganization',
                    name: 'responsibleOrganization',
                    view: 'richselect',
                    label: 'Ответственный комитет',
                    labelPosition: 'top',
                    options: 'responsible_organization_list',
                },
                {
                    id: 'responsibleEmployee',
                    name: 'responsibleEmployee',
                    view: 'richselect',
                    label: 'Ответственное лицо',
                    labelPosition: 'top',
                    options: 'responsible_employee_list',
                },
            ]
        },
        {
            cols: [
                {
                    id: 'includedInAgenda',
                    name: 'includedInAgenda',
                    view: 'datepicker',
                    label: 'Включен в повестку',
                    labelPosition: 'top',
                    timepicker: false,
                },
                {
                    id: 'agendaNumber',
                    name: 'agendaNumber',
                    view: 'text',
                    label: 'Номер по повестке',
                    labelPosition: 'top',
                },
            ]
        },
        {
            cols: [
                {
                    id: 'session',
                    name: 'session',
                    view: 'richselect',
                    label: 'Номер сессии',
                    labelPosition: 'top',
                    options: 'session_list',
                },
                {
                    id: 'sessionDate',
                    name: 'sessionDate',
                    // template: '#session.date#',
                    view: 'datepicker',
                    label: 'Дата сессии',
                    labelPosition: 'top',
                    timepicker: false,
                    readonly: true
                },
            ]
        },
        {
            cols: [
                {
                    id: 'deadline',
                    name: 'deadline',
                    view: 'datepicker',
                    label: 'Контрольный срок',
                    labelPosition: 'top',
                    timepicker: false,
                },
                {
                    id: 'readyForSession',
                    name: 'readyForSession',
                    view: 'checkbox',
                    label: 'Подготовлен к сессии',
                    labelPosition: 'top',
                },
            ]
        },
        {
            cols: [
                {
                    id: 'headSignature',
                    name: 'headSignature',
                    view: 'datepicker',
                    label: 'Подпись Главы / Председателя НХ',
                    labelPosition: 'top',
                    timepicker: false,
                },
                {
                    id: 'publicationDate',
                    name: 'publicationDate',
                    view: 'datepicker',
                    label: 'Опубликование',
                    labelPosition: 'top',
                    timepicker: false,
                },
            ]
        },
        {},
        btnRkkPanel
    ]
}

//////////////////////////////////// FILE UPLOADER TAB/////////////////////////////////////////////////
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
    { id: 'group',            header: 'Группа',            template: '#group.name#',            adjust: true, sort: 'string'},
    { id: 'type',             header: 'Тип',               template: '#type.name#',             adjust: true, sort: 'string'},
    { id: 'participant',      header: 'Участник',          template: '#participant.name#',      adjust: true, sort: 'string'},
    { id: 'numberAttachment', header: 'Номер',                                                  adjust: true, sort: 'string'},
    { id: 'signingDate',      header: 'Дата подписания',   format: dateFormat,                  adjust: true, sort: 'date'},
    { id: 'pageCount',        header: 'Количество страниц',template: '#pageCount#',        adjust: true, sort: 'string'},
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

var attachmentTab = {
    id: 'attachmentTabId',
    rows: [
        topAttachmentPanel,
        attachmentTable,
    ]
}

///////////////////////////////////////// SPECIAL MARK TAB ////////////////////////////////////////////
var mailingRkkTableColumns = [
    { id: 'organizationId', header: 'Кому',       editor: "richselect", adjust: true, sort: 'string', options: 'participant_attachment_list', fillspace: 3 },
    { id: 'date',         header: 'Дата',       editor: "editdate",   adjust: true, sort: 'date', format: dateFormat, fillspace: 1 },
    { id: 'note',         header: 'Примечание', editor:"text",        adjust: true, sort: 'string', fillspace: true, fillspace: 3},
]

var mailingRkkTable = {
    id: 'mailingRkkTableId',
    view: 'datatable',
    select: 'row',
    resizeColumn:true,
    editable:true,
    editaction:"dblclick",
    columns: mailingRkkTableColumns,
}

var mailingRkkBtns = {
    view:"toolbar",
    cols:[
        { gravity:2 },
        {
            view: "button",
            align: 'left',
            maxWidth: 150,
            // css: 'webix_primary',
            value: 'Добавить',
            click: function () {$$('mailingRkkTableId').add({});}
        },
        {
            view: 'button',
            align: 'right',
            maxWidth: 150,
            // css: 'webix_primary',
            value: 'Удалить',
            click: function () { $$("mailingRkkTableId").remove($$("mailingRkkTableId").getSelectedId());}
        }
    ]
}

var visaRkkTableColumns = [
    { id: 'stageId', header: 'Стадия',     editor: "richselect", adjust: true, sort: 'string', options: 'stage_list', fillspace: 3},
    { id: 'date',  header: 'Дата',       editor: "editdate",   adjust: true, sort: 'date',   format: dateFormat, fillspace: 1},
    { id: 'note',  header: 'Примечание', editor: "text",       adjust: true, sort: 'string', fillspace: 3},
]

var visaRkkTable = {
    id: 'visaRkkTableId',
    view: 'datatable',
    select: 'row',
    resizeColumn:true,
    editable:true,
    editaction:"dblclick",
    columns: visaRkkTableColumns,
}

var visaRkkBtns = {
    cols:[
        { gravity:2 },
        {
            view: "button",
            align: 'left',
            maxWidth: 150,
            // css: 'webix_primary',
            value: 'Добавить',
            click: function () {$$('visaRkkTableId').add({});}
        },
        {
            view: 'button',
            align: 'right',
            maxWidth: 150,
            // css: 'webix_primary',
            value: 'Удалить',
            click: function () { $$("visaRkkTableId").remove($$("visaRkkTableId").getSelectedId());}
        }
    ]
}

var specialMarksTab = {
    id: 'specialMarksTabId',
    rows: [
        {
            view: "segmented", type: "bottom", multiview: true, options: [
                {value: "Рассылки", id: 'rkkMailing'},
                {value: "Виза",     id: 'rkkVisa'},],
        },
        {
            id: 'views',
            cells: [
                {
                    view: 'form', id: 'rkkMailing',
                    rows: [
                        mailingRkkTable,
                        mailingRkkBtns,
                    ]
                },
                {
                    view: 'form', id: 'rkkVisa',
                    rows: [
                        visaRkkTable,
                        visaRkkBtns,
                    ]
                },
            ]
        },
        btnRkkPanel
    ]
}

////////////////////////////////////// HISTORY OF CHANGES /////////////////////////////////////////////
var changeHistoryTab = {
    id: 'changeHistoryTabId',
    rows: []
}

///////////////////////////////////////////////////////////////////////////////////////////////////////
var rkkTabview = {
    id: 'rkkTabviewId',
    animate:false,
    cells: [
        mainRkkTab,
        specialMarksTab,
        attachmentTab,
        changeHistoryTab
    ]
}

var rkkFormTabbar = {
    id: 'rkkTabs',
    view: 'tabbar',
    multiview: true,
    borderless:true,
    value: 'passportTabId',
    options: [
        {
            id: 'passportTabId',
            value: 'Паспорт ЗИ',
        },
        {
            id: 'specialMarksTabId',
            value: 'Особые отметки',
        },
        {
            id: 'attachmentTabId',
            value: 'Вложения',
        },
        {
            id: 'changeHistoryTabId',
            value: 'Протокол правки',
        },
    ]
}

const rkkForm = {
    id: 'rkkFormId',
    view: 'scrollview',
    scroll: 'xy',
    body: {
        type: 'space',
        rows: [
            {
                id: 'rkkForm',
                view: 'form',
                elements: [
                    rkkFormTabbar,
                    rkkTabview,
                    // {},
                    // btnRkkPanel,
                ]
            },
        ]
    }
}