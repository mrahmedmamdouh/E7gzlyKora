package com.example.android.e7gzlykora.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.Owner;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    EditText name;
    EditText mobile;
    EditText field;
    EditText address;
    EditText cost;
    Spinner zone1;
    Spinner zone2;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         name = (EditText) findViewById(R.id.editName);
          mobile = (EditText) findViewById(R.id.editMobilePhone);
          field = (EditText) findViewById(R.id.editField);
          address = (EditText) findViewById(R.id.editAddress);
          cost = (EditText) findViewById(R.id.editCost);
         save = (Button) findViewById(R.id.save);
          zone1 = (Spinner) findViewById(R.id.zone1);
          zone2 = (Spinner) findViewById(R.id.zone2);

          name.setText(Auth.getInstance().getName());
          mobile.setText(Auth.getInstance().getMobile());

        String[] items = new String[]{"Cairo","Giza", "Alexandria","Others"};
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, items);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        zone1.setAdapter(adapter);

        zone1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView <?> parent, View view,
                                                                   int position, long id) {

                                            if (zone1.getSelectedItem().equals("Cairo")) {


                                                ArrayAdapter adapter2 = ArrayAdapter.createFromResource(MainActivity.this,
                                                        R.array.cairo, android.R.layout.simple_spinner_item);
                                                zone2.setAdapter(adapter2);
                                            } else if (zone1.getSelectedItem().equals("Giza")) {
                                                ArrayAdapter adapter3 = ArrayAdapter.createFromResource(MainActivity.this,
                                                        R.array.Giza, android.R.layout.simple_spinner_item);
                                                zone2.setAdapter(adapter3);
                                            } else if (zone1.getSelectedItem().equals("Alexandria")) {
                                                ArrayAdapter adapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                                                        R.array.Alex, android.R.layout.simple_spinner_item);
                                                zone2.setAdapter(adapter4);
                                            } else {
                                                ArrayAdapter adapter5 = ArrayAdapter.createFromResource(MainActivity.this,
                                                        R.array.Others, android.R.layout.simple_spinner_item);
                                                zone2.setAdapter(adapter5);
                                            }
                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView <?> parent) {
                                            // TODO Auto-generated method stub
                                        }


                                    });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Owner.getInstance().setName(Auth.getInstance().getName());
                Owner.getInstance().setMobile(Auth.getInstance().getMobile());
                Owner.getInstance().setFieldName(field.getText().toString());
                Owner.getInstance().setAddress(address.getText().toString());
                Owner.getInstance().setCost(Double.parseDouble(cost.getText().toString()));
                Owner.getInstance().setZone1(zone1.getSelectedItem().toString());
                Owner.getInstance().setZone2(zone2.getSelectedItem().toString());
                Owner.getInstance().setOwnerGUID(Auth.getInstance().getUserGUID());
                Owner obj = Owner.getInstance();
                AndroidNetworking.post("http://192.168.2.8:8089/api/Owner/InsertData")
                        .addBodyParameter(obj)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                if (response.equals("\"Owner data has been updated successfully\"")){
                                    Intent intent = new Intent(MainActivity.this,ownerprofile.class);
                                    startActivity(intent);}
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext(),anError.getResponse().toString(),Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

    }
}