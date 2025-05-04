package com.example.gdrbtestapplication.WapperClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpWapper {
    public static String post (String url, String json) {
        try {
            String response = "";
            HttpsURLConnection httpURLConnection = null;
            SSLContext context = null;
            try {
                TrustManager localTrustmanager = new X509TrustManager() {

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }
                };
                try {
                    SSLContext sslc = SSLContext.getInstance("TLS");
                    sslc.init(null, new TrustManager[]{localTrustmanager}, new SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                URL u = new URL(url);
                httpURLConnection = (HttpsURLConnection) u.openConnection();
                httpURLConnection.setHostnameVerifier(hostnameVerifier);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.connect();
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(json);
                writer.flush();
                writer.close();
                os.close();
                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                        response = sb.toString().substring(0, sb.toString().length() - 1);
                    }
                } else {
                    throw new Exception("Invalid response code received from server. Response code: " + String.valueOf(responseCode));
                }

            } catch (Exception ex) {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
