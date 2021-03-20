package de.guntram.mcmod.infinitevillagertrading.mixins;

import net.minecraft.village.TradeOffer;
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
    
    @Inject(method = "<init>*", at = @At("RETURN"))
    public void forceHighMaxUseCount(CallbackInfo ci) {
        this.maxUses = 1_000_000;
        this.demandBonus = 0;
    }
    
    @Inject(method = "updateDemandBonus", at=@At("RETURN")) 
    public void resetDemandBonus(CallbackInfo ci) {
        this.demandBonus = 0;
    }
}
