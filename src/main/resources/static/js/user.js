webix.i18n.setLocale("ru-RU");

webix.ready(function() {
    let layout = webix.ui({
        cols: [
            {
                width: 220,
                id: 'menuRow',
                rows: [
                    {
                        view: 'label',
                        css: {
                            'background-color': '#565B67 !important',
                            'color': '#FFFFFF'
                        },
                        height: 46,
                        label: `<img height='36px' width='36px' style="padding: 0 0 2px 2px" src = \"favicon.ico\"><span style="color: white; font-size: 16px; font-family: Roboto, sans-serif;">${APPLICATION_NAME}</span>`,
                    },
                    {
                        view: 'menu',
                        id: 'menu',
                        css: 'my_menubar',
                        layout: 'y',
                        data: [
                            // Позже переименовать id
                            { id: "ID8", icon: "fas fa-globe", value: 'Статистика и отчетность' },
                        ],
                        type: {
                            height: 44
                        },
                    }
                ]
            },
            {
                rows: [
                    {
                        view: 'toolbar',
                        autoheight: true,
                        elements: [
                            {
                                view: 'label',
                                id: 'labelLK',
                                align: 'left',
                                css: {"padding-left": "5px"},
                                label: 'Личный кабинет',
                            },
                            {
                                cols: [
                                    {
                                        view: 'label',
                                        label: ' (<a href="/logout" title="Выйти">' + USER_NAME + '</a>)',
                                        align: 'right'
                                    }
                                ]
                            }
                        ]
                    },
                ]
            }
        ]
    })
    webix.event(window, "resize", function(event){
        layout.define("width",document.body.clientWidth);
        layout.define("height",window.innerHeight);
        layout.resize();
    });
})
