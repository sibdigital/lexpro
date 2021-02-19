import {btnRkkPanel} from "./generalBtns.js";

export const mainRkkTab = {
    id: 'passportTabId',
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
                    options: 'npa_type_list_richselect',
                },
                {
                    id: 'status',
                    name: 'status',
                    view: 'richselect',
                    label: 'Состояние',
                    labelPosition: 'top',
                    required: true,
                    options: 'status_list_richselect',
                },
            ]
        },
        {id: 'rkkNumber', name: 'rkkNumber', view: 'text', label: '№ РКК', labelPosition: 'top', required: true,},
        {
            cols: [{
                id: 'npaName',
                name: 'npaName',
                view: "textarea",
                label: "Наименование НПА",
                labelPosition: 'top',
                required: true,
                height: 150,
            },
                {
                    id: 'legislativeBasis',
                    name: 'legislativeBasis',
                    view: 'textarea',
                    label: 'Законодательная основа',
                    labelPosition: 'top',
                    height: 150,
                },]
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
                },
                {
                    id: 'introductionDate',
                    name: 'introductionDate',
                    view: 'datepicker',
                    label: 'Дата внесения',
                    labelPosition: 'top',
                    timepicker: false,
                },
            ],
        },
        {
            cols: [
                {
                    id: 'lawSubject',
                    name: 'lawSubject',
                    view: 'richselect',
                    label: 'Субъект права законодательной инициативы',
                    labelPosition: 'top',
                    options: 'law_subject_list_richselect',
                },
                {
                    id: 'speaker',
                    name: 'speaker',
                    view: 'richselect',
                    label: 'Докладчик',
                    labelPosition: 'top',
                    options: 'responsible_employee_list_richselect',
                },
            ]
        },
        {
            cols: [
                {
                    id: 'responsibleOrganization',
                    name: 'responsibleOrganization',
                    view: 'richselect',
                    label: 'Ответственный комитет',
                    labelPosition: 'top',
                    options: 'responsible_organization_list_richselect',
                },
                {
                    id: 'responsibleEmployee',
                    name: 'responsibleEmployee',
                    view: 'richselect',
                    label: 'Ответственное лицо',
                    labelPosition: 'top',
                    options: 'responsible_employee_list_richselect',
                },
            ]
        },
        {
            cols: [
                {
                    id: 'includedInAgenda',
                    name: 'includedInAgenda',
                    view: 'datepicker',
                    label: 'Включен в повестку',
                    labelPosition: 'top',
                    timepicker: false,
                },
                {
                    id: 'agendaNumber',
                    name: 'agendaNumber',
                    view: 'text',
                    label: 'Номер по повестке',
                    labelPosition: 'top',
                },
            ]
        },
        {
            cols: [
                {
                    id: 'session',
                    name: 'session',
                    view: 'richselect',
                    label: 'Номер сессии',
                    labelPosition: 'top',
                    options: 'session_list_richselect',
                },
                {
                    id: 'sessionDate',
                    name: 'sessionDate',
                    // template: '#session.date#',
                    view: 'datepicker',
                    label: 'Дата сессии',
                    labelPosition: 'top',
                    timepicker: false,
                    readonly: true
                },
            ]
        },
        {
            cols: [
                {
                    id: 'deadline',
                    name: 'deadline',
                    view: 'datepicker',
                    label: 'Контрольный срок',
                    labelPosition: 'top',
                    timepicker: false,
                },
                {
                    id: 'readyForSession',
                    name: 'readyForSession',
                    view: 'checkbox',
                    label: 'Подготовлен к сессии',
                    labelPosition: 'top',
                },
            ]
        },
        {
            cols: [
                {
                    id: 'headSignature',
                    name: 'headSignature',
                    view: 'datepicker',
                    label: 'Подпись Главы / Председателя НХ',
                    labelPosition: 'top',
                    timepicker: false,
                },
                {
                    id: 'publicationDate',
                    name: 'publicationDate',
                    view: 'datepicker',
                    label: 'Опубликование',
                    labelPosition: 'top',
                    timepicker: false,
                },
            ]
        },
        {},
        btnRkkPanel
    ]
}
