package actors;
import play.Logger;
import utils.DropBoxHelper;
import actors.IndexWorker.Msg;
import actors.messages.StartIndexerEvent;
import actors.messages.StopIndexerEvent;
import actors.messages.UserEvent;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class IndexerActor extends UntypedActor	 {
	//final ActorRef indexer =	getContext().actorOf(Props.create(IndexWorker.class), "worker");
	

	@Override
	 public void onReceive(Object message) throws Exception {
        // TODO add event handling logic
        if(message instanceof StartIndexerEvent){
        	StartIndexerEvent event=(StartIndexerEvent)message;
        	DropBoxHelper dropBoxHelper=new DropBoxHelper(event.getClient());
        	final ActorRef indexer =	getContext().actorOf(Props.create(IndexWorker.class,dropBoxHelper),	"worker");
        	indexer.tell(Msg.START, getSelf());
          
        } else if(message instanceof StopIndexerEvent) {
         // broadcastEvent((UserEvent)message);
            
        } else if(message instanceof Enum) {
           if(message==Msg.DONE){
        	   
           }
            
           }
        else {
            unhandled(message);
        }
        
    }

}
