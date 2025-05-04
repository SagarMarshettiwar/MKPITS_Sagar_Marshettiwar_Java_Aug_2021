package com.trustbank.util;

import android.util.Base64;

import org.json.JSONObject;

public class GCMMessageBodyManipulator {
    public static String GCMencryptRequestBody(String data) throws Exception {
        if (AppConstants.isInterceptorEnabled) {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("d", GCMEnDecryption.encrypt(Base64.decode(JetPackSecurePreference.storeAndRetriveSecretKey(), Base64.DEFAULT),
                    data.getBytes()).replaceAll("\n", ""));
            return jsonRequest.toString();
        } else {
            return data;
        }
    }

    public static String GCMdecryptResponseBody(String data) throws Exception {

        if (AppConstants.isInterceptorEnabled) {
            JSONObject jsonResponse = new JSONObject(data);
            if (jsonResponse.has("response_code") && jsonResponse.getString("response_code").equals("1")) {
                String  base64DecodedBytes = jsonResponse.getString("response");
                return GCMEnDecryption.decrypt(Base64.decode(JetPackSecurePreference.storeAndRetriveSecretKey(), Base64.DEFAULT), base64DecodedBytes);
            } else {
                return data;

            }
        } else {
            return data;
        }
    }
}
