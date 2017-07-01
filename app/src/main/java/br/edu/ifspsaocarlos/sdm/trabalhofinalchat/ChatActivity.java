package br.edu.ifspsaocarlos.sdm.trabalhofinalchat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.StateManagement;

public class ChatActivity extends BaseActivity {

    protected StateManagement stateManagement = StateManagement.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        long contactId = getIntent().getLongExtra("contact_id", 0);

        //Finish activity if the contact is invalid
        if(contactId == 0){
            finish();
        }

        stateManagement.setContactOpened(contactId);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onDestroy() {

        stateManagement.clearContactOpened();

        super.onDestroy();
    }
}
