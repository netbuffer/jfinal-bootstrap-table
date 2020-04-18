package cn.netbuffer.jfinal_bootstrap_table.controller;

import cn.netbuffer.jfinal_bootstrap_table.interceptor.TimeInterceptor;
import cn.netbuffer.jfinal_bootstrap_table.model.User;
import cn.netbuffer.jfinal_bootstrap_table.service.IUserService;
import cn.netbuffer.jfinal_bootstrap_table.service.impl.UserServiceImpl;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

/**
 * user控制器
 *
 * @author netbuffer
 */
@Slf4j
@Before({TimeInterceptor.class})
public class UserController extends Controller {

    private IUserService userservice = Aop.get(UserServiceImpl.class);

    public void index() {
        render("index.html");
    }

    /**
     * 开启缓存
     */
    @Before({CacheInterceptor.class})
    public void userlist() {
        int limit = getParaToInt("limit");
        int offset = getParaToInt("offset");
        Map<String, Object> data = userservice.getUserList(offset, limit);
        renderJson(data);
    }

    public void delete() {
        User.dao.deleteById(getPara("id"));
        setAttr("status", "success");
        renderJson();
    }

    public void update() {
        User user = getModel(User.class, "");
        log.info("update user:{}", user);
        renderText(String.valueOf(userservice.update(user)));
    }

}
