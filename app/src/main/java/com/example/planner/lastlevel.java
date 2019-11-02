package com.example.planner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class lastlevel extends AppCompatActivity {

    private String TAG = "";
    private String DES = "";
    public static datab database;
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        database = datab.getInstance(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        TAG = extras.getString("TAG");
        getSupportActionBar().setTitle(TAG);
        DES = extras.getString("Description");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastlevel);
        tv = findViewById(R.id.last_description);
        tv.setText(DES);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //start of add button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_edit_task:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(lastlevel.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                final TextView todo = (TextView) mView.findViewById(R.id.textView);
                final EditText mTitle = (EditText) mView.findViewById(R.id.etTitle);
                final EditText mDescription = (EditText) mView.findViewById(R.id.etDescription);
                final TextView mDate = (TextView) mView.findViewById(R.id.etDate);
//                Calendar cal1 = Calendar.getInstance();
//                int year1 = cal1.get(Calendar.YEAR);
//                int month1 = cal1.get(Calendar.MONTH);
//                int day1 = cal1.get(Calendar.DAY_OF_MONTH);
//                String date1 = day1 + "/" + month1 + "/" + year1;
//                mDate.setText(date1);
                todo.setText("Edit Subtast");
                List<table2> hi = database.t2dao().getDes(TAG);
                ListItem li = table2.ent_to_list(hi.get(0));
                mTitle.setText(li.getTitle());
                mDescription.setText(hi.get(0).getDescription());
                mDate.setText(li.getDate());


                //for date picker
                Calendar cal = Calendar.getInstance();
                final int year = cal.get(Calendar.YEAR);
                final int month = cal.get(Calendar.MONTH) + 1;
                final int day = cal.get(Calendar.DAY_OF_MONTH);
                Log.w(TAG, day + "/" + month + "/" + year);


                final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Log.w(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                        String date = month + "/" + day + "/" + year;
                        mDate.setText(date);
                        Log.w(TAG, "The date is set: date");
                    }
                };

                final Button mSave = (Button) mView.findViewById(R.id.btnSave);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                mDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePickerDialog dialog2 = new DatePickerDialog(
                                lastlevel.this,
                                android.R.style.Theme_Material_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year, month, day
                        );
//                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog2.show();
                    }
                });


                mSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mTitle.getText().toString().isEmpty() && !mDescription.getText().toString().isEmpty()) {

                            table2 li = database.t2dao().getDes(TAG).get(0);

                            String temp;
                            if(!mDate.getText().toString().isEmpty()){
                                String[] dat = mDate.getText().toString().split("/", 3);
                                temp = dat[2] + "-" + dat[1] + "-" + dat[0];
                            }else{
                                temp = null;
                            }
                                table2 entry = table2.t2(mTitle.getText().toString(), li.getParent(), mDescription.getText().toString(), temp);

                            //                                wait(10);
                            database.t2dao().delete(li);
                            database.t2dao().insert(entry);
                                Toast.makeText(lastlevel.this,
                                        R.string.saved,
                                        Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "Updated Database");
                                dialog.dismiss();
                            getSupportActionBar().setTitle(entry.getChild());
//                            TextView tv = findViewById(R.id.last_description);
                            tv.setText(entry.getDescription());
                        } else {
                            Toast.makeText(lastlevel.this,
                                    R.string.null_input,
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }
        //end add button

    }


}
