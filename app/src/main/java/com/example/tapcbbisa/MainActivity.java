package com.example.tapcbbisa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {

    private Button btnon, btnback;
    private Button btnsingle, btndouble;
    private TextView txt, kon;
    private String nilaiPH, knd;
    private String kondisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnon = (Button) findViewById(R.id.btnganti);
        txt = (TextView) findViewById(R.id.pH);
        kon = (TextView) findViewById(R.id.kondisi);
        btnsingle = (Button) findViewById(R.id.single);
        btndouble = (Button) findViewById(R.id.doubl);
        btnback = (Button) findViewById(R.id.bback);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("NilaiPH");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nilaiPH = dataSnapshot.getValue(String.class);
                txt.setText(nilaiPH);

                if (Float.parseFloat(nilaiPH) <= 2) {
                    kon.setText("Baik");
                    FirebaseDatabase kondisiDB = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = kondisiDB.getReference("KondisiLarutan");
                    myRef.setValue("Baik");


                } else if (Float.parseFloat(nilaiPH) <= 3) {
                    kon.setText("Sedang");

                    FirebaseDatabase kondisiDB = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = kondisiDB.getReference("KondisiLarutan");
                    myRef.setValue("Sedang");


                } else if (Float.parseFloat(nilaiPH) <= 4) {
                    kon.setText("Buruk");

                    FirebaseDatabase kondisiDB = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = kondisiDB.getReference("KondisiLarutan");
                    myRef.setValue("Buruk");

                }
            }

                /*if (Float.parseFloat(nilaiPH) <= 3) {

                    FirebaseDatabase kondisiDB = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = kondisiDB.getReference("Kondisi");
                    myRef.setValue(0);

                } else if (Float.parseFloat(nilaiPH)<= 4) {

                    FirebaseDatabase kondisiDB = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = kondisiDB.getReference("Kondisi");
                    myRef.setValue(1);


                } else if (Float.parseFloat(nilaiPH) >= 5) {

                    FirebaseDatabase kondisiDB = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = kondisiDB.getReference("Kondisi");
                    myRef.setValue(2);

                    }*/

               /* if (Integer.parseInt(Kondisi)==0){
                    notification1();
                } else if (Integer.parseInt(Kondisi)==1) {
                    notification2();
                } else if (Integer.parseInt(Kondisi)==2) {
                    notification3();
                }*/

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mykond = db.getReference("KondisiLarutan");

        mykond.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                knd = dataSnapshot.getValue(String.class);
                if ((knd) == "Baik"){
                    notification1();
                } else if ((knd) == "Sedang"){
                    notification2();
                } else if ((knd) == "Buruk"){
                    notification3();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        FirebaseDatabase dbknd = FirebaseDatabase.getInstance();
        DatabaseReference myknd = dbknd.getReference("Kondisi");

        myknd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kondisi = dataSnapshot.getValue(String.class);
                if (Integer.parseInt(kondisi) == 0){
                    notification4();

                    DatabaseReference myknd = dbknd.getReference("Kondisi");
                    myknd.setValue("3");

                } else if (Integer.parseInt(kondisi) == 1){
                    notification5();

                    DatabaseReference myknd = dbknd.getReference("Kondisi");
                    myknd.setValue("3");

                } else if (Integer.parseInt(kondisi) == 2){
                    notification6();

                    DatabaseReference myknd = dbknd.getReference("Kondisi");
                    myknd.setValue("3");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        btnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("GantiLarutan");

                myRef.setValue(1);


                Toast.makeText(MainActivity.this, "Pengantian larutan aktif", Toast.LENGTH_LONG).show();
            }
        });

        btnsingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("TipePCB");

                myRef.setValue(3);

                Toast.makeText(MainActivity.this, "Etching PCB Single Layer", Toast.LENGTH_LONG).show();
            }
        });

        btndouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("TipePCB");

                myRef.setValue(4);

                Toast.makeText(MainActivity.this, "Etching PCB Double Layer", Toast.LENGTH_LONG).show();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void notification1() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                    .setContentText("Ta PCB")
                    .setContentTitle("Peringatan Kondisi Larutan")
                    .setColor(213123)
                    .setSmallIcon(R.drawable.phmonitor)
                    .setAutoCancel(true)
                    .setContentText("Kondisi PH Baik");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    private void notification2() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentText("Ta PCB")
                .setContentTitle("Peringatan Kondisi Larutan")
                .setColor(213123)
                .setSmallIcon(R.drawable.phmonitor)
                .setAutoCancel(true)
                .setContentText("Kondisi PH Sedang");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    private void notification3() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentText("Ta PCB")
                .setContentTitle("Peringatan Kondisi Larutan")
                .setColor(213123)
                .setSmallIcon(R.drawable.phmonitor)
                .setAutoCancel(true)
                .setContentText("Kondisi PH Buruk");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    private void notification4() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentText("Ta PCB")
                .setContentTitle("Peringatan Pergantian Larutan")
                .setColor(213123)
                .setSmallIcon(R.drawable.phmonitor)
                .setAutoCancel(true)
                .setContentText("Pergantian Larutan FeCl3 Telah Selesai");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    private void notification5() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentText("Ta PCB")
                .setContentTitle("Single Layer")
                .setColor(213123)
                .setSmallIcon(R.drawable.phmonitor)
                .setAutoCancel(true)
                .setContentText("Etching PCB Single Layer Telah Selesai");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    private void notification6() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentText("Ta PCB")
                .setContentTitle("Double Layer")
                .setColor(213123)
                .setSmallIcon(R.drawable.phmonitor)
                .setAutoCancel(true)
                .setContentText("Etching PCB Double Layer Telah Selesai");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }
}