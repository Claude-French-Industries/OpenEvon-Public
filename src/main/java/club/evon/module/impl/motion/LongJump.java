package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Direction.Axis;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.ClientTickEvent;
import club.evon.event.InputEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.manager.IncomingPacketManager;
import club.evon.manager.KeybindManager;
import club.evon.manager.OutgoingPacketManager;
import club.evon.manager.RotationManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;
import club.evon.util.net.PacketBlocker;




public final class LongJump extends BaseModule {
   private final PacketBlocker incomingBlocker;
   private final PacketBlocker outgoingBlocker;
   private int boostTicks;
   private int windupTicks;
   private boolean boosted;
   private static final long KEY = KeyedRandom.create(-7384214343184835666L, 4948546694156851612L, MethodHandles.lookup().lookupClass()).next(239271009507545L);
   private static final String[] STRINGS;
   private static final long INITIAL_TICKS;

   public LongJump() {
      super((STRINGS)[1], (STRINGS)[3], ModuleCategory.MOTION);

      long longValue = KEY ^ 115300415670187L;
      String[] strings = STRINGS;
      Scaffold.z();
      this.incomingBlocker = new PacketBlocker(IncomingPacketManager.getInstance());
      this.outgoingBlocker = new PacketBlocker(OutgoingPacketManager.getInstance());
      if (BaseModule.getStateArray() == null) {
         Scaffold.X_r2(new BaseModule[3]);
      }
      }

   @Override
   public void onEnable() {
      long longValue = KEY ^ 67252559026730L;
      this.boostTicks = (int)INITIAL_TICKS;
      this.windupTicks = 0;
      this.boosted = false;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.incomingBlocker.unblock();
      this.outgoingBlocker.unblock();
      super.onDisable();
   }

   @Subscribe
   private void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 72966895076939L;
      BaseModule[] values = Scaffold.z();
      if (!this.boosted) {
         Packet packet;
         label52: {
            Packet packet2 = event.getPacket();
            boolean tmp = packet2 instanceof EntityVelocityUpdateS2CPacket;
            if (values == null) {
               if (tmp) {
                  EntityVelocityUpdateS2CPacket packet3 = (EntityVelocityUpdateS2CPacket)packet2;
                  if (ClientContext.MINECRAFT_CLIENT.player == null || packet3.getEntityId() != ClientContext.MINECRAFT_CLIENT.player.getId()) {
                     return;
                  }

                  this.incomingBlocker.blockAll();
                  this.outgoingBlocker.blockAll();
                  this.boostTicks = 0;
                  this.boosted = true;
                  if (values == null) {
                     return;
                  }
               }

               packet2 = event.getPacket();
               packet = packet2;
               if (values != null) {
                  break label52;
               }

               tmp = packet2 instanceof EntityDamageS2CPacket;
            }

            if (!tmp) {
               return;
            }

            packet = packet2;
         }

         EntityDamageS2CPacket packet4 = (EntityDamageS2CPacket)packet;
         if (ClientContext.MINECRAFT_CLIENT.player != null && packet4.entityId() == ClientContext.MINECRAFT_CLIENT.player.getId()) {
            this.incomingBlocker.blockAll();
            this.outgoingBlocker.blockAll();
            this.boostTicks = 0;
            this.boosted = true;
         }
      }
   }

   @Subscribe
   private void onInput(InputEvent event) {
      long longValue = KEY ^ 68886570204197L;
      BaseModule[] values = Scaffold.z();
      int value = this.boosted ? 1 : 0;
      if (values == null) {
         if (this.boosted) {
            return;
         }

         value = this.windupTicks;
      }

      if (value == 2 && ClientContext.MINECRAFT_CLIENT.player != null && this.isFireball(ClientContext.MINECRAFT_CLIENT.player.getMainHandStack())) {
         KeybindManager.getUseState().queuePress();
      }
   }

   private boolean isFireball(ItemStack stack) {
      long longValue = KEY ^ 109678381705643L;
      BaseModule[] values = Scaffold.z();
      boolean empty = stack.isEmpty();
      if (values == null) {
         if (empty) {
            return false;
         }

         empty = Formatting.strip(stack.getFormattedName().getString()).contains(STRINGS[0]);
      }

      return values != null ? empty : empty;
   }

   @Subscribe
   private void onTick(ClientTickEvent event) {
      long longValue = KEY ^ 46758090260338L;
      BaseModule[] values = Scaffold.z();
      if (ClientContext.MINECRAFT_CLIENT.player != null) {
         int value = this.boosted ? 1 : 0;
         if (values == null) {
            if (!this.boosted) {
               this.windupTicks++;
               value = this.windupTicks;
               if (values == null) {
                  if (this.windupTicks > 2) {
                     return;
                  }

                  value = -1;
               }

               int value2 = value;
               int index = 0;

               label98: {
                  while (index < 9) {
                  ItemStack stack = ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(index);
                  if (values == null) {
                     boolean fireball = this.isFireball(stack);
                     if (values != null) {
                        break label98;
                     }

                     if (fireball) {
                           value2 = index;
                           if (values == null) {
                              break;
                           }
                        }

                        index++;
                     }

                     if (values != null) {
                        break;
                     }
                  }

                  value = value2;
               }

               if (value == -1) {
                  ChatUtil.sendError(this.getName() + STRINGS[2]);
                  this.setEnabled(false);
                  return;
               }

               SilentSlotManager.getInstance().switchTo(value2);
               RotationManager.getRotationController().rotateTo(new Vec2f(RotationManager.getMouseRotationManager().getYaw(ClientContext.MINECRAFT_CLIENT.player.getYaw()) + 180.0F, 70.0F), 360.0F);
               return;
            }

            this.boostTicks++;
            value = this.boostTicks;
         }

         byte byteValue = 5;
         if (values == null) {
               label127: {
                  if (value < 5) {
                     if (ClientContext.MINECRAFT_CLIENT.player.isOnGround()) {
                        ClientContext.MINECRAFT_CLIENT.player.jump();
                     }
                  }

                  value = this.boostTicks;
               }

            byteValue = 15;
         }

         if (values == null) {
            if (value == byteValue) {
               this.incomingBlocker.unblock();
               MovementUtil.setSpeed(2.0);
               ClientContext.MINECRAFT_CLIENT.player.setVelocity(ClientContext.MINECRAFT_CLIENT.player.getVelocity().withAxis(Axis.Y, 0.0));
            }

            value = this.boostTicks;
            byteValue = 20;
         }

         label136: {
            if (values == null) {
               if (value == byteValue) {
                  this.outgoingBlocker.unblock();
               }

               value = this.boostTicks;
               if (values != null) {
                  break label136;
               }

               byteValue = 20;
            }

            if (value < byteValue) {
               return;
            }

            if (ClientContext.MINECRAFT_CLIENT.player.isOnGround()) {
               this.setEnabled(false);
            }
         }
      }
   }

   static {
      try {
      long var5 = KEY ^ 47720639073390L;
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[4];
      int var12 = 0;
      String var11 = "\n\u009e\u00a5-\u00c8\u00f7\u00c1v\u009e\u00abD\u009a\u00deX\u00b99\u0010\u00e6\u001a\u00c5\u0015\u0086:\u00e5S\u009d\u00c5\u00fe\u00da\u00839\u00d20";
      int var13 = "\n\u009e\u00a5-\u00c8\u00f7\u00c1v\u009e\u00abD\u009a\u00deX\u00b99\u0010\u00e6\u001a\u00c5\u0015\u0086:\u00e5S\u009d\u00c5\u00fe\u00da\u00839\u00d20"
         .length();
      char var10 = 16;
      int var17 = -1;

      label37:
      while (true) {
         String var18 = var11.substring(++var17, var17 + var10);
         byte var10001 = -1;

         while (true) {
            byte[] var15 = var7.doFinal(var18.getBytes("ISO-8859-1"));
            String var26 = decodeUtf8(var15).intern();
            switch (var10001) {
               case 0:
                  var14[var12++] = var26;
                  if ((var17 += var10) >= var13) {
                     STRINGS = var14;
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long var2 = -4156515820564852650L;
                     byte[] var4 = var0.doFinal(
                        new byte[]{
                           (byte)(var2 >>> 56),
                           (byte)(var2 >>> 48),
                           (byte)(var2 >>> 40),
                           (byte)(var2 >>> 32),
                           (byte)(var2 >>> 24),
                           (byte)(var2 >>> 16),
                           (byte)(var2 >>> 8),
                           (byte)var2
                        }
                     );
                     long var30 = (var4[0] & 255L) << 56
                        | (var4[1] & 255L) << 48
                        | (var4[2] & 255L) << 40
                        | (var4[3] & 255L) << 32
                        | (var4[4] & 255L) << 24
                        | (var4[5] & 255L) << 16
                        | (var4[6] & 255L) << 8
                        | var4[7] & 255L;
                     var10001 = -1;
                     INITIAL_TICKS = var30;
                     break label37;
                  }

                  var10 = var11.charAt(var17);
                  break;
               default:
                  var14[var12++] = var26;
                  if ((var17 += var10) < var13) {
                     var10 = var11.charAt(var17);
                     continue label37;
                  }

                  var11 = "b s\u00e7\u0084\u001f\u00dd\u000brn\u0093\b\u008a!\u0004()\u00ff\u00be\u009e\u00d3\u00a88e\u00ba\u00f1\u00a8\rpu\u0086\u00de\u0081\u00a8\u00eb@\u00eb\u001dy-\u0018Y\u0097\u00b3_\u0090\u008e\u00ad\u00f8z\u0098\u00d8\u00dd\u00e4I\u001a\u000b;O\u00f9C\u0017\u001a\u00c0\u00c9";
                  var13 = "b s\u00e7\u0084\u001f\u00dd\u000brn\u0093\b\u008a!\u0004()\u00ff\u00be\u009e\u00d3\u00a88e\u00ba\u00f1\u00a8\rpu\u0086\u00de\u0081\u00a8\u00eb@\u00eb\u001dy-\u0018Y\u0097\u00b3_\u0090\u008e\u00ad\u00f8z\u0098\u00d8\u00dd\u00e4I\u001a\u000b;O\u00f9C\u0017\u001a\u00c0\u00c9"
                     .length();
                  var10 = '(';
                  var17 = -1;
            }

            var18 = var11.substring(++var17, var17 + var10);
            var10001 = 0;
         }
      }
   
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}

   private static JSONException rethrow(JSONException exception) {
      return exception;
   }

   private static String decodeUtf8(byte[] bytes) {
      int index = 0;
      int value;
      char[] chars = new char[value = bytes.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & bytes[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = bytes[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
