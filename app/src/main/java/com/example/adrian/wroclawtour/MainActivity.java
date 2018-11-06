package com.example.adrian.wroclawtour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String language = "en";
    public static int placeCount=6;

    public final static String EXTRA_MESSAGE = "MEASSAGE";
    private static final String TAG = "MainActivity";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mPlaceReferences;

    public int placeId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        List<LinearLayout> llList = new ArrayList<>();

        Random generator = new Random();


//READ NUMBER OF PLACES FROM DB
        mPlaceReferences = mDatabase.getReference("places").child("placeCounter");
        mPlaceReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer temp = dataSnapshot.getValue(Integer.class);
                placeCount = temp;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//------------------------------------------------------------------------------------


//IMPLEMENT VIEW
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLinearVertical);

        ImageView mImageView = new ImageView(this);
        mImageView.setImageResource(R.drawable.wt_logo);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(1080, 697));

        linearLayout.addView(mImageView);

        for (int i = 0; i < 5; i++) {

            int id = generator.nextInt(placeCount);
            final LinearLayout ll2 = new LinearLayout(this);
            ll2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            ll2.setOrientation(LinearLayout.HORIZONTAL);
            ll2.setId(id);
            llList.add(ll2);

            linearLayout.addView(ll2);

            final ImageView bImageView = new ImageView(this);
            bImageView.setLayoutParams(new LayoutParams(400, 400));
            ll2.addView(bImageView);


            final TextView bTextView = new TextView(this);
            bTextView.setLayoutParams(new LayoutParams(680, 400));
            ll2.addView(bTextView);


            String pl = "place" + id;
            mPlaceReferences = mDatabase.getReference("places").child(pl);
            mPlaceReferences.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Place p = dataSnapshot.getValue(Place.class);

                    bTextView.setText(p.name);

                    Picasso.get().load(p.imageSmall).into(bImageView);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            ll2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent placeActivity = new Intent(MainActivity.this, PlaceActivity.class);
                    placeActivity.putExtra("placeId", ll2.getId());
                    startActivity(placeActivity);
                }
            });
        }
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

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        mDatabase = FirebaseDatabase.getInstance();
//        List<LinearLayout> llList = new ArrayList<>();
//
//        //Implement view
//        ScrollView sv = new ScrollView(this);
//        sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//
//        ImageView mImageView = new ImageView(this);
//        mImageView.setImageResource(R.drawable.wt_logo);
//        mImageView.setLayoutParams(new LayoutParams(1080, 697));
//
//        LinearLayout ll = new LinearLayout(this);
//        ll.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        ll.setOrientation(LinearLayout.VERTICAL);
//
//        sv.addView(ll);
//        ll.addView(mImageView);
//
////        mPlaceReferences = mDatabase.getReference("places").child("place3");
////        mPlaceReferences.setValue(new Place("museums", "National Museum", "image", "Jakiś opis", "Wrocław", "5f", "15f", "40f"));
//
//        for (int i = 0; i < 3; i++) {
//            final LinearLayout ll2 = new LinearLayout(this);
//            ll2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//            ll2.setOrientation(LinearLayout.HORIZONTAL);
//            ll2.setId(i);
//            llList.add(ll2);
//
//            ll.addView(ll2);
//
//            final ImageView bImageView = new ImageView(this);
//            bImageView.setLayoutParams(new LayoutParams(400, 400));
//            ll2.addView(bImageView);
//
//
//            final TextView bTextView = new TextView(this);
//            bTextView.setLayoutParams(new LayoutParams(680, 400));
//            ll2.addView(bTextView);
//
//
//            String pl = "place" + Integer.toString(i);
//            mPlaceReferences = mDatabase.getReference("places").child(pl);
//            mPlaceReferences.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Place p = dataSnapshot.getValue(Place.class);
//
//                    bTextView.setText(p.name);
//
//                    Picasso.get().load(p.imageSmall).into(bImageView);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//            ll2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent placeActivity = new Intent(MainActivity.this, PlaceActivity.class);
//                    placeActivity.putExtra("place", ll2.getId());
//                    startActivity(placeActivity);
//                }
//            });
//        }
//
//
//        //END
//    }

