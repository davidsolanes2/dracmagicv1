package com.app.dracmagicv1.service.db;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dracmagicv1.model.User;
import com.app.dracmagicv1.repository.UserRepository;
import com.app.dracmagicv1.service.UserService;

@Service
public class UserServiceJpa implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void guardar(User user) {
		userRepo.save(user);
	}
	
	@Override
	public void eliminar(int idUser) {
		userRepo.deleteById(idUser);
	}
	
	@Override
	public List<User> buscarTodos() {
		return userRepo.findAll();
	}
	
	@Override
	public User buscarPorId(int idUser) {
		Optional<User> optional = userRepo.findById(idUser);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Override
	public User buscarPorUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	@Override
	public User buscarPorFirstName(String firstName) {
		return userRepo.findByFirstName(firstName);
	}
	
	@Override
	public User buscarPorEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	@Override
	public List<User> buscarRegistrados() {
		return userRepo.findByFechaRegistroNotNull();
	}
	

	@Transactional
	@Override
	public int bloquear(int idUser) {
		int rows = userRepo.lock(idUser);
		return rows;
	}
	
	@Transactional
	@Override
	public int activar(int idUser) {
		int rows = userRepo.unlock(idUser);
		return rows;
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByEmail(String email) {
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException(email);
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("USUARIO"));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
	}
	
}
