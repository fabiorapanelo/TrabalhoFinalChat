package br.edu.ifspsaocarlos.sdm.trabalhofinalchat;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.dao.ContactDAO;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.ContactService;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.ServiceListener;

public class AddContactActivity extends AppCompatActivity {

    private ListView listView;
    private Button searchContactButton;
    private EditText searchContactText;
    private ContactService contactService = new ContactService();
    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        this.setupToolbar();

        listView = (ListView) findViewById(R.id.list_view_contacts);

        searchContactText = (EditText) findViewById(R.id.search_contact_text);

        searchContactButton = (Button) findViewById(R.id.search_contact_button);
        searchContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = searchContactText.getText().toString();
                contactService.findByNameOrNickname(AddContactActivity.this, new ServiceListener() {
                    @Override
                    public void onSuccess(Object object) {
                        contacts = (ArrayList<Contact>) object;
                        ContactListAdapter adapter = new ContactListAdapter(AddContactActivity.this, contacts);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Exception ex) {
                        Toast.makeText(AddContactActivity.this, R.string.error_retrieve_contact, Toast.LENGTH_LONG).show();
                    }
                }, searchText);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactDAO contactDAO = ContactDAO.getInstance();
                contactDAO.save(contacts.get(i));
                AddContactActivity.this.setResult(AddContactActivity.RESULT_OK);
                AddContactActivity.this.finish();
            }
        });
    }

    protected void setupToolbar(){
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}
