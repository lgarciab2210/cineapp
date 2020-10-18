package pruebasrelaciones;

import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import net.itinajero.app.model.Horario;
import net.itinajero.app.repository.HorariosRepository;

public class AppRepoHorarios {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");
		HorariosRepository repo = context.getBean("horariosRepository", HorariosRepository.class);
		
		List<Horario> lista = repo.findAll();
		
		System.out.println("No. de entidades " + lista.size());
		
		for (Horario h : lista) {
			System.out.println(h);
		}
		
		context.close();

	}

}
