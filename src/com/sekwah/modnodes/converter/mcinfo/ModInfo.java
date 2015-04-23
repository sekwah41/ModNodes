package com.sekwah.modnodes.converter.mcinfo;

import com.sekwah.modnodes.Assets;

import java.io.IOException;

public class ModInfo {

    public String currentFile;

    public ModInfo(String modID, String modName, String modVersion, String mcVersion, InfoProperties... extra){
        try {
            currentFile = Assets.readReasourceToString("/Code/resources/mcmod.info");
        } catch (IOException e) {
            e.printStackTrace();
        }

        replace("%%modNode%%", modID);
        replace("%%modNodeName%%", modName);
        replace("%%modNodeDesc%%", "Test mod using ModNodes");
        replace("%%modNodeModVersion%%", modVersion);
        replace("%%modNodeMcVersion%%", mcVersion);
        replace("%%modNodeAuthors%%", "\"SEKWAH41\"");

        String extraData = "";
        if(extra.length > 0){
            for(InfoProperties property: extra){
                extraData += ",\n  \"" + property.name + "\": \"" + property.value + "\"";
            }
        }
        replace("%%extradata%%", extraData);


    }

    public String getFileString(){
        return currentFile;
    }

    private void replace(String modNode, String modID) {
        currentFile = currentFile.replace(modNode, modID);
    }

}
