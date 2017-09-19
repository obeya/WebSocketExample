import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketUrl")
public class WebSocketListen {

    private static int onlineCount = 0; // 当前在线连接数

    private static CopyOnWriteArraySet<WebSocketListen> webSocketSet = new CopyOnWriteArraySet<WebSocketListen>();
    
    private Session session; // 连接会话
    
    /**
     * 连接建立成功后
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("在线人数:" + onlineCount);
    }

    /**
     * 连接关闭后调用
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("在线人数:" + onlineCount);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("客户端消息: " + message);
        for (WebSocketListen item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            
        }
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("错误!");
        error.printStackTrace();
    }
    
    /**
     * 发送信息
     */
    public void sendMessage(String message)throws IOException {
        this.session.getBasicRemote().sendText(message);
        // this.session.getAsyncRemote().sendText(message);
    }
    
    public static synchronized int getOnlineCount() {
        return WebSocketListen.onlineCount;
    }
    
    public static synchronized void addOnlineCount() {
        WebSocketListen.onlineCount++;
    }
    
    public static synchronized void subOnlineCount() {
        WebSocketListen.onlineCount--;
    }
}
