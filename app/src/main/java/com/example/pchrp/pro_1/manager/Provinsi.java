package com.example.pchrp.pro_1.manager;

import java.security.PublicKey;

public class Provinsi {
    String Topics,Side;
    String Id;
    public Provinsi(String topics,String side,int id){
        Topics = topics;
        Side = side;
        if(id<10&&id>0){
            Id = "00"+id;
        }
        else if(id<100&&id>10){
            Id = "0"+id;
        }
        else {
            Id = id+"";
        };
    }

    public String getTopics(){
        return Topics;
    }
    public String getSide(){
        return Side;
    }
    public String getIdDevelorment(){
        return Id;
    }
}
