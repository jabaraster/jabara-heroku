/**
 * 
 */
package info.jabara;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import info.jabara.heroku.HerokuHttpsOnlyResponse;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

/**
 * @author jabaraster
 */
@SuppressWarnings("static-method")
public class HerokuHttpsOnlyResponseTest {

    /**
     * @throws IOException -
     */
    @SuppressWarnings("nls")
    @Test
    public void スキームから始まるURLへのリダイレクトはそのままのURLが使用される() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = new MockResponse();

        // テスト開始
        final HerokuHttpsOnlyResponse sut = new HerokuHttpsOnlyResponse(request, response);
        final String absoluteUrl = "http://google.com";
        sut.sendRedirect(absoluteUrl);

        assertThat(sut.getHeader("location"), is(absoluteUrl));
    }

    /**
     * @throws IOException
     */
    @SuppressWarnings("nls")
    @Test
    public void スラッシュから始まらないパスへのリダイレクトはリクエストされたURLのディレクトリからの相対パス位置を示すスキーム付きURLへのリダイレクトになる_01() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final String contextPath = "/context";
        final String directory = "/directory/";
        when(request.getServerName()).thenReturn("example.com");
        when(request.getContextPath()).thenReturn(contextPath);
        when(request.getRequestURI()).thenReturn(contextPath + directory + "file");

        final HttpServletResponse response = new MockResponse();

        // テスト開始
        final HerokuHttpsOnlyResponse sut = new HerokuHttpsOnlyResponse(request, response);
        sut.sendRedirect("page");

        assertThat(sut.getHeader("location"), is("https://example.com" + contextPath + directory + "page"));
    }

    /**
     * @throws IOException
     */
    @SuppressWarnings("nls")
    @Test
    public void スラッシュから始まらないパスへのリダイレクトはリクエストされたURLのディレクトリからの相対パス位置を示すスキーム付きURLへのリダイレクトになる_02() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final String contextPath = "/context";
        final String directory = "/";
        when(request.getServerName()).thenReturn("example.com");
        when(request.getContextPath()).thenReturn(contextPath);
        when(request.getRequestURI()).thenReturn(contextPath + directory + "file");

        final HttpServletResponse response = new MockResponse();

        // テスト開始
        final HerokuHttpsOnlyResponse sut = new HerokuHttpsOnlyResponse(request, response);
        sut.sendRedirect("page");

        assertThat(sut.getHeader("location"), is("https://example.com" + contextPath + directory + "page"));
    }

    /**
     * @throws IOException
     */
    @SuppressWarnings("nls")
    @Test
    public void スラッシュから始まらないパスへのリダイレクトはリクエストされたURLのディレクトリからの相対パス位置を示すスキーム付きURLへのリダイレクトになる_03() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final String contextPath = "/context";
        final String directory = "/directory1/directory2/";
        when(request.getServerName()).thenReturn("example.com");
        when(request.getContextPath()).thenReturn(contextPath);
        when(request.getRequestURI()).thenReturn(contextPath + directory + "file");

        final HttpServletResponse response = new MockResponse();

        // テスト開始
        final HerokuHttpsOnlyResponse sut = new HerokuHttpsOnlyResponse(request, response);
        sut.sendRedirect("page");

        assertThat(sut.getHeader("location"), is("https://example.com" + contextPath + directory + "page"));
    }

    /**
     * @throws IOException
     */
    @SuppressWarnings("nls")
    @Test
    public void スラッシュから始まらないパスへのリダイレクトはリクエストされたURLのディレクトリからの相対パス位置を示すスキーム付きURLへのリダイレクトになる_04() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final String contextPath = "/";
        final String directory = "/directory1/directory2/";
        when(request.getServerName()).thenReturn("example.com");
        when(request.getContextPath()).thenReturn(contextPath);
        when(request.getRequestURI()).thenReturn(contextPath + directory + "file");

        final HttpServletResponse response = new MockResponse();

        // テスト開始
        final HerokuHttpsOnlyResponse sut = new HerokuHttpsOnlyResponse(request, response);
        sut.sendRedirect("page");

        assertThat(sut.getHeader("location"), is("https://example.com" + directory + "page"));
    }

    /**
     * @throws IOException -
     */
    @SuppressWarnings("nls")
    @Test
    public void スラッシュから始まる相対パスへのリダイレクトはコンテキストパスからの相対パス位置を示すスキーム付きURLへのリダイレクトになる_01() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final String host = "example.com";
        final String contextPath = "/context";
        when(request.getContextPath()).thenReturn(contextPath);
        when(request.getServerName()).thenReturn(host);
        when(request.getRequestURI()).thenReturn(contextPath + "/directory"); // リクエストされたURLが結果に影響を与えないことの検証.

        final HttpServletResponse response = new MockResponse();

        // テスト開始
        final HerokuHttpsOnlyResponse sut = new HerokuHttpsOnlyResponse(request, response);
        sut.sendRedirect("/page");

        assertThat(sut.getHeader("location"), is("https://" + host + contextPath + "/page"));
    }

    /**
     * @throws IOException -
     */
    @SuppressWarnings("nls")
    @Test
    public void スラッシュから始まる相対パスへのリダイレクトはコンテキストパスからの相対パス位置を示すスキーム付きURLへのリダイレクトになる_02() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final String contextPath = "/";
        final String host = "example.com";
        when(request.getContextPath()).thenReturn(contextPath);
        when(request.getServerName()).thenReturn(host);
        when(request.getRequestURI()).thenReturn(contextPath + "/directory"); // リクエストされたURLが結果に影響を与えないことの検証.

        final HttpServletResponse response = new MockResponse();

        // テスト開始
        final HerokuHttpsOnlyResponse sut = new HerokuHttpsOnlyResponse(request, response);
        sut.sendRedirect("/page");

        assertThat(sut.getHeader("location"), is("https://" + host + contextPath + "/page"));
    }

    /**
     * @throws IOException -
     */
    @SuppressWarnings("nls")
    @Test
    public void スラッシュから始まる相対パスへのリダイレクトはコンテキストパスからの相対パス位置を示すスキーム付きURLへのリダイレクトになる_03() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final String contextPath = "/";
        final String host = "example.com";
        when(request.getContextPath()).thenReturn(contextPath);
        when(request.getServerName()).thenReturn(host);
        when(request.getRequestURI()).thenReturn(contextPath); // リクエストされたURLが結果に影響を与えないことの検証.

        final HttpServletResponse response = new MockResponse();

        // テスト開始
        final HerokuHttpsOnlyResponse sut = new HerokuHttpsOnlyResponse(request, response);
        sut.sendRedirect("/page");

        assertThat(sut.getHeader("location"), is("https://" + host + contextPath + "/page"));
    }
}
