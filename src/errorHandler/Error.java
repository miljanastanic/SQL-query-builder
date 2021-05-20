package errorHandler;

public class Error {

    int type;
    String message;
    String title;

    public Error(int type, String message, String title) {
        this.type = type;
        this.message = message;
        this.title = title;

    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
