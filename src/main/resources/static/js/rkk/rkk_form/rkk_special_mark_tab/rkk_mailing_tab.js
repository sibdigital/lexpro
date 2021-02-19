var mailingRkkTableColumns = [
    { id: 'organizationId', header: 'Кому',       editor: "richselect", adjust: true, sort: 'string', options: 'participant_attachment_list_richselect', fillspace: 3 },
    { id: 'date',         header: 'Дата',       editor: "editdate",   adjust: true, sort: 'date', format: dateFormat, fillspace: 1 },
    { id: 'note',         header: 'Примечание', editor:"text",        adjust: true, sort: 'string', fillspace: 3},
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

export const rkkMailingTab = {
    view: 'form', id: 'rkkMailing',
    rows: [
        mailingRkkTable,
        mailingRkkBtns,
    ]
}