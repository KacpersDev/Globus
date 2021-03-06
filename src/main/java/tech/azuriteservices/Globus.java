package tech.azuriteservices;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.settings.PacketEventsSettings;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tech.azuriteservices.check.impl.movement.fly.FlyA;
import tech.azuriteservices.check.impl.movement.fly.FlyB;
import tech.azuriteservices.check.impl.movement.fly.FlyC;
import tech.azuriteservices.check.impl.movement.fly.FlyD;
import tech.azuriteservices.check.impl.movement.speed.SpeedA;
import tech.azuriteservices.check.manager.CheckManager;
import tech.azuriteservices.config.ConfigData;
import tech.azuriteservices.utils.Database;
import xyz.invisraidinq.rocketapi.api.database.mongo.RocketMongo;

import java.io.File;

public final class Globus extends JavaPlugin {

    private final CheckManager checkManager = new CheckManager();
    private static Globus INSTANCE;
    public File config = new File(getDataFolder(), "config.yml");
    public FileConfiguration configuration = new YamlConfiguration();

    @Override
    public void onLoad(){
        PacketEvents.create(this);
        PacketEventsSettings settings = PacketEvents.getAPI().getSettings();
        settings
                .fallbackServerVersion(ServerVersion.v_1_8)
                .compatInjector(false)
                .checkForUpdates(false)
                .bStats(true);
        PacketEvents.get().loadAsyncNewThread();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getConsoleSender().sendMessage("Globus has been enabled...");
        checkManager.registerChecks();
        PacketEvents.get().init();
        this.config();
        this.database();
    }

    public static Globus getINSTANCE() {
        return INSTANCE;
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

    @Override
    public void onDisable() {
        INSTANCE = null;
        PacketEvents.get().terminate();
    }

    private void config(){

        new ConfigData(config, configuration, "config.yml");
    }

    private void database(){

        new Database();
        System.out.println("Enabled");
    }
}
