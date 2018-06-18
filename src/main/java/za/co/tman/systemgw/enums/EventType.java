package za.co.tman.systemgw.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EventType {
    
    START_EVENT("START", "Start a new event"),
    UPDATE_EVENT("UPDATE", "Update an existing event"),
    UPDATE_PRIORITY("UPDATE_PRIORITY", "Update incident priority"),
    CLOSE_EVENT("CLOSE", "Close the event"),
    GENERIC_MESSAGE("GENERIC_MESSAGE", "Generic message");
    
    private String eventTypeCode;
    private String eventDescription;
    
    private static final Map<String, EventType> eventTypesMap = new HashMap<>();
    
    static {
        Arrays.stream(EventType.values()).forEach(event -> eventTypesMap.put(event.eventTypeCode, event));
    }
    
    /**
     * Enum constructor
     *
     * @param eventTypeCode
     */
    EventType(String eventTypeCode, String eventDescription) {
        this.eventTypeCode = eventTypeCode;
        this.eventDescription = eventDescription;
    }
    
    /**
     * Return the eventTypeCode for the specific enum entry.
     *
     * @return
     */
    public String getEventTypeCode() {
        return eventTypeCode;
    }
    
    /**
     * Return the eventDescription for the specific enum entry.
     *
     * @return
     */
    public String getEventDescription() {
        return eventDescription;
    }
    
    /**
     * Lokup the enum for the code provided.
     *
     * @param eventTypeCode
     * @return
     */
    public static EventType findEventType(String eventTypeCode) {
        return eventTypesMap.get(eventTypeCode);
    }
}
