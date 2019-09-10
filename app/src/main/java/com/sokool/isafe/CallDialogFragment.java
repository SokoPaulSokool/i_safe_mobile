package com.sokool.isafe;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallDialogFragment extends DialogFragment {


    protected TextView notify;
    private View rootView;

    public CallDialogFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_call_dialog, null);
        initView(rootView);
        builder.setView(rootView);
        AlertDialog dialog = builder.create();
        scaleView(notify,0.9f,1f);


        if (getArguments() != null) {

        }


        return dialog;
    }


    private void initView(View rootView) {
        notify = (TextView) rootView.findViewById(R.id.notify);
    }
    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                0.9f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1000);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        v.startAnimation(anim);
    }
}
