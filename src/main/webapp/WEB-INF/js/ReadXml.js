/**
 * Created by Administrator on 2017/10/13.
 */
/**
 * 实现js解析xml文件的功能，包含2种方式：
 * 1：读取本地xml文件或xml格式字符串，解析xml内容
 * 2：ajax获取服务器xml文件，采用文件或字符串的方式解析xml
 *
 * 测试示例数据：
 * <?xml version="1.0" encoding="utf-8" ?>
 * <DongFang>
 * <Company>
 * <cNname>1</cNname>
 * <cIP>1</cIP>
 * </Company>
 * <Company>
 * <cNname>2</cNname>
 * <cIP>2</cIP>
 * </Company>
 * <Company>
 * <cNname>3</cNname>
 * <cIP>3</cIP>
 * </Company>
 * <Company>
 * <cNname>4</cNname>
 * <cIP>4</cIP>
 * </Company>
 * <Company>
 * <cNname>5</cNname>
 * <cIP>5</cIP>
 * </Company>
 * <Company>
 * <cNname>6</cNname>
 * <cIP>6</cIP>
 * </Company>
 * </DongFang>
 *
 * @param xmlFilePath
 * @returns {*}
 */
//xmlFilePath xml文件路径
function loadXmlFile(xmlFilePath) {
    var xmlDoc=null;
    //判断浏览器的类型
    if (!!window.ActiveXObject || "ActiveXObject" in window) {//支持IE浏览器
        var xmlDomVersions = ['MSXML.2.DOMDocument.6.0','MSXML.2.DOMDocument.3.0','Microsoft.XMLDOM'];
        for(var i=0;i<xmlDomVersions.length;i++){
            try{
                xmlDoc = new ActiveXObject(xmlDomVersions[i]);
                break;
            }catch(e){
            }
        }
    }
    else if (document.implementation && document.implementation.createDocument) {//Firefox, Mozilla, Opera, etc
        try{
            /* document.implementation.createDocument('','',null); 方法的三个参数说明
             * 第一个参数是包含文档所使用的命名空间URI的字符串；
             * 第二个参数是包含文档根元素名称的字符串；
             * 第三个参数是要创建的文档类型（也称为doctype）
             */
            xmlDoc = document.implementation.createDocument('','',null);
        }catch(e){
        }
    }
    else{
        return null;
    }

    if(xmlDoc!=null){
        try{
            xmlDoc.async = false;
            xmlDoc.load(xmlFilePath);//chrome没有load方法
        }catch(e){
            //针对Chrome,不过只能通过http访问,通过file协议访问会报错
            var xmlhttp = new window.XMLHttpRequest();
            xmlhttp.open("GET", xmlFilePath, false);
            xmlhttp.send(null);
            if(xmlhttp.readyState == 4){
                xmlDoc = xmlhttp.responseXML.documentElement;
            }
        }
    }

    return xmlDoc;
}
//读取xml 字符串
function loadXmlString(xmlString) {
    var xmlDoc = null;
    //判断浏览器的类型
    //支持IE浏览器
    if (!!window.ActiveXObject || "ActiveXObject" in window) {   //window.DOMParser 判断是否是非ie浏览器
        var xmlDomVersions = ['MSXML.2.DOMDocument.6.0', 'MSXML.2.DOMDocument.3.0', 'Microsoft.XMLDOM'];
        for (var i = 0; i < xmlDomVersions.length; i++) {
            try {
                xmlDoc = new ActiveXObject(xmlDomVersions[i]);
                xmlDoc.async = false;
                xmlDoc.loadXML(xmlString); //loadXML方法载入xml字符串
                break;
            } catch (e) {
            }
        }
    }
    else if (window.DOMParser && document.implementation && document.implementation.createDocument) {//支持Mozilla浏览器
        try {
            /* DOMParser 对象解析 XML 文本并返回一个 XML Document 对象。
             * 要使用 DOMParser，使用不带参数的构造函数来实例化它，然后调用其 parseFromString() 方法
             * parseFromString(text, contentType) 参数text:要解析的 XML 标记 参数contentType文本的内容类型
             * 可能是 "text/xml" 、"application/xml" 或 "application/xhtml+xml" 中的一个。注意，不支持 "text/html"。
             */
            domParser = new DOMParser();
            xmlDoc = domParser.parseFromString(xmlString, 'text/xml');
        } catch (e) {
        }
    }
    else {
        return null;
    }

    return xmlDoc;
}

