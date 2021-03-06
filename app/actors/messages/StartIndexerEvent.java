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
