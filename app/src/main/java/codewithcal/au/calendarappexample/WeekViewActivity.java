package codewithcal.au.calendarappexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import static codewithcal.au.calendarappexample.CalendarUtils.daysInMonthArray;
import static codewithcal.au.calendarappexample.CalendarUtils.daysInWeekArray;
import static codewithcal.au.calendarappexample.CalendarUtils.monthYearFromDate;

import codewithcal.au.calendarappexample.books.BookViewActivity;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private Button btnhisto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();
        btnhisto=(Button) findViewById(R.id.btnhisto);
        btnhisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HistoryActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyCalendarActivity.class));
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