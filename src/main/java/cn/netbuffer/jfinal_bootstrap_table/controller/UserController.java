package cn.netbuffer.jfinal_bootstrap_table.controller;

import cn.netbuffer.jfinal_bootstrap_table.interceptor.TimeInterceptor;
import cn.netbuffer.jfinal_bootstrap_table.model.User;
import cn.netbuffer.jfinal_bootstrap_table.service.IUserService;
import cn.netbuffer.jfinal_bootstrap_table.service.impl.UserServiceImpl;
import cn.netbuffer.jfinal_bootstrap_table.util.POIExcelUtil;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.List;
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
//    @Before({CacheInterceptor.class})
    public void userlist() {
        int limit = getParaToInt("limit");
        int offset = getParaToInt("offset");
        Map<String, Object> data = userservice.getUserList(offset, limit);
        if (data == null) {
            Map<String, Object> datas = new HashMap<String, Object>(2);
            datas.put("rows", null);
            datas.put("total", 0);
            data = datas;
        }
        renderJson(data);
    }

    public void delete() {
        String id = getPara("id");
        boolean del = User.dao.deleteById(id);
        log.debug("删除用户:{},结果:{}", id, del);
        if (del) {
            setAttr("status", "success");
        } else {
            setAttr("status", "false");
        }
        renderJson();
    }

    public void update() {
        User user = getModel(User.class, "");
        log.info("update user:{}", user);
        renderText(String.valueOf(userservice.update(user)));
    }

    public void upload() {
        UploadFile file = getFile("excel");
        log.debug("接收文件上传:{}", file.getFileName());
        Map data = new HashMap();
        data.put("name", file.getFileName());
        List<List<String>> excelData = POIExcelUtil.read(file.getFile().getAbsolutePath());
        excelData.forEach(e -> {
            User user = new User();
            user.setName(e.get(0));
            user.setAge((int) Double.parseDouble(e.get(1)));
            user.setSex(e.get(2));
            user.setAdddate((int) System.currentTimeMillis() / 1000);
            log.debug("save user[{}]:{}", user, user.save());
        });
        data.put("count", excelData.size());
        renderJson(data);
    }

}
