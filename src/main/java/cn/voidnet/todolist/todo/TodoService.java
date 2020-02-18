package cn.voidnet.todolist.todo;

import cn.voidnet.todolist.user.User;
import cn.voidnet.todolist.user.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class TodoService {

    @Resource
    private TodoDAO todoDAO;

    @Resource
    private UserDAO userDAO;

    public List<Todo> getAllTodos(User user) {
        return user.getTodos();
    }

    public Todo getTodo(Long id, User user) {
        return user
                .getTodos()
                .stream()
                .filter(it -> it.getId() == id)
                .findFirst()
                .orElseThrow(() -> new TodoNotFoundException(id));
//        以上为基于java8 stream的写法,以下为传统写法:
//        var todos = user.getTodos();
//        Todo foundTodo = null;
//        for(var todo : todos)
//        {
//            if(todo.getId()==id)
//            {
//                foundTodo  = todo;
//                break;
//            }
//        }
//        if(foundTodo!=null)
//            return foundTodo;
//        else throw  new TodoNotFoundException(id);
    }

    public boolean deleteTodo(Long id, User user) {
        return user
                .getTodos()
                .stream()
                .filter(it -> it.getId() == id)
                .findFirst()
                .map(todo -> {
                    user.getTodos().remove(todo);
                    userDAO.save(user);
                    todoDAO.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    public Todo addTodo(Todo todo, User user) {
        todo.setUser(user);
        var saveTodo = todoDAO.save(todo);
        user.getTodos().add(saveTodo);
        userDAO.save(user);
        return saveTodo;
    }


    public Todo modifyTodo(Todo newTodo, User user) {
        user    //判断todo是否归属于该用户
                .getTodos()
                .stream()
                .filter(it -> it.getId() == newTodo.getId())
                .findFirst()
                .orElseThrow(() -> new TodoNotFoundException(newTodo.getId()));
        todoDAO.save(newTodo);
        return newTodo;
    }


}
