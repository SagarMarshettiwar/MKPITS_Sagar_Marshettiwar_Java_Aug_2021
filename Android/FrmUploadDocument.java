package com.trustbank.activity;

import static com.trustbank.util.TrustMethods.LogMessage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trustbank.R;
import com.trustbank.interfaceclass.AlertDialogOkListener;
import com.trustbank.model.AccountDetailsModel;
import com.trustbank.model.ClientManagementModel;
import com.trustbank.model.DocumentModel;
import com.trustbank.util.ConnectionDetector;
import com.trustbank.util.Controller;
import com.trustbank.util.GlobalVarHolder;
import com.trustbank.util.HttpClientCall;
import com.trustbank.util.NetworkUtil;
import com.trustbank.util.RwProgressDialog;
import com.trustbank.util.TrustMethods;
import com.trustbank.util.TrustURL;
import com.trustbank.util.ViewUploadDocuments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FrmUploadDocument extends AppCompatActivity implements View.OnClickListener, AlertDialogOkListener
{
    private RwProgressDialog rwProgressDialog;
    ArrayList<String> branchlist=new ArrayList<>();
    HashMap<String,String> branchmap=new HashMap<>();
    ArrayList<ClientManagementModel> idprooflist=new ArrayList<>();
    HashMap<String,String> idproofmap=new HashMap<>();
    ArrayList<String> iddocumentlist=new ArrayList<>();
    HashMap<String,String> nonidproofmap=new HashMap<>();
    ArrayList<String> noniddocumentlist=new ArrayList<>();
    HashMap<String,String> corraddmap=new HashMap<>();
    ArrayList<String> addrdocumentlist=new ArrayList<>();
    HashMap<String,String> noncorraddmap=new HashMap<>();
    ArrayList<String> nonaddrdocumentlist=new ArrayList<>();
    ArrayList<ClientManagementModel> corraddlist=new ArrayList<>();
    ArrayList<ClientManagementModel> peraddlist=new ArrayList<>();
    ArrayList<String> permaddrdocumentlist=new ArrayList<>();
    HashMap<String,String> peraddmap=new HashMap<>();
    TrustMethods trustMethods;
    EditText customerIDEditText;
    TextView customerNameTextView;
    Spinner spinner_purpose, spinner_doc_type, spinner_account;
    Button goButton, button_camera, button_gallary,button_submit,button_add,button_view;
    ImageView doc_image;
    LinearLayout doc_type_layout,doc_purpose_layout,account_layout,button_layout, add_button_layout;

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;

    public List<DocumentModel> documentModelList = new ArrayList<DocumentModel>();
    public List<AccountDetailsModel> accountDetailsModelList = new ArrayList<AccountDetailsModel>();

    boolean isImageSelected=false;
    AlertDialogOkListener alertDialogOkListener=this;
    String clientType="",purposeSelected="",requiredImageSize="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_upload_document);
        init();
        new LookUpAsyncTask(this).execute();
    }

    private void init()
    {
        trustMethods = new TrustMethods(FrmUploadDocument.this);
        customerIDEditText=findViewById(R.id.customerIDEditText);
        customerNameTextView=findViewById(R.id.customerNameTextView);
        goButton=findViewById(R.id.goButton);
        button_camera=findViewById(R.id.button_camera);
        button_gallary=findViewById(R.id.button_gallary);
        button_submit=findViewById(R.id.button_submit);
        button_add=findViewById(R.id.button_add);
        button_view=findViewById(R.id.button_view);
        goButton.setOnClickListener(this);
        button_camera.setOnClickListener(this);
        button_gallary.setOnClickListener(this);
        button_submit.setOnClickListener(this);
        button_add.setOnClickListener(this);
        button_view.setOnClickListener(this);

        doc_image=findViewById(R.id.doc_image);
        spinner_purpose=findViewById(R.id.spinner_purpose);
        spinner_doc_type=findViewById(R.id.spinner_doc_type);
        spinner_account=findViewById(R.id.spinner_account);
        doc_type_layout=findViewById(R.id.doc_type_layout);
        doc_purpose_layout=findViewById(R.id.doc_purpose_layout);
        account_layout=findViewById(R.id.account_layout);
        button_layout=findViewById(R.id.button_layout);
        add_button_layout=findViewById(R.id.add_button_layout);

        List<String> documentPurpose=new ArrayList<>();
        documentPurpose.add("Select Document Purpose");
        documentPurpose.add("Photo");
        documentPurpose.add("Signature");
        documentPurpose.add("Id Document");
        documentPurpose.add("Address Document");

        ArrayAdapter<String> spinnergltypeArrayAdapter = new ArrayAdapter<String>(FrmUploadDocument.this, android.R.layout.simple_spinner_item, documentPurpose);
        spinnergltypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_purpose.setAdapter(spinnergltypeArrayAdapter);

        spinner_purpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try
                {
                    if (position != 0)
                    {
                        String Selected= (String) parent.getItemAtPosition(position);
                        purposeSelected=Selected;
                        if(Selected.equalsIgnoreCase("Photo"))
                        {
                            doc_type_layout.setVisibility(View.GONE);
                            requiredImageSize=GlobalVarHolder.getCkyc_photo_size();
                        }
                        else if(Selected.equalsIgnoreCase("Signature"))
                        {
                            doc_type_layout.setVisibility(View.GONE);
                            requiredImageSize=GlobalVarHolder.getIndv_ckyc_batch_size();
                        }
                        else if(Selected.equalsIgnoreCase("Id Document"))
                        {
                            doc_type_layout.setVisibility(View.VISIBLE);
                            SetDocumentSpinner("ID");
                            requiredImageSize=GlobalVarHolder.getIndv_ckyc_batch_size();
                        }
                        else  if(Selected.equalsIgnoreCase("Address Document"))
                        {
                            doc_type_layout.setVisibility(View.VISIBLE);
                            SetDocumentSpinner("ADD");
                            requiredImageSize=GlobalVarHolder.getIndv_ckyc_batch_size();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void SetDocumentSpinner(String docType)
    {
        ArrayAdapter<String> idDocumentAdapter;
        if(docType.equals("ID"))
        {
            if(clientType.equals("0"))
                idDocumentAdapter = new ArrayAdapter<String>(FrmUploadDocument.this, android.R.layout.simple_spinner_item, iddocumentlist);
            else
                idDocumentAdapter = new ArrayAdapter<String>(FrmUploadDocument.this, android.R.layout.simple_spinner_item, noniddocumentlist);
        }
        else
        {
            if(clientType.equals("0"))
                idDocumentAdapter = new ArrayAdapter<String>(FrmUploadDocument.this, android.R.layout.simple_spinner_item, addrdocumentlist);
            else
                idDocumentAdapter = new ArrayAdapter<String>(FrmUploadDocument.this, android.R.layout.simple_spinner_item, nonaddrdocumentlist);
        }
        idDocumentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_doc_type.setAdapter(idDocumentAdapter);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.goButton)
        {
            String customerID = customerIDEditText.getText().toString();
            if (customerID.equals(""))
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_enter_customer_id),
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (customerID.equals(customerID))
            {
                if (NetworkUtil.getConnectivity(FrmUploadDocument.this))
                {
                    new AsyncTaskGetData(FrmUploadDocument.this
                            , GlobalVarHolder.getAgentOrg_ELEMENTID()
                            , GlobalVarHolder.getAgent_ID(), customerID).execute();
                }
                else
                {
                    Toast.makeText(this, getResources().getString(R.string.text_check_internet), Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_invalid_cust_id), Toast.LENGTH_LONG).show();
            }
        }
        else if(v.getId()==R.id.button_camera)
        {
            if(purposeSelected.length()==0 || purposeSelected.equals("Select Document Purpose"))
            {
                Toast.makeText(this, "Please Select Document Purpose", Toast.LENGTH_SHORT).show();
            }
            else {
                isImageSelected = false;
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        }
        else if(v.getId()==R.id.button_gallary)
        {
            if(purposeSelected.length()==0 || purposeSelected.equals("Select Document Purpose"))
            {
                Toast.makeText(this, "Please Select Document Purpose", Toast.LENGTH_SHORT).show();
            }
            else {
                isImageSelected = false;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        }
        else if(v.getId()==R.id.button_submit)
        {

                /*try {
                    StringWriter writer = new StringWriter();
                    // Create an XmlSerializer

                    // Start the root element

                    // Start account_details
                    for(int m=0;m<=documentModelList.size()-1;m++){
                        XmlSerializer xmlSerializer = Xml.newSerializer();
                        xmlSerializer.setOutput(writer);
                        xmlSerializer.startDocument("UTF-8", true);
                        xmlSerializer.startTag("", "Documents");
                        xmlSerializer.startTag("", "DocumentDetails");

                        xmlSerializer.startTag("", "AccountNo");
                        xmlSerializer.text(documentModelList.get(m).getAccountNo());
                        xmlSerializer.endTag("", "AccountNo");

                        xmlSerializer.startTag("", "CustomerId");
                        xmlSerializer.text(documentModelList.get(m).getCustomerId());
                        xmlSerializer.endTag("", "CustomerId");

                        xmlSerializer.startTag("", "DocumentImageBase64");
                        xmlSerializer.text(documentModelList.get(m).getDocumentencreptedImage());
                        xmlSerializer.endTag("", "DocumentImageBase64");

                        xmlSerializer.startTag("", "DocumentName");
                        xmlSerializer.text(documentModelList.get(m).getDocumentType());
                        xmlSerializer.endTag("", "DocumentName");

                        xmlSerializer.startTag("", "DocumentTypeId");
                        xmlSerializer.text(documentModelList.get(m).getDocumentNameid());
                        xmlSerializer.endTag("", "DocumentTypeId");

                        xmlSerializer.endTag("", "DocumentDetails");
                        xmlSerializer.endTag("", "Documents");
                        xmlSerializer.endDocument();
                        String xmlString = writer.toString();
                        if (ConnectionDetector.isConnectingToInternet(FrmUploadDocument.this)) {
                            new SaveDetailData(FrmUploadDocument.this, xmlString).execute();
                        } else {
                            TrustMethods.message(FrmUploadDocument.this, getResources().getString(R.string.error_check_internet));
                        }
                    }

                    //Log.e("xml", xmlString);


                }catch(Exception e){
                    e.printStackTrace();
                }*/
                DocumentModel documentModel = new DocumentModel();
                if(customerIDEditText.getText().toString().length()==0)
                {
                    Toast.makeText(this,"Enter Customer Id", Toast.LENGTH_LONG).show();
                }
                else if(spinner_purpose.getSelectedItemPosition()==0)
                {
                    Toast.makeText(this,"Select Purpose", Toast.LENGTH_LONG).show();
                }
                else if(spinner_purpose.getSelectedItemPosition()>2 && spinner_doc_type.getSelectedItemPosition()==0)
                {
                    Toast.makeText(this,"Select Document", Toast.LENGTH_LONG).show();
                }
                else if(!isImageSelected)
                {
                    Toast.makeText(this,"Select Document Image", Toast.LENGTH_LONG).show();
                }
                else if(GlobalVarHolder.getAccount_client_wise_sign().equals("0") && spinner_account.getSelectedItemPosition()==0)
                {
                    Toast.makeText(this,"Select Account Number", Toast.LENGTH_LONG).show();
                }
                else
                {
                    documentModel.setCustomerId(customerIDEditText.getText().toString());
                    documentModel.setDocumentType(spinner_purpose.getSelectedItem().toString());
                    if (spinner_purpose.getSelectedItemPosition() > 2) {
                        documentModel.setDocumentName(spinner_doc_type.getSelectedItem().toString());
                        if(clientType.equals("0") && spinner_purpose.getSelectedItemPosition() == 3){
                            documentModel.setDocumentNameid(idproofmap.get(spinner_doc_type.getSelectedItem().toString()));
                        }else if(clientType.equals("1") && spinner_purpose.getSelectedItemPosition() == 3){
                            documentModel.setDocumentNameid(nonidproofmap.get(spinner_doc_type.getSelectedItem().toString()));
                        }

                        if(clientType.equals("0") && spinner_purpose.getSelectedItemPosition() == 4){
                            documentModel.setDocumentNameid(corraddmap.get(spinner_doc_type.getSelectedItem().toString()));
                        }else if(clientType.equals("1") && spinner_purpose.getSelectedItemPosition() == 4){
                            documentModel.setDocumentNameid(noncorraddmap.get(spinner_doc_type.getSelectedItem().toString()));
                        }
                    }else{
                        documentModel.setDocumentName("");
                        documentModel.setDocumentNameid("");
                    }

                    documentModel.setDocumentImage(((BitmapDrawable) doc_image.getDrawable()).getBitmap());
                    documentModel.setDocumentencreptedImage(bitmapToBase64(((BitmapDrawable) doc_image.getDrawable()).getBitmap()));

                    if (GlobalVarHolder.getAccount_client_wise_sign().equals("1") && spinner_account.getSelectedItemPosition() > 0)
                        documentModel.setAccountNo(spinner_account.getSelectedItem().toString());
                    documentModelList.add(documentModel);

                    if (ConnectionDetector.isConnectingToInternet(FrmUploadDocument.this)) {
                        new SaveDetailData(FrmUploadDocument.this, documentModelList).execute();
                    } else {
                        TrustMethods.message(FrmUploadDocument.this, getResources().getString(R.string.error_check_internet));
                    }


            }
        }
       /* else if(v.getId()==R.id.button_add)
        {
            */
        /*DocumentModel documentModel = new DocumentModel();
            if(customerIDEditText.getText().toString().length()==0)
            {
                Toast.makeText(this,"Enter Customer Id", Toast.LENGTH_LONG).show();
            }
            else if(spinner_purpose.getSelectedItemPosition()==0)
            {
                Toast.makeText(this,"Select Purpose", Toast.LENGTH_LONG).show();
            }
            else if(spinner_purpose.getSelectedItemPosition()>2 && spinner_doc_type.getSelectedItemPosition()==0)
            {
                Toast.makeText(this,"Select Document", Toast.LENGTH_LONG).show();
            }
            else if(!isImageSelected)
            {
                Toast.makeText(this,"Select Document Image", Toast.LENGTH_LONG).show();
            }
            else if(GlobalVarHolder.getAccount_client_wise_sign().equals("0") && spinner_account.getSelectedItemPosition()==0)
            {
                Toast.makeText(this,"Select Account Number", Toast.LENGTH_LONG).show();
            }
            else
            {
                documentModel.setCustomerId(customerIDEditText.getText().toString());
                documentModel.setDocumentType(spinner_purpose.getSelectedItem().toString());
                if (spinner_purpose.getSelectedItemPosition() > 2) {
                    documentModel.setDocumentName(spinner_doc_type.getSelectedItem().toString());
                    if(clientType.equals("0") && spinner_purpose.getSelectedItemPosition() == 3){
                            documentModel.setDocumentNameid(idproofmap.get(spinner_doc_type.getSelectedItem().toString()));
                    }else if(clientType.equals("1") && spinner_purpose.getSelectedItemPosition() == 3){
                            documentModel.setDocumentNameid(nonidproofmap.get(spinner_doc_type.getSelectedItem().toString()));
                    }

                    if(clientType.equals("0") && spinner_purpose.getSelectedItemPosition() == 4){
                        documentModel.setDocumentNameid(corraddmap.get(spinner_doc_type.getSelectedItem().toString()));
                    }else if(clientType.equals("1") && spinner_purpose.getSelectedItemPosition() == 4){
                        documentModel.setDocumentNameid(noncorraddmap.get(spinner_doc_type.getSelectedItem().toString()));
                    }
                }else{
                    documentModel.setDocumentName("");
                    documentModel.setDocumentNameid("");
                }
                    documentModel.setDocumentImage(((BitmapDrawable) doc_image.getDrawable()).getBitmap());
                    documentModel.setDocumentencreptedImage(bitmapToBase64(((BitmapDrawable) doc_image.getDrawable()).getBitmap()));

                if (GlobalVarHolder.getAccount_client_wise_sign().equals("1") && spinner_account.getSelectedItemPosition() > 0)
                    documentModel.setAccountNo(spinner_account.getSelectedItem().toString());
                    documentModelList.add(documentModel);

                clearForm();
            }*//*
        }
        else if(v.getId()==R.id.button_view)
        {
            if(documentModelList.size()>0)
            {
                ViewUploadDocuments viewDocumentDialog = new ViewUploadDocuments(this, documentModelList);
                viewDocumentDialog.show();
            }
        }*/
    }

    public void clearForm()
    {
        spinner_purpose.setSelection(0);
        spinner_doc_type.setSelection(0);
        spinner_account.setSelection(0);
        doc_image.setImageBitmap(null);
        documentModelList.clear();
        //Toast.makeText(this, "Document Added Successfully",Toast.LENGTH_SHORT).show();
    }

    // Convert Bitmap to Base64 string
    public static String bitmapToBase64(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onDialogOk(int resultCode)
    {
        if(resultCode==1)
        {
            Intent intent = new Intent(FrmUploadDocument.this, FrmMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private class LookUpAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private String _error = "";
        private Context ctx;
        private String result;
        private String action="GET_LOOKUPS";
        JSONObject responseData;
        public LookUpAsyncTask(Context ctx)
        {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            rwProgressDialog = TrustMethods.showDialog(FrmUploadDocument.this);
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            String url= TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);

            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }
            String jsonString = "{\"agent_id\":\"" + GlobalVarHolder.Agent_id + "\", \"payload_base64\":\"" + "PGRhdGE+CiAgICA8bG9va3Vwcz4gICAgICAgIAogICAgICAgIDxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPkJSQU5DSDwvY29kZT4KICAgICAgICA8L2xvb2t1cD4gICAgICAgIAogICAgICAgIDxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPkRPQ19UWVBFX0lEX1BST09GPC9jb2RlPgogICAgICAgIDwvbG9va3VwPgogICAgICAgIDxsb29rdXA+CiAgICAgICAgICAgIDxjb2RlPkRPQ19UWVBFX0FERFJFU1NfQ09SUkVTUE9OREVOQ0U8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+RE9DX1RZUEVfQUREUkVTU19QRVJNQU5FTlQ8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+Tk9OX0lORElfRE9DX1RZUEVfQUREUkVTU19QRVJNQU5FTlQ8L2NvZGU+CiAgICAgICAgPC9sb29rdXA+CiAgICAgICAgPGxvb2t1cD4KICAgICAgICAgICAgPGNvZGU+Tk9OX0lORElfRE9DX1RZUEVfQUREUkVTU19DT1JSRVNQT05ERU5DRTwvY29kZT4KICAgICAgICA8L2xvb2t1cD4KICAgICAgICA8bG9va3VwPgogICAgICAgICAgICA8Y29kZT5OT05fSU5ESV9ET0NfVFlQRV9JRF9QUk9PRjwvY29kZT4KICAgICAgICA8L2xvb2t1cD4gICAgICAgIAogICAgPC9sb29rdXBzPgo8L2RhdGE+Cg==" + "\"}";
            try
            {
                result = HttpClientCall.postlookup(url,jsonString,action);
                Log.e("result",result);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if(responseCode.equals("1")){
                    responseData = jsonObject.getJSONObject("response").getJSONObject("data");
                } else {
                    String errorCode = responseData.has("error_code") ? responseData.getString("error_code") : "NA";
                    this._error = responseData.has("error_message") ? responseData.getString("error_message") : "NA";
                }
                if(responseData!=null)
                {
                    JSONArray listdocArray = responseData.getJSONObject("list_doc_type_id_proof").getJSONArray("doc_type_id_proof");
                    idproofmap=new HashMap<>();
                    iddocumentlist=new ArrayList<>();
                    iddocumentlist.add("Select Id Document");

                    for (int i = 0; i < listdocArray.length(); i++) {
                        JSONObject document = listdocArray.getJSONObject(i);
                        String valueField = document.getString("value_field");
                        String textField = document.getString("text_field");

                        iddocumentlist.add(textField);
                        idproofmap.put(textField, valueField);
                    }

                    JSONArray corraddressArray = responseData.getJSONObject("list_doc_type_address_correspondence").getJSONArray("doc_type_address_correspondence");
                    corraddmap=new HashMap<>();
                    addrdocumentlist=new ArrayList<>();
                    addrdocumentlist.add("Select Document");

                    for (int i = 0; i < corraddressArray.length(); i++) {
                        JSONObject corradd = corraddressArray.getJSONObject(i);
                        String valueField = corradd.getString("value_field");
                        String textField = corradd.getString("text_field");

                        addrdocumentlist.add(textField);
                        corraddmap.put(textField, valueField);
                    }

                    JSONArray nicorraddressArray = responseData.getJSONObject("list_non_indi_doc_type_address_correspondence").getJSONArray("doc_type_address_correspondence");
                    noncorraddmap=new HashMap<>();
                    nonaddrdocumentlist=new ArrayList<>();
                    nonaddrdocumentlist.add("Select Document");
                    for (int i = 0; i < nicorraddressArray.length(); i++) {
                        JSONObject nicorradd = nicorraddressArray.getJSONObject(i);
                        String valueField = nicorradd.getString("value_field");
                        String textField = nicorradd.getString("text_field");

                        nonaddrdocumentlist.add(textField);
                        noncorraddmap.put(textField, valueField);
                    }

                    JSONArray nidocidproogArray = responseData.getJSONObject("list_non_indi_doc_type_id_proof").getJSONArray("doc_type_id_proof");

                    nonidproofmap=new HashMap<>();
                    noniddocumentlist=new ArrayList<>();
                    noniddocumentlist.add("Select Id Document");
                    for (int i = 0; i < nidocidproogArray.length(); i++)
                    {
                        JSONObject nidocproof = nidocidproogArray.getJSONObject(i);
                        String valueField = nidocproof.getString("value_field");
                        String textField = nidocproof.getString("text_field");

                        noniddocumentlist.add(textField);
                        nonidproofmap.put(textField, valueField);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void value)
        {
            super.onPostExecute(value);
            if (rwProgressDialog.isShowing()) {
                rwProgressDialog.dismiss();
            }

            if (this._error != "" && !TextUtils.isEmpty(_error)) {
                Toast.makeText(this.ctx, this._error, Toast.LENGTH_SHORT).show();
                LogMessage("error", this._error);
                return;
            }
        }
    }

    private class AsyncTaskGetData extends AsyncTask<String, String, String> {
        private RwProgressDialog rwProgressDialog;
        Context ctx;
        String displayName;
        String mAgentOrgElementId;
        String mAgentId;
        String mClientId;
        String result;
        String action ="GET_CLIENT_ACCOUNTS";
        JSONObject response;
        String _error;

        public AsyncTaskGetData(Context ctx, String agentOrgElementId, String agentId, String clientId) {
            this.ctx = ctx;
            this.mAgentOrgElementId = agentOrgElementId;
            this.mAgentId = agentId;
            this.mClientId = clientId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rwProgressDialog = TrustMethods.showDialog(ctx);
        }

        @Override
        protected String doInBackground(String... params)
        {
            try {
                String url = TrustURL.LookUpURL();
                JSONObject jObj = new JSONObject();
                try {
                    jObj.put("p_clientId", mClientId);
                    jObj.put("p_agentId", mAgentId);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                LogMessage("Cust Details: ", "URL:-" + url);
                String jsonString = jObj.toString();
                result = HttpClientCall.postlookup(url, jsonString, action);
                Log.e("result", result);
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.getString("response_code");
                if (responseCode.equals("1")) {
                    try
                    {
                        response = jsonObject.getJSONObject("response").getJSONObject("misc");
                        LogMessage("Cust Details: ", "response::::::-" + response.getString("@p_out_xml"));

                        // Create a DocumentBuilder
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

                        // Parse the XML string to create a Document
                        Document doc = dBuilder.parse(new InputSource(new StringReader(response.getString("@p_out_xml"))));

                        // Normalize the document
                        doc.getDocumentElement().normalize();

                        // Get the root element
                        Element root = doc.getDocumentElement();
                        System.out.println("Root element :" + root.getNodeName());

                        // Get DisplayName
                        displayName = getTextContent(root, "DisplayName");
                        System.out.println("DisplayName: " + displayName);

                        clientType = getTextContent(root, "ClientType");
                        System.out.println("DisplayName: " + clientType);
                        // Get accounts
                        NodeList accountList = root.getElementsByTagName("account");
                        System.out.println("----------------------------");
                        if(accountList.getLength()>0){
                            for (int i = 0; i < accountList.getLength(); i++) {
                                Node accountNode = accountList.item(i);
                                if (accountNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element accountElement = (Element) accountNode;
                        
                                    AccountDetailsModel accountDetailsModel = new AccountDetailsModel();
                                    accountDetailsModel.setAcno(getTextContent(accountElement, "acno"));
                                    accountDetailsModel.setName(getTextContent(accountElement, "name"));
                                    accountDetailsModel.setAc_type(getTextContent(accountElement, "ac_type"));
                                    accountDetailsModel.setAc_status(getTextContent(accountElement, "ac_status"));
                                    accountDetailsModel.setAc_type_code(getTextContent(accountElement, "ac_type_code"));
                                    accountDetailsModel.setHead_code(getTextContent(accountElement, "head_code"));
                                    accountDetailsModelList.add(accountDetailsModel);
                                }
                            }
                        }else{
                            _error = "Client ID not belong to Agent ID Account Details not Found";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String errorCode = jsonObject.has("error_code") ? jsonObject.getString("error_code") : "NA";
                    _error = jsonObject.has("error_message") ? jsonObject.getString("error_message") : "NA";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                if (rwProgressDialog.isShowing()) {
                    rwProgressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            customerNameTextView.setVisibility(View.VISIBLE);
            customerNameTextView.setText(displayName);
            doc_type_layout.setVisibility(View.VISIBLE);
            doc_purpose_layout.setVisibility(View.VISIBLE);
            if(GlobalVarHolder.getAccount_client_wise_sign().equals("1"))
                account_layout.setVisibility(View.VISIBLE);
            else
                account_layout.setVisibility(View.GONE);
            button_layout.setVisibility(View.VISIBLE);
            doc_image.setVisibility(View.VISIBLE);
            button_submit.setVisibility(View.VISIBLE);
           // add_button_layout.setVisibility(View.VISIBLE);

            if(accountDetailsModelList.size()>0)
            {
                List<String> accounts=new ArrayList<>();
                accounts.add("Select Account Number");
                for(int i=0;i<accountDetailsModelList.size();i++)
                {
                    accounts.add(accountDetailsModelList.get(i).getAcno());
                }

                ArrayAdapter<String> spinnerAccountAdapter = new ArrayAdapter<String>(FrmUploadDocument.this, android.R.layout.simple_spinner_item, accounts);
                spinnerAccountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_account.setAdapter(spinnerAccountAdapter);
            }
            else
                account_layout.setVisibility(View.GONE);
            if(_error != null){
                Toast.makeText(FrmUploadDocument.this, ""+_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static String getTextContent(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent().trim();
        }
        return "";
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Controller.workThread.disconnectBt();
        Intent intent = new Intent(FrmUploadDocument.this, FrmMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);
        trustMethods.activityOpenAnimation();
        finish();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
            doc_image.setImageBitmap(selectedImage);
            isImageSelected=true;
        }*/
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap selectedImage = null;
            try {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        selectedImage = (Bitmap) extras.get("data");
                    }
                }
                if (selectedImage != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 25, stream); // 50 is the quality percentage (0-100)
                    doc_image.setImageBitmap(selectedImage);
                    isImageSelected = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            try
            {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());

                if(selectedImage!=null)
                {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    if (purposeSelected.equals("Photo") &&  (byteArray.length / 1024) > Double.parseDouble(requiredImageSize.split("-")[0]) &&  (byteArray.length / 1024) <= Double.parseDouble(requiredImageSize.split("-")[1]))
                    {
                        doc_image.setImageBitmap(selectedImage);
                        isImageSelected=true;
                    }
                    else if ((purposeSelected.equals("ID") || purposeSelected.equals("Signature") || purposeSelected.equals("ADD")) &&  (byteArray.length / 1024) <= Double.parseDouble(requiredImageSize))
                    {
                        doc_image.setImageBitmap(selectedImage);
                        isImageSelected=true;
                    }
                    else
                    {
                        Toast.makeText(this, "Select Image With "+requiredImageSize+" KB Size", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class SaveDetailData extends AsyncTask<Void, Void, Void> {
        private String _error = "";
        private Context ctx;
        String xmlString;
        private String result;
        private String action="UPLOAD_DOCUMENT";
        JSONObject responseData;
        String customerId = null;
        String accountNumber = null;
        List<DocumentModel> list;
        String requestId = "Upload Again";

        public SaveDetailData(Context ctx, List<DocumentModel> list) {
            this.ctx=ctx;
            this.list=list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rwProgressDialog = TrustMethods.showDialog(FrmUploadDocument.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url=TrustURL.LookUpURL();
            TrustMethods.systemMessage("URL:-" + url);

            if (url.equals("")) {
                this._error = "Error while building service url.";
                return null;
            }
            // Add fields to the JsonObject
            try {
                if(list.size()>0) {
                    JSONObject obj = new JSONObject();
                    obj.put("agent_id", GlobalVarHolder.Agent_id);
                    obj.put("image_base64", list.get(0).getDocumentencreptedImage());
                    obj.put("customer_id", list.get(0).getCustomerId());
                    obj.put("documentType_Id", list.get(0).getDocumentNameid());
                    String jsonString = String.valueOf(obj);

                    // Convert JsonObject to JSON string
                    TrustMethods.systemMessage("jsonString:-" + jsonString);
                    result = HttpClientCall.postlookup(url, jsonString, action);
                    if (!result.equals("") && result != null) {
                        JSONObject jsonObject = new JSONObject(result);
                        String responseCode = jsonObject.getString("response_code");
                        if (responseCode.equals("1")) {
                            requestId = "Documents Uploaded Successfully....!";
                        } else {
                            String errorCode = jsonObject.has("error_code") ? jsonObject.getString("error_code") : "NA";
                            _error = jsonObject.has("error_message") ? jsonObject.getString("error_message") : "NA";
                        }
                    } else {
                        requestId = "Something went wrong....!";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            if (rwProgressDialog.isShowing()) {
                rwProgressDialog.dismiss();
            }
            if(!_error.equalsIgnoreCase("")){
                Toast.makeText(FrmUploadDocument.this, _error, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(FrmUploadDocument.this, requestId, Toast.LENGTH_SHORT).show();
                list.clear();
                clearForm();
            }
            if (this._error != "" && !TextUtils.isEmpty(_error)) {
                Toast.makeText(this.ctx, this._error, Toast.LENGTH_SHORT).show();
                LogMessage("error", this._error);
                return;
            }
        }
    }
}