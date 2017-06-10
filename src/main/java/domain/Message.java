
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
public class Message extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	senderName;
	private String	recipientName;
	private Date	sendingMoment;
	private String	subject;
	private String	text;
	private boolean	isSender;


	@NotBlank
	@SafeHtml
	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(final String senderName) {
		this.senderName = senderName;
	}

	@NotBlank
	@SafeHtml
	public String getRecipientName() {
		return this.recipientName;
	}

	public void setRecipientName(final String recipientName) {
		this.recipientName = recipientName;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendingMoment() {
		return this.sendingMoment;
	}

	public void setSendingMoment(final Date sendingMoment) {
		this.sendingMoment = sendingMoment;
	}

	@NotBlank
	@SafeHtml
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String title) {
		this.subject = title;
	}

	@NotBlank
	@SafeHtml
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public boolean getIsSender() {
		return this.isSender;
	}

	public void setIsSender(final boolean isSender) {
		this.isSender = isSender;
	}


	// Relationships ----------------------------------------------------------

	private Actor	sender;
	private Actor	recipient;
	private Folder	folder;


	@Valid
	@ManyToOne(optional = true)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@Valid
	@ManyToOne(optional = true)
	public Actor getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Folder getFolder() {
		return this.folder;
	}

	public void setFolder(final Folder folder) {
		this.folder = folder;
	}

}
