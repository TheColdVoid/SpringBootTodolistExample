package cn.voidnet.todolist.user;

import cn.voidnet.todolist.token.Token;
import cn.voidnet.todolist.token.TokenDAO;
import cn.voidnet.todolist.token.TokenNotExistException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDAO userDAO;

    @Resource
    private TokenDAO tokenDAO;

    public String login(User loginUser) {
        return userDAO
                .findUserByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword())
                .map(Token::new)
                .map(tokenDAO::saveAndFlush)
                .map(Token::getTokenContent)
                .orElseThrow(PasswordOrUsernameWrongException::new);
//         以上是Java8的写法 等价于:
//        var user = userDAO.findUserByUsernameAndPassword(loginUser.getUsername(),loginUser.getPassword());
//        if(user.isPresent())
//        {
//            var token = new Token(user.get());
//            tokenDAO.saveAndFlush(token);
//            return token.getTokenContent();
//        }
//        else throw new PasswordOrUsernameWrongException();
    }

    public void register(User newUser) {
        userDAO.saveAndFlush(newUser);
    }

    //鉴权 并获得对应的用户实体
    public User authenticateAndGetUser(String tokenContent) {
        return tokenDAO
                .findTokenByTokenContent(tokenContent)
                .map(Token::getUser)
                .orElseThrow(TokenNotExistException::new);
//         以上是Java8的写法 等价于:
//        var token = tokenDAO.findTokenByTokenContent(tokenContent);
//        if (token.isPresent()) {
//            return token.get().getUser();
//        }
//        else throw new TokenNotExistException();


    }

}
