
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import domain.Actor;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Services------------------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	//View------------------------------------------------------------

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int administratorId) {
		ModelAndView result;
		Administrator administrator;
		Actor principal;
		Boolean owner;

		principal = this.actorService.findActorByPrincipal();
		administrator = this.administratorService.findOne(administratorId);
		owner = administrator.equals(principal);

		result = new ModelAndView("administrator/view");
		result.addObject("administrator", administrator);
		result.addObject("owner", owner);

		result.addObject("requestURI", "administrator/view.do?administratorId=" + administratorId);

		return result;
	}
}
