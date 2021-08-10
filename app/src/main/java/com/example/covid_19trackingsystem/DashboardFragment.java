package com.example.covid_19trackingsystem;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;


public class DashboardFragment extends Fragment {


    DrawerLayout drawerLayout;

    NavigationView navView;
    ImageView imageView1;


    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private static final String TAG = "BluetoothDiscovery";

    // GUI Components
    private TextView mBluetoothStatus;


    String id;
    private Button mDiscoverBtn;
    private ListView mDevicesListView;


    private BluetoothAdapter mBTAdapter;
    private ArrayAdapter<String> mBTArrayAdapter;


    protected ArrayList<String> DeviceNameList = new ArrayList<>();
    protected ArrayList<String> MacAddressList = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> uids = new ArrayList<>();
    ArrayList<WarningNotification> msganddate = new ArrayList<>();

    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    NotificationAdapter  notificationAdapter;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();




    //Context for fragments
    Context context;
    //UI Views
    ToggleButton toggle;
    ImageButton imagbutton;
    ImageButton imgbell;
    Button infectedbtn ;
    TextView report;
    String uuid;
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       // getNotification();



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //init UI views
        //  progress_Bar = view.findViewById(R.id.progressBar);
        toggle = view.findViewById(R.id.toggleButton);
       // ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);

        imagbutton = view.findViewById(R.id.imageButtoncall);
        infectedbtn = view.findViewById(R.id.infected);
        imgbell = view.findViewById(R.id.imageButtonbell);


        mDiscoverBtn = view.findViewById(R.id.button2);
        db = FirebaseFirestore.getInstance();
        mBTArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDevicesListView = view.findViewById(R.id.devices_list_view);
//        mDevicesListView.setAdapter(mBTArrayAdapter); // assign model to view
        firebaseAuth = FirebaseAuth.getInstance();
        report= view.findViewById(R.id.reportTV);
        report.setVisibility(View.VISIBLE);


        navView = view.findViewById(R.id.nav_view);
        drawerLayout = view.findViewById(R.id.drawer_layout);

        imageView1 = view.findViewById(R.id.bt_menu);

// initiate a DrawerLayout
        drawerLayout.closeDrawers();

// close all the Drawers

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);



//        progress_Bar.setVisibility(View.GONE);
        //LoadHomeData();

        navView.bringToFront();

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });







        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.item_profile) {
                    openProfileActivity();
                }
                else if(id==R.id.item_logout){
                    logout();
                }
                else if(id==R.id.item_instruction)
                    openInstructionActivity();
                return false;
            }
        });



        return view;


    }

    private void openInstructionActivity() {
        startActivity(new Intent(getActivity(),InstructionActivity.class));
    }


    private void logout() {
        firebaseAuth.signOut();
        if (firebaseAuth.getCurrentUser() == null) {


            Intent intent = new Intent(context , login.class);
            startActivity(intent);
            getActivity().finish();

            return;
        }

    }

    private void openProfileActivity() {
        Intent intent = new Intent(context , ProfileActivity.class);
        startActivity(intent);
        getActivity().finish();
        return;
    }




protected void call(){
    Intent x = new Intent(Intent.ACTION_CALL);
    x.setData(Uri.parse("tel : 000000000000")); // fake number we can replace it with the hotline number
    startActivity(x);
}


    @Override
    public void onResume() {
        super.onResume();



        imgbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowActivity.class);
                startActivity(intent);
               // getNotification();
            }
        });

        imagbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();                   // Calling Covid 19 emergency hotline


            }
        });

        infectedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String temp : days) {
                    sendNotification(temp);





                }
                getNotification();



                infectedbtn.setEnabled(false);
                infectedbtn.setVisibility(View.GONE);
                report.setVisibility(View.VISIBLE);

            }
        });



        if (mBTArrayAdapter == null) {
            // Device does not support Bluetooth
          //  mBluetoothStatus.setText("Status: Bluetooth not found");
            Toast.makeText(context, "Bluetooth device not found!", Toast.LENGTH_SHORT).show();
            return;
        } else {


            //bluetoothOn();

            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled

                        bluetoothOn();
                        getNotification();

                    } else {
                        // The toggle is disabled
                        bluetoothOff();


                    }
                }
            });


            mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    discover();
                    getallpeople();
                }
            });

        }
    }


    public void getNotification(){



        ref.child("WarningNotification").child(getLocalMacAddressFromIp()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msganddate.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    WarningNotification warningNotification = dataSnapshot.getValue(WarningNotification.class);


                    //if (warningNotification.getReceiverID().equalsIgnoreCase(getLocalMacAddressFromIp()) ) {
                        System.out.println(warningNotification.getReceiverID() + "/" + getLocalMacAddressFromIp());
                        String s = warningNotification.getMsg() +"\n"+warningNotification.getDate();
                        Sendingnotifiy sendingnotifiy = new Sendingnotifiy(context);
                        sendingnotifiy.highPriorityNotifications("Warning",s);

                    //}
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void sendNotification(String receiver){


        id = ref.push().getKey();

        WarningNotification wrnNotify =
                new WarningNotification("Sorry for that but you Contact some one infected last days "
                        ,getLocalMacAddressFromIp(),
                        receiver,getcurrentdate(),id );


        ref.child("WarningNotification").child(receiver).child(id).setValue(wrnNotify);


    }

    public String getcurrentdate(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = (day) + "/" + (month + 1) + "/" + year;
        return date;
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








    private void bluetoothOn() {
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//           mBluetoothStatus.setText("Bluetooth enabled");
            Toast.makeText(context, "Bluetooth turned on", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

    // Enter here after user selects "yes" or "no" to enabling radio
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent Data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == -1) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
//                mBluetoothStatus.setText("Enabled");
            } else{}
  //              mBluetoothStatus.setText("Disabled");
        }
    }

    private void bluetoothOff() {
        mBTAdapter.disable(); // turn off
//        mBluetoothStatus.setText("Bluetooth disabled");
        Toast.makeText(context, "Bluetooth turned Off", Toast.LENGTH_SHORT).show();
    }

    private void discover() {
        // Check if the device is already discovering
        if (mBTAdapter.isDiscovering()) {
            mBTAdapter.cancelDiscovery();
            Toast.makeText(context, "Discovery stopped", Toast.LENGTH_SHORT).show();
        } else {
            if (mBTAdapter.isEnabled()) {
                mBTArrayAdapter.clear(); // clear items
                DeviceNameList.clear();
                MacAddressList.clear();
                mBTAdapter.startDiscovery();
                Toast.makeText(context, "Discovery started", Toast.LENGTH_SHORT).show();
              context.registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

            } else {
                Toast.makeText(context, "Bluetooth not on", Toast.LENGTH_SHORT).show();
            }

        }
    }

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // add the name to the list
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mBTArrayAdapter.notifyDataSetChanged();
                DeviceNameList.add(device.getName());
                MacAddressList.add(device.getAddress());


                saveContactPersons();


            }


        }
    };


    protected void saveContactPersons(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = (day) + "/" + (month + 1) + "/" + year;
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = dateFormat.format(new Date());


        for(int i=0 ; i<DeviceNameList.size();i++) {

            ContactPersons  contactPersons = new ContactPersons(date, time ,DeviceNameList.get(i),MacAddressList.get(i),getLocalMacAddressFromIp());

            db.collection("Users").document(""+getLocalMacAddressFromIp()).collection("ContactPersons").document(""+MacAddressList.get(i))
                    .set(contactPersons).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

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


    private static String getLocalMacAddressFromIp() {
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