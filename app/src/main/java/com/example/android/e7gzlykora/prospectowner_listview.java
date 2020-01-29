package com.example.android.e7gzlykora;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.model.Bookings;
import com.example.android.e7gzlykora.model.Owner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class prospectowner_listview extends AppCompatActivity {

    public static RecyclerView list;
   ArrayList <Owner> ownerlist = new ArrayList <>();
    public static customAdapter adapter;
    public static Context mContext;
    private Owner owner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prospectowner_listview);
        mContext = prospectowner_listview.this;
        list = findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        list.setHasFixedSize(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        GetListData(Bookings.getInstance().getZone1(),Bookings.getInstance().getZone2());
    }



    private void GetListData(String Zone1, String Zone2){
        AndroidNetworking.get("http://192.168.2.222:8089/api/User/GetFields")
                .addQueryParameter("Zone1",Zone1)
                .addQueryParameter("Zone2",Zone2)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener(){
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = null;
                            owner = new Owner();
                            try {
                                obj = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                assert obj != null;
                                owner.setName(String.valueOf(obj.get("Name")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setAddress(String.valueOf(obj.get("Address")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setCost(Double.parseDouble(String.valueOf(obj.get("Cost"))));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setFieldName(String.valueOf(obj.get("FieldName")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setMobile(String.valueOf(obj.get("Mobile")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setZone1(String.valueOf(obj.get("Zone1")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setZone2(String.valueOf(obj.get("Zone2")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ownerlist.add(owner);
                        }
                        adapter = new customAdapter(prospectowner_listview.this, ownerlist);
                        list.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: "+anError );
                    }
                });
    }


    public static void Book(String name, String mobile, String fieldName, final int Position, final ArrayList<Owner> ownerlist) {
        Bookings.getInstance().setOwnerName(name);
        Bookings.getInstance().setOwnerMobile(mobile);
        Bookings.getInstance().setOwnerField(fieldName);
        AndroidNetworking.post("http://192.168.2.222:8089/api/Bookings/Booked")
                .addBodyParameter(Bookings.getInstance())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mContext,response,Toast.LENGTH_SHORT).show();
                        ownerlist.remove(Position);
//                        list.removeViewAt(Position);
                        adapter.notifyItemRemoved(Position);
                        adapter.notifyItemRangeChanged(Position, ownerlist.size());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: "+anError );
                    }
                });
    }

}