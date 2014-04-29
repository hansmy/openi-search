/*******************************************************************************
 * Copyright (c) 2014 AmbieSense Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
				Thread.sleep(500);
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
