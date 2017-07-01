package br.edu.ifspsaocarlos.sdm.trabalhofinalchat;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data.ContactDao;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data.MessageDao;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data.UserInfoDao;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Message;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.MessageService;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.ServiceListener;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.StateManagement;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MENSAGEM_TEXTO = 0;
    private MessageService messageService = new MessageService();
    private ArrayAdapter<String> historyChatAdapter;
    private ChatHandler chatHandler;
    private ThreadChat threadChat;
    private Button btSend;
    private long lastMessageId = 0;
    private long contactId;
    private Contact user;
    private Contact contact;

    private UserInfoDao userInfoDao = new UserInfoDao(this);
    private ContactDao contactDao = new ContactDao(this);
    private MessageDao messageDao = new MessageDao(this);

    protected StateManagement stateManagement = StateManagement.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        long contactId = getIntent().getLongExtra("contact_id", 0);

        user = userInfoDao.find();
        contact = contactDao.findById(contactId);

        //Finish activity if the contact is invalid
        if(user.getId() == 0 || contact.getId() == 0){
            finish();
        }

        stateManagement.setContactOpened(contactId);

        chatHandler = new ChatHandler();
        threadChat = new ThreadChat();
        historyChatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ListView lvHistory = (ListView) findViewById(R.id.lv_history);
        lvHistory.setAdapter(historyChatAdapter);
        btSend = (Button) findViewById(R.id.bt_send);
        btSend.setOnClickListener(this);
        threadChat.run();
    }

    @Override
    public void onClick(View view) {
        if (view == btSend) {
            EditText etMessage = (EditText) findViewById(R.id.et_message);
            String textMessage = etMessage.getText().toString();
            etMessage.setText("");

            Message message = new Message();
            message.setOrigin(user);
            message.setDestination(contact);
            message.setSubject("");
            message.setBody(textMessage);

            messageService.save(this, new ServiceListener() {
                @Override
                public void onSuccess(Object object) {
                    Message message = (Message) object;
                    messageDao.save(message);
                }

                @Override
                public void onError(Exception ex) {
                    Log.d("sendMessage", ex.getMessage(), ex);
                }
            }, message);

            /*historyChatAdapter.add(user.getName() + ": " + textMessage);
            historyChatAdapter.notifyDataSetChanged();*/
        }

    }
    private class ChatHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == MENSAGEM_TEXTO) {
                historyChatAdapter.add(msg.obj.toString());
                historyChatAdapter.notifyDataSetChanged();
            }
        }
    }
    private class ThreadChat extends Thread{
        public void run() {
            List<Message> messages = messageDao.findByContact(contact, lastMessageId);

            if (messages != null) {
                for (Message message : messages) {
                    historyChatAdapter.add(message.getOrigin().getName() + ": " + message.getBody());
                    lastMessageId = message.getId();
                }
            }

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
