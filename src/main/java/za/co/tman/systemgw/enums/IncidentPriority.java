package za.co.tman.systemgw.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import za.co.tman.systemgw.enums.enumwrappers.IncidentPriorityDeserializer;
import za.co.tman.systemgw.enums.enumwrappers.IncidentPrioritySerializer;


/**
 * Enum to be used when creating the incident.
 */
@JsonDeserialize(using = IncidentPriorityDeserializer.class)
@JsonSerialize(using = IncidentPrioritySerializer.class)
public enum IncidentPriority {
    
    CRITICAL("Critical", "Critical Priority"),
    SAFETY("Safety", "Safety Related"),
    HIGH("High", "High Priority"),
    MEDIUM("Medium", "Medium Priority"),
    LOW("Low", "Low Priority"),
    MAINTENANCE("Maintenace", "Next Maintenance Schedule");
    
    private String priorityCode;
    private String priorityDescription;
    
    private static final Map<String, IncidentPriority> incidentPriorityMap = new HashMap<>();
    
    static {
        Arrays.stream(IncidentPriority.values())
            .forEach(incidentPriority -> incidentPriorityMap.put(incidentPriority.getPriorityCode(), incidentPriority));
    }
    
    IncidentPriority(String priorityCode, String priorityDescription) {
        this.priorityCode = priorityCode;
        this.priorityDescription = priorityDescription;
    }
    
    public String getPriorityCode() {
        return priorityCode;
    }
    
    public String getPriorityDescription() {
        return priorityDescription;
    }
    
    public static IncidentPriority findIncidentPriority(String priorityCode) {
        return incidentPriorityMap.get(priorityCode);
    }
}
