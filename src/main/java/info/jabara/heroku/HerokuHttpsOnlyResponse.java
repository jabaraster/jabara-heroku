/**
 * 
 */
package info.jabara.heroku;

import jabara.general.Empty;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Heroku上で動作している状況で、HTTPSのみに通信を限定したい場合に生じるリダイレクトに関する問題を解消するための{@link HttpServletResponseWrapper}拡張です. <br>
 * クライアントから見たらHTTPS通信であっても、Heroku上のServletコンテナ上ではHTTP通信になるため、クライアントへのレスポンスヘッダのLocationの値がHTTPとなってしまいます. <br>
 * この問題に対処するため、このクラスは{@link HttpServletResponseWrapper#sendRedirect(String)}の実装を変更します. <br>
 * 引数に渡されたパスが相対パスの場合は、強制的にHTTPSから始まる絶対URLに書き換え、Locationヘッダに設定します. <br>
 * また、デフォルトの{@link HttpServletResponseWrapper#sendRedirect(String)}は<code>302 Found</code>ですが、当クラスでは<code>301 Moved Permanently</code>に変更します. <br>
 * ただし、当クラスの{@link #sendRedirect(String)}にスキーム(xxx://)から始まるURLが渡された場合、デフォルトの{@link HttpServletResponseWrapper#sendRedirect(String)}が動作します. <br>
 * 
 * @author jabaraster
 */
public class HerokuHttpsOnlyResponse extends HttpServletResponseWrapper {

    private final HttpServletRequest request;

    /**
     * @param pRequest -
     * @param pResponse -
     */
    public HerokuHttpsOnlyResponse(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
        super(pResponse);
        this.request = pRequest;
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#sendRedirect(java.lang.String)
     */
    @Override
    public void sendRedirect(final String pLocation) throws IOException {
        if (pLocation == null || pLocation.length() == 0) {
            super.sendRedirect(pLocation);
            return;
        }

        try {
            // リダイレクト先URLがスキーム(xxx://のxxx)を含むかどうかの判定にURIクラスを使う.
            // スキームを含む場合は加工せずにそのままリダイレクトする.
            final URI uri = new URI(pLocation);
            if (uri.getScheme() != null) {
                sendRedirectCore(pLocation);
                return;
            }

            final String path;
            if (pLocation.charAt(0) == '/') {
                // pLocationをコンテキストパスからの相対パス指定とみなす
                path = getContextPathSafe() + pLocation;
            } else {
                // pLocationをリクエストされたURLのディレクトリからの相対パス指定とみなす
                path = getDirectoryPathForRequest() + pLocation;
            }
            final String queryString = getQueryString();
            sendRedirectCore("https://" + this.request.getServerName() + path + queryString); //$NON-NLS-1$ 

        } catch (final URISyntaxException e) {
            super.sendRedirect(pLocation);
        }
    }

    private String getContextPathSafe() {
        final String contextPath = this.request.getContextPath();
        if (contextPath == null || contextPath.length() == 0) {
            return Empty.STRING;
        }
        return contextPath;
    }

    private String getDirectoryPathForRequest() {
        final String requestUri = this.request.getRequestURI();
        if (requestUri.endsWith("/")) { //$NON-NLS-1$
            return requestUri;
        }
        // 親ディレクトリを得るのにFileクラスを利用する.
        return new File(requestUri).getParent() + "/"; //$NON-NLS-1$
    }

    private String getQueryString() {
        final String queryString = this.request.getQueryString();
        return queryString == null || queryString.length() == 0 ? Empty.STRING : "?" + queryString; //$NON-NLS-1$
    }

    private void sendRedirectCore(final String pLocation) {
        this.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        setHeader("location", pLocation); //$NON-NLS-1$
    }
}
