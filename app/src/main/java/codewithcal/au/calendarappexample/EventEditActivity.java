package codewithcal.au.calendarappexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

import codewithcal.au.calendarappexample.database.MyDatabase;
import codewithcal.au.calendarappexample.entity.Events;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
private Button Add;
    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
        Add= (Button) findViewById(R.id.btnadd);
        MyDatabase db = MyDatabase.getDatabase(EventEditActivity.this);
        Add.setOnClickListener(v -> {
            String EventName = eventNameET.getText().toString();
            String EventDate=eventDateTV.getText().toString();

            Events a = new Events(EventName,EventDate);

            db.EventsDAO().insertEvent(a);

            String eventName = eventNameET.getText().toString();
            Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
            Event.eventsList.add(newEvent);
            finish();

        });


    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}