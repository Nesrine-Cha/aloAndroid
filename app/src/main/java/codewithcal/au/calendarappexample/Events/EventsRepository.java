package codewithcal.au.calendarappexample.Events;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import codewithcal.au.calendarappexample.dao.EventsDAO;
import codewithcal.au.calendarappexample.database.MyDatabase;
import codewithcal.au.calendarappexample.entity.Events;

public class EventsRepository {
    private EventsDAO eventsDAO;
    private LiveData<List<Events>> allEvents;


    public EventsRepository(Application application) {
        MyDatabase database = MyDatabase.getDatabase(application);
        eventsDAO = database.EventsDAO();
        allEvents = (LiveData<List<Events>>) eventsDAO.getAllEvents();
    }

    public void insert(Events events) {
        new InsertNoteAsyncTask(eventsDAO).execute(events);
    }
    public void update(Events events) {
        new UpdateNoteAsyncTask(eventsDAO).execute(events);
    }

    public void delete(Events events) {

        new DeleteEventsAsyncTask(eventsDAO).execute(events);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(eventsDAO).execute();
    }

    public LiveData<List<Events>> getAllEvents() {
        return allEvents;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Events, Void, Void> {
        private EventsDAO eventsDAO;

        private InsertNoteAsyncTask(EventsDAO eventsDAO) {
            this.eventsDAO = eventsDAO;
        }


        @Override
        protected Void doInBackground(Events... events) {
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Events, Void, Void> {
        private EventsDAO eventsDAO;

        private UpdateNoteAsyncTask(EventsDAO eventsDAO) {

            this.eventsDAO = eventsDAO;
        }

             @Override
        protected Void doInBackground(Events... events) {
                 eventsDAO.updateEvent(events[0]);
            return null;
        }
    }


    private static class DeleteEventsAsyncTask extends AsyncTask<Events, Void, Void> {
        private EventsDAO eventsDAO;
private DeleteEventsAsyncTask(EventsDAO eventsDAO){
    this.eventsDAO=eventsDAO;
}

        @Override
        protected Void doInBackground(Events... events) {
            eventsDAO.deleteEvent(events[0]);

    return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private EventsDAO noteDao;

        private DeleteAllNotesAsyncTask(EventsDAO noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllEvents();
            return null;
        }
    }
}