package za.co.tman.systemgw.enums.enumwrappers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import za.co.tman.systemgw.enums.IncidentStatusType;


public class IncidentStatusTypeDeserializer extends JsonDeserializer<IncidentStatusType> {
    
    @Override
    public IncidentStatusType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        
        if (node == null) {
            return null;
        }
        
        String text = node.get("incident_status_code").textValue();
        
        if (text == null) {
            return null;
        }
        
        return IncidentStatusType.findIncidentStatus(text);
    }
}
