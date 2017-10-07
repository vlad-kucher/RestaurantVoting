package vlad.kucher.rv.util.exception;

public class ErrorInfo {
    public final String url;
    public final String t;

    public ErrorInfo(String url, Throwable t) {
        this.url = url;
        this.t = t.getLocalizedMessage();
    }
}
