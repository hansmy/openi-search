package actors.messages;

import com.dropbox.core.DbxClient;

public class StartIndexerEvent implements IndexEvent {
	private DbxClient client;
	public StartIndexerEvent(DbxClient client) {
		super();
		this.setClient(client);
	}
	public DbxClient getClient() {
		return client;
	}
	public void setClient(DbxClient client) {
		this.client = client;
	}
	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
