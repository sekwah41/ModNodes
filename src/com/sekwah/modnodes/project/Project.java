package com.sekwah.modnodes.project;

public class Project {

    public String modName = "ModNodes Test Mod";

    public String modID = "modNode";

    public String modVersion = "1.0";

    public String modDescription = "Special thanks to sekwah for modnodes.";

    // Would be located at /modNodes.png
    public String imageName = "modNodes";

    public Project(String modName, String modID, String modVersion){
        this.modName = modName;
        this.modID = modID;
        this.modVersion = modVersion;
    }

    public Project(String modName, String modID, String modVersion, String modDescription, String imageName){
        this(modName, modID,modVersion);
        this.modDescription = modDescription;
        this.imageName = imageName;
    }


}
