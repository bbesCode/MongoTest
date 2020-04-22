package com.example.mongotest.Class;

import com.google.gson.annotations.SerializedName;

public class Id {
    @SerializedName("$oid")
    public String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
