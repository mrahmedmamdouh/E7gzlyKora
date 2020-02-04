package com.example.android.e7gzlykora.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.databinding.ActivitySearchBinding;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.Bookings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class searchActivity extends Fragment {
    private static final String TAG = searchActivity.class.getSimpleName();
    private int year, month, day;
    private static Date date;
    private String WeeklyTime,SingleTime;
    private Button chooseDate;
    private ActivitySearchBinding binding;

    public searchActivity(ActivitySearchBinding binding) {
        this.binding = binding;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
    }

    private void init() {
        Calendar now = Calendar.getInstance();
        year = now.get(java.util.Calendar.YEAR);
        month = now.get(java.util.Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_MONTH);
        String[] items = new String[]{"Cairo", "Giza", "Alexandria", "Others"};
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemSelected(AdapterView <?> parent, View view,
                                       int position, long id) {

                if (binding.spinner.getSelectedItem().equals("Cairo")) {
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),
                            R.array.cairo, android.R.layout.simple_spinner_item);
                    binding.spinner2.setAdapter(adapter2);
                } else if (binding.spinner.getSelectedItem().equals("Giza")) {
                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),
                            R.array.Giza, android.R.layout.simple_spinner_item);
                    binding.spinner2.setAdapter(adapter3);
                } else if (binding.spinner.getSelectedItem().equals("Alexandria")) {
                    ArrayAdapter adapter4 = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),
                            R.array.Alex, android.R.layout.simple_spinner_item);
                    binding.spinner2.setAdapter(adapter4);
                } else {
                    ArrayAdapter adapter5 = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),
                            R.array.Others, android.R.layout.simple_spinner_item);
                    binding.spinner2.setAdapter(adapter5);
                }
            }


            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                // TODO Auto-generated method stub
            }


        });

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter <CharSequence> adapter3 = ArrayAdapter
                .createFromResource(getActivity(), R.array.Time,
                        android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.spinner3.setAdapter(adapter3);

        ArrayAdapter <CharSequence> adapter1 = ArrayAdapter
                .createFromResource(getActivity(), R.array.Time,
                        android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.spinner4.setAdapter(adapter1);

    }

    private void onClick() {
        binding.checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkbox1.isChecked()) {
                    SingleTime = binding.checkbox1.getText().toString();
                }

            }
        });

        binding.checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.checkbox2.isChecked()) {
                    WeeklyTime = binding.checkbox2.getText().toString();
                }


            }
        });

        binding.chooseDate.setOnClickListener(new View.OnClickListener() {
            @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String picker = dayOfMonth + "-" + (month + 1) + "-" + year;
                        date = null;
                        try {
                            date = formatter.parse(picker);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Log.d("=====", String.valueOf(date));
                        chooseDate.setText(formatter.format(date));
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        binding.search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final String fromtime = binding.spinner3.getSelectedItem().toString();
                final String totime = binding.spinner4.getSelectedItem().toString();
                final String zone3 = binding.spinner.getSelectedItem().toString();
                final String zone4 = binding.spinner2.getSelectedItem().toString();
                Intent intent1 = new Intent(getActivity(), prospectowner_listview.class);
                Bookings.getInstance().setZone1(zone3);
                Bookings.getInstance().setZone2(zone4);
                Bookings.getInstance().setBookingTimeFrom(fromtime);
                Bookings.getInstance().setBookingTimeTo(totime);
                if (SingleTime != null) {
                    Bookings.getInstance().setDateTypes(SingleTime);
                }else if (WeeklyTime != null){
                    Bookings.getInstance().setDateTypes(WeeklyTime);
                }else  {
                    Bookings.getInstance().setDateTypes(SingleTime);
                }
                Bookings.getInstance().setDateDetails(String.valueOf(date));
                Bookings.getInstance().setUserMobile(Auth.getInstance().getMobile());
                Bookings.getInstance().setUserName(Auth.getInstance().getUserName());
                Bookings.getInstance().setNameUser(Auth.getInstance().getName());
                startActivity(intent1);
            }
        });


    }



}

