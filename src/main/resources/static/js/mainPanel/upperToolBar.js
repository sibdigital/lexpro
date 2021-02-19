export const upperToolbar = {
    view: 'toolbar',
    autoheight: true,
    rows: [
        {
            responsive: 't1',
            css: 'webix_dark',
            cols: [
                {
                    view: 'label',
                    width: 40,
                    template: "<img height='35px' width='35px' src = \"favicon.ico\">"
                },
                {
                    id: 'labelLK',
                    view: 'label',
                    minWidth: 400,
                    label: '<span style="font-size: 1.0rem">Личный кабинет администратора</span>',
                },
                {},
                {
                    view: 'label',
                    label: ' (<a href="/logout" title="Выйти">' + USER_NAME + '</a>)',
                    align: 'right'
                }
            ]
        }
    ]
}
