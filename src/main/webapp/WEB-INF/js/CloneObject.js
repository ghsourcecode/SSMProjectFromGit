/**
 * Created by Administrator on 2017/10/20.
 */
/**
 * var a = "hello world";
 * var b = [];
 * var c = function(){};
 *
 * console.log( object.prototype.toString.call( a ) );
 * console.log( object.prototype.toString.call( b ) );
 * console.log( object.prototype.toString.call( c ) );

 * //结果
 * [object String];
 * [object Array];
 * [object Function];
 * @param obj
 * @returns {*}
 */
function clone(obj) {
    var copy;
    switch (typeof obj) {
        case "undefined":
            break;
        case "number":
            copy = obj - 0;
            break;
        case "string":
            copy = obj + "";
            break;
        case "boolean":
            copy = obj;
            break;
        case "object":  //object分为两种情况 对象（Object）和数组（Array）
            if (obj === null) {
                copy = null;
            } else {
                if (obj.prototype.toString.call(obj).slice(8, -1) === "Array") {
                    copy = [];
                    for (var i = 0 ; i < obj.length ; i++) {
                        copy.push(clone(obj[i]));
                    }
                } else {
                    copy = {};
                    for (var j in obj) {
                        copy[j] = clone(obj[j]);
                    }
                }
            }
            break;
        default:
            copy = obj;
            break;
    }
    return copy;
}