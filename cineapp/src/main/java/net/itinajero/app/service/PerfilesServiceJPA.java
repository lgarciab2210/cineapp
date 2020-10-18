package net.itinajero.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.itinajero.app.model.Perfil;
import net.itinajero.app.repository.PerfilesRepository;

@Service
public class PerfilesServiceJPA implements IPerfilesService {

	@Autowired
	private PerfilesRepository perfilesRepo;
	
	@Override
	public void guardar(Perfil perfil) {
		perfilesRepo.save(perfil);
	}

}
