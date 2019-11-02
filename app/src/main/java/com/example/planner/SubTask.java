package com.example.planner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SubTask extends AppCompatActivity {

    private String TAG = "";
    private String DES = "";
    private static datab database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> cardList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //add title with this code

        Bundle extras = getIntent().getExtras();
        TAG = extras.getString("TAG");
        getSupportActionBar().setTitle(TAG);

        DES = extras.getString("Description");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task);
        database = datab.getInstance(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewSubTask);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView des = findViewById(R.id.Description);
        des.setText(DES);
        Log.w(TAG, DES);


    }

    @Override
    public void onResume(){

        super.onResume();

        cardList = new ArrayList<>();

        List<table2> entitiesList = (List<table2>) database.t2dao().getChildren(TAG);

        for(int i=0; i<entitiesList.size(); i++){
            ListItem li = table2.ent_to_list(entitiesList.get(i));
            String children = entitiesList.get(i).getDescription();
            li.setChildren(children);
            cardList.add(li);
        }

        adapter = new nAdapter(cardList, this);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //start of add button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_task:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SubTask.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                final EditText mTitle = (EditText) mView.findViewById(R.id.etTitle);
                final EditText mDescription = (EditText) mView.findViewById(R.id.etDescription);
                final TextView mDate = (TextView) mView.findViewById(R.id.etDate);
//                Calendar cal1 = Calendar.getInstance();
//                int year1 = cal1.get(Calendar.YEAR);
//                int month1 = cal1.get(Calendar.MONTH);
//                int day1 = cal1.get(Calendar.DAY_OF_MONTH);
//                String date1 = day1 + "/" + month1 + "/" + year1;
//                mDate.setText(date1);

                Calendar cal = Calendar.getInstance();
                final int year = cal.get(Calendar.YEAR);
                final int month = cal.get(Calendar.MONTH) + 1;
                final int day = cal.get(Calendar.DAY_OF_MONTH);

                Log.w(TAG, day+ "/" + month + "/" + year);


                final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Log.w(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                        String date = day + "/" + (month+1) + "/" + year;
                        mDate.setText(date);
                        Log.w(TAG,"The date is set: date");
                    }
                };

                final Button mSave = (Button) mView.findViewById(R.id.btnSave);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mDate.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        DatePickerDialog dialog2 = new DatePickerDialog(
                                SubTask.this,
                                android.R.style.Theme_Material_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year,month-1,day
                        );
//                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog2.show();
                    }
                });


                mSave.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        if(!mTitle.getText().toString().isEmpty() && !mDescription.getText().toString().isEmpty()){
                            if(database.t2dao().getDes(mTitle.getText().toString()).isEmpty()) {
                                Toast.makeText(SubTask.this,
                                        R.string.saved,
                                        Toast.LENGTH_SHORT).show();
                                String temp;
                                if (mDate.getText().toString().equals("Select Date...")) {
                                    temp = null;
                                } else {
                                    String[] dat = mDate.getText().toString().split("/", 3);
                                    temp = dat[2] + "-" + dat[1] + "-" + dat[0];
                                }

                                table2 entry = table2.t2(mTitle.getText().toString(), TAG, mDescription.getText().toString(), temp);
                                database.t2dao().insert(entry);
                                ListItem li = table2.ent_to_list(entry);
                                li.setChildren(entry.getDescription());
                                cardList.add(0,li);adapter.notifyDataSetChanged();

                                Log.w(TAG, "Added to Database");
                                dialog.dismiss();
                            }else {
                                Toast.makeText(SubTask.this,
                                        R.string.acp,
                                        Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(SubTask.this,
                                    R.string.null_input,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //end add button

}



