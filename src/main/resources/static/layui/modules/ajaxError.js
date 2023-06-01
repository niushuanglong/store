/** demo.js **/
layui.define(['layer', 'laypage'], function(exports){ //此处 mod1 为你的任意扩展模块
           var err=function () {debugger
               //设置ajax请求完成后运行的函数,
               $.ajaxSetup( {

                   //设置ajax请求结束后的执行动作
                   complete :function(XMLHttpRequest, textStatus) {
                       // 通过XMLHttpRequest取得响应头，sessionstatus
                       var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                       if (sessionstatus == "TIMEOUT") {
                           var win = window;
                           while (win != win.top){
                               win = win.top;
                           }
                           win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");
                       }
                   },
                   error:function (e){
                       var  message=JSON.parse(e.responseText).message;
                       alert(message)
                       if (message===("登录已过期，请重新登录!")){
                           top.location="login.html";
                       }
                       console.log(JSON.parse(e.responseText).message)
                   }
               });
           }
    exports('ajaxError',err);
});