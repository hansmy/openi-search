package utils;
import com.dropbox.core.*;

import java.io.*;
import java.util.Locale;
public class DropBoxHelper {
	private DbxClient client;
	public DropBoxHelper(DbxClient client) {
		this.client=client;        
        
    }
	//KeSzp-3nUcIAAAAAAAAAAdKELlVJVhPgo3_Olg0jimM
	public void processFiles(String path){
		String cursor = null;
		DbxDelta<DbxEntry> result=null;
		try {
			result = client.getDelta(cursor);
		} catch (DbxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       // while (!result.hasMore) {
		 while (true) {  
            cursor = result.cursor;
            if (result.reset) {
                System.out.println("Reset!");
            }
            for (DbxDelta.Entry entry : result.entries) {
                if (entry.metadata == null) {
                    System.out.println("Deleted: " + entry.lcPath);
                } else {
                    System.out.println("Added or modified: " + entry.lcPath);
                }
            }

            if (!result.hasMore) {
                // Avoid a tight loop by sleeping when there are no more changes.
                // TODO: Use /longpoll_delta instead!
                // See https://www.dropbox.com/developers/blog/63
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }

	
	
	
	
}
