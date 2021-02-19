webix.i18n.setLocale("ru-RU");
import {mainRkkTab} from "./rkk_main_tab.js";
import {specialMarksTab} from "./rkk_special_mark_tab.js";
import {attachmentTab} from "./rkk_file_uploader_tab.js";
import {changeHistoryTab} from "./rkk_change_history_tab.js"

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

export const rkkForm = {
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