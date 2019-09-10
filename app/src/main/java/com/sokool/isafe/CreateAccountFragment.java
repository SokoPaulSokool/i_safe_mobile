package com.sokool.isafe;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment implements View.OnClickListener {

    protected View rootView;
    protected EditText phoneNumber;
    protected EditText code;
    protected Button submit;
    protected TextView message;
    RegistrationActivity registrationActivity;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    String SUBMIT_TYPE = "PHONE";

    public CreateAccountFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_create_account, container, false);
        initView(rootView);
        code.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        SUBMIT_TYPE = "PHONE";
        mAuth = FirebaseAuth.getInstance();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {

            switch (SUBMIT_TYPE) {
                case "PHONE":
                    String phoneNumberStr = phoneNumber.getText().toString().trim();
                    if (!TextUtils.isEmpty(phoneNumberStr)) {
                        sendVerificationCode("+256" + phoneNumberStr);

                    } else {
                        Toast.makeText(registrationActivity, "Phone number is required", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case "CODE":
                    String codeStr = code.getText().toString().trim();
                    if (!TextUtils.isEmpty(codeStr)) {
                        verifyVerificationCode(codeStr);
                    } else {
                        Toast.makeText(registrationActivity, "Verification Code is required", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }


        }
    }

    private void initView(View rootView) {
        phoneNumber = (EditText) rootView.findViewById(R.id.phone_number);
        code = (EditText) rootView.findViewById(R.id.code);
        submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(CreateAccountFragment.this);
        message = (TextView) rootView.findViewById(R.id.message);
    }

    private void sendVerificationCode(String no) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                no,
                100,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
//                otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
            phoneNumber.setVisibility(View.GONE);
            code.setVisibility(View.VISIBLE);
            SUBMIT_TYPE = "CODE";
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
//                            Intent intent = new Intent(getActivity(), MainActivity.class);
//                            startActivity(intent);

                            registrationActivity.sendData("account");

                        } else {

                            //verification unsuccessful.. display an error message

                            String msg = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                msg = "Invalid code entered...";
                            }
                            message.setVisibility(View.VISIBLE);
                            message.setText(msg);


                        }
                    }
                });
    }
}
