package com.sokool.isafe;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.sokool.isafe.interfaces.BuddyInterface;
import com.sokool.isafe.models.Buddy;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends DialogFragment implements View.OnClickListener {


    protected EditText name;
    protected EditText phoneNumber;
    protected Button submit;
    private View rootView;
    BuddyInterface buddyInterface;

    public AddContactFragment() {
        // Required empty public constructor
    }


    public AddContactFragment(BuddyInterface buddyInterface) {
        // Required empty public constructor
        this.buddyInterface = buddyInterface;
    }

    public static AddContactFragment newInstance(BuddyInterface buddyInterface) {
        AddContactFragment fragment = new AddContactFragment();
        fragment.buddyInterface = buddyInterface;
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_add_contact, null);
        initView(rootView);
        builder.setView(rootView);
        AlertDialog dialog = builder.create();

        if (getArguments() != null) {

        }


        return dialog;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            String nameStr = name.getText().toString().trim();
            String phoneStr = phoneNumber.getText().toString().trim();
            if (!TextUtils.isEmpty(nameStr) && !TextUtils.isEmpty(phoneStr)) {
                buddyInterface.sendBuddy(new Buddy(nameStr, phoneStr));

                dismiss();
            } else {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void initView(View rootView) {
        name = (EditText) rootView.findViewById(R.id.name);
        phoneNumber = (EditText) rootView.findViewById(R.id.phone_number);
        submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(AddContactFragment.this);
    }
}
