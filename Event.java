import java.util.HashMap;
import java.time.LocalTime;
import java.time.LocalDate;

import java.io.Serializable;

public class Event implements Serializable  {
	// Event info
	private String title;
	private String location;
	private String description;
	private User host; 

	private HashMap<String, Session> sessions = new HashMap<String, Session>();

	private boolean isLaunched;

	// Constructor
	public Event(String _title, String _location, String _description, User _host) {
		this.title = _title;
		this.location = _location;
		this.description = _description;
		this.host = _host;
	}

	// Getters
	public String getTitle() 					{ return title; 					}
	public String getLocation() 				{ return location; 					}
	public String getDescription()				{ return description;				}
	public User getHost() 						{ return host;						}
	public Session getSession(String _key)	 	{ return sessions.get(_key);		}

	// Add Session
	public void addSession(int _day, int _month, int _year, int _hour, int _minute, double _price, int _maxCapacity) {
		// Create Local Date and Time
		LocalDate date = LocalDate.of(_year, _month, _day);
		LocalTime time = LocalTime.of(_hour, _minute);

		// Test String
		String key = date.toString() + " " + time.toString();

		// Check if session exists and add it if it does
		if (!sessions.containsKey(key)) {
			sessions.put(key, new Session(_day, _month, _year, _hour, _minute, _price, _maxCapacity));
		}
	}

	// Remove session
	public void removeSession(String _key) {
		if (sessions.containsKey(_key)) {
			sessions.remove(_key);
		}
	}

	// Add all sessions
	public void addTotalSessions(HashMap<String, Session> _sessions) {
		// Initialise Temp Key
		String tempKey;

		// Loop through sessions argument
		for (Session sess: _sessions.values()) {
			// Get Temp Key
			tempKey = sess.getDate() + " " + sess.getTime();

			// Add to event sessions if it doesn't exist
			if (!sessions.containsKey(tempKey)) {
				sessions.put(tempKey, sess);
			}
		}
	}

	// Get Total Sessions
	public HashMap<String, Session> getTotalSessions() {
		return sessions;
	}

	// Get launch status
	public boolean getLaunched() {
		return isLaunched;
	}

	// Edit Event
	public void editData(String _location, String _description) {
		this.location = _location;
		this.description = _description;
	}

	// Launch event
	public void launchEvent(boolean _status) {
		isLaunched = _status;
	}

	@Override
	public String toString() {
		// Set up initial times
		String results = title + " " + location + " " + host.getFullname();

		// Get Dates and Times
		for (Session session : sessions.values()) {
			if (!results.contains(session.getDate() + " " + session.getTime())) {
				results += " " + session.getDate() + " " + session.getTime();
			}
		}

		return String.format(results);
	}
}