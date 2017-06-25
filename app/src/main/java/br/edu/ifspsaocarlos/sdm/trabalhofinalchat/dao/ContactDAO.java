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

    private static ContactDAO instance = new ContactDAO();

    private ContactDAO(){

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

    public static ContactDAO getInstance(){
        return instance;
    }
}
