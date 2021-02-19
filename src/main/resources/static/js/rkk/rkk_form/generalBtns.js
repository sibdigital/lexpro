function btnSaveAndCloseRkkActions() {
    if ($$('rkkForm').validate()) {
        let params = $$('rkkForm').getValues();
        params.mailings     = $$('mailingRkkTableId').serialize();
        params.visas        = $$('visaRkkTableId').serialize();

        webix.ajax().headers({
            'Content-Type': 'application/json'
        }).post('/save_rkk',
            params).then(function (data) {
            if (data.text() === 'РКК сохранена') {
                webix.message({text: data.text(), type: 'success'});
                webix.ui(rkkList, $$('rkkFormId'));
                $$('rkkTableId').clearAll();
                $$('rkkTableId').load('doc_rkks');
            }
        });
    }
}

export const btnRkkPanel = {
    cols: [
        {},
        {
            view: 'button',
            align: 'right',
            maxWidth: 150,
            css: 'webix_primary',
            value: 'Сохранить',
            click: function () {
                btnSaveAndCloseRkkActions();
            }
        },
        {
            view: 'button',
            align: 'right',
            maxWidth: 150,
            css: 'webix_secondary',
            value: 'Отмена',
            click: function () {
                webix.ui(rkkList, $$('rkkFormId'));
            }
        }
    ]
}