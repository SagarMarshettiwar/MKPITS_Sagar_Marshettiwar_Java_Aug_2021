package com.example.googlemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnPolylineClickListener{
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            polyline.setPattern(null);
        }
        Toast.makeText(this, "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<String> a = new ArrayList<>();
        a.add("21.1173397");
        a.add("21.1208244");
        a.add("21.1317774");
        a.add("21.1452169");
        a.add("21.1689104");
        a.add("21.1735713");
        a.add("21.187392");
        a.add("21.166588");
        a.add("21.187835");
        a.add("21.188663");
        ArrayList<String> b = new ArrayList<>();
        b.add("79.0515752");
        b.add("79.0461174");
        b.add("79.0532449");
        b.add("79.0710473");
        b.add("79.0765292");
        b.add("79.0556553");
        b.add("79.0548241");
        b.add("79.0258331");
        b.add("79.0611702");
        b.add("79.0609323");
        ArrayList<LatLng> dynamicLatLngList = new ArrayList<>();
        for(int i=0;i<=a.size()-1;i++){
            dynamicLatLngList.add(new LatLng(Double.parseDouble(a.get(i)), Double.parseDouble(b.get(i))));
        }
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(dynamicLatLngList));
        /*Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(21.1173397,79.0515752),
                        new LatLng(21.1208244,79.0461174),
                        new LatLng(21.1317774,79.0532449),
                        new LatLng(21.1452169,79.0710473),
                        new LatLng(21.1689104,79.0765292),
                        new LatLng(21.1735713,79.0556553)));*/
        polyline1.setTag("alpha");
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(21.1610858,79.0725101), 13));
        googleMap.setOnPolylineClickListener(this);
    }
}