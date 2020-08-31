package de.guntram.bukkit.InfiniteVillagerTrading;

import java.lang.reflect.Field;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void interact(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        if (!(event.getRightClicked() instanceof Villager)) {
            return;
        }
        Villager villager = (Villager) event.getRightClicked();
        
        List<MerchantRecipe> recipes = villager.getRecipes();
        for (MerchantRecipe recipe: recipes) {
            recipe.setUses(0);
            recipe.setMaxUses(1_000_000);
            
            try {
                Field handle = findField(recipe, "handle");
                handle.setAccessible(true);
                Object NMSRecipe = handle.get(recipe);
                Field demand = findField(NMSRecipe, "demand");
                demand.setAccessible(true);
                demand.setInt(NMSRecipe, 0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // No idea why this is neccesary, but getField(), even when applied to the
    // correct class, seems to throw an exception. Optimizer plus mappings maybe?
    Field findField(Object obj, String name) {
        Class cls = obj.getClass();
        
        while (cls != null) {
            for (Field f: cls.getDeclaredFields()) {
                if (f.getName().equals(name)) {
                    return f;
                }
            }
            cls = cls.getSuperclass();
        }
        
        System.err.println("Field "+name+" was not found. Here's what I have:\n");
        cls = obj.getClass();
        while (cls != null) {
            for (Field f: cls.getDeclaredFields()) {
                System.err.println("   - "+f.getName());
            }
            cls = cls.getSuperclass();
        }
        
        return null;
    }
}
