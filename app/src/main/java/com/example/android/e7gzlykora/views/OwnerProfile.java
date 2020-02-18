package com.example.android.e7gzlykora.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.databinding.OwnerprofileBinding;
import com.example.android.e7gzlykora.model.Owner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class OwnerProfile extends Fragment {
    private OwnerprofileBinding binding;

    public OwnerProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.ownerprofile);
        binding.setLifecycleOwner(this);
//        binding.setViewModel(new LoginFragmentViewModel(getActivity()));
        binding.executePendingBindings();
        return inflater.inflate(R.layout.ownerprofile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
    }

    private void init() {
        GetData();
    }

    private void onClick() {
        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LoginFragment.class);
                startActivity(i);
            }
        });
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditOwnerProfile.class);
                startActivity(i);
            }
        });
    }

    private void GetData() {
        String Mobile = Owner.getInstance().getMobile();
        AndroidNetworking.get("http://192.168.10.17:8089/api/Owner/GetData")
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
                        binding.name.setText(Owner.getInstance().getName());
                        binding.mobilephone.setText(Owner.getInstance().getMobile());
                        binding.addressuser.setText(Owner.getInstance().getAddress());
                        binding.costhour.setText(String.valueOf(Owner.getInstance().getCost()));
                        binding.field.setText(Owner.getInstance().getFieldName());
                        binding.zone1.setText(Owner.getInstance().getZone1());
                        binding.zone2.setText(Owner.getInstance().getZone2());

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+ anError);

                    }
                });

    }
}
