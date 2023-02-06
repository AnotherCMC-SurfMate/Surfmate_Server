package cmc.surfmate.user.repository;

import cmc.surfmate.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository.java
 *
 * @author jemlog
 */
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByPhNum(String phNum);

    Optional<User> findUserByNickname(String nickname);
}
