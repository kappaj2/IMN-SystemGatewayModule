package za.co.tman.systemgw.enums.enumwrappers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import za.co.tman.systemgw.enums.EventType;


/**
 * Utility class to serialize the EventType enums to transmit from RestController.
 */
public class EventTypeSerializer extends StdSerializer<EventType> {
    
    public EventTypeSerializer(){
        super(EventType.class);
    }
    
    @Override
    public void serialize(EventType value, JsonGenerator gen, SerializerProvider serializers) throws
                                                                                              IOException {
        
        gen.writeStartObject();
        gen.writeFieldName("event_type_code");
        gen.writeString(value.getEventTypeCode());
        gen.writeFieldName("event_description");
        gen.writeString(value.getEventDescription());
        gen.writeEndObject();
    }
}
