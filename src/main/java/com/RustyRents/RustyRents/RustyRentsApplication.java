package com.RustyRents.RustyRents;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.LogIn.HardwareToken;
import com.RustyRents.RustyRents.LogIn.LogIn;
import com.RustyRents.RustyRents.LogIn.LogInEmailCodeWindow;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.util.Map;

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
			frameNavigator.showFrame(MainMenu.class);
			//frameNavigator.showFrame(HardwareToken.class);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
