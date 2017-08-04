/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SpamWordService;
import controllers.AbstractController;
import domain.SpamWord;

@Controller
@RequestMapping("/spamword/administrator")
public class SpamWordAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	public SpamWordService	spamWordService;


	// Constructors -----------------------------------------------------------

	public SpamWordAdministratorController() {
		super();
	}

	// List ------------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SpamWord> spamWords;

		result = new ModelAndView("spamword/administrator/list");
		spamWords = this.spamWordService.findAll();

		result.addObject("spamWords", spamWords);
		result.addObject("requestURI", "spamword/administrator/list.do");

		return result;
	}

	// Edit ------------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		SpamWord spamWord;

		spamWord = this.spamWordService.create();

		Assert.notNull(spamWord);

		result = this.createEditModelAndView(spamWord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SpamWord spamWord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(spamWord);
		else
			try {
				this.spamWordService.save(spamWord);
				result = new ModelAndView("redirect:list.do");
			} catch (final IllegalArgumentException e) {
				result = this.createEditModelAndView(spamWord, e.getMessage());
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@Valid final int spamWordId) {
		ModelAndView result;

		try {
			this.spamWordService.delete(spamWordId);
			result = new ModelAndView("redirect:list.do");
		} catch (final IllegalArgumentException e) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", e.getMessage());
		}

		return result;
	}

	//Ancillary methods ----------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SpamWord spamWord) {
		ModelAndView result;

		result = this.createEditModelAndView(spamWord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SpamWord spamWord, final String message) {
		ModelAndView result;

		result = new ModelAndView("spamword/administrator/edit");

		result.addObject("spamWord", spamWord);
		result.addObject("message", message);

		return result;
	}

}
