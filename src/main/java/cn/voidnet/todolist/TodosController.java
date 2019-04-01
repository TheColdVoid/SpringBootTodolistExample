package cn.voidnet.todolist;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/todos")
@Api(description = "和待办事项有关的API 对待办事项进行增删查改")
public class TodosController {
    private final static Logger log = LoggerFactory.getLogger(TodosController.class);
    @Autowired
    TodoRepository todoRepository;

    @ApiOperation(value = "获取全部待办事项")
    @GetMapping("")
    public List<Todo> getAllTodos() {
        log.info("Request FindAll");
        return todoRepository.findAll();
    }

    @ApiOperation(value = "获取某一个待办事项")
    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Long id) throws TodoNotFoundException {
        log.info("Request Find Todo: " + id);
        return todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @ApiOperation("删除某个待办事项")
    @DeleteMapping("/{id}")
    public void removeById(@ApiParam("要删除的待办事项的序号") @PathVariable("id") Long id) throws TodoNotFoundException {
        log.info("Request Delete " + id.toString());
        try {
            todoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new TodoNotFoundException(id);
        }
    }

    @PostMapping("/{id}")
    @ApiOperation("修改某个待办事项")
    public void modifyById(@ApiParam("要修改的待办事项的序号") @PathVariable("id") Long id,
                           @ApiParam("待办事项修改为的内容(文本)") @RequestParam String text,@ApiParam("待办事项修改为的完成状态(标识是否完成)") @RequestParam Boolean done) throws TodoNotFoundException {

        log.info("Request Modify " + id.toString() + " To " + text+" | "+(done?"done":"not done"));
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        todo.setText(text);
        todo.setDone(done);
        todoRepository.save(todo);
        todoRepository.flush();
    }

    @PutMapping("")
    @ApiOperation(value = "新建一个新的待办事项",notes =
            "参数中的ID可以留空,待办事项实际的id应该以服务器上为准," +
            "PUT请求将会返回该待办事项在服务器上实际存储的ID作为response," +
            "本地中的id应该修改为PUT请求返回的id,ID是区分待办事项的唯一表示")
    @ApiResponses(
            @ApiResponse(response = Long.class,code=200,message = "成功 ^_^ 待办事项在数据库中的序号将会被返回")
    )
    public Long newTodo(
            @ApiParam(name = "要上传的待办事项对象\n(id可留空或者胡写,实际id以服务器(本请求将会返回实际id作为response)上生成的为准)",
            example="{'text'='fix bugs'}")
            @RequestBody Todo todo) throws IllegalArgumentException{
        if(todo.getText()==null)
            throw new IllegalArgumentException();
        return todoRepository.save(todo).getId();
    }


    @ExceptionHandler({TodoNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFoundException(HttpServletRequest req, TodoNotFoundException e) {
    }

}
