package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model;

/**
 * Created by Robson on 26-Jun-17.
 */

public class UserInfo {
    private long id;
    private String name;
    private String nickname;
    private long lastMessageId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(long lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

}
