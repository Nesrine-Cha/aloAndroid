package codewithcal.au.calendarappexample.chatRoom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import codewithcal.au.calendarappexample.R;
import codewithcal.au.calendarappexample.entity.ChatRoom;
import codewithcal.au.calendarappexample.entity.Events;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> implements Filterable {
private List<ChatRoom> events = new ArrayList<>();
private List<ChatRoom> eventsearch = new ArrayList<>();
    private OnItemClickListener listener;


    public ChatAdapter(List<ChatRoom> exampleList) {
        this.events = exampleList;
        eventsearch = new ArrayList<>(exampleList);
    }
    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView= LayoutInflater.from(parent.getContext())
               .inflate(R.layout.chat_item,parent,false);
        return new ChatHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
ChatRoom currentEvent=events.get(position);
holder.textViewTitle.setText(currentEvent.getName());
        holder.TextViewDate.setText(currentEvent.getMessage());


    }

    @Override
    public int getItemCount() {
        return events.size();
    }
    public void setEvents(List<ChatRoom> events){
        this.events=events;
        notifyDataSetChanged();
        eventsearch=new ArrayList<>(events);
    }

    public ChatRoom getNoteAt(int position) {
        return events.get(position);
    }

    @Override
    public Filter getFilter() {
        return eventFilter;
    }

    private Filter eventFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ChatRoom> filteredList=new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(eventsearch);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ChatRoom item : eventsearch) {
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


    class ChatHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView TextViewDate;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.text_view_titleM);
            TextViewDate=itemView.findViewById(R.id.text_view_descriptionM);

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
        void onItemClick(ChatRoom events);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener =  listener;
    }
}




