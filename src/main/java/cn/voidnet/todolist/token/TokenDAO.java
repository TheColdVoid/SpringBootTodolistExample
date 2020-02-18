package cn.voidnet.todolist.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenDAO extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByTokenContent(String tokenContent);

}
