package cn.netbuffer.jfinal_bootstrap_table.service;

import cn.netbuffer.jfinal_bootstrap_table.model.User;

import java.util.Map;

public interface IUserService {
	public Map<String, Object> getUserList(int offset,int limit);
	public int getNewData();
	boolean update(User user);
}
