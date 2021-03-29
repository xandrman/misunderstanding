package pro.xandr.misunderstanding;

import java.util.Date;
import java.util.UUID;

public class Event {

    private UUID uuid;
    private String title;
    private Date date;
    private boolean solved;

    public Event() {
        this(UUID.randomUUID());
    }

    public Event(UUID uuid) {
        this.uuid = uuid;
        date = new Date();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
