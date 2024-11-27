package com.trustbank.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trustbank.R;
import com.trustbank.util.AppConstants;
import com.trustbank.util.TrustMethods;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PersonalProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backButton_new;
    private TextView toolbar,txt_clientId,txt_name,txt_cedulaId,txt_email,txt_mobNum,txt_add;
    ImageView imageViewProfile;
    TrustMethods method;
    int RESULT_LOAD_IMAGE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppConstants.IS_CHECK_ACCESS_BROKEN) {
            if (savedInstanceState != null) {
                Object currentPID = String.valueOf(android.os.Process.myPid());
                // Check current PID with old PID
                if (currentPID != savedInstanceState.getString(AppConstants.PID)) {
                    // If current PID and old PID are not equal, new process was created, restart the app from SplashActivity
                    TrustMethods.naviagteToSplashScreen(PersonalProfileActivity.this);
                }
            }
        }
        setContentView(R.layout.activity_personal_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        toolbar=findViewById(R.id.toolbar);
        toolbar.setText(R.string.PersonalProfile);

        inIt();
    }

    private void inIt() {
        method = new TrustMethods(PersonalProfileActivity.this);
        backButton_new = findViewById(R.id.backButton_new);
        imageViewProfile = findViewById(R.id.imageViewProfile_Id);
        txt_clientId = findViewById(R.id.txt_clientId);
        txt_name = findViewById(R.id.txt_name);
        txt_cedulaId = findViewById(R.id.txt_cedulaId);
        txt_email = findViewById(R.id.txt_email);
        txt_mobNum = findViewById(R.id.txt_mobNum);
        txt_add = findViewById(R.id.txt_add);
        String encoded = method.GetProfilepicture(PersonalProfileActivity.this);
        byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
        imageViewProfile.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        backButton_new.setOnClickListener(this);
        imageViewProfile.setOnClickListener(this);
        String a=method.getCedulaId(PersonalProfileActivity.this, "Cedula");
        txt_clientId.setText(AppConstants.getCLIENTID());
        txt_name.setText(AppConstants.getUSERNAME());
        txt_cedulaId.setText(a);
        txt_email.setText(AppConstants.getUSEREMAILADDRESS());
        txt_mobNum.setText(AppConstants.getUSERMOBILENUMBER());
        txt_add.setText(AppConstants.getAddress());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.backButton_new:
                Intent intent = new Intent(PersonalProfileActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.imageViewProfile_Id:
                try {
                    Intent photoPickerIntent = new Intent();
                    photoPickerIntent.setType("image//*");
                    photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
                break;

            default:
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE ){
            try {
                if (resultCode == Activity.RESULT_OK) {
                    Uri imageUri = data.getData();
                    if(imageUri != null){
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        method.setProfilepicture(PersonalProfileActivity.this,selectedImage);
                        PersonalProfileActivity.this.finish();
                        PersonalProfileActivity.this.startActivity(PersonalProfileActivity.this.getIntent());
                    }else{
                        Toast.makeText(PersonalProfileActivity.this, getResources().getString(R.string.ImagenotSelected), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PersonalProfileActivity.this, getResources().getString(R.string.WentWrong), Toast.LENGTH_LONG).show();
            }
        }
    }
}