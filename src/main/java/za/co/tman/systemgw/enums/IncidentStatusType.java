package za.co.tman.systemgw.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import za.co.tman.systemgw.enums.enumwrappers.IncidentStatusTypeDeserializer;
import za.co.tman.systemgw.enums.enumwrappers.IncidentStatusTypeSerializer;


@JsonDeserialize(using = IncidentStatusTypeDeserializer.class)
@JsonSerialize(using = IncidentStatusTypeSerializer.class)
public enum IncidentStatusType {
    
    OPENED("OPENED", "Incident Created"),
    ASSIGNED("ASSIGNED", "Incident Assigned to technician"),
    IN_PROGRESS("IN_PROGRESS", "Technician working on incident"),
    PENDING_SPARES("PENDING_SPARES", "Waiting for spares"),
    CLOSED("CLOSED","Incident is closed");
    
    private String incidentStatusCode;
    private String incidentStatusDescription;
    
    private static final Map<String, IncidentStatusType> incidentStatusMap = new HashMap<>();
    
    static {
        Arrays.stream(IncidentStatusType.values()).forEach(incidentStatusType -> incidentStatusMap.put(incidentStatusType.incidentStatusCode, incidentStatusType));
    }
    
    /**
     * Enum constructor
     *
     * @param incidentStatusCode
     * @param incidentStatusDescription
     */
    IncidentStatusType(String incidentStatusCode, String incidentStatusDescription) {
        this.incidentStatusCode = incidentStatusCode;
        this.incidentStatusDescription = incidentStatusDescription;
    }
    
    public String getIncidentStatusCode() {
        return incidentStatusCode;
    }
    
    public void setIncidentStatusCode(String incidentStatusCode) {
        this.incidentStatusCode = incidentStatusCode;
    }
    
    public String getIncidentStatusDescription() {
        return incidentStatusDescription;
    }
    
    public void setIncidentStatusDescription(String incidentStatusDescription) {
        this.incidentStatusDescription = incidentStatusDescription;
    }
    
    public static IncidentStatusType findIncidentStatus(String incidentStatusCode) {
        return incidentStatusMap.get(incidentStatusCode);
    }
}
