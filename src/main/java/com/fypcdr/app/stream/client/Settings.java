package com.fypcdr.app.stream.client;

/**
 *
 * @author Lahiru Kaushalya
 */
public interface Settings {
    
    //Server ip address
    final String ipAddress = "localhost";
    
    //Server port
    final int port = 8080;
    
    //#of CDR records per one GET request
    final int chunkSize = 10000;
    
    //request timeout 60s
    final long timeout = 60000;
}
