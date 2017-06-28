package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Message;

/**
 * Created by Robson on 27-Jun-17.
 */

public class JSONConverter {

    public static Contact contactJSONToObject(String json) {
        try {
            JSONObject contactJSON = new JSONObject(json);

            Contact contact = new Contact();
            contact.setId(contactJSON.getLong("id"));
            contact.setName(contactJSON.getString("nome_completo"));
            contact.setNickname(contactJSON.getString("apelido"));

            return contact;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Contact> contactListJSONToArrayList(String json) {
        try {
            JSONObject contactListJSON = new JSONObject(json);
            JSONArray contactArrayJSON = contactListJSON.getJSONArray("contatos");

            ArrayList contactList = new ArrayList<>();
            for (int i = 0; i < contactArrayJSON.length(); i++) {
                contactList.add(contactJSONToObject(contactArrayJSON.getString(i)));
            }
            return contactList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Message messageJSONToObject(String json) {
        try {
            JSONObject messageJSON = new JSONObject(json);

            Message message = new Message();
            message.setId(messageJSON.getLong("id"));
            message.setOrigin(contactJSONToObject(messageJSON.getString("origem")));
            message.setDestination(contactJSONToObject(messageJSON.getString("destino")));
            message.setSubject(messageJSON.getString("assunto"));
            message.setBody(messageJSON.getString("corpo"));

            return message;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Message> messageListJSONToArrayList(String json) {
        try {
            JSONObject messageListJSON = new JSONObject(json);
            JSONArray messageArrayJSON = messageListJSON.getJSONArray("mensagens");

            ArrayList messageList = new ArrayList<>();
            for (int i = 0; i < messageArrayJSON.length(); i++) {
                messageList.add(messageJSONToObject(messageArrayJSON.getString(i)));
            }
            return messageList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