function getLocalXmlPath() {
    var host = window.location.host;
    var path = window.location.pathname;
    var webinfIndex = path.lastIndexOf("WEB-INF");
    var xmlPath = path.substr(1, webinfIndex - 1).concat("WEB-INF/resource/testReadXml.xml");

    return xmlPath = window.location.protocol + "//" + host + "/" + xmlPath;
}

//读取本地xml文件，根据ajax设置的返回数据类型不同，以文件或字符串的形式解析返回的xml
function readXMLFile() {
    var xmlPath = getLocalXmlPath();
    //读取xmlfile
    var xmlDoc = loadXmlFile(xmlPath);
    var elements = xmlDoc.getElementsByTagName("Company");
    for (var i = 0; i < elements.length; i++) {
        var name = elements[i].getElementsByTagName("cNname")[0].firstChild.nodeValue;
        var ip = elements[i].getElementsByTagName("cIP")[0].firstChild.nodeValue;

        alert("name=" + name + " ,ip=" + ip);

        break;
    }
}
function readXMLString() {
    //读取xml string
    var xmlstring = '<?xml version="1.0" encoding="utf-8" ?><DongFang><Company><cNname>1</cNname><cIP>1</cIP></Company><Company><cNname>2</cNname><cIP>2</cIP></Company><Company><cNname>3</cNname><cIP>3</cIP></Company><Company><cNname>4</cNname><cIP>4</cIP></Company><Company><cNname>5</cNname><cIP>5</cIP></Company><Company><cNname>6</cNname><cIP>6</cIP></Company></DongFang>';
    var xmlstringDoc = loadXmlString(xmlstring);
    var elements_2 = xmlstringDoc.getElementsByTagName("Company");
    for (var i = 0; i < elements_2.length; i++) {
        var name = elements_2[i].getElementsByTagName("cNname")[0].firstChild.nodeValue;
        var ip = elements_2[i].getElementsByTagName("cIP")[0].firstChild.nodeValue;

        alert("name=" + name + " ,ip=" + ip);

        break;
    }
}

//ajax获取取本地xml文件，根据ajax设置的返回数据类型不同，以文件或字符串的形式解析返回的xml
function ajaxLoadXmlFile() {
    var xmlPath = getLocalXmlPath();
    $.ajax({
        url: "../resource/testReadXml.xml",
        dataType: "xml",
        type: "get",
        success: function (responseData) {

            $(responseData).find("Company").each(function(i){
                var name = $(this).children("cNname").text();
                var ip = $(this).children("cIP").text();
                alert("name=" + name + " ,ip=" + ip);
                return false;
            });
        }
    });
}
//ajax获取取本地xml文件，根据ajax设置的返回数据类型不同，以文件或字符串的形式解析返回的xml
function ajaxLoadXmlString() {
    var xmlPath = getLocalXmlPath();
    $.ajax({
        url: xmlPath,
        dataType:"text",
        type:"get",
        success: function (responseData) {
            var xmlDoc = loadXmlString(responseData);
            var elements_2 = xmlDoc.getElementsByTagName("Company");
            for (var i = 0; i < elements_2.length; i++) {
                var name = elements_2[i].getElementsByTagName("cNname")[0].firstChild.nodeValue;
                var ip = elements_2[i].getElementsByTagName("cIP")[0].firstChild.nodeValue;

                alert("name=" + name + " ,ip=" + ip);

                break;
            }
        }
    });
}