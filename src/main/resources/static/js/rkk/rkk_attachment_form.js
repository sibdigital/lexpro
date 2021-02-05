function btnSaveRkkAttachmentActions() {
    if ($$('rkkAttachmentForm').validate()) {
        let docRkkValues = $$('rkkForm').getValues();

        let params = $$('rkkAttachmentForm').getValues();
        params.docRkkId = docRkkValues.id;

        webix.ajax().headers({
            'Content-Type': 'application/json'
        }).post('/save_rkk_attachment',
            params).then(function (data) {
            if (data.text() === 'Вложение сохранено') {
                webix.message({text: data.text(), type: 'success'});

                $$('window').close();
                $$('attachmentDatatableId').clearAll();
                let attachmentData = webix.ajax().get('doc_rkk_attachments', params);
                $$('attachmentDatatableId').parse(attachmentData);
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
        if (params.id) {
            return {
                docRkkId: params.id
            };
        }
    },
    multiple: true,
    on: {
        onFileUpload: (response) => {
            if (response.cause == "Ошибка сохранения") {
                webix.message(response.cause, "error")
            } else {
                webix.message(response.cause + ": " + response.sname, "success")
                $$('rkkAttachmentForm').setValues({
                    originalFileName: response.sname,
                    pageCount: response.pageCount,
                    newFileId: response.fileId,
                }, true);
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
                btnSaveRkkAttachmentActions();
            }
        },
        {
            view: 'button',
            align: 'right',
            maxWidth: 200,
            css: 'webix_secondary',
            value: 'Отмена',
            click: function () {
                $$('window').close();
            }
        }
    ]
}

var rkkAttachmentElements = [
    { view: 'text', label: '№ документа', labelPosition: 'top', name: 'numberAttachment',},
    { view: 'datepicker', label: 'Дата подписания', labelPosition: 'top', name: 'signingDate', timepicker: false,},
    { view: 'richselect', label: 'Участник', labelPosition: 'top', name: 'participantId', options: 'participant_attachment_list'},
    { view: 'richselect', label: 'Группа', labelPosition: 'top', name: 'groupId', options: 'group_attachement_list'},
    { view: 'richselect', label: 'Тип документа', labelPosition: 'top', name: 'typeId', options: 'type_attachement_list'},
    { view: 'text', label: 'Файл', labelPosition: 'top', name: 'originalFileName', readonly:true},
    { view: 'text', label: 'Кол-во стр.', labelPosition: 'top', name: 'pageCount', readonly:true},
    rkkFileUploader,
    {},
    btnRkkAttachmentPanel,
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
                elements: rkkAttachmentElements,
            },
        ]
    }
}