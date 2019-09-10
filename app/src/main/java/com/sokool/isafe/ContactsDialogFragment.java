package com.sokool.isafe;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sokool.isafe.interfaces.BuddyInterface;
import com.sokool.isafe.models.Buddy;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String CONSTACT_TYPE = "constactType";
    protected TextView title;
    protected RecyclerView contactsRecylerView;
    protected Button add;
    protected TextView message;

    private String constactType;
    private View rootView;
    private ArrayList<Buddy> buddyList;
    private SlimAdapter slimAdapter;


    public ContactsDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_contacts_dialog, null);
        initView(rootView);
        builder.setView(rootView);
        AlertDialog dialog = builder.create();

        if (getArguments() != null) {
            constactType = getArguments().getString(CONSTACT_TYPE);
            title.setText("My " + constactType);
        }

        contactsRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        slimAdapter = SlimAdapter.create()
                .register(R.layout.contact_item, new SlimInjector<Buddy>() {

                    @Override
                    public void onInject(final Buddy data, final IViewInjector injector) {
                        TextView name = (TextView) injector.findViewById(R.id.name);
                        TextView phoneNumber = (TextView) injector.findViewById(R.id.phone_number);
                        name.setText(data.getName());
                        phoneNumber.setText(data.getPhoneNumber());

                        injector.clicked(R.id.delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buddyList.remove(data);
                                slimAdapter.updateData(buddyList);
                                showMessage();
                            }
                        });

                    }
                })
                .attachTo(contactsRecylerView);

        buddyList = new ArrayList<>();
        slimAdapter.updateData(buddyList);
        showMessage();

        return dialog;
    }


    public static ContactsDialogFragment newInstance(String constactType) {
        ContactsDialogFragment fragment = new ContactsDialogFragment();
        Bundle args = new Bundle();
        args.putString(CONSTACT_TYPE, constactType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private void initView(View rootView) {
        title = (TextView) rootView.findViewById(R.id.title);
        contactsRecylerView = (RecyclerView) rootView.findViewById(R.id.contacts_recyler_view);
        add = (Button) rootView.findViewById(R.id.add);
        add.setOnClickListener(ContactsDialogFragment.this);
        message = (TextView) rootView.findViewById(R.id.message);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            AddContactFragment addContactFragment = AddContactFragment.newInstance(new BuddyInterface() {
                @Override
                public void sendBuddy(Buddy buddy) {
                    buddy.setId(buddyList.size() + 1 + "");
                    buddyList.add(buddy);
                    slimAdapter.updateData(buddyList);
                    showMessage();
                    Toast.makeText(getContext(), buddy.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            addContactFragment.show(fragmentManager, "addContact");
        }
    }

    private void showMessage(){
        if (buddyList.size() < 1) {
            message.setVisibility(View.VISIBLE);
        } else {
            message.setVisibility(View.INVISIBLE);
        }
    }
}
