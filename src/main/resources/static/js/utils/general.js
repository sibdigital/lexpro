webix.i18n.setLocale("ru-RU");

const xml_format =  webix.Date.strToDate("%Y-%m-%d %H:%i:%s.S");
const dateFormat = webix.Date.dateToStr("%d.%m.%Y")

function convertToDate(stringDate) {
    stringDate = stringDate.replace("T", " ");
    let date = xml_format(stringDate);
    return date;
}
