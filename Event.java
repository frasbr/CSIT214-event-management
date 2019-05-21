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
		sessions.add(new Session(_day, _month, _year, _hour, _minute, _price, _maxCapacity));
	}

	// Get Session
	public Session getSession(int index) {
		return sessions.get(index);
	}

	// Get Launched
	public boolean getLaunched() {
		return launched;
	}

	// Launch event
	public void launch() {
		launched = !launched;
	}


	@Override
	public String toString() {
		return String.format(title + " " + location + " " + host.getFullname());
	}
}