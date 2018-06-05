package com.zg.webrtc.webrtc;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@ServerEndpoint(value = "/rtc/{username}")
public class WebScoketServer {


    private static Map<WebScoketServer, String> webSocketSet = new HashMap<WebScoketServer, String>();

    private Session session;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) throws IOException {

        this.session = session;
        webSocketSet.put(this, username);
    }


    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(message, Map.class);
        if(map.get("target") != null){
            for(WebScoketServer webScoketServer:webSocketSet.keySet()){
                if(webSocketSet.get(webScoketServer).equals(map.get("target"))){
                    webScoketServer.session.getBasicRemote().sendText(message);
                }
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        webSocketSet.remove(this);
    }


    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);
    }
}
