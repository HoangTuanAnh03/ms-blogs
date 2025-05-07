package com.blog.authservice.repository;

import com.blog.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndActive(String email, Boolean active);

    Optional<User> findFirstByEmailAndActiveAndIsLocked(String email, Boolean active, Boolean lock);

    Optional<User> findByIdAndActive(String id, Boolean active);

    List<User> findByIdIn(List<String> ids);
}
