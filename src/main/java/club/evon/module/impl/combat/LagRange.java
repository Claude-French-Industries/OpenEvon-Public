package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.hypixel.data.type.GameType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.MathHelper;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.HypixelTargetFilter;
import club.evon.api.HypixelLocationManager;
import club.evon.data.HypixelLocation;
import club.evon.event.ClientTickEvent;
import club.evon.event.OutboundPacketEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.manager.OutgoingPacketManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.impl.motion.Scaffold;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.TargetsSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.RotationUtil;
import club.evon.util.net.PacketBlocker;




public final class LagRange extends BaseModule {
   private final DoubleSetting minRange;
   private final DoubleSetting maxRange;
   private final DoubleSetting delay;
   private final BooleanSetting swordOnly;
   private final TargetsSetting targets;
   private final PacketBlocker packetBlocker;
   private static final long KEY = KeyedRandom.create(3848888668947419599L, 5495971044139838018L, MethodHandles.lookup().lookupClass()).next(47792584135694L);
   private static final String[] STRINGS;

   public LagRange() {
      super((STRINGS)[1], (STRINGS)[5], ModuleCategory.COMBAT);

      String[] strings = STRINGS;
      this.minRange = new DoubleSetting(strings[0], 3.0, 0.0, 8.0, 0.1);
      this.maxRange = new DoubleSetting(strings[2], 3.0, 0.0, 8.0, 0.1);
      this.delay = new DoubleSetting(strings[6], 250.0, 50.0, 1000.0, 10.0);
      this.swordOnly = new BooleanSetting(strings[4], true);
      this.targets = new TargetsSetting();
      this.packetBlocker = new PacketBlocker(OutgoingPacketManager.getInstance());
      this.addSettings(this.minRange, this.maxRange, this.delay, this.swordOnly, this.targets);
      }

   @Override
   public void onDisable() {
      this.releasePackets();
      super.onDisable();
   }

   @Subscribe
   private void onOutboundPacket(OutboundPacketEvent event) {
      long longValue = KEY ^ 65702582192741L;
      String[] strings = KillAura.getStrings();
      boolean blocking = this.isBlocking();
      if (strings == null) {
         if (!blocking) {
            return;
         }

         blocking = this.isAttackPacket(event.getPacket());
      }

      if (!blocking) {
         return;
      }

      this.releasePackets();
   }

   private boolean isAttackPacket(Packet<?> packet) {
      long longValue = KEY ^ 117012491782910L;
      String[] strings = KillAura.getStrings();
      boolean tmp = packet instanceof PlayerInteractEntityC2SPacket;
      if (strings == null) {
         if (tmp) {
            PlayerInteractEntityC2SPacket player = (PlayerInteractEntityC2SPacket)packet;
            if (player.type == PlayerInteractEntityC2SPacket.ATTACK) {
               return true;
            }
         }

         tmp = packet instanceof PlayerActionC2SPacket;
      }

      if (strings == null) {
         if (tmp) {
            PlayerActionC2SPacket player2 = (PlayerActionC2SPacket)packet;
            if (player2.getAction() != Action.RELEASE_USE_ITEM) {
               return true;
            }
         }

         tmp = packet instanceof PlayerInteractItemC2SPacket;
      }

      if (strings == null) {
         if (!tmp) {
            return false;
         }

         tmp = this.isHoldingSword();
      }

      return strings != null ? tmp : !tmp;
   }

   private boolean isHoldingSword() {
      return SilentSlotManager.getInstance().getMainHandStack(ClientContext.MINECRAFT_CLIENT.player).isIn(ItemTags.SWORDS);
   }

   @Subscribe(h = -2)
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 82173651693687L;
      String[] strings = KillAura.getStrings();
      LagRange tmp = this;
      if (strings == null) {
         if (!this.shouldLag()) {
            this.releasePackets();
            return;
         }

         tmp = this;
      }

      List<LivingEntity> findTargets = tmp.targets.findTargets(this.maxRange.J());
      List<LivingEntity> entity = findTargets;
      if (strings == null) {
         if (findTargets.isEmpty()) {
            this.releasePackets();
            return;
         }

         entity = findTargets;
      }

      for (LivingEntity entity2 : entity) {
         label81: {
            double doubleValue = PlayerUtil.getDistanceToLivingEntity(entity2);
            double doubleValue2;
            int value = (doubleValue2 = doubleValue - this.minRange.J()) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);
            if (strings == null) {
               if (value < 0) {
                  break label81;
               }

               double doubleValue3;
               value = (doubleValue3 = doubleValue - this.maxRange.J()) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
            }

            if (strings == null) {
               if (value > 0) {
                  break label81;
               }

               if (this.packetBlocker.isBlocking()) {
                  break label81;
               }
            }

            if (strings == null) {
               float floatValue;
               value = (floatValue = MathHelper.angleBetween(ClientContext.MINECRAFT_CLIENT.player.getYaw(), RotationUtil.getRotationTo(entity2.getEyePos()).x) - 100.0F) == 0.0F ? 0 : (floatValue < 0.0F ? -1 : 1);
            }

            if (value <= 0) {
               this.blockFor(this.delay.J().longValue());
               if (strings == null) {
                  break;
               }
            }
         }

