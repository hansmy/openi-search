package actors.messages;


import org.apache.solr.common.SolrInputDocument;

public class SolrIndexEvent implements SolrEvent {
	private SolrInputDocument doc;
	private String user;

	public SolrIndexEvent(String user, SolrInputDocument doc) {
		this.setDocument(doc);
		this.user = user;
	}

	@Override
	public String getUser() {
		return user;
	}

	public SolrInputDocument getDocuement() {
		return doc;
	}

	public void setDocument(SolrInputDocument doc) {
		this.doc = doc;
	}

}
