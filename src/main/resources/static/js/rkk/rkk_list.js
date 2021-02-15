webix.i18n.setLocale("ru-RU");

function changeRkkDatesFormat(obj) {
    if ( obj.registrationDate != null && !(obj.registrationDate instanceof Date)) {
        obj.registrationDate = convertToDate(obj.registrationDate);
    }

    if ( obj.introductionDate != null &&  !(obj.introductionDate instanceof Date)) {
        obj.introductionDate = convertToDate(obj.introductionDate);
    }

    if ( obj.deadline != null && !(obj.deadline instanceof Date)) {
        obj.deadline = convertToDate(obj.deadline);
    }

    if ( obj.includedInAgenda != null && !(obj.includedInAgenda instanceof Date)) {
        obj.includedInAgenda = convertToDate(obj.includedInAgenda);
    }

    if ( obj.headSignature != null && !(obj.headSignature instanceof Date)) {
        obj.headSignature = convertToDate(obj.headSignature);
    }

    if ( obj.publicationDate != null && !(obj.publicationDate instanceof Date)) {
        obj.publicationDate = convertToDate(obj.publicationDate);
    }
}

function getSessionNumber(session) {
    let number = '';
    if (session) {
        number = session.number;
    }

    return number;
}

function getSessionDate(session) {
    let date = '';
    if (session) {
        date = convertToDate(session.date);
    }

    return date;
}

function getDataFromRkkTable(id) {
    let data = $$('rkkTableId').getItem(id);
    if (data.status) {
        data.status.value = data.status.name;
    }

    if (data.npaType) {
        data.npaType.value = data.npaType.name;
    }

    if (data.responsibleOrganization) {
        data.responsibleOrganization.value = data.responsibleOrganization.name;
    }

    if (data.responsibleEmployee) {
        data.responsibleEmployee.value = data.responsibleEmployee.name;
    }

    if (data.lawSubject) {
        data.lawSubject.value = data.lawSubject.name;
    }

    if (data.speaker) {
        data.speaker.value = data.speaker.name;
    }

    if (data.session) {
        data.session.value = data.session.number;
        data.sessionDate   = data.session && convertToDate(data.session.date);
    }

    return data;
}

function openRkkTab(id) {
    let data = getDataFromRkkTable(id);

    webix.ui(rkkForm, $$('rkkListId'));
    $$('rkkForm').parse(data);

    let params = {'docRkkId': data.id};
    let attachmentData = webix.ajax().get('doc_rkk_files', params);
    $$('attachmentDatatableId').parse(attachmentData);

    let mailingData = webix.ajax().get('rkk_mailing_dtos', params);
    $$('mailingRkkTableId').parse(mailingData);

    let visaData = webix.ajax().get('rkk_visa_dtos', params);
    $$('visaRkkTableId').parse(visaData);
}

var includedInAgentaColumn = {
    id: 'includedInAgenda',
    header: {text: "Включен в <br/> повестку",
             height: 40,
             css: "multiline"},
    adjust: true,
    format: dateFormat,
    // template: function (obj, type, value) {
    //     return (value ? "<span class='webix_icon fas fa-check'></span>" : "")
    // },
    // css: 'styleIcon'
}

// 3.4.3 Личный кабинет. Перечислены столбцы
var rkkTableColumns = [
    { id: 'rkkNumber',          header: '№ РКК',                                    adjust: true, sort: 'string'},
    { id: 'npaName',            header: 'Наименование НПА',   maxWidth:250,         adjust: true, sort: 'string', },
    { id: 'registrationDate',   header: 'Дата регистрации',   format: dateFormat,   adjust: true, sort: 'date'},
    { id: 'introductionDate',   header: 'Дата внесения',      format: dateFormat,   adjust: true, sort: 'date'},
    { id: 'npaType',            header: 'Тип НПА',   template: function (obj) {
                                                        if (obj.npaType) { return obj.npaType.name; } else { return '';}}, adjust: true,},
    { id: 'lawSubject',         header:
                                {text: "Субъект права <br/> законодательной инициативы",
                                height: 40,
                                css: "multiline"}, template: function (obj) {
                                    if (obj.lawSubject) { return obj.lawSubject.name; } else { return '';}}, adjust: true,},
    { id: 'responsibleOrganization', header: 'Ответственный комитет', template: function (obj) {
                                    if (obj.responsibleOrganization) { return obj.responsibleOrganization.name;} else { return '';}} , adjust: true,},
    { id: 'responsibleEmployee',     header: 'Ответственное лицо',    template: function (obj) {
                                    if (obj.responsibleEmployee) { return obj.responsibleEmployee.name;} else { return '';}}  ,     adjust: true,},
    { id: 'deadline',           header: 'Контрольный срок',   format: dateFormat,   adjust: true, sort: 'date'},
    { id: 'sessionNumber',      header: 'Номер сессии', template: function (obj) {return getSessionNumber(obj.session);},
                                                            adjust: true, sort: 'string'},
    { id: 'sessionDate',        header: 'Дата сессии',  template: function (obj) {return getSessionDate(obj.session);},
                                                            format: dateFormat,   adjust: true},
    includedInAgentaColumn,
    { id: 'status',             header: 'Состояние',   template: function (obj) {
                                    if (obj.status) { return obj.status.name;}  else { return '';}} ,   adjust: true},
]

var pager = {
    id: 'Pager',
    view: 'pager',
    height: 38,
    size: 25,
    group: 5,
    template: '{common.first()}{common.prev()}{common.pages()}{common.next()}{common.last()}'
}

var btnAddRkk = {
    id: 'btnAddRkk',
    view: 'button',
    css: 'webix_primary',
    value: 'Добавить',
    maxWidth: 300,
    click: function () {
        webix.ui(rkkForm, $$('rkkListId'));
    }
}

var bottomPanel = {
    cols: [
        pager,
        {},
        btnAddRkk,
    ]
}

function getRkkTable(tableId, url) {
    return {
        id: tableId,
        view: 'datatable',
        select: 'row',
        resizeColumn:true,
        readonly: true,
        columns: rkkTableColumns,
        pager: 'Pager',
        datafetch: 25,
        scheme: {
            $init: function (obj) {
                changeRkkDatesFormat(obj)
            },
            $update: function (obj) {
                changeRkkDatesFormat(obj)
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
                openRkkTab(id);
            }
        },
        url: url,
    }
}

var rkkTable = {
    id: 'rkk_table',
    view: 'datatable',
    select: 'row',
    resizeColumn:true,
    readonly: true,
    columns: rkkTableColumns,
    pager: 'Pager',
    datafetch: 25,
    scheme: {
        $init: function (obj) {
            changeRkkDatesFormat(obj)
        },
        $update: function (obj) {
            changeRkkDatesFormat(obj)
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
            openRkkTab(id);
        }
    },
    url: 'doc_rkks',
}

const rkkList = {
    view: 'scrollview',
    id: 'rkkListId',
    scroll: 'xy',
    body: {
        type: 'space',
        rows: [
            {
                autowidth: true,
                autoheight: true,
                rows: [
                    getRkkTable('rkkTableId', 'doc_rkks'),
                    bottomPanel]
            }]
    }
}

const rkkDeletedList = {
    view: 'scrollview',
    id: 'rkkDeletedListId',
    scroll: 'xy',
    body: {
        type: 'space',
        rows: [
            {
                autowidth: true,
                autoheight: true,
                rows: [
                    getRkkTable('rkkTableId', 'deleted_doc_rkks'),
                    bottomPanel]
            }]
    }
}