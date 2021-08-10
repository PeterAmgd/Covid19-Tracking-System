package com.example.covid_19trackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    String uid;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public String username,lastname,email,address,age,phone;
    EditText editTextEmail, editTextUsername,editTextlastname,editTextAge,editTextAddress,editTextPhone;
    Button btn_update;

    protected String MYMACADDRESS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        editTextEmail = findViewById(R.id.profile_user_email);
        editTextUsername = findViewById(R.id.profile_username);
        editTextlastname =findViewById(R.id.profile_user_lastname);
        editTextAge = findViewById(R.id.profile_Age);
        editTextAddress = findViewById(R.id.profile_address);
        editTextPhone =findViewById(R.id.profile_Phone);
        btn_update = findViewById(R.id.profile_btn);





        //update button on click
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
            }
        });

    }
    private void getUserData() {
        firestore.collection("Users").document(getLocalMacAddressFromIp()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if (task.isSuccessful()) {
                    UserDbClass userData = task.getResult().toObject(UserDbClass.class);
                    updateUi(userData);

                } else {
                    String errormessage = task.getException().getLocalizedMessage();
                    Log.i(TAG, "Oncomplete:  " + errormessage);
                    Toast.makeText(ProfileActivity.this, errormessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUi(UserDbClass userData) {

        editTextEmail.setText(userData.getEmail());
        editTextUsername.setText(userData.getFirstname());
        editTextlastname.setText(userData.getLastname());
        editTextAddress.setText(userData.getAddress());
        editTextAge.setText(userData.getAge());
        editTextPhone.setText(userData.getPhoneNumber());


    }
    private void updateUserData() {

        email = editTextEmail.getText().toString().trim();
        username = editTextUsername.getText().toString().trim();
        lastname = editTextlastname.getText().toString().trim();
        age = editTextAge.getText().toString().trim();
        address = editTextAddress.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        if (username.isEmpty()) {

            Toast.makeText(this, "Please write your Name", Toast.LENGTH_SHORT).show();

            editTextUsername.setError("name requierd");
            return;
        }
        else if(age.isEmpty()){
            Toast.makeText(this, "Please write your Age", Toast.LENGTH_SHORT).show();

            editTextAge.setError("age requierd");
            return;
        }
        else if(address.isEmpty()){
            Toast.makeText(this, "Please write your address", Toast.LENGTH_SHORT).show();

            editTextAddress.setError("address requierd");
            return;
        }
        else if(phone.isEmpty()){
            Toast.makeText(this, "Please write your Phone", Toast.LENGTH_SHORT).show();

            editTextPhone.setError("Phone requierd");
            return;
        }

        HashMap<String, Object> usernameMap = new HashMap<>();

        usernameMap.put("name", username);

        firestore.collection("Users").document(getLocalMacAddressFromIp()).update(usernameMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Name Updated", Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = task.getException().getLocalizedMessage();
                    Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });




        HashMap<String, Object> userlastnameMap = new HashMap<>();
        userlastnameMap.put("lastname",lastname);
        firestore.collection("Users").document(getLocalMacAddressFromIp()).update(userlastnameMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "lastName Updated", Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = task.getException().getLocalizedMessage();
                    Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

            }
        });



        HashMap<String, Object> useremailmap = new HashMap<>();
        useremailmap.put("email",email);
        firestore.collection("Users").document(getLocalMacAddressFromIp()).update(useremailmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Email Updated", Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = task.getException().getLocalizedMessage();
                    Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        HashMap<String, Object> useragemap = new HashMap<>();
        useragemap.put("age",age);
        firestore.collection("Users").document(getLocalMacAddressFromIp()).update(useragemap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "age Updated", Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = task.getException().getLocalizedMessage();
                    Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

            }
        });


        HashMap<String, Object> useraddressmap = new HashMap<>();
        useraddressmap.put("address",address);
        firestore.collection("Users").document(getLocalMacAddressFromIp()).update(useraddressmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Address Updated", Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = task.getException().getLocalizedMessage();
                    Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

            }
        });




        HashMap<String, Object> userphonemap = new HashMap<>();
        userphonemap.put("phone",phone);
        firestore.collection("Users").document(getLocalMacAddressFromIp()).update(userphonemap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Phone Updated", Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = task.getException().getLocalizedMessage();
                    Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void Clickback_profile(View view) {
        startActivity(new Intent(this,DashboardFragment.class));

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