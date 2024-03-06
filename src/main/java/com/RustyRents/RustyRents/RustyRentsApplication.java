package com.RustyRents.RustyRents;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.LogIn.LogIn;
import com.RustyRents.RustyRents.LogIn.SoftwareToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RustyRentsApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		ApplicationContext applicationContext = SpringApplication.run(RustyRentsApplication.class, args);
		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			System.out.println(beanName);
		}

		try {
			Database.establishConnection();
			Database.removeAllEmailCodes();
			Database.removeAllSoftwareCodes();
			FrameNavigator frameNavigator = applicationContext.getBean(FrameNavigator.class);
			frameNavigator.showFrame(LogIn.class);
			frameNavigator.showFrame(SoftwareToken.class);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
