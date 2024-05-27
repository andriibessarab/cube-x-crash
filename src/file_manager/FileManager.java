package file_manager;

import java.io.*;
import java.util.Properties;

public class FileManager {
    private Properties properties;
    private String propertiesPath;

    public FileManager() {
        properties = new Properties();
        propertiesPath = "gameData.properties";
        loadProperties();
    }

    private void loadProperties() {
        try {
            File file = new File(propertiesPath);
            if (file.exists()) {
                properties.load(new FileInputStream(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveHighScore(int score) {
        properties.setProperty("highScore", String.valueOf(score));
        saveProperties();
    }

    public int getHighScore() {
        return Integer.parseInt(properties.getProperty("highScore", "0"));
    }

    public void saveCoins(int coins) {
        properties.setProperty("coins", String.valueOf(coins));
        saveProperties();
    }

    public int getCoins() {
        return Integer.parseInt(properties.getProperty("coins", "0"));
    }

    public void saveSoundEnabled(boolean soundEnabled) {
        properties.setProperty("soundEnabled", String.valueOf(soundEnabled));
        saveProperties();
    }

    public boolean isSoundEnabled() {
        return Boolean.parseBoolean(properties.getProperty("soundEnabled", "true"));
    }

    private void saveProperties() {
        try {
            properties.store(new FileOutputStream(propertiesPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
