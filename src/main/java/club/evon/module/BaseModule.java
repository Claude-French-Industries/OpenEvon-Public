package club.evon.module;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import club.evon.config.ConfigManager;
import club.evon.event.EventBus;
import club.evon.event.ModuleToggleEvent;
import club.evon.manager.HudDragManager;
import club.evon.setting.BindSetting;
import club.evon.setting.ModeSetting;
import club.evon.setting.PositionSetting;
import club.evon.setting.Setting;
import club.evon.util.KeyedRandom;




public class BaseModule {
   private final String u;
   private final String e;
   private final ModuleCategory Q;
   private final BindSetting bindSetting;
   private boolean S;
   private boolean n;
   private final ArrayList<Setting<?>> settings = new ArrayList<>();
   private static BaseModule[] stateArray;
   private static final long KEY = KeyedRandom.create(-414645475115555030L, -5744124084267770781L, MethodHandles.lookup().lookupClass()).next(207156156284461L);

   public BaseModule(String name, String description, ModuleCategory category, int key) {
      this.u = name;
      this.e = description;
      this.Q = category;
      this.settings.add(this.bindSetting = new BindSetting(key));
   }

   public BaseModule(String name, String description, ModuleCategory category) {
      this(name, description, category, -1);
   }

   public final void addSettings(Setting<?>... properties) {
      long longValue = KEY ^ 82289780784688L;

      for (Setting setting : properties) {
         if (setting instanceof PositionSetting setting2) {
            HudDragManager.getInstance().registerModule(setting2, this);
         }

         this.settings.add(setting);
      }
   }

   public void onEnable() {
      long longValue = KEY ^ 87705611018999L;

      for (Setting setting : this.settings) {
         if (setting instanceof ModeSetting setting2) {
            setting2.getValue().enable();
         }
      }

      EventBus.register(this);
   }

   public void onDisable() {
      long longValue = KEY ^ 68941626012822L;

      for (Setting setting : this.settings) {
         if (setting instanceof ModeSetting setting2) {
            setting2.getValue().disable();
         }
      }

      EventBus.unregister(this);
   }

   public List<Setting<?>> getSettings() {
      ArrayList list = new ArrayList();
      this.settings.forEach(var1x -> {
         long longValue = KEY ^ 22502998418685L;
         String[] strings = ModuleCategory.getStateArray();
         List settings2 = var1x.getSettings();
         list.add(var1x);
         String[] strings2 = strings;
         List list2 = settings2;
         if (strings2 == null) {
            if (settings2 == null) {
               return;
            }

            list2 = list;
         }

         list2.addAll(settings2);
      });
      return list;
   }

   public String getSuffix() {
      return null;
   }

   public final void setEnabled(boolean enabled) {
      long longValue = KEY ^ 58712654361947L;
      if (this.n != enabled) {
         ModuleToggleEvent moduleToggleEvent = new ModuleToggleEvent(this, enabled);
         EventBus.post(moduleToggleEvent);
         if (moduleToggleEvent.isCanceled()) {
            return;
         }

         if (enabled) {
            this.onEnable();
         } else {
            this.onDisable();
         }

         this.n = enabled;
         // Module state is part of the config; the debounced autosave picks this up. Called
         // with "enabled" already applied so a half-toggled module is never what gets written.
         ConfigManager.markDirty();
      }
   }

   public String getName() {
      return this.u;
   }

   public String getDescription() {
      return this.e;
   }

   public ModuleCategory getCategory() {
      return this.Q;
   }

   public BindSetting getBindSetting() {
      return this.bindSetting;
   }

   public boolean isHidden() {
      return this.S;
   }

   public boolean isEnabled() {
      return this.n;
   }

   public void setHidden(boolean hidden) {
      this.S = hidden;
   }

   public static void setStateArray(BaseModule[] values) {
      stateArray = values;
   }

   public static BaseModule[] getStateArray() {
      return stateArray;
   }

   static {
      if (getStateArray() == null) {
         setStateArray(new BaseModule[3]);
      }
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }
}
