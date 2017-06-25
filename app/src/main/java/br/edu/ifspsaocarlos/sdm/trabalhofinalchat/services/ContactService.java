package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;

/**
 * Created by fabio on 25/06/2017.
 */
public class ContactService extends ServiceBase {

    private static final String PATH = "/contato";
    private static final String CONTATOS_ROOT_OBJECT = "contatos";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "nome_completo";
    private static final String FIELD_NICKNAME = "apelido";

    public void findAll(final Context context, final ServiceListener serviceListener) {

        RequestQueue queue = Volley.newRequestQueue(context);
        try {
            String url = this.getUrl(PATH);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject s) {
                    List<Contact> contacts = new ArrayList<>();

                    try {
                        JSONArray jsonArray = s.getJSONArray(CONTATOS_ROOT_OBJECT);

                        for (int index = 0; index < jsonArray.length(); index++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(index);
                            Contact contact = ContactService.this.mapJsonToContact(jsonObject);
                            contacts.add(contact);
                        }

                        serviceListener.onSuccess(contacts);
                    } catch (JSONException ex) {
                        serviceListener.onError(ex);
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    serviceListener.onError(volleyError);
                }
            });
            queue.add(jsonObjectRequest);
        } catch (Exception ex) {
            serviceListener.onError(ex);
        }
    }

    public void findByNameOrNickname(final Context context, final ServiceListener serviceListener, final String text) {

        if(text == null){
            serviceListener.onError(new NullPointerException());
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        try {
            String url = this.getUrl(PATH);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject s) {
                    List<Contact> contacts = new ArrayList<>();

                    try {
                        JSONArray jsonArray = s.getJSONArray(CONTATOS_ROOT_OBJECT);

                        for (int index = 0; index < jsonArray.length(); index++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(index);

                            String name = jsonObject.getString(FIELD_NAME);
                            String nickname = jsonObject.getString(FIELD_NICKNAME);

                            if(name.toLowerCase().contains(text.toLowerCase()) ||
                                    nickname.toLowerCase().contains(text.toLowerCase())){
                                Contact contact = ContactService.this.mapJsonToContact(jsonObject);
                                contacts.add(contact);
                            }
                        }

                        serviceListener.onSuccess(contacts);
                    } catch (JSONException ex) {
                        serviceListener.onError(ex);
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    serviceListener.onError(volleyError);
                }
            });
            queue.add(jsonObjectRequest);
        } catch (Exception ex) {
            serviceListener.onError(ex);
        }
    }

    protected Contact mapJsonToContact(JSONObject jsonObject) throws JSONException{
        Contact contact = new Contact();
        contact.setId(jsonObject.getLong(FIELD_ID));
        contact.setName(jsonObject.getString(FIELD_NAME));
        contact.setNickname(jsonObject.getString(FIELD_NICKNAME));

        return contact;
    }
}
