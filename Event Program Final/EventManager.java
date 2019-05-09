import java.util.HashMap;

public class EventManager {
	// Login Info
	private static Account loggedAccount = null;

	// Data Files
	private static HashMap<String, Account> accounts = new HashMap<String, Account>();
	private static HashMap<String, Event> events = new HashMap<String, Event>();

	public static void performLogin(Account acc) {
		if (loggedAccount != acc) {
			loggedAccount.login();
			loggedAccount = acc;
			loggedAccount.login();
		}
	}

	public static Account getLoggedAccount() { return loggedAccount; }

	// Create Admin, User and Events
	public static void createAdmin() {
		if (!accounts.containsKey("admin")) {
			accounts.put("admin", new Account("admin", "admin111"));
		}
	}

	public static void createUser(String _username, String _password, String _fullname, int _day, int _month, int _year) {
		if (!accounts.containsKey(_username)) {
			accounts.put(_username, new User(_username, _password, _fullname, _day, _month, _year));
		}
	}
	public static void createEvent(String _title, String _location, User _host) {
		if (!events.containsKey(_title)) {
			events.put(_title, new Event(_title, _location, _host));
		}
	}

	// Add Sessions
	public static void addSession(String _title, int _day, int _month, int _year, int _hour, int _minute, int _price, int _maxCapacity) {
		if (events.containsKey(_title)) {
			Event selectedEvent = events.get(_title);

		}
	}

	// Getters
	public static Account getAccount(String _username) {
		Account foundAcc = null;
		if (accounts.containsKey(_username)) {
			foundAcc = accounts.get(_username);
		}
		return foundAcc;
	}
	public static Event getEvent(String _title) {
		Event foundEvent = null;
		if (accounts.containsKey(_title)) {
			foundEvent = events.get(_title);
		}
		return foundEvent;
	}

	// Destructors
	public static void deleteAccount(String _username) {
		if (accounts.containsKey(_username)) {
			accounts.remove(_username);
		}
	}
	public static void deleteEvent(String _title) {
		if (events.containsKey(_title)) {
			events.remove(_title);
		}
	}
	// public static void deleteSession(String _title,)


	//public String getLoggedAccount() { return loggedAccount; }
}