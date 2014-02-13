package actors.messages;

public class SolrDeleteEvent implements SolrEvent {
	private String path;
	private String user;

	public SolrDeleteEvent(String user, String path) {
		this.user = user;
		this.path = path;
	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
