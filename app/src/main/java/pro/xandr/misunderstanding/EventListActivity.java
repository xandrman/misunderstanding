package pro.xandr.misunderstanding;

import androidx.fragment.app.Fragment;

public class EventListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new EventListFragment();
    }
}
