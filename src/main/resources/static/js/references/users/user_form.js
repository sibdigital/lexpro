var userRoleTable = {
    id: 'userRoleTableId',
    view: 'datatable',
    columns: [
        {
            id: 'checkbox',
            name: 'checkbox',
            header: '',
            template: '{common.checkbox()}',
            editor: 'checkbox',
            value: true
        },
        {id: 'name', header: 'Роль', adjust: true, fillspace: true},
    ],
    on: {
        onCheck: function (row, column, state) {
            if (column == 'checkbox' && state == 0) {
                this.updateItem(row, {deactivationDate: new Date()});
            } else if (column == 'checkbox' && state == 1) {
                this.updateItem(row, {deactivationDate: null});
            }
        },
        onAfterLoad: function () {
            this.eachRow(function (row) {
                var record = this.getItem(row);
                if (record.id == 1) {
                    this.addCellCss(row, 'checkbox',
                        webix.html.createCss({
                            "pointer-events": "none",
                            "opacity": "0.5"
                        }));
                }
            })
        },
        onItemDblClick: function (id) {
            let data = this.getItem(id);
            $$('mailingForm').setValues({
                index: data.id,
                name: data.name,
                description: data.description
            });
            $$('mailingForm').show();
        }
    },
}

const rkkForm = {
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
                ]
            },
        ]
    }
}