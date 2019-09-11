package net.nenko.ScrAutoCaptur;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cfg - maps properties read from property file
 */
public class Cfg {
	private static final Logger log = LoggerFactory.getLogger(Cfg.class);
	private Properties properties = new Properties();

	public Cfg(String propName) {
		log.debug("Cfg: loading resources from " + propName);
		try {
			InputStream iStream = getClass().getResourceAsStream(propName);
			if(iStream == null) {
				log.error("Cfg: resource '{}' not found in classpath", propName);
			} else {
				properties.load(iStream);
			}
		} catch(IOException e) {
			log.error("Cfg: error on loading resources from '{}'", propName, e);
		}
	}

	public String getFile() {
		return properties.getProperty("file");
	}

	public String getKey() {
		return properties.getProperty("key");
	}


	public String getFileName(long ms, long count, String type) {
		Date time = new Date(ms);
		String result = getFile();
		result = result.replace("{YY}", s99(time.getYear()));
		result = result.replace("{MM}", s99(time.getMonth() + 1));
		result = result.replace("{DD}", s99(time.getDate()));
		result = result.replace("{hh}", s99(time.getHours()));
		result = result.replace("{mm}", s99(time.getMinutes()));
		result = result.replace("{ss}", s99(time.getSeconds()));
		result = result.replace("{tt}", s99(ms / 10));
		result = result.replace("{type}", type);
		result = result.replace("{count}", "" + count);
		return result;
	}

	private String s99(long number) {
		number = number % 100;
		return (number < 10 ? "0" : "") + number;
	}
	
}
