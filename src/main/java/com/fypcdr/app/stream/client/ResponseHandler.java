package com.fypcdr.app.stream.client;

import akka.actor.ActorSystem;
import static akka.http.javadsl.ConnectHttp.toHost;
import akka.http.javadsl.Http;
import akka.http.javadsl.OutgoingConnection;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.ResponseEntity;
import akka.http.javadsl.model.Uri;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lahiru Kaushalya
 */
public class ResponseHandler extends Thread {

    private final int start;
    private final int end;
    private final long TIME_OUT;
    private final ActorSystem system;

    public ResponseHandler(ActorSystem system, int start, int end) {
        this.start = start;
        this.end = end;
        this.TIME_OUT = Settings.timeout; // 60 Seconds
        this.system = system;
    }

    @Override
    public void run() {

        System.out.println("Requesting CDR records from " + start + " to " + end);
        
        final Materializer materializer = ActorMaterializer.create(system);
        
        final Flow<HttpRequest, HttpResponse, CompletionStage<OutgoingConnection>> connectionFlow =
            Http.get(system).outgoingConnection(toHost(Settings.ipAddress, Settings.port));
        
        final CompletionStage<HttpResponse> responseFuture
                = Source.single(HttpRequest.create().withUri(getUri()))
                .via(connectionFlow)
                .runWith(Sink.<HttpResponse>head(), materializer);

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
                        .split(Settings.jsonSeparator);

                System.out.println(body.length + " records recieved.");

                DatabaseHandler dbThread = new DatabaseHandler(body, start, end);
                dbThread.start();
            } 
            else {
                System.out.println("Error while communicating with the server.");
            }
        } 
        catch (InterruptedException ex) {
            Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ExecutionException ex) {
            Logger.getLogger(ResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private Uri getUri() {
        Uri uri = Uri.create(
                "/cdrRecords?start="
                + start + "&end="
                + end
        );
        return uri;
    }

}
