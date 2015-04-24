package com.sekwah.modnodes.converter.modfiles;

import com.sekwah.modnodes.Assets;

import java.io.File;
import java.io.IOException;

public class ModMain {

    public String currentFile;

    public ModMain(String modID, String modVersion, String packageLocation){
        try {
            currentFile = Assets.readReasourceToString("/Code/java/classes/NodeMod");
        } catch (IOException e) {
            e.printStackTrace();
        }

        replace("%%modPackage%%", packageLocation);
        replace("%%modClassName%%", modID);
        replace("%%modID%%", modID);
        replace("%%modNodeModVersion%%", modVersion);


    }

    public String getFileString(){
        return currentFile;
    }

    private void replace(String modNode, String modID) {
        currentFile = currentFile.replace(modNode, modID);
    }

    public void writeToFile(File file) {
        Assets.writeFile(file, currentFile);
    }
}
