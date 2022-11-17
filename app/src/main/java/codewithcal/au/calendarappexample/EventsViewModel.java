package codewithcal.au.calendarappexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import codewithcal.au.calendarappexample.Events.EventsRepository;
import codewithcal.au.calendarappexample.dao.EventsDAO;
import codewithcal.au.calendarappexample.entity.Events;

public class EventsViewModel extends AndroidViewModel {
    private  EventsRepository repository;
    private  LiveData<List<Events>> allEvents;

    public EventsViewModel(@NonNull Application application) {
        super(application);
        repository = new EventsRepository(application);
        allEvents = repository.getAllEvents();
    }

    public LiveData<List<Events>> getAllEvents() {
        return allEvents;
    }

    public void insert(Events events) {
        repository.insert(events);
    }

    public void update(Events events) {
        repository.update(events);
    }

    public void delete(Events events) {
        repository.delete(events);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }


    // public void (deleteAllEvents) {
     //   repository.deleteAllNotes();
  //  }

}