import java.time.LocalDate;
import java.util.ArrayList;

import java.io.Serializable;

class User extends Account implements Serializable  {
	// User info
	private String fullname;
	private LocalDate dob;
	private int age;

	ArrayList<Booking> bookings = new ArrayList<Booking>();

	public User(String _username, String _password, String _fullname, int _day, int _month, int _year) {
		super(_username, _password);
		this.fullname = _fullname;
		this.dob = LocalDate.of(_year, _month, _day);
		calcAge();
	}

	// Getters
	public String getFullname() { return fullname; }
	public String getDob() { return dob.toString(); }

	// Set data
	public void setData(String _password, String _fullname, int _day, int _month, int _year) {
		this.setPassword(_password);
		this.fullname = _fullname;
		this.dob = LocalDate.of(_year, _month, _day);
		calcAge();
	}

	// Calculate Age
	private void calcAge() {
		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Calculate year
		int year = currentDate.getYear() - dob.getYear();

		// Calculate Age
		if (dob.isAfter(currentDate)) {
			this.age = year;
		} else {
			this.age = year - 1;
		}
	}

	// Add booking
	public void addBooking(Booking _booking) {
		bookings.add(_booking);
	}

	public Booking getBooking(Event _event, Session _session) {
		Booking foundBooking = null;

		for (Booking booking : bookings) {
			// Check if booking and session are the same
			boolean sameEvent = booking.getEvent().getTitle().equals(_event.getTitle());
			boolean sameSession = (booking.getSession().getDate() + " " + booking.getSession().getTime()).equals(_session.getDate() + " " + _session.getTime());
			
			// If they are the same, change boolean
			if (sameEvent && sameSession) {
				foundBooking = booking;
			}
		}
		return foundBooking;
	}

	// Remove Booking
	public void removeBooking(Booking _booking) {
		bookings.remove(_booking);
	}

	// Check if booking exists
	public boolean bookingExists(Event _event, Session _session) {
		boolean sameEvent;
		boolean sameSession;
		boolean bookingExists = false;

		for (Booking booking : bookings) {
			// Check if booking and session are the same
			sameEvent = booking.getEvent().getTitle().equals(_event.getTitle());
			sameSession = (booking.getSession().getDate() + " " + booking.getSession().getTime()).equals(_session.getDate() + " " + _session.getTime());
			
			// If they are the same, change boolean
			if (sameEvent && sameSession) {
				bookingExists = true;
			}
		}

		// Return presence of booking
		return bookingExists;
	}

	// Get total bookings
	public ArrayList<Booking> getTotalBookings() { return bookings; }

	// Used for searching
	@Override
	public String toString() {
		return String.format(getUsername() + " " + getPassword() + " " + fullname + " " + dob + " " + age);
	}
}