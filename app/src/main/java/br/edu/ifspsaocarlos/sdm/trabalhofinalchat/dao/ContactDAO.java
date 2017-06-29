package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;

/**
 * Created by fabio on 25/06/2017.
 */
public class ContactDAO {

    //TODO: Remove onmemory list and use SQLITE
    public List<Contact> contacts = new ArrayList<Contact>();
    public Contact currentUser;

    private static ContactDAO instance = new ContactDAO();

    private ContactDAO(){

        //Remove in case you want to see create user screen
        currentUser  = new Contact();
        currentUser.setId(621);
        currentUser.setName("Fábio Rapanelo");
        currentUser.setNickname("fabio");


        //currentUser  = new Contact();
        //currentUser.setId(660);
        //currentUser.setName("Robson Teixeira");
        //currentUser.setNickname("robson.teixeira");

    }

    public List<Contact> findAll(){
        return contacts;
    }

    public Contact findById(long id){
        for(Contact contact: contacts){
            if(contact.getId() == id){
                return contact;
            }
        }
        return null;
    }

    public void save(Contact contact){
        contacts.add(contact);
    }

    public Contact getCurrentUser(){
        return this.currentUser;
    }

    public void saveCurrentUser(Contact currentUser){
        this.currentUser = currentUser;
    }

    public static ContactDAO getInstance(){
        return instance;
    }
}
