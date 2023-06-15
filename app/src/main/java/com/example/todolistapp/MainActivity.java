package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private toDoAdapter mAdapter;
    private ArrayList<toDo> ToDo;
    Thread t;
    Handler h;
    String url = "https://mgm.ub.ac.id/todo.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToDo = new ArrayList<>();
        // Ambil RecycleView dari layout
        rvList = this.findViewById(R.id.rvList);

        this.h = new Handler(Looper.getMainLooper());
        this.t = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObj = response.getJSONObject(i);
                                String Waktu = responseObj.getString("time");
                                String Kegiatan = responseObj.getString("what");
                                ToDo.add(new toDo(Waktu, Kegiatan));
                                h.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //membuat Adapter
                                        //Memasukkan dataset dan context ke dalam adapter
                                        mAdapter = new toDoAdapter(MainActivity.this, (ArrayList<toDo>) ToDo);
                                        // Berikan layout manager ke dalam RecycleView
                                        rvList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        // Memasukkan adapter ke dalam RecycleView
                                        rvList.setAdapter(mAdapter);
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(jsonArrayRequest);
            }
        });
        t.start();
    }
}