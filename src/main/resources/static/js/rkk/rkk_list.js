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

function openRkkTab(id) {

    let data = $$('rkk_table').getItem(id);
    data.status.value = data.status.name;
    data.npaType.value = data.npaType.name;
    data.responsibleOrganization.value = data.responsibleOrganization.name;
    data.responsibleEmployee.value = data.responsibleEmployee.name;
    data.lawSubject.value = data.lawSubject.name;
    data.speaker.value = data.speaker.name;
    if (data.session) {
        data.session.value = data.session.number;
        data.sessionDate   = data.session && convertToDate(data.session.date);
    }


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
    { id: 'npaType',            header: 'Тип НПА',   template: '#npaType.name#',    adjust: true,},
    { id: 'lawSubject',         header:
                                {text: "Субъект права <br/> законодательной инициативы",
                                height: 40,
                                css: "multiline"}, template: '#lawSubject.name#', adjust: true,},
    { id: 'responsibleOrganization', header: 'Ответственный комитет', template: '#responsibleOrganization.name#', adjust: true,},
    { id: 'responsibleEmployee',     header: 'Ответственное лицо',    template: '#responsibleEmployee.name#',     adjust: true,},
    { id: 'deadline',           header: 'Контрольный срок',   format: dateFormat,   adjust: true, sort: 'date'},
    { id: 'sessionNumber',      header: 'Номер сессии', template: function (obj) {return getSessionNumber(obj.session);},
                                                            adjust: true, sort: 'string'},
    { id: 'sessionDate',        header: 'Дата сессии',  template: function (obj) {return getSessionDate(obj.session);},
                                                            format: dateFormat,   adjust: true},
    includedInAgentaColumn,
    { id: 'status',             header: 'Состояние',   template: '#status.name#',   adjust: true},
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
                    rkkTable,
                    bottomPanel]
            }]
    }
}