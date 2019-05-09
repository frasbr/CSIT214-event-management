import java.io.Serializable;

import java.time.LocalDate;
import java.time.LocalTime;

public class User extends Account  implements Serializable {
	// User Info
	private String fullname;
	private LocalDate dob;
	private int age;

	public User(String _username, String _password, String _fullname, int _day, int _month, int _year) {
		super(_username, _password);
		this.fullname = _fullname;
		this.dob = LocalDate.of(_year, _month, _day);
		calcAge();
	}
	// Getters
	public String getFullname() { return fullname; 		 }
	public String getDob() 		{ return dob.toString(); }
	public int getAge() 		{ return age; 			 }

	// Calculate Age
	private void calcAge() {
		LocalDate curr = LocalDate.now();

		int year = curr.getYear() - dob.getYear();

		if (dob.isAfter(curr)) {
			this.age = year;
		} else {
			this.age = year - 1;
		}
	}

	// Print
	public void print() {
		super.print();
		System.out.println("Fullname: " + fullname);
		System.out.println("Date of Birth: " + dob);
		System.out.println("Age: " + age);
	}
	@Override
	public String toString() {
		return String.format(fullname + " " + dob + " " + age);
	}
}