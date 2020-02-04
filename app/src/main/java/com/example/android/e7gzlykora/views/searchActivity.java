package com.example.android.e7gzlykora.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.Bookings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class searchActivity extends AppCompatActivity {
    private static final String TAG = searchActivity.class.getSimpleName();
    DatePickerDialog.OnDateSetListener mDateSetListener = null;
    String mobile;
    String name;
    private int year, month, day;
    private Calendar now;
    public static Date date;
    private String WeeklyTime,SingleTime;
    Button chooseDate;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_search);
        now = java.util.Calendar.getInstance();
        year = now.get(java.util.Calendar.YEAR);
        month = now.get(java.util.Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_MONTH);
        final Spinner zone1 = (Spinner) findViewById(R.id.spinner);
        final Spinner zone2 = (Spinner) findViewById(R.id.spinner2);
        chooseDate = findViewById(R.id.chooseDate);
        String[] items = new String[]{"Cairo", "Giza", "Alexandria", "Others"};
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter <String> adapter = new ArrayAdapter <String>(searchActivity.this, android.R.layout.simple_spinner_item, items);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        zone1.setAdapter(adapter);

        zone1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView <?> parent, View view,
                                       int position, long id) {

                if (zone1.getSelectedItem().equals("Cairo")) {
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(searchActivity.this,
                            R.array.cairo, android.R.layout.simple_spinner_item);
                    zone2.setAdapter(adapter2);
                } else if (zone1.getSelectedItem().equals("Giza")) {
                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(searchActivity.this,
                            R.array.Giza, android.R.layout.simple_spinner_item);
                    zone2.setAdapter(adapter3);
                } else if (zone1.getSelectedItem().equals("Alexandria")) {
                    ArrayAdapter adapter4 = ArrayAdapter.createFromResource(searchActivity.this,
                            R.array.Alex, android.R.layout.simple_spinner_item);
                    zone2.setAdapter(adapter4);
                } else {
                    ArrayAdapter adapter5 = ArrayAdapter.createFromResource(searchActivity.this,
                            R.array.Others, android.R.layout.simple_spinner_item);
                    zone2.setAdapter(adapter5);
                }
            }


            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                // TODO Auto-generated method stub
            }


        });

        final Spinner time1 = (Spinner) findViewById(R.id.spinner3);
        final Spinner time2 = (Spinner) findViewById(R.id.spinner4);

        Button search = (Button) findViewById(R.id.search);



// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter <CharSequence> adapter3 = ArrayAdapter
                .createFromResource(this, R.array.Time,
                        android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        time1.setAdapter(adapter3);

        ArrayAdapter <CharSequence> adapter1 = ArrayAdapter
                .createFromResource(this, R.array.Time,
                        android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        time2.setAdapter(adapter1);


        final CheckBox single = (CheckBox) findViewById(R.id.checkbox1);
        final CheckBox weekly = (CheckBox) findViewById(R.id.checkbox2);

        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (single.isChecked()) {
                     SingleTime = single.getText().toString();
                }

            }
        });

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weekly.isChecked()) {
                     WeeklyTime = weekly.getText().toString();
                }


            }
        });
//        final CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
////
////        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
////
////            @Override
////            public void onSelectedDayChange(CalendarView view, int year, int month,
////                                            int dayOfMonth) {
////                int d = dayOfMonth;
////                date = month + "/" + d + "/" + year;
////            }
////        });


        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(searchActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final String fromtime = time1.getSelectedItem().toString();
                final String totime = time2.getSelectedItem().toString();
                final String zone3 = zone1.getSelectedItem().toString();
                final String zone4 = zone2.getSelectedItem().toString();
                Intent intent1 = new Intent(searchActivity.this, prospectowner_listview.class);
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

