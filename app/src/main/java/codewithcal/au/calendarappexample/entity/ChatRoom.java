package codewithcal.au.calendarappexample.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ChatRoom")
public class ChatRoom {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String message;



    public ChatRoom(String name, String message) {
        this.name = name;
        this.message = message;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
