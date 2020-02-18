package cn.voidnet.todolist.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoDAO extends JpaRepository<Todo, Long> {
    List<Todo> findAll();
}
