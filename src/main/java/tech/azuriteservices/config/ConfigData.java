package tech.azuriteservices.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import tech.azuriteservices.Globus;

import java.io.File;
import java.io.IOException;

public class ConfigData {

    public ConfigData(File file, FileConfiguration configuration, String dir){

        if (!(file.exists())) {
            file.getParentFile().mkdir();
            Globus.getINSTANCE().saveResource(dir, false);
        }

        try {
            configuration.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
