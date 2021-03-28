package pro.xandr.misunderstanding;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.UUID;

public class EventActivity extends SingleFragmentActivity {

    private static final String EXTRA_EVENT_ID = "pro.xandr.misunderstanding.event_id";

    public static Intent newIntent(Context packageContext, UUID eventId) {
        Intent intent = new Intent(packageContext, EventActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventId);
        return intent;
    };

    @Override
    protected Fragment createFragment() {
        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);
        return EventFragment.newInstance(eventId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}