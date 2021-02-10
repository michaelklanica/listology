package com.michaelklanica.listology.repos;

import com.michaelklanica.listology.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByIsAdminTrue();
    List<User> findAllByIsAdminFalse();
    User getFirstByEmail(String email);
    User getById(Long id);
    User getUserByEmail(String email);
    User getFirstById(long id);
    User findFirstByEmail(String email);
    User findFirstById(long id);
    User getFirstByUsername(String username);
}
