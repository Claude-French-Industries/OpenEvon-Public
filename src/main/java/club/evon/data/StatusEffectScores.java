package club.evon.data;
import java.util.HashMap;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;




public class StatusEffectScores extends HashMap<StatusEffect, Integer> {
   public StatusEffectScores() {
      this.put((StatusEffect)StatusEffects.INSTANT_HEALTH.value(), 1);
      this.put((StatusEffect)StatusEffects.REGENERATION.value(), 2);
      this.put((StatusEffect)StatusEffects.HEALTH_BOOST.value(), 3);
      this.put((StatusEffect)StatusEffects.ABSORPTION.value(), 4);
      this.put((StatusEffect)StatusEffects.SATURATION.value(), 5);
      this.put((StatusEffect)StatusEffects.STRENGTH.value(), 6);
      this.put((StatusEffect)StatusEffects.SPEED.value(), 7);
      this.put((StatusEffect)StatusEffects.FIRE_RESISTANCE.value(), 8);
      this.put((StatusEffect)StatusEffects.INVISIBILITY.value(), 9);
      this.put((StatusEffect)StatusEffects.HASTE.value(), 10);
      this.put((StatusEffect)StatusEffects.WATER_BREATHING.value(), 11);
   }
}
