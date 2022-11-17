package codewithcal.au.calendarappexample.Events;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import codewithcal.au.calendarappexample.chatRoom.chatRepository;
import codewithcal.au.calendarappexample.entity.ChatRoom;

public class ChatViewModel extends AndroidViewModel {
    private chatRepository repository;
    private LiveData<List<ChatRoom>> allNotes;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        repository = new chatRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(ChatRoom note) {
        repository.insert(note);
    }

    public void update(ChatRoom note) {
        repository.update(note);
    }

    public void delete(ChatRoom note) {

        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllChatRoom();
    }

    public LiveData<List<ChatRoom>> getAllNotes() {
        return allNotes;
    }}