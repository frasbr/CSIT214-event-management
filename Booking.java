import java.io.Serializable;

public class Booking implements Serializable {
	private Event event;
	private Session session;
	private int attendees;
	private String requirements;

	public Booking(Event _event, Session _session, int _attendees, String _requirements) {
		this.event = _event;
		this.session = _session;
		this.attendees = _attendees;
		this.requirements = _requirements;
	}

	public Event getEvent() 		{ return event; 		}
	public Session getSession() 	{ return session; 		}
	public int getAttendees() 		{ return attendees; 	}
	public String getRequirements() { return requirements; 	}

	public void updateAttendees(int _attendees) {
		// Chance capacity
		if (_attendees < attendees) {
			session.decreaseCapacity(attendees - _attendees);
		} else {
			session.increaseCapacity(_attendees - attendees);
		}

		this.attendees = _attendees;
	}
}