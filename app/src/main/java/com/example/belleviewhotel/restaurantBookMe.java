package com.example.belleviewhotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class restaurantBookMe extends AppCompatActivity {

    private EditText nameOfCustomer,seatNumber,CheckInTime;
    private Button reserve;
    private ImageView time;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private LinearLayout layout;

    private static AlertDialog.Builder alert,alert1;
    private static AlertDialog dialog,dialog1;

    private int hour,minute;
    private String remainderTime;

    HashMap<String,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_book_me);

        database = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        nameOfCustomer = findViewById(R.id.nameOfCustomer);
        seatNumber = findViewById(R.id.seatNumber);
        CheckInTime = findViewById(R.id.checkInTime);
        reserve = findViewById(R.id.reserve);
        layout = findViewById(R.id.layout);
        time = findViewById(R.id.imgStart1);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameOfCustomer.getText().toString().equals("") && seatNumber.getText().toString().equals("") && CheckInTime.getText().toString().equals("")){
                    Toast.makeText(restaurantBookMe.this, "Empty Slot", Toast.LENGTH_SHORT).show();
                }else {
                    reserveTable();
                }
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(restaurantBookMe.this);
                View view1 = layoutInflater.inflate(R.layout.layout_set_reminder,layout,false);

                final TimePicker timePicker = view1.findViewById(R.id.currentTime);
                final Button setButton = view1.findViewById(R.id.setBtn);
                final Button cancelButton = view1.findViewById(R.id.cancel);

                alert1 = new AlertDialog.Builder(restaurantBookMe.this);
                alert1.setView(view1);
                dialog1 = alert1.create();
                dialog1.show();

                setButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hour = timePicker.getCurrentHour();
                        minute = timePicker.getCurrentMinute();

                        remainderTime = hour + ":" + minute;
                        CheckInTime.setText(remainderTime);
                        dialog1.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
            }
        });
    }



    public void reserveTable(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    for (DataSnapshot snapshot1: snapshot.getChildren()){
//                        Log.d("View",snapshot1.getValue().toString());
//                        if (snapshot1.getValue().toString().contains("True")){
//                                fullAlert("Sorry, all slot has been booked");
//                            }else{

                                map.put("Name Of Customer",nameOfCustomer.getText().toString());
                                map.put("Seat Number", seatNumber.getText().toString());
                                map.put("Time Of Arrival", CheckInTime.getText().toString());
                                ref.child("Diner Reservation").child(CheckInTime.getText().toString()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        fullAlert("Your Reservation is being processed. Expect feedback in 10 minutes time");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
//                            }
                        }
//                    }
//            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void fullAlert(String msg){
        LayoutInflater layoutInflater = LayoutInflater.from(restaurantBookMe.this);
        View view = layoutInflater.inflate(R.layout.slot_full,layout, false);

        TextView popUp = view.findViewById(R.id.Full_alert);
        Button closeIt = view.findViewById(R.id.closeit);

        alert = new AlertDialog.Builder(restaurantBookMe.this);

        popUp.setText(msg);

        alert.setView(view);
        dialog = alert.create();
        dialog.show();

        closeIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                restaurantBookMe.this.finish();
            }
        });

    }
}
