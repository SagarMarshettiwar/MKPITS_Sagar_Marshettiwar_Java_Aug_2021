package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class Encryption : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encryption)
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        val securePreferences = EncryptedSharedPreferences.create(
            "secure_prefs",
            mainKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        // Storing Data in Preferences
        with(securePreferences.edit()) {
            putBoolean("Bool", true)
            putString("String", "Some normal string value")
            putInt("Integer", 10)
            commit()
        }


        val boolValue = securePreferences.getBoolean("Bool", false)
        val strValue = securePreferences.getString("String", "")
        val intValue = securePreferences.getInt("Integer", 0)

        Log.e("booleValue", boolValue.toString());
        if (strValue != null) {
            Log.e("strValue", strValue)
        };
        Log.e("intValue", intValue.toString());
    }
}