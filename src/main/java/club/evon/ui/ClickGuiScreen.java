package club.evon.ui;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.ClientTickEvent;
import club.evon.event.EventBus;
import club.evon.event.HudRenderEvent;
import club.evon.event.HudRenderPassEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.runtime.IndyRuntime;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.Stopwatch;




public final class ClickGuiScreen extends Screen {
   private final List<GuiComponent> panels = new ArrayList<>();
   private static final int q;
   private static final int d;
   private static final int c;
   private static final float PANEL_HEIGHT = 21.5F;
   private int lastMouseX;
   private int lastMouseY;
   private final Stopwatch animationTimer = new Stopwatch();
   private final List<GuiComponent> animationQueue = new ArrayList<>();
   private boolean closing;
   private static final long KEY = KeyedRandom.create(6796242070334583714L, 9004119485820509263L, MethodHandles.lookup().lookupClass()).next(5145139445783L);
   private static final long[] encryptedConstants;
   private static final Long[] decryptedConstants;
   private static final Map cipherCache;

   public ClickGuiScreen() {
      super(Text.of(""));
      EventBus.register(this);
   }

   public void init() {
      long longValue = KEY ^ 9866159025582L;
      String[] strings = AbstractGuiComponent.getStrings();
      super.init();
      String[] strings2 = strings;
      List list = this.panels;
      if (strings2 == null) {
         label44: {
            if (this.panels.isEmpty()) {
               ModuleCategory[] values = ModuleCategory.values();
               int length = values.length;
               int index = 0;

               while (index < length) {
                  ModuleCategory module = values[index];
                  this.panels.add(new CategoryPanel(module));
                  index++;
                  if (strings2 != null) {
                     break label44;
                  }

                  if (strings2 != null) {
                     BaseModule.setStateArray(new BaseModule[1]);
                     break;
                  }
               }
            }

            this.animationQueue.clear();
         }

         list = this.panels;
      }

      Iterator iterator = list.iterator();

      while (true) {
         if (iterator.hasNext()) {
            GuiComponent guiComponent = (GuiComponent)iterator.next();
            guiComponent.init();
            this.animationQueue.add(guiComponent);
            if (strings2 != null) {
               break;
            }

            if (strings2 == null) {
               continue;
            }
         }

         this.animationTimer.reset();
         this.closing = false;
         break;
      }
   }

