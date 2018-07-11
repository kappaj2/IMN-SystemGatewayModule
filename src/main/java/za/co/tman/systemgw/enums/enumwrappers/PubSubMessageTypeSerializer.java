package za.co.tman.systemgw.enums.enumwrappers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import za.co.tman.systemgw.enums.PubSubMessageType;


public class PubSubMessageTypeSerializer extends StdSerializer<PubSubMessageType> {
    
    public PubSubMessageTypeSerializer() {
        super(PubSubMessageType.class);
    }
    
    @Override
    public void serialize(PubSubMessageType value, JsonGenerator gen, SerializerProvider serializers) throws
                                                                                                      IOException {
        
        gen.writeStartObject();
        gen.writeFieldName("message_type_code");
        gen.writeString(value.getMessageTypeCode());
        gen.writeFieldName("message_type_description");
        gen.writeString(value.getMessageTypeDescription());
        gen.writeEndObject();
    }
}

