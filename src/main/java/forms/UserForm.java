
package forms;

import domain.User;

public class UserForm extends ActorForm {

	private String	picture;
	private String	genre;
	private String	address;


	//Constructor
	public UserForm() {
		super();
	}

	public UserForm(final User user) {
		super(user);

		this.setPicture(user.getPicture());
		this.setGenre(user.getGenre());
		this.setAddress(user.getAddress());

	}

	//attributes------------

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

}
