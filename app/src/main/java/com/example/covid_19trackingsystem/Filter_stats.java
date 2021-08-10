package com.example.covid_19trackingsystem;

import android.widget.Adapter;
import android.widget.Filter;

import java.util.ArrayList;

public class Filter_stats extends Filter {
    private RVAdapter_Stats adapter_stats;
    private ArrayList<Model_Statistics> filterlist;

    public Filter_stats(RVAdapter_Stats adapter_stats, ArrayList<Model_Statistics> filterlist) {
        this.adapter_stats = adapter_stats;
        this.filterlist = filterlist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults  results = new FilterResults();
        //check constraint validity
        if(constraint != null && constraint.length() > 0){
            //change to uppercase
            constraint = constraint.toString().toUpperCase();
            //store our filtered records
            ArrayList<Model_Statistics> filteredmodles = new ArrayList<>();
            for (int i=0 ; i<filterlist.size() ; i++){
                if (filterlist.get(i).getCountry().toUpperCase().contains(constraint)){

                    filteredmodles.add(filterlist.get(i));
                }


            }

            results.count= filteredmodles.size();
            results.values = filteredmodles;

        }
        else{

            results.count=filterlist.size();
            results.values=filterlist;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter_stats.StatsArrayList=(ArrayList<Model_Statistics>) results.values;
        //refresh list
        adapter_stats.notifyDataSetChanged();
    }
}