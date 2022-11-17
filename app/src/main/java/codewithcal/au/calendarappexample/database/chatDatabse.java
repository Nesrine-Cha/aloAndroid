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
import codewithcal.au.calendarappexample.entity.ChatRoom;
import codewithcal.au.calendarappexample.entity.Events;

@Database(entities = {ChatRoom.class}, version = 1, exportSchema = false)
public abstract class chatDatabse extends RoomDatabase {

    private static chatDatabse instance;

    public abstract ChatDAO chatDAO();

    public static  chatDatabse getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            chatDatabse.class, "db1")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
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
        private ChatDAO noteDao;

        private PopulateDbAsyncTask(chatDatabse db) {
            noteDao = db.chatDAO();
        }



        @Override
        protected Void doInBackground(Void... voids) {
           // noteDao.insert(new Note("Title 1", "Description 1", 1));
           // noteDao.insert(new Note("Title 2", "Description 2", 2));
            noteDao.insertEvent(new ChatRoom("Title 3", "Description 3"));
            return null;
        }
    }
}