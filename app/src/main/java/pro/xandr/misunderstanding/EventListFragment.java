package pro.xandr.misunderstanding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventListFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_list_fragment, container, false);
        recyclerView = v.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return recyclerView;
    }

    private class EventHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDate;
        private Event event;

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.event_list_item, parent, false));
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
        }

        public void bind(Event event) {
            this.event = event;
            tvTitle.setText(event.getTitle());
            tvDate.setText(event.getDate().toString());
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {

        List<Event> eventList;

        public EventAdapter(List<Event> eventList) {
            this.eventList = eventList;
        }

        @NonNull
        @Override
        public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EventHolder holder, int position) {
            Event event = eventList.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }
    }

    private void updateUI() {
        EventLab eventLab = EventLab.get(getActivity());
        List<Event> eventList = eventLab.getEventList();
        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);
    }
}