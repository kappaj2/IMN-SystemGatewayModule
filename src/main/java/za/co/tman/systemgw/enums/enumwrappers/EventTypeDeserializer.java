package za.co.tman.systemgw.enums.enumwrappers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import za.co.tman.systemgw.enums.EventType;


/**
 * Utility class to handle Deserialization of the incoming object on the RestController back to an Enum.
 */
public class EventTypeDeserializer extends JsonDeserializer<EventType> {
    
    @Override
    public EventType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        
        if (node == null) {
            return null;
        }
        
        String text = node.get("event_type_code").textValue(); // gives "A" from the request
        
        if (text == null) {
            return null;
        }
        
        return EventType.findEventType(text);
    }
}

