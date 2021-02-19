webix.i18n.setLocale("ru-RU");

import {upperToolbar} from "./mainPanel/upperToolBar.js";
import {sideBar} from "./mainPanel/adminSideBar.js";

webix.ready(() => {
    let layout = webix.ui({
        rows: [
            upperToolbar,
            {
                cols: [
                    sideBar,
                    {
                        id: 'content'
                    }
                ],
            }
        ]
    })

    webix.event(window, "resize", function (event) {
        layout.define("width",document.body.clientWidth);
        layout.define("height",window.innerHeight);
        layout.resize();
    });
})