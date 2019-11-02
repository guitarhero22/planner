package com.example.planner;

import android.content.Context;
import android.provider.SyncStateContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {table1.class, table2.class}, version = 1)
public abstract class datab extends RoomDatabase {
    public abstract table1dao t1dao();
    public abstract table2dao t2dao();
    public static datab actualdb;

    public static datab getInstance(Context context){
        if(null == actualdb){
            actualdb = buildDatabaseInstance(context);
            actualdb.t1dao().insert(table1.t1("Acads", "Zen", "Padhai ki baatein", "2019-12-31"));
            actualdb.t1dao().insert(table1.t1("Self Improvement", "Zen", "Reading list, blogs, exercise, etc.", "2021-2-29"));
            actualdb.t1dao().insert(table1.t1("Research", "Zen", "Pet projects", null));
            actualdb.t1dao().insert(table1.t1("Hobbies", "Zen", "<3", null));
            actualdb.t2dao().insert(table2.t2("Exercise","Self Improvement","someday?","2021-2-29"));
            actualdb.t2dao().insert(table2.t2("Reading list","Self Improvement","My bucket list:\nHear the Wind Sing\nThe Fountainhead\nAtlas Shrugged\nA prisoner of birth",null));
            actualdb.t2dao().insert(table2.t2("Origami","Hobbies","cranes and tigers","2019-10-29"));
            actualdb.t2dao().insert(table2.t2("Drum practice!","Hobbies","Aim:\nHallowed be thy name,\nAcid Rain (LTE)","2019-10-29"));
        }
        return actualdb;
    }

    public static datab buildDatabaseInstance(Context context){
        return Room.databaseBuilder(context, datab.class, "tasktree").allowMainThreadQueries().build();
    }

}
