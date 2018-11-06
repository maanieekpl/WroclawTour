package com.example.adrian.wroclawtour;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CatActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mPlaceReferences;
    List<LinearLayout> llList = new ArrayList<>();


    String catName = null;
    int placeId;
    int countId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Intent catActivity = getIntent();
        catName = catActivity.getStringExtra("categ");
//        placeId = catActivity.getIntExtra("placeId",999);

        mDatabase = FirebaseDatabase.getInstance();



        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.cat_ll_vert);


        for (int i = 0; i <= MainActivity.placeCount; i++) {

//            final LinearLayout ll2 = new LinearLayout(this);
//            ll2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            ll2.setOrientation(LinearLayout.HORIZONTAL);
//            ll2.setId(i);
//            llList.add(ll2);
//
//            linearLayout.addView(ll2);
//
//            final ImageView bImageView = new ImageView(this);
//            bImageView.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
//            ll2.addView(bImageView);
//
//
//            final TextView bTextView = new TextView(this);
//            bTextView.setLayoutParams(new ViewGroup.LayoutParams(680, 400));
//            ll2.addView(bTextView);

            String pl = "place" + Integer.toString(i);
            mPlaceReferences = mDatabase.getReference("places").child(pl);
            mPlaceReferences.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Place p = dataSnapshot.getValue(Place.class);

                    if(p.category.equals(catName)){


                        final LinearLayout ll2 = new LinearLayout(CatActivity.this);
                        ll2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        ll2.setOrientation(LinearLayout.HORIZONTAL);
                        ll2.setId(countId);
                        llList.add(ll2);

                        linearLayout.addView(ll2);

                        final ImageView bImageView = new ImageView(CatActivity.this);
                        bImageView.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
                        ll2.addView(bImageView);


                        final TextView bTextView = new TextView(CatActivity.this);
                        bTextView.setLayoutParams(new ViewGroup.LayoutParams(680, 400));
                        ll2.addView(bTextView);

                        bTextView.setText(p.name);

                        Picasso.get().load(p.imageSmall).into(bImageView);

                        ll2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent placeActivity = new Intent(CatActivity.this, PlaceActivity.class);
                                placeActivity.putExtra("placeId", ll2.getId());
                                startActivity(placeActivity);
                            }
                        });

                    }
                    countId++;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

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
