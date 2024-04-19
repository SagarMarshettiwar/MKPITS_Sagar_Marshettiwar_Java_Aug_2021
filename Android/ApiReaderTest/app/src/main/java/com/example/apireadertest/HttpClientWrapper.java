package com.example.apireadertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpClientWrapper {
    public static String getResponseGET(String url) {
        try {
            if (url.contains("http://")) {

                String response = "";
                HttpURLConnection httpURLConnection = null;
                try {
                    URL u = new URL(url);
                    httpURLConnection = (HttpURLConnection) u.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "text/plain");
                    httpURLConnection.connect();
                    int status = httpURLConnection.getResponseCode();

                    switch (status) {
                        case 200:
                        case 201:
                            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line).append("\n");
                                response = sb.toString().substring(0, sb.toString().length() - 1);
                            }
                            br.close();
                            return response;
                    }
                } catch (IOException ex) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
            return null;
        }
    }