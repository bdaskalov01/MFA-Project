package com.RustyRents.RustyRents.FrameNavigator;

import com.RustyRents.RustyRents.Listings.ListingDetails;
import com.RustyRents.RustyRents.Listings.MyListings;
import com.RustyRents.RustyRents.Listings.ViewListings;
import com.RustyRents.RustyRents.Tokens.SoftwareToken;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import com.RustyRents.RustyRents.Tokens.SoftwareTokenEmailPopUp;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.text.View;

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
            if (currentFrame instanceof ViewListings) {
                ((ViewListings) currentFrame).onCloseInnit();
                currentFrame.dispose();
            }
            else if (currentFrame instanceof MyListings) {
                ((MyListings) currentFrame).onCloseInnit();
                currentFrame.dispose();
            }
            else {
                currentFrame.dispose();
            }
        }


        if (frame instanceof MainMenu) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((MainMenu) frame).refreshUIData();
            currentFrame.revalidate();
            currentFrame.repaint();
        }

        else if (frame instanceof ViewListings) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((ViewListings) frame).refreshUIData();
            currentFrame.revalidate();
            currentFrame.repaint();
        }

        else if (frame instanceof MyListings) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((MyListings) frame).refreshUIData();
            currentFrame.revalidate();
            currentFrame.repaint();
        }

        else if (frame instanceof ListingDetails) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((ListingDetails) frame).refreshUIData();
            currentFrame.revalidate();
            currentFrame.repaint();
        }

        else if (frame instanceof SoftwareToken && currentFrame instanceof SoftwareTokenEmailPopUp) {
            ((SoftwareToken) frame).setLoggedUI();
            currentFrame = frame;
            currentFrame.setVisible(true);
            currentFrame.revalidate();
            currentFrame.repaint();
        }

        else {
            currentFrame = frame;
            currentFrame.setVisible(true);
            currentFrame.revalidate();
            currentFrame.repaint();
        }
        // TODO : currentFrame.initChanges() - Всички текстове и детайли, които се взимат от Database класът да се извикват с тази функция, за да се избегне NULL.
    }
}
