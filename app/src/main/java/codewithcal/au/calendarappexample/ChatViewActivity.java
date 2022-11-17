package codewithcal.au.calendarappexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.calendarappexample.Events.ChatViewModel;
import codewithcal.au.calendarappexample.Events.EventsAdapter;
import codewithcal.au.calendarappexample.books.BookViewActivity;
import codewithcal.au.calendarappexample.chatRoom.ChatAdapter;
import codewithcal.au.calendarappexample.entity.ChatRoom;
import codewithcal.au.calendarappexample.entity.Events;

public class ChatViewActivity extends AppCompatActivity {
private ChatViewModel chatViewModel;
    private List<ChatRoom> events =new ArrayList<>();
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private List<ChatRoom> chats =new ArrayList<>();
    private ChatAdapter adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);
        setUpRecyclerView();

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatViewActivity.this, AddChatActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ChatAdapter adapter = new ChatAdapter(chats);
        recyclerView.setAdapter(adapter);

        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        chatViewModel.getAllNotes().observe(this, new Observer<List<ChatRoom>>() {
            @Override
            public void onChanged(@Nullable List<ChatRoom> notes) {
                adapter.setEvents(notes);
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
                chatViewModel.delete(adap.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ChatViewActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adap.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ChatRoom events) {
                Intent intent = new Intent(ChatViewActivity.this, AddEditEventHistoryActivity.class);
                intent.putExtra(AddEditEventHistoryActivity.EXTRA_ID, events.getId());
                intent.putExtra(AddEditEventHistoryActivity.EXTRA_TITLE, events.getName());
                intent.putExtra(AddEditEventHistoryActivity.EXTRA_DESCRIPTION, events.getMessage());
                // intent.putExtra(AddEditEventHistoryActivity.EXTRA_PRIORITY, events.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adap = new ChatAdapter(events);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddChatActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddChatActivity.EXTRA_DESCRIPTION);


            ChatRoom note = new ChatRoom(title, description);
            chatViewModel.insert(note);

            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                chatViewModel.deleteAllNotes();
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





