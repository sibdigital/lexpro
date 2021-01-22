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

    if ( obj.sessionDate != null && !(obj.sessionDate instanceof Date)) {
        obj.sessionDate = convertToDate(obj.sessionDate);
    }
}

function openRkkTab(id) {

    let data = $$('rkk_table').getItem(id);
    data.status.value = data.status.name;
    data.npaType.value = data.npaType.name;
    data.responsibleOrganization.value = data.responsibleOrganization.name;
    data.responsibleEmployee.value = data.responsibleEmployee.name;

    webix.ui(rkkForm, $$('rkkListId'));
    $$('rkkForm').parse(data);
}

var includedInAgentaColumn = {
    id: 'includedInAgenta',
    header: {text: "Включен в <br/> повестку",
             height: 40,
             css: "multiline"},
    adjust: true,
    template: function (obj, type, value) {
        return (value ? "<span class='webix_icon fas fa-check'></span>" : "")
    },
    css: 'styleIcon'
}

var rkkTableColumns = [
    { id: 'rkkNumber',          header: '№ РКК',                                  adjust: true, sort: 'string'},
    { id: 'npaName',            header: 'Наименование НПА',   fillspace: true,    adjust: true, sort: 'string', },
    { id: 'registrationDate',   header: 'Дата регистрации',   format: dateFormat, adjust: true, sort: 'date'},
    { id: 'introductionDate',   header: 'Дата внесения',      format: dateFormat, adjust: true, sort: 'date'},
    { id: 'npaType',            header: 'Тип НПА',   template: '#npaType.name#',  adjust: true},
    { id: 'deadline',           header: 'Контрольный срок',   format: dateFormat, adjust: true, sort: 'date'},
    { id: 'sessionNumber',      header: 'Номер сессии',                           adjust: true, sort: 'string'},
    { id: 'sessionDate',        header: 'Дата сессии',        format: dateFormat, adjust: true},
    includedInAgentaColumn,
    { id: 'status',             header: 'Состояние',   template: '#status.name#', adjust: true},
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