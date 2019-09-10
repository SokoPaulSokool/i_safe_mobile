package com.sokool.isafe;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDeviceFragment extends Fragment implements View.OnClickListener {


    protected View rootView;
    protected EditText deviceId;
    protected Button submit;
    private RegistrationActivity registrationActivity;

    public AddDeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registrationActivity = (RegistrationActivity) context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_device, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        deviceId = (EditText) rootView.findViewById(R.id.device_id);
        submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(AddDeviceFragment.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            String deviceIdStr = deviceId.getText().toString().trim();
            if (!TextUtils.isEmpty(deviceIdStr)) {
                registrationActivity.sendData("device");

            } else {
                Toast.makeText(registrationActivity, "Device Id is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
