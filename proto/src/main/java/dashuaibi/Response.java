package dashuaibi;

import lombok.Data;

@Data
public class Response {
    private int code=0;
    private String message="success";
    private Object data;
}
