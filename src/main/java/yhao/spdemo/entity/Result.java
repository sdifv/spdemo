package yhao.spdemo.entity;

import lombok.Data;

@Data
public class Result {

    private Integer code;

    private String message;

    private Object data;


    public static Result OK() {
        return new Result(200, "success");
    }

    public static Result OK(Object data) {
        return new Result(200, "success", data);
    }

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
