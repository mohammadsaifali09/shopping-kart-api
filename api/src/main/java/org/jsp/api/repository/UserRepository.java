package org.jsp.api.repository;

import org.jsp.api.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("select u from User u where u.token=?1")
	User findUserByToken(String token);

	@Query("select u from User u where u.email=?1")
	User findUserByEmail(String email);
}
