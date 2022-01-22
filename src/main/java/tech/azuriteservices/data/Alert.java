package tech.azuriteservices.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tech.azuriteservices.Globus;
import tech.azuriteservices.utils.CC;

public class Alert {

    public Alert(Player player, String checkName, int vl, String type){

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!players.hasPermission("globus.command.alerts")) return;
            players.sendMessage(CC.toColor(Globus.getINSTANCE().getConfig().getString("alert")
                    .replace("%player%", player.getName())
                    .replace("%check_name%", checkName)
                    .replace("%vl%", String.valueOf(vl))
                    .replace("%type%", type)));
        }
    }
}
