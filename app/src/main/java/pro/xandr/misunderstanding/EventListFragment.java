package pro.xandr.misunderstanding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventListFragment extends Fragment {

    private static final int REQUEST_EVENT = 1;

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

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private TextView tvDate;
        private ImageView ivSolved;
        private Event event;

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.event_list_item, parent, false));
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            ivSolved = itemView.findViewById(R.id.iv_solved);
            itemView.setOnClickListener(this);
        }

        public void bind(Event event) {
            this.event = event;
            tvTitle.setText(event.getTitle());
            tvDate.setText(event.getDate().toString());
            ivSolved.setVisibility(event.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            Intent intent = EventPagerActivity.newIntent(getActivity(), event.getUuid());
            startActivityForResult(intent, REQUEST_EVENT);
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
        if (eventAdapter == null) {
            eventAdapter = new EventAdapter(eventList);
            recyclerView.setAdapter(eventAdapter);
        } else {
            eventAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_EVENT) {
//            eventAdapter.notifyItemChanged();
        }
    }
}
