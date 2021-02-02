function btnSaveRkkActions() {
    if ($$('rkkAttachmentForm').validate()) {
        let params = $$('rkkAttachmentForm').getValues();

        webix.ajax().headers({
            'Content-Type': 'application/json'
        }).post('/save_rkk_attachment',
            params).then(function (data) {
            if (data.text() === 'Вложение сохранено') {
                webix.message({text: data.text(), type: 'success'});

                // webix.ui(rkkList, $$('rkkFormId'));
                // $$('rkk_table').clearAll();
                // $$('rkk_table').load('doc_rkks');
            }
        });
    }
}

var rkkFileUploader = {
    id: 'upload',
    view: 'uploader',
    css: 'transparentBtnStyle',
    type: "icon",
    icon: "mdi mdi-download",
    label: 'Загрузить',
    upload: 'upload_rkk_files',
    required: true,
    autosend: true,
    accept: 'application/pdf, application/msword',
    formData: function () {
        let params = $$('rkkForm').getValues();
        return {
            docRkkId: params.id
        };
    },
    multiple: true,
    on: {
        onFileUpload: (response) => {
            if (response.cause == "Ошибка сохранения" || response.cause == "Отсутствует организация") {
                webix.message(response.cause, "error")
            } else if (response.cause == "Вы уже загружали этот файл") {
                webix.message(response.cause + ": " + response.sname, "error")
                console.log(response.cause)
            } else {
                webix.message(response.cause + ": " + response.sname, "success")
                $$('attachmentDatatableId').load(function ()
                {
                    let params = {
                        "docRkkId" : $$('rkkForm').getValues().id
                    }
                    return webix.ajax().get('doc_rkk_files', params);
                });
            }
        }
    }
}

var btnRkkAttachmentPanel = {
    cols: [
        {},
        {
            view: 'button',
            align: 'right',
            maxWidth: 200,
            css: 'webix_primary',
            value: 'Сохранить',
            click: function () {
                btnSaveRkkActions();
            }
        },
        {
            view: 'button',
            align: 'right',
            maxWidth: 200,
            css: 'webix_secondary',
            value: 'Отмена',
            click: function () {
                webix.ui(rkkList, $$('rkkFormId'));
            }
        }
    ]
}

var rkkAttachmentElements = [
    { view: 'text', label: '№ документа', labelPosition: 'top', name: 'numberAttachment',},
    { view: 'datepicker', label: 'Дата подписания', labelPosition: 'top', name: 'signingDate', timepicker: false,},
    { view: 'combo', label: 'Участник', labelPosition: 'top', name: 'participantId', options: 'participant_attachment_list'},
    { view: 'combo', label: 'Группа', labelPosition: 'top', name: 'groupId', options: 'group_attachement_list'},
    { view: 'combo', label: 'Тип документа', labelPosition: 'top', name: 'typeId', options: 'type_attachement_list'},
    { view: 'text', label: 'Файл', labelPosition: 'top', name: 'originalFileName',},
    { view: 'text', label: 'Кол-во стр.', labelPosition: 'top', name: 'pageCount',},
    rkkFileUploader
]

const rkkAttachmentForm = {
    view: 'scrollview',
    scroll: 'y',
    autowidth: true,
    autoheight: true,
    body: {
        type: 'space',
        rows: [
            {
                view: 'form',
                id: 'rkkAttachmentForm',
                elements: [
                    rkkAttachmentElements,
                    {},
                    btnRkkAttachmentPanel,
                    ]
            },
        ]
    }
}