<<<<<<<< HEAD:src/main/java/com/vibely_social/repository/UserRepository.java
package com.vibely_social.repository;

import com.vibely_social.entity.User;
========
package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.User;
>>>>>>>> 1261f2bf1f1141a807a673cb59e87e47420e3b50:src/main/java/com/cg_vibely_social_service/repository/UserRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
