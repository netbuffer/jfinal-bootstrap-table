package cn.com.ttblog.jfinal_bootstrap_table.controller;

import java.util.Arrays;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * @author netbuffer
 */
public class CacheController extends Controller {
	private static Logger cacheLog = LoggerFactory
			.getLogger(CacheController.class);

	public void index() {
		String[] cnames=CacheKit.getCacheManager().getCacheNames();
		cacheLog.info(Arrays.toString(cnames));
		for(String key:cnames){
			cacheLog.info(ToStringBuilder.reflectionToString(CacheKit.getCacheManager().getCache(key).getStatus()));
		}
		renderJson(Arrays.toString(cnames));
	}
}
