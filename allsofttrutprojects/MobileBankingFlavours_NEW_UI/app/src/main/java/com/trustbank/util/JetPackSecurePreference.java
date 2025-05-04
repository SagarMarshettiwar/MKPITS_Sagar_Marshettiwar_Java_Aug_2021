package com.trustbank.util;

import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.trustbank.BuildConfig;

public class JetPackSecurePreference {

    public static String storeAndRetriveSecretKey() {

        String intValue = "";
        try {
            /*KeyGenParameterSpec var10000 = MasterKeys.AES256_GCM_SPEC;
            KeyGenParameterSpec keyGenParameterSpec = var10000;
            String var9 = MasterKeys.getOrCreate(keyGenParameterSpec);
            String mainKeyAlias = var9;
            SharedPreferences var10 = EncryptedSharedPreferences.create("secure_prefs", mainKeyAlias,MBank.getInstance(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            SharedPreferences securePreferences = var10;
            SharedPreferences.Editor var5 = securePreferences.edit();*/
            /// int var7 = false;
          /*  var5.putString("enc_key", );
            var5.commit();*/
            intValue = BuildConfig.enc_key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intValue;
    }


}
