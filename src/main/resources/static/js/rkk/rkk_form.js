webix.i18n.setLocale("ru-RU");

function btnSaveActions() {
    if ($$('rkkForm').validate()) {
        let params = $$('rkkForm').getValues();

        webix.ajax().headers({
            'Content-Type': 'application/json'
        }).post('/save_rkk',
            params).then(function (data) {
                if (data.text() === 'РКК сохранена') {
                    webix.message({text: data.text(), type: 'success'});

                    webix.ui(rkkList, $$('rkkFormId'));
                    $$('rkk_table').clearAll();
                    $$('rkk_table').load('doc_rkks');
                }
            });
    }
}

var rkkFormTabbar = {
    id: 'rkkTabs',
    view: 'tabbar',
    multiview: true,
    borderless:true,
    value: 'mainRkkTabId',
    options: [
        {
            id: 'mainRkkTabId',
            value: 'Основное',
        },
        {
            id: 'attachmentTabId',
            value: 'Вложения',
        },
    ]
}

var mainRkkTab = {
    id: 'mainRkkTabId',
    rows: [
        {
          cols: [
              {
                  id: 'npaType',
                  name: 'npaType',
                  view: 'richselect',
                  label: 'Тип НПА',
                  labelPosition: 'top',
                  required: true,
                  options: 'npa_type_list',
              },
              {
                  id: 'status',
                  name: 'status',
                  view: 'richselect',
                  label: 'Состояние',
                  labelPosition: 'top',
                  required: true,
                  options: 'status_list',
              },
          ]
        },
        {id: 'rkkNumber', name: 'rkkNumber', view: 'text', label: '№ РКК', labelPosition: 'top', required: true,},
        {
            id: 'npaName',
            name: 'npaName',
            view: "textarea",
            label: "Наименование НПА",
            labelPosition: 'top',
            required: true,
            height: 150,
        },
        {
            cols: [
                {
                    id: 'registrationDate',
                    name: 'registrationDate',
                    view: 'datepicker',
                    label: 'Дата регистрации',
                    labelPosition: 'top',
                    timepicker: false,
                    required: true,
                },
                {
                    id: 'introductionDate',
                    name: 'introductionDate',
                    view: 'datepicker',
                    label: 'Дата внесения',
                    labelPosition: 'top',
                    timepicker: false,
                    required: true,
                },
            ],
        },
        {
            cols: [
                {
                    id: 'responsibleOrganization',
                    name: 'responsibleOrganization',
                    view: 'richselect',
                    label: 'Ответственный комитет',
                    labelPosition: 'top',
                    required: true,
                    options: 'responsible_organization_list',
                },
                {
                    id: 'responsibleEmployee',
                    name: 'responsibleEmployee',
                    view: 'richselect',
                    label: 'Ответственное лицо',
                    labelPosition: 'top',
                    required: true,
                    options: 'responsible_employee_list',
                },
            ]
        },
        {
            cols: [
                {
                    id: 'includedInAgenta',
                    name: 'includedInAgenta',
                    view: 'checkbox',
                    label: 'Включен в повестку',
                    labelPosition: 'top',
                },
                {
                    id: 'deadline',
                    name: 'deadline',
                    view: 'datepicker',
                    label: 'Контрольный срок',
                    labelPosition: 'top',
                    timepicker: false,
                },
            ]
        },
        {
            cols: [
                {
                    id: 'sessionNumber',
                    name: 'sessionNumber',
                    view: 'text',
                    label: 'Номер сессии',
                    labelPosition: 'top',
                },
                {
                    id: 'sessionDate',
                    name: 'sessionDate',
                    view: 'datepicker',
                    label: 'Дата сессии',
                    labelPosition: 'top',
                    timepicker: false,
                },
            ]
        },
    ]
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
                $$('attachmentDataviewId').load(function ()
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

var docFileColumn = {
    id: 'docFileColumnId',
    header: 'Файл',
    template: function (obj) {
        let docImg;
        if (obj.fileExtension == ".zip") {
            docImg = "zip.png"
        } else {
            docImg = "pdf.png"
        }
        return '<div class="overall"><div><img scr="images/pdf.png"></div></div>';
    }
}

var attachmentDatatable = {
    id: 'attachmentDatatableId',
    view: 'datatable',
    autoConfig: true,
    select: 'row',
    // url: function () {
    //     let params = {
    //         "docRkkId" : 1
    //     }
    //     return webix.ajax().get('doc_rkk_files', params);
    // },
    columns: [docFileColumn]

}

var attachmentTab = {
    id: 'attachmentTabId',
    rows:[
        rkkFileUploader,
        attachmentDatatable
    ]
}

var rkkTabview = {
    id: 'rkkTabviewId',
    animate:false,
    cells: [
        mainRkkTab,
        attachmentTab
    ]
}

var btnPanel = {
    cols: [
        {},
        {
            view: 'button',
            align: 'right',
            maxWidth: 200,
            css: 'webix_primary',
            value: 'Сохранить',
            click: function () {
                btnSaveActions();
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
                    rkkFormTabbar,
                    rkkTabview,
                    {},
                    btnPanel,
                ]
            },
        ]
    }
}