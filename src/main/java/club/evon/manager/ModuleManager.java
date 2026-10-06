package club.evon.manager;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap.Builder;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.EventBus;
import club.evon.event.KeyPressEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BindMode;
import club.evon.util.KeyedRandom;




public final class ModuleManager {
   private final ImmutableClassToInstanceMap<BaseModule> moduleMap;
   private static String[] flowGuard;
   private static final long KEY = KeyedRandom.create(-4296797870919973100L, -6427448338481363843L, MethodHandles.lookup().lookupClass()).next(197882230620820L);

   public ModuleManager(BaseModule... modules) {
      super();

      long longValue = KEY ^ 43608719049431L;
      Builder builder = new Builder();

      for (BaseModule module : modules) {
         builder.put(module.getClass(), module);
      }

      this.moduleMap = builder.build();
      EventBus.register(this);
      }

   @Subscribe
   private void onKeyPress(KeyPressEvent event) {
      long longValue = KEY ^ 65211319609998L;
      int value = event.getAction();
      String[] strings = ModuleCategory.getStateArray();
      int value2 = event.getKey();
      String[] strings2 = strings;
      MinecraftClient minecraftClient = ClientContext.MINECRAFT_CLIENT;
      if (strings2 == null) {
         if (ClientContext.MINECRAFT_CLIENT.currentScreen != null) {
            return;
         }

         minecraftClient = ClientContext.MINECRAFT_CLIENT;
      }

      if (minecraftClient.getOverlay() == null) {
         for (BaseModule module : this.getModules()) {
            label103: {
               int value3 = module.getBindSetting().getValue();
               if (strings2 == null) {
                  applyBindAction(module, value2, value);
                  break label103;
               }

               label104: {
                  if (strings2 == null) {
                     switch (value3) {
                        case 1:
                           value3 = value;
                           break;
                        case 2:
                           break label104;
                        default:
                           break label103;
                     }
                  }

                  label75: {
                     if (strings2 == null) {
                        if (value3 == 1) {
                           break label75;
                        }

                        value3 = value;
                     }

                     if (value3 != 0) {
                        break label103;
                     }
                  }

                  boolean tmp = value != 0;
                  if (strings2 == null) {
                     tmp = value == 1;
                  }

                  module.setEnabled(tmp);
                  if (strings2 == null) {
                     break label103;
                  }
               }

               if (value == 1) {
                  boolean enabled = module.isEnabled();
                  if (strings2 == null) {
                     enabled = !enabled;
                  }

                  module.setEnabled(enabled);
               }
            }

            if (strings2 != null) {
               break;
            }
         }
      }
   }

   static void applyBindAction(BaseModule module, int key, int action) {
      if (module.getBindSetting().getValue() != key) {
         return;
      }

      BindMode bindMode = module.getBindSetting().getBindMode();
      if (bindMode == BindMode.HOLD) {
         if (action == 0 || action == 1) {
            module.setEnabled(action == 1);
         }
      } else if (bindMode == BindMode.TOGGLE && action == 1) {
         module.setEnabled(!module.isEnabled());
      }
   }

   public Collection<BaseModule> getModules() {
      return this.moduleMap.values();
   }

   public BaseModule getModuleByName(String name) {
      long longValue = KEY ^ 58352523420747L;
      String[] strings = ModuleCategory.getStateArray();
      BaseModule orElse = this.getModules().stream().filter(module -> module.getName().replace(" ", "").equalsIgnoreCase(name.replace(" ", ""))).findFirst().orElse(null);
      if (BaseModule.getStateArray() == null) {
         ModuleCategory.setStateArray(new String[5]);
      }

      return orElse;
   }

   public List<BaseModule> getModulesByCategory(ModuleCategory category) {
      long longValue = KEY ^ 117314241201992L;
      String[] strings = ModuleCategory.getStateArray();
      ArrayList list = new ArrayList();
      String[] strings2 = strings;

      for (BaseModule module : this.getModules()) {
         if (strings2 == null && module.getCategory().equals(category)) {
            list.add(module);
         }

         if (strings2 != null) {
            BaseModule.setStateArray(new BaseModule[3]);
            break;
         }
      }

      return list;
   }

   public <T extends BaseModule> T getModuleByClass(Class<T> clazz) {
      return (T)this.moduleMap.getInstance(clazz);
   }

   public static void setFlowGuard(String[] value) {
      flowGuard = value;
   }

   public static String[] getFlowGuard() {
      return flowGuard;
   }

   static {
      if (getFlowGuard() != null) {
         setFlowGuard(new String[5]);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
