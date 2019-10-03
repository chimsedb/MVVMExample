package com.example.mvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Node.class, version = 1)
public abstract class NodeDatabase extends RoomDatabase {
    private static NodeDatabase instance;

    public abstract NodeDAO nodeDAO();

    public static synchronized NodeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NodeDatabase.class,
                    "node_data")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynTask(instance).execute();
        }
    };

    private static class PopulateDbAsynTask extends AsyncTask<Void,Void,Void>{
        private NodeDAO nodeDAO;

        public PopulateDbAsynTask(NodeDatabase db) {
            nodeDAO = db.nodeDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            nodeDAO.insert(new Node("Title 1","Description 1",1));
            nodeDAO.insert(new Node("Title 2","Description 2",2));
            nodeDAO.insert(new Node("Title 3","Description 3",3));
            nodeDAO.insert(new Node("Title 4","Description 4",4));
            return null;
        }
    }
}
