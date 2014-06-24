package tk.chat_webrtc.chat;


public class Client {
    
    public enum STATUS {
        OFFLINE,
        ONLINE,
        CHATTING
    }
    
    private String id;
            
    private String name;
    
    private STATUS status;
    
    
    public Client(String id){
        this.id = id;
        this.name = id;
        this.status = STATUS.ONLINE;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
    
    
}
