package com.example.apireadertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView catalague_List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }
    private void Init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catalague_List=findViewById(R.id.catalague_List);
        new AsyncTaskGetApi(MainActivity.this).execute();
    }
    private class AsyncTaskGetApi extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String error;
        Context ctx;
        ArrayList<PlaystoreModel> playstoreModel;
        String response;
        JSONArray data;
        String result;

        public AsyncTaskGetApi(Context ctx) {

            this.error = "";
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading  Details....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = "http://vasundharaapps.com/artwork_apps/api/AdvertiseNewApplications/17/com.hd.camera.apps.high.quality";
                if (!url.equals("")) {
                    result = HttpClientWrapper.getResponseGET(url);
                }
                if (result == null || result.equals("")) {
                    error = "SERVER_NOT_RESPONDING";
                    return error;
                }
                JSONObject jsonResponse = (new JSONObject(result));

                String statusCode = jsonResponse.has("status") ? jsonResponse.getString("status") : "NA";
                if (statusCode.equals("1")) {
                    String smessage = jsonResponse.has("message") ? jsonResponse.getString("message") : "NA";
                    if(smessage.equalsIgnoreCase("successfully")) {
                        data = jsonResponse.getJSONArray("data");
                        if (data.length() == 0) {
                            error = "NO_RECORDS_FOUND";
                            return error;
                        }
                        playstoreModel=new ArrayList<>();
                        if (data != null && data.length() > 0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject JsonObject = data.getJSONObject(i);
                                String name = JsonObject.has("name") ? JsonObject.getString("name") : "NA";
                                String img = JsonObject.has("thumb_image") ? JsonObject.getString("thumb_image") : "NA";
                                String link = JsonObject.has("app_link") ? JsonObject.getString("app_link") : "NA";
                                String pkg = JsonObject.has("package_name") ? JsonObject.getString("package_name") : "NA";

                                PlaystoreModel model=new PlaystoreModel();
                                model.setName(name);
                                model.setThumb_image(img);
                                model.setPackage_name(pkg);
                                model.setApp_link(Uri.parse(link));
                                playstoreModel.add(model);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
                return error;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String value) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (this.error != "") {
                return;
            }
            MyAdapter adapter=new MyAdapter(MainActivity.this,playstoreModel);
            catalague_List.setHasFixedSize(true);
            catalague_List.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            catalague_List.setAdapter(adapter);
        }
    }
}