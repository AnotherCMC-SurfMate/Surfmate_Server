package cmc.surfmate.user.domain;

import cmc.surfmate.common.enums.Provider;
import cmc.surfmate.common.enums.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User.java
 *
 * @author jemlog
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;  // DB ID

    @Column(name = "uid")
    private String uid;  // 소셜 인증 아이디

    private String nickname;  // 닉네임

    private String password;  // 비밀번호

    private String fcmToken;  // 유저 토큰 값

    private String phNum;  // 핸드폰 번호

    private Boolean isOwner;  // 차 보유 여부

    // soft delete 여부
    private String isDelete;  // soft delete 여부

    @Enumerated(EnumType.STRING)
    private RoleType roleType;       // 권한 -> 이거 고도화 시켜보기

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String profileImage;  // 프로필 이미지

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void addFCMToken(String token)
    {
        this.fcmToken = token;
    }

    @Builder
    public User(String uid, String nickname, String phNum, String password, RoleType roleType, Provider provider,String fcmToken) {
        this.phNum = phNum;
        this.uid = uid;
        this.nickname = nickname;
        this.fcmToken = fcmToken;
        this.password = password;
        this.roleType = roleType;
        this.provider = provider;
    }

    @PrePersist
    private void init()
    {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isDelete = "N";

    }

    @PreUpdate
    private void update()
    {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateUserForSignup(String phNum, String nickname)
    {
        this.phNum = phNum;
        this.nickname = nickname;
    }
}
