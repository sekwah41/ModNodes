package com.sekwah.modnodes.windows;

import com.sekwah.modnodes.Assets;

import javax.swing.*;
import java.awt.*;

public class ProjectMenu extends JFrame {

    public ProjectMenu(){
        this.setTitle("ModNodes");
        this.setIconImage(Assets.favicon);
        this.setSize(new Dimension(400, 320));
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(180, 180, 180));
    }

}
