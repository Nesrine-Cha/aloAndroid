package codewithcal.au.calendarappexample.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import codewithcal.au.calendarappexample.entity.ChatRoom;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatDAO_Impl implements ChatDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChatRoom> __insertionAdapterOfChatRoom;

  private final EntityDeletionOrUpdateAdapter<ChatRoom> __deletionAdapterOfChatRoom;

  private final EntityDeletionOrUpdateAdapter<ChatRoom> __updateAdapterOfChatRoom;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllEvents;

  public ChatDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChatRoom = new EntityInsertionAdapter<ChatRoom>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ChatRoom` (`id`,`name`,`message`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChatRoom value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getMessage() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMessage());
        }
      }
    };
    this.__deletionAdapterOfChatRoom = new EntityDeletionOrUpdateAdapter<ChatRoom>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ChatRoom` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChatRoom value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfChatRoom = new EntityDeletionOrUpdateAdapter<ChatRoom>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ChatRoom` SET `id` = ?,`name` = ?,`message` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChatRoom value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getMessage() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMessage());
        }
        stmt.bindLong(4, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllEvents = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM chatroom";
        return _query;
      }
    };
  }

  @Override
  public void insertEvent(final ChatRoom a) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfChatRoom.insert(a);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteEvent(final ChatRoom a) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfChatRoom.handle(a);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateEvent(final ChatRoom a) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfChatRoom.handle(a);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllEvents() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllEvents.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllEvents.release(_stmt);
    }
  }

  @Override
  public List<ChatRoom> findEvents() {
    final String _sql = "SELECT * FROM ChatRoom";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
      final List<ChatRoom> _result = new ArrayList<ChatRoom>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ChatRoom _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpMessage;
        if (_cursor.isNull(_cursorIndexOfMessage)) {
          _tmpMessage = null;
        } else {
          _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
        }
        _item = new ChatRoom(_tmpName,_tmpMessage);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ChatRoom>> getAllEvents() {
    final String _sql = "SELECT * FROM chatroom ORDER BY name DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"chatroom"}, false, new Callable<List<ChatRoom>>() {
      @Override
      public List<ChatRoom> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final List<ChatRoom> _result = new ArrayList<ChatRoom>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ChatRoom _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpMessage;
            if (_cursor.isNull(_cursorIndexOfMessage)) {
              _tmpMessage = null;
            } else {
              _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            }
            _item = new ChatRoom(_tmpName,_tmpMessage);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
