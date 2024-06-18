package com.help_farmers.service.websocket;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * @Author: WZ
 * @Date: 2023/7/5 20:58
 * @Description:
 */

@Component
@Slf4j
@ServerEndpoint("/hp/ws")
public class HPWebSocket {

    public static Set<Session> sessionSet = new HashSet<>();

    private static Map<Session, String> userMap = new HashMap<>();

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Map<Session, String> getUserMap() {
        return userMap;
    }

    /**
     * 当客户端和服务器通过websocket 建立起一个长连接的时候 就会自动调用这个方法
     * @param session 就是双向会话对象 讲来我们得通过这个会话对象 实现 服务器给客户端主动的发送消息
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {

        log.info("建立连接");
        sessionSet.add(session);
        // 每有一个客户端链接上来 就把这个连接放入到set 中 日后可以集中管理
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        List<String> userName = requestParameterMap.get("userName");
        if (ObjectUtil.isNotNull(userName)) {
            if (!userMap.containsValue(userName.get(0))) {
                userMap.put(session, userName.get(0));
                log.info("userMap: {}", userMap);
                log.info("用户：{}---建立连接", userName);
                List<Integer> result = new ArrayList<>();
                result.add(userMap.size());
                String resdata = objectMapper.writeValueAsString(result);
                for (Session session1 : sessionSet) {
                    session1.getBasicRemote().sendText(resdata);
                }
            }else {
                Session key = null;
                Set<Map.Entry<Session, String>> entries = userMap.entrySet();
                for (Map.Entry<Session, String> entry : entries) {
                    if (entry.getValue().equals(userName.get(0))) {
                        key = entry.getKey();
                        userMap.remove(key);
                    }
                }
                userMap.put(session, userName.get(0));
                log.info("userMap: {}", userMap);
                log.info("用户：{}---建立连接", userName);
                List<Integer> result = new ArrayList<>();
                result.add(userMap.size());
                String resdata = objectMapper.writeValueAsString(result);
                sessionSet.remove(key);
                sessionSet.add(session);
                for (Session session2 : sessionSet) {
                    session2.getBasicRemote().sendText(resdata);
                }
            }

        }
    }

    /**
     * 如果有连接关闭了 服务器端就会调用这个方法
     * @param session 关闭的连接
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        if (sessionSet.contains(session)){
            sessionSet.remove(session);
            String userName = userMap.get(session);
            log.info("userMap: {}", userMap);
            if (StrUtil.isNotBlank(userName)) {
                userMap.remove(session);
                log.info("用户：{}---断开连接", userName);
                log.info("userMap: {}", userMap);
                if (sessionSet.size() != 0) {
                    List<Integer> result = new ArrayList<>();
                    result.add(userMap.size());
                    String resdata = objectMapper.writeValueAsString(result);
                    for (Session session1 : sessionSet) {
                        session1.getBasicRemote().sendText(resdata);
                    }
                }
            }
            log.info("连接关闭");
        }
    }

    public void sendMessageToAllClient() throws IOException {

        Map<String, Object> result = new HashMap<>();
        result.put("333", "刷新控制台");

        String resdata = objectMapper.writeValueAsString(result);

        Set<Session> sessions = new HashSet<>();

        for (Session session : sessionSet) {
            if (!userMap.containsKey(session)) {
                sessions.add(session);
            }
        }

        for (Session session : sessions) {
            session.getBasicRemote().sendText(resdata);
        }
    }


}
