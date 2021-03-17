package com.app.dracmagicv1.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.app.dracmagicv1.model.User;

public interface UserService {
	
	public void guardar(User user);
	public void eliminar(int idUser);
	List<User> buscarTodos();
	List<User> buscarRegistrados();
	User buscarPorId(int idUser);
	User buscarPorUsername(String username);
	User buscarPorFirstName(String firstName);
	int bloquear(int idUser);
	int activar(int idUser);
	UserDetails loadUserByEmail(String email);
	User buscarPorEmail(String email);

}
