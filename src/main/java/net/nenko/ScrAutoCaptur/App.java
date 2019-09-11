package net.nenko.ScrAutoCaptur;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starting application with slf4j/logback, and properties configuration
 */
public class App {
	private static final Logger log = LoggerFactory.getLogger(App.class);
	private static final String FILE_FORMAT = "png";

	public static void main( String[] args) {
		log.info("App start.");
		Cfg cfg = new Cfg("/App.properties");
		log.info("Configuration: file={}, key={}", cfg.getFile(), cfg.getKey());
		// TODO: check validity of properties

		int secondsLoop = 0;

		for(String arg: args) {
			secondsLoop = Integer.parseInt(arg);
		}

		for(long count = 0; ; count ++) {
			try {
				Robot robot = new Robot();
				String fileName = cfg.getFileName(System.currentTimeMillis(), count, FILE_FORMAT);
				Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
				File objFile = new File(fileName);
				objFile.mkdirs();
				ImageIO.write(screenFullImage, FILE_FORMAT, objFile);

				if(secondsLoop <= 0) {
					break;
				}

				Thread.sleep(secondsLoop * 1000);

			} catch(Exception e) {
				log.error("ScrAutoCaptur failed with ex:", e);
				System.exit(1);
			}
		}
		log.info("App finished successfully.");
	}

}
