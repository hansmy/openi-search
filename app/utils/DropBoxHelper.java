package utils;
import com.dropbox.core.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class DropBoxHelper {
	private DbxClient client;
	public DropBoxHelper(DbxClient client) {
		this.client=client;        
        
    }
	//KeSzp-3nUcIAAAAAAAAAAdKELlVJVhPgo3_Olg0jimM
	public String processFiles(String cursor){
	      System.out.println("Out :" + cursor);
		DbxDelta<DbxEntry> result=null;
		try {
			result = client.getDelta(cursor);
		} catch (DbxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    
		// while (true) {  
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

            
            
     
           
                // Avoid a tight loop by sleeping when there are no more changes.
                // TODO: Use /longpoll_delta instead!
                // See https://www.dropbox.com/developers/blog/63
                try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
       
 
       
       DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       Date date = new Date();
       System.out.println(dateFormat.format(date));
       
     
       return cursor;
    }

	
	
	
	
}
