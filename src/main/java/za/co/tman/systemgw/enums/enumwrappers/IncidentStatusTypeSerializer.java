package za.co.tman.systemgw.enums.enumwrappers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import za.co.tman.systemgw.enums.IncidentStatusType;


public class IncidentStatusTypeSerializer extends StdSerializer<IncidentStatusType> {
    
    public IncidentStatusTypeSerializer() {
        super(IncidentStatusType.class);
    }
    
    @Override
    public void serialize(IncidentStatusType value, JsonGenerator gen, SerializerProvider serializers) throws
                                                                                                       IOException {
        
        gen.writeStartObject();
        gen.writeFieldName("incident_status_code");
        gen.writeString(value.getIncidentStatusCode());
        gen.writeFieldName("incident_status_description");
        gen.writeString(value.getIncidentStatusDescription());
        gen.writeEndObject();
    }
}
