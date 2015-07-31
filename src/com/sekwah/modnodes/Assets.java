package com.sekwah.modnodes;

import com.sekwah.modnodes.files.DeleteFolder;
import com.sekwah.modnodes.files.Unpacker;
import com.sekwah.modnodes.windows.SplashScreen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Assets {

    public static String AppdataStorageLocation;

    public static BufferedImage favicon = null;

    private static SplashScreen splashScreen;

    private static String currentOS;


    public static void loadResources(SplashScreen loadingScreen){
        Assets.splashScreen  = loadingScreen;
        
        favicon = loadTexture("Images/favicon.png");

        currentOS = System.getProperty("os.name").toUpperCase();
        if (currentOS.contains("WIN")){
            AppdataStorageLocation = System.getenv("APPDATA") + File.separator + "ModNodes";
        }
        else if (currentOS.contains("MAC")){
            AppdataStorageLocation = System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + "SekC's Model Editor";
        }
        else if (currentOS.contains("NUX")){
            JOptionPane.showMessageDialog(Assets.splashScreen, "Sorry but the Linux operating system is not currently supported at the moment, we plan to add support soon though!\n\n" +
                    "If you are using Linux and would like the mod installer to work please contact sekwah41,\n" +
                    "we could not make it work because we couldnt find any Linux users to test it on\n" +
                    "so we need someone to be our tester!", "Operating system not currently supported!", JOptionPane.ERROR_MESSAGE);
            System.exit(Assets.splashScreen.ABORT);
        }
        else{
            JOptionPane.showMessageDialog(Assets.splashScreen, "You seem to be using some sort of odd operating system or your\n" +
                    "operating system hasn't been detected correctly.\n\n" +
                    "Please report this to sekwah41 along with the operating system you are using.", "What are you using?", JOptionPane.ERROR_MESSAGE);
            System.exit(Assets.splashScreen.ABORT);
        }

        Executor exec = Executors.newSingleThreadExecutor();

        exec.execute(new Runnable() {
            public void run() {
                /** this part is probably not needed but is being kept like this just in case
                 if(new File(AppdataInstallLocation + File.separator + "versions" + File.separator + Version).exists()){
                 try {
                 DeleteFolder.main(AppdataInstallLocation + File.separator + "versions" + File.separator + Version);
                 } catch (Exception e) {
                 System.out.println("Delete error!");
                 e.printStackTrace();
                 }
                 }*/

                String fileLocation = null;

                makeDir(AppdataStorageLocation);
                makeDir(AppdataStorageLocation + File.separator + "forge");
                makeDir(AppdataStorageLocation + File.separator + "forgeSrc");
                makeDir(AppdataStorageLocation + File.separator + "saves");
            }

            private void makeDir(String dir) {
                if(!new File(dir).exists()){new File(dir).mkdir();}

            }});
        while(!new File(AppdataStorageLocation + File.separator + "forge" + File.separator + "build").exists()){
            if(!new File(AppdataStorageLocation + File.separator + "gradlew.bat").exists() &&
                    new File(AppdataStorageLocation + File.separator + "forgeSrc" + File.separator + "forge-1.7.10-10.13.2.1291-src.zip").exists()){
                exec.execute(new Thread(new Unpacker(AppdataStorageLocation + File.separator + "forgeSrc" + File.separator + "forge-1.7.10-10.13.2.1291-src.zip", AppdataStorageLocation + File.separator + "forge")));
            }
            else{

                JOptionPane.showMessageDialog(null, "Place the forgeSrc into the opened folder once downloaded", "Download ForgeSrc", JOptionPane.INFORMATION_MESSAGE);

                String url = "http://adfoc.us/serve/?id=27122854927026";
                try {
                    java.awt.Desktop.getDesktop().browse(URI.create(url));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Desktop.getDesktop().open(new File(AppdataStorageLocation + File.separator + "forgeSrc"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Click ok once forgeSrc has been placed into the folder which was opened", "Download ForgeSrc", JOptionPane.INFORMATION_MESSAGE);
                continue;
            }
            if(currentOS.contains("WIN")){
                try {
                    File forgeLocation = new File(AppdataStorageLocation + File.separator + "forge" + File.separator);
                    Process p = Runtime.getRuntime().exec("cmd /c start /wait cmd.exe /K \"title ModNodes && cd " + forgeLocation.getAbsolutePath() + " && cls && gradlew setupDecompWorkspace && exit\"");
                    p.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            else{
                break;
            }

        }

        loadingScreen.fadeOut();



        ModNodes.runMinecraft();

        System.exit(1);

    }

    public static BufferedImage loadTexture(String texture){
        try {
            return ImageIO.read(Assets.class.getClassLoader().getResource(texture));
        } catch (IOException e) {
            System.out.println("Could not load image: " + texture);
            return null;
        }
    }

    public static String readReasourceToString(String resourceLocation) throws IOException {
        return readFileStream(Assets.class.getResourceAsStream(resourceLocation));
    }

    private static String readFileStream(InputStream filestream) throws IOException {
        InputStreamReader inr = new InputStreamReader(filestream, "ASCII");
        BufferedReader br = new BufferedReader(inr);
        StringBuffer sb = new StringBuffer();
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            sb.append(line);
            sb.append("\n");
        }

        br.close();
        inr.close();

        return sb.toString();
    }

    public static void cleanSrc() {
        // Cleans up the src folders
        File javaFolder = new File(AppdataStorageLocation+ File.separator + "forge" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator);
        DeleteFolder.delete(javaFolder, false);
        javaFolder.mkdirs();
        File resourceFolder = new File(AppdataStorageLocation + File.separator + "forge" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator);
        DeleteFolder.delete(resourceFolder, false);
        resourceFolder.mkdirs();
    }

    public static URL getResource(String resourceLoc) {
        return Assets.class.getClassLoader().getResource(resourceLoc);
    }

    public static void writeFile(File file, String contents) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(contents);
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
