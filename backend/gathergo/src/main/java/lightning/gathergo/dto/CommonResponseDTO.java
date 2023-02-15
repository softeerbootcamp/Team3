package lightning.gathergo.dto;

public class CommonResponseDTO <T>{
    private final int status; //1이면 성공, 0이면 실패
    private String message;
    private T data;

    public CommonResponseDTO(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
