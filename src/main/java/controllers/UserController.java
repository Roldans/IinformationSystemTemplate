
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.UserService;
import domain.Actor;
import domain.Comment;
import domain.User;
import forms.UserForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	//Services------------------------------------------------------------
	@Autowired
	private UserService		userService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CommentService	commentService;


	//List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;

		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	//View------------------------------------------------------------

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int userId) {
		Collection<Comment> comments;

		ModelAndView result;
		User user;
		Actor principal;
		Boolean owner;

		principal = this.actorService.findActorByPrincipal();
		user = this.userService.findOne(userId);
		owner = user.equals(principal);
		comments = this.commentService.findAllCommentsByCommentable(userId);

		result = new ModelAndView("user/view");
		result.addObject("user", user);
		result.addObject("owner", owner);
		result.addObject("comments", comments);

		result.addObject("requestURI", "user/view.do?userId=" + userId);

		return result;
	}
	//Register ------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		UserForm userForm;

		userForm = new UserForm();
		result = this.createRegisterModelAndView(userForm);

		return result;
	}

	// Save ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveNew(@Valid final UserForm userForm, final BindingResult binding) {
		ModelAndView result;
		User user;

		user = this.userService.reconstructNewUser(userForm, binding);

		if (binding.hasErrors())
			result = this.createRegisterModelAndView(userForm);
		else
			try {
				this.userService.save(user);
				result = new ModelAndView("redirect:/");
			} catch (final IllegalArgumentException oops) {
				result = this.createRegisterModelAndView(userForm, oops.getMessage());
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createRegisterModelAndView(final UserForm userForm) {
		ModelAndView result;

		result = this.createRegisterModelAndView(userForm, null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(final UserForm userForm, final String message) {
		ModelAndView result;
		String requestURI;

		requestURI = "user/register.do";

		result = new ModelAndView("user/register");
		result.addObject("userForm", userForm);
		result.addObject("isNew", true);
		result.addObject("requestURI", requestURI);
		result.addObject("message", message);

		return result;
	}
}
