//todo type1 using list
/*package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button button;
    HashMap<String, CustomerIdentityDocModel> customerIdentityDocModel = new HashMap<>();
    CustomerIdenityDocAdapter customerIdenityDocAdapter;
    // Remove getCustomerIdenityDocAdapter, it's not used

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    private void Init() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        button = findViewById(R.id.send);

        List<String> idprooflist = new ArrayList<>();
        idprooflist.add("AADHAR");
        idprooflist.add("PAN");
        idprooflist.add("DRIVING");
        idprooflist.add("RASHAN");
        customerIdenityDocAdapter = new CustomerIdenityDocAdapter(idprooflist,customerIdentityDocModel);
        recyclerView.setAdapter(customerIdenityDocAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(idprooflist.size());
    }

    public void send(View view) {
        try {
            StringWriter writer = new StringWriter();
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag("", "data");
            List<CustomerIdentityDocModel> data = new ArrayList<>(customerIdenityDocAdapter.getData());
            if (data.size() != 0) {
                for (int i = 0; i < data.size(); i++) {
                    xmlSerializer.startTag("", "doc");

                    xmlSerializer.startTag("", "checkbox_name");
                    xmlSerializer.text(data.get(i).getCkname());
                    xmlSerializer.endTag("", "checkbox_name");

                    xmlSerializer.startTag("", "ref_no");
                    xmlSerializer.text(data.get(i).getDocrefNo());
                    xmlSerializer.endTag("", "ref_no");

                    xmlSerializer.startTag("", "issuing_authority");
                    xmlSerializer.text(data.get(i).getIssuingAuth());
                    xmlSerializer.endTag("", "issuing_authority");

                    xmlSerializer.startTag("", "expiry_date");
                    xmlSerializer.text(data.get(i).getExpiryDate());
                    xmlSerializer.endTag("", "expiry_date");

                    xmlSerializer.endTag("", "doc");
                }
            } else {
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            }
            xmlSerializer.endTag("", "data");
            xmlSerializer.endDocument();

            String xmlString = writer.toString();
            Log.e("xmlString", xmlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/


//todo type 2 using HashMAP
package com.example.recyclerviewtest;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.DefaultItemAnimator;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.os.Bundle;
        import android.util.Log;
        import android.util.Xml;
        import android.view.View;
        import android.widget.Button;

        import org.xmlpull.v1.XmlSerializer;

        import java.io.StringWriter;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button button;
    HashMap<String, CustomerIdentityDocModel> customerIdentityDocModel = new HashMap<>();
    CustomerIdenityDocAdapter customerIdenityDocAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    private void Init() {
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        button=findViewById(R.id.send);

        List<String> idprooflist=new ArrayList<>();
        idprooflist.add("AADHAR");
        idprooflist.add("PAN");
        idprooflist.add("DRIVING");
        idprooflist.add("RASHAN");
        customerIdenityDocAdapter = new CustomerIdenityDocAdapter(idprooflist,customerIdentityDocModel);
        recyclerView.setAdapter(customerIdenityDocAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(idprooflist.size());
    }
    public void send(View view){
        try {
            StringWriter writer = new StringWriter();
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag("", "data");
            for (Map.Entry<String, CustomerIdentityDocModel> entry : customerIdentityDocModel.entrySet())
            {
                entry.getKey();
                entry.getValue();
                xmlSerializer.startTag("", "doc");

                xmlSerializer.startTag("", "checkbox_name");
                xmlSerializer.text(entry.getKey());
                xmlSerializer.endTag("", "checkbox_name");

                xmlSerializer.startTag("", "ref_no");
                xmlSerializer.text(entry.getValue().getDocrefNo());
                xmlSerializer.endTag("", "ref_no");

                xmlSerializer.startTag("", "issuing_authority");
                xmlSerializer.text(entry.getValue().getIssuingAuth());
                xmlSerializer.endTag("", "issuing_authority");

                xmlSerializer.startTag("", "expiry_date");
                xmlSerializer.text(entry.getValue().getExpiryDate());
                xmlSerializer.endTag("", "expiry_date");

                xmlSerializer.endTag("", "doc");
            }
            xmlSerializer.endTag("", "data");
            xmlSerializer.endDocument();
            String xmlString = writer.toString();
            Log.e("xmlString", xmlString);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }
}
