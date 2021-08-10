package com.example.covid_19trackingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter_Stats extends RecyclerView.Adapter<RVAdapter_Stats.HolderStat> implements Filterable {

    //declaration of Context
    private Context context;
    public ArrayList<Model_Statistics> StatsArrayList,filterList ;
    private Filter_stats filter;


    public RVAdapter_Stats(Context context, ArrayList<Model_Statistics> statsArrayList) {
        this.context = context;
        StatsArrayList = statsArrayList;
        this.filterList = statsArrayList;
    }

    @NonNull
    @Override
    public HolderStat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_stats.xml
        View view = LayoutInflater.from(context).inflate(R.layout.rows_ofstats,parent,false);


        return new HolderStat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderStat holder, int position) {
        //get Data
        Model_Statistics model_statistics = StatsArrayList.get(position);
        String country = model_statistics.getCountry();
        String NewConfirmed = model_statistics.getNewConfirmed();
        String TotalConfirmed = model_statistics.getTotalConfirmed();
        String NewDeaths = model_statistics.getNewDeaths();
        String TotalDeaths = model_statistics.getTotalDeaths();
        String NewRecovered = model_statistics.getNewRecovered();
        String TotalRecovered = model_statistics.getTotalRecovered();


        //Set Data
        holder.countryname.setText(country);
        holder.total_infected_cases.setText(TotalConfirmed);
        holder.new_infected_cases.setText(NewConfirmed);
        holder.total_deaths_cases.setText(TotalDeaths);
        holder.new_deaths_cases.setText(NewDeaths);
        holder.total_recovered_cases.setText(TotalRecovered);
        holder.new_recovered_cases.setText(NewRecovered);

    }

    @Override
    public int getItemCount() {
        return StatsArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new Filter_stats(this,filterList);

        }
        return filter;
    }

    // view holder class
    class HolderStat extends RecyclerView.ViewHolder{

        //UI views
        TextView countryname,
                new_infected_cases,total_infected_cases
                ,new_deaths_cases,total_deaths_cases
                ,new_recovered_cases,total_recovered_cases;


        public HolderStat(@NonNull View itemView) {
            super(itemView);


            //init UI views
            countryname=itemView.findViewById(R.id.Country_name);
            new_infected_cases=itemView.findViewById(R.id.NewCases_RS);
            total_infected_cases=itemView.findViewById(R.id.cases__RS);

            total_deaths_cases=itemView.findViewById(R.id.TotalDeaths_RS);
            new_deaths_cases=itemView.findViewById(R.id.NewDeath_RS);
            total_recovered_cases=itemView.findViewById(R.id.TotalRecovered_RS);
            new_recovered_cases=itemView.findViewById(R.id.NewRecovered_RS);


        }
    }
}
