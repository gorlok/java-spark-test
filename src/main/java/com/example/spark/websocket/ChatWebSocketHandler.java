package com.example.spark.websocket;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class ChatWebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = "User" + ChatServer.nextUserNumber++;
        ChatServer.userUsernameMap.put(user, username);
        ChatServer.broadcastMessage("Server", (username + " joined the chat"));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = ChatServer.userUsernameMap.get(user);
        ChatServer.userUsernameMap.remove(user);
        ChatServer.broadcastMessage("Server", (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        ChatServer.broadcastMessage(ChatServer.userUsernameMap.get(user), message);
    }

}
