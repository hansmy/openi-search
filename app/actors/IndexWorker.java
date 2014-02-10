package actors;

import actors.messages.UpdateCursorEvent;
import akka.actor.UntypedActor;
import utils.DropBoxHelper;

public class IndexWorker extends UntypedActor {
	private final DropBoxHelper dbhelper;

	public IndexWorker(DropBoxHelper dbhelper) {
		this.dbhelper = dbhelper;
	}

	public static enum Msg {
		START, UPDATE, DONE;
	}

	@Override
	public void onReceive(Object msg) {
		if (msg == Msg.START) {
			System.out.println("Start");
			String cursor = dbhelper.processFiles(null);
			if (cursor != null) {
				UpdateCursorEvent eventCursor = new UpdateCursorEvent(cursor);
				getSelf().tell(eventCursor, getSelf());

			} else {
				getSender().tell(Msg.DONE, getSelf());
			}

		} else if (msg instanceof UpdateCursorEvent) {
			System.out.println("UpdateCursor");
			UpdateCursorEvent eventCursor = (UpdateCursorEvent) msg;
			String cursor = dbhelper.processFiles(eventCursor.getCursor());
			if (cursor != null) {
				eventCursor.setCursor(cursor);
				getSelf().tell(eventCursor, getSelf());

			} else {
				getSender().tell(Msg.DONE, getSelf());
			}

		} else {
			unhandled(msg);
		}
	}

}
