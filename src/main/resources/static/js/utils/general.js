webix.i18n.setLocale("ru-RU");

const xml_format =  webix.Date.strToDate("%Y-%m-%d %H:%i:%s.S");
const dateFormat = webix.Date.dateToStr("%d.%m.%Y")

webix.editors.editdate = webix.extend({
    // what editor will return
    getValue:function(){
        var value = this.getInputNode(this.node).value;
        console.log("Get "+value);
        if (!!value && typeof value == "string")
            return new Date(value);
        else return value
    },
    // what will be set to editor
    setValue:function(value){
        console.log("Set "+value)
        if (!!value && typeof value == "string")
            this.getInputNode(this.node).value = new Date(value);
        else
            this.getInputNode(this.node).value = value;
    },
    render:function(){
        var icon = "<span class='webix_icon wxi wxi-calendar' style='position:absolute; cursor:pointer; top:8px; right:5px;'></span>";
        var node = webix.html.create("div", {
            "class":"webix_dt_editor"
        }, "<input type='text'>"+icon);

        node.childNodes[1].onclick = function(){
            var master = webix.UIManager.getFocus();
            var editor = master.getEditor();

            master.editStop(false);
            var config = master.getColumnConfig(editor.column);
            config.editor = "date";
            master.editCell(editor.row, editor.column);
            config.editor = "editdate";
        }
        return node;
    }
}, webix.editors.text);

function view_section(title) {
    return {
        view: 'template',
        type: 'section',
        template: title
    }
}

function convertToDate(stringDate) {
    stringDate = stringDate.replace("T", " ");
    let date = xml_format(stringDate);
    return date;
}

function getImageClassByExtension(extensionName) {
    let imgClass;
    switch (extensionName) {
        case '.zip':
            imgClass = 'zip_background';
            break;
        case '.7z':
            imgClass = 'zip_background';
            break;
        case '.doc':
            imgClass = 'doc_background';
            break;
        case '.docx':
            imgClass = 'doc_background';
            break;
        case '.xlsx':
            imgClass = 'xls_background';
            break;
        case '.xls':
            imgClass = 'xls_background';
            break;
        case '.pdf':
            imgClass = 'pdf_background';
            break;
    }
    return imgClass;
}