var visaRkkTableColumns = [
    { id: 'stageId', header: 'Стадия',     editor: "richselect", adjust: true, sort: 'string', options: 'stage_list_richselect', fillspace: 3},
    { id: 'date',  header: 'Дата',       editor: "editdate",   adjust: true, sort: 'date',   format: dateFormat, fillspace: 1},
    { id: 'note',  header: 'Примечание', editor: "text",       adjust: true, sort: 'string', fillspace: 3},
]

var visaRkkTable = {
    id: 'visaRkkTableId',
    view: 'datatable',
    select: 'row',
    resizeColumn:true,
    editable:true,
    editaction:"dblclick",
    columns: visaRkkTableColumns,
}

var visaRkkBtns = {
    cols:[
        { gravity:2 },
        {
            view: "button",
            align: 'left',
            maxWidth: 150,
            // css: 'webix_primary',
            value: 'Добавить',
            click: function () {$$('visaRkkTableId').add({});}
        },
        {
            view: 'button',
            align: 'right',
            maxWidth: 150,
            // css: 'webix_primary',
            value: 'Удалить',
            click: function () { $$("visaRkkTableId").remove($$("visaRkkTableId").getSelectedId());}
        }
    ]
}

export const rkkVisaTab = {
    view: 'form', id: 'rkkVisa',
        rows: [
        visaRkkTable,
        visaRkkBtns,
    ]
}