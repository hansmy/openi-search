package actors.messages;

public class SolrSearchEvent implements SolrEvent{
	private String user;
	private String query;
	
	
	
	public SolrSearchEvent(String user, String query) {
		super();
		this.user = user;
		this.query = query;
	}


	@Override
	public String getUser() {
		
		return user;
	}


	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}


	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

}
