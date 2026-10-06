package club.evon.manager;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.ClientService;
import club.evon.event.EventBus;
import club.evon.event.HudRenderEvent;
import club.evon.event.Subscribe;
import club.evon.event.WindowResizeEvent;
import club.evon.module.BaseModule;
import club.evon.setting.PositionSetting;
import club.evon.ui.SnapManager;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;




public final class HudDragManager implements ClientService {
   private final SnapManager snapManager = new SnapManager();
   private final Map<PositionSetting, BaseModule> positionModules = new HashMap<>();
   private boolean dragging;
   private static HudDragManager instance;
   private static final long KEY = KeyedRandom.create(-687251129992698656L, -1584481537129278646L, MethodHandles.lookup().lookupClass()).next(38956369447735L);

   public void registerModule(PositionSetting property, BaseModule module) {
      this.positionModules.put(property, module);
   }

   @Subscribe
   private void onHudRender(HudRenderEvent event) {
      HudDragManager hudDragManager;
      label30: {
         long longValue = KEY ^ 28401879115912L;
         String flowGuard = ClientState.getFlowGuard();
         boolean tmp = ClientContext.MINECRAFT_CLIENT.currentScreen instanceof ChatScreen;
         if (flowGuard == null) {
            if (tmp) {
               float floatValue = (float)event.getMouseX();
               float floatValue2 = (float)event.getMouseY();
               int value = ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth();
               int value2 = ClientContext.MINECRAFT_CLIENT.getWindow().getScaledHeight();
               this.positionModules.forEach((var5x, var6x) -> {
                  PositionSetting setting;
                  label29: {
                     long var7x = KEY ^ 130732537079440L;
                     String var9x = ClientState.getFlowGuard();
                     boolean var10000x = var6x.isEnabled();
                     if (var9x == null) {
                        if (!var10000x) {
                           return;
                        }

                        setting = var5x;
                        if (var9x != null) {
                           break label29;
                        }

                        var10000x = var5x.isDragging();
                     }

                     if (!var10000x) {
                        return;
                     }

                     this.snapManager.updateSnapPoints(var5x, value, value2);
                     setting = var5x;
                  }

                  float floatValue3 = setting.getWidth();
                  float floatValue4 = var5x.getHeight();
                  float floatValue5 = floatValue - var5x.getOffsetX();
                  float floatValue6 = floatValue2 - var5x.getOffsetY();
                  floatValue5 = this.snapManager.snapX(floatValue5, floatValue3);
                  floatValue6 = this.snapManager.snapY(floatValue6, floatValue4);
                  floatValue5 = MathHelper.clamp(floatValue5, 0.0F, value - floatValue3);
                  floatValue6 = MathHelper.clamp(floatValue6, 0.0F, value2 - floatValue4);
                  float floatValue7 = floatValue5 / value;
                  float floatValue8 = floatValue6 / value2;
                  var5x.setValue(new Vec2f(floatValue7, floatValue8));
                  this.snapManager.renderGuides(value, value2, var5x.getX(), var5x.getY(), var5x.getWidth(), var5x.getHeight());
               });
               return;
            }

            hudDragManager = this;
            if (flowGuard != null) {
               break label30;
            }

            tmp = this.dragging;
         }

         if (!tmp) {
            return;
         }

         hudDragManager = this;
      }

      hudDragManager.stopDragging();
   }

   @Subscribe
   private void onWindowResize(WindowResizeEvent event) {
      int value = event.getWidth();
      int value2 = event.getHeight();
      this.positionModules.forEach((var2x, var3x) -> {
         Vec2f value3 = var2x.getValue();
         float floatValue = var2x.getWidth();
         float floatValue2 = var2x.getHeight();
         float floatValue3 = MathHelper.clamp(value3.x * value, 0.0F, value - floatValue);
         float floatValue4 = MathHelper.clamp(value3.y * value2, 0.0F, value2 - floatValue2);
         float floatValue5 = floatValue3 / value;
         float floatValue6 = floatValue4 / value2;
         var2x.setValue(new Vec2f(floatValue5, floatValue6));
      });
   }

   public void startDrag(Click click) {
      long longValue = KEY ^ 21119073413229L;
      String flowGuard = ClientState.getFlowGuard();
      Click tmp = click;
      if (flowGuard == null) {
         if (click.button() != 0) {
            return;
         }

         tmp = click;
      }

      double doubleValue = tmp.x();
      double doubleValue2 = click.y();
      this.positionModules.forEach((var5x, module) -> {
         long var7x = KEY ^ 107312056125695L;
         String var10000x = ClientState.getFlowGuard();
         Vec2f vec2f = new Vec2f(var5x.getX(), var5x.getY());
         String text = var10000x;
         Vec2f vec2f2 = new Vec2f(var5x.getWidth(), var5x.getHeight());
         if (text == null) {
            if (!HoverUtil.isHovered(doubleValue, doubleValue2, vec2f.x, vec2f.y, vec2f2.x, vec2f2.y)) {
               return;
            }

            var5x.beginDrag((float)doubleValue, (float)doubleValue2, vec2f.x, vec2f.y);
            var5x.setDragging(true);
         }

         this.dragging = true;
      });
   }

   public void stopDragging() {
      this.positionModules.forEach((setting, module) -> setting.setDragging(false));
      this.dragging = false;
   }

   public static void init() {
      instance = new HudDragManager();
      EventBus.register(instance);
   }

   public static HudDragManager getInstance() {
      return instance;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
