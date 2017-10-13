/**
 * Created by Administrator on 2017/10/13.
 */
function getRealPath() {
    //获取网页当前网址
    var currentPath = window.document.location.href;
    //获取主机目录之后的位置
    var pathName = window.document.location.pathname;

    //获取带"/"的项目名
    var index = currentPath.indexOf(pathName);
    var projectName = pathName.substring(0, pathName.substring(1).indexOf("/") + 1)
    //获取主机名
    var host = currentPath.substring(0, index);

    var port = window.document.location.port;

    return "currentPath=" + currentPath + " "
        + "pathName=" + pathName + " "
        + "projectName=" + projectName + " "
        + "host=" + host + " "
        + "port=" + port;
}

function getRealPath2() {
    //获取网页当前路径
    var currentPath = window.location.href;
    //获取端口号之后的路径，带"/"
    var pathName = window.location.pathname;
    //获取带"/"的项目名
    var index = currentPath.indexOf(pathName);
    var projectName = pathName.substr(0, pathName.substring(1).indexOf("/") + 1);
    //获取主机名
    var host = window.location.host;
    //获取端口号
    var port = window.location.port;

    return "currentPath=" + currentPath + " "
        + "pathName=" + pathName + " "
        + "projectName=" + projectName + " "
        + "host=" + host + " "
        + "port=" + port;
}