package br.edu.ifspsaocarlos.sdm.trabalhofinalchat;


import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.dao.ContactDAO;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.fragment.AddContactFragment;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.fragment.ViewContactsFragment;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.SearchNewMessagesService;

public class MainActivity extends AppCompatActivity {

    private ContactDAO contactDAO = ContactDAO.getInstance();

    private Intent serviceIntent;

    public static final int REQUEST_ADD_CONTACT = 1;
    public static final int REQUEST_CHAT = 2;

    //Temporary flag
    private boolean hasUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(this.userExists()){
            this.createViewContactsFragment();
        } else {
            this.createAddContactFragment();
        }

        serviceIntent = new Intent(getApplicationContext(), SearchNewMessagesService.class);
        startService(serviceIntent);
    }

    protected boolean userExists(){

        Contact contact = contactDAO.getCurrentUser();

        if(contact == null){
            return false;
        }

        return true;
    }

    protected void createAddContactFragment(){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container_fragment, new AddContactFragment());
        ft.commit();

    }

    protected void createViewContactsFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container_fragment, new ViewContactsFragment());
        ft.commit();
    }

    public void replaceViewContactsFragment(){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_fragment, new ViewContactsFragment());
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_contact_item) {
            Intent intent = new Intent(this, AddContactActivity.class);
            startActivityForResult(intent, REQUEST_ADD_CONTACT);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (REQUEST_ADD_CONTACT == requestCode && resultCode == RESULT_OK) {
            this.replaceViewContactsFragment();
        }
    }

    protected void onDestroy() {
        stopService(serviceIntent);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
        super.onDestroy();
    }


}
