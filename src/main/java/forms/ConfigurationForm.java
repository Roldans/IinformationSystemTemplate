
package forms;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Range;

public class ConfigurationForm {

	private int		hours;
	private int		minutes;
	private int		seconds;
	private double	chorbiFee;
	private double	managerFee;


	// Constructor-------------------------------------
	public ConfigurationForm() {
		super();
	}
	// Attributes--------------------------------------

	@Min(0)
	public int getHours() {
		return this.hours;
	}

	public void setHours(final int hours) {
		this.hours = hours;
	}

	@Range(min = 0, max = 59)
	public int getMinutes() {
		return this.minutes;
	}

	public void setMinutes(final int minutes) {
		this.minutes = minutes;
	}

	@Range(min = 0, max = 59)
	public int getSeconds() {
		return this.seconds;
	}

	public void setSeconds(final int seconds) {
		this.seconds = seconds;
	}

	public double getChorbiFee() {
		return this.chorbiFee;
	}

	public void setChorbiFee(final double chorbiFee) {
		this.chorbiFee = chorbiFee;
	}

	public double getManagerFee() {
		return this.managerFee;
	}

	public void setManagerFee(final double managerFee) {
		this.managerFee = managerFee;
	}

}
