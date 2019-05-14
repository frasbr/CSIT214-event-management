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

	// Perform Login
	public static void performLogin(Account _acc) {
		try {
			if (loggedAccount != _acc) {
				System.out.println(getLoggedAccount());
				loggedAccount = _acc;
				loggedAccount.login();
				System.out.println(getLoggedAccount());
			}
		} catch (NullPointerException e) {

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
	public static void createEvent(String _title, String _location, User _host) {
		if (!events.containsKey(_title)) {
			events.put(_title, new Event(_title, _location, _host));
		}
	}

	// Add Session
	

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

	// Delete Account
	public void deleteAccount(String _username) {
		if (accounts.containsKey(_username)) {
			accounts.remove(_username);
		}
	}

	// Delete Event
	public void deleteEvent(String _title) {
		if (events.containsKey(_title)) {
			events.remove(_title);
		}
	}

	// Delete Session

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

		} catch(IOException e) {
			System.err.println("Error opening file. Terminating...");
			System.exit(1);
		} catch (NoSuchElementException e) {
			System.err.println("Invalid Data Type Given");
			System.exit(1);
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
					Account acc = (Account) inData.readObject();
					addUserToMap(acc);
				}
			} else {
				while (inData != null) {
					Event event = (Event) inData.readObject();
					createEvent(event.getTitle(), event.getLocation(), event.getHost());
				}
			}

		} catch (EOFException e) {

		} catch (ClassNotFoundException e) {

		} catch (IOException e) {

		} catch (UnknownFormatConversionException e) {

		}
	}
}