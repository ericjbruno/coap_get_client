import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;

/**
 * @author ebruno
 *
 */
public class CoapGetClient {
    
    static class AsynchListener implements CoapHandler {
        @Override
        public void onLoad(CoapResponse response) {
            String content = response.getResponseText();
            System.out.println("onLoad: " + content);
        }

        @Override
        public void onError() {
            System.err.println("Error");
        }
    }

    public static void main(String args[]) throws Exception {
        /*
        if ( args.length == 0 ) {
            // display help
            System.out.println("Usage: " + CoapGetClient.class.getSimpleName() + " URI");
            System.out.println("  URI: The CoAP URI of the remote resource to GET");
            System.exit(-1);
        }

        // Use input URI from command line arguments
        URI uri = null; // URI parameter of the request
        try {
            uri = new URI(args[0]);
        }
        catch ( URISyntaxException e ) {
            System.err.println("Invalid URI: " + e.getMessage());
            System.exit(-1);
        }
        */
        
        URI uri = new URI("coap://localhost:5683/helloWorld");

        // synchronous CoAP GET request
        //
        CoapClient client = new CoapClient(uri);
        CoapResponse response = client.get();

        if ( response != null ) {
            System.out.println(response.getCode());
            System.out.println(response.getOptions());
            System.out.println(response.getResponseText());
            
            byte[] bytes = response.getPayload();

            System.out.println("\nDETAILED RESPONSE:");

            // access advanced API with access to more details through .advanced()
            System.out.println(Utils.prettyPrint(response));
        }
        else {
            System.out.println("No response received.");
        }
        
        // asynchronous CoAp GET request
        //
        client.get(new CoapHandler() {
            @Override public void onLoad(CoapResponse response) {
                String content = response.getResponseText();
                System.out.println("onLoad: " + content);
            }

            @Override public void onError() {
                System.err.println("Error");
            }
        });
        
        AsynchListener asynchListener = new AsynchListener();
        client.get( asynchListener );
        // execution continues without waiting...
        
        // wait
        try { Thread.sleep(5000; } catch (IOException e) { }
    }

}
