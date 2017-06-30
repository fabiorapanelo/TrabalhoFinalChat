package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.ChatActivity;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.ContactListAdapter;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.MainActivity;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.R;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.dao.ContactDAO;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data.ContactDao;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.ContactService;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.ServiceListener;

/**
 * Created by fabio on 26/06/2017.
 */
public class ViewContactsFragment extends Fragment  implements AdapterView.OnItemClickListener {

    private MainActivity mainActivity;

    public static List<Contact> contacts;

    private ListView listView;
    private ContactListAdapter adapter;

    private ContactDao contactDao;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            contactDao = new ContactDao(mainActivity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_contacts, container, false);

        listView = (ListView) view.findViewById(R.id.list_view_contacts);

        this.updateContactList();

        return view;
    }

    protected void updateContactList(){
        this.contacts = contactDao.findAll();
        this.adapter = new ContactListAdapter(mainActivity, contacts);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(this);
    }




    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public void onItemClick(AdapterView l, View v, int position, long id) {

        Contact contact = contacts.get(position);
        Intent intent = new Intent(mainActivity, ChatActivity.class);
        intent.putExtra("contact_id", contact.getId());
        startActivityForResult(intent, MainActivity.REQUEST_CHAT);

    }
}
