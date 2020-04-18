package cn.netbuffer.jfinal_bootstrap_table.controller;

import cn.netbuffer.jfinal_bootstrap_table.interceptor.UserValidator;
import cn.netbuffer.jfinal_bootstrap_table.model.User;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * register控制器
 *
 * @author netbuffer
 */
@Slf4j
public class RegisterController extends Controller {

    public void index() {
        redirect("/register.html");
    }

    @Before({UserValidator.class})
    public void save() {
        User u = getModel(User.class);
        u.setAdddate((int) (System.currentTimeMillis() / 1000));
        log.info("user:" + ToStringBuilder.reflectionToString(u));
        try {
            if (u.save()) {
                redirect("/register-success.html");
            } else {
                redirect("/register-error.html");
            }
        } catch (Exception e) {
            log.error("保存用户信息发生错误:" + e.getMessage() + ",原因:" + e.getCause().getMessage());
            redirect("/register-error.html");
        }
    }

    public void captcha() {
        renderCaptcha();
    }
}
