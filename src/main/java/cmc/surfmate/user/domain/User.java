package cmc.surfmate.user.domain;

import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.common.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * User.java
 *
 * @author jemlog
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "uid")
    private String uid;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder
    public User(String uid, String password, Role role, Provider provider) {
        this.uid = uid;
        this.password = password;
        this.role = role;
        this.provider = provider;
    }
}
