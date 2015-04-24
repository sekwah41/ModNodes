package com.sekwah.modnodes;

import com.sekwah.modnodes.converter.mcinfo.ModInfo;
import com.sekwah.modnodes.converter.modfiles.ModMain;
import com.sekwah.modnodes.project.Project;
import com.sekwah.modnodes.windows.SplashScreen;

import java.io.File;
import java.io.IOException;

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
        mcinfo.writeToFile(new File(srcLocation + File.separator + "resources" + File.separator + "mcmod.info"));
        String packageLocation = "com.sekwah.modnodes";
        String modMainLoc = srcLocation + File.separator + "java" + File.separator + packageLocation.replace(".", File.separator);
        new File(modMainLoc).mkdirs();
        ModMain modMain = new ModMain("modNode", "1.0", packageLocation);
        modMain.writeToFile(new File(modMainLoc + File.separator + "modNode.java"));

        try {
            File forgeLocation = new File(Assets.AppdataStorageLocation + File.separator + "forge" + File.separator);
            Process p = Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"title ModNodes && cd " + forgeLocation.getAbsolutePath() + " && cls && gradlew runClient && exit\"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}
