package com.RustyRents.RustyRents.FrameNavigator;

import com.RustyRents.RustyRents.Account.MyProfile;
import com.RustyRents.RustyRents.Admin.AdminPanel;
import com.RustyRents.RustyRents.Admin.EditUser;
import com.RustyRents.RustyRents.Admin.ListingsAdmin;
import com.RustyRents.RustyRents.Admin.UsersAdmin;
import com.RustyRents.RustyRents.Listings.EditListing;
import com.RustyRents.RustyRents.Listings.ListingDetails;
import com.RustyRents.RustyRents.Listings.MyListings;
import com.RustyRents.RustyRents.Listings.ViewListings;
import com.RustyRents.RustyRents.LogIn.LogIn;
import com.RustyRents.RustyRents.Register.Register;
import com.RustyRents.RustyRents.Security.SecretQuestion;
import com.RustyRents.RustyRents.Security.SoftwareToken;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import com.RustyRents.RustyRents.Security.SoftwareTokenEmailPopUp;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import javax.swing.*;

@Component
public class FrameNavigatorImpl implements FrameNavigator, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private JFrame currentFrame;

    public JFrame olderFrame;

    public FrameNavigatorImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public JFrame getOlderFrame() {
        return olderFrame;
    }

    public ApplicationContext getApplicationContext() throws BeansException {
        return this.applicationContext;
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
            else if (currentFrame instanceof ListingsAdmin) {
                ((ListingsAdmin) currentFrame).onCloseInnit();
                currentFrame.dispose();
            }
            else if (currentFrame instanceof UsersAdmin) {
                ((UsersAdmin) currentFrame).onCloseInnit();
                currentFrame.dispose();
            }
            else if (currentFrame instanceof Register) {
                ((Register) currentFrame).onClose();
                currentFrame.dispose();
            }
            else if (currentFrame instanceof LogIn) {
                if (frame instanceof SoftwareToken) {
                }
                else {
                    currentFrame.dispose();
                }
            }
            else if(currentFrame instanceof SoftwareToken) {
                //Do nothing
            }
            else {
                currentFrame.dispose();
            }
        }


        if (frame instanceof MainMenu) {
            if (currentFrame instanceof SecretQuestion) {
                LogIn.clearFields();
            }
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

        else if (frame instanceof EditListing) {
            olderFrame = currentFrame;
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((EditListing) frame).refreshUI();
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


        else if (frame instanceof SecretQuestion) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((SecretQuestion) frame).refreshUIData();
            currentFrame.pack();
            currentFrame.revalidate();
            currentFrame.repaint();
        }

        else if (frame instanceof MyProfile) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((MyProfile) frame).refreshUIData();
        //    currentFrame.pack();
            currentFrame.revalidate();
            currentFrame.repaint();
        }

        else if (frame instanceof AdminPanel) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            // ((MyProfile) frame).refreshUIData();
            //    currentFrame.pack();
            currentFrame.revalidate();
            currentFrame.repaint();
        }

        else if (frame instanceof ListingsAdmin) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((ListingsAdmin) frame).refreshUIData();
            //    currentFrame.pack();
            currentFrame.revalidate();
            currentFrame.repaint();
        }

        else if (frame instanceof UsersAdmin) {
            currentFrame = frame;
            currentFrame.setVisible(true);
            ((UsersAdmin) frame).refreshUIData();
            //    currentFrame.pack();
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

        else if (frame instanceof EditUser) {
            ((EditUser) frame).refreshUI();
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
