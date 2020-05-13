package cn.netbuffer.jfinal_bootstrap_table.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TestPOI {

    String path = System.getProperty("user.dir") + File.separator + "test.xlsx";

    @Test
    public void testReadFile() {
        log.info("read {}:\n{}", path, POIExcelUtil.read(path));
    }

    @Test
    public void testExportFile() {
        List<String> titles = new ArrayList<>();
        titles.add("昵称");
        titles.add("姓名");
        titles.add("性别");
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 4; i++) {
            //保障顺序
            Map<String, Object> mp = new LinkedHashMap<String, Object>();
            mp.put("nick", "nick" + i);
            mp.put("name", "name" + i);
            mp.put("sex", "sex" + i);
            datas.add(mp);
        }
        POIExcelUtil.write(titles, datas, path);
    }

}
