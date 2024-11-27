package com.trustbank.adapter;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trustbank.Model.DynamicMenuModel;
import com.trustbank.Module.ImageTextMenuModel;
import com.trustbank.R;
import com.trustbank.activity.AccountsActivity;
import com.trustbank.activity.InvestmentMenus;
import com.trustbank.activity.ProfileSettingsActivity;
import com.trustbank.activity.TransactionMenu;
import com.trustbank.activity.SaveMenu;
import com.trustbank.activity.BorrowMenu;
import com.trustbank.activity.VisitUsMenu;
import com.trustbank.util.AppConstants;
import com.trustbank.util.TrustMethods;

import java.util.HashMap;
import java.util.List;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private static final String TAG = MenuAdapter.class.getSimpleName();
    private List<ImageTextMenuModel> itemsData;
    Activity mActivity;
    HashMap<String, List<DynamicMenuModel>> submenumap;
    TrustMethods trustMethods;

    public MenuAdapter(Activity activity, List<ImageTextMenuModel> itemsData, HashMap<String, List<DynamicMenuModel>> submenumap) {
        this.itemsData = itemsData;
        this.mActivity = activity;
        this.submenumap = submenumap;
        trustMethods = new TrustMethods(mActivity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_demo, null);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RelativeLayout linear_menu;
        public TextView txtViewTitle;
        public ImageView imgViewIcon;
        private CardView imgCrdView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewTitle = itemLayoutView.findViewById(R.id.txt_state_name);
            imgViewIcon = itemLayoutView.findViewById(R.id.departmentIV);
            linear_menu = itemLayoutView.findViewById(R.id.linear_menu);
            imgCrdView = itemLayoutView.findViewById(R.id.imgCrdViewId);
        }

        @Override
        public void onClick(View view) {
            switch (itemsData.get(getAdapterPosition()).getItemName().trim()) {

                case "Accounts":
                    if (submenumap.containsKey("mnu_accounts")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_accounts");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=true;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentAccounts = new Intent(view.getContext(), AccountsActivity.class);
                    intentAccounts.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccounts);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Investments":
                    if (submenumap.containsKey("mnu_investment")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_investment");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=true;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentService = new Intent(view.getContext(), InvestmentMenus.class);
                    intentService.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentService);
                    trustMethods.activityOpenAnimation();

                    break;

                case "Transactions":
                    if (submenumap.containsKey("mnu_transactions")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_transactions");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=true;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentFundTransferAccount = new Intent(view.getContext(), TransactionMenu.class);
                    intentFundTransferAccount.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentFundTransferAccount);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Save":
                    if (submenumap.containsKey("mnu_save")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_save");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=true;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentLocate = new Intent(view.getContext(), SaveMenu.class);
                    intentLocate.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLocate);
                    trustMethods.activityOpenAnimation();
                    break;
                case "Borrow":
                    if (submenumap.containsKey("mnu_borrow")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_borrow");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=true;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentBorrow = new Intent(view.getContext(), BorrowMenu.class);
                    intentBorrow.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentBorrow);
                    trustMethods.activityOpenAnimation();

                    break;

                case "Profile Settings":
                    if (submenumap.containsKey("mnu_profile_settings")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_profile_settings");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = true;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentHelp = new Intent(view.getContext(), ProfileSettingsActivity.class);
                    intentHelp.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentHelp);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Visit Us":
                    if (submenumap.containsKey("mnu_visit_us")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_visit_us");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=true;
                        AppConstants.profile_group = false;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentVisitus = new Intent(view.getContext(), VisitUsMenu.class);
                    intentVisitus.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentVisitus);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Cuentas":
                    if (submenumap.containsKey("mnu_accounts")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_accounts");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=true;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentAccounts1 = new Intent(view.getContext(), AccountsActivity.class);
                    intentAccounts1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentAccounts1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Inversiones":
                    if (submenumap.containsKey("mnu_investment")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_investment");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=true;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentService1 = new Intent(view.getContext(), InvestmentMenus.class);
                    intentService1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentService1);
                    trustMethods.activityOpenAnimation();

                    break;

                case "Actas":
                    if (submenumap.containsKey("mnu_transactions")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_transactions");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=true;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentFundTransferAccount1 = new Intent(view.getContext(), TransactionMenu.class);
                    intentFundTransferAccount1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentFundTransferAccount1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Ahorrar":
                    if (submenumap.containsKey("mnu_save")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_save");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=true;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentLocate1 = new Intent(view.getContext(), SaveMenu.class);
                    intentLocate1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentLocate1);
                    trustMethods.activityOpenAnimation();
                    break;


                case "Pedir prestado":
                    if (submenumap.containsKey("mnu_borrow")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_borrow");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=true;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = false;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentBorrow1 = new Intent(view.getContext(), BorrowMenu.class);
                    intentBorrow1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentBorrow1);
                    trustMethods.activityOpenAnimation();

                    break;

                case "Configuración de perfil":
                    if (submenumap.containsKey("mnu_profile_settings")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_profile_settings");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=false;
                        AppConstants.profile_group = true;

                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentHelp1 = new Intent(view.getContext(), ProfileSettingsActivity.class);
                    intentHelp1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentHelp1);
                    trustMethods.activityOpenAnimation();
                    break;

                case "Visítanos":
                    if (submenumap.containsKey("mnu_visit_us")){
                        List<DynamicMenuModel> subMenu = submenumap.get("mnu_visit_us");
                        AppConstants.setSubmenuList(subMenu);
                        AppConstants.account_group=false;
                        AppConstants.investments_group=false;
                        AppConstants.transactions_group=false;
                        AppConstants.save_group=false;
                        AppConstants.borrow_group=false;
                        AppConstants.visitus_group=true;
                        AppConstants.profile_group = false;
                    }else {
                        AppConstants.setSubmenuList(null);
                    }
                    Intent intentVisitus1 = new Intent(view.getContext(), VisitUsMenu.class);
                    intentVisitus1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    view.getContext().startActivity(intentVisitus1);
                    trustMethods.activityOpenAnimation();
                    break;

                default:
                    break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
