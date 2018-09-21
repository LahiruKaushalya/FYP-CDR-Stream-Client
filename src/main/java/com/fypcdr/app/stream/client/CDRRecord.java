package com.fypcdr.app.stream.client;

/**
 *
 * @author Lahiru Kaushalya
 */
public abstract class CDRRecord {
    
    private final String called_num;
    private final String called_tower;
    private final String recipient_num;
    private final String recipient_tower;
    private final String datetime;
    private final String duration;
    
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
    
    public static class CDRTemplate1 extends CDRRecord{
        
        private final String special_attribute1;

        public CDRTemplate1(
                String called_num,
                String called_tower,
                String recipient_num,
                String recipient_tower,
                String datetime,
                String duration,
                String special_attribute1
        ){
            super(called_num, called_tower, recipient_num, recipient_tower, datetime, duration);
            
            this.special_attribute1 = special_attribute1;
        }
        
        public String getSpecial_attribute1() {
            return special_attribute1;
        }
    
    }
    
    public static class CDRTemplate2 extends CDRRecord{
        
        private final String special_attribute1;

        public CDRTemplate2(
                String called_num,
                String called_tower,
                String recipient_num,
                String recipient_tower,
                String datetime,
                String duration,
                String special_attribute1
        ){
            super(called_num, called_tower, recipient_num, recipient_tower, datetime, duration);
            
            this.special_attribute1 = special_attribute1;
        }
        
        public String getSpecial_attribute1() {
            return special_attribute1;
        }
    
    }
    
}
