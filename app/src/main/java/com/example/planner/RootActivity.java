package com.example.planner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RootActivity extends AppCompatActivity {

    private String TAG = "Zen";
    private String DES = "If you chase two rabbits, you catch none.";
    private static datab database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> cardList;

    @Override
    public void onResume(){
        super.onResume();
        cardList = new ArrayList<>();

        List<table1> entitiesList = (List<table1>) database.t1dao().getAll();

        for(int i=0; i<entitiesList.size(); i++){

            ListItem li = table1.ent_to_list(entitiesList.get(i));

            StringBuilder children = new StringBuilder("");
            List<table2> childList = database.t2dao().getChildren(entitiesList.get(i).getChild());

            children.append(entitiesList.get(i).getDescription());

            for(int k=0; k<childList.size(); k++){
                    children.append("\n+" + childList.get(k).getChild());
                }

            li.setChildren(children.toString());
            cardList.add(li);
        }

        adapter = new rAdapter(cardList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //add title with this code
        getSupportActionBar().setTitle(TAG);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = datab.getInstance(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView des = findViewById(R.id.Description);
        des.setText(DES);
        Log.w(TAG, DES);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //start of add button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_task:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RootActivity.this);
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
                                RootActivity.this,
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


                            if(database.t1dao().getDes(mTitle.getText().toString()).isEmpty()){String temp ;
                            if(mDate.getText().toString().equals("Select Date..."))
                            {temp = null;}
                            else {
                                String[] dat = mDate.getText().toString().split("/", 3);
                                temp = dat[2] + "-" + dat[1] + "-" +dat[0];
                            }
                            table1 entry = table1.t1(mTitle.getText().toString(), TAG, mDescription.getText().toString(), temp);
                            database.t1dao().insert(entry);
                            ListItem li = table1.ent_to_list(entry);
                            li.setChildren(entry.getDescription());
                            cardList.add(0,li);
                            adapter.notifyDataSetChanged();

                            Toast.makeText(RootActivity.this,
                                    R.string.saved,
                                    Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Added to Database");
                            dialog.dismiss();}
                            else{
                                Toast.makeText(RootActivity.this,
                                        R.string.acp,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RootActivity.this,
                                    R.string.null_input,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case R.id.action_day_view:
                Intent in = new Intent(RootActivity.this, DayView.class);
                startActivity(in);
                break;

        }
        return super.onOptionsItemSelected(item);

    }
    //end add button
}



