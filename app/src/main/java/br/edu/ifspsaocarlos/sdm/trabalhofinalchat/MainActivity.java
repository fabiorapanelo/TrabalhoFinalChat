package br.edu.ifspsaocarlos.sdm.trabalhofinalchat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    private static final int REQUEST_ADD_CONTACT = 1;
    private static final int REQUEST_CHAT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Toast.makeText(this, this.getString(R.string.contact_added), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //contacts.get(position)
        Intent intent = new Intent(this, ChatActivity.class);
        //intent.putExtra("contact_id", contacts.getId());
        startActivityForResult(intent, REQUEST_CHAT);

    }
}
