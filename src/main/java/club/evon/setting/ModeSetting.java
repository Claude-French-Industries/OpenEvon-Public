package club.evon.setting;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.util.KeyedRandom;




public final class ModeSetting extends Setting<SubMode<?>> {
   private final List<SubMode<?>> F = new ArrayList<>();
   private static final long KEY = KeyedRandom.create(-755106158964981281L, -1253091853300830197L, MethodHandles.lookup().lookupClass()).next(242567084564364L);

   public ModeSetting(String name, SubMode<?>... subModes) {
      super(name, subModes[0]);
      this.F.addAll(Arrays.asList(subModes));
      this.F.forEach(subMode -> subMode.getSettings().forEach(setting -> {
         BooleanSupplier visible = setting.getVisible();
         setting.setVisible(() -> {
            long var3x = KEY ^ 35647016484956L;
            int[] values = SubMode.getStateArray();
            BooleanSupplier tmp = visible;
            if (values == null) {
               if (visible == null) {
                  return subMode == this.getValue();
               }

               tmp = visible;
            }

            return !tmp.getAsBoolean() ? false : subMode == this.getValue();
         });
      }));
   }

   public <T extends SubMode<?>> T getByName(String name) {
      long longValue = KEY ^ 74181297767864L;
      int[] values = SubMode.getStateArray();
      SubMode orElse = this.F.stream().filter(subMode -> subMode.getName().equals(name)).findFirst().orElse(null);
      if (BaseModule.getStateArray() == null) {
         SubMode.setStateArray(new int[4]);
      }

      return (T)orElse;
   }

   public void setSubMode(SubMode<?> value) {
      long longValue = KEY ^ 31332474773182L;
      if (value.getParent() != null && value.getParent().isEnabled()) {
         this.getValue().disable();
         super.setValue(value);
         this.getValue().enable();
      } else {
         super.setValue(value);
      }
   }

   @Override
   public void setValue(SubMode<?> value) {
      this.setSubMode(value);
   }

   @Override
   public List<Setting<?>> getSettings() {
      long longValue = KEY ^ 97530417540651L;
      int[] values = SubMode.getStateArray();
      ArrayList list = new ArrayList();
      int[] values2 = values;
      Iterator iterator = this.getSubModes().iterator();

      while (iterator.hasNext()) {
         SubMode subMode = (SubMode)iterator.next();
         if (values2 != null) {
            break;
         }

         list.addAll(subMode.getSettings());
         if (values2 != null) {
            BaseModule.setStateArray(new BaseModule[3]);
            break;
         }
      }

      return list;
   }

   public List<SubMode<?>> getSubModes() {
      return this.F;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
