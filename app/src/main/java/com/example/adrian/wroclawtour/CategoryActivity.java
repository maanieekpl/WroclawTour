package com.example.adrian.wroclawtour;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseDatabase mDatabase;
    private DatabaseReference mPlaceReferences;
    int catLenght = 19;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDatabase = FirebaseDatabase.getInstance();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.category_ll_ver);

        List<LinearLayout> llList = new ArrayList<>();
        final ArrayList<String> categories = new ArrayList<>();


//READ DATA FROM DB

        for (int i = 0; i < catLenght; i++) {

            final LinearLayout ll = new LinearLayout(this);
            ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setId(i);
            llList.add(ll);

            linearLayout.addView(ll);

            ImageView imageView = new ImageView(CategoryActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            ll.addView(imageView);

            final TextView textView = new TextView(CategoryActivity.this);
            textView.setLayoutParams(new ViewGroup.LayoutParams(680, 200));
            ll.addView(textView);




            final String cat = "cat" + Integer.toString(i);
            mPlaceReferences = mDatabase.getReference("categories").child(MainActivity.language).child(cat);
            mPlaceReferences.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String category = dataSnapshot.getValue(String.class);

                    categories.add(category);

                    textView.setText(category);



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent catActivity = new Intent(CategoryActivity.this, CatActivity.class);
                    catActivity.putExtra("categ",textView.getText());
//                    catActivity.putExtra("placeId", ll.getId());
                    startActivity(catActivity);
                }
            });


        }


//SORT CATEGORIES

//        Collections.sort(categories);


////ADD LL TO VIEW AND ADD DATA TO VIEW
//
//        for(int i = 0; i < catLenght; i++) {
//
//            LinearLayout ll = new LinearLayout(this);
//            ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            ll.setOrientation(LinearLayout.HORIZONTAL);
//            ll.setId(i);
//            llList.add(ll);
//
//            linearLayout.addView(ll);
//
//            ImageView imageView = new ImageView(this);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
//            ll.addView(imageView);
//
//            TextView textView = new TextView(this);
//            textView.setLayoutParams(new ViewGroup.LayoutParams(680, 200));
//            textView.setText(categories.get(i));
//            ll.addView(textView);
//
//        }
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
