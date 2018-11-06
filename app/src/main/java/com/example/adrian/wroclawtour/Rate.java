package com.example.adrian.wroclawtour;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rate {

    private FirebaseDatabase mDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference mPlaceReferences;

    private Place place;

    private float rate;
    private int id;


    public Rate(String rate){
        this.rate = Float.parseFloat(rate);
    }

    public void setRateToDB(float rate){
        mDatabase = FirebaseDatabase.getInstance();
        mPlaceReferences = mDatabase.getReference("places").child("place" + id).child("rate");
        mPlaceReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                place = dataSnapshot.getValue(Place.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        float ra = (Float.parseFloat(place.rate) + this.rate)/2;

        String r = Float.toString(ra);

        mPlaceReferences.setValue(r);

    }


    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
