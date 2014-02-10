package actors;

import akka.actor.UntypedActor;
import utils.DropBoxHelper;

public class IndexWorker extends UntypedActor {
	private final DropBoxHelper dbhelper;
	
	public IndexWorker(DropBoxHelper dbhelper){
		this.dbhelper=dbhelper;
	}
		
	public static enum Msg {
		START, DONE;
	}

	@Override
	public void onReceive(Object msg) {
		if (msg == Msg.START){
			System.out.println("Hello World!");
			
			getSender().tell(Msg.DONE, getSelf());
		} else
			unhandled(msg);
	}

}
