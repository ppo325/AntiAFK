package antiafk.antiafk;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;


public class CheckPlayer implements org.bukkit.event.Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent Player) {            //若检测到玩家加入服务器，将自动加入到Map内
        new BukkitRunnable() {
            @Override
            public void run() {
                AntiAFK.semap.put(Player.getPlayer().getName(), 0);
                AntiAFK.isGuaji.put(Player.getPlayer().getName(), false);
            }
        }.runTaskAsynchronously(AntiAFK.me);

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerMoving(PlayerMoveEvent player) {            //若检测到玩家移动，则将玩家的semap数值清空
        new BukkitRunnable() {
            @Override
            public void run() {
                String name = player.getPlayer().getName();
                if (AntiAFK.isGuaji.get(name)) {
                    Bukkit.broadcastMessage(name+"回来了");  //可以改为你需要的代码
                    AntiAFK.isGuaji.put(name, false);
                }
                AntiAFK.semap.put(name, 0);
            }
        }.runTaskAsynchronously(AntiAFK.me);
    }

    @EventHandler
    public void playerchat(AsyncPlayerChatEvent player) {            //若检测到玩家聊天，则将玩家的semap数值清空
        new BukkitRunnable() {
            @Override
            public void run() {
                String name = player.getPlayer().getName();
                AntiAFK.semap.put(name, 0);
                if (AntiAFK.isGuaji.get(name)) {
                    AntiAFK.isGuaji.put(name, false);
                    Bukkit.broadcastMessage(name+"回来了");  //可以改为你需要的代码
                }
            }
        }.runTaskAsynchronously(AntiAFK.me);


    }

    @EventHandler
    public void playerquit(PlayerQuitEvent player) {            //若检测到退出服务器，将玩家移出Map
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Iterator<Map.Entry<String, Integer>> it = AntiAFK.semap.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, Integer> entry = it.next();
                    if (entry.getKey().equals(player.getPlayer().getName())) {
                        it.remove();
                    }
                }
            }
        }.runTaskAsynchronously(AntiAFK.me);
    }
}
