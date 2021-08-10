package com.example.covid_19trackingsystem;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  {
    private FirebaseAuth firebaseAuth;



   /*    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
           ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            Intent intent = new Intent(MainActivity.this , COVID19_Statistics.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(MainActivity.this , login.class);
            startActivity(intent);
            finish();
        }*/





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);


        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            Intent intent = new Intent(MainActivity.this , COVID19_Statistics.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(MainActivity.this , login.class);
            startActivity(intent);
            finish();
        }

   

      /*  FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String  token = Objects.requireNonNull(task.getResult()).getToken();

                        }

                    }
                });*/

        //Intent intent = new Intent(MainActivity.this,BluetoothDiscovery.class);
        //tartActivity(intent);

     //   FirebaseMessaging.getInstance().subscribeToTopic("all");


      //  notifications();




      /* Intent intent = new Intent(MainActivity.this ,BluetoothDiscovery.class);
        startActivity(intent);*/
       /* NotificationAdapter notificationAdapter = new NotificationAdapter(this);
        notificationAdapter.highPriorityNotifications("momken","eshta3'let");*/




      /*  Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        String formattedDate = df.format(date);
        System.out.println(formattedDate);*/








       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShowBluetoothActivity();
                Intent intent = new Intent(MainActivity.this , login.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   ShowMapActivity();
                Intent intent = new Intent(MainActivity.this , registration.class);
                startActivity(intent);
            }
        });

*/

    }
    /*public static String returnMeFCMtoken() {
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isComplete()){
                    token[0] = task.getResult();
                    Log.e("AppConstants", "onComplete: new Token got: "+token[0] );

                }
            }
        });
        return token[0];
    }

    private void notifications(){

    if (!title.isEmpty() && !msg.isEmpty()){

        FcmNotificationsSender fcmNotificationsSender
                = new FcmNotificationsSender("/topics/all",title,msg,getApplicationContext(),MainActivity.this);

        fcmNotificationsSender.SendNotifications();

    }
    else {
        Toast.makeText(MainActivity.this,"enter the message and title",Toast.LENGTH_SHORT).show();
    }

    }*/


    private void ShowMapActivity(){
        //here we implement Intent class then take object from it
        // we pass two parameters , first one is the context, the second is other activity
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }




    //LogoutMethod
    private void  Logout(){
       FirebaseAuth.getInstance().signOut();
       Intent intent = new Intent(MainActivity.this , login.class);
       startActivity(intent);
       finish();
    }


}