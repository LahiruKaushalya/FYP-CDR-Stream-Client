package com.fypcdr.app.stream.client;

/**
 *
 * @author Lahiru Kaushalya
 */
public class Client {

    public static void main(String[] args){
       
        int noOfRecords = Integer.parseInt(args[0]);
        
        StreamHandler streamHandler = new StreamHandler();
        
        streamHandler.requestCDRRecords(noOfRecords); 
    }

}
