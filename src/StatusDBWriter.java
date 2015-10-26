import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;


public class StatusDBWriter implements StatusListener {
	Connection conn = null;
	
	public StatusDBWriter(Connection conn) throws SQLException {
	   	// connect to MySQL DB on amazon using JDBC
		this.conn = conn; 
		System.out.println("conn is closed in const "+conn.isClosed());
	}

	@Override
	public void onStatus(Status status) {
		double latitude;
		double longitude;
    	if (status.getGeoLocation() == null) {
    		
    		// try to fetch location from place
            Place place = status.getPlace();
            if (place == null || place.getBoundingBoxCoordinates() == null)
                return;
            GeoLocation loc = place.getBoundingBoxCoordinates()[0][0];
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
    	} else {
            latitude = status.getGeoLocation().getLatitude();
            longitude = status.getGeoLocation().getLongitude();
    	}
		try {    	
			System.out.println(status.getId() + ":" + status.getUser().getScreenName() + "@" + "lati:" + latitude +  " long: " + longitude  +" - " + status.getText());	
	
					
			String sql = "INSERT INTO tweets (id, screen_name, latitude, longitude, text) " +
			               "VALUES (?,?,?,?,?);";
		

			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setLong(1, status.getId());
			preparedStatement.setString(2, status.getUser().getScreenName());
			preparedStatement.setDouble(3, latitude);
			preparedStatement.setDouble(4, longitude);		
			preparedStatement.setString(5, status.getText().replaceAll("\n"," "));
			preparedStatement.executeUpdate(); 
		} catch (SQLException ex) {
			ex.printStackTrace();			
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
