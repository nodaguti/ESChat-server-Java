package tk.chat_webrtc.chat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chatinfo")
public class ChatWSEndpoint {
    
    static Map<Session, Client> clients =
            Collections.synchronizedMap(new HashMap<Session, Client>());

    @OnMessage
    public String onMessage(String aMessage, Session aSession) {
         try{
             ObjectMapper mapper = new ObjectMapper();
             Message message = mapper.readValue(aMessage, Message.class);
             Client target = ChatWSEndpoint.clients.get(aSession);
             
             switch(message.getType()){
                 
                case LOGIN:
                     ChatWSEndpoint.clients.put(aSession, new Client(message.getValue()));
                     break;
                    
                case NAME_CHANGED:
                    target.setName(message.getValue());
                    break;
                    
                case CHAT_STARTED:
                    target.setStatus(Client.STATUS.CHATTING);
                    break;
                    
                case CHAT_ENDED:
                    target.setStatus(Client.STATUS.ONLINE);
                    break;
                    
                case LOGOUT:
                    target.setStatus(Client.STATUS.OFFLINE);
                    break;
                    
                default:
             }
             
             ChatWSEndpoint.notifyOnlineAccountsInfo(aSession);
             
         }catch(JsonMappingException e){
             e.printStackTrace();
         }catch(JsonParseException e){
             e.printStackTrace();
         }catch(Exception e){
             e.printStackTrace();
         }
        
        return null;
    }
    
    
    public static void notifyOnlineAccountsInfo(Session session){
        ObjectMapper mapper = new ObjectMapper();
        
        try{
            List<Client> clientList = new ArrayList<>();
            Set<Map.Entry<Session, Client>> clientSet = ChatWSEndpoint.clients.entrySet();
            Iterator<Map.Entry<Session, Client>> it = clientSet.iterator();
            
            while(it.hasNext()){
                Map.Entry<Session, Client> entry = it.next();
                clientList.add(entry.getValue());
            }
            
            String json = mapper.writeValueAsString(clientList);
            
            for(Session s : session.getOpenSessions()){
                s.getAsyncRemote().sendText(json);
            }
        }catch(JsonMappingException e){
            e.printStackTrace();
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        ChatWSEndpoint.clients.remove(session);
    }

    
}
