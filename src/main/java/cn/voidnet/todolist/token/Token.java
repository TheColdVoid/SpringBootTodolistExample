package cn.voidnet.todolist.token;

import cn.voidnet.todolist.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Token {
     @Id
     @GeneratedValue
     Long id;

     @OneToOne
     User user;

     String tokenContent;

     public Token(User user) {
          this.user = user;
          this.tokenContent = UUID.randomUUID().toString();
     }
}

