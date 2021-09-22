package de.guntram.mcmod.infinitevillagertrading.mixins;

import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(TradeOffer.class) 
public class MixinTradeOffer {
    
    @Shadow private @Final @Mutable int maxUses;
    @Shadow private int demandBonus;

    // As the other parametrized methods all call the max-parameter-count one,
    // we only need to inject into the NBT one here (when the villager gets loaded from disk)
    // and the max-parameter-count one below (when the server generated trades).
    
    @Inject(method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("RETURN"))
    public void forceHighMaxUseCountNbt(CallbackInfo ci) {
//        System.out.println("forceHigMaxUseCount called NBT");
        adjustTradeVars();
    }

/*
    @Inject(method = "<init>(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;IIF)V", at = @At("RETURN"))
//    @Inject(method = "<init>*", at = @At("RETURN"))
    public void forceHighMaxUseCount2SIIF(CallbackInfo ci) {
        System.out.println("forceHigMaxUseCount called 2S IIF");
        adjustTradeVars();
    }
    
    @Inject(method = "<init>(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;IIF)V", at = @At("RETURN"))
//    @Inject(method = "<init>*", at = @At("RETURN"))
    public void forceHighMaxUseCount3SIIF(CallbackInfo ci) {
        System.out.println("forceHigMaxUseCount called 3S IIF");
        adjustTradeVars();
    }
    
    @Inject(method = "<init>(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;IIIF)V", at = @At("RETURN"))
//    @Inject(method = "<init>*", at = @At("RETURN"))
    public void forceHighMaxUseCountIIIF(CallbackInfo ci) {
        System.out.println("forceHigMaxUseCount called IIIF");
        adjustTradeVars();
    }

*/

    @Inject(method = "<init>(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;IIIFI)V", at = @At("RETURN"))
    public void forceHighMaxUseCountIIIFI(CallbackInfo ci) {
//        System.out.println("forceHigMaxUseCount called IIIFI");
        adjustTradeVars();
    }
    
    private void adjustTradeVars() {
//        System.out.println("Daemon: "+ Thread.currentThread().isDaemon());
//        System.out.println("Name: "+ Thread.currentThread().getName());
        // is this REALLY the only way when there's no world to check world.isRemote() ??
        if (Thread.currentThread().getName().equals("Server thread")) {
            this.maxUses = 1_000_000;
            this.demandBonus = 0;
        }
//        System.out.println("Before: maxUses="+this.maxUses+", demandBonus= "+this.demandBonus);
    }
    
    @Inject(method = "updateDemandBonus", at=@At("RETURN")) 
    public void resetDemandBonus(CallbackInfo ci) {
        // System.out.println("resetDemandBonus called");
        this.demandBonus = 0;
    }
}
