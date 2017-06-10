
package forms;

import javax.validation.constraints.AssertTrue;

import security.UserAccount;
import domain.Actor;

public class ActorForm {

	private String		confirmPassword;
	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private Boolean		acepted;
	private UserAccount	userAccount;


	//Constructor
	public ActorForm() {
		super();
	}

	public ActorForm(final Actor actor) {
		super();

		this.setName(actor.getName());
		this.setSurname(actor.getSurname());
		this.setEmail(actor.getEmail());
		this.setPhone(actor.getPhone());
		this.setUserAccount(actor.getUserAccount());

	}

	//attributes------------

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@AssertTrue
	public Boolean getAcepted() {
		return this.acepted;
	}

	public void setAcepted(final Boolean acepted) {
		this.acepted = acepted;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@AssertTrue
	public boolean getValid() {
		boolean result;
		if (this.confirmPassword != null)
			result = this.confirmPassword.equals(this.userAccount.getPassword());
		else
			result = this.userAccount.getPassword() == null;

		return result;
	}

}
