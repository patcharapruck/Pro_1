package com.example.pchrp.pro_1.manager;

public class ProvinsiVaccine {
    String Vaccine,Type;

    public ProvinsiVaccine(String vaccine, String type){
        Vaccine = vaccine;
        Type = type;
    }

    public String getTopics(){
        return Vaccine;
    }
    public String getSide(){
        return Type;
    }
}
