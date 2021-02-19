import {rkkMailingTab} from "./rkk_special_mark_tab/rkk_mailing_tab.js";
import {rkkVisaTab} from "./rkk_special_mark_tab/rkk_visa_tab.js";
import {btnRkkPanel} from "./generalBtns.js";

export const specialMarksTab = {
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
                rkkMailingTab,
                rkkVisaTab,
            ]
        },
        btnRkkPanel
    ]
}