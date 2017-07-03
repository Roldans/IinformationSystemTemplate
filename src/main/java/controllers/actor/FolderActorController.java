
package controllers.actor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/folder")
public class FolderActorController extends AbstractController {

	//InitBinder---------------------------------------------------------------

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
	}


	// Services ---------------------------------------------------------------

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;
	@Autowired
	private MessageService	messageService;


	//	@Autowired
	//	private MessageService	messageService;

	// Constructors -----------------------------------------------------------

	public FolderActorController() {
		super();
	}

	// List -------------------------------------------------------------------	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor actor = this.actorService.findActorByPrincipal();
		final Collection<Folder> folders = this.folderService.findFoldersOfActor(actor);

		//fin del todo
		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/list.do");

		return result;
	}
	// Edition ----------------------------------------------------------------	

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "folderId")
	public ModelAndView edit(@RequestParam(required = false) final Integer folderId) {
		final ModelAndView result;
		final Folder folder;
		if (folderId != null)
			folder = this.folderService.findOne(folderId);
		else
			folder = this.folderService.create(this.actorService.findActorByPrincipal());
		Assert.notNull(folder);

		result = this.createEditModelAndView(folder);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;

		Assert.notNull(folder);

		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(folder);

		} else if (folder.getReadOnly())
			result = new ModelAndView("redirect:list.do");
		else
			try {
				this.folderService.save(folder);
				result = new ModelAndView("redirect:list.do");

			} catch (final IllegalArgumentException e) {
				result = this.createEditModelAndView(folder, e.getMessage());
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;

		Assert.notNull(folder);

		if (folder.getReadOnly())
			result = new ModelAndView("redirect:list.do");
		else
			try {
				this.folderService.delete(folder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}
		return result;
	}

	// View -------------------------------------------------------------------	

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = "folderId")
	public ModelAndView view(final int folderId) {
		ModelAndView result;
		Collection<Message> messages = new ArrayList<Message>();
		final Folder folder = this.folderService.findOne(folderId);

		messages = this.messageService.findMessagesInFolder(folderId);

		result = new ModelAndView("folder/view");
		result.addObject("folder", folder);
		result.addObject("messages", messages);
		result.addObject("requestURI", "folder/view.do?folderId=" + folderId);
		return result;
	}
	// Ancillary Methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;
		result = this.createEditModelAndView(folder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String message) {
		ModelAndView result;
		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		result.addObject("message", message);
		return result;
	}

}
