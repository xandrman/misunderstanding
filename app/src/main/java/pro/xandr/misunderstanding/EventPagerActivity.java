package pro.xandr.misunderstanding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class EventPagerActivity extends AppCompatActivity {

    private static final String EXTRA_EVENT_ID = "pro.xandr.misunderstanding.event_id";

    private ViewPager viewPager;
    private List<Event> eventList;

    public static Intent newIntent(Context packageContext, UUID eventId) {
        Intent intent = new Intent(packageContext, EventPagerActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventId);
        return intent;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);

        viewPager = findViewById(R.id.vp_event);
        eventList = EventLab.get(this).getEventList();

        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Event event = eventList.get(position);
                return EventFragment.newInstance(event.getUuid());
            }

            @Override
            public int getCount() {
                return eventList.size();
            }
        });

        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getUuid().equals(eventId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
