package com.RustyRents.RustyRents.FrameNavigator;

import org.springframework.context.ApplicationContext;

import javax.swing.*;

public interface FrameNavigator {
    void showFrame(Class<? extends JFrame> frameClass);

    public ApplicationContext getApplicationContext();

    public JFrame getOlderFrame();
}
