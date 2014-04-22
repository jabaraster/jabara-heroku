/**
 * 
 */
package info.jabara;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jabaraster
 */
@SuppressWarnings({ "nls", "javadoc", "unused" })
public class MockResponse implements HttpServletResponse {

    private final Map<String, String> header = new HashMap<String, String>();

    private int                       status = Integer.MIN_VALUE;
    private String                    redirectLocation;

    @Override
    public void addCookie(final Cookie pCookie) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public void addDateHeader(final String pName, final long pDate) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public void addHeader(final String pName, final String pValue) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public void addIntHeader(final String pName, final int pValue) {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public boolean containsHeader(final String pName) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public String encodeRedirectUrl(final String pUrl) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public String encodeRedirectURL(final String pUrl) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public String encodeUrl(final String pUrl) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public String encodeURL(final String pUrl) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public void flushBuffer() throws IOException {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public int getBufferSize() {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public String getCharacterEncoding() {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public String getContentType() {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public String getHeader(final String pName) {
        return this.header.get(pName);
    }

    @Override
    public Collection<String> getHeaderNames() {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public Collection<String> getHeaders(final String pName) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public Locale getLocale() {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public boolean isCommitted() {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public void reset() {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public void resetBuffer() {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public void sendError(final int pSc) throws IOException {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public void sendError(final int pSc, final String pMsg) throws IOException {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public void sendRedirect(final String pLocation) throws IOException {
        this.redirectLocation = pLocation;
    }

    @Override
    public void setBufferSize(final int pSize) {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public void setCharacterEncoding(final String pCharset) {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public void setContentLength(final int pLen) {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public void setContentType(final String pType) {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public void setDateHeader(final String pName, final long pDate) {
        throw new IllegalStateException("このメソッドは未実装です.");

    }

    @Override
    public void setHeader(final String pName, final String pValue) {
        this.header.put(pName, pValue);
    }

    @Override
    public void setIntHeader(final String pName, final int pValue) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public void setLocale(final Locale pLoc) {
        throw new IllegalStateException("このメソッドは未実装です.");
    }

    @Override
    public void setStatus(final int pSc) {
        this.status = pSc;
    }

    @Override
    public void setStatus(final int pSc, final String pSm) {
        this.status = pSc;
    }
}
