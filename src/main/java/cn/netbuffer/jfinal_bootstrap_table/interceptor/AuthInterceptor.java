package cn.netbuffer.jfinal_bootstrap_table.interceptor;

import cn.netbuffer.jfinal_bootstrap_table.constant.ConfigConstant;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 认证拦截器
 *
 * @author netbuffer
 */
@Slf4j
public class AuthInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        log.info("Before [{}] method invoking", inv.getMethodName());
        Object login = inv.getController().getSession().getAttribute(ConfigConstant.ISLOGIN);
        log.info("invoking controller[{}]method[{}]view[{}]args[{}]\nlogin status:{}", inv.getControllerKey(), inv.getMethodName(), inv.getViewPath(),
                ToStringBuilder.reflectionToString(inv.getArgs()), login);
        if (StringUtils.equalsAny(inv.getMethodName(), "login", "captcha") || inv.getControllerKey().contains("register") || (null != login && Boolean.parseBoolean(login.toString()))) {
            //传递本次调用，调用剩下的拦截器与目标方法
            inv.invoke();
        } else {
            inv.getController().renderJavascript("<script type='text/javascript'>alert('您还未登录,不能执行此操作!');</script>");
        }
        log.info("After method invoking");
    }

}
