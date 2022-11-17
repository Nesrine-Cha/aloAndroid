package codewithcal.au.calendarappexample;

import static codewithcal.au.calendarappexample.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import codewithcal.au.calendarappexample.books.BookViewActivity;

public class DailyCalendarActivity extends AppCompatActivity
{

    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        initWidgets();
    }

    private void initWidgets()
    {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.hourListView);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setDayView();
    }

    private void setDayView()
    {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setHourAdapter();
    }

    private void setHourAdapter()
    {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<HourEvent> hourEventList()
    {
        ArrayList<HourEvent> list = new ArrayList<>();

        for(int hour = 0; hour < 24; hour++)
        {
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Event> events = Event.eventsForDateAndTime(selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }

        return list;
    }

    public void previousDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MENU:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                Intent intent774 =new Intent(getApplicationContext(), CalendarViewActivity.class);
                startActivity(intent774);
                return true;
            case R.id.chats:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                Intent intent7 =new Intent(getApplicationContext(), ChatViewActivity.class);
                startActivity(intent7);
                return true;
            case R.id.books:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                Intent intent6 =new Intent(getApplicationContext(), BookViewActivity.class);
                startActivity(intent6);
                return true;
            case R.id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();

                Intent intent8 =new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent8);
                return true;
            case R.id.Retour:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                Intent intent5 =new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent5);

            case R.id.item2:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(getApplicationContext(),CalendarViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                Intent intent77 =new Intent(getApplicationContext(), WeekViewActivity.class);
                startActivity(intent77);
                return true;

            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
                Intent intent25 =new Intent(getApplicationContext(), DailyCalendarActivity.class);
                startActivity(intent25);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}