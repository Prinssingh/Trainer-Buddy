package com.example.covid_19tracker;

public class CountryModel {

    private String flag,country,cases,todayCases,totalDeaths,
                    todayDeaths,recovered,activeCases,criticalCases,population;


    public CountryModel(String flag, String country, String cases, String todayCases, String totalDeaths, String todayDeaths, String recovered, String activeCases, String criticalCases, String population) {
        this.flag = flag;
        this.country = country;
        this.cases = cases;
        this.todayCases = todayCases;
        this.totalDeaths = totalDeaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.activeCases = activeCases;
        this.criticalCases = criticalCases;
        this.population = population;
    }

    public CountryModel() {
    }

    public String getFlag() {
        return flag;
    }

    public String getCountry() {
        return country;
    }

    public String getCases() {
        return cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public String getCriticalCases() {
        return criticalCases;
    }

    public String getPopulation() {
        return population;
    }

}
