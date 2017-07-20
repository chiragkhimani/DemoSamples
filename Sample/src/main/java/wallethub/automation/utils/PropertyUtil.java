package wallethub.automation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyUtil {
	static Properties prop = new Properties();

	public static void initProperties() {
		try {
			prop.load(new FileInputStream(new File(
					System.getProperty("user.dir") + "//src//test//resources//configuration//config.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

}