         if (strings != null) {
            break;
         }
      }
   }

   private void blockFor(long delayMs) {
      this.packetBlocker.blockFor(delayMs);
   }

   private boolean shouldLag() {
      long longValue = KEY ^ 73600032451198L;
      String[] strings = KillAura.getStrings();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (strings == null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return false;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (strings == null) {
         if (tmp.world == null) {
            return false;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      int value = tmp.player.age;
      if (strings == null) {
         if (tmp.player.age < 10) {
            return false;
         }

         value = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter ? 1 : 0;
      }

      if (strings == null) {
         if (value != 0) {
            HypixelLocation currentLocation = HypixelLocationManager.getInstance().getCurrentLocation();
            HypixelLocation hypixelLocation = currentLocation;
            if (strings == null) {
               if (currentLocation == null) {
                  return false;
               }

               hypixelLocation = currentLocation;
            }

            boolean lobby = hypixelLocation.hasLobby();
            if (strings != null) {
               return lobby;
            }

            if (lobby || currentLocation.getServerType() == GameType.REPLAY) {
               return false;
            }
         }

         value = this.swordOnly.I() ? 1 : 0;
      }

      label89:
      if (strings == null) {
         if (value != 0) {
            value = this.isHoldingSword() ? 1 : 0;
            if (strings != null) {
               break label89;
            }

            if (value == 0) {
               return false;
            }
         }

         value = EvonClient.getInstance().getModuleManager().getModuleByClass(Scaffold.class).isEnabled() ? 1 : 0;
      }

      if (strings == null) {
         if (value != 0) {
            return false;
         }

         value = ClientContext.MINECRAFT_CLIENT.interactionManager.isBreakingBlock() ? 1 : 0;
      }

      if (strings == null) {
         if (value != 0) {
            return false;
         }

         value = 1;
      }

      return value != 0;
   }

   private boolean isBlocking() {
      return this.packetBlocker.isBlocking();
   }

   private void releasePackets() {
      this.packetBlocker.unblock();
   }

   @Override
   public String getSuffix() {
      return this.delay.J().intValue() + STRINGS[3];
   }

   public DoubleSetting getMinRange() {
      return this.minRange;
   }

   public DoubleSetting getMaxRange() {
      return this.maxRange;
   }

   public DoubleSetting getDelay() {
      return this.delay;
   }

   public BooleanSetting getSwordOnly() {
      return this.swordOnly;
   }

   public TargetsSetting getTargets() {
      return this.targets;
   }

   public PacketBlocker getPacketBlocker() {
      return this.packetBlocker;
   }

   static {
      try {
      long var0 = KEY ^ 128842331257270L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[7];
      int var7 = 0;
      String var6 = "\u00e4\u00e7\u00fc\u008b\u00d7\u00fc\u00dc(\u0084\u00bcj\u009b\u00b1YXb\u0010T\f@\u0010\u0099\u00a8\u000f\u00dd\u00e60\u0097\u00ceD<i\u000b\u0010\u00a4\u00f2C\\\u0001\u00fcfBo\u00f5i\u00e7i\u00a2\u0083\u0011\b\u001a\u00a9\u000e7\u001d7\u0005\u0088\u0010\u008f3O\u00af\u00d6\u00ac\u00fcM\u0091\u00f9|\u00d6\u0010\u000f\u00dd\f";
      int var8 = "\u00e4\u00e7\u00fc\u008b\u00d7\u00fc\u00dc(\u0084\u00bcj\u009b\u00b1YXb\u0010T\f@\u0010\u0099\u00a8\u000f\u00dd\u00e60\u0097\u00ceD<i\u000b\u0010\u00a4\u00f2C\\\u0001\u00fcfBo\u00f5i\u00e7i\u00a2\u0083\u0011\b\u001a\u00a9\u000e7\u001d7\u0005\u0088\u0010\u008f3O\u00af\u00d6\u00ac\u00fcM\u0091\u00f9|\u00d6\u0010\u000f\u00dd\f"
         .length();
      char var5 = 16;
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var19;
                  if ((var12 += var5) >= var8) {
                     STRINGS = var9;
                     break label27;
                  }

                  var5 = var6.charAt(var12);
                  break;
               default:
                  var9[var7++] = var19;
                  if ((var12 += var5) < var8) {
                     var5 = var6.charAt(var12);
                     continue label27;
                  }

                  var6 = "c\u00ea\u0012\u00fe\u001ev\u00d4\u00a42\u00ac\u00c8?\u0096!#\u0090\u00a4gF\u00a1\u00d7a?\u001e\b\u00be\u00fdjUl\u00b0Rn";
                  var8 = "c\u00ea\u0012\u00fe\u001ev\u00d4\u00a42\u00ac\u00c8?\u0096!#\u0090\u00a4gF\u00a1\u00d7a?\u001e\b\u00be\u00fdjUl\u00b0Rn".length();
                  var5 = 24;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception varE) {
         throw new ExceptionInInitializerError(varE);
      }
   }

   private static JSONException identity(JSONException exception) {
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
}
