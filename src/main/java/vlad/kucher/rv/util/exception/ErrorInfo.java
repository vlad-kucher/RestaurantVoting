package vlad.kucher.rv.util.exception;

public class ErrorInfo {
    public final String url;
    public final String exception;

    public ErrorInfo(String url, Throwable t) {
        this.url = url;
        this.exception = t.getLocalizedMessage();
    }
}
