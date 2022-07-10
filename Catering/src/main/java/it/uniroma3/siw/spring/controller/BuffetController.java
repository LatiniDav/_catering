package it.uniroma3.siw.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import it.uniroma3.siw.spring.controller.validator.BuffetValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;
import it.uniroma3.siw.spring.service.PiattoService;
import it.uniroma3.siw.spring.util.FileUploadUtil;

@Controller
public class BuffetController {

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private PiattoService piattoService;

	@Autowired
	private ChefService chefService;

	@Autowired
	private BuffetValidator buffetValidator;

	@GetMapping("/buffets")
	public String getAllBuffets(Model model) {
		List<Buffet> allBuffets = buffetService.findAll();
		model.addAttribute("buffets", allBuffets);
		return "buffets.html";
	}

	@GetMapping("/buffetsAdmin")
	public String getAllBuffetForAdmin(Model model) {
		List<Buffet> allBuffets = buffetService.findAll();
		model.addAttribute("buffets", allBuffets);
		return "buffetsAdmin.html";
	}

	// se serve un attributo di buffet usa questo metodo e poi i relativi
	// getter/setter
	@GetMapping("/buffet/id/{id}")
	public String getBuffetById(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		Chef chef = buffet.getChef();
		model.addAttribute("buffet", buffet);
		model.addAttribute("chef", chef);
		return "buffet.html";
	}

	// se serve un attributo di buffet usa questo metodo e poi i relativi
	// getter/setter
	@GetMapping("/buffetAdmin/id/{id}")
	public String getBuffetByIdAdmin(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "buffetAdmin.html";
	}

	@GetMapping("/buffet/chef/{chef}")
	public String getAllBuffetByChef(@PathVariable("chef") Chef chef, Model model) {
		List<Buffet> buffets = buffetService.findAllBuffetByChef(chef);
		model.addAttribute("buffetsChef", buffets);
		return "chef.html";
	}
	
	@PostMapping("/buffet")
    public String addBuffetFromBuffets(@ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult, @RequestParam("image") MultipartFile multipartFile,
    									Model model) throws IOException {
    	this.buffetValidator.validate(buffet, bindingResult);
        if (!bindingResult.hasErrors()) {
        	
        	/*UPLOAD FOTO*/
        	if(multipartFile.getOriginalFilename() != null) {
        	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            buffet.setImg("/images/" + fileName);
            this.buffetService.save(buffet);
            String uploadDir = "src/main/resources/static/images/";
            if(fileName != null && multipartFile != null && !fileName.isEmpty())
            	FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        	}
        	return this.getAllBuffetForAdmin(model);
        }
        return "buffetFormAdmin.html";
    }
	
	
	
	
	
	

	@GetMapping("/deleteBuffet/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		buffetService.deleteBuffetById(id);
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return "buffetsAdmin.html";
	}

	// metodo utilizzato per autenticazione utente
	@GetMapping("/user")
	public String getUser(@AuthenticationPrincipal OidcUser principal, Model model) {
		User user = new User();
		user.setCognome(principal.getEmail());
		user.setNome(principal.getFullName());
		model.addAttribute("user", user);
		return "/user.html";
	}

	@RequestMapping(value = "/buffetFormAdmin", method = RequestMethod.GET)
	public String showChefForm(Model model) {
		model.addAttribute("buffet", new Buffet());
		return "buffetFormAdmin.html";
	}

///////////////////////////////////MODIFY////////////////////////////////////////
	@GetMapping("/modifyBuffetFirst/{id}")
	public String modifyBuffetFirst(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		List<Piatto> piatti = this.piattoService.findAll();
		List<Chef> chefs = this.chefService.findAll();
		model.addAttribute("chefs", chefs);
		model.addAttribute("piatti", piatti);
		model.addAttribute("buffet", buffet);
		return "buffetFormAdminModify.html";
	}

	@PostMapping("/modifyBuffetSecond/{id}")
	public String modifyBuffetSecond(@PathVariable("id") Long vecchioId, @Valid @ModelAttribute("buffet") Buffet buffet,
			BindingResult bindingResult, Model model) {

		Buffet vecchioBuffet = this.buffetService.findById(vecchioId);
		if (!vecchioBuffet.getNome().equals(buffet.getNome()))
			this.buffetValidator.validate(buffet, bindingResult);

		if (!bindingResult.hasErrors()) {// se i dati sono corretti

			vecchioBuffet.setId(buffet.getId());
			vecchioBuffet.setNome(buffet.getNome());
			vecchioBuffet.setDescrizione(buffet.getDescrizione());
			vecchioBuffet.setChef(buffet.getChef());
			vecchioBuffet.setPiatti(buffet.getPiatti());

			this.buffetService.save(vecchioBuffet);

			model.addAttribute("buffets", this.buffetService.findAll());
			return this.getAllBuffetForAdmin(model);
		} else {

			model.addAttribute("buffet", this.buffetService.findById(vecchioId));
			model.addAttribute("piatti", this.piattoService.findAll());
			model.addAttribute("chefs", this.chefService.findAll());
			return this.modifyBuffetFirst(vecchioId, model); // ci sono errori, torna alla form iniziale

		}

	}
}