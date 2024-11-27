package com.trustbank.adapter;

import static com.trustbank.util.AlertDialogMethod.alertDialogOk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.activity.LockActivity;
import com.trustbank.activity.MenuActivity;
import com.trustbank.activity.PersonalProfileActivity;
import com.trustbank.fragment.BottomSheetFragment;
import com.trustbank.interfaces.AlertDialogListener;
import com.trustbank.interfaces.AlertDialogOkListener;
import com.trustbank.util.AlertDialogMethod;
import com.trustbank.util.AppConstants;
import com.trustbank.util.HttpClientWrapper;
import com.trustbank.util.SessionManager;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class HorizontalMenuAdapter extends RecyclerView.Adapter<HorizontalMenuAdapter.ViewHolder> implements AlertDialogListener {

    private static final String TAG = HorizontalMenuAdapter.class.getSimpleName();
    private List<ImageTextMenuModel> itemsData;
    HashMap<String, List<DynamicMenuModel>> bottomsubmenumap;
    AlertDialogListener alertDialogListener=this;
    Activity mActivity;
    TrustMethods trustMethods;

    public HorizontalMenuAdapter(Activity activity, List<ImageTextMenuModel> itemsData, HashMap<String, List<DynamicMenuModel>> bottomsubmenumap) {
        this.itemsData = itemsData;
        this.mActivity = activity;
        this.bottomsubmenumap = bottomsubmenumap;
        trustMethods = new TrustMethods(mActivity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_adapter_menu_demo, null);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (itemsData.get(position).getLableName() != null && !TextUtils.isEmpty(itemsData.get(position).getLableName())) {
            viewHolder.txtViewTitle.setText(itemsData.get(position).getLableName());
        }
        if (itemsData.get(position).getImage() != 0) {
            viewHolder.imgViewIcon.setImageResource(itemsData.get(position).getImage());
        }


    }

    @Override
    public void onDialogOk(int resultCode) {
        if(resultCode==12){
            new LogoutUserAsyncTask(mActivity).execute();
        }
    }

    @Override
    public void onDialogCancel(int resultCode) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewTitle = itemLayoutView.findViewById(R.id.txt_state_name);
            imgViewIcon = itemLayoutView.findViewById(R.id.departmentIV);
        }

        @Override
        public void onClick(View view) {
            switch (itemsData.get(getAdapterPosition()).getItemName().trim()) {

                case "Home":
                    Intent intentMenu = new Intent(view.getContext(), MenuActivity.class);
                    intentMenu.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentMenu);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Profile":
                    Intent intentAccounts = new Intent(view.getContext(), PersonalProfileActivity.class);
                    intentAccounts.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccounts);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Settings":
                   /* Intent intentWithinBank = new Intent(view.getContext(), WithinBankActivity.class);
                    intentWithinBank.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentWithinBank);
                    trustMethods.activityOpenAnimation();*/
                    break;

                case "Logout":
                    AlertDialogMethod.alertDialog(view.getContext(), "",mActivity.getResources().getString(R.string.logoout),mActivity.getResources().getString(R.string.btn_yes), mActivity.getString(R.string.Cancle),
                            12, false, alertDialogListener);
                    break;

                case "language":
                    BottomSheetFragment fragment = new BottomSheetFragment(mActivity);
                    fragment.show(((FragmentActivity) mActivity).getSupportFragmentManager(),TAG);
                    break;

                case "idioma":
                    BottomSheetFragment fragment1 = new BottomSheetFragment(mActivity);
                    fragment1.show(((FragmentActivity) mActivity).getSupportFragmentManager(),TAG);
                    break;
                case "Hogar":
                    Intent intentMenu1 = new Intent(view.getContext(), MenuActivity.class);
                    intentMenu1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentMenu1);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Perfil":
                    Intent intentAccounts1 = new Intent(view.getContext(), PersonalProfileActivity.class);
                    intentAccounts1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccounts1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Ajustes":
                   /* Intent intentWithinBank = new Intent(view.getContext(), WithinBankActivity.class);
                    intentWithinBank.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentWithinBank);
                    trustMethods.activityOpenAnimation();*/
                    break;

                case "Cerrar sesión":
                    AlertDialogMethod.alertDialog(view.getContext(), "",mActivity.getResources().getString(R.string.logoout),mActivity.getResources().getString(R.string.btn_yes), mActivity.getString(R.string.Cancle),12, false, alertDialogListener);
                    break;

                default:
                    break;
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LogoutUserAsyncTask extends AsyncTask<Void, Void, String> implements AlertDialogOkListener {
        String error = "";
        Context ctx;
        String response;
        ProgressDialog pDialog;
        AlertDialogOkListener alertDialogOkListener=this;
        private SessionManager sessionManager;
        String result;
        private String errorCode;

        public LogoutUserAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ctx);
            pDialog.setMessage("Loading Please wait....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                String url = TrustURL.LogoutUserUrl();

                if (!url.equals("")) {
                    result = HttpClientWrapper.postWitAuthHeader(url, "", AppConstants.getAuth_token());
                }

                if (result == null || result.equals("")) {
                    error = AppConstants.SERVER_NOT_RESPONDING;
                    return error;
                }

                JSONObject jsonResponse = (new JSONObject(result));
                if (jsonResponse.has("error")) {
                    error = jsonResponse.getString("error");
                    return error;
                }

                String responseCode = jsonResponse.has("response_code") ? jsonResponse.getString("response_code") : "NA";
                if (responseCode.equals("1")) {
                    response = "true";
                } else {
                    errorCode = jsonResponse.has("error_code") ? jsonResponse.getString("error_code") : "NA";
                    error = jsonResponse.has("error_message") ? jsonResponse.getString("error_message") : "NA";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            try {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (!this.error.equals("")) {
                    if (TrustMethods.isLogoutSessionExpired(errorCode)) {
                        alertDialogOk(ctx, "Session Expired!",
                                "", "OK", 0, false, alertDialogOkListener);
                    }

                } else {
                    if (response != null) {
                        sessionManager = new SessionManager(ctx);
                        trustMethods.clearAccountsArrayList(ctx);
                        sessionManager.logoutUser();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onDialogOk(int resultCode) {
            if(resultCode==0){
                Intent i = new Intent(mActivity, LockActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ctx.startActivity(i);
            }
        }
    }
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}

