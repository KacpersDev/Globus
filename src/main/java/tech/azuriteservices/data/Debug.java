package tech.azuriteservices.data;

import org.bukkit.Bukkit;
import tech.azuriteservices.utils.CC;

public class Debug {

    public String prefix = CC.toColor("&8[&2Globus&8]&f ");

    public Debug(String message){

        Bukkit.broadcastMessage(prefix + message);
    }
}
