package com.RustyRents.RustyRents;

import com.RustyRents.RustyRents.Account.ChangeEmail;
import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.LogIn.LogIn;
import com.RustyRents.RustyRents.Account.ChangePassword;
import com.RustyRents.RustyRents.Security.SecretQuestion;
import com.RustyRents.RustyRents.Security.SoftwareToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RustyRentsApplication {

	static final Logger logger = LogManager.getLogger(RustyRentsApplication.class.getName());

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		ApplicationContext applicationContext = SpringApplication.run(RustyRentsApplication.class, args);
		try {
			Database.establishConnection();
			Database.removeAllEmailCodes();
			Database.removeAllSoftwareCodes();
			FrameNavigator frameNavigator = applicationContext.getBean(FrameNavigator.class);
			frameNavigator.showFrame(LogIn.class);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
