webix.i18n.setLocale("ru-RU");

const xml_format =  webix.Date.strToDate("%Y-%m-%d %H:%i:%s.S");
const dateFormat = webix.Date.dateToStr("%d.%m.%Y")

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