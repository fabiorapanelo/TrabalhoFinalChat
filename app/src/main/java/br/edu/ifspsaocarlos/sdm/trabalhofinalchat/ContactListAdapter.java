package br.edu.ifspsaocarlos.sdm.trabalhofinalchat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;

/**
 * Created by fabio on 23/06/2017.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater layoutInflater;

    public ContactListAdapter(Activity activity, List<Contact> categories) {
        super(activity, R.layout.contact, categories);
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.contact, null);
        }
        Contact contact = getItem(position);
        TextView textView = (TextView) convertView.findViewById(R.id.txt_contact_name);
        textView.setText(contact.getName());

        return convertView;

    }
}