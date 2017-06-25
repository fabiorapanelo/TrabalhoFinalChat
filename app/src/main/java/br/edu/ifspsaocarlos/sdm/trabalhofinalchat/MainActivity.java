package br.edu.ifspsaocarlos.sdm.trabalhofinalchat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.dao.ContactDAO;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static List<Contact> contacts;

    private ListView listView;
    private ContactListAdapter adapter;

    public static final int REQUEST_ADD_CONTACT = 1;
    public static final int REQUEST_CHAT = 2;

    private ContactDAO contactDAO = ContactDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view_contacts);

        this.updateContactList();
    }

    protected void updateContactList(){
        this.contacts = contactDAO.findAll();
        this.adapter = new ContactListAdapter(this, contacts);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(this);
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
            this.updateContactList();
        }
    }

    public void onItemClick(AdapterView l, View v, int position, long id) {

        Contact contact = contacts.get(position);
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("contact_id", contact.getId());
        startActivityForResult(intent, REQUEST_CHAT);

    }

}
