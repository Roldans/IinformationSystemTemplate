
package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import controllers.AbstractController;
import domain.User;
import forms.UserForm;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {

	@Autowired
	private UserService	userService;


	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		User user;
		UserForm userForm;

		user = this.userService.findUserByPrincipal();

		userForm = new UserForm(user);

		result = this.createEditModelAndView(userForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final UserForm userForm, final BindingResult binding) {
		ModelAndView result;
		User principal, userResult;

		principal = this.userService.findUserByPrincipal();

		userResult = this.userService.reconstruct(userForm, principal, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(userForm);
		else
			try {
				this.userService.save(userResult);
				result = new ModelAndView("redirect:/actor/myProfile.do");
			} catch (final IllegalArgumentException oops) {
				result = this.createEditModelAndView(userForm, oops.getMessage());
			}

		return result;
	}

	// Delete ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final UserForm userForm) {
		ModelAndView result;

		try {
			this.userService.delete();
			result = new ModelAndView("redirect:/j_spring_security_logout");

		} catch (final IllegalArgumentException e) {
			result = this.createEditModelAndView(userForm, e.getMessage());
		}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final UserForm userForm) {
		ModelAndView result;

		result = this.createEditModelAndView(userForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm, final String message) {
		ModelAndView result;
		String requestURI;

		requestURI = "user/user/edit.do";

		result = new ModelAndView("user/edit");
		result.addObject("userForm", userForm);
		result.addObject("isNew", false);
		result.addObject("requestURI", requestURI);
		result.addObject("message", message);

		return result;
	}
}
