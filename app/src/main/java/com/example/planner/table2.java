package com.example.planner;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.sql.Timestamp;

@Entity(tableName = "level2", primaryKeys = {"child" , "parent"}, foreignKeys = @ForeignKey(entity = table1.class, parentColumns = "child", childColumns = "parent"))
public class table2 {
    public static ListItem ent_to_list(table2 ent){

        ListItem hi;

        int year;
        int day;
        int month;
        if(ent.getDate() != null){
            String[] dat = ent.getDate().split("-", 3);
            if(dat.length < 3) {hi = new ListItem(ent.getChild(),"");}
            else {
                year = Integer.parseInt(dat[0]);
                month = Integer.parseInt(dat[1]);
                day = Integer.parseInt(dat[2]);
                hi = new ListItem(ent.getChild(), day + "/" + month + "/" + year);}

        }else{ hi = new ListItem(ent.getChild(),"");}

        return hi;
    };

    @NonNull
    private String child;

    @NonNull
    private String parent;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private String date;

    public String getChild(){
        return this.child;
    }

    public String getParent(){
        return this.parent;
    }

    public String getDescription(){
        return this.description;
    }

    public String getDate(){
        return this.date;
    }

    public void setChild(String c){
        this.child = c;
        return;
    }

    public void setParent(String p){
        this.parent = p;
    }

    public void setDescription(String d){
        this.description = d;
    }

    public void setDate(String d){
        this.date = d;
    }


    static table2 t2(String c, String p, String d, String t){
        table2 k = new table2();
        k.child = c;
        k.parent = p;
        k.description = d;
        k.date = t;

        return k;
    }
}
