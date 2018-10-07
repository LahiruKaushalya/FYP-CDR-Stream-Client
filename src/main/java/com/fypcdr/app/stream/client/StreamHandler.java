package com.fypcdr.app.stream.client;

import akka.actor.ActorSystem;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lahiru Kaushalya
 */
public class StreamHandler {

    private final ActorSystem system;
    private final long TIME_OUT;

    public StreamHandler() {
        this.system = ActorSystem.create("CDRHttpClient");
        this.TIME_OUT = Integer.parseInt(Client.getProp().getProperty("timeout")); // 60 Seconds;
    }

    public void requestCDRRecords(int noOfRecords) {
        
        Long startTime = System.currentTimeMillis();

        int defaultChunkSize = Integer.parseInt(Client.getProp().getProperty("chunkSize")); // 60 Seconds;

        if (noOfRecords <= defaultChunkSize) {
            try {
                ResponseHandler rh = new ResponseHandler(system, 0, noOfRecords);
                rh.run();
                rh.join(TIME_OUT);
            } 
            catch (Exception ex) {
                Logger.getLogger(StreamHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
       
            Long endTime = System.currentTimeMillis();
            System.out.println((endTime - startTime) / 1000.0 + " Seconds taken.");
            system.terminate();
        } 
        else {
            int chunkSize = getChunkSize(noOfRecords, defaultChunkSize);
            int start, end;

            for (int i = 0; i < noOfRecords; i += chunkSize) {
                start = i;
                end = i + chunkSize;
                if (end > noOfRecords) {
                    end = noOfRecords;
                }
                try {
                    ResponseHandler thread = new ResponseHandler(system, start, end);
                    thread.start();
                    thread.join(TIME_OUT);
                } 
                catch (Exception ex) {
                    Logger.getLogger(StreamHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Long endTime = System.currentTimeMillis();
            System.out.println((endTime - startTime) / 1000.0 + " Seconds taken.");
            system.terminate();
        }
    }

    private int getChunkSize(int noOfRecords, int defaultChunkSize) {

        int curChunkSize = noOfRecords;
        if (noOfRecords % defaultChunkSize == 0) {
            curChunkSize = defaultChunkSize;
        } 
        else {
            while (curChunkSize > defaultChunkSize) {
                if (curChunkSize % 2 != 0) {
                    curChunkSize += 1;
                    curChunkSize /= 2;
                } 
                else {
                    curChunkSize /= 2;
                }
            }
        }
        return curChunkSize;
    }
}
