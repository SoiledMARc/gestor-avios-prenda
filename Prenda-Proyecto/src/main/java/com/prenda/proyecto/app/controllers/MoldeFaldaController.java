package com.prenda.proyecto.app.controllers;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prenda.proyecto.app.models.entity.MoldeFalda;
import com.prenda.proyecto.app.models.service.IMoldeFaldaService;
import com.prenda.proyecto.app.util.paginator.PageRender;

@Controller
@SessionAttributes("moldeFalda")
public class MoldeFaldaController {

	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IMoldeFaldaService faldaService;
	
	@RequestMapping(value = {"molde/molde"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, 4);
		Page<MoldeFalda> moldes = null;
		
		moldes = faldaService.findAllEnable(pageRequest);
		PageRender<MoldeFalda> pageRender = new PageRender<MoldeFalda>("/molde/molde", moldes);
		model.addAttribute("titulo", "Listado de Telas");
		model.addAttribute("moldes", moldes);
		model.addAttribute("molde", new MoldeFalda());
		model.addAttribute("page", pageRender);

		return "/molde/molde";
		
	}
	
	@GetMapping("/molde/listar")
	public String findAll(@RequestParam String keyword, @RequestParam int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);
		Page<MoldeFalda> moldes = faldaService.findByKeyWord(keyword, pageRequest);
		PageRender<MoldeFalda> pageRender = new PageRender<MoldeFalda>("/molde/listar?keyword=" + keyword, moldes);
		model.addAttribute("titulo", "Listado de Faldas");
		model.addAttribute("moldes", moldes);
		model.addAttribute("molde", new MoldeFalda());
		model.addAttribute("page", pageRender);

		return "molde/listar";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "molde/form")
	public String crear(Map<String, Object> model) {

		MoldeFalda molde = new MoldeFalda();
		model.put("molde", molde);
		model.put("titulo", "Crear Molde");

		return "molde/form";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "molde/form/{id}")
	public String editar(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {
		MoldeFalda molde = null;

		if (id > 0) {
			molde = faldaService.findOne(id);
			if (molde == null) {
				flash.addFlashAttribute("error", "El ID de la Molde no existe en la BBDD!");
				return "redirect:/molde";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la Molde no puede ser cero!");
			return "redirect:/molde";
		}

		model.put("molde", molde);
		model.put("titulo", "Editar Molde");
		return "molde/form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "molde/form", method = RequestMethod.POST)
	public String guardar(@Valid MoldeFalda molde, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Molde");
			return "molde/form";
		}

		String mensajeFlash = (molde.getId() != null) ? "Molde editado con éxito!" : "Molde creado con éxito!";

		molde.setEnable("0");

		faldaService.save(molde);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:/molde/moldes";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "molde/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Integer id, RedirectAttributes flash) {

		if (id > 0) {
			MoldeFalda molde = faldaService.findOne(id);

			faldaService.delete(id);
			flash.addFlashAttribute("success", "Tela eliminado con éxito!");

		}
		return "redirect:/molde/moldes";
	}

	private boolean hasRole(String role) {

		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return false;
		}

		Authentication auth = context.getAuthentication();

		if (auth == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		return authorities.contains(new SimpleGrantedAuthority(role));
	}
	
}
