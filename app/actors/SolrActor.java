package actors;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;


import actors.messages.SolrDeleteEvent;
import actors.messages.SolrIndexEvent;
import akka.actor.UntypedActor;


public class SolrActor extends UntypedActor {
	private SolrServer server;
    



	public boolean createUsersCollection(String usersId) {
		return true;
	}
	
	public boolean indexDocument(String userId, Object document){
		
		return true;
	}

	public boolean indexUpdated(SolrIndexEvent msg) {
		System.out.println("SolrIndexEvent");
		System.out.println(msg.getDocuement().get("id"));
		
		return false;
	}

	@Override
	public void preStart() {
		String url="http://localhost:8983/solr";
		server=new HttpSolrServer(url );
		
	}

	@Override
	public void onReceive(Object msg)  {
		if(msg instanceof SolrIndexEvent ){
			indexUpdated((SolrIndexEvent) msg);
			/*try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}else if(msg instanceof SolrDeleteEvent){
			
		}else {
			unhandled(msg);
		}
	}

}
