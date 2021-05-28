package ch.bbw.yr.sospri.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * To register a new Member
 * @author peter.rutschmann
 * @version 27.04.2020
 */
public class RegisterMember {
	@NotEmpty(message = "firstname may not be empty" )
	@Size(min=2, max=512, message="firstname needs to be between 2 and 25 characters long.")
	private String prename;
	@NotEmpty (message = "lastname may not be empty" )
	@Size(min=2, max=20, message="lastname needs to be between 2 and 25 characters long.")
	private String lastname;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!*%^&+=])(?=\\S+$).{8,}$", message = "The password is not valid. It needs to be at least 8 characters long, contain at least one digit, contain at least one lower and one upper character and contain atleast one special character")
	private String password;
	private String confirmation;
	private String message;

	public String getPrename() {
		return prename;
	}
	public void setPrename(String prename) {
		this.prename = prename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "RegisterMember{" +
				"prename='" + prename + '\'' +
				", lastname='" + lastname + '\'' +
				", password='" + password + '\'' +
				", confirmation='" + confirmation + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
