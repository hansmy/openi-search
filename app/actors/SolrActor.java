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

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.ContentStreamBase;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import actors.messages.SolrDeleteEvent;
import actors.messages.SolrIndexEvent;
import actors.messages.SolrSearchEvent;
import akka.actor.UntypedActor;

public class SolrActor extends UntypedActor {
	private SolrServer server;

	public boolean createUsersCollection(String usersId) {
		return true;
	}

	public boolean indexDocument(String userId, Object document) {

		return true;
	}

	public void indexUpdated(SolrIndexEvent msg) {
		try {
			System.out.println("SolrIndexEvent");
			SolrInputDocument doc = msg.getDocuement();
			//Making realtime GET
			System.out.println("GET");
			SolrQuery parameters = new SolrQuery();
			parameters.setRequestHandler("/get");
			String f1 = doc.getFieldValue("literal.id").toString();
			String f2 = doc.getFieldValue("literal.rev").toString();
			parameters.set("id", f1);
			parameters.set("rev", f2);
			//System.out.println(parameters);

			QueryResponse response = server.query(parameters);
			
			NamedList<Object> result = response.getResponse();
			//System.out.println(response.getResponse());
			//System.out.println(result.size() );
			//System.out.println();
			//System.out.println(result);
			//validate the doc exists
			if (result == null || result.get("doc")==null) {
				System.out.println("/update/extract");
				ContentStreamUpdateRequest req = new ContentStreamUpdateRequest(
						"/update/extract");
				// url dropbox
				URL url = new URL(doc.getFieldValue("literal.links").toString());
				ContentStreamBase content = new ContentStreamBase.URLStream(url);
				System.out.println("ContentStreamBase");
				req.addContentStream(content);
				// Adittionall metadata
				req.setParam("literal.id", doc.getFieldValue("literal.id")
						.toString());
				req.setParam("literal.title", doc
						.getFieldValue("literal.title").toString());
				req.setParam("literal.rev", doc.getFieldValue("literal.rev")
						.toString());
				req.setParam("literal.when", doc.getFieldValue("literal.when")
						.toString());
				req.setParam("literal.path", doc.getFieldValue("literal.path")
						.toString());
				req.setParam("literal.icon", doc.getFieldValue("literal.icon")
						.toString());
				req.setParam("literal.size", doc.getFieldValue("literal.size")
						.toString());
				req.setParam("literal.url", doc.getFieldValue("literal.links").toString());

				req.setParam("uprefix", "attr_");
				req.setParam("fmap.content", "attr_content");
				req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
				//Requesting Solr
				result = server.request(req);
				//System.out.println("Result: " + result.toString());

			} else {
				System.out.println("It's already update");

				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void preStart() {
		try {
			String url = "http://localhost:8983/solr";
			server = new ConcurrentUpdateSolrServer(url, 4000, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onReceive(Object msg) {
		if (msg instanceof SolrIndexEvent) {
			indexUpdated((SolrIndexEvent) msg);
			
		} else if (msg instanceof SolrDeleteEvent) {

		} else if (msg instanceof SolrSearchEvent) {
			performedSearch((SolrSearchEvent)msg);
		} else {
			unhandled(msg);
		}
	}
	
	public void performedSearch(SolrSearchEvent msg) {
		try {
			System.out.println("SolrSearchEvent");
			System.out.println("");
			SolrQuery parameters = new SolrQuery();
			//parameters.setRequestHandler("/get");
			parameters.set("q", msg.getQuery());
			parameters.setFields("title","id","path","when","icon","size","content_type");
			parameters.set("defType", "edismax");
			parameters.addFilterQuery("title","content");
			parameters.setStart(0);
			parameters.setHighlight(true).setHighlightSnippets(1); //set other params as needed
		    parameters.setParam("hl.fl", "content");
		    parameters.setParam("wt", "json");
			server.query(parameters) ;
			
			System.out.println(parameters);

			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
