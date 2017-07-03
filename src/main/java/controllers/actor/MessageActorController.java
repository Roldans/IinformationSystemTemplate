/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AttachmentService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Attachment;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message")
public class MessageActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageService		messageService;

	@Autowired
	private AttachmentService	attachmentService;

	@Autowired
	private ActorService		actorService;
	@Autowired
	private FolderService		folderService;


	// Constructors -----------------------------------------------------------

	public MessageActorController() {
		super();
	}

	// Methods -----------------------------------------------------------------

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int messageId) {
		ModelAndView result;
		Message res;
		final Collection<Attachment> attachments;
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		final Collection<Folder> folders = this.folderService.findFoldersOfActor(actor);

		res = this.messageService.findOne(messageId);
		attachments = this.attachmentService.findAttachmentsOfMessage(res);

		result = new ModelAndView("message/actor/view");
		result.addObject("res", res);
		result.addObject("attachments", attachments);
		result.addObject("requestURI", "message/actor/view.do?messageId=" + messageId);
		result.addObject("folders", folders);

		result.addObject("isAdmin", this.messageService.isAdmin(res));

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;

		this.messageService.delete(messageId);

		result = new ModelAndView("redirect:/folder/list.do");

		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final int messageId) {
		ModelAndView result;
		MessageForm messageForm;

		messageForm = this.messageService.replyMessage(messageId);
		result = this.createEditModelAndView(messageForm);

		return result;
	}
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		final Collection<Folder> folders = this.folderService.findFoldersOfActor(actor);

		message = this.messageService.findOne(messageId);

		result = new ModelAndView("message/actor/move");
		result.addObject("requestURI", "message/actor/move.do");
		result.addObject("Message", message);
		result.addObject("folders", folders);
		return result;
	}
	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView move(final Message message, final BindingResult bindingResult) {
		ModelAndView result;
		Message messageReconstructed;

		Actor actor;
		messageReconstructed = this.messageService.reconstruct(message, bindingResult);
		if (bindingResult.hasErrors()) {

			result = this.move(message.getId());
			result.addObject("message", null);
		} else
			try {
				this.messageService.moveMessage(messageReconstructed);
				result = new ModelAndView("redirect:../folder/view.do?folderId=" + message.getFolder().getId());
			} catch (final IllegalArgumentException e) {
				actor = this.actorService.findActorByPrincipal();
				final Collection<Folder> folders = this.folderService.findFoldersOfActor(actor);

				result = new ModelAndView("message/actor/move");
				result.addObject("requestURI", "message/actor/move.do");
				result.addObject("Message", message);
				result.addObject("folders", folders);
			}

		return result;
	}
	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam final int messageId) {
		ModelAndView result;
		MessageForm messageForm;

		messageForm = this.messageService.forwardMessage(messageId);
		result = this.createEditModelAndView(messageForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView write(@RequestParam(required = false) final Integer actorId) {
		ModelAndView result;
		MessageForm messageForm;
		Collection<Actor> actors;
		if (actorId == null) {
			messageForm = new MessageForm();
			actors = this.actorService.findAll();
			result = this.createEditModelAndView(messageForm);
			result.addObject("actors", actors);
		} else {
			messageForm = this.messageService.writeTo(actorId);
			result = this.createEditModelAndView(messageForm);
		}
		return result;
	}
	@RequestMapping(value = "/write", method = RequestMethod.POST, params = "addAttachment")
	public ModelAndView addAttachment(final MessageForm messageForm) {
		ModelAndView result;

		messageForm.addAttachmentSpace();

		result = this.createEditModelAndView(messageForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST, params = "removeAttachment")
	public ModelAndView removeAttachment(final MessageForm messageForm) {
		ModelAndView result;

		messageForm.removeAttachmentSpace();

		result = this.createEditModelAndView(messageForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST, params = "save")
	public ModelAndView write(final MessageForm messageForm, final BindingResult bindingResult) {
		ModelAndView result;
		Message message;
		String error;

		message = this.messageService.reconstruct(messageForm, bindingResult);
		if (bindingResult.hasErrors()) {
			error = null;
			if (bindingResult.hasFieldErrors("url"))
				error = "message.url.error";
			result = this.createEditModelAndView(messageForm, error);
		} else
			try {
				this.messageService.save(message, messageForm.getAttachments());
				result = new ModelAndView("redirect:../folder/view.do?folderId=" + message.getFolder().getId());
			} catch (final IllegalArgumentException e) {
				result = this.createEditModelAndView(messageForm, e.getMessage());
			}

		return result;
	}
	///////////////////////////////////////////

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;
		Collection<Actor> actors;

		result = new ModelAndView("message/actor/write");
		actors = this.actorService.findAll();

		result.addObject("actors", actors);
		result.addObject("messageForm", messageForm);
		result.addObject("message", message);
		result.addObject("requestURI", "message/write.do");

		return result;
	}

}
