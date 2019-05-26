import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import java.io.Serializable;

public class Session implements Serializable {
	// Session Info
	private LocalDate date;
	private LocalTime time;
	private int maxCapacity;
	private double price;

	private int currentCapacity = 0;

	private ArrayList<User> attendees = new ArrayList<User>();

	// Constructor
	public Session(int _day, int _month, int _year, int _hour, int _minute, double _price, int _maxCapacity) {
		this.date = LocalDate.of(_year, _month, _day);
		this.time = LocalTime.of(_hour, _minute);
		this.price = _price;
		this.maxCapacity = _maxCapacity;
	}

	// Getters
	public double getPrice() 	 		{ return price; 		  }
	public int getCurCapacity()			{ return currentCapacity; }
	public int getCapacity() 			{ return maxCapacity; 	  }
	public String getTime() 			{ return time.toString(); }
	public String getDate() 			{ return date.toString(); }
	//public String displayCapacity() 	{ return capacity + "/" + maxCapacity; }

	public ArrayList<User> getAttendees() { return attendees; }

	public void editData(int _day, int _month, int _year, int _hour, int _minute, double _price, int _maxCapacity) {
		this.date = LocalDate.of(_year, _month, _day);
		this.time = LocalTime.of(_hour, _minute);
		this.price = _price;
		this.maxCapacity = _maxCapacity;
	}

	public void makeBooking(Event _event, Session _session, User _user, int _attendees, String _requirements) {
		// Add attendee
		attendees.add(_user);

		// Increase capacity
		increaseCapacity(_attendees);

		// Make booking and add it to User
		Booking booking = new Booking(_event, _session, _attendees, _requirements);
		_user.addBooking(booking);
	}

	public void increaseCapacity(int _count) {
		if (currentCapacity < maxCapacity) {
			currentCapacity += _count;
		} 
	}

	public void decreaseCapacity(int _count) {
		if (currentCapacity >= 0) {
			currentCapacity -= _count;
		}
	}

	public String displayCapacity() {
		return currentCapacity + "/" + maxCapacity;
	}
}