package com.sokool.isafe;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmergenceDialogFragment extends DialogFragment implements View.OnClickListener {


    protected LinearLayout emergenceLayout;
    protected TextView name;
    protected TextView location;
    protected Button call;
    private View rootView;
    String nameStr;
    String messageStr;
    String locationStr;

    private static String ARG_LOCATION = "location";
    private static String ARG_MESSAGE = "message";
    private static String ARG_NAME= "name";

    public EmergenceDialogFragment() {
        // Required empty public constructor
    }

    public EmergenceDialogFragment(String name, String message, String location) {
        // Required empty public constructor
        this.nameStr = name;
        this.messageStr = message;
        this.locationStr = location;
    }
    public static EmergenceDialogFragment newInstance(String name, String message, String location) {
        EmergenceDialogFragment fragment = new EmergenceDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_emergence_dialog, null);
        initView(rootView);
        builder.setView(rootView);
        AlertDialog dialog = builder.create();
        scaleView(emergenceLayout, 0.95f, 1f);

        if (getArguments() != null) {
            nameStr = getArguments().getString(ARG_NAME);
            messageStr = getArguments().getString(ARG_MESSAGE);
            locationStr = getArguments().getString(ARG_LOCATION);
            name.setText(nameStr);

        }


        return dialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emergence_dialog, container, false);
    }

    private void initView(View rootView) {
        emergenceLayout = (LinearLayout) rootView.findViewById(R.id.emergence_layout);
        name = (TextView) rootView.findViewById(R.id.name);
        location = (TextView) rootView.findViewById(R.id.location);
        call = (Button) rootView.findViewById(R.id.call);
        call.setOnClickListener(EmergenceDialogFragment.this);
    }

    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                0.95f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1000);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        v.startAnimation(anim);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.call) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+256 777947691"));
            startActivity(intent);
        }
    }
}
