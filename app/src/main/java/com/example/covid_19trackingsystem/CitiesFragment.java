package com.example.covid_19trackingsystem;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CitiesFragment extends Fragment {

    

    private static  final String Stats_URL=" https://api.covid19api.com/summary ";
    //Context for fragmet
    Context context;


    //UI views

    private EditText searchEt;
    private ImageButton sortbtn;
    private RecyclerView stats_Rv;
    private ArrayList<Model_Statistics> StatArrayList ;
    private RVAdapter_Stats adapter_stats;


    public CitiesFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        //init UI views

        searchEt = view.findViewById(R.id.SearchEt);
        sortbtn = view.findViewById(R.id.SortBtn);
        stats_Rv=view.findViewById(R.id.StatisticsRv);



        LoadStatsData();
        //Search edit text
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //when user type letter or remove letter
                try {

                    adapter_stats.getFilter().filter(s);

                }
                catch (Exception ee){

                    ee.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //popup menu to show sorting options
        //PopupMenu popupMenu = new PopupMenu(context,sortbtn);
         final   PopupMenu popupMenu = new PopupMenu(context,sortbtn);
        popupMenu.getMenu().add(Menu.NONE,0,0,"Ascending Order");
        //first parameter is the id of item and the second parameter is the position of item in the list
        popupMenu.getMenu().add(Menu.NONE,1,1,"Descending Order");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               int id = item.getItemId();
               if(id ==0){
                   Collections.sort(StatArrayList,new SortStatsAsc());
                   adapter_stats.notifyDataSetChanged();

               }
               else if (id ==1){
                   Collections.sort(StatArrayList,new SortStatsDesc());
                   adapter_stats.notifyDataSetChanged();
               }

                return false;
            }
        });


        // Sorting
        sortbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //show menu
               popupMenu.show();

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadStatsData();
    }

    private void LoadStatsData(){


        //API Calling
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Stats_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //got response
                handleResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //failed getting response

                Toast.makeText(context,""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void handleResponse(String response) {
        StatArrayList = new ArrayList<>();
        StatArrayList.clear();
        try {
            //we have JSON Object as Response
            JSONObject jsonObject = new JSONObject(response);
            // and then we have array of Records
            JSONArray jsonArray = jsonObject.getJSONArray("Countries");
            GsonBuilder  gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
            Gson gson = gsonBuilder.create();

            //Start getting data
            for(int i=0 ; i<jsonArray.length();i++){
                Model_Statistics model_statistics = gson.fromJson(jsonArray.getJSONObject(i).toString(),Model_Statistics.class);
                StatArrayList.add(model_statistics);

            }

            //Setup adapter
            adapter_stats = new RVAdapter_Stats(context,StatArrayList);
            stats_Rv.setAdapter(adapter_stats); //set adapter to recycler view



        }
        catch (Exception e){

            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



        ////////////////// Sorting countries in ascending order
    public class SortStatsAsc implements Comparator<Model_Statistics> {

        @Override
        public int compare(Model_Statistics left, Model_Statistics right) {
            return left.getCountry().compareTo(right.getCountry());
        }
    }



    ////////////////// Sorting countries in descending order
   public class SortStatsDesc implements Comparator<Model_Statistics>{

        @Override
        public int compare(Model_Statistics left, Model_Statistics right) {
            return right.getCountry().compareTo(left.getCountry());
        }
    }

}