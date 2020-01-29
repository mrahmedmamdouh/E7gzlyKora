package com.example.android.e7gzlykora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.Owner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ownerprofile extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    TextView name,mobilePhone,field,addressUser,costHour,zone1,zone2;
    Button editprofile,logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ownerprofile);

         editprofile = (Button) findViewById(R.id.edit);
        name = findViewById(R.id.name);
        mobilePhone = findViewById(R.id.mobilephone);
        field = findViewById(R.id.field);
        addressUser = findViewById(R.id.addressuser);
        logOut = findViewById(R.id.logOut);
        costHour = findViewById(R.id.costhour);
        zone1 = findViewById(R.id.zone1);
        zone2 = findViewById(R.id.zone2);

        GetData();
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ownerprofile.this,LoginActivity.class);
                startActivity(i);
            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ownerprofile.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void GetData() {
        String Mobile = Owner.getInstance().getMobile();
        AndroidNetworking.get("http://192.168.2.222:8089/api/Owner/GetData")
                .addQueryParameter("Mobile",Mobile)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG", "onResponse: "+ response);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = null;
                            try {
                                obj = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Owner owner = new Owner();
                            try {
                                assert obj != null;
                                owner.setName(obj.getString("Name"));
                                Owner.getInstance().setName(owner.getName());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setMobile(obj.getString("Mobile"));
                                Owner.getInstance().setMobile(owner.getMobile());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setAddress(obj.getString("Address"));
                                Owner.getInstance().setAddress(owner.getAddress());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setFieldName(obj.getString("FieldName"));
                                Owner.getInstance().setFieldName(owner.getFieldName());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setCost(Double.parseDouble(obj.getString("Cost")));
                                Owner.getInstance().setCost(owner.getCost());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setZone1(obj.getString("Zone1"));
                                Owner.getInstance().setZone1(owner.getZone1());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                owner.setZone2(obj.getString("Zone2"));
                                Owner.getInstance().setZone2(owner.getZone2());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        name.setText(Owner.getInstance().getName());
                        mobilePhone.setText(Owner.getInstance().getMobile());
                        addressUser.setText(Owner.getInstance().getAddress());
                        costHour.setText(String.valueOf(Owner.getInstance().getCost()));
                        field.setText(Owner.getInstance().getFieldName());
                        zone1.setText(Owner.getInstance().getZone1());
                        zone2.setText(Owner.getInstance().getZone2());

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+ anError);

                    }
                });

    }
}
