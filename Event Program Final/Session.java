import java.io.Serializable;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;

public class Session implements Serializable {
	private LocalDate date;
	private LocalTime time;
	private int price;
	private int capacity;
	private int maxCapacity; 

	private ArrayList<User> attendees = new ArrayList<User>();

	public Session(int _day, int _month, int _year, int _hour, int _minute, int _price, int _maxCapacity) {
		this.date = LocalDate.of(_year, _month, _day);
		this.time = LocalTime.of(_hour, _minute);
		this.capacity = 0;
		this.price = _price;
		this.maxCapacity = _maxCapacity;
	}

	public int getPrice() 	 	{ return price; 		  }
	public int getCapacity() 	{ return capacity; 	  	  }
	public String getTime() 	{ return time.toString(); }
	public String getDate() 	{ return date.toString(); }
	public ArrayList<User> getAttendees() { return attendees; }

	public void makeBooking(User customer, int totalTickets) {
		if (!attendees.contains(customer)) {
			attendees.add(customer);
			//increaseCapacity()
		} 
	}

	// private void increaseCapacity(int num) {
	// 	// Increase capacity
	// 	if (capacity < maxCapacity) {
	// 		capacity += num;
	// 	}
	// }
}