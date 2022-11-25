package com.example.demo

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


class MainActivity : AppCompatActivity() {


    var advancedKeyAlias: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        encryptToSharedPref("hello");
        fun getValueFromSharedPreferencesWith(key: String) = getEncryptedPreferences().getString(key, null);

    }

    init {
        val advancedSpec = KeyGenParameterSpec.Builder("your_master_key_name",
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT).apply {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setKeySize(256)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val hasStrongBox = packageManager.hasSystemFeature(PackageManager.FEATURE_STRONGBOX_KEYSTORE)
                if (hasStrongBox)
                    setIsStrongBoxBacked(true)
            }
        }.build()
         advancedKeyAlias = MasterKeys.getOrCreate(advancedSpec)
    }

    private fun encryptToSharedPref(data: String){
        getEncryptedPreferences().edit().putString("android_crypto", data).apply()
    }


    private fun getEncryptedPreferences() =
        EncryptedSharedPreferences.create("your_shared_preferences", advancedKeyAlias.toString(),
            this, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)



}