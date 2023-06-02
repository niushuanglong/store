(function(){

})();

layui.use(['form', 'element'], function () {
    var form = layui.form;
    var e = layui.element;
    form.on('submit(lookuser)', function (data) {
        $.ajax({
            url: basePath + "/api/chat/queryUser" ,
            data: {username:data.field.username,userId:userId},
            contentType: "application/json;charset=UTF-8", //发送数据的格式
            type: "get",
            dataType: "json", //回调
            beforeSend: function () {
                layer.load(1, {
                    content: '查询中...',
                    success: function (layero) {
                        layero.find('.layui-layer-content').css({
                            'padding-top': '39px',
                            'width': '60px'
                        });
                    }
                });
            },
            complete: function () {
                layer.closeAll('loading');
            },
            success: function (data) {
                if (data.data==null) {
                    layer.msg(data.msg, {
                        time: 1500,
                        icon: 2,
                        offset: '350px'
                    });
                } else {
                    setUserInfo(data.data);
                }
            },
            error: function (err) {
                alert(err.msg)
                console.log("err:", err);
            }
        });
        return false;
    });

});

var normalEidt;
var layedit;
var  kflag =  false ;
layui.use(['form', 'layedit', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer;
        layedit = layui.layedit;

    //设置上传图片接口
    layedit.set({
        uploadImage: {
            url: basePath + '/api/chat/uploadImg' //接口url
            , type: 'POST' //默认post
            , size: 1024 * 5
        }
    });
    //创建一个好友聊天时的编辑器
    normalEidt = layedit.build('LAY_demo_editor', {
        tool: ['face' //删除线
            , 'image'
            , 'strong' //加粗
            , 'italic' //斜体
            , 'underline' //下划线
            , 'video'
        ],
        height: 120 //设置编辑器高度
    });
    //好友编辑器键盘按下事件
    layedit.monitor(normalEidt,"keypress",function(e) {
        if (e.keyCode == 13) {
            if ( !kflag ) {
                kflag =true;
                sendNormalMsg();
                layedit.setContent(normalEidt, "", false);
            }
            //只要按下回车，都失去焦点  解决一直按着，一直回车问题。
            e.target.blur();
        }
    });

    //好友编辑器键盘抬起事件
    layedit.monitor(normalEidt,"keyup",function(e) {
        if (e.keyCode == 13) {
            layedit.setContent(normalEidt, "", false);
            if(kflag)
            {   //获取焦点
                kflag = false;
                e.target.focus();
            }else{
                //失去焦点
                e.target.blur();
            }
        }
    });
    form.verify({
        content: function () {
            layedit.sync(normalEidt)
        }
    });
    form.render();
});

function isEmpty(obj) {
    if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
    } else {
        return false;
    }
}

/*控制鼠标移动到div显示或者隐藏div的滚动*/
function leftscroll(id) {
    if (id == 1) {
        document.getElementById("leftscroll").style.overflowY = "auto";
        document.getElementById("leftscroll").style.overflowX = "hidden";
        // document.getElementById("leftscroll_group").style.overflowY = "auto";
        // document.getElementById("leftscroll_group").style.overflowX = "hidden";
    } else if (id == 2) {
        document.getElementById("leftscroll").style.overflowY = "hidden";
        document.getElementById("leftscroll").style.overflowX = "hidden";
        // document.getElementById("leftscroll_group").style.overflowY = "hidden";
        // document.getElementById("leftscroll_group").style.overflowX = "hidden";
    } else if (id == 3) {
        document.getElementById("msgcontent").style.overflowY = "auto";
        document.getElementById("msgcontent").style.overflowX = "hidden";
        // document.getElementById("msgcontent_group").style.overflowY = "auto";
        // document.getElementById("msgcontent_group").style.overflowX = "hidden";
    } else if (id == 4) {
        document.getElementById("msgcontent").style.overflowY = "hidden";
        document.getElementById("msgcontent").style.overflowX = "hidden";
        // document.getElementById("msgcontent_group").style.overflowY = "hidden";
        // document.getElementById("msgcontent_group").style.overflowX = "hidden";
    }
}
var info = new Vue({
    el: '#userinfo',
    data() {
        return {
            userinfo: []
        }
    },
    mounted: function () {
        window.setUserInfo = this.setUserInfo;
    },
    methods: {
        /**添加好友查询用户信息*/
        setUserInfo: function (info) {
            var that = this;
            that.userinfo = info;
            $("#userinfo").show();
        },adduser:function (uid) {
            $.ajax({
                url: basePath + "/api/chat/adduser/" + userid+"/"+uid,
                data: "",
                contentType: "application/json;charset=UTF-8", //发送数据的格式
                type: "post",
                dataType: "json", //回调
                beforeSend: function () {
                    layer.load(1, {
                        content: '添加中...',
                        success: function (layero) {
                            layero.find('.layui-layer-content').css({
                                'padding-top': '39px',
                                'width': '60px'
                            });
                        }
                    });
                },
                complete: function () {
                    layer.closeAll('loading');
                },
                success: function (data) {
                    if (data.code != 100) {
                        layer.msg(data.msg, {
                            time: 1500,
                            icon: 2,
                            offset: '350px'
                        });
                    } else {
                        layer.msg(data.msg, {
                            time: 1000,
                            icon: 1,
                            offset: '350px'
                        }, function () {
                            getlistnickname();//刷新好友列表
                        });
                    }
                }
            });
        }
    }
})

var actuserid = null;
var vuechat = new Vue({
    el: '#vuechat',
    data() {
        return {
            listnickname: [],
            listmessage: [],
            userId: userid
        }
    },
    mounted: function () {
        this.getlistnickname();
        window.alertnote = this.alertnote;
        window.activeuser = this.activeuser;
        window.appendmsg = this.appendmsg;
        window.getlistnickname=this.getlistnickname;
    },
    methods: {
        /**点击预览图片*/
        openimg: function (id) {
            var imgs = document.getElementById(id).getElementsByTagName("img");
            var pho = "";
            for (var i = 0; i < imgs.length; i++) {
                var img = '<img src="' + $(imgs[i]).attr("src") + '" style="width:100%;">'
                layer.open({
                    type: 1,
                    title: false, //不显示标题
                    closeBtn: 0, //不显示关闭按钮
                    shade: 0.6,//遮罩透明度
                    shadeClose: true, //开启遮罩关闭
                    anim: 0,
                    content: img
                });
            }
        },
        /*新信息提示*/
        alertnote: function (msgtouid) {
            var that = this;
            for (var i = 0; i < that.listnickname.length; i++) {
                if (that.listnickname[i].userId === msgtouid) {
                    layer.msg(that.listnickname[i].username + '发来一条信息', {
                        time: 1500,
                        icon: 0,
                        offset: '50px'
                    });
                }
            }
        },
        /*添加聊天记录*/
        appendmsg(msgType, revive, send, text) {
            var that = this;
            that.listmessage.push({msgType: msgType, receiveUserId: revive, sendUserId: send, content: text});
            setTimeout(function () {
                document.getElementById("msg_end").scrollIntoView();
            }, 500)
        },
        /*设置点击左侧的列表的时候切换样式同时查找聊天记录*/
        selectStyle: function (item, acuserid) {
            this.$nextTick(function () {
                this.listnickname.forEach(function (item) {
                    Vue.set(item, 'active', false);
                });
                Vue.set(item, 'active', true);
            });
            this.getMessageList(item.fuserId);
            actuserid = acuserid;
        },
        /*获取左侧的好友聊天窗口*/
        getlistnickname: function () {
            var that = this;
            $.ajax({
                url:basePath+"/api/chat/findFriends/"+userid,
                data: "",
                contentType: "application/json;charset=UTF-8",
                type: "post",
                dataType: "json",
                success: function (data) {
                    that.listnickname = data.data;
                    $("#leftc").show();
                    $("#appLoading").hide();
                },
                error: function (err) {
                    alert("出现异常 getlistnickname")
                    console.log("err:", err);
                }
            });
        },
        /*获取好友聊天记录*/
        getMessageList: function (receiveUserId) {
            $("#waits").hide();
            $("#words").hide();
            $("#appLoading2").show();
            var that = this;
            $.ajax({
                type: 'post',
                url: basePath + '/api/chat/queryFriendsMsg/' + receiveUserId+"/"+userid,
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                success: function (msg) {
                    that.listmessage = msg.data;
                    $("#words").show();
                    $("#appLoading2").hide();
                },
                error: function (err) {
                    console.log("err:", err);
                }
            })
        }

    },
    watch:{
        //聊天记录渲染完毕后，将滚动条滚动到最下面
        listmessage:function(){
            setTimeout(function () {
                document.getElementById("msg_end").scrollIntoView(
                    {
                        behavior:"smooth"
                    }
                );
            }, 500)
        }
    }
});

//var randnumber=Math.ceil(Math.random()*1000)+1;
//创建一个+实例

var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    //连接WebSocket节点
    var userId=JSON.parse(localStorage.getItem("userInfo")).id;
    websocket = new WebSocket("ws://"+socketPath+"/websocket/" + userId);
} else {
    alert('Not support websocket')
}
//连接发生错误的回调方法
websocket.onerror = function () {
    debugger
    setMessageInnerHTML("error");
};
//连接成功建立的回调方法
websocket.onopen = function (event) {
    setMessageInnerHTML("open");
}
//接收到消息的回调方法
websocket.onmessage = function (event) {
    debugger
    setMessageInnerHTML(event.data);
}
//连接关闭的回调方法
websocket.onclose = function (e) {
    debugger
    console.log(e.code + ' ' + e.reason + ' ' + e.wasClean);
    setMessageInnerHTML("close");
}
//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    debugger
    websocket.close();
};

//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    var msgs = innerHTML.split("|");
    if (msgs[0] === actuserid) {
        appendmsg("0", actuserid, msgs[0], msgs[1]);
    } else if (msgs[0] === "0") {
        layer.msg('当前用户不在线', {
            time: 1500,
            icon: 0,
            offset: '50px'
        });
    } else if(msgs[0] == "G"){
        //alertGroupNote();
        //appendGroupmsg("0", actuserid, msgs[0], msgs[1] ,msgs[2]);
    }else {
        alertnote(msgs[0]);
    }
    document.getElementById("msg_end").scrollIntoView();
    //document.getElementById("msg_end_group").scrollIntoView();
}

//关闭连接
function closeWebSocket() {
    websocket.close();
}

function sendVideo(){
    if (confirm("是否查看直播?")){
        layer.open({
            title:'用户'+username+'直播'
            ,type: 1
            ,skin: 'layui-layer-molv'
            ,shadeClose: true
            ,area: ['700px', '500px']
            ,content: '<video id="videoPlay" width="100%" height="100%" controls></video>'
            ,success: function(layero, index){
                console.log(layero, index);
                var videoPlay = document.getElementById('videoPlay');
                // //服务器上rtmp应用设置为src 所以这里为src
                // //var videoSrc = 'http://192.168.1.175/flv?port=1935&app=src&stream=room';
                var videoSrc=rtmpPath+'/flv?port='+rtmpPort+'&app='+appName+'&stream='+stream;
                if (flvjs.isSupported()) {
                    var flvPlayer = flvjs.createPlayer({
                        type: 'flv',
                        url: videoSrc
                    });
                    flvPlayer.attachMediaElement(videoPlay);
                    flvPlayer.load();
                    flvPlayer.play();
                }
            }
            ,yes: function(){
                layer.msg("是否开启视频?");

            }
            ,no: function (){
                layer.msg("点击关闭");
            }
        });
    }else {
        layer.msg("你已取消观看帅哥直播!");
    }
}


//发送指定人消息
function sendNormalMsg() {
    let layedit = layui.layedit;
    let message = layedit.getContent(normalEidt);
    //没有标签的内容
    let notTagMessage = layedit.getText(normalEidt);
    //有空格jq解析失败问题&nbsp;
    let contentList= $(message);
    //判断是否包含表情，如果包含表情，则直接发送消息
    let isImg = false;
    for(let index=0; index < contentList.length;index++){
        contentList[index].nodeName === "IMG" ? isImg = true: 0;
    }

    if (!isImg && (message.length == 0 || notTagMessage.length == 0 )) {
        layer.msg("请输入发送的内容", {
            time: 2500,
            icon: 2,
            offset: "300px"
        });
        return;
    }
    // 将内容设置为空
    layedit.setContent(normalEidt, "", false);
    var object = new Object();
    object["receiveUserId"] = actuserid;
    object["msgType"] = "text";
    object["reciveObject"] = "N";
    object["sendUserId"] = userid;
    object["content"] =message;
    var jsonData = JSON.stringify(object);
    websocket.send(jsonData);//websocket发送数据
    appendmsg("0", actuserid, userid, message);//vue追加聊天数据
    document.getElementById("msg_end").scrollIntoView();
}

//发送指定人消息
// function sendGroupMsg() {
//     let layedit = layui.layedit;
//     let message = layedit.getContent(groupEidt);
//     //没有标签的内容
//     let notTagMessage = layedit.getText(groupEidt);
//
//     let contentList= $(message);
//     //判断是否包含表情，如果包含表情，则直接发送消息
//     let isImg = false;
//     for(let index=0; index < contentList.length;index++){
//         contentList[index].nodeName === "IMG" ? isImg = true: 0;
//     }
//
//     if (!isImg && (message.length == 0 || notTagMessage.length == 0 )) {
//         layer.msg("请输入发送的内容", {
//             time: 2500,
//             icon: 2,
//             offset: "300px"
//         });
//         return;
//     }
//     // 将内容设置为空
//     layedit.setContent(groupEidt, "", false);
//     var object = new Object();
//     object["reciveGroupId"] = $("#msgcontent_group").attr("data-group-id");
//     object["msgtype"] = 0;
//     object["reciveObject"] = "G";
//     object["sendtext"] =message;
//     var jsonData = JSON.stringify(object);
//     websocket.send(jsonData);//websocket发送数据
//     appendGroupmsg("0", actuserid, "G" ,userid, message);//vue追加聊天数据
//     document.getElementById("msg_end_group").scrollIntoView();
// }


function sendaudio(message) {
    if (actuserid == null) {
        layer.msg("聊天界面未选择用户", {
            time: 2500,
            icon: 2,
            offset: "300px"
        });
        return;
    }
    var object = new Object();
    object["reciveuserid"] = actuserid;
    object["msgtype"] = 0;
    object["sendtext"] = "<audio controls class=\"audio-player\"><source src=" +  message + " type=\"audio/mp3\"></audio>";
    var jsonData = JSON.stringify(object);
    websocket.send(jsonData);
    appendmsg("0", actuserid, userid, "<audio controls class=\"audio-player\"><source src=" +  message + " type=\"audio/mp3\"></audio>");
    document.getElementById("msg_end").scrollIntoView();
}

//layui面板刷新保留在当前面板
$(".layui-tab-title li").click(function () {
    var picTabNum = $(this).index();
    sessionStorage.setItem("picTabNum", picTabNum);
});
$(function () {
    var getPicTabNum = sessionStorage.getItem("picTabNum");
    $(".layui-tab-title li").eq(getPicTabNum).addClass("layui-this").siblings().removeClass("layui-this");
    $(".layui-tab-content>div").eq(getPicTabNum).addClass("layui-show").siblings().removeClass("layui-show");
})