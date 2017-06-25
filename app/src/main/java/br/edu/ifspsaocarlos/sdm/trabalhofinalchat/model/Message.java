package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model;

/**
 * Created by fabio on 25/06/2017.
 */
public class Message {

    private long id;
    private Contact origin;
    private Contact destination;
    private String subject;
    private String body;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Contact getOrigin() {
        return origin;
    }

    public void setOrigin(Contact origin) {
        this.origin = origin;
    }

    public Contact getDestination() {
        return destination;
    }

    public void setDestination(Contact destination) {
        this.destination = destination;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
