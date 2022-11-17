package codewithcal.au.calendarappexample.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import codewithcal.au.calendarappexample.dao.ChatDAO;
import codewithcal.au.calendarappexample.dao.EventsDAO;
import codewithcal.au.calendarappexample.entity.Events;


@Database(entities = {Events.class}, version = 1, exportSchema = false)
public abstract class   MyDatabase extends RoomDatabase {

    private static MyDatabase instance;

    public abstract EventsDAO EventsDAO();


    public static MyDatabase getDatabase(Context ctx) {
        if (instance == null) {
            instance = Room.databaseBuilder(ctx.getApplicationContext(), MyDatabase.class, "db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private EventsDAO eventsDAO;

        private PopulateDbAsyncTask(MyDatabase db) {
            eventsDAO = db.EventsDAO();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            eventsDAO.insertEvent(new Events("Title 1", "Description 1"));
            eventsDAO.insertEvent(new Events("Title 2", "Description 2"));
            eventsDAO.insertEvent(new Events("Title 3", "Description 3"));
            return null;
        }
    }
}
