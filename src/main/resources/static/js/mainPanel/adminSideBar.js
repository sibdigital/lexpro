// OPERATION_READ = "READ_";
// OPERATION_WRITE = "WRITE_"; //create + update
// OPERATION_DELETE = "DELETE_"

import {referenceList} from "../references/reference_list.js";
import {rkkList} from "../rkk/rkk_list.js"
import {rkkArchivedList} from "../rkk/rkk_list.js"
import {rkkDeletedList} from "../rkk/rkk_list.js"

function checkPrivilege(privilegeName) {
    return PRIVILEGES.includes(privilegeName);
}

function getView(id) {
    let view;
    switch (id) {
        case 'Rkk': {
            view = rkkList;
            break;
        }
        case 'ArchivedRkk': {
            view = rkkArchivedList;
            break;
        }
        case 'DeletedRkk': {
            view = rkkDeletedList;
            break;
        }
        case 'References': {
            view = referenceList;
            break;
        }
    }
    return view;
}

function getSideBarData() {
    var data = [{ id: 'References', icon: 'fas fa-book', value: 'Справочники',}];
    if (checkPrivilege('READ_RKK')) {
        data.push({ id: 'Rkk', icon: 'fas fa-file-alt',   value: 'РКК', });
        data.push({ id: 'ArchivedRkk', icon: 'fas fa-folder', value: 'Архив РКК'})
        data.push({ id: 'DeletedRkk', icon: 'fas fa-trash-alt', value: 'Удаленные РКК'});
    }

    return data;
}

export const sideBar = {
    view: 'sidebar',
    id: 'sidebar',
    css: 'webix_dark',
    // data: sideBarData,
    data: getSideBarData(),
    on: {
        onAfterSelect: function(id) {
            let view = getView(id);
            webix.ui({
                id: 'content',
                rows: [
                    view
                ]
            }, $$('content'))
        }
    }
}
