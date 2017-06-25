package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model;

/**
 * Created by fabio on 23/06/2017.
 */
public class Contact {

    private long id;
    private String name;
    private String nickname;

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
}
