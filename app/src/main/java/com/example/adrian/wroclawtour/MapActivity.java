package com.example.adrian.wroclawtour;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback
//        ,NavigationView.OnNavigationItemSelectedListener
{

    private static final String TAG = "MapActivity";

    private static final float DEFAULT_LONGTITUDE = 51.109537f;
    private static final float DEFAULT_LATITUDE = 17.032086f;
    private static final LatLng DEFAULT_LATLNG = (new LatLng(51.109537f, 17.032086f));

    private GoogleMap mMap;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mPlaceReferences;

    private List<String> names = new ArrayList<>();
    private List<LatLng> latLngs = new ArrayList<>();

    int id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);
//
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        mDatabase = FirebaseDatabase.getInstance();
        final List<GoogleMap> mapList = new ArrayList<>();



        for(int i = 0; i <= MainActivity.placeCount; i++) {
            id = i;
            String pl = "place" + Integer.toString(i);
            mPlaceReferences = mDatabase.getReference("places").child(pl);
            mPlaceReferences.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Place p = dataSnapshot.getValue(Place.class);

                    names.add(p.name);
                    latLngs.add(new LatLng(Float.parseFloat(p.longtitude), Float.parseFloat(p.latitude)));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Float.parseFloat(p.longtitude), Float.parseFloat(p.latitude))).title(p.name)).showInfoWindow();
                    mapList.add(mMap);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LATLNG, 15f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LATLNG));
        for(int i = 0; i<3; i++){


//            mMap.addMarker(new MarkerOptions().position(latLngs.get(i)).title(names.get(i))).showInfoWindow();
//            mMap.addMarker(new MarkerOptions().position(latLngs.get(i)).title(names.get(i))).showInfoWindow();


        }




    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_map) {
//            Intent mapActivity = new Intent(this, MapActivity.class);
//            startActivity(mapActivity);
//            // Handle the camera action
//        } else if (id == R.id.nav_best_places) {
//            Intent mainActivity = new Intent(this, MainActivity.class);
//            startActivity(mainActivity);
//
//        } else if (id == R.id.nav_category) {
//            Intent categoryActivity = new Intent(this, CategoryActivity.class);
//            startActivity(categoryActivity);
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
