package com.RustyRents.RustyRents.FrameNavigator;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import com.sun.tools.javac.Main;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import javax.swing.*;

@Component
public class FrameNavigatorImpl implements FrameNavigator, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private JFrame currentFrame;

    public FrameNavigatorImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void showFrame(Class<? extends JFrame> frameClass) {
        JFrame frame = applicationContext.getBean(frameClass);
        if (currentFrame != null) {
            currentFrame.dispose();
        }

        if (frame instanceof MainMenu) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((MainMenu) frame).refreshUIData();
            currentFrame.revalidate();
            currentFrame.repaint();
            System.out.println("rat");
        }
        else {
            currentFrame = frame;
            currentFrame.setVisible(true);
        }
        // TODO : currentFrame.initChanges() - Всички текстове и детайли, които се взимат от Database класът да се извикват с тази функция, за да се избегне NULL.
    }
}
