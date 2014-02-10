package controllers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

import actors.IndexerActor;
import actors.messages.StartIndexerEvent;
import akka.actor.ActorRef;
import akka.actor.Props;
import play.libs.Akka;
import play.mvc.Controller;
import play.mvc.Result;

public class IndexerController extends Controller {
     
    
    private final ActorRef ref= Akka.system().actorOf(Props.create(IndexerActor.class),"Indexer");
   // private final String appKey;
   // private final String appSecret;
    
	public  Result startIndexing() {
    	//this it will index just one user
		 final String APP_KEY = "do94s6laa15zhtp";
         final String APP_SECRET = "d055a137s7xjfjg";

         DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

         DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
             Locale.getDefault().toString());
         DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

         // Have the user sign in and authorize your app.
         String authorizeUrl = webAuth.start();
         //System.out.println("1. Go to: " + authorizeUrl);
         System.out.println("2. Click \"Allow\" (you might have to log in first)");
         System.out.println("3. Copy the authorization code.");
         String code = "m-sR86cTLzsAAAAAAAAAAXBjQHdM5VsfFIAFQMM4X7Y".trim();
         //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
			
		

         // This will fail if the user enters an invalid authorization code.
        /* DbxAuthFinish authFinish = null;
		try {
			authFinish = webAuth.finish(code);
			System.out.println(authFinish);
			System.out.println(authFinish.accessToken);
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("I saw this line: "+code);
         DbxClient client = new DbxClient(config, "Ktqkt7NkvmEAAAAAAAAAAaxQVS8fp-pIb18SyuMEyGEi4P_RsWkhuiHv64fAlJZN");

         try {
			System.out.println("Linked account: " + client.getAccountInfo().displayName);
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         ref.tell(new StartIndexerEvent(client), ActorRef.noSender());

        return ok(views.html.index.render("Start Indexing"));
    }
    
    
}
