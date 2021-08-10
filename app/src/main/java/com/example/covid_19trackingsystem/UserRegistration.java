package com.example.covid_19trackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;



public class UserRegistration extends AppCompatActivity  {

    private EditText firstname,lastname,emailTV, passwordTV1,password2TV,phonenumber ,age,address;
    private Button signupBtn;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db;
    private UserDbClass userDbClass;
    private String FirstName,LastName,EMAIL, Password,PhoneNumber ,Age,Address;

    public String  MYUSERID;
    private BluetoothAdapter bluetoothAdapter;
    protected String MYMACADDRESS;
    private String MyUserID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        firstname = findViewById(R.id.inputfirstname);
        lastname = findViewById(R.id.inputlastname);
        emailTV = findViewById(R.id.inputEmail);
        passwordTV1 = findViewById(R.id.inputPass1);
        password2TV = findViewById(R.id.inputPass2);
        phonenumber= findViewById(R.id.inputPhone);
        age = findViewById(R.id.inputAge);
        address = findViewById(R.id.inputAddress);
        signupBtn = findViewById(R.id.buttonRegst);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        MYMACADDRESS = getLocalMacAddressFromIp();
        System.out.println(getLocalMacAddressFromIp()+"-----------------------------------------------------");
       // Register();






        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create account with email and password
                //Create a profile and saving info

                Register();






            }
        });









    }

    protected void SavingInfo(String USERID) {
        FirstName= firstname.getText().toString().trim();
        LastName= lastname.getText().toString().trim();
        EMAIL= emailTV.getText().toString().trim();
        Password=passwordTV1.getText().toString().trim();
        PhoneNumber = phonenumber.getText().toString().trim();
        Age = age.getText().toString().trim();
        Address = address.getText().toString().trim();

        // validating the text fields if empty or not.
        if (TextUtils.isEmpty(FirstName)) {
            firstname.setError("Required Field");
        } else if (TextUtils.isEmpty(LastName)) {
            lastname.setError("Required Field");
        }  else if (TextUtils.isEmpty(PhoneNumber)) {
            phonenumber.setError("Required Field");
        }
        else if (TextUtils.isEmpty(Age)) {
            age.setError("Required Field");
        } else if (TextUtils.isEmpty(Address)) {
            address.setError("Required Field");
        }
        else {

            MyUserID =USERID;
          //  userid=USERID;

            userDbClass = new UserDbClass(FirstName,LastName,EMAIL,Password,PhoneNumber,Age,Address,MyUserID,MYMACADDRESS);
            // add data to Firebase Firestore.
            DocumentReference documentReference = db.collection("Users").document(""+MYMACADDRESS );
            documentReference.set(userDbClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(UserRegistration.this ,"Your Profile Creation Success",Toast.LENGTH_SHORT).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(UserRegistration.this ,"Your Profile Creation Failed",Toast.LENGTH_SHORT).show();

                }
            });

        }


    }

    protected void Register() {

        String EMAIL = emailTV.getText().toString().trim();
        String Password = passwordTV1.getText().toString().trim();
        String  ConfirmPassword = password2TV.getText().toString().trim();

        if (TextUtils.isEmpty(EMAIL)){
            emailTV.setError("Enter your Email");
            return ;
        }
        else if (TextUtils.isEmpty(Password)){
            passwordTV1.setError("Enter your Password");
            return;
        }
        else if (TextUtils.isEmpty(ConfirmPassword)){
            password2TV.setError("Confirm your Password");
            return;
        }

        else if (!Password.equals(ConfirmPassword)){
            passwordTV1.setError("Your Password Not Matching");
            password2TV.setError("Your Password Not Matching");
            return;
        }

        else if (Password.length()<6){
            passwordTV1.setError("Too Short Password");
            return;
        }

        else  if (!ValidEmail(EMAIL)){
            emailTV.setError("Email Not Valid");
            return;
        }
       /* progressDialog.setMessage("Creating Your Account");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);*/

        else {

            mAuth.createUserWithEmailAndPassword(EMAIL, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {




                        MYUSERID = task.getResult().getUser().getUid();
                       // MYUSERID = user.getUid();
                        System.out.println(MYUSERID+"------------------------------------------------------------------------");
                        SavingInfo(MYUSERID);
                        Intent intent = new Intent(UserRegistration.this, login.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(UserRegistration.this, "Successfully Register", Toast.LENGTH_SHORT).show();



                    } else {
                        Toast.makeText(UserRegistration.this, "Register Failed", Toast.LENGTH_SHORT).show();

                    }
                }

            });

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




    private boolean ValidEmail(CharSequence charSequence) {
        return (!TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches());
    }

    private void DeleteAccount(){
        db.collection("Users").document( "")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UserRegistration.this ,"Profile Deleted",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(UserRegistration.this ,"Your Profile Still usable",Toast.LENGTH_SHORT).show();

                    }
                });
    }



}