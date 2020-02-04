package com.example.android.e7gzlykora.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.adapters.Booking_Items_Adapter;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.PendingItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OwnerPendingBookings extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private static Booking_Items_Adapter adapter;
    private PendingItems pendingItems;
    private ArrayList<PendingItems> pendingItemsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_pending_bookings);
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        getData(Auth.getInstance().getMobile());
    }

    private void getData(String OwnerMobileNo) {
        AndroidNetworking.get("http://192.168.2.8:8089/api/Bookings/Pending")
                .addQueryParameter("OwnerMobileNo",OwnerMobileNo)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener(){
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = null;
                            pendingItems = new PendingItems();
                            try {
                                obj = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                assert obj != null;
                                pendingItems.setNameUser(String.valueOf(obj.get("NameUser")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                pendingItems.setUserMobile(String.valueOf(obj.get("UserMobile")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                pendingItems.setDateTypes(String.valueOf(obj.get("DateDetails")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                pendingItems.setBookingTimeFrom(String.valueOf(obj.get("BookingTimeFrom")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                pendingItems.setBookingTimeTo(String.valueOf(obj.get("BookingTimeTo")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pendingItemsArrayList.add(pendingItems);
                        }
                        adapter = new Booking_Items_Adapter(OwnerPendingBookings.this, pendingItemsArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: "+anError );
                    }
                });
    }

    public static void Confirm(String TimeFrom, String TimeTo,final Context mContext, String OwnerMobileNo, String UserMobile, final int Position, final ArrayList<PendingItems> pendingItemsArrayList){

        AndroidNetworking.get("http://192.168.2.8:8089/api/Bookings/UpdateToConfirm")
                .addQueryParameter("status","Confirmed")
                .addQueryParameter("OwnerMobileNo",OwnerMobileNo)
                .addQueryParameter("UserMobile",UserMobile)
                .addQueryParameter("TimeFrom",TimeFrom)
                .addQueryParameter("TimeTo",TimeTo)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mContext,response,Toast.LENGTH_SHORT).show();
                        pendingItemsArrayList.remove(Position);
//                        recyclerView.removeViewAt(Position);
                        adapter.notifyItemRemoved(Position);
                        adapter.notifyItemRangeChanged(Position, pendingItemsArrayList.size());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: "+anError );
                    }
                });
    }

    public static void Reject(String TimeFrom, String TimeTo,final Context mContext, String OwnerMobileNo, String UserMobile, final int Position, final ArrayList<PendingItems> pendingItemsArrayList) {

        AndroidNetworking.get("http://192.168.2.8:8089/api/Bookings/UpdateToConfirm")
                .addQueryParameter("status","reject")
                .addQueryParameter("OwnerMobileNo",OwnerMobileNo)
                .addQueryParameter("UserMobile",UserMobile)
                .addQueryParameter("TimeFrom",TimeFrom)
                .addQueryParameter("TimeTo",TimeTo)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mContext,response,Toast.LENGTH_SHORT).show();
                        pendingItemsArrayList.remove(Position);
//                        recyclerView.removeViewAt(Position);
                        adapter.notifyItemRemoved(Position);
                        adapter.notifyItemRangeChanged(Position, pendingItemsArrayList.size());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: "+anError );
                    }
                });

    }
}
