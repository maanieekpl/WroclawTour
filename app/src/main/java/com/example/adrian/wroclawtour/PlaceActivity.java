package com.example.adrian.wroclawtour;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PlaceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "PlaceActivity";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mPlaceReferences;

    private Place place;

    private String placeName;
    private ImageView placeImage;
    private TextView placeDescription;
    private TextView placeAddress;
    private RatingBar placeRate;
    private FloatingActionButton mapView;

    private float placeLongtitude;
    private float placeLatitude;

    private int placeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Toolbar toolbar = (Toolbar) findViewById(R.id.place_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mDatabase = FirebaseDatabase.getInstance();

        Intent placeActivity = getIntent();
        placeID = placeActivity.getIntExtra("placeId" , 999);

        String toast = Integer.toString(placeID);
//        Toast.makeText(PlaceActivity.this, toast, Toast.LENGTH_SHORT).show();

        String pl = "place" + Integer.toString(placeID);
        mPlaceReferences = mDatabase.getReference("places").child(pl);
        mPlaceReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                place = dataSnapshot.getValue(Place.class);

                placeName = place.name;

                placeImage = (ImageView) findViewById(R.id.placeImage);
                Picasso.get().load(place.imageBig).into(placeImage);

                placeDescription = (TextView) findViewById(R.id.placeDescription);
                placeDescription.setText(place.description);

                placeAddress = (TextView) findViewById(R.id.placeAdress);
                placeAddress.setText(place.address);

                placeRate = (RatingBar) findViewById(R.id.placeRatingBar);
                placeRate.setRating(Float.parseFloat(place.rate));

                placeLongtitude = Float.parseFloat(place.longtitude);
                placeLatitude = Float.parseFloat(place.latitude);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mapView = (FloatingActionButton) findViewById(R.id.mapView);
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsActivity = new Intent(PlaceActivity.this, MapsActivity.class);
                mapsActivity.putExtra("longtitude", placeLongtitude);
                mapsActivity.putExtra("latitude", placeLatitude);
                mapsActivity.putExtra("placeName", placeName);
                startActivity(mapsActivity);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            Intent mapActivity = new Intent(this, MapActivity.class);
            startActivity(mapActivity);
            // Handle the camera action
        } else if (id == R.id.nav_best_places) {
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);

        } else if (id == R.id.nav_category) {
            Intent categoryActivity = new Intent(this, CategoryActivity.class);
            startActivity(categoryActivity);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
