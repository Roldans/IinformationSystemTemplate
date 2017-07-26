
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;


	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator administrator;
		AdministratorForm administratorForm;

		administrator = this.administratorService.findAdministratorByPrincipal();

		administratorForm = new AdministratorForm(administrator);

		result = this.createEditModelAndView(administratorForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AdministratorForm administratorForm, final BindingResult binding) {
		ModelAndView result;
		Administrator principal, administratorResult;

		principal = this.administratorService.findAdministratorByPrincipal();

		administratorResult = this.administratorService.reconstruct(administratorForm, principal, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(administratorForm);
		else
			try {
				this.administratorService.save(administratorResult);
				result = new ModelAndView("redirect:/actor/myProfile.do");
			} catch (final IllegalArgumentException oops) {
				result = this.createEditModelAndView(administratorForm, oops.getMessage());
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AdministratorForm administratorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(administratorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm administratorForm, final String message) {
		ModelAndView result;
		String requestURI;

		requestURI = "administrator/administrator/edit.do";

		result = new ModelAndView("administrator/edit");
		result.addObject("administratorForm", administratorForm);
		result.addObject("isNew", false);
		result.addObject("requestURI", requestURI);
		result.addObject("message", message);

		return result;
	}
}
