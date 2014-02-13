package actors;

import utils.DropBoxHelper;
import actors.IndexWorker.Msg;
import actors.messages.StartIndexerEvent;
import actors.messages.StopIndexerEvent;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class IndexerActor extends UntypedActor {
	// final ActorRef indexer =
	// getContext().actorOf(Props.create(IndexWorker.class), "worker");

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO add event handling logic
		if (message instanceof StartIndexerEvent) {
			StartIndexerEvent event = (StartIndexerEvent) message;
			DropBoxHelper dropBoxHelper = new DropBoxHelper(event.getClient());
			final ActorRef indexer = getContext().actorOf(
					Props.create(IndexWorker.class, dropBoxHelper), "worker");
			//indexer.wait(1000);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			indexer.tell(Msg.START, getSelf());

		} else if (message instanceof StopIndexerEvent) {
			// broadcastEvent((UserEvent)message);

		} else if (message instanceof Enum) {
			if (message == Msg.DONE) {

			}

		} else {
			unhandled(message);
		}

	}

}
