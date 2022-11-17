package codewithcal.au.calendarappexample.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


import codewithcal.au.calendarappexample.entity.Events;

@Dao
public interface EventsDAO {

    @Insert
    public void insertEvent(Events a);

    @Update
    public void updateEvent(Events a);

    @Delete
    public void deleteEvent(Events a);

    @Query("SELECT * FROM Events")
    public List<Events> findEvents();

    @Query("DELETE FROM Events")
    void deleteAllEvents();


    @Query("SELECT * FROM Events ORDER BY date DESC")
    LiveData<List<Events>> getAllEvents();
}
