package cn.voidnet.todolist;

public class TodoNotFoundException extends Exception{
    public TodoNotFoundException(Long id) {
        super("Todo: "+id+" Not Found");
    }
}
