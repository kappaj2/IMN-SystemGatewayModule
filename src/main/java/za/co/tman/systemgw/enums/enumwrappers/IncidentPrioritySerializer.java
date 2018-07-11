package za.co.tman.systemgw.enums.enumwrappers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import za.co.tman.systemgw.enums.IncidentPriority;


public class IncidentPrioritySerializer extends StdSerializer<IncidentPriority> {
    
    public IncidentPrioritySerializer() {
        super(IncidentPriority.class);
    }
    
    @Override
    public void serialize(IncidentPriority value, JsonGenerator gen, SerializerProvider serializers) throws
                                                                                                     IOException {
        
        gen.writeStartObject();
        gen.writeFieldName("priority_code");
        gen.writeString(value.getPriorityCode());
        gen.writeFieldName("priority_description");
        gen.writeString(value.getPriorityDescription());
        gen.writeEndObject();
    }
}
