package com.example.android.e7gzlykora;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.assist.AssistStructure;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class searchActivity extends AppCompatActivity {
    private static final String TAG = searchActivity.class.getSimpleName();
    DatePickerDialog.OnDateSetListener mDateSetListener = null;
    String mobile;
    String name;
    private DatabaseReference mFirebaseDatabase;
    private String UserId;
    private CheckBox single;

    @Override
    public void onCreate(Bundle icicle) {


        super.onCreate(icicle);
        setContentView(R.layout.activity_search);

        final Spinner zone1 = (Spinner) findViewById(R.id.spinner);
        final Spinner zone2 = (Spinner) findViewById(R.id.spinner2);

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
                    String singletime = single.getText().toString();
                }

            }
        });

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weekly.isChecked()) {
                    String weeklytime = weekly.getText().toString();
                }


            }
        });
        final CalendarView calendar = (CalendarView) findViewById(R.id.calendar);


        calendar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        searchActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String x = month + "/" + day + "/" + year;

                calendar.setDate(Long.parseLong(x));

            }
        };


        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("E7gzlykora").setValue("Realtime Database");

        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(searchActivity.this, com.example.android.e7gzlykora.prospectowner_listview.class);
                startActivity(intent1);


                final String fromtime = time1.getSelectedItem().toString();
                final String totime = time2.getSelectedItem().toString();
                final String zone3 = zone1.getSelectedItem().toString();
                final String zone4 = zone2.getSelectedItem().toString();
                String x = String.valueOf(calendar.getDate());

                // Check for already existed userId
                if (TextUtils.isEmpty(UserId)) {
                    createUser(fromtime, totime, zone3, zone4, x);
                } else {

                }
            }
        });

        return;


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createUser(String fromtime, String totime, String zone3, String zone4, String x) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(UserId)) {
            UserId = mFirebaseDatabase.push().getKey();
        }

        if (!TextUtils.isEmpty(fromtime))
            mFirebaseDatabase.child(UserId).child("from").setValue(fromtime);

        if (!TextUtils.isEmpty(totime))
            mFirebaseDatabase.child(UserId).child("to").setValue(totime);
        if (!TextUtils.isEmpty(zone3))
            mFirebaseDatabase.child(UserId).child("Area").setValue(zone3);

        if (!TextUtils.isEmpty(zone4))
            mFirebaseDatabase.child(UserId).child("Zone").setValue(zone4);



        mFirebaseDatabase.child(UserId).child("Date").setValue(x);


        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(UserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "user data is null!");
                    return;
                }

                Log.e(TAG, "user data is changed!" + user.fromtime + ", " + user.totime + ", " + user.zone3 + ", " + user.zone4 + ", " + user.x);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }


}

