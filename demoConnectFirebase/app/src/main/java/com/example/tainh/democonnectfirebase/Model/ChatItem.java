package com.example.tainh.democonnectfirebase.Model;

import java.util.HashMap;
import java.util.Map;

public class ChatItem {
    private String uID;
    private String chatConnten;
    private String chatAuthor;

    public ChatItem(String uID, String chatConnten, String chatAuthor) {
        this.uID = uID;
        this.chatConnten = chatConnten;
        this.chatAuthor = chatAuthor;
    }

    public Map<String ,Object> toMap(){
        HashMap<String ,Object> result = new HashMap<>();
        result.put("uID", uID);
        result.put("chatAuthor",chatAuthor);
        result.put("chatConnten",chatConnten);

        return result;
    }

    public ChatItem() {
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getChatConnten() {
        return chatConnten;
    }

    public void setChatConnten(String chatConnten) {
        this.chatConnten = chatConnten;
    }

    public String getChatAuthor() {
        return chatAuthor;
    }

    public void setChatAuthor(String chatAuthor) {
        this.chatAuthor = chatAuthor;
    }
}
