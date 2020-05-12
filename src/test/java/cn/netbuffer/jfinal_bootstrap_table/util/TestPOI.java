package cn.netbuffer.jfinal_bootstrap_table.util;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestPOI {

    @Test
    public void testExportFile() {
        String dir = System.getProperty("user.dir");
        List<String> titles = new ArrayList<>();
        titles.add("昵称");
        titles.add("姓名");
        titles.add("性别");
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 4; i++) {
            //保障顺序
            Map<String, Object> mp = new LinkedHashMap<String, Object>();
            mp.put("n", "tt" + i);
            mp.put("x", "行ing" + i);
            mp.put("b", "男" + i);
            datas.add(mp);
        }
        POIExcelUtil.export(titles, datas, dir + File.separator + "test.xls");
    }

}
