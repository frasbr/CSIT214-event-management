import java.io.Serializable;

public class Account implements Serializable  {
	// Login Info
	private String username;
	private String password;

	private boolean loggedIn = false;

	// Constructor
	public Account(String _username, String _password) {
		this.username = _username;
		this.password = _password;
	}

	// Getters
	public String getUsername() { return username; }
	public String getPassword() { return password; }

	// Setters
	public void setPassword(String _password) { this.password = _password; }
 
	// Login
	public void login() { loggedIn = !loggedIn; }

	@Override
	public String toString() {
		return String.format(username + " " + password);
	}
}