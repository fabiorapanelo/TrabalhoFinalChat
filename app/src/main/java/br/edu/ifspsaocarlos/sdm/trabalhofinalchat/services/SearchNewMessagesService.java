package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.ChatActivity;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.R;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.dao.ContactDAO;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data.MessageDao;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Message;

public class SearchNewMessagesService extends Service implements Runnable {
    private boolean aplicationOpened;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        aplicationOpened = true;
        new Thread(this).start();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void run() {
        while (aplicationOpened) {
            try {
                Thread.sleep(3000);

                ContactDAO contactDAO = ContactDAO.getInstance();
                final MessageDao messageDao = new MessageDao(this);

                Contact currentUser = contactDAO.getCurrentUser();

                for(Contact contact: contactDAO.findAll()){

                    Message message = messageDao.findLastMessage(contact);
                    if(message == null){
                        message = this.createDefaultMessage(contact, currentUser);
                    }

                    MessageService messageService = new MessageService();
                    messageService.getMessages(this, new ServiceListener() {
                        @Override
                        public void onSuccess(Object object) {
                            //If there is messages, show the notification

                            List<Message> messages = (List<Message>) object;

                            if(messages != null && messages.size() > 0) {

                                Message message = messages.get(0);

                                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                Intent intent = new Intent(SearchNewMessagesService.this, ChatActivity.class);
                                intent.putExtra("contact_id", message.getOrigin().getId());

                                PendingIntent p = PendingIntent.getActivity(SearchNewMessagesService.this, 0, intent, 0);
                                Notification.Builder builder = new Notification.Builder(SearchNewMessagesService.this);
                                builder.setSmallIcon(R.drawable.ic_contato);
                                builder.setTicker("Nova mensagem de " + message.getOrigin().getName());
                                builder.setContentTitle("Nova mensagem de " + message.getOrigin().getName());
                                builder.setContentText("Voce tem " + messages.size() + "mensagens");
                                builder.setWhen(System.currentTimeMillis());
                                builder.setContentIntent(p);
                                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_contato));
                                Notification notification = builder.build();
                                notification.vibrate = new long[]{100, 250};
                                nm.notify(R.mipmap.ic_launcher, notification);

                                messageDao.saveAll(messages);
                            }
                        }

                        @Override
                        public void onError(Exception ex) {

                        }
                    }, message);

                }

            } catch (InterruptedException ie) {
                Log.e("SDM", "Erro na recuperação das mensagens");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        aplicationOpened = false;
        stopSelf();
    }

    protected Message createDefaultMessage(Contact origin, Contact destination){

        Message defaultMessage = new Message();

        defaultMessage.setId(0);
        defaultMessage.setOrigin(origin);
        defaultMessage.setDestination(destination);

        return defaultMessage;
    }
}
