function btnSaveRkkAttachmentActions() {
    if ($$('rkkAttachmentForm').validate()) {
        if ($$('uploadlist').serialize().length > 0) {
            $$('rkkFileUploaderId').send(function (response) {
                if (response.cause == "Ошибка сохранения") {
                    webix.message(response.cause, "error")
                } else {
                    webix.message(response.cause + ": " + response.sname, "success");
                    $$('window').close();
                    $$('attachmentDatatableId').clearAll();

                    let docRkkValues = $$('rkkForm').getValues();
                    let params = {'docRkkId': docRkkValues.id};
                    let attachmentData = webix.ajax().get('doc_rkk_files', params);
                    $$('attachmentDatatableId').parse(attachmentData);
                }
            })
        } else {
            let docRkkValues = $$('rkkForm').getValues();
            let params = $$('rkkAttachmentForm').getValues();
            params.docRkkId = docRkkValues.id;
            params.userId   = USER.id;

            webix.ajax().headers({
                'Content-Type': 'application/json'
            }).post('/edit_rkk_file',
                params).then(function (data) {
                if (data.text() === 'Вложение сохранено') {
                    webix.message({text: data.text(), type: 'success'});

                    $$('window').close();
                    $$('attachmentDatatableId').clearAll();
                    let attachmentData = webix.ajax().get('doc_rkk_files', params);
                    $$('attachmentDatatableId').parse(attachmentData);
                }
            });
        }
    }
}


var rkkFileUploader = {
    id: 'rkkFileUploaderId',
    view: 'uploader',
    css: 'transparentBtnStyle',
    type: "icon",
    icon: "fas fa-paperclip",
    upload: 'save_rkk_file',
    width:30,
    required: true,
    autosend: false,
    link: 'uploadlist',
    accept: 'application/pdf, application/msword',
    formData: function () {
        let docRkkValues = $$('rkkForm').getValues();
        let params = $$('rkkAttachmentForm').getValues();
        params.docRkkId = docRkkValues.id;
        params.userId   = USER.id;
        params.signingDate = webix.i18n.parseFormatStr($$('signingDateId').getValue());

        return params;
    },
    multiple: false,
    on: {
        onAfterFileAdd: function () {
            $$('uploadlistLabelId').show();
        },
    }
}

var rkkFileDownloader = {
    id: 'upload',
    view: 'icon',
    css: 'transparentBtnStyle',
    // type: "icon",
    icon: "fas fa-download",
    width:30,
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
    { view: 'richselect', label: 'Участник', labelPosition: 'top', name: 'participantId', options: 'participant_attachment_list_richselect'},
    { cols: [
            { view: 'richselect', label: 'Группа', labelPosition: 'top', name: 'groupId', options: 'group_attachement_list_richselect'},
            { view: 'richselect', label: 'Тип документа', labelPosition: 'top', name: 'typeId', options: 'type_attachment_list_richselect'},
        ]
    },
    {
        cols: [
            { view: 'text', label: '№ документа', labelPosition: 'top', name: 'numberAttachment',},
            { view: 'datepicker', id: 'signingDateId', label: 'Дата подписания', labelPosition: 'top', name: 'signingDate', timepicker: false,},
            { view: 'text', label: 'Кол-во стр.', labelPosition: 'top', name: 'pageCount', readonly:true, maxWidth:100,},
        ]
    },
    {
        cols: [
            { view: 'text', label: 'Файл:', labelPosition: 'left', name: 'originalFileName', readonly:true,},
            rkkFileUploader,
        ]
    },
    {
        cols: [
            { view: 'label', id: 'uploadlistLabelId', label: "Новая версия файла:", hidden: true},
            { view: 'list', id: 'uploadlist', type: 'uploader', autoheight: true, borderless: true, adjust: true, fillspace: true, },
        ]
    },
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