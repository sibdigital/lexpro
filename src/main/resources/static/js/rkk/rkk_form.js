webix.i18n.setLocale("ru-RU");

var rkkFormTabbar = {
    view: 'tabbar',
    id: 'rkkTabs',
    multiview: true,
    borderless:true,
    value: 'mainRkkTab',
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
        {id: 'rkkNumber', name: 'rkkNumber', view: 'text', label: '№ РКК', labelPosition: 'top',},
        {
            id: 'registrationDate',
            name: 'registrationDate',
            view: 'datepicker',
            label: 'Дата регистрации',
            labelPosition: 'top',
            timepicker: false,
        },
        {
            id: 'introductionDate',
            name: 'introductionDate',
            view: 'datepicker',
            label: 'Дата внесения',
            labelPosition: 'top',
            timepicker: false,
        },
        //{Состояние}
    ]
}

var attachmentTab = {
    id: 'attachmentTabId',
    rows:[
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

const rkkForm = {
    view: 'scrollview',
    id: 'rkkFormId',
    scroll: 'xy',
    body: {
        type: 'space',
        rows: [
            {
                id: 'rkkForm',
                view: 'form',
                elements: [
                    rkkFormTabbar,
                    rkkTabview,]
            },
        ]
    }
}