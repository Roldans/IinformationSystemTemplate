
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import controllers.AbstractController;
import controllers.ActorController;
import domain.Actor;
import domain.Comment;
import domain.Commentable;
import domain.User;

@Controller
@RequestMapping("/comment")
public class CommentActorController extends AbstractController {

	@Autowired
	private CommentService		commentService;
	@Autowired
	private CommentableService	commentableService;

	@Autowired
	private ActorController		actorController;


	// List --------------------------------------------------------------------

	// Create --------------------------------------------------------------------
	@RequestMapping(value = "/actor/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int commentableId) {
		final Commentable commentable;
		ModelAndView result;
		Comment comment;
		String requestURI = "";
		String cancelURI = "";

		commentable = this.commentableService.findOne(commentableId);

		comment = this.commentService.create(commentable);

		result = this.createEditModelAndView(comment);

		requestURI = "comment/actor/create.do?actorId=" + comment.getCommentable().getId();
		if (Actor.class.equals(comment.getCommentable().getClass()))
			cancelURI = "actor/view.do?actorId=" + comment.getCommentable().getId();
		result.addObject("cancelURI", cancelURI);
		result.addObject("requestURI", requestURI);
		result.addObject("comment", comment);
		return result;

	}
	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/actor/create", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(final Comment comment, final BindingResult binding) {
		ModelAndView result = null;
		Comment commentReconstructed;

		commentReconstructed = this.commentService.reconstruct(comment, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(comment);
		else
			try {

				this.commentService.save(commentReconstructed);
				if (User.class.equals(comment.getCommentable().getClass()))
					result = this.actorController.view(comment.getCommentable().getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, oops.getMessage());
			}

		return result;
	}

	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/actor/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int commentId) {
		ModelAndView result = null;
		Comment comment;

		comment = this.commentService.findOne(commentId);

		this.commentService.delete(comment);

		if (Actor.class.equals(comment.getCommentable().getClass()))
			result = this.actorController.view(comment.getCommentable().getId());

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;
		result = new ModelAndView("comment/actor/create");

		result.addObject("comment", comment);
		result.addObject("message", message);
		return result;
	}

}
