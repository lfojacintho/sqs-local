package me.lfojacintho.sqsui.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:lfojacintho@gmail.com">Luis Jacinhto</a>
 */
public class QueueInfoResponse implements Serializable {

    private static final long serialVersionUID = -1796181398117343982L;

    private final String name;

    private final int messagesAvailable;

    private final int messagesInFlight;

    private final LocalDateTime created;

    public QueueInfoResponse(final String name, final int messagesAvailable, final int messagesInFlight,
                             final LocalDateTime created) {
        this.name = name;
        this.messagesAvailable = messagesAvailable;
        this.messagesInFlight = messagesInFlight;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public int getMessagesAvailable() {
        return messagesAvailable;
    }

    public int getMessagesInFlight() {
        return messagesInFlight;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
