package cn.netbuffer.jfinal_bootstrap_table.util;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import lombok.extern.slf4j.Slf4j;

/**
 * GeneratorDemo
 */
@Slf4j
public class GeneratorDemo {

    public static DataSource getDataSource() {
        Prop p = PropKit.use("config.properties");
        DruidPlugin druidds = new DruidPlugin(p.get("jdbc.jdbcUrl"), p.get("jdbc.user"), p.get("jdbc.password"));
        druidds.setDriverClass(p.get("jdbc.driver"));
        if (druidds.start()) {
            return druidds.getDataSource();
        }
        return null;
    }

    public static void main(String[] args) {
        // base model 所使用的包名
        String baseModelPackageName = "cn.netbuffer.jfinal_bootstrap_table.model.base";
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath()
                + "/src/main/java/cn/netbuffer/jfinal_bootstrap_table/model/base";
        log.debug("路径:" + baseModelOutputDir);
        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "cn.netbuffer.jfinal_bootstrap_table.model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = PathKit.getWebRootPath()
                + "/src/main/java/cn/netbuffer/jfinal_bootstrap_table/model";

        // 创建生成器
        Generator gernerator = new Generator(getDataSource(),
                baseModelPackageName, baseModelOutputDir, modelPackageName,
                modelOutputDir);
        // 设置数据库方言
        gernerator.setDialect(new MysqlDialect());
        // 设置是否在 Model 中生成 dao 对象
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        gernerator.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为
        // "User"而非 OscUser
//		gernerator.setRemovedTableNamePrefixes("t_");
        // 生成
        gernerator.generate();
    }
}
