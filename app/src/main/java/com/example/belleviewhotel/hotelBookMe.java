package com.example.belleviewhotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class hotelBookMe extends AppCompatActivity {

    private EditText checkIn,checkOut,name,email,contact;
    private ImageView cal,calEnd;
    private TextView roomID,priceTag;
    private Button bookMe,checkPrice;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String roomId,Rprice;
    private int roomPrice;
    private CheckBox payPal,creditCard,debitCard,mobileMoney;
    private long date1,date2,diff;
    private LinearLayout llayout;

    private static AlertDialog.Builder alert;
    private static AlertDialog dialog;

    HashMap<String,String> map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_book_me);

        database = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomID");
        roomPrice = Integer.parseInt(intent.getStringExtra("room_price"));
        Log.d("roomID", "onCreate: " + roomId);
        Log.d("roomPrice", "onCreate: " + roomPrice);


        checkIn = findViewById(R.id.checkIn);
        checkOut = findViewById(R.id.checkOut);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        roomID = findViewById(R.id.roomID);
        priceTag = findViewById(R.id.priceTag);

        roomID.setText(roomId);
        priceTag.setText("0");

        bookMe = findViewById(R.id.bookMe);
        checkPrice = findViewById(R.id.checkPrice);

        payPal = findViewById(R.id.paypal);
        creditCard = findViewById(R.id.creditCard);
        debitCard = findViewById(R.id.debitCard);
        mobileMoney = findViewById(R.id.mobilMoney);

        llayout = findViewById(R.id.llayout);

        cal = findViewById(R.id.imgStart);
        calEnd = findViewById(R.id.imgEnd);

        checkPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diff = (date2-date1)/(1000*60*60*24);
                Rprice = String.valueOf(roomPrice * (int) diff);
                priceTag.setText(Rprice);
            }
        });

        final Calendar c = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date1 = c.getTimeInMillis();
                checkIn.setText(sdf.format(c.getTime()));
            }
        };

        final DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date2 = c.getTimeInMillis();
                checkOut.setText(sdf.format(c.getTime()));
            }
        };

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(hotelBookMe.this, date,c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        calEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(hotelBookMe.this, dateEnd,c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        payPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payPal.isChecked()){
                    creditCard.setEnabled(false);
                    debitCard.setEnabled(false);
                mobileMoney.setEnabled(false);
                }else {
                    payPal.setEnabled(true);
                    creditCard.setEnabled(true);
                    debitCard.setEnabled(true);
                    mobileMoney.setEnabled(true);
                }
            }
        });

        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (creditCard.isChecked()){
                    payPal.setEnabled(false);
                    debitCard.setEnabled(false);
                    mobileMoney.setEnabled(false);
                }else {
                    payPal.setEnabled(true);
                    creditCard.setEnabled(true);
                    debitCard.setEnabled(true);
                    mobileMoney.setEnabled(true);                }
            }
        });

        debitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (debitCard.isChecked()){
                    creditCard.setEnabled(false);
                    payPal.setEnabled(false);
                    mobileMoney.setEnabled(false);
                }else {
                    payPal.setEnabled(true);
                    creditCard.setEnabled(true);
                    debitCard.setEnabled(true);
                    mobileMoney.setEnabled(true);                }
            }
        });

         mobileMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobileMoney.isChecked()){
                    creditCard.setEnabled(false);
                    debitCard.setEnabled(false);
                    payPal.setEnabled(false);
                }else {
                    payPal.setEnabled(true);
                    creditCard.setEnabled(true);
                    debitCard.setEnabled(true);
                    mobileMoney.setEnabled(true);                }
            }
        });


        bookMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookDataUpload();
            }
        });
    }

    public void bookDataUpload(){

        map.put("Room ID",roomId);
        map.put("Price", Rprice);
        map.put("Stay Duration", String.valueOf(diff));
        map.put("CheckIn",checkIn.getText().toString());
        map.put("CheckOut", checkOut.getText().toString());
        map.put("Name",name.getText().toString());
        map.put("Email", email.getText().toString());
        map.put("Contact",contact.getText().toString());
        if (payPal.isChecked()){
            map.put("Payment Option",payPal.getText().toString());
        }else if (creditCard.isChecked()){
            map.put("Payment Option",creditCard.getText().toString());
        }else if (debitCard.isChecked()){
            map.put("Payment Option",debitCard.getText().toString());
        }else if (mobileMoney.isChecked()){
            map.put("Payment Option",mobileMoney.getText().toString());
        }

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        if (snapshot1.getKey().contains(roomId)){
                            Log.d("Log",snapshot1.getKey());
                            Toast.makeText(hotelBookMe.this, "Room Has Been Booked", Toast.LENGTH_LONG).show();
                            hotelBookMe.this.finish();
                        }else{
                            ref.child("Book Hotel Room").child(roomId).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    summaryPopUp(checkIn.getText().toString(),checkOut.getText().toString(),String.valueOf(diff),name.getText().toString(),
                                            email.getText().toString(),contact.getText().toString(),roomId,Rprice);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(hotelBookMe.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void summaryPopUp(String cIn, String cOut, String dur, String cusName, String cusEmail,
                             String cusContact,String rID,String rPrice){
        LayoutInflater layoutInflater = LayoutInflater.from(hotelBookMe.this);
        View view = layoutInflater.inflate(R.layout.book_details,llayout, false);

        TextView id = view.findViewById(R.id.viewRoomID);
        TextView price = view.findViewById(R.id.viewRoomPrice);
        TextView checkIn = view.findViewById(R.id.viewCheckIn);
        TextView checkOut = view.findViewById(R.id.viewCheckOut);
        TextView duration = view.findViewById(R.id.viewNumOfDays);
        TextView name = view.findViewById(R.id.viewName);
        TextView email = view.findViewById(R.id.viewEmail);
        TextView contact = view.findViewById(R.id.viewContact);
        Button close = view.findViewById(R.id.close);

        id.setText(rID);
        price.setText(rPrice);
        checkIn.setText(cIn);
        checkOut.setText(cOut);
        duration.setText(dur);
        name.setText(cusName);
        email.setText(cusEmail);
        contact.setText(cusContact);

        alert = new AlertDialog.Builder(hotelBookMe.this);

        alert.setView(view);
        dialog = alert.create();
        dialog.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                hotelBookMe.this.finish();
            }
        });

    }

}
