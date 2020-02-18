package cn.voidnet.todolist.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Token wrong")
public class TokenNotExistException extends RuntimeException {
}
