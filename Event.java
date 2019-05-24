import java.io.Serializable;
import java.util.HashMap;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event implements Serializable {
	// Event Details
	String title;
	String location;
	User host;

	// Sessions
	HashMap<String, Session> sessions = new HashMap<String, Session>();

	// Launching
	boolean isLaunched;

	// Constructor
	public Event(String _title, String _location, User _host) {
		this.title = _title;
		this.location = _location;
		this.host = _host;

		this.isLaunched = false;
	}
	// Getters
	public String getTitle() 	{ return title; 					}
	public String getLocation() { return location; 					}
	public User getHost() 		{ return host;						}

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
			
			Session session = sessions.get(key);

			System.out.println(session.getDate() + " " + session.getTime() + " " + session.getCapacity() + " $" + session.getPrice());
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

	// Get Session
	public Session getSession(String _key) {
		Session session = null;
		if (sessions.containsKey(_key)) {
			session = sessions.get(_key);
		}

		return session;
	}

	// Get Total Sessions
	public HashMap<String, Session> getTotalSessions() {
		return sessions;
	}

	// Remove Session
	public boolean removeSession(String _key) {
		if (sessions.containsKey(_key)) {
			sessions.remove(_key);
			return true;
		} else {
			return false;
		}
	}

	// Get launch status
	public boolean getLaunched() {
		return isLaunched;
	}

	// Launch event
	public void launchEvent(boolean _status) {
		isLaunched = _status;
	}

	@Override
	public String toString() {
		return String.format(title + " " + location + " " + host.getFullname());
	}
}