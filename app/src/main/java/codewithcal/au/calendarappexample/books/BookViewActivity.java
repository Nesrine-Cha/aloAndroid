package codewithcal.au.calendarappexample.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codewithcal.au.calendarappexample.CalendarViewActivity;
import codewithcal.au.calendarappexample.ChatViewActivity;
import codewithcal.au.calendarappexample.DailyCalendarActivity;
import codewithcal.au.calendarappexample.HistoryActivity;
import codewithcal.au.calendarappexample.LoginActivity;
import codewithcal.au.calendarappexample.R;
import codewithcal.au.calendarappexample.WeekViewActivity;

public class BookViewActivity extends AppCompatActivity {

    //creating variables for our requestqueue, array list, progressbar, edittext, image button and our recycler view. 
    private RequestQueue mRequestQueue;
    private ArrayList<BookInfo> bookInfoArrayList;
    private ProgressBar progressBar;
    private EditText searchEdt;
    private ImageButton searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbactivity_main_books);
        //initializing our views. 
        progressBar = findViewById(R.id.idLoadingPB);
        searchEdt = findViewById(R.id.idEdtSearchBooks);
        searchBtn = findViewById(R.id.idBtnSearch);

        //initializing on click listner for our button.
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                //checking if our edittext field is empty or not.
                if (searchEdt.getText().toString().isEmpty()) {
                    searchEdt.setError("Please enter search query");
                    return;
                }
                //if the search query is not empty then we are calling get book info method to load all the books from the API.
                getBooksInfo(searchEdt.getText().toString());
            }
        });
    }

    private void getBooksInfo(String query) {
        //creating a new array list.
        bookInfoArrayList = new ArrayList<>();
        //below line is use to initialze the variable for our request queue.
        mRequestQueue = Volley.newRequestQueue(BookViewActivity.this);
        //below line is use to clear cache this will be use when our data is being updated.
        mRequestQueue.getCache().clear();
        //below is the url for getting data from API in json format.
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;
        //below line we are  creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(BookViewActivity.this);

        //below line is use to make json object request inside that we are passing url, get method and getting json object. .
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                //inside on response method we are extracting all our json data.
                try {
                    JSONArray itemsArray = response.getJSONArray("items");

                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemsObj = itemsArray.getJSONObject(i);
                        JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                        String title = volumeObj.optString("title");
                        String subtitle = volumeObj.optString("subtitle");
                        JSONArray authorsArray = volumeObj.getJSONArray("authors");
                        String publisher = volumeObj.optString("publisher");
                        String publishedDate = volumeObj.optString("publishedDate");
                        String description = volumeObj.optString("description");
                        int pageCount = volumeObj.optInt("pageCount");
                        JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                        String thumbnail = imageLinks.optString("thumbnail");
                        String previewLink = volumeObj.optString("previewLink");
                        String infoLink = volumeObj.optString("infoLink");
                        JSONObject saleInfoObj = itemsObj.optJSONObject("saleInfo");
                        String buyLink = saleInfoObj.optString("buyLink");
                        ArrayList<String> authorsArrayList = new ArrayList<>();
                        if (authorsArray.length() != 0) {
                            for (int j = 0; j < authorsArray.length(); j++) {
                                authorsArrayList.add(authorsArray.optString(i));
                            }
                        }
                        //after extracting all the data we are saving this data in our modal class. 
                        BookInfo bookInfo = new BookInfo(title, subtitle, authorsArrayList, publisher, publishedDate, description, pageCount, thumbnail, previewLink, infoLink, buyLink);
                        //beloe line is use to pass our modal class in our array list.
                        bookInfoArrayList.add(bookInfo);
                        //below line is use to pass our array list in adapter class.
                        BookAdapter adapter = new BookAdapter(bookInfoArrayList, BookViewActivity.this);
                        //below line is use to add linear layout manager for our recycler view.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookViewActivity.this, RecyclerView.VERTICAL, false);
                        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.idRVBooks);
                        //in below line we are setting layout manager and adapter to our recycler view.
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //displaying a toast message when we get any erro from API
                    Toast.makeText(BookViewActivity.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //also displaying error message in toast.
                Toast.makeText(BookViewActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        //at last we are adding our json object request in our request queue.
        queue.add(booksObjrequest);

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




