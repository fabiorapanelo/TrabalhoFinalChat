package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.MainActivity;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.R;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.dao.ContactDAO;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data.UserInfoDao;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.ContactService;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.services.ServiceListener;

public class AddContactFragment extends Fragment {

    private MainActivity mainActivity;

    private UserInfoDao userInfoDao;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            userInfoDao = new UserInfoDao(mainActivity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        this.setupAddContactButton(view);

        return view;
    }


    protected void setupAddContactButton(final View view) {
        Button addContactButton = (Button) view.findViewById(R.id.add_contact_button);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameText = (EditText) view.findViewById(R.id.contact_name);
                EditText nicknameText = (EditText) view.findViewById(R.id.contact_nickname);

                Contact contact = new Contact();
                contact.setName(nameText.getText().toString());
                contact.setNickname(nicknameText.getText().toString());

                ContactService contactService = new ContactService();
                contactService.save(mainActivity, new ServiceListener() {
                    @Override
                    public void onSuccess(Object object) {

                        Contact currentUser = ((Contact) object);
                        userInfoDao.save(currentUser);
                        mainActivity.replaceViewContactsFragment();

                        Toast.makeText(mainActivity, "Seu id é #" + currentUser.getId(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Exception ex) {
                        Toast.makeText(mainActivity, R.string.error_add_contact, Toast.LENGTH_LONG).show();
                    }
                }, contact);

            }

        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }
}
