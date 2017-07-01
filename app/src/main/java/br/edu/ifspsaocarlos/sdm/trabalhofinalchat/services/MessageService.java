package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services;

import android.content.Context;
import android.util.Log;

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
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Message;

/**
 * Created by fabio on 25/06/2017.
 */
public class MessageService extends ServiceBase {

    private static final String PATH = "/mensagem";

    private static final String MENSAGENS_ROOT_OBJECT = "mensagens";

    private static final String FIELD_ID = "id";
    private static final String FIELD_ORIGIN = "origem";
    private static final String FIELD_ORIGIN_ID = "origem_id";
    private static final String FIELD_DESTINATION = "destino";
    private static final String FIELD_DESTINATION_ID = "destino_id";
    private static final String FIELD_SUBJECT = "assunto";
    private static final String FIELD_BODY = "corpo";

    public void save(final Context context, final ServiceListener serviceListener, final Message message) {

        if(message == null){
            serviceListener.onError(new NullPointerException());
            return;
        }

        try {
            JSONObject jsonObject = this.mapMessageToJson(message);
            RequestQueue queue = Volley.newRequestQueue(context);

            String url = this.getUrl(PATH);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jsonObject) {
                    try {
                        Message message = MessageService.mapJsonToMessage(jsonObject);
                        serviceListener.onSuccess(message);
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

    public void getMessages(final Context context, final ServiceListener serviceListener, final Message message) {

        if(message == null){
            serviceListener.onError(new NullPointerException());
            return;
        }

        try {

            RequestQueue queue = Volley.newRequestQueue(context);

            String url = this.getUrl(PATH + "/" + message.getId() + "/" + message.getOrigin().getId() + "/" + message.getDestination().getId());

            Log.d("MessageService", "URL#" + url);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jsonObject) {
                    try {

                        List<Message> messages = new ArrayList<>();

                        JSONArray jsonArray = jsonObject.getJSONArray(MENSAGENS_ROOT_OBJECT);

                        for (int index = 0; index < jsonArray.length(); index++) {
                            JSONObject jsonMessage = jsonArray.getJSONObject(index);
                            Message message = MessageService.mapJsonToMessage(jsonMessage);
                            messages.add(message);
                        }

                        serviceListener.onSuccess(messages);
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

    public static JSONObject mapMessageToJson(Message message) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(FIELD_ORIGIN_ID, message.getOrigin().getId());
        jsonObject.put(FIELD_DESTINATION_ID, message.getDestination().getId());
        jsonObject.put(FIELD_SUBJECT, message.getSubject());
        jsonObject.put(FIELD_BODY, message.getBody());

        return jsonObject;
    }

    public static Message mapJsonToMessage(JSONObject jsonObject) throws JSONException {

        Message message = new Message();

        message.setId(jsonObject.getLong(FIELD_ID));
        message.setSubject(jsonObject.getString(FIELD_SUBJECT));
        message.setBody(jsonObject.getString(FIELD_BODY));

        JSONObject jsonOrigin = (JSONObject) jsonObject.get(FIELD_ORIGIN);
        Contact contactOrigin = ContactService.mapJsonToContact(jsonOrigin);
        message.setOrigin(contactOrigin);

        JSONObject jsonDestination = (JSONObject) jsonObject.get(FIELD_DESTINATION);
        Contact contactDestination = ContactService.mapJsonToContact(jsonDestination);
        message.setDestination(contactDestination);

        return message;
    }
}
