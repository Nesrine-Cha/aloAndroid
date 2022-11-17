package codewithcal.au.calendarappexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.lifecycle.Observer;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.calendarappexample.Events.EventsAdapter;
import codewithcal.au.calendarappexample.entity.Events;

public class HistoryActivity extends AppCompatActivity {
    private EventsViewModel eventsViewModel;
        private List<Events> events =new ArrayList<>();
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private EventsAdapter adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        fillExampleList();
        setUpRecyclerView();
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication() ,WeekViewActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adap = new EventsAdapter(events);
        recyclerView.setAdapter(adap);

    eventsViewModel= ViewModelProviders.of(this).get(EventsViewModel.class);
    eventsViewModel.getAllEvents().observe(this, new Observer<List<Events>>() {
        @Override
        public void onChanged(List<Events> events) {
            adap.setEvents(events);

        }
    });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                eventsViewModel.delete(adap.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(HistoryActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        adap.setOnItemClickListener(new EventsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Events events) {
                Intent intent = new Intent(HistoryActivity.this, AddEditEventHistoryActivity.class);
                intent.putExtra(AddEditEventHistoryActivity.EXTRA_ID, events.getId());
                intent.putExtra(AddEditEventHistoryActivity.EXTRA_TITLE, events.getName());
                intent.putExtra(AddEditEventHistoryActivity.EXTRA_DESCRIPTION, events.getDate());
               // intent.putExtra(AddEditEventHistoryActivity.EXTRA_PRIORITY, events.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }
    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adap = new EventsAdapter(events);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditEventHistoryActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditEventHistoryActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditEventHistoryActivity.EXTRA_PRIORITY, 1);

            Events events = new Events(title, description);
            eventsViewModel.insert(events);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditEventHistoryActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditEventHistoryActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditEventHistoryActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditEventHistoryActivity.EXTRA_PRIORITY, 1);

            Events events = new Events(title, description);
            events.setId(id);
            eventsViewModel.update(events);

            Toast.makeText(this, "event updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "event not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_history_deleteall, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adap.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    private void fillExampleList() {
        events = new ArrayList<>();
        events.add(new Events("One", "Ten"));
        events.add(new Events("sadgrg", "gte"));
        events.add(new Events("dasda", "Tengtegtg"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                eventsViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Retour:

                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                Intent intent8 =new Intent(getApplicationContext(), CalendarViewActivity.class);
                startActivity(intent8);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}





