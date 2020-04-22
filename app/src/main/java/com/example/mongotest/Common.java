package com.example.mongotest;

import com.example.mongotest.Class.User;

public class Common {
    private static String DB_NAME ="LogoQuizDB";
    private static String COLLECTION_NAME = "TestCollection";
    public static String API_KEY = "mongodb+srv://bbes:<password>@logoquitcluster-jtlho.mongodb.net/test?retryWrites=true&w=majority";

    public static String getAdressSingle(User user){
        String baseURL = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s", DB_NAME, COLLECTION_NAME);
        StringBuilder stringBuilder = new StringBuilder(baseURL);
        stringBuilder.append("/"+user.get_id().getOid()+"?apiKey="+API_KEY);
        return stringBuilder.toString();

    }

    public static String getAdressAPI(){
        String baseURL = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s", DB_NAME, COLLECTION_NAME);
        StringBuilder stringBuilder = new StringBuilder(baseURL);
        stringBuilder.append("?apiKey="+API_KEY);
        return stringBuilder.toString();

    }

}
