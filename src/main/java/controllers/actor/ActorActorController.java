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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/actor")
public class ActorActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	public ActorService	actorService;


	// List ------------------------------------------------------------------		
	//Es un listado de los actores del sistema, para tener una primera vista de listado
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Actor> actors;

		result = new ModelAndView("actor/list");

		actors = this.actorService.findAll();

		result.addObject("actors", actors);
		result.addObject("requestURI", "request/actor/list.do");

		return result;
	}
}
