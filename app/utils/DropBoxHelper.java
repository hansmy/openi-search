package utils;

import com.dropbox.core.*;
import com.dropbox.core.DbxDelta.Entry;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.solr.common.SolrInputDocument;

public class DropBoxHelper {
	private DbxClient client;
	private static final String REGEX_BINARY_TYPES_NOT_SUPPORTED = ".*(jpg|jpeg|png|tiff|tif|bmp|mov|bin)$";

	public DropBoxHelper(DbxClient client) {
		this.client = client;

	}

	// KeSzp-3nUcIAAAAAAAAAAdKELlVJVhPgo3_Olg0jimM
	public DbxDelta<DbxEntry> processFiles(String cursor) {

		DbxDelta<DbxEntry> result = null;
		try {
			result = client.getDelta(cursor);
		} catch (DbxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// while (true) {
		cursor = result.cursor;
		if (result.reset) {
	//		System.out.println("Reset!");
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

		return result;
	}

	public SolrInputDocument pipeToSolr(DbxDelta.Entry entry) {
		SolrInputDocument doc = null;

		if (entry.metadata instanceof DbxEntry) {
			DbxEntry real = (DbxEntry) entry.metadata;

			DbxEntry.File file = real.asFile();
			System.out.println(file.toString());

			System.out.println("Added or modified: " + entry.lcPath);

			String title = file.path.replaceAll("/.*\\/([^\\/\\.]*)\\.?.*/",
					"$1");
			if (isSupported(file.path)) {
				DateFormat df1 = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				String dateFormat = df1.format(file.clientMtime);
				doc = new SolrInputDocument();
				doc.setField("literal.id", entry.lcPath);
				doc.setField("literal.title", title);
				doc.setField("literal.rev", file.rev);
				doc.setField("literal.when", dateFormat);
				doc.setField("literal.path", file.path);
				doc.setField("literal.icon", file.iconName);
				doc.setField("literal.size", file.numBytes);
				try {
					DbxUrlWithExpiration urlEx = client
							.createTemporaryDirectUrl(entry.lcPath);
					doc.setField("literal.links", urlEx.url.toString());
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			// mime-type can be infered from path

		}
		return doc;

	}

	private boolean isSupported(String path) {

		return !path.matches(REGEX_BINARY_TYPES_NOT_SUPPORTED);
	}
}
