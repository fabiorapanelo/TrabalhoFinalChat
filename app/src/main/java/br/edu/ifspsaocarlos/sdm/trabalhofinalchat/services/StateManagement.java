package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services;

/**
 * Created by fabio on 01/07/2017.
 */
public class StateManagement {

    private long contactOpened = 0;

    private static StateManagement instance = new StateManagement();

    private StateManagement(){

    }

    public static StateManagement getInstance(){
        return instance;
    }

    public void clearContactOpened(){
        this.setContactOpened(0);
    }

    public void setContactOpened(long contactOpened){
        this.contactOpened = contactOpened;
    }

    public long getContactOpened(){
        return contactOpened;
    }
}
