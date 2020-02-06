package com.example.android.e7gzlykora.views;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.Utils.FragmentUtils;
import com.example.android.e7gzlykora.databinding.FragmentSearchBinding;
import com.example.android.e7gzlykora.viewmodels.SearchFragmentViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class SearchFragment extends Fragment {
    private int year, month, day;
    private Date date;
    private FragmentSearchBinding binding;


    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.fragment_search);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new SearchFragmentViewModel(getActivity(), binding));
        binding.executePendingBindings();
        return inflater.inflate(R.layout.fragment_search, container, false);
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
            public void onItemSelected(AdapterView<?> parent, View view,
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
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }


        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter
                .createFromResource(getActivity(), R.array.Time,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.spinner3.setAdapter(adapter3);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter
                .createFromResource(getActivity(), R.array.Time,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.spinner4.setAdapter(adapter1);

    }

    private void onClick() {
        binding.checkbox1.setOnClickListener(v -> {
            if (binding.checkbox1.isChecked()) {
                binding.getViewModel().setSingleTime(binding.checkbox1.getText().toString());
            }

        });

        binding.checkbox2.setOnClickListener(v -> {

            if (binding.checkbox2.isChecked()) {
                binding.getViewModel().setWeeklyTime(binding.checkbox2.getText().toString());
            }


        });

        binding.chooseDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    String picker = dayOfMonth + "-" + (month + 1) + "-" + year;
                    date = null;
                    try {
                        date = formatter.parse(picker);
                        binding.getViewModel().setDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.d("=====", String.valueOf(date));
                    binding.chooseDate.setText(formatter.format(date));
                }
            }, year, month, day);

            datePickerDialog.show();
        });

        binding.search.setOnClickListener(view -> {
            if (binding.getViewModel().SaveDataToSearch()) {
                FragmentUtils.addFragment(getActivity(), new Prospectowner_listview(), R.id.search_layout, true);
            }

        });


    }


}

