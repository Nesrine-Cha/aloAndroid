package codewithcal.au.calendarappexample.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import codewithcal.au.calendarappexample.entity.ChatRoom;
import codewithcal.au.calendarappexample.entity.Events;

@Dao
public interface ChatDAO {

    @Insert
    public void insertEvent(ChatRoom a);

    @Update
    public void updateEvent(ChatRoom a);

    @Delete
    public void deleteEvent(ChatRoom a);

    @Query("SELECT * FROM ChatRoom")
    public List<ChatRoom> findEvents();

    @Query("DELETE FROM chatroom")
    void deleteAllEvents();


    @Query("SELECT * FROM chatroom ORDER BY name DESC")
    LiveData<List<ChatRoom>> getAllEvents();
}
