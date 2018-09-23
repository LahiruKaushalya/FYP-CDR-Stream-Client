package com.fypcdr.app.stream.client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Lahiru Kaushalya
 */
public class CDRRecord {
    
    @JsonProperty("called_num")
    private final String called_num;
    
    @JsonProperty("called_tower")
    private final String called_tower;
    
    @JsonProperty("recipient_num")
    private final String recipient_num;
    
    @JsonProperty("recipient_tower")
    private final String recipient_tower;
    
    @JsonProperty("datetime")
    private final String datetime;
    
    @JsonProperty("duration")
    private final String duration;
    
    public CDRRecord(){
        this.datetime = "";
        this.duration = "";
        this.called_num = "";
        this.called_tower = "";
        this.recipient_num = "";
        this.recipient_tower = "";
    }
    
    public CDRRecord(
            String called_num,
            String called_tower,
            String recipient_num,
            String recipient_tower,
            String datetime,
            String duration
    ){ 
        this.datetime = datetime;
        this.duration = duration;
        this.called_num = called_num;
        this.called_tower = called_tower;
        this.recipient_num = recipient_num;
        this.recipient_tower = recipient_tower;
    }
    
    public String getDatetime() {
        return datetime;
    }

    public String getDuration() {
        return duration;
    }
    
    public String getCalled_num() {
        return called_num;
    }

    public String getCalled_tower() {
        return called_tower;
    }

    public String getRecipient_num() {
        return recipient_num;
    }

    public String getRecipient_tower() {
        return recipient_tower;
    }
    
}
