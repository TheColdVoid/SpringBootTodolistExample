package cn.voidnet.todolist;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {
    @ApiModelProperty(value = "待办事项的ID,用于区分待办事项的唯一标识",example = "233")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(value = "待办事项的内容(文本)",example = "fix bugs")
    private String text;

    @ApiModelProperty(value = "标识待办事项是否已经做完",example = "true")
    private Boolean done;

    protected Todo() {
    }

    public Todo(String text) {
        this.text = text;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
