package de.guntram.mcmod.infinitevillagertrading.mixins;

import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(TradeOffer.class) 
public class MixinTradeOffer {
    
    @Shadow private @Final int maxUses;
    
    @Inject(method = "<init>*", at = @At("RETURN"))
    public void forceHighMaxUseCount(CallbackInfo ci) {
        this.maxUses = 1_000_000;
    }
}
