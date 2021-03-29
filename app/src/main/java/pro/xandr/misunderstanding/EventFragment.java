package pro.xandr.misunderstanding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

public class EventFragment extends Fragment {

    private static final String ARG_EVENT_ID = "event_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Event event;
    private EditText etTitle;
    private Button btnDate;
    private CheckBox chbSolved;

    public static EventFragment newInstance(UUID eventId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID, eventId);

        EventFragment fragment = new EventFragment();
        fragment.setArguments(args);
        return fragment;
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID eventId = (UUID) getArguments().getSerializable(ARG_EVENT_ID);
        Log.d("S", EventLab.get(getActivity()).toString());
        event = EventLab.get(getActivity()).getEvent(eventId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);

        etTitle = (EditText) v.findViewById(R.id.et_title);
        etTitle.setText(event.getTitle());
        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                event.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDate = v.findViewById(R.id.btn_date);
        updateDate();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment fragment = DatePickerFragment.newInstance(event.getDate());
                fragment.setTargetFragment(EventFragment.this, REQUEST_DATE);
                if (manager != null) {
                    fragment.show(manager, DIALOG_DATE);
                }
            }
        });

        chbSolved = v.findViewById(R.id.chb_solved);
        chbSolved.setChecked(event.isSolved());
        chbSolved.setOnCheckedChangeListener((buttonView, isChecked) -> event.setSolved(isChecked));

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        EventLab.get(getActivity()).updateEvent(event);

        returnResult();
    }

    public void returnResult() {
        Intent data = new Intent();
        data.putExtra("s", event.getUuid());
        getActivity().setResult(Activity.RESULT_OK, data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            event.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        btnDate.setText(event.getDate().toString());
    }
}
