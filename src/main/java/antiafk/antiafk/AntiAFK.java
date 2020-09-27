package antiafk.antiafk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public final class AntiAFK extends JavaPlugin {
    public static HashMap<String, Integer> semap = new HashMap<>();
    public static HashMap<String, Boolean> isGuaji = new HashMap<>();
    public static Plugin me;
    public static Integer time=5; //玩家不移动多少秒后判定为挂机（即semap值为多少后判断玩家为挂机状态）

    @Override
    public void onEnable() {
        //插件启动时的工作
        me=this;        //将me赋值为本插件
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {    //将所有玩家加入Map
            semap.put(p.getName(), 0);
            isGuaji.put(p.getName(), false);
        }
        Bukkit.getPluginManager().registerEvents(new CheckPlayer(), this);   //注册事件

        new BukkitRunnable() {
            @Override
            public void run() {
                for (String key : semap.keySet()) { //遍历所有玩家，逐个比较是否有玩家无操作时间(semap)大于设定时间
                    int value = semap.get(key);  //获取玩家已经
                    semap.put(key, value + 1);  //所有玩家semap值+1
                    if (value >= time && !isGuaji.get(key)) {
                        isGuaji.put(key, true);
                        Bukkit.broadcastMessage(key+"暂时离开了");   //可以改为你需要的代码
                        }
                    }
                }
        }.runTaskTimerAsynchronously(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
