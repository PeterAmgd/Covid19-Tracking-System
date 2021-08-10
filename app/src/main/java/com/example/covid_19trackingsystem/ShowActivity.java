package com.example.covid_19trackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class ShowActivity extends AppCompatActivity {
     RecyclerView recyclerView;
     NotificationAdapter  notificationAdapter;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> days = new ArrayList<>();
    ArrayList<WarningNotification> msganddate = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        recyclerView=findViewById(R.id.recycler);
        notificationAdapter = new NotificationAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getallpeople();
        getNotification();


    }
    public void getNotification(){

        ref.child("WarningNotification").child(getLocalMacAddressFromIp()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msganddate.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    WarningNotification warningNotification = dataSnapshot.getValue(WarningNotification.class);


    if (warningNotification.getReceiverID().equalsIgnoreCase(getLocalMacAddressFromIp()) ) {
        System.out.println("data found");
        msganddate.add(warningNotification);
        notificationAdapter.setList(msganddate);
        recyclerView.setAdapter(notificationAdapter);


    }
                }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }









    protected void getallpeople(){




        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date="";

        for(int i=0;i<=14;i++) {
            if (day-i>0) {
                date = (day - i) + "/" + (month + 1) + "/" + year;

                db.collection("Users").document(""+getLocalMacAddressFromIp()).collection("ContactPersons")
                        .whereEqualTo("date", ""+date)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // Log.d(TAG, document.getId() + " => " + document.get("macaddress"));

                                        String item = document.getString("macaddress");
                                        days.add(item);

                                    }

                                } else {
                                    //Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }

                        });



            }

            if (day-i==0){

                date= c.getActualMaximum(Calendar.DAY_OF_MONTH) +"/"+ month + "/" +year;

                db.collection("Users").document(""+getLocalMacAddressFromIp()).collection("ContactPersons")
                        .whereEqualTo("date", ""+date)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        String item = document.getString("macaddress");
                                        days.add(item);

                                    }

                                } else {
                                    System.out.println("bug found here"+task.getException());
                                }
                            }
                        });
            }
            if (day-i<0){

                date= (c.getActualMaximum(Calendar.DAY_OF_MONTH) + (day-i) ) +"/"+ month + "/" +year;

                db.collection("Users").document(""+getLocalMacAddressFromIp()).collection("ContactPersons")
                        .whereEqualTo("date", ""+date)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        String item = document.getString("macaddress");
                                        days.add(item);

                                    }

                                } else {
                                    System.out.println("bug found here"+task.getException());
                                }
                            }
                        });
            }

        }

    }
    public static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //List
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) { // Is there any element?
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement(); // Get the next element
                Enumeration<InetAddress> en_ip = ni.getInetAddresses(); // Get an enumeration of an ip address
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }


    protected static String getLocalMacAddressFromIp() {
        String strMacAddr = null;
        try {
            // Get the IpD address
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {

        }

        return strMacAddr;
    }






}