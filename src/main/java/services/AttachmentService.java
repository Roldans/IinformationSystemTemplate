
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AttachmentRepository;
import domain.Actor;
import domain.Attachment;
import domain.Message;

@Service
@Transactional
public class AttachmentService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private AttachmentRepository	attachmentRepository;


	//Supported Services--------------------------------------------------------------------
	//Simple CRUD methods------------------------------------------------------------
	public Attachment create(final Message message) {
		final Attachment result = new Attachment();
		result.setMessage(message);
		return result;
	}

	public Attachment save(final Attachment attachment) {
		Attachment result;
		result = this.attachmentRepository.save(attachment);
		return result;
	}

	public void delete(final Attachment attachment) {
		Assert.notNull(attachment, "El objeto no puede ser nulo");
		Assert.isTrue(attachment.getId() != 0, "El objeto no puede tener id 0");
		this.attachmentRepository.delete(attachment);

	}

	//Other Bussnisnes methods------------------------------------------------------------
	//Devuelve la lista de Attachments de un mensaje dado
	public List<Attachment> findAttachmentsOfMessage(final Message message) {
		final List<Attachment> result = this.attachmentRepository.findAttachmentsOfMessage(message.getId());
		return result;
	}
	//Guarda los attachments setteandolos al mensaje dado, AVISO: el mensaje debe estar almacenado ya en base de datos si no la ID seria 0.
	public void addAttachments(final Collection<Attachment> attachments, final Message message) {
		final Attachment attachment = this.create(message);
		for (final Attachment a : attachments) {
			attachment.setName(a.getName());
			attachment.setUrl(a.getUrl());
			this.save(attachment);
		}

	}

	//Devuelve una colleci�n con copias de los attachments de un mensaje, se podria coger los attachments por query en vez de pedirlos como entrada.
	public Collection<Attachment> copyAttachments(final Message message) {
		final Attachment attachment = this.create(message);
		final LinkedList<Attachment> result = new LinkedList<Attachment>();
		for (final Attachment a : this.attachmentRepository.findAttachmentsOfMessage(message.getId())) {
			attachment.setName(a.getName());
			attachment.setUrl(a.getUrl());
			result.add(attachment);
		}
		return result;

	}
	//Borra los attachments de un mensaje, se debe llamar antes de borrar el mensaje para evitar problemas de persistencia
	public void deleteAttachmentsOfMessage(final Message message) {
		final List<Attachment> aux = this.findAttachmentsOfMessage(message);
		for (final Attachment attachment : aux)
			this.delete(attachment);

	}

	public void deleteActor(final Actor actor) {
		final Collection<Attachment> attachments = new ArrayList<Attachment>();
		attachments.addAll(this.attachmentRepository.findAttachmentsOfActorDeleting(actor.getId()));
		this.attachmentRepository.delete(attachments);

	}

}
