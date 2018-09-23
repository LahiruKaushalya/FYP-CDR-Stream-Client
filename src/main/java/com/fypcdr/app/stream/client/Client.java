package com.fypcdr.app.stream.client;

/**
 *
 * @author Lahiru Kaushalya
 */
public class Client {

    public static void main(String[] args){

        StreamHandler streamHandler = new StreamHandler();
        streamHandler.requestCDRRecords(20000000); // Test...
    }

}
