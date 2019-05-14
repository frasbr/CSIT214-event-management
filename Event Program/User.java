import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class User extends Account implements Serializable {
	// User Info
	private String fullname;
	private LocalDate dob;
	private int age;

	// Constructor
	public User(String _username, String _password, String _fullname, int _day, int _month, int _year) {
		super(_username, _password);
		this.fullname = _fullname;
		this.dob = LocalDate.of(_year, _month, _day);
		calcAge();
	}

	// Getters
	public String getFullname() { return fullname; 			}
	public String getDob()		{ return dob.toString(); 	}
	public int getAge()			{ return age; 				}

	// Calculate Age
	private void calcAge() {
		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Calculate Year
		int year = currentDate.getYear() - dob.getYear();

		// Calculate Age
		if (dob.isAfter(currentDate)) {
			this.age = year;
		}  else {
			this.age = year - 1;
		}
	}

	// toString - Used for searching
	@Override
	public String toString() {
		return String.format(getUsername() + " " + getPassword() + " " + fullname + " " + dob + " " + age);
	}
}