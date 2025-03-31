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

    boolean existsByCompanyIdAndRole(long companyId, Role role);

    //    Optional<User> findById(int email);
    //
    boolean existsByEmail(String email);

    boolean existsByEmailAndActive(String email, Boolean active);

    Optional<User> findFirstByEmailAndActive(String email, Boolean active);

    List<User> findByIdIn(List<String> ids);


//    List<User> findByCompany(Company company);
}
