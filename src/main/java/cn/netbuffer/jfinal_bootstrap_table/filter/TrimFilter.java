package cn.netbuffer.jfinal_bootstrap_table.filter;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 过滤空白符号filter
 *
 * @author netbuffer
 */
@Slf4j
public class TrimFilter implements Filter {

    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        boolean enable = Boolean.parseBoolean(filterConfig.getInitParameter("enable"));
        String requri = ((HttpServletRequest) request).getRequestURI();
        if (enable) {
            log.debug("{}清除空白", requri);
            chain.doFilter(
                    new TrimRequestWrapper((HttpServletRequest) request),
                    response);
        } else {
            log.debug("{}直接放行", requri);
            chain.doFilter(request, response);
        }
    }
}
