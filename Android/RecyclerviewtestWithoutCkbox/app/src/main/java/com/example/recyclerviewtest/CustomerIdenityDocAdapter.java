//todo type1 using list
/*package com.example.recyclerviewtest;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CustomerIdenityDocAdapter extends RecyclerView.Adapter<CustomerIdenityDocAdapter.ViewHolder> {

    List<String> idprooflist;

    private ArrayList<Boolean> checkedItems;
    private ArrayList<String> referenceNumbers;
    private ArrayList<String> issuingAuthorities;
    private ArrayList<String> expiryDates;
    List<CustomerIdentityDocModel> customerIdentityDocModel;

    public CustomerIdenityDocAdapter(List<String> idprooflist, HashMap<String, CustomerIdentityDocModel> customerDetailsModelHashMap) {
        this.idprooflist = idprooflist;
        referenceNumbers = new ArrayList<>(Collections.nCopies(idprooflist.size(), ""));
        issuingAuthorities = new ArrayList<>(Collections.nCopies(idprooflist.size(), ""));
        expiryDates = new ArrayList<>(Collections.nCopies(idprooflist.size(), ""));
        customerIdentityDocModel = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.documentproofrv, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (idprooflist.get(position) != null) {
            holder.checkIdentityProof.setText(idprooflist.get(position));
        }
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return idprooflist.size();
    }

    public List<CustomerIdentityDocModel> getData() {
        return customerIdentityDocModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView checkIdentityProof;
        EditText edittextDocReferenceNo, editTextIssuingAuthority, editTextExpirydate;
        private int position;
        CustomerIdentityDocModel cust = new CustomerIdentityDocModel();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkIdentityProof = itemView.findViewById(R.id.checkIdentityProof);
            edittextDocReferenceNo = itemView.findViewById(R.id.edittextDocReferenceNo);
            editTextIssuingAuthority = itemView.findViewById(R.id.editTextIssuingAuthority);
            editTextExpirydate = itemView.findViewById(R.id.editTextExpirydate);

            // TextChangedListener for reference number
            edittextDocReferenceNo.addTextChangedListener(new TextWatcherAdapter() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().equalsIgnoreCase("") ) {
                        referenceNumbers.set(position, s.toString());
                        updateModelData(position);
                    }else if(before==1){
                        referenceNumbers.set(position,"");
                        updateModelData(position);
                    }
                }
            });

            // TextChangedListener for issuing authority
            editTextIssuingAuthority.addTextChangedListener(new TextWatcherAdapter() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().equalsIgnoreCase("") ) {
                        issuingAuthorities.set(position, s.toString());
                        updateModelData(position);
                    }else if(before==1){
                        issuingAuthorities.set(position,"");
                        updateModelData(position);
                    }
                }
            });

            // TextChangedListener for expiry date
            editTextExpirydate.addTextChangedListener(new TextWatcherAdapter() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().equalsIgnoreCase("")) {
                        expiryDates.set(position, s.toString());
                        updateModelData(position);
                    }else if(before==1){
                        expiryDates.set(position,"");
                        updateModelData(position);
                    }
                }
            });
        }

        public void bind(int position) {
            this.position = position;
            edittextDocReferenceNo.setText(referenceNumbers.get(position));
            editTextIssuingAuthority.setText(issuingAuthorities.get(position));
            editTextExpirydate.setText(expiryDates.get(position));
        }

        private void updateModelData(int position) {
            String checkboxName;
            checkboxName = idprooflist.get(position);
            cust.setCkname(checkboxName);
            cust.setDocrefNo(referenceNumbers.get(position));
            cust.setIssuingAuth(issuingAuthorities.get(position));
            cust.setExpiryDate(expiryDates.get(position));
            // Remove previous occurrence of the item to avoid duplicates
            customerIdentityDocModel.remove(cust);
            customerIdentityDocModel.add(cust);
        }

        private void clearModelData() {
            referenceNumbers.set(position, "");
            issuingAuthorities.set(position, "");
            expiryDates.set(position, "");
            edittextDocReferenceNo.setText("");
            editTextIssuingAuthority.setText("");
            editTextExpirydate.setText("");
            customerIdentityDocModel.clear();
        }
    }

    // Custom TextWatcherAdapter to reduce boilerplate code
    private abstract class TextWatcherAdapter implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}*/

