package cn.voidnet.todolist.todo;

import cn.voidnet.todolist.user.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("todo")
@Api(description = "和待办事项有关的API 对待办事项进行增删查改")
@Slf4j
public class TodosController {
    @Resource
    UserService userService;
    @Resource
    TodoService todoService;

    @ApiOperation(value = "获取全部待办事项")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户的token,用于鉴定是哪个用户,每个用户只能访问自己的todo"
                    , name = "id", required = true, paramType = "header"
                    , example = "452c09b9-8477-4340-bdf0-451e9945cc3d")
    })
    @GetMapping("")
    @Transactional
    public List<Todo> getAllTodos(@RequestHeader("token") String token) {
        log.info("Request FindAll");
        var user = userService.authenticateAndGetUser(token);
        return todoService.getAllTodos(user);
    }

    @ApiOperation(value = "获取某一个待办事项")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "要获取的待办事项的ID", name = "id", required = true, paramType = "path", example = "1"),
            @ApiImplicitParam(value = "用户的token,用于鉴定是哪个用户,每个用户只能访问自己的todo"
                    , name = "id", required = true, paramType = "header"
                    , example = "452c09b9-8477-4340-bdf0-451e9945cc3d")
    })
    @GetMapping("{id}")
    public Todo getTodo(@PathVariable("id") Long id,
                        @RequestHeader String token
    ) throws TodoNotFoundException {
        log.info("Request Find Todo: " + id);
        var user = userService.authenticateAndGetUser(token);
        return todoService.getTodo(id, user);
    }

    @ApiOperation("删除某个待办事项")
    @DeleteMapping("{id}")
    public void removeById(@ApiParam("要删除的待办事项的序号") @PathVariable("id") Long id,
                           @RequestHeader String token) {
        log.info("Request Delete: {}", id.toString());
        var user = userService.authenticateAndGetUser(token);
        todoService.deleteTodo(id, user);
    }

    @PatchMapping("{id}")
    @ApiOperation("修改某个待办事项")
    public void modifyById(@ApiParam("要修改的待办事项的序号") @PathVariable("id") Long id,
                           @RequestBody Todo todo,
                           @RequestHeader String token) {
        todo.setId(id);
        log.info("Request Modify: {}", todo.getId());
        var user = userService.authenticateAndGetUser(token);
        todoService.modifyTodo(todo, user);
    }

    @PutMapping("")
    @ApiOperation(value = "新建一个新的待办事项", notes =
            "参数中的ID可以留空,待办事项实际的id应该以服务器上为准," +
                    "PUT请求将会返回该待办事项在服务器上实际存储的ID作为response," +
                    "本地中的id应该修改为PUT请求返回的id,ID是区分待办事项的唯一表示")
    @ApiResponses(
            @ApiResponse(response = Long.class, code = 200, message = "成功 ^_^ 待办事项在数据库中的序号将会被返回")
    )
    public Long newTodo(
            @ApiParam(name = "要上传的待办事项对象\n(id可留空或者胡写,实际id以服务器(本请求将会返回实际id作为response)上生成的为准)",
                    example = "{'text'='fix bugs'}")
            @RequestBody Todo todo,
            @RequestHeader String token
    ) throws IllegalArgumentException {
        var user = userService.authenticateAndGetUser(token);
        return todoService.addTodo(todo, user).getId();
    }



}
