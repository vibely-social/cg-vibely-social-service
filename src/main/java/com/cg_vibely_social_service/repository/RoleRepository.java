<<<<<<<< HEAD:src/main/java/com/vibely_social/repository/RoleRepository.java
package com.vibely_social.repository;

import com.vibely_social.entity.Role;
========
package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.Role;
>>>>>>>> 1261f2bf1f1141a807a673cb59e87e47420e3b50:src/main/java/com/cg_vibely_social_service/repository/RoleRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
