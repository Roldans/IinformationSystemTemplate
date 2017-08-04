/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AbuseReportService;
import controllers.AbstractController;
import domain.AbuseReport;

@Controller
@RequestMapping("/abuseReport/user")
public class AbuseReportUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	public AbuseReportService	abuseReportService;


	// Constructors -----------------------------------------------------------

	public AbuseReportUserController() {
		super();
	}

	// Edit ------------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer reportedId) {
		ModelAndView result;
		AbuseReport abuseReport;

		abuseReport = this.abuseReportService.create(reportedId);
		Assert.notNull(abuseReport);

		result = this.createEditModelAndView(abuseReport);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final AbuseReport abuseReport, final BindingResult binding) {
		ModelAndView result;
		AbuseReport res;

		res = this.abuseReportService.reconstruct(abuseReport, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(abuseReport);
		else
			try {
				this.abuseReportService.save(res);
				result = new ModelAndView("redirect:/user/view.do?userId=" + abuseReport.getReported().getId());
			} catch (final IllegalArgumentException e) {
				result = this.createEditModelAndView(abuseReport, e.getMessage());
			}

		return result;
	}

	//Ancillary methods ----------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AbuseReport abuseReport) {
		ModelAndView result;

		result = this.createEditModelAndView(abuseReport, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AbuseReport abuseReport, final String message) {
		ModelAndView result;

		result = new ModelAndView("abuseReport/user/edit");

		result.addObject("abuseReport", abuseReport);
		result.addObject("message", message);

		return result;
	}

}
