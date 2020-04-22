package com.example.mongotest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mongotest.Class.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnAdd,btnEdit,btnDelete;
    EditText edtUser;
    User userSelected = null;
    List<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lstView);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        edtUser = findViewById(R.id.edtUsername);

        //Load data when app opened
        new GetData().execute(Common.getAdressAPI());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userSelected = users.get(position);
                //set text to edit text
                edtUser.setText(userSelected.getUser());
            }
        });

        //Add event to button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostData(edtUser.getText().toString()).execute(Common.getAdressAPI());
            }
        });

        //Because of this function we need the parameter 'userSelected', thus we need to set uS
        //when user clocks on an item in the listView
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PutData(edtUser.getText().toString()).execute(Common.getAdressSingle(userSelected));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteData(userSelected).execute(Common.getAdressSingle(userSelected));
            }
        });

    }

    //Function Process Data
    class GetData extends AsyncTask<String,Void,String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);



        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //Pre Process
            pd.setTitle("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Running process...
            String stream = null;
            String urlString = params[0];

            HTTPDataHandler http = new HTTPDataHandler();
            stream = http.GetHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPreExecute();
            //Done process

            //we will use Gson to parse Json to Class
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>(){}.getType();
            users = gson.fromJson(s,listType); //parse to list
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(), users); // create adapter
            listView.setAdapter(adapter); // set adapter to listview
            pd.dismiss();
        }
    }

    //Function to add new user
    class PostData extends AsyncTask<String,String,String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        String username;

        public PostData(String username) {
            this.username = username;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            String json = "\"user\":\""+username+"\"";
            hh.PostHTTPData(urlString,json);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Refresh Data
            new GetData().execute(Common.getAdressAPI());

            pd.dismiss();
        }
    }

    //Function to edit already existing user
    class PutData extends AsyncTask<String,String,String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        String username;

        public PutData(String username) {
            this.username = username;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            String json = "\"user\":\""+username+"\"";
            hh.PutHTTPData(urlString,json);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Refresh Data
            new GetData().execute(Common.getAdressAPI());

            pd.dismiss();
        }
    }

    //Function to delete already existing user
    class DeleteData extends AsyncTask<String,String,String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        User user;

        public DeleteData(User user) {
            this.user = user;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            String json = "\"user\":\""+user.getUser()+"\"";
            hh.DeleteHTTPData(urlString,json);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Refresh Data
            new GetData().execute(Common.getAdressAPI());

            pd.dismiss();
        }
    }
}