   public void close() {
      super.close();
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   @Subscribe(h = -50)
   private void onHudRenderPass(HudRenderPassEvent event) throws Throwable {
      long longValue = KEY ^ 133655236295781L;
      String[] strings = AbstractGuiComponent.getStrings();
      if (ClientContext.MINECRAFT_CLIENT.currentScreen == this) {
         int value;
         boolean tmp;
         tmp = this.animationQueue.isEmpty();
         label133:
         if (strings == null) {
            label131:
            if (!tmp) {
               long longValue2 = 829963717616248228L ^ longValue;
               short shortValue = 17075;
               long longValue3 = ((Number)IndyRuntime.invoke(
                     "club.evon.ui.ClickGuiScreen",
                     "decryptLongSiteFactory",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "j",
                     "(IJ)J",
                     new Object[]{Integer.valueOf(shortValue), longValue2}
                  ))
                     .longValue();
               tmp = this.closing;
               label129:
               if (strings == null) {
                  label127:
                  if (!this.closing) {
                     if (strings == null) {
                        long longValue4 = 815377106393244506L ^ longValue;
                        short shortValue2 = 17484;
                        if (!this.animationTimer
                           .hasElapsed(
                              ((Number)IndyRuntime.invoke(
                                    "club.evon.ui.ClickGuiScreen",
                                    "decryptLongSiteFactory",
                                    "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                                    "j",
                                    "(IJ)J",
                                    new Object[]{Integer.valueOf(shortValue2), longValue4}
                                 ))
                                 .longValue(),
                              true
                           )) {
                           value = this.animationQueue.size();
                           if (strings != null) {
                              break label129;
                           }

                           if (value != ModuleCategory.values().length) {
                              break label127;
                           }
                        }

                        this.animationQueue.getFirst().onOpen();
                        this.animationQueue.removeFirst();
                     }

                     if (strings == null) {
                        break label131;
                     }
                  }

                  tmp = this.closing;
               }

               if (strings != null) {
                  break label133;
               }

               if (tmp) {
                  long longValue5 = 815377106393244506L ^ longValue;
                  short shortValue3 = 17484;
                  tmp = this.animationTimer
                     .hasElapsed(
                        ((Number)IndyRuntime.invoke(
                              "club.evon.ui.ClickGuiScreen",
                              "decryptLongSiteFactory",
                              "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                              "j",
                              "(IJ)J",
                              new Object[]{Integer.valueOf(shortValue3), longValue5}
                           ))
                           .longValue(),
                        true
                     );
                  if (strings != null) {
                     break label133;
                  }

                  if (tmp) {
                     this.animationQueue.getFirst().onClose();
                     this.animationQueue.removeFirst();
                  }
               }
            }
         }

         tmp = this.closing;
         value = tmp ? 1 : 0;

         label108:
         if (strings == null) {
            if (tmp) {
               int value2 = 1;

               label103: {
                  for (GuiComponent guiComponent : this.panels) {
                     tmp = guiComponent.isClosed();
                     if (strings != null) {
                        break label103;
                     }

                     if (tmp) {
                        continue;
                     }

                     value2 = 0;
                     break;
                  }

                  value = value2;
               }

               if (strings != null) {
                  break label108;
               }

               if (value != 0) {
                  ClientContext.MINECRAFT_CLIENT.player.closeScreen();
               }
            }

            value = this.width - this.panels.size() * 140 - 20;
         }

         float floatValue = value / 2.0F;

         for (GuiComponent guiComponent2 : this.panels) {
            guiComponent2.setX(floatValue);
            guiComponent2.setY(30.0F);
            guiComponent2.setWidth(120.0F);
            guiComponent2.setHeight(21.5F);
            guiComponent2.renderBackground(this.lastMouseX, this.lastMouseY);
            floatValue += 140.0F;
            if (strings != null) {
               break;
            }
         }
      }
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      this.lastMouseX = mouseX;
      this.lastMouseY = mouseY;
   }

   @Subscribe(h = -50)
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 87634439762856L;
      String[] strings = AbstractGuiComponent.getStrings();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (strings == null) {
         if (ClientContext.MINECRAFT_CLIENT.currentScreen != this) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      boolean tmp2;
      KeyBinding keyBinding = tmp.options.forwardKey;
      tmp2 = ClientContext.MINECRAFT_CLIENT.options.forwardKey.isPressed();
      label139:
      if (strings == null) {
         if (!tmp2) {
            tmp2 = PlayerUtil.isBindingPressed(ClientContext.MINECRAFT_CLIENT.options.forwardKey);
            if (strings != null) {
               break label139;
            }

            if (!tmp2) {
               tmp2 = false;
               break label139;
            }
         }

         tmp2 = true;
      }

      keyBinding.setPressed(tmp2);
      KeyBinding keyBinding2 = ClientContext.MINECRAFT_CLIENT.options.backKey;
      tmp2 = ClientContext.MINECRAFT_CLIENT.options.backKey.isPressed();
      label131:
      if (strings == null) {
         if (!tmp2) {
            tmp2 = PlayerUtil.isBindingPressed(ClientContext.MINECRAFT_CLIENT.options.backKey);
            if (strings != null) {
               break label131;
            }

            if (!tmp2) {
               tmp2 = false;
               break label131;
            }
         }

         tmp2 = true;
      }

      keyBinding2.setPressed(tmp2);
      KeyBinding keyBinding3 = ClientContext.MINECRAFT_CLIENT.options.leftKey;
      tmp2 = ClientContext.MINECRAFT_CLIENT.options.leftKey.isPressed();
      label123:
      if (strings == null) {
         if (!tmp2) {
            tmp2 = PlayerUtil.isBindingPressed(ClientContext.MINECRAFT_CLIENT.options.leftKey);
            if (strings != null) {
               break label123;
            }

            if (!tmp2) {
               tmp2 = false;
               break label123;
            }
         }

         tmp2 = true;
      }

      keyBinding3.setPressed(tmp2);
      KeyBinding keyBinding4 = ClientContext.MINECRAFT_CLIENT.options.rightKey;
      tmp2 = ClientContext.MINECRAFT_CLIENT.options.rightKey.isPressed();
      label115:
      if (strings == null) {
         if (!tmp2) {
            tmp2 = PlayerUtil.isBindingPressed(ClientContext.MINECRAFT_CLIENT.options.rightKey);
            if (strings != null) {
               break label115;
            }

            if (!tmp2) {
               tmp2 = false;
               break label115;
            }
         }

         tmp2 = true;
      }

      keyBinding4.setPressed(tmp2);
      KeyBinding keyBinding5 = ClientContext.MINECRAFT_CLIENT.options.sprintKey;
      tmp2 = ClientContext.MINECRAFT_CLIENT.options.sprintKey.isPressed();
      label107:
      if (strings == null) {
         if (!tmp2) {
            tmp2 = PlayerUtil.isBindingPressed(ClientContext.MINECRAFT_CLIENT.options.sprintKey);
            if (strings != null) {
               break label107;
            }

            if (!tmp2) {
               tmp2 = false;
               break label107;
            }
         }

         tmp2 = true;
      }

      keyBinding5.setPressed(tmp2);
      KeyBinding keyBinding6 = ClientContext.MINECRAFT_CLIENT.options.jumpKey;
      tmp2 = ClientContext.MINECRAFT_CLIENT.options.jumpKey.isPressed();
      label99:
      if (strings == null) {
         if (!tmp2) {
            tmp2 = PlayerUtil.isBindingPressed(ClientContext.MINECRAFT_CLIENT.options.jumpKey);
            if (strings != null) {
               break label99;
            }

            if (!tmp2) {
               tmp2 = false;
               break label99;
            }
         }

         tmp2 = true;
      }

      keyBinding6.setPressed(tmp2);
   }

   @Subscribe(h = -50)
   private void onHudRender(HudRenderEvent event) {
      long longValue = KEY ^ 103673622760735L;
      String[] strings = AbstractGuiComponent.getStrings();
      if (ClientContext.MINECRAFT_CLIENT.currentScreen == this) {
         float floatValue = (this.width - this.panels.size() * 140 - 20) / 2.0F;

         for (GuiComponent guiComponent : this.panels) {
            guiComponent.setX(floatValue);
            guiComponent.setY(30.0F);
            guiComponent.setWidth(120.0F);
            guiComponent.setHeight(21.5F);
            guiComponent.render(this.lastMouseX, this.lastMouseY);
            floatValue += 140.0F;
            if (strings != null) {
               break;
            }
         }
      }
   }

   public boolean mouseClicked(Click click, boolean doubled) {
      long longValue = KEY ^ 117542376213779L;
      int value = click.button();
      String[] strings = AbstractGuiComponent.getStrings();
      double doubleValue = click.x();
      String[] strings2 = strings;
      double doubleValue2 = click.y();
      boolean flag;
      Iterator iterator = this.panels.iterator();

      while (true) {
         if (iterator.hasNext()) {
            GuiComponent guiComponent = (GuiComponent)iterator.next();
            GuiComponent guiComponent2 = guiComponent;
            double doubleValue3 = doubleValue;
            double doubleValue4 = doubleValue2;
            if (strings2 == null) {
               flag = guiComponent.isHovered(doubleValue, doubleValue2);
               if (strings2 != null) {
                  break;
               }

               if (!flag && strings2 == null) {
                  continue;
               }

               guiComponent2 = guiComponent;
               doubleValue3 = doubleValue;
               doubleValue4 = doubleValue2;
            }

            guiComponent2.mouseClicked(doubleValue3, doubleValue4, value);
            if (strings2 == null) {
               continue;
            }
         }

         flag = super.mouseClicked(click, doubled);
         break;
      }

      return flag;
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      long longValue = KEY ^ 22368889824552L;
      String[] strings = AbstractGuiComponent.getStrings();
      Iterator iterator = this.panels.iterator();
      String[] strings2 = strings;
      boolean flag;

      while (true) {
         if (iterator.hasNext()) {
            GuiComponent guiComponent = (GuiComponent)iterator.next();
            GuiComponent guiComponent2 = guiComponent;
            double doubleValue = mouseX;
            if (strings2 == null) {
               flag = guiComponent.isHovered(mouseX, mouseY);
               if (strings2 != null) {
                  break;
               }

               if (!flag && strings2 == null) {
                  continue;
               }

               guiComponent2 = guiComponent;
               doubleValue = verticalAmount;
            }

            guiComponent2.mouseScrolled((float)doubleValue);
            if (strings2 == null) {
               continue;
            }
         }

         flag = super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
         break;
      }

      return flag;
   }

   public boolean mouseReleased(Click click) {
      long longValue = KEY ^ 24578828995984L;
      String[] strings = AbstractGuiComponent.getStrings();
      int value = click.button();
      String[] strings2 = strings;
      double doubleValue = click.x() * 2.0;
      double doubleValue2 = click.y() * 2.0;

      for (GuiComponent guiComponent : this.panels) {
         guiComponent.mouseReleased(doubleValue, doubleValue2, value);
         if (strings2 != null) {
            break;
         }
      }

      boolean mouseReleased = super.mouseReleased(click);
      if (BaseModule.getStateArray() == null) {
         AbstractGuiComponent.setStrings(new String[5]);
      }

      return mouseReleased;
   }

   public boolean keyPressed(KeyInput input) {
      long longValue = KEY ^ 55680090453037L;
      String[] strings = AbstractGuiComponent.getStrings();
      int value = input.key();
      if (strings != null) {
         return value != 0;
      }

      if (value != 256) {
         for (GuiComponent guiComponent : this.panels) {
            guiComponent.keyPressed(value);
            if (strings != null) {
               break;
            }
         }

         return super.keyPressed(input);
      }

      if (!this.closing) {
         this.animationQueue.clear();
         this.animationQueue.addAll(this.panels.reversed());
         this.closing = true;
      }

      return false;
   }

   public boolean shouldPause() {
      return false;
   }

   static {
      try {
      long var12 = KEY ^ 24776816571298L;
      Cipher var14;
      Cipher var10000 = var14 = Cipher.getInstance("DES/CBC/NoPadding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var12 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var15 = 1; var15 < 8; var15++) {
         var10003[var15] = (byte)(var12 << var15 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long[] var11 = new long[3];
      int var17 = 0;
      String var18 = "\u00e3{\u00d5\u00d3\u0093\u001a\u00f7\u00f8\u0007\u00fd\u00a8\u00cf\u00dc\u00b1\u000f\u00f0\u0086<\u00a4\u0089\u0010D\u0099Z";
      int var19 = "\u00e3{\u00d5\u00d3\u0093\u001a\u00f7\u00f8\u0007\u00fd\u00a8\u00cf\u00dc\u00b1\u000f\u00f0\u0086<\u00a4\u0089\u0010D\u0099Z".length();
      int var16 = 0;

      do {
         int var10001 = var16;
         var16 += 8;
         byte[] var20 = var18.substring(var10001, var16).getBytes("ISO-8859-1");
         var10001 = var17++;
         long var21 = (var20[0] & 255L) << 56
            | (var20[1] & 255L) << 48
            | (var20[2] & 255L) << 40
            | (var20[3] & 255L) << 32
            | (var20[4] & 255L) << 24
            | (var20[5] & 255L) << 16
            | (var20[6] & 255L) << 8
            | var20[7] & 255L;
         byte[] var23 = var14.doFinal(
            new byte[]{
               (byte)(var21 >>> 56),
               (byte)(var21 >>> 48),
               (byte)(var21 >>> 40),
               (byte)(var21 >>> 32),
               (byte)(var21 >>> 24),
               (byte)(var21 >>> 16),
               (byte)(var21 >>> 8),
               (byte)var21
            }
         );
         long var10004 = (var23[0] & 255L) << 56
            | (var23[1] & 255L) << 48
            | (var23[2] & 255L) << 40
            | (var23[3] & 255L) << 32
            | (var23[4] & 255L) << 24
            | (var23[5] & 255L) << 16
            | (var23[6] & 255L) << 8
            | var23[7] & 255L;
         byte var29 = -1;
         var11[var10001] = var10004;
      } while (var16 < var19);

      q = (int)var11[0];
      c = (int)var11[1];
      d = (int)var11[2];
      cipherCache = new HashMap(13);
      Cipher var0;
      var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
      var10002 = SecretKeyFactory.getInstance("DES");
      var10003 = new byte[]{(byte)(var12 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var1 = 1; var1 < 8; var1++) {
         var10003[var1] = (byte)(var12 << var1 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long[] var6 = new long[2];
      int var3 = 0;
      String var4 = "W(\u00aeX\u0015\u0018\u0089\u00ee\u00e9\u00e6\u00cf\bh($\u0016";
      int var5 = "W(\u00aeX\u0015\u0018\u0089\u00ee\u00e9\u00e6\u00cf\bh($\u0016".length();
      int var2 = 0;

      do {
         int var26 = var2;
         var2 += 8;
         byte[] var7 = var4.substring(var26, var2).getBytes("ISO-8859-1");
         var26 = var3++;
         long var8 = (var7[0] & 255L) << 56
            | (var7[1] & 255L) << 48
            | (var7[2] & 255L) << 40
            | (var7[3] & 255L) << 32
            | (var7[4] & 255L) << 24
            | (var7[5] & 255L) << 16
            | (var7[6] & 255L) << 8
            | var7[7] & 255L;
         byte[] var10 = var0.doFinal(
            new byte[]{
               (byte)(var8 >>> 56),
               (byte)(var8 >>> 48),
               (byte)(var8 >>> 40),
               (byte)(var8 >>> 32),
               (byte)(var8 >>> 24),
               (byte)(var8 >>> 16),
               (byte)(var8 >>> 8),
               (byte)var8
            }
         );
         long var32 = (var10[0] & 255L) << 56
            | (var10[1] & 255L) << 48
            | (var10[2] & 255L) << 40
            | (var10[3] & 255L) << 32
            | (var10[4] & 255L) << 24
            | (var10[5] & 255L) << 16
            | (var10[6] & 255L) << 8
            | var10[7] & 255L;
         byte var31 = -1;
         var6[var26] = var32;
      } while (var2 < var5);

      encryptedConstants = var6;
      decryptedConstants = new Long[2];
      } catch (Throwable var1000) {
         throw rethrow(var1000);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }

   private static long decryptLong(int index, long key) {
      int value = index ^ (int)(key & 32767L) ^ 12943;
      if (decryptedConstants[value] == null) {
         byte[] bytes = new byte[]{
            (byte)(key >>> 56),
            (byte)(key >>> 48),
            (byte)(key >>> 40),
            (byte)(key >>> 32),
            (byte)(key >>> 24),
            (byte)(key >>> 16),
            (byte)(key >>> 8),
            (byte)key
         };
         long longValue = encryptedConstants[value];
         byte[] bytes2 = new byte[]{
            (byte)(longValue >>> 56),
            (byte)(longValue >>> 48),
            (byte)(longValue >>> 40),
            (byte)(longValue >>> 32),
            (byte)(longValue >>> 24),
            (byte)(longValue >>> 16),
            (byte)(longValue >>> 8),
            (byte)longValue
         };
         Long threadId = Thread.currentThread().threadId();
         Object[] values = (Object[])cipherCache.get(threadId);

         byte[] bytes3;
         try {
            if (values == null) {
               values = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
               cipherCache.put(threadId, values);
            }

            DESKeySpec dESKeySpec = new DESKeySpec(bytes);
            SecretKey generateSecret = ((SecretKeyFactory)values[1]).generateSecret(dESKeySpec);
            Cipher cipher = (Cipher)values[0];
            cipher.init(2, generateSecret, (IvParameterSpec)values[2]);
            bytes3 = cipher.doFinal(bytes2);
         } catch (Exception exception) {
            throw new RuntimeException("club/evon/zn", exception);
         }

         long longValue2 = (bytes3[0] & 255L) << 56
            | (bytes3[1] & 255L) << 48
            | (bytes3[2] & 255L) << 40
            | (bytes3[3] & 255L) << 32
            | (bytes3[4] & 255L) << 24
            | (bytes3[5] & 255L) << 16
            | (bytes3[6] & 255L) << 8
            | bytes3[7] & 255L;
         decryptedConstants[value] = longValue2;
      }

      return decryptedConstants[value];
   }

   private static long decryptLongBootstrap(Lookup lookup, MutableCallSite callSite, String name, Object[] args) {
      int value = (Integer)args[0];
      long longValue = (Long)args[1];
      long longValue2 = decryptLong(value, longValue);
      MethodHandle constant = MethodHandles.constant(long.class, longValue2);
      callSite.setTarget(MethodHandles.dropArguments(constant, 0, int.class, long.class));
      return longValue2;
   }

   private static CallSite decryptLongSiteFactory(Lookup lookup, String name, MethodType type) {
      MutableCallSite mutableCallSite = new MutableCallSite(type);

      try {
         MethodHandle findStatic = MethodHandles.lookup()
            .findStatic(
               ClickGuiScreen.class,
               "decryptLongBootstrap",
               MethodType.methodType(long.class, Lookup.class, MutableCallSite.class, String.class, Object[].class)
            );
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(findStatic.asCollector(Object[].class, type.parameterCount()), 0, lookup, mutableCallSite, name), type
            )
         );
         return mutableCallSite;
      } catch (Exception exception) {
         throw new RuntimeException("club/evon/zn" + " : " + name + " : " + type.toString(), exception);
      }
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException rethrow(Throwable exception) throws T {
      throw (T)exception;
   }
}
