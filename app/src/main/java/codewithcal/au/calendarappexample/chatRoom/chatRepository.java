package codewithcal.au.calendarappexample.chatRoom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import codewithcal.au.calendarappexample.dao.ChatDAO;
import codewithcal.au.calendarappexample.dao.EventsDAO;
import codewithcal.au.calendarappexample.database.chatDatabse;
import codewithcal.au.calendarappexample.entity.ChatRoom;
import codewithcal.au.calendarappexample.entity.Events;

public class chatRepository{
    private ChatDAO chatDao;
        private LiveData<List<ChatRoom>> allCHAT;

        public chatRepository(Application application) {
            chatDatabse database = chatDatabse.getInstance(application);
            chatDao = database.chatDAO();
            allCHAT = chatDao.getAllEvents();
        }

        public void insert(ChatRoom note) {
            new InsertNoteAsyncTask(chatDao).execute(note);
        }

        public void update(ChatRoom note) {
            new UpdateNoteAsyncTask(chatDao).execute(note);
        }

        public void delete(ChatRoom note) {

            new DeleteNoteAsyncTask(chatDao).execute(note);
        }

        public void deleteAllChatRoom() {
            new DeleteAllNotesAsyncTask(chatDao).execute();
        }

        public LiveData<List<ChatRoom>> getAllNotes() {
            return allCHAT;
        }

private static class InsertNoteAsyncTask extends AsyncTask<ChatRoom, Void, Void> {
    private ChatDAO chatDao;

    private InsertNoteAsyncTask(ChatDAO noteDao) {
        this.chatDao = noteDao;
    }

    @Override
    protected Void doInBackground(ChatRoom... notes) {
        chatDao.insertEvent(notes[0]);
        return null;
    }
}

private static class UpdateNoteAsyncTask extends AsyncTask<ChatRoom, Void, Void> {
    private ChatDAO chatDao;

    private UpdateNoteAsyncTask(ChatDAO noteDao) {
        this.chatDao = noteDao;
    }

    @Override
    protected Void doInBackground(ChatRoom... notes) {
        chatDao.updateEvent(notes[0]);
        return null;
    }
}

private static class DeleteNoteAsyncTask extends AsyncTask<ChatRoom, Void, Void> {
    private ChatDAO chatDao;

    private DeleteNoteAsyncTask(ChatDAO noteDao) {
        this.chatDao = noteDao;
    }

    @Override
    protected Void doInBackground(ChatRoom... notes) {
        chatDao.deleteEvent(notes[0]);
        return null;
    }
}

private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
    private ChatDAO chatDao;

    private DeleteAllNotesAsyncTask(ChatDAO chatDao) {
        this.chatDao = chatDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        chatDao.deleteAllEvents();
        return null;
    }
}
}