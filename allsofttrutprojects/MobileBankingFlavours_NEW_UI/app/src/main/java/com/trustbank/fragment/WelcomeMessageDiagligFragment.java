package com.trustbank.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trustbank.R;
import com.trustbank.activity.MenuActivity;
import com.trustbank.util.AppConstants;
import com.trustbank.util.TrustMethods;

import java.util.Objects;

public class WelcomeMessageDiagligFragment extends DialogFragment implements View.OnClickListener {
    private TextView message,title;
    private TrustMethods method;
    private Button close_button;
    private String welcomemessage,welcomemessageexitflag;

    public WelcomeMessageDiagligFragment(String welcomemessage, String welcomemessageexitflag) {
        this.welcomemessage=welcomemessage;
        this.welcomemessageexitflag=welcomemessageexitflag;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialogFragment = new Dialog(Objects.requireNonNull(getActivity()));
        dialogFragment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFragment.setCanceledOnTouchOutside(false);
        return dialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_message_diaglig, container, false);
        inIt(view);
        return view;
    }

    private void inIt(View view) {
        method=new TrustMethods(getActivity());
        message=(TextView)view.findViewById(R.id.message);
        title=(TextView)view.findViewById(R.id.title);
        close_button=(Button)view.findViewById(R.id.close_button);
        close_button.setOnClickListener(this);
        message.setText(Html.fromHtml(welcomemessage));
    }

    @Override
    public void onClick(View v) {
        if(welcomemessageexitflag.equals("1")){
            getActivity().finish();
            System.exit(0);
        }else{
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            dismiss();
            getActivity().finish();
            method.activityOpenAnimation();
        }
    }
}