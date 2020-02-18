package cn.voidnet.todolist.user;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }

    @PutMapping("register")
    public boolean register(@RequestBody User user) {
        userService.register(user);
        return true;
    }


}
