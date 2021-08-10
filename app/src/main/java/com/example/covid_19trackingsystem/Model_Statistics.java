package com.example.covid_19trackingsystem;

public class Model_Statistics {
    /*"Country": "ALA Aland Islands",
            "CountryCode": "AX",
            "Slug": "ala-aland-islands",
            "NewConfirmed": 0,
            "TotalConfirmed": 0,
            "NewDeaths": 0,
            "TotalDeaths": 0,
            "NewRecovered": 0,
            "TotalRecovered": 0,
            "Date": "2020-04-05T06:37:00Z"

     */

    String  Country,NewConfirmed,TotalConfirmed,NewDeaths,TotalDeaths,NewRecovered,TotalRecovered;

    public Model_Statistics() {

    }

    public Model_Statistics(String country,  String newConfirmed, String totalConfirmed, String newDeaths, String totalDeaths, String newRecovered, String totalRecovered) {
        Country = country;

        NewConfirmed = newConfirmed;
        TotalConfirmed = totalConfirmed;
        NewDeaths = newDeaths;
        TotalDeaths = totalDeaths;
        NewRecovered = newRecovered;
        TotalRecovered = totalRecovered;

    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }



    public String getNewConfirmed() {
        return NewConfirmed;
    }

    public void setNewConfirmed(String newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public String getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public String getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        NewDeaths = newDeaths;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public String getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(String newRecovered) {
        NewRecovered = newRecovered;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        TotalRecovered = totalRecovered;
    }


}
