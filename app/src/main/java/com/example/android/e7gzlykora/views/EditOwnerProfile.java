package com.example.android.e7gzlykora.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.databinding.FragmentEditOwnerProfileBinding;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.Owner;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


public class EditOwnerProfile extends Fragment {

    private FragmentEditOwnerProfileBinding binding;

    public EditOwnerProfile(FragmentEditOwnerProfileBinding binding) {
        this.binding = binding;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_owner_profile, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
    }


    private void onClick() {
        binding.zone1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (binding.zone1.getSelectedItem().equals("Cairo")) {
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(),
                            R.array.cairo, android.R.layout.simple_spinner_item);
                    binding.zone2.setAdapter(adapter2);
                } else if (binding.zone1.getSelectedItem().equals("Giza")) {
                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getActivity(),
                            R.array.Giza, android.R.layout.simple_spinner_item);
                    binding.zone2.setAdapter(adapter3);
                } else if (binding.zone1.getSelectedItem().equals("Alexandria")) {
                    ArrayAdapter adapter4 = ArrayAdapter.createFromResource(getActivity(),
                            R.array.Alex, android.R.layout.simple_spinner_item);
                    binding.zone2.setAdapter(adapter4);
                } else {
                    ArrayAdapter adapter5 = ArrayAdapter.createFromResource(getActivity(),
                            R.array.Others, android.R.layout.simple_spinner_item);
                    binding.zone2.setAdapter(adapter5);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }


        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Owner.getInstance().setName(Auth.getInstance().getName());
                Owner.getInstance().setMobile(Auth.getInstance().getMobile());
                Owner.getInstance().setFieldName(binding.editField.getText().toString());
                Owner.getInstance().setAddress(binding.editAddress.getText().toString());
                Owner.getInstance().setCost(Double.parseDouble(binding.editCost.getText().toString()));
                Owner.getInstance().setZone1(binding.zone1.getSelectedItem().toString());
                Owner.getInstance().setZone2(binding.zone2.getSelectedItem().toString());
                Owner.getInstance().setOwnerGUID(Auth.getInstance().getUserGUID());
                Owner obj = Owner.getInstance();
                AndroidNetworking.post("http://192.168.10.17:8089/api/Owner/InsertData")
                        .addBodyParameter(obj)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                if (response.equals("\"Owner data has been updated successfully\"")) {
                                    Intent intent = new Intent(getActivity(), OwnerProfile.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getActivity(), anError.getResponse().toString(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {
        binding.editName.setText(Auth.getInstance().getName());
        binding.editMobilePhone.setText(Auth.getInstance().getMobile());

        String[] items = new String[]{"Cairo", "Giza", "Alexandria", "Others"};
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, items);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.zone1.setAdapter(adapter);
    }

}