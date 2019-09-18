package autoTests;

import ru.qatools.properties.Property;
import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Resource;

import java.io.File;

@Resource.Classpath("test.properties")
public final class Config {
    private static volatile Config instance;

    private Config() {
        PropertyLoader.newInstance().populate(this);
    }

    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    //region Параметры
    @Property("baseUrl")
    private static String baseUrl;

    @Property("testTextFile")
    private static String testTextFile;

    //endregion

    //region Getters and Setters
    public String getBaseUrl() {
        return baseUrl;
    }

    public File getTestTextFile() {
        return new File(testTextFile);
    }
    //endregion
}