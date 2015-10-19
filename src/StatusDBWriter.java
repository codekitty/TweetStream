import java.sql.Connection;
import java.sql.DriverManager;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;


public class StatusDBWriter implements StatusListener {
	Connection conn = null;
	
	public StatusDBWriter(Connection conn) {
	   	// connect to MySQL DB on amazon using JDBC
		this.conn = conn; 
	}

	@Override
	public void onStatus(Status status) {
    	if (status.getGeoLocation()!= null) {
    		System.out.println(status.getId() + ":" + status.getUser().getScreenName() + "@" + status.getGeoLocation() + " - " + status.getText());	
    	}
	}

	@Override
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub

	}

}