//todo type 2 using HashMAP

package com.example.recyclerviewtest;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.EditText;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;
public class CustomerIdenityDocAdapter extends RecyclerView.Adapter<CustomerIdenityDocAdapter.ViewHolder> {
    List<String> idprooflist;

    private ArrayList<String> referenceNumbers;
    private ArrayList<String> issuingAuthorities;
    private ArrayList<String> expiryDates;
    HashMap<String, CustomerIdentityDocModel> customerDetailsModelHashMap;


    public CustomerIdenityDocAdapter(List<String> idprooflist, HashMap<String, CustomerIdentityDocModel> customerDetailsModelHashMap) {
        this.idprooflist = idprooflist;
        this.customerDetailsModelHashMap = customerDetailsModelHashMap;
//        checkedItems = new ArrayList<>(Collections.nCopies(idprooflist.size(), false));
        referenceNumbers = new ArrayList<>(Collections.nCopies(idprooflist.size(), ""));
        issuingAuthorities = new ArrayList<>(Collections.nCopies(idprooflist.size(), ""));
        expiryDates = new ArrayList<>(Collections.nCopies(idprooflist.size(), ""));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.documentproofrv, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (idprooflist.get(position) != null) {
            holder.checkIdentityProof.setText(idprooflist.get(position));
        }
        holder.bind(position);
        holder.edittextDocReferenceNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                referenceNumbers.set(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        holder.editTextIssuingAuthority.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                issuingAuthorities.set(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        holder.editTextExpirydate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                expiryDates.set(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return idprooflist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView checkIdentityProof;
        String checkboxName;
        EditText edittextDocReferenceNo, editTextIssuingAuthority, editTextExpirydate;
        private int position;
        CustomerIdentityDocModel customerIdentityDocModel = new CustomerIdentityDocModel();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkIdentityProof = itemView.findViewById(R.id.checkIdentityProof);
            edittextDocReferenceNo = itemView.findViewById(R.id.edittextDocReferenceNo);
            editTextIssuingAuthority = itemView.findViewById(R.id.editTextIssuingAuthority);
            editTextExpirydate = itemView.findViewById(R.id.editTextExpirydate);


            edittextDocReferenceNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().equalsIgnoreCase("") ) {
                        referenceNumbers.set(position, s.toString());
                        customerIdentityDocModel.setDocrefNo(referenceNumbers.get(position));
                        customerDetailsModelHashMap.put(idprooflist.get(position), customerIdentityDocModel);
                    }else if(before==1){
                        referenceNumbers.set(position,"");
                        customerIdentityDocModel.setDocrefNo(referenceNumbers.get(position));
                        customerDetailsModelHashMap.put(idprooflist.get(position), customerIdentityDocModel);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editTextIssuingAuthority.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().equalsIgnoreCase("") ) {
                        issuingAuthorities.set(position, s.toString());
                        customerIdentityDocModel.setIssuingAuth(issuingAuthorities.get(position));
                        customerDetailsModelHashMap.put(idprooflist.get(position), customerIdentityDocModel);
                    }else if(before==1){
                        issuingAuthorities.set(position, s.toString());
                        customerIdentityDocModel.setIssuingAuth(issuingAuthorities.get(position));
                        customerDetailsModelHashMap.put(idprooflist.get(position), customerIdentityDocModel);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editTextExpirydate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().equalsIgnoreCase("") ) {
                        expiryDates.set(position, s.toString());
                        customerIdentityDocModel.setExpiryDate(expiryDates.get(position));
                        customerDetailsModelHashMap.put(idprooflist.get(position), customerIdentityDocModel);
                    }else if(before==1){
                        expiryDates.set(position, s.toString());
                        customerIdentityDocModel.setExpiryDate(expiryDates.get(position));
                        customerDetailsModelHashMap.put(idprooflist.get(position), customerIdentityDocModel);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

        }

        public void bind(int position) {
            this.position = position;
            edittextDocReferenceNo.setText(referenceNumbers.get(position));
            editTextIssuingAuthority.setText(issuingAuthorities.get(position));
            editTextExpirydate.setText(expiryDates.get(position));
        }
    }
}
