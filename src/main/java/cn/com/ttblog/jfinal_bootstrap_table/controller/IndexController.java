package cn.com.ttblog.jfinal_bootstrap_table.controller;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.ttblog.jfinal_bootstrap_table.Constant.ConfigConstant;
import cn.com.ttblog.jfinal_bootstrap_table.interceptor.LoginValidator;
import cn.com.ttblog.jfinal_bootstrap_table.interceptor.TimeInterceptor;
import cn.com.ttblog.jfinal_bootstrap_table.service.IUserService;
import cn.com.ttblog.jfinal_bootstrap_table.service.impl.UserServiceImpl;

import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

/**
 * index控制器
 * @author netbuffer
 *
 */
@Before({ TimeInterceptor.class })
public class IndexController extends Controller {
	private static Logger logIndex = LoggerFactory
			.getLogger(IndexController.class);
	private static IUserService userservice=Enhancer.enhance(UserServiceImpl.class);

	/*
	 * jfinal默认访问不到的路径都会访问这个index方法来处理，如果想让找不到的路径引发404，可以加NoUrlPara这个拦截器
	 */
	// @Before(NoUrlPara.class)
	public void index() {
		render("index.html");
	}
	
	@Before({LoginValidator.class})
	public void login() {
		Prop p = PropKit.use("config.txt");
		String cu=p.get("musername");
		String cp=p.get("mpassword");
		logIndex.debug("配置:"+ToStringBuilder.reflectionToString(p));
		String username=getPara("username");
		String password=getPara("password");
		if(cu.equals(username)&&cp.equals(password)){
			getSession().setAttribute(ConfigConstant.ISLOGIN, true);
			getSession().setAttribute(ConfigConstant.USERNAME, cu);
			logIndex.info("登陆信息:"+ToStringBuilder.reflectionToString(this));
			redirect("/manage.html");
		}else{
			logIndex.info("登陆失败!");
			redirect("/index.html");
		}
	}
	
	public void exit() {
		getSession().removeAttribute(ConfigConstant.ISLOGIN);
		getSession().removeAttribute(ConfigConstant.USERNAME);
		redirect("/index.html");
	}
	
	public void newdata(){
		int newcount=userservice.getNewData();
		String username=getSession().getAttribute(ConfigConstant.USERNAME).toString();
		setAttr("newcount", newcount);
		setAttr("username", username);
		renderJson();
	}
}
