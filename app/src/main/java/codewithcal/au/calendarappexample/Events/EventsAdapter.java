package codewithcal.au.calendarappexample.Events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.calendarappexample.Event;
import codewithcal.au.calendarappexample.R;
import codewithcal.au.calendarappexample.entity.Events;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventHolder> implements Filterable {
private List<Events> events = new ArrayList<>();
private List<Events> eventsearch = new ArrayList<>();
    private OnItemClickListener listener;

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;

        ExampleViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.text_view_title);
            textView2 = itemView.findViewById(R.id.text_view_date);
        }
    }

    public EventsAdapter(List<Events> exampleList) {
        this.events = exampleList;
        eventsearch = new ArrayList<>(exampleList);
    }
    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView= LayoutInflater.from(parent.getContext())
               .inflate(R.layout.event_item,parent,false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
Events currentEvent=events.get(position);
holder.textViewTitle.setText(currentEvent.getName());
        holder.TextViewDate.setText(currentEvent.getDate());


    }

    @Override
    public int getItemCount() {
        return events.size();
    }
    public void setEvents(List<Events> events){
        this.events=events;
        notifyDataSetChanged();
        eventsearch=new ArrayList<>(events);
    }

    public Events getNoteAt(int position) {
        return events.get(position);
    }

    @Override
    public Filter getFilter() {
        return eventFilter;
    }

    private Filter eventFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Events> filteredList=new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(eventsearch);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Events item : eventsearch) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };


    class EventHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView TextViewDate;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.text_view_title);
            TextViewDate=itemView.findViewById(R.id.text_view_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(events.get(position));

                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Events events);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener =  listener;
    }
}




