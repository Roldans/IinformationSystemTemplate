
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class AbuseReport extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	description;
	private Date	reportDate;


	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(final Date reportDate) {
		this.reportDate = reportDate;
	}


	// Relationships ----------------------------------------------------------

	private User	reported;
	private User	reporter;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getReported() {
		return this.reported;
	}

	public void setReported(final User reported) {
		this.reported = reported;
	}

	@Valid
	@ManyToOne(optional = true)
	public User getReporter() {
		return this.reporter;
	}

	public void setReporter(final User reporter) {
		this.reporter = reporter;
	}

}
