import java.io.Serializable;

public class Admin extends Account implements Serializable {
	// Constructor
	public Admin(String _username, String _password) {
		super(_username, _password);
	}
}