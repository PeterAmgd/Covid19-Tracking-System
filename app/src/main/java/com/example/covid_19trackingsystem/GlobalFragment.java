package com.example.covid_19trackingsystem;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;


public class GlobalFragment extends Fragment {





    private static  final String Stats_URL="https://api.covid19api.com/summary";

    //Context for fragments
    Context context;
    //UI Views

    private ProgressBar progressBar;

    private TextView Total_Cases,New_Cases,Total_death_Cases,
    new_death_Cases,Total_Recovered_Cases , New_Recovered_Cases ;



    public GlobalFragment() {
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
        View view = inflater.inflate(R.layout.fragment_global, container, false);

        //init UI views

        Total_Cases = view.findViewById(R.id.Total_Cases_Tv);
        New_Cases = view.findViewById(R.id.New_Cases_Tv);
        Total_death_Cases = view.findViewById(R.id.death_Cases_Tv);
        new_death_Cases = view.findViewById(R.id.new_death_Cases_Tv);
        Total_Recovered_Cases = view.findViewById(R.id.Total_Recovered_Cases_Tv);
        New_Recovered_Cases = view.findViewById(R.id.New_Recovered_Cases_Tv);

        progressBar = view.findViewById(R.id.progressBarglobal);

//        progress_Bar.setVisibility(View.GONE);
        LoadHomeData();

        return view;


    }




    @Override
    public void onResume() {
        super.onResume();
        LoadHomeData();
    }

    private void LoadHomeData(){
            //show ProgressBar
        progressBar.setVisibility(View.VISIBLE);
        //Json String Request
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Stats_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response Received , handle response
                handleResponse(response);
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // some error will occured , hide progress , show error message
              //  progress_Bar.setVisibility(View.GONE);
                Toast.makeText(context,""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void handleResponse(String response) {
        try {
            //our response in JSON Object so we will Convert it into Object
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Global_JO = jsonObject.getJSONObject("Global");
            // get data from it
            String TotalConfirmed = Global_JO.getString("TotalConfirmed");
            String NewConfirmed = Global_JO.getString("NewConfirmed");
            String TotalDeaths = Global_JO.getString("TotalDeaths");
            String NewDeaths = Global_JO.getString("NewDeaths");
            String TotalRecovered = Global_JO.getString("TotalRecovered");
            String NewRecovered = Global_JO.getString("NewRecovered");


                //Set Data after got it
                    Total_Cases.setText(TotalConfirmed);
                    New_Cases.setText(NewConfirmed);
                    Total_death_Cases.setText(TotalDeaths);
                    new_death_Cases.setText(NewDeaths);
                    Total_Recovered_Cases.setText(TotalRecovered);
                    New_Recovered_Cases.setText(NewRecovered);

               //hide progressBar
          //  progress_Bar.setVisibility(View.GONE);

        }

        catch (Exception e){
//            progress_Bar.setVisibility(View.GONE);
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}