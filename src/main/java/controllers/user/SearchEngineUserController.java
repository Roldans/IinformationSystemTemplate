
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SearchEngineService;
import controllers.AbstractController;
import domain.Actor;
import domain.SearchEngine;
import domain.User;

@Controller
@RequestMapping("/searchEngine/user")
public class SearchEngineUserController extends AbstractController {

	@Autowired
	private SearchEngineService	searchEngineService;
	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		final Collection<User> results;
		SearchEngine search;
		Actor principal;

		search = this.searchEngineService.findByPrincipal();
		results = search.getResults();

		result = new ModelAndView("searchEngine/user/search.do");
		result.addObject("results", results);
		result.addObject("search", search);
		result.addObject("requestURI", "searchEngine/user/search.do");

		principal = this.actorService.findActorByPrincipal();

		result.addObject("principal", principal);
		return result;
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "save")
	public ModelAndView search(final SearchEngine search, final BindingResult binding) {
		ModelAndView result;
		SearchEngine res;

		Actor principal;

		res = this.searchEngineService.reconstruct(search, binding);
		Collection<User> results;
		if (binding.hasErrors()) {
			results = new ArrayList<User>();

			result = new ModelAndView("searchEngine/user/search.do");
			result.addObject("search", search);
			result.addObject("requestURI", "searchEngine/user/search.do");
			result.addObject("results", results);

		} else
			try {
				this.searchEngineService.save(res);
				result = this.search();

			} catch (final IllegalArgumentException e) {
				results = new ArrayList<User>();

				result = new ModelAndView("searchEngine/user/search.do");
				result.addObject("search", search);
				result.addObject("requestURI", "searchEngine/user/search.do");
				result.addObject("results", results);
				result.addObject("message", e.getMessage());
			}

		principal = this.actorService.findActorByPrincipal();

		result.addObject("principal", principal);
		return result;
	}
}
