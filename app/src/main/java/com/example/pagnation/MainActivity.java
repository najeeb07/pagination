package com.example.pagnation;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener {

    //Creating a List of superheroes
    private List<Passenger> passengerList;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CardAdapter adapter;

    String url ="https://api.instantwebtools.net/v1/passenger?page=";

    String url2= "&size=10";

    //Volley Request Queue
    private RequestQueue requestQueue;

    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        passengerList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        adapter = new CardAdapter(passengerList, getApplicationContext());

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        //Calling method to get data to fetch data
        getData();
//        loadCatogaries(requestCount);

        //Adding an scroll change listener to recyclerview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener(this);
        }


    }

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue
//    private JsonArrayRequest getDataFromServer(int requestCount) {
//        //Initializing ProgressBar
//        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
//
//        //Displaying Progressbar
//        progressBar.setVisibility(View.VISIBLE);
//        setProgressBarIndeterminateVisibility(true);
//
//        //JsonArrayRequest of volley
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url + String.valueOf(requestCount)+url2,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        //Calling method parseData to parse the json response
//                        parseData(response);
//                        //Hiding the progressbar
//                        progressBar.setVisibility(View.GONE);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressBar.setVisibility(View.GONE);
//                        //If an error occurs that means end of the list has reached
//                        Toast.makeText(MainActivity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        //Returning the request
//        return jsonArrayRequest;
//    }

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
//        requestQueue.add(getDataFromServer(requestCount));

        requestQueue.add(loadCatogaries(requestCount));
        //Incrementing the request counter
        requestCount++;
    }

    //This method will parse json data
//    private void parseData(JSONArray array) {
//        for (int i = 0; i < array.length(); i++) {
//            //Creating the superhero object
//            SuperHero superHero = new SuperHero();
//            JSONObject json = null;
//            try {
//                //Getting json
//                json = array.getJSONObject(i);
//
//                //Adding data to the superhero object
//                superHero.setImageUrl(json.getString("logo"));
//                superHero.setName(json.getString("name"));
////                superHero.setPublisher(json.getString(Config.TAG_PUBLISHER));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            //Adding the superhero object to the list
//            passengerList.add(superHero);
//        }
//
//        //Notifying the adapter that data has been added or changed
//        adapter.notifyDataSetChanged();
//    }

    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    //Overriden method to detect scrolling
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Ifscrolled at last then
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getdata again
            getData();
            Toast.makeText(this, "new data is loading", Toast.LENGTH_SHORT).show();
        }
    }


    private Request<String> loadCatogaries(int requestCount) {
        //getting the progressbar
        final ProgressDialog progressDialog;

        progressDialog = new ProgressDialog(MainActivity.this);

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        //creating a string request to send request to the url
        StringRequest sr = new StringRequest(Request.Method.GET, url + String.valueOf(requestCount)+url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                mView.showMessage(response);
                progressDialog.dismiss();
                try {


                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray Array = obj.getJSONArray("data");

                    //now looping through all the elements of the json array
                    for (int i = 0; i < Array.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject passengerObject = Array.getJSONObject(i);

                        //creating a hero object and giving them the values from json object
                        Passenger superHero = new Passenger(passengerObject.getString("name"), passengerObject.getInt("trips"));


                        passengerList.add(superHero);
                    }

                    //creating custom adapter object
//                    ListViewAdapter adapter = new ListViewAdapter(heroList, getApplicationContext());
//
//                    //adding the adapter to listview
//                    listView.setAdapter(adapter);
                    //initializing our adapter


                    adapter.UpdateData(passengerList);




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mView.showMessage(error.getMessage());
            }
        });


        //creating a request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //adding the string request to request queue
//        requestQueue.add(sr);
        return sr;
    }


}