package pro.xandr.misunderstanding;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventLab {

    private static EventLab sEventLab;
    private List<Event> eventList;

    private EventLab(Context context) {
        eventList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Event event = new Event();
            event.setTitle("Event #" + i);
            event.setSolved(i % 2 == 0); // Для каждого второго объекта
            eventList.add(event);
        }
    }

    public static EventLab get(Context context) {
        if (sEventLab == null) {
            sEventLab = new EventLab(context);
        }
        return sEventLab;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public Event getEvent(UUID uuid) {
        for (Event event : eventList) {
            if (event.getUuid() == uuid) {
                return event;
            }
        }
        return null;
    }
}
