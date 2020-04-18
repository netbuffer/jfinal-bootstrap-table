package cn.netbuffer.jfinal_bootstrap_table.controller;

import cn.netbuffer.jfinal_bootstrap_table.constant.ConfigConstant;
import cn.netbuffer.jfinal_bootstrap_table.interceptor.LoginValidator;
import cn.netbuffer.jfinal_bootstrap_table.interceptor.TimeInterceptor;
import cn.netbuffer.jfinal_bootstrap_table.model.User;
import cn.netbuffer.jfinal_bootstrap_table.service.IUserService;
import cn.netbuffer.jfinal_bootstrap_table.service.impl.UserServiceImpl;
import cn.netbuffer.jfinal_bootstrap_table.util.BeanMapUtil;
import cn.netbuffer.jfinal_bootstrap_table.util.POIExcelUtil;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * index控制器
 *
 * @author netbuffer
 */
@Slf4j
@Before({TimeInterceptor.class})
public class IndexController extends Controller {

    private IUserService userService = Aop.get(UserServiceImpl.class);

    /*
     * jfinal默认访问不到的路径都会访问这个index方法来处理，如果想让找不到的路径引发404，可以加NoUrlPara这个拦截器
     */
    // @Before(NoUrlPara.class)
    public void index() {
        render("index.html");
    }

    @Before({LoginValidator.class})
    public void login() {
        Prop p = PropKit.use("config.properties");
        String cu = p.get("jdbc.musername");
        String cp = p.get("jdbc.mpassword");
        log.debug("配置:" + ToStringBuilder.reflectionToString(p));
        String username = getPara("username");
        String password = getPara("password");
        if (cu.equals(username) && cp.equals(password)) {
            getSession().setAttribute(ConfigConstant.ISLOGIN, true);
            getSession().setAttribute(ConfigConstant.USERNAME, cu);
            log.info("登陆信息:" + ToStringBuilder.reflectionToString(this));
            setCookie(ConfigConstant.USERNAME, username, 86400);//1天免登陆
            redirect("/manage.html");
        } else {
            log.info("登陆失败!");
            redirect("/index.html");
        }
    }

    public void exit() {
        getSession().removeAttribute(ConfigConstant.ISLOGIN);
        getSession().removeAttribute(ConfigConstant.USERNAME);
        redirect("/index.html");
    }

    /**
     * 开启缓存
     */
    @Before({CacheInterceptor.class})
    public void newdata() {
        log.info("newdata执行!");
        int newcount = userService.getNewData();
        String username = getSession().getAttribute(ConfigConstant.USERNAME).toString();
        setAttr("newcount", newcount);
        setAttr("username", username);
        renderJson();
    }

    public void export() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        List<User> users = User.dao.find("select * from " + ConfigConstant.USERTABLE + " limit 50");
        String projectPath = getRequest().getServletContext().getRealPath("export");
        int userCount = users.size();
        List<Map<String, Object>> mps = new ArrayList<Map<String, Object>>(users.size());
        for (int i = 0; i < userCount; i++) {
            Map<String, Object> m = BeanMapUtil.transBean2Map(users.get(i));
            mps.add(m);
        }
        log.info("mps:{}", mps.toString());
        List<String> titles = new ArrayList<String>(mps.get(0).size() - 1);
        titles.add("adddate");
        titles.add("age");
        titles.add("deliveryaddress");
        titles.add("id");
        titles.add("name");
        titles.add("phone");
        titles.add("sex");
        String file = projectPath + format.format(new Date()) + "." + ConfigConstant.EXCELSTR;
        POIExcelUtil.export(titles, mps, file);
        renderFile(new File(file));
    }

    public void testWrapper() {
        renderJson(getRequest().getParameterMap());
    }

    public void sleep() throws InterruptedException {
        int sleep = RandomUtils.nextInt(0, 5);
        TimeUnit.SECONDS.sleep(sleep);
        renderJson(sleep);
    }
}
