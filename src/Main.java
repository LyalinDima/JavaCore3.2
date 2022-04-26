import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String SAVE_PATH = "E:\\Games\\savegames\\";

    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(100, 10, 1, 32.1);
        GameProgress save2 = new GameProgress(65, 50, 7, 250.3);
        GameProgress save3 = new GameProgress(87, 250, 15, 128.4);
        saveGame(SAVE_PATH + "save1.dat", save1);
        saveGame(SAVE_PATH + "save2.dat", save2);
        saveGame(SAVE_PATH + "save3.dat", save3);
        List<String> saveFiles = new ArrayList<>();
        File saveFolder = new File(SAVE_PATH);
        for (File f : saveFolder.listFiles()) {
            saveFiles.add(f.getAbsolutePath());
        }
        zipFiles(SAVE_PATH + "zip.zip", saveFiles);
        for (String filePath : saveFiles) {
            new File(filePath).delete();
        }
    }

    private static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void zipFiles(String zipPath, List<String> files) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String file : files) {
                ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf(File.separator) + 1));
                zipOutputStream.putNextEntry(entry);
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
