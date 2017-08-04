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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AbuseReportService;
import controllers.AbstractController;
import domain.AbuseReport;

@Controller
@RequestMapping("/abuseReport/administrator")
public class AbuseReportAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	public AbuseReportService	abuseReportService;


	// Constructors -----------------------------------------------------------

	public AbuseReportAdministratorController() {
		super();
	}

	// List ------------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<AbuseReport> abuseReports;

		result = new ModelAndView("abuseReport/administrator/list");
		abuseReports = this.abuseReportService.findAll();

		result.addObject("abuseReports", abuseReports);
		result.addObject("requestURI", "abuseReport/administrator/list.do");

		return result;
	}

	//Ancillary methods ----------------------------------------------------------------------

}
