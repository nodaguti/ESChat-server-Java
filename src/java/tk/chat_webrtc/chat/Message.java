package tk.chat_webrtc.chat;

public class Message {
    public enum TYPE {
        LOGIN,
        NAME_CHANGED,
        CHAT_STARTED,
        CHAT_ENDED,
        LOGOUT
    };
    
    
    private TYPE type;
    
    private String value;
    
    
    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
