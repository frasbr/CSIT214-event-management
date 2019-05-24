import java.io.Serializable;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;

public class Session implements Serializable {
	// Member variables
	private LocalDate date;
	private LocalTime time;
	private double price;
	private int capacity;
	private int maxCapacity; 

	// Attendees list
	private ArrayList<User> attendees = new ArrayList<User>();

	// Constructor
	public Session(int _day, int _month, int _year, int _hour, int _minute, double _price, int _maxCapacity) {
		this.date = LocalDate.of(_year, _month, _day);
		this.time = LocalTime.of(_hour, _minute);
		this.capacity = 0;
		this.price = _price;
		this.maxCapacity = _maxCapacity;
	}

	// Getters
	public double getPrice() 	 		{ return price; 		  }
	public int getCapacity() 			{ return capacity; 	  	  }
	public String getTime() 			{ return time.toString(); }
	public String getDate() 			{ return date.toString(); }
	public String displayCapacity() 	{ return capacity + "/" + maxCapacity; }

	public boolean makeBooking(User _customer, int _totalTickets) {
		// Check if attendee exists
		boolean alreadyBooked = false;

		for (User user : attendees) {
			if (user.getFullname().equals(_customer.getFullname())) {
				alreadyBooked = true;
			}
		}

		if (!alreadyBooked) {
			attendees.add(_customer);
			increaseCapacity(_totalTickets);

			return true;
		} else {
			return false;
		}
	}

	private void increaseCapacity(int _num) {
		// Increase capacity
		if (capacity < maxCapacity) {
			capacity += _num;
			//return 0;
		} else {
			//return 1;
		}
	}
}