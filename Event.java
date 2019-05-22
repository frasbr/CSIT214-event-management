import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {
	// Event Details
	String title;
	String location;
	User host;

	boolean launched = false;

	ArrayList<Session> sessions = new ArrayList<Session>();

	// Constructor
	public Event(String _title, String _location, User _host) {
		this.title = _title;
		this.location = _location;
		this.host = _host;
	}
	// Getters
	public String getTitle() 	{ return title; 					}
	public String getLocation() { return location; 					}
	public User getHost() 		{ return host;						}

	// Add Session
	public void addSession(int _day, int _month, int _year, int _hour, int _minute, double _price, int _maxCapacity) {
		// Create new Session
		Session session = new Session(_day, _month, _year, _hour, _minute, _price, _maxCapacity);

		// Check if session exists
		boolean exists = false;
		
		for (Session sess : sessions) {
			if (sess.getDate().equals(session.getDate()) && sess.getTime().equals(session.getTime())) {
				// This session already exists!
				exists = true;
			}
		}

		// If session does not exist, add it
		if (exists != true) {
			sessions.add(session);
			System.out.println(session.getDate() + " " + session.getTime() + " " + session.getCapacity() + " $" + session.getPrice());
		}
	} 

	public void addTotalSessions(ArrayList<Session> _sessions) {
		for (Session sess : _sessions) {
			sessions.add(sess);
			System.out.println(sess.getDate() + " " + sess.getTime() + " " + sess.getCapacity() + " $" + sess.getPrice());
		}
	}

	// Get Session
	public Session getSession(int index) {
		return sessions.get(index);
	}

	// Get Total Sessions
	public ArrayList<Session> getTotalSessions() {
		return sessions;
	}

	// Remove Session
	public void removeSession(Session _session) {
		for (Session sess : sessions) {
			if (sess == _session) {
				sessions.remove(sess);
			}
		}
	}

	// Set Launched

	// Get Launched
	public boolean getLaunched() {
		return launched;
	}

	// Launch event
	public void launch(boolean status) {
		launched = status;
	}


	@Override
	public String toString() {
		return String.format(title + " " + location + " " + host.getFullname());
	}
}