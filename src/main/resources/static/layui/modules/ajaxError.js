/** demo.js **/
layui.define(['layer', 'laypage'], function(exports){ //此处 mod1 为你的任意扩展模块
           var err=function () {
               $.ajaxSetup({//这个相当于全局ajax配置 就是Ajax不设置什么属性写在这里会默认调用 比如没写contentType ajaxSetup里有contentType
                   contentType: "application/json;charset=utf-8",
                   success:function(result){
                       alert("进入success")
                   },error:function () {
                       alert("进入error")
                   },
                   error(xhr,status,error){
                       alert("进入错误")
                   },
                   callback(){
                       alert("callback")
                   },
                   //请求完成时运行的函数（在请求成功或失败之后均调用，即在 success 和 error 函数之后）
                   complete(xhr,status){
                       alert("进入complete")
                   }
               })
           }
    exports('ajaxError',err);
});