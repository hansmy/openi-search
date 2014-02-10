package actors.messages;

import com.dropbox.core.DbxClient;

public class StartIndexerEvent implements UserEvent {
	private DbxClient client;
	public StartIndexerEvent(DbxClient client) {
		this.setClient(client);
	}
	public DbxClient getClient() {
		return client;
	}
	public void setClient(DbxClient client) {
		this.client = client;
	}
	

}
