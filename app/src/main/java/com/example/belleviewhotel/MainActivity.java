package com.example.belleviewhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageView,imageView1,imageView3,imageView4,imageView5,
            Hotimg,Restimg;
    private StorageReference storageReference;
    private TextView roomIDcode1,roomPrice1,roomIDcode2,roomPrice2,roomIDcode3,roomPrice3,
            roomIDcode4,roomPrice4,roomIDcode5,roomPrice5;
    private LinearLayout llayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageReference = FirebaseStorage.getInstance().getReference();
        imageView = findViewById(R.id.img);
        imageView1 = findViewById(R.id.img1);
        imageView3 = findViewById(R.id.img3);
        imageView4 = findViewById(R.id.img4);
        imageView5 = findViewById(R.id.img5);
        Hotimg = findViewById(R.id.Hotimg);
        Restimg = findViewById(R.id.Restimg);

        roomIDcode1 = findViewById(R.id.roomIDcode1);
        roomIDcode2 = findViewById(R.id.roomIDcode2);
        roomIDcode3 = findViewById(R.id.roomIDcode3);
        roomIDcode4 = findViewById(R.id.roomIDcode4);
        roomIDcode5 = findViewById(R.id.roomIDcode5);

        roomPrice1 = findViewById(R.id.roomPrice1);
        roomPrice2 = findViewById(R.id.roomPrice2);
        roomPrice3 = findViewById(R.id.roomPrice3);
        roomPrice4 = findViewById(R.id.roomPrice4);
        roomPrice5 = findViewById(R.id.roomPrice5);

        String url = "https://firebasestorage.googleapis.com/v0/b/belleviewhotel-8c342.appspot.com/o/SD1.jpeg?alt=media&token=7a80a285-d45e-4b8d-a0b1-8cecab0d3ac8";
        String url1 = "https://firebasestorage.googleapis.com/v0/b/belleviewhotel-8c342.appspot.com/o/SD2.jpeg?alt=media&token=7ed1a0bd-3e73-4725-b217-81edd0021b7b";
        String url3 = "https://firebasestorage.googleapis.com/v0/b/belleviewhotel-8c342.appspot.com/o/lux5.jpeg?alt=media&token=12e51ae7-dbd9-40d2-b9ba-ac2577dd1513";
        String url4 = "https://firebasestorage.googleapis.com/v0/b/belleviewhotel-8c342.appspot.com/o/SS1.jpeg?alt=media&token=466aa4cf-4ef3-4f39-8963-a4a96b7ba6bc";
        String url5 = "https://firebasestorage.googleapis.com/v0/b/belleviewhotel-8c342.appspot.com/o/SS3.jpeg?alt=media&token=3ab1d0ac-9dec-475b-bc5d-bf817089fb11";

        String Hot_Url = "https://firebasestorage.googleapis.com/v0/b/belleviewhotel-8c342.appspot.com/o/hotel.jpeg?alt=media&token=7f088673-2322-4927-8350-66ec55dc8191";
        String Rest_Url = "https://firebasestorage.googleapis.com/v0/b/belleviewhotel-8c342.appspot.com/o/restaurant.jpeg?alt=media&token=66cc7cc3-86c0-49ab-a16b-691a2d318726";



        Glide.with(getApplicationContext()).load(url).override(350,200).centerCrop().into(imageView);
        Glide.with(getApplicationContext()).load(url1).override(350,200).centerCrop().into(imageView1);
//        Glide.with(getApplicationContext()).load(url2).override(350,200).centerCrop().into(imageView2);
        Glide.with(getApplicationContext()).load(url3).into(imageView3);
        Glide.with(getApplicationContext()).load(url4).override(350,200).centerCrop().into(imageView4);
        Glide.with(getApplicationContext()).load(url5).override(350,200).centerCrop().into(imageView5);
        Glide.with(getApplicationContext()).load(Hot_Url).override(350,200).centerCrop().into(Hotimg);
        Glide.with(getApplicationContext()).load(Rest_Url).override(350,200).centerCrop().into(Restimg);


        imageView.setOnClickListener(this);
        imageView1.setOnClickListener(this);
//        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);

        Restimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,restaurantBookMe.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this,hotelBookMe.class);

        switch(view.getId()){
            case R.id.img:
                intent.putExtra("roomID",roomIDcode1.getText().toString());
                intent.putExtra("room_price",roomPrice1.getText().toString());
                startActivity(intent);
                break;
            case R.id.img1:
                intent.putExtra("roomID",roomIDcode2.getText().toString());
                intent.putExtra("room_price",roomPrice2.getText().toString());
                startActivity(intent);

                break;
            case R.id.img3:
                intent.putExtra("roomID",roomIDcode3.getText().toString());
                intent.putExtra("room_price",roomPrice3.getText().toString());
                startActivity(intent);

                break;
            case R.id.img4:
                intent.putExtra("roomID",roomIDcode4.getText().toString());
                intent.putExtra("room_price",roomPrice4.getText().toString());
                startActivity(intent);

                break;
            case R.id.img5:
                intent.putExtra("roomID",roomIDcode5.getText().toString());
                intent.putExtra("room_price",roomPrice5.getText().toString());
                startActivity(intent);

                break;

        }

        Toast.makeText(MainActivity.this, "Book Now", Toast.LENGTH_SHORT).show();
    }
}
