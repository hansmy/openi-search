package actors;

import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import com.dropbox.core.DbxDelta;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxDelta.Entry;

import actors.messages.SolrIndexEvent;
import actors.messages.SolrDeleteEvent;
import actors.messages.UpdateCursorEvent;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.UntypedActor;
import utils.DropBoxHelper;

public class IndexWorker extends UntypedActor {
	private final DropBoxHelper dbhelper;
	final String identifyId = "1";
	{
		ActorSelection router = getContext().system().actorSelection(
				"/user/myrouter1");
		System.out.println(router.toString());

		router.tell(new Identify(identifyId), getSelf());

	}
	ActorRef another;

	public IndexWorker(DropBoxHelper dbhelper) {
		this.dbhelper = dbhelper;

	}

	public static enum Msg {
		START, UPDATE, DONE;
	}

	@Override
	public void onReceive(Object msg) {
		if (msg instanceof ActorIdentity) {
			ActorIdentity identity = (ActorIdentity) msg;
			if (identity.correlationId().equals(identifyId)) {
				ActorRef ref = identity.getRef();
				if (ref == null)
					getContext().stop(getSelf());
				else {
					another = ref;
					System.out.println(another);
					// getContext().watch(another);

				}
			}
		} else {
			if (another != null) {
				if (msg == Msg.START) {
					System.out.println("Start");

					DbxDelta<DbxEntry> result = null;
					do {
						result = dbhelper.processFiles(null);
						List<Entry<DbxEntry>> entries = result.entries;
						this.indexingSolr(entries);
					} while (result.hasMore);
					System.out.println(result.hasMore);
					UpdateCursorEvent eventCursor = new UpdateCursorEvent(
							result.cursor);
					// getSender().tell(Msg.DONE, getSelf());
					getSelf().tell(eventCursor, getSelf());

				} else if (msg instanceof UpdateCursorEvent) {
					System.out.println("UpdateCursor");
					UpdateCursorEvent eventCursor = (UpdateCursorEvent) msg;

					DbxDelta<DbxEntry> result = null;
					do {
						System.out.println(eventCursor.getCursor());
						// here should be longpoll delta
						result = dbhelper.processFiles(eventCursor.getCursor());
						System.out.println(result.entries.size());
						if (result.entries.size() > 0) {
							this.indexingSolr(result.entries);
						}
					} while (result.hasMore);
					eventCursor.setCursor(result.cursor);
					getSelf().tell(eventCursor, getSelf());
					// getSender().tell(Msg.DONE, getSelf());
					/*
					 * else { getSender().tell(Msg.DONE, getSelf()); }
					 */

				} else {
					unhandled(msg);
				}

			}
		}

	}

	public void indexingSolr(List<Entry<DbxEntry>> entries) {
		for (Entry<DbxEntry> entry : entries) {
			if (entry.metadata == null) {
				System.out.println("Deleted: " + entry.lcPath);
				SolrDeleteEvent eventSolr = new SolrDeleteEvent("",
						entry.lcPath);
				// logic for delete the doc
			} else {
				SolrInputDocument doc = dbhelper.pipeToSolr(entry);
				if (doc != null) {
					System.out.println(doc.toString());
				}
				// creating solrinput doc
				SolrIndexEvent eventSolr = new SolrIndexEvent("", doc);

				another.tell(eventSolr, getSelf());
			}
			// send to solr using solr index event
		}
	}

}
