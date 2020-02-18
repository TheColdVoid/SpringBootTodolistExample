package cn.voidnet.todolist.user;

import cn.voidnet.todolist.todo.Todo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class User {
    @OneToMany
    List<Todo> todos;
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;


}
