import {attachmentGroupList} from "./attachment_groups/attachment_group_list.js";
import {attachmentTypeList} from "./attachment_types/attachment_type_list.js";
import {organizationList} from "./organizations/organization_list.js";
import {userList} from "./users/user_list.js";

function getReferenceView(id) {
    let view;
    switch (id) {
        case 'Users': {
            view = userList;
            break;
        }
        case 'RolesAndPrivileges': {
            // view = referenceList;
            break;
        }
        case 'Organizations': {
            view = organizationList;
            break;
        }
        case 'TypeAttachments': {
            view = attachmentTypeList;
            break;
        }
        case 'GroupAttachments': {
            view = attachmentGroupList;
            break;
        }
    }
    return view;
}

function openReference(id) {
    let data = $$('reference_table').getItem(id);
    let view = getReferenceView(data.referenceId);

    webix.ui(view, $$('referenceListId'));
}

var reference_set = [
    { referenceId: 'Users',              referenceName: 'Пользователи', },
    { referenceId: 'RolesAndPrivileges', referenceName: 'Роли и права', },
    { referenceId: 'Organizations',      referenceName: 'Организационная структура',},
    { referenceId: 'TypeAttachments',    referenceName: 'Типы документов (вложения)',},
    { referenceId: 'GroupAttachments',   referenceName: 'Группы вложений',},
]

var referenceTable = {
    id: 'reference_table',
    view: 'datatable',
    select: 'row',
    resizeColumn:true,
    readonly: true,
    columns: [
        {id: 'referenceId', hidden: true},
        {id: 'referenceName', header: 'Наименование', adjust: true, fillspace: true, sort: 'string'}
    ],
    data: reference_set,
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
            openReference(id);
        }
    },
}

export const referenceList = {
    view: 'scrollview',
    id: 'referenceListId',
    scroll: 'xy',
    body: {
        type: 'space',
        rows: [
            {
                autowidth: true,
                autoheight: true,
                rows: [
                    referenceTable,
                ]
            }]
    }
}