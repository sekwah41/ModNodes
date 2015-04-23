package com.sekwah.modnodes;

import com.sekwah.modnodes.converter.mcinfo.ModInfo;
import com.sekwah.modnodes.project.Project;
import com.sekwah.modnodes.windows.SplashScreen;

import java.io.File;

public class ModNodes {

    public static String mcVersion = "1.7.10";

    public static Project currentProject;

    public static void main(String[] args){

        currentProject = new Project("","","");

        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setOpacity(0);
        splashScreen.setVisible(true);
        splashScreen.fadeIn();

    }

    public static boolean runMinecraft(){

        String srcLocation = Assets.AppdataStorageLocation + File.separator + "forge" + File.separator + "src" + File.separator + "main";
        Assets.cleanSrc();
        ModInfo mcinfo = new ModInfo("modNode", "ModNodes for MC", "1.0", mcVersion);
        Assets.writeFile(new File(srcLocation + File.separator + "resources" + File.separator + "mcmod.info"), mcinfo.getFileString());

        return true;
    }

}
