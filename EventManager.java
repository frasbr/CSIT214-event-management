import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UnknownFormatConversionException;

import java.io.IOException;
import java.io.EOFException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.nio.file.Files;
import java.nio.file.Paths;

public class EventManager {
	// HashMaps
	private static HashMap<String, Account> accounts = new HashMap<String, Account>();
	private static HashMap<String, Event> events = new HashMap<String, Event>();

	// Logged User
	private static Account loggedAccount = null;

	// Current Event
	private static Event selectedEvent = null;
	private static Session selectedSession = null;

	// Perform Login
	public static int performLogin(Account _acc) {
		if (_acc != null) {
			if (loggedAccount != _acc) {
				System.out.println(getLoggedAccount());
				loggedAccount = _acc;
				loggedAccount.login();
				System.out.println(getLoggedAccount());
				
				return 0;
			} else {
				return -2;
			}
		} else {
			return -1;
		}
	}

	// Perform Logout
	public static void performLogout() {
		if (loggedAccount != null) {
			System.out.println(getLoggedAccount());
			loggedAccount.login();
			loggedAccount = null;
			System.out.println(getLoggedAccount());
		}
	}

	// Get Logged Account
	public static Account getLoggedAccount() {
		return loggedAccount;
	}	

	// Select Event
	public static void selectEvent(Event _event) {
		selectedEvent = _event;
	}

	// Get Selected Event
	public static Event getSelectedEvent() {
		return selectedEvent;
	}

	// Select Sessions
	public static void selectSession(Session _session) {
		selectedSession = _session;
	}

	// Get Selected Event
	public static Session getSelectedSession() {
		return selectedSession;
	}

	// Create Admin
	public static void createAdmin() {
		if (!accounts.containsKey("admin")) {
			accounts.put("admin", new Admin("admin", "admin123"));
		}
	}

	// Create User
	public static void createUser(String _username, String _password, String _fullname, int _day, int _month, int _year) {
		if (!accounts.containsKey(_username)) {
			accounts.put(_username, new User(_username, _password, _fullname, _day, _month, _year));
		}
	}
	// Add Accounts to Hashmap
	public static void addUserToMap(Account _acc) {
        if (!accounts.containsKey(_acc.getUsername())) {
            if (_acc instanceof Admin) {
            	Admin admin = (Admin) _acc;
                accounts.put(_acc.getUsername(), admin);
            } else {
            	User user = (User) _acc;
            	accounts.put(_acc.getUsername(), user);
            }
        }
    }

	// Create Event
    public static void createEvent(String _title, String _location, String _description, User _host) {
    	if (!events.containsKey(_title)) {
			events.put(_title, new Event(_title, _location, _description, _host));
		}
    }

	// Get User
	public static Account getAccount(String _username) {
		Account foundAccount = null;
		if (accounts.containsKey(_username)) {
			foundAccount = accounts.get(_username);
		}
		return foundAccount;
	}

	// Get Event
	public static Event getEvent(String _title) {
		Event foundEvent = null;
		if (events.containsKey(_title)) {
			foundEvent = events.get(_title);
		}
		return foundEvent;
	}

	// Get Total Events
	public static HashMap<String, Event> getTotalEvents() {
		return events;
	}

	// Get Total Accounts
	public static HashMap<String, Account> getTotalAccounts() {
		return accounts;
	}

	// Write to File
	public static void writeFile(String _filename) {
		try {
			// Set up Writing File
			ObjectOutputStream outData = new ObjectOutputStream(Files.newOutputStream(Paths.get(_filename + ".ser")));
			
			// Write data to file
			if (_filename.equals("accounts")) {
				for (Account acc : accounts.values()) {
					outData.writeObject(acc);
				}
			} else {
				for (Event event : events.values()) {
					outData.writeObject(event);
				}
			}	
			// Close file once data is recorded
			if (outData != null) outData.close();

		// } catch(IOException e) {
		// 	System.err.println("Error opening file. Terminating...");
		// 	System.exit(1);
		// } catch (NoSuchElementException e) {
		// 	System.err.println("Invalid Data Type Given");
		// 	System.exit(1);
		// }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// Read to File	
	public static void readFile(String _filename) {
		try {
			// Set up Reading File
			ObjectInputStream inData = new ObjectInputStream(Files.newInputStream(Paths.get(_filename + ".ser")));
		
			// Read Data from file
			if (_filename.equals("accounts")) {
				while (inData != null) {
					// Create New User
					Account acc = (Account) inData.readObject();
					addUserToMap(acc);

					System.out.println(acc);
				}
			} else {
				while (inData != null) {
					// Create New Event
					Event event = (Event) inData.readObject();
					createEvent(event.getTitle(), event.getLocation(), event.getDescription(), event.getHost());

					// Add Sessions and set launched
					Event ev = getEvent(event.getTitle());
					ev.launchEvent(event.getLaunched());
					ev.addTotalSessions(event.getTotalSessions());

					System.out.println(ev);
					for (Session session : ev.getTotalSessions().values()) {
						System.out.println(" > " + session.getDate() + " " + session.getTime() + " " + session.getPrice() + " " + session.getCapacity());
					}
				}
			}

		} catch (EOFException e) {

		} catch (ClassNotFoundException e) {

		} catch (IOException e) {

		} catch (UnknownFormatConversionException e) {

		}
	}
}