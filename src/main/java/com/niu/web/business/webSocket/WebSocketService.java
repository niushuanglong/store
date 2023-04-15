package com.niu.web.business.webSocket;

import com.alibaba.fastjson.JSONObject;
import com.niu.web.business.dto.ChatMessageDTO;
import com.niu.web.business.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author niushuanglong
 * @date 2023/2/26 22:12:52
 * @description
 */
@Slf4j
@Service
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketService {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String,WebSocketService> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 使用静态 变成类
     */
    private static ChatMessageService chatMessageService;
    @Autowired
    public void setChatService(ChatMessageService chatService) {
        WebSocketService.chatMessageService = chatService;
    }

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的session对象。
     */
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if(webSocketMap.containsKey(userId) && sessionMap.containsKey(userId)){
            webSocketMap.remove(userId);
            sessionMap.remove(userId);
            sessionMap.put(userId,session);
            webSocketMap.put(userId,this);
        }else{
            webSocketMap.put(userId,this);
            sessionMap.put(userId,session);
            addOnlineCount();
        }
        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @SuppressWarnings("unused")
    @OnMessage
    public void onMessage(String message, Session session) {
        if(!StringUtils.isEmpty(message)) {
            this.session = session;
            log.info("收到客户端消息 -> {}",message);
            JSONObject jsonObject=JSONObject.parseObject(message);
            ChatMessageDTO chatMessageDTO = jsonObject.toJavaObject(ChatMessageDTO.class);
            sendToUser(chatMessageDTO);
        }else {
            try {
                webSocketMap.get(userId).sendMessage("0"+"|"+"数据异常");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.getMessage());
    }

    /**
     * 实现服务器主动推送   可以通过controller调用此方法实现主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    public void sendToUser(ChatMessageDTO dto){
        try {
            String sendUserId = dto.getSendUserId();
            String content = dto.getContent();
            String msgType = dto.getMsgType();
            String receiveUserId = dto.getReceiveUserId();
            dto.setReceiveDate(new Date());
            chatMessageService.insertCharMessage(dto);
            if (webSocketMap.get(receiveUserId)!=null){
                webSocketMap.get(receiveUserId).sendMessage(userId+"|"+content);
            }else {
                webSocketMap.get(userId).sendMessage("0"+"|"+"当前用户不在线");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount.getAndIncrement();
    }

    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount.getAndDecrement();
    }
}



