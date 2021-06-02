package com.prenda.proyecto.app.models.dao;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.prenda.proyecto.app.models.entity.Usuario;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);

}
