package cn.netbuffer.jfinal_bootstrap_table.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 计时器。用于记录请求执行时间。
 */
@Slf4j
public class TimerFilter implements Filter {

    private FilterConfig filterConfig;

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();
        boolean enable = Boolean.parseBoolean(filterConfig.getInitParameter("enable"));
        if (enable) {
            stopWatch.start();
            chain.doFilter(request, response);
            stopWatch.stop();
            String uri = ((HttpServletRequest) request).getRequestURI();
            log.info("URI={},execute costs in {} second(s). ", uri, stopWatch.getTime(TimeUnit.SECONDS));
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

}
