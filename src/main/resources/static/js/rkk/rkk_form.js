webix.i18n.setLocale("ru-RU");

function changeRkkFilesDatesFormat(obj) {
    if (obj.signingDate != null && !(obj.signingDate instanceof Date)) {
        obj.signingDate = convertToDate(obj.signingDate);
    }
}

function btnSaveRkkActions() {
    if ($$('rkkForm').validate()) {
        let params = $$('rkkForm').getValues();
        params.attachments = $$('attachmentDatatableId').serialize();

        webix.ajax().headers({
            'Content-Type': 'application/json'
        }).post('/save_rkk',
            params).then(function (data) {
                if (data.text() === 'РКК сохранена') {
                    webix.message({text: data.text(), type: 'success'});

                    webix.ui(rkkList, $$('rkkFormId'));
                    $$('rkk_table').clearAll();
                    $$('rkk_table').load('doc_rkks');
                }
            });
    }
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
                    required: true,
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
                    required: true,
                },
                {
                    id: 'introductionDate',
                    name: 'introductionDate',
                    view: 'datepicker',
                    label: 'Дата внесения',
                    labelPosition: 'top',
                    timepicker: false,
                    required: true,
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
                    required: true,
                    options: 'law_subject_list',
                },
                {
                    id: 'speaker',
                    name: 'speaker',
                    view: 'richselect',
                    label: 'Докладчик',
                    labelPosition: 'top',
                    required: true,
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
                    required: true,
                    options: 'responsible_organization_list',
                },
                {
                    id: 'responsibleEmployee',
                    name: 'responsibleEmployee',
                    view: 'richselect',
                    label: 'Ответственное лицо',
                    labelPosition: 'top',
                    required: true,
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
    ]
}

//////////////////////////////////// FILE UPLOADER TAB/////////////////////////////////////////////////

var docFileColumn = {
    id: 'docFileColumnId',
    header: 'Файл',
    template: function (obj) {
        let imageClass = getImageClassByExtension(obj.file.fileExtension);
        return '<div class="'+ imageClass + '"></div>' + obj.file.originalFileName;
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
    { id: 'pageCount',        header: 'Количество страниц',template: '#file.pageCount#',        adjust: true, sort: 'string'},
    docFileColumn,
]

var attachmentTable = {
    id: 'attachmentDatatableId',
    view: 'datatable',
    autoConfig: true,
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
    id: 'tnAddAttachment',
    view: 'button',
    css: 'webix_primary',
    value: 'Добавить',
    maxWidth: 300,
    click: function () {
        // if (checkIsRkkNewWithoutId()) {
        //     webix.confirm({
        //         title:"Сохранить новую РКК",
        //         type:"confirm-warning",
        //         ok:"Да", cancel:"Нет",
        //         text:"Для прикрепления вложений в новую РКК"
        //     }).then(function(){
        //         webix.ajax()
        //             .headers({'Content-type': 'application/json'})
        //             .post('delete_file', JSON.stringify(param))
        //             .then(function (data) {
        //                 if (data !== null) {
        //                     $$("docs_grid").remove($$("docs_grid").getSelectedId());
        //                     webix.message("Файл удалён", 'success');
        //                 } else {
        //                     webix.message("Не удалось удалить файл", 'error');
        //                 }
        //             });
        //     })
        //
        // }

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

var topAttachmentPanel = {
    cols: [
        btnAddAttachmentRkk,
    ]
}

var attachmentTab = {
    id: 'attachmentTabId',
    rows: [
        topAttachmentPanel,
        attachmentTable
    ]
}

///////////////////////////////////////// SPECIAL MARK TAB ////////////////////////////////////////////
var specialMarksTab = {
    id: 'specialMarksTabId',
    rows: []
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

var btnRkkPanel = {
    cols: [
        {},
        {
            view: 'button',
            align: 'right',
            maxWidth: 200,
            css: 'webix_primary',
            value: 'Сохранить',
            click: function () {
                btnSaveRkkActions();
            }
        },
        {
            view: 'button',
            align: 'right',
            maxWidth: 200,
            css: 'webix_secondary',
            value: 'Отмена',
            click: function () {
                webix.ui(rkkList, $$('rkkFormId'));
            }
        }
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
                    {},
                    btnRkkPanel,
                ]
            },
        ]
    }
}