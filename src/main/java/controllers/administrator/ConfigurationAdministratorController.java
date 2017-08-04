
package controllers.administrator;

import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;
import forms.ConfigurationForm;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ConfigurationAdministratorController() {
		super();
	}

	// View ---------------------------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		final Configuration conf = this.configurationService.findConfiguration();

		final long millis = conf.getCacheTime();

		final String time = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis)
			- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

		result = new ModelAndView("configuration/administrator/view");
		result.addObject("time", time);
		result.addObject("requestURI", "configuration/administrator/view");

		return result;
	}

	// Edit -----------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Configuration conf;
		ConfigurationForm configurationForm;
		int hours, minutes, seconds;
		long millis;

		conf = this.configurationService.findConfiguration();
		millis = conf.getCacheTime();

		hours = (int) (TimeUnit.MILLISECONDS.toHours(millis));
		minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
		seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

		configurationForm = new ConfigurationForm();
		configurationForm.setHours(hours);
		configurationForm.setMinutes(minutes);
		configurationForm.setSeconds(seconds);

		result = this.createEditModelAndView(configurationForm);

		return result;

	}

	// Save ---------------------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ConfigurationForm configurationForm, final BindingResult binding) {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.reconstruct(configurationForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(configurationForm);
			System.out.println(binding.getAllErrors().toString());
		} else
			try {
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:view.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(configurationForm, oops.getMessage());
			}
		return result;
	}

	// Ancillary Methods ----------------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ConfigurationForm configurationForm) {
		ModelAndView result;

		result = this.createEditModelAndView(configurationForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ConfigurationForm configurationForm, final String message) {
		ModelAndView result;
		result = new ModelAndView("configuration/administrator/edit");

		result.addObject("configurationForm", configurationForm);
		result.addObject("message", message);
		result.addObject("requestURI", "configuration/administrator/edit.do");
		return result;
	}

}
