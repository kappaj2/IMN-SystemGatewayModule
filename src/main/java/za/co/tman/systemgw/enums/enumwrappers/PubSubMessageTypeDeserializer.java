package za.co.tman.systemgw.enums.enumwrappers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import za.co.tman.systemgw.enums.PubSubMessageType;


public class PubSubMessageTypeDeserializer extends JsonDeserializer<PubSubMessageType> {
    
    @Override
    public PubSubMessageType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        
        if (node == null) {
            return null;
        }
        
        String text = node.get("message_type_code").textValue();
        
        if (text == null) {
            return null;
        }
        
        return PubSubMessageType.findPubSubMessageType(text);
    }
}
