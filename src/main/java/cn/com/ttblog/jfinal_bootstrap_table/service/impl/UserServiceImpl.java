package cn.com.ttblog.jfinal_bootstrap_table.service.impl;

import cn.com.ttblog.jfinal_bootstrap_table.constant.ConfigConstant;
import cn.com.ttblog.jfinal_bootstrap_table.model.User;
import cn.com.ttblog.jfinal_bootstrap_table.service.IUserService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements IUserService {

	private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

//	private SimpleDateFormat format = new SimpleDateFormat(
//			"yyyy-MM-dd HH:mm:ssSSS");

	public Map<String, Object> getUserList(int offset, int limit) {
		int total = Db.queryLong("select count(id) from " + ConfigConstant.USERTABLE).intValue();
		if (total == 0) {
			return null;
		} else {
			List<Record> records = Db.find("select id,name,sex,age,phone,deliveryaddress,FROM_UNIXTIME(adddate,'%Y-%m-%d %H:%m:%s') adddate from "
					+ ConfigConstant.USERTABLE
					+ " order by adddate desc limit ?,?", offset, limit);
			Map<String, Object> datas = new HashMap<String, Object>(2);
			datas.put("rows", records);
			datas.put("total", total);
			return datas;
		}
	}

	public boolean update(User user) {
		Record record=Db.findById(ConfigConstant.USERTABLE,user.getId());
		if(StringUtils.isNotBlank(user.getName())){
			record.set("name",user.getName());
		}
		if(StringUtils.isNotBlank(user.getSex())){
			record.set("sex",user.getSex());
		}
		if(StringUtils.isNotBlank(user.getPhone())){
			record.set("phone",user.getPhone());
		}
		LOGGER.info("修改user record:{}",record);
		return Db.update(ConfigConstant.USERTABLE,record);
	}

	@Override
	public int getNewData() {
		return Db.queryLong("select count(id) from " + ConfigConstant.USERTABLE+" where DATE_FORMAT(NOW(),'%Y-%m-%d')=FROM_UNIXTIME(adddate,'%Y-%m-%d')").intValue();
	}
}
