package com.example.orcodegenerator.HttpWraper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
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

public class HttpOperation {
    public static String post(String requestUrl, String postValues, String Action, String random_no){
        try {
            if (requestUrl.contains("http://")) {

                URL url;
                String response = "";
                HttpURLConnection conn = null;
                try {
                    url = new URL(requestUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);  //Specifies whether this URLConnection allows receiving data.
                    conn.setDoOutput(true); //Specifies whether this URLConnection allows sending data.
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("action", Action);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(postValues);

                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        String line = "";
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        while ((line = br.readLine()) != null) {
//                    response+=line;

                            sb.append(line).append("\n");
                            response = sb.toString().substring(0, sb.toString().length() - 1);
                        }
                    } else {
                        response = "";
                    }
                } catch (IOException ex) {
                    if (conn != null) {
                        conn.disconnect();
                    }
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }

                return response;

            } else if (requestUrl.contains("https://")) {

                URL url;
                String response = "";
                HttpsURLConnection conn = null;
                SSLContext context = null;
                try {
                    // Dummy trust manager that trusts all certificates
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
                    // Create SSLContext and set the socket factory as default
                    try {
                        SSLContext sslc = SSLContext.getInstance("TLS");
                        sslc.init(null, new TrustManager[]{localTrustmanager}, new SecureRandom());
                        HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    }
                    // Set a custom HostnameVerifier to bypass hostname verification
                    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true; // Accept all hostnames
                        }
                    };
                    url = new URL(requestUrl);

                    conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(1500000);
                    conn.setConnectTimeout(1500000);
                    conn.setHostnameVerifier(hostnameVerifier);
                    conn.setDoInput(true);// Specifies whether this URLConnection allows receiving data.
                    conn.setDoOutput(true);// Specifies whether this URLConnection allows sending data.
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("action", Action);
                    conn.setRequestProperty("txn_id", random_no);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write (postValues);

                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                            response = sb.toString().substring(0, sb.toString().length() - 1);
                        }
                    } else {
                        throw new Exception("Invalid response code received from server. Response code: " + String.valueOf(responseCode));
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (conn != null) {
                        conn.disconnect();
                    }
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return null;

    }

    private static final HostnameVerifier DUMMY_VERIFIER = (hostname, session) -> {
        String ip =" https://192.168.0.16:8095";
        if (hostname.equalsIgnoreCase(ip)) {
            return true;
        } else {
            return false;
        }

    };
}
