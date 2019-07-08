package users;

/**
 * An exception to throw when we try to  create a user with an already used username or ID 
 * @author Guy Tayou
 */
public class ExistingUserException extends Exception {

	private static final long serialVersionUID = 6369001735235026127L;

	public ExistingUserException() {
	}
}
