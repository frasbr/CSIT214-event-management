import java.io.Serializable;

public class Account implements Serializable {
	// Login Info
	private String username;
	private String password;
	private boolean loggedIn = false;

	public Account(String _username, String _password) {
		this.username = _username;
		this.password = _password;
	}
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	public void login() { loggedIn = !loggedIn; }

	public void print() {
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
	}
}