webix.i18n.setLocale("ru-RU");
import {rkkForm} from "./rkk_form/rkk_form.js";

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

function getAttachmentDataByRkkId(id) {
    let params = {'docRkkId': id};
    let xhr = webix.ajax().sync().get('doc_rkk_files', params);
    var data = JSON.parse(xhr.responseText);

    for (var k in data) {
        var row = data[k];
        if (row.group) {
            row.group.value = row.group.name;
        }

        if (row.type) {
            row.type.value = row.type.name;
        }

        if (row.participant) {
            row.participant.value = row.participant.name;
        }
    }

    return data;
}

function openRkkTab(id) {
    let data = getDataFromRkkTable(id);

    webix.ui(rkkForm, $$('rkkListId'));
    $$('rkkForm').parse(data);

    let attachmentData = getAttachmentDataByRkkId(data.id);
    // let attachmentData = webix.ajax().get('doc_rkk_files', params);
    $$('attachmentDatatableId').parse(attachmentData);

    let params = {'docRkkId': data.id};
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

function addRkk() {
    webix.ui(rkkForm, $$('rkkListId'));
}

function archiveRkk(selectedRows) {
    webix.confirm('Вы действительно хотите заархивировать РКК?')
        .then(
            function () {
                selectedRows.forEach(element => {
                    let item = $$('rkkTableId').getItem(element.id);
                    let params = {'id': item.id};
                    webix.ajax().headers({
                        'Content-Type': 'application/json'
                    }).post('/archive_rkk', params).then(function (data) {
                        if (data.text() === 'РКК сохранена') {
                            webix.message({
                                text: 'Заархивировано',
                                type: 'success'
                            });

                            $$('rkkTableId').clearAll();
                            $$('rkkTableId').load('doc_rkks');
                        }})
                })
            })
}

function deleteRkk(selectedRows) {
    webix.confirm('Вы действительно хотите удалить РКК?')
        .then(
            function () {
                selectedRows.forEach(element => {
                    let item = $$('rkkTableId').getItem(element.id);
                    let params = {'id': item.id};
                    webix.ajax().headers({
                        'Content-Type': 'application/json'
                    }).post('/delete_rkk', params).then(function (data) {
                        if (data.text() === 'РКК сохранена') {
                            webix.message({
                                text: 'Удалено',
                                type: 'success'
                            });
                            $$('rkkTableId').clearAll();
                            $$('rkkTableId').load('doc_rkks');
                        }})
                })
            })
}

function restoreRkk(selectedRows) {
    webix.confirm('Вы действительно хотите восстановить РКК?')
        .then(
            function () {
                selectedRows.forEach(element => {
                    let item = $$('rkkTableId').getItem(element.id);
                    let params = {'id': item.id};
                    webix.ajax().headers({
                        'Content-Type': 'application/json'
                    }).post('/restore_rkk', params).then(function (data) {
                        if (data.text() === 'РКК сохранена') {
                            webix.message({
                                text: 'Восстановлено',
                                type: 'success'
                            });

                            $$('rkkTableId').clearAll();
                            $$('rkkTableId').load('deleted_doc_rkks');
                        }})
                })
            })
}

function rearchiveRkk(selectedRows) {
    webix.confirm('Вы действительно хотите разархивировать РКК?')
        .then(
            function () {
                selectedRows.forEach(element => {
                    let item = $$('rkkTableId').getItem(element.id);
                    let params = {'id': item.id};
                    webix.ajax().headers({
                        'Content-Type': 'application/json'
                    }).post('/rearchive_rkk', params).then(function (data) {
                        if (data.text() === 'РКК сохранена') {
                            webix.message({
                                text: 'Разархивировано',
                                type: 'success'
                            });
                            $$('rkkTableId').clearAll();
                            $$('rkkTableId').load('archived_doc_rkks');
                        }})
                })
            })
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
        // btnAddRkk,
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

function getTopPanelData(rkkType) {
    let data = [];
    if (rkkType == 'Deleted') {
        data.push({id: 1, value: 'Восстановить', icon: 'trash-restore-alt', class: 'restoreRkk', });
    } else if (rkkType == 'Archived') {
        data.push({ id: 1, value: 'Разархивировать', icon: 'folder-open', class: 'rearchiveRkk', css: 'styleIcon'});
    } else if (rkkType == 'Active') {
        data.push({ id: 1, value: 'Добавить', icon: 'plus-square', class: 'addRkk'});
        data.push({ id: 2, value: 'Редактировать', icon: 'edit',   class: 'editRkk'});
        data.push({ id: 3, value: 'Заархивировать', icon: 'folder',class: 'archiveRkk'});
        data.push({ id: 4, value: 'Удалить', icon: 'trash-alt',    class: 'deleteRkk'});
    }

    return data;
}

function getTopPanel(rkkType) {
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
        data: getTopPanelData(rkkType),
        onClick: {
            'addRkk': function () { webix.ui(rkkForm, $$('rkkListId'));},
            'editRkk': function () {
                var selectedRows = $$('rkkTableId').getSelectedId(true);
                if (selectedRows.length == 1) {
                    openRkkTab(selectedRows[0].id);
                }
            },
            'archiveRkk': function () {
                var selectedRows = $$('rkkTableId').getSelectedId(true);
                if (selectedRows.length > 0) {
                    archiveRkk(selectedRows);
                }
            },
            'deleteRkk': function () {
                var selectedRows = $$('rkkTableId').getSelectedId(true);
                if (selectedRows.length > 0) {
                    deleteRkk(selectedRows);
                }
            },
            'rearchiveRkk': function () {
                var selectedRows = $$('rkkTableId').getSelectedId(true);
                if (selectedRows.length > 0) {
                    rearchiveRkk(selectedRows);
                }
            },
            'restoreRkk': function () {
                var selectedRows = $$('rkkTableId').getSelectedId(true);
                if (selectedRows.length > 0) {
                    restoreRkk(selectedRows);
                }
            },
        }
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

export const rkkList = {
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
                    getTopPanel('Active'),
                    getRkkTable('rkkTableId', 'doc_rkks'),
                    bottomPanel
                ]
            }]
    }
}

export const rkkDeletedList = {
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
                    getTopPanel('Deleted'),
                    getRkkTable('rkkTableId', 'deleted_doc_rkks'),
                    bottomPanel]
            }]
    }
}

export const rkkArchivedList = {
    view: 'scrollview',
    id: 'rkkArchivedListId',
    scroll: 'xy',
    body: {
        type: 'space',
        rows: [
            {
                autowidth: true,
                autoheight: true,
                rows: [
                    getTopPanel('Archived'),
                    getRkkTable('rkkTableId', 'archived_doc_rkks'),
                    bottomPanel]
            }]
    }
}
