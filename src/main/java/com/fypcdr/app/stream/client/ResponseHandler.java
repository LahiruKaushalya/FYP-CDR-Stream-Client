package com.fypcdr.app.stream.client;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.ResponseEntity;
import akka.http.javadsl.model.Uri;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import java.util.concurrent.CompletionStage;

/**
 *
 * @author Lahiru Kaushalya
 */
public class ResponseHandler extends Thread{
    
    private final int start;
    private final int end;
    private final long TIME_OUT;
    private final ActorSystem system;
    
    public ResponseHandler(ActorSystem system, int start, int end){
        this.start = start;
        this.end = end;
        this.TIME_OUT = Settings.timeout; // 60 Seconds
        this.system = system;
    }
    
    @Override
    public void run() {
        System.out.println("Requesting CDR records from " + start + " to " + end);
        
        final CompletionStage<HttpResponse> responseFuture
                = Http.get(system)
                .singleRequest(HttpRequest.create()
                .withUri(getUri()));

        final Materializer materializer = ActorMaterializer.create(system);
        
        try {
            final HttpResponse response = responseFuture.toCompletableFuture().get();

            if (response.status().intValue() == 200) {

                ResponseEntity entity = response.entity();
                String[] body = entity
                        .toStrict(TIME_OUT, materializer).toCompletableFuture()
                        .get()
                        .getData()
                        .utf8String()
                        .replace("[", "")
                        .replace("]", "")
                        .split("/");
                
                System.out.println(body.length + " records recieved.");
                
                DatabaseHandler dbThread = new DatabaseHandler(body, start, end);
                dbThread.start();
            }

        } catch (Exception e) {System.out.println(e);}
        
    }
    
    private Uri getUri(){
        Uri uri = Uri.create(
                "http://"
                + Settings.ipAddress + ":"
                + Settings.port + "/cdrRecords?start="
                + start + "&end="
                + end
        );
        return uri;
    }
    
}
