package net.itinajero.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import net.itinajero.app.model.*;
import net.itinajero.app.service.*;
import net.itinajero.app.util.Utileria;

@Controller
public class HomeController {
	
	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private IBannersService serviceBannners;
	
	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private IPeliculasService servicePeliculas;
	
	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private IHorariosService serviceHorarios;	
	
	// Inyectamos una instancia desde nuestro Root ApplicationContext
	@Autowired
	private INoticiasService serviceNoticias;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	 * Metodo para mostrar la pagina principal de la aplicacion
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String mostrarPrincipal(Model model) {	

		try {
			Date fechaSinHora = dateFormat.parse(dateFormat.format(new Date()));
			List<String> listaFechas = Utileria.getNextDays(4);		
			List<Pelicula> peliculas = servicePeliculas.buscarPorFecha(fechaSinHora);			
			model.addAttribute("fechas", listaFechas);
			model.addAttribute("fechaBusqueda", dateFormat.format(new Date()));
			model.addAttribute("peliculas", peliculas);
		} catch (ParseException e) {
			System.out.println("Error: HomeController.mostrarPrincipal" + e.getMessage());
		}
		return "home";

	}
	/**
	 * Metodo para filtrar las peliculas por fecha
	 * @param fecha
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method=RequestMethod.POST)
	public String buscar(@RequestParam("fecha") Date fecha, Model model) {		
		try {			
			Date fechaSinHora = dateFormat.parse(dateFormat.format(fecha));
			List<String> listaFechas = Utileria.getNextDays(4);
			List<Pelicula> peliculas  = servicePeliculas.buscarPorFecha(fechaSinHora);
			model.addAttribute("fechas", listaFechas);			
			// Regresamos la fecha que selecciono el usuario con el mismo formato
			model.addAttribute("fechaBusqueda",dateFormat.format(fecha));			
			model.addAttribute("peliculas", peliculas);			
			return "home";
		} catch (ParseException e) {
			System.out.println("Error: HomeController.buscar" + e.getMessage());
		}
		return "home";
	}
	
	/**
	 * Metodo para ver los detalles y horarios de una pelicula
	 * @param idPelicula
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}/{fecha}")
	public String mostrarDetalle(@PathVariable("id") int idPelicula, @PathVariable("fecha") Date fecha, Model model) {
		// TODO - Buscar en la base de datos los horarios.		
		List<Horario> horarios= serviceHorarios.buscarPorIdPelicula(idPelicula, fecha);
		model.addAttribute("horarios", horarios);
		model.addAttribute("fechaBusqueda", dateFormat.format(fecha));
		model.addAttribute("pelicula", servicePeliculas.buscarPorId(idPelicula));		
		return "detalle";
	}

	/**
	 * Metodo que muestra la vista de la pagina de Acerca
	 * @return
	 */
	@RequestMapping(value = "/about")
	public String mostrarAcerca() {			
		return "acerca";
	}
	
	@RequestMapping(value="/formLogin", method=RequestMethod.GET)
	public String mostrarLogin() {
		return "formLogin";
	}
	
	@ModelAttribute("noticias")
	public List<Noticia> getNoticias(){
		return serviceNoticias.buscarUltimas();
	}
	
	@ModelAttribute("banners")
	public List<Banner> getBanners(){
		return serviceBannners.buscarActivos();
	}
	
	/**
	 * Metodo para personalizar el Data Binding para los atributos de tipo Date
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {				
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
