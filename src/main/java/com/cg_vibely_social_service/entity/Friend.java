<<<<<<<< HEAD:src/main/java/com/vibely_social/entity/Friend.java
package com.vibely_social.entity;
========
package com.cg_vibely_social_service.entity;
>>>>>>>> 1261f2bf1f1141a807a673cb59e87e47420e3b50:src/main/java/com/cg_vibely_social_service/entity/Friend.java

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
