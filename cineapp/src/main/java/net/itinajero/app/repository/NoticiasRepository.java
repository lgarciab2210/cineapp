package net.itinajero.app.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.itinajero.app.model.Noticia;

/**
 * Marcamos esta clase como un Bean de tipo Repository en nuestro Root
 * ApplicationContext. Nota: La anotacion @Repository es opcional ya que al
 * extender la interfaz JpaRepository Spring crea una instancia en nuestro Root
 * ApplicationContext.
 */
@Repository
public interface NoticiasRepository extends JpaRepository<Noticia, Integer> {

	// select * from Noticias
	List<Noticia> findBy();

	// select * from Noticias where estatus = ?
	List<Noticia> findByEstatus(String estatus);

	// where fecha = ?
	List<Noticia> findByFecha(Date fecha);

	// where estatus=? and fecha=?
	List<Noticia> findByEstatusAndFecha(String estatus, Date fecha);

	// where estatus=? or fecha=?
	List<Noticia> findByEstatusOrFecha(String estatus, Date fecha);

	// where fecha between ? and ?
	List<Noticia> findByFechaBetween(Date fecha1, Date fecha2);

	// where id between ? and ?
	List<Noticia> findByIdBetween(int n1, int n2);

	// select * from Noticias where estatus = ? order by id desc limit 3
	public List<Noticia> findTop3ByEstatusOrderByIdDesc(String estatus);
}
