package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayView extends AppCompatActivity {

    public String date;
    private static datab database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> cardList;
    public int year;
    public int month;
    public int day;

    @Override
    public void onResume(){
        super.onResume();
        refresh(year, day, month);
    }



    public void refresh(int y, int d, int m){
//        super.onResume();

        cardList = new ArrayList<>();
        date = y + "-" + m + "-" + d;
        Log.w("DayView",date);

        List<table1> list1 = database.t1dao().getDate(date);
        List<table2> list2 = database.t2dao().getDate(date);

        for(int i=0; i<list1.size(); i++){
            StringBuilder des = new StringBuilder("");
            ListItem li = table1.ent_to_list(list1.get(i));
            des.append("Description: "+list1.get(i).getDescription()+"\nHierarchy: "+list1.get(i).getParent()+"/"+li.getTitle());
            li.setChildren(des.toString());
            Log.w("DayView", li.getTitle());
            cardList.add(li);
            List<table2> child = database.t2dao().getChildren(li.getTitle());
            for(int k=0; k<child.size(); k++){
                if(child.get(i).getDate().equals(date)){
                    ListItem le = table2.ent_to_list(child.get(k));
                    StringBuilder hier = new StringBuilder("");
                    hier.append("Description: "+child.get(k).getDescription()+"\n");
                    hier.append("Hierarchy: "+"Zen/"+child.get(k).getParent()+"/"+child.get(k).getChild());
                    le.setChildren(hier.toString());
                    Log.w("DayView", le.getTitle());
                    cardList.add(le);
                }
            }
        }
        for(int i=0; i<list2.size(); i++){
            ListItem le = table2.ent_to_list(list2.get(i));
            StringBuilder hier = new StringBuilder("");
            hier.append("Description: "+list2.get(i).getDescription()+"\n");
            hier.append("Hierarchy: "+"Zen/"+list2.get(i).getParent()+"/"+list2.get(i).getChild());
            le.setChildren(hier.toString());
            if(!cardList.contains(le)){
            cardList.add(le);
            Log.w("DayView", le.getTitle());

            }
        }

        adapter = new dAdapter(cardList, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.w("DayView","recycler view set");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
         year = cal.get(Calendar.YEAR);
         month = cal.get(Calendar.MONTH)+1;
         day = cal.get(Calendar.DAY_OF_MONTH);
        getSupportActionBar().setTitle("Day View");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        database = datab.getInstance(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewDayView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dayview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //start of add button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.selectDate:
                final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date = year+"-"+(month+1)+"-"+day;
                        Log.w("DayView DateSelector", date);
                        refresh(year, day ,month+1 );
                    }
                };

                DatePickerDialog dialog2 = new DatePickerDialog(
                        DayView.this,
                        android.R.style.Theme_Material_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month-1,day
                );
//                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}