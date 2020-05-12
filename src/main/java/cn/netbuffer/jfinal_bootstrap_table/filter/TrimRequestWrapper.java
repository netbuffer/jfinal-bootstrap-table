package cn.netbuffer.jfinal_bootstrap_table.filter;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * TrimRequestWrapper 删除多余空格,装饰HttpServletRequest对象,装饰模式的使用
 *
 * @author netbuffer
 */
@Slf4j
public class TrimRequestWrapper extends HttpServletRequestWrapper {

    public TrimRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 重写getParameterValues方法来删除多余的空格
     */
    public String[] getParameterValues(String parameter) {
        String[] results = super.getParameterValues(parameter);
        if (results == null)
            return null;
        int count = results.length;
        String[] trimResults = new String[count];
        for (int i = 0; i < count; i++) {
            trimResults[i] = results[i].trim();
        }
        log.debug("过滤空格:{}", Arrays.toString(trimResults));
        return trimResults;
    }

    /**
     * 重写getParameter方法去空白
     */
    public String getParameter(String name) {
        return null != super.getParameter(name) ? super.getParameter(name).trim() : "";
    }
}
