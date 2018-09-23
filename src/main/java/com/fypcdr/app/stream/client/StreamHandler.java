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
public class StreamHandler {

    private final ActorSystem system;

    public StreamHandler() {
        this.system = ActorSystem.create("CDRHttpClient");
    }

    public void requestCDRRecords(int noOfRecords) {

        int defaultChunkSize = Settings.chunkSize;

        if (noOfRecords <= defaultChunkSize) {
            request(0, noOfRecords);
        }
        else{
            int chunkSize = getChunkSize(noOfRecords);
            int start, end, count = 0;
            
            for(int i = 0; i < noOfRecords; i += chunkSize){
                 start = i;
                 end = i + chunkSize;
                 if(end > noOfRecords)end = noOfRecords;
                 
                 System.out.println("Sending GET request : " + ++count);
                 System.out.println("Requesting CDR records from " + start + " to " + end);
            }
        }
    }

    private void request(int start, int end) {

        Uri getUri = Uri.create(
                "http://"
                + Settings.ipAddress + ":"
                + Settings.port + "/cdrRecords?start="
                + start + "&end="
                + end
        );

        final CompletionStage<HttpResponse> responseFuture
                = Http.get(system)
                .singleRequest(HttpRequest.create().withUri(getUri));

        final Materializer materializer = ActorMaterializer.create(system);

        try {
            final HttpResponse response = responseFuture.toCompletableFuture().get();

            if (response.status().intValue() == 200) {

                ResponseEntity entity = response.entity();
                int body = entity.toStrict(1000, materializer).toCompletableFuture().get().getData().size();
                System.out.println("Result.....\n" + body);
            }

        } catch (Exception e) {
        }
    }

    public int getChunkSize(int noOfRecords) {

        int curChunkSize = noOfRecords;

        if (noOfRecords % Settings.chunkSize == 0) {
            curChunkSize = Settings.chunkSize;
        } 
        else {
            while (curChunkSize > Settings.chunkSize) {
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
