package com.app.dracmagicv1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.dracmagicv1.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByFirstName(String firstName);
	User findByUsername(String username);
	User findByEmail(String email);

	List<User> findByFechaRegistroNotNull();
	
	@Modifying
	@Query("UPDATE User u SET u.estatus=0 WHERE u.id = :paramIdUser")
	int lock(@Param("paramIdUser") int idUser);
	
	@Modifying
	@Query("UPDATE User u SET u.estatus=1 WHERE u.id = :paramIdUser")
	int unlock(@Param("paramIdUser") int idUser);
}
