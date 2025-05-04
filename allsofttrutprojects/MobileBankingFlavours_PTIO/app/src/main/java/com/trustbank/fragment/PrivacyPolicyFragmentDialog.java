package com.trustbank.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


import com.trustbank.R;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONException;
import org.json.JSONObject;

public class PrivacyPolicyFragmentDialog extends DialogFragment implements AlertDialogOkListener {


    private WebView wv1;
    private ImageView cancelDialogue;


    public static DialogFragment newInstance() {
        PrivacyPolicyFragmentDialog fragment = new PrivacyPolicyFragmentDialog();
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.privacy_policy_fragment, container, false);
        inIt(view);
        return view;
    }

    private void inIt(View view) {
        try {
            cancelDialogue = view.findViewById(R.id.cancelDialogue);
            wv1 = view.findViewById(R.id.termsConditionId);
            String myPdfUrl = TrustURL.getTermsConditions();
            new LoadTermsandConditionsAsyncTask(getActivity(),myPdfUrl).execute();
            cancelDialogue.setOnClickListener(v -> dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDialogOk(int resultCode) {
        try {
            if (resultCode == 1) {

            } else if (resultCode == 2) {
                //Dismiss alert dialog here.
            } else if (resultCode == 3) {
                //Dismiss alert dialog here.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        wv1.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        wv1.onResume();
    }
    @SuppressLint("StaticFieldLeak")
    private class LoadTermsandConditionsAsyncTask extends AsyncTask<Void, Void, String> {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;

        private String errorCode = "";
        private String myPdfUrl;

        public LoadTermsandConditionsAsyncTask(Context ctx, String myPdfUrl) {
            this.ctx = ctx;
            this.myPdfUrl = myPdfUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.loading_wait));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                if (!myPdfUrl.equals("")) {
                    response = HttpClientWrapper.getResponsetermsandcondition(myPdfUrl);
                }
                if (response == null || response.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }

            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {

                if (!this.error.equals("")) {
                    TrustMethods.message(getActivity(), error);
                } else {
                    WebSettings webSettings = wv1.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    wv1.loadDataWithBaseURL(null,response , "text/html", "UTF-8", null);
                }

                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
