import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * <p>This is a code example of Twitter4J Streaming API - sample method support.<br>
 * Usage: java twitter4j.examples.PrintSampleStream<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class TweetGet {
    /**
     * Main entry of this application.
     *
     * @param args
     */
    public static void main(String[] args) throws TwitterException {
    	Connection conn = null;
	   
    	try {
    		// tell java I'm using mysql
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    		
    		// connect to MySQL DB on amazon using JDBC
    		conn = DriverManager.getConnection("jdbc:mysql://geotweets.cncbo1roiupx.us-east-1.rds.amazonaws.com:3306/geotweets", "mdt2125", "qwerty123");
    		System.out.println("DB is connected: "+ !conn.isClosed());
    		
	     	// connect to twitter
	    	 ConfigurationBuilder cb = new ConfigurationBuilder();
	         cb.setDebugEnabled(true)
	           .setOAuthConsumerKey("ICsjVwqwMS2YCVsT3xqAETuxQ")
	           .setOAuthConsumerSecret("DfHEa2M0ZccimRrbXPbZfEM8TH599Wo91DrgI6LVYsdTd4mz86")
	           .setOAuthAccessToken("29206858-Gj7BLDf6ezYnAQPS53lA6ZaRrHLimQLUGWJPoo5k2")
	           .setOAuthAccessTokenSecret("QUBBRe0u0JtKscENwYZXIdKDBokg4fbtrdurcWpxRW8WI");
	         
	        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	        StatusListener listener = new StatusDBWriter(conn);
	            
	        twitterStream.addListener(listener);
	        twitterStream.sample();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return;
    	} finally {
//    		if (conn != null) {
//    	        try {
//    	            conn.close();
//    	        } catch (SQLException e) { /* ignored */}
//    	    }
    	
    	}
    }
}