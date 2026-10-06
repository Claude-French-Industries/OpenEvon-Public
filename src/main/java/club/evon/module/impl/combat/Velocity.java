package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.HypixelTargetFilter;
import club.evon.api.HypixelLocationManager;
import club.evon.data.HypixelLocation;
import club.evon.manager.ClientState;
import club.evon.manager.ModuleManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.SubMode;
import club.evon.module.impl.motion.Flight;
import club.evon.setting.ModeSetting;
import club.evon.util.KeyedRandom;




public final class Velocity extends BaseModule {
   private final ModeSetting mode;
   private static final long KEY = KeyedRandom.create(-83002513257894001L, -2289527869585906092L, MethodHandles.lookup().lookupClass()).next(67764790372985L);
   private static final String[] STRINGS;

   public Velocity() {
      super((STRINGS)[4], (STRINGS)[3], ModuleCategory.COMBAT);

      String[] strings = STRINGS;
      this.mode = new ModeSetting(strings[5], new VelocityVanillaMode(strings[2], this), new VelocityMinemenMode(strings[0], this), new VelocityHypixelMode(strings[1], this));
      this.addSettings(this.mode);
      }

   public boolean shouldCancel() {
      long longValue = KEY ^ 118500625196347L;
      String[] strings = KillAura.getStrings();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (strings == null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return true;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (tmp.world != null) {
         ModuleManager moduleManager = EvonClient.getInstance().getModuleManager();
         boolean enabled = moduleManager.getModuleByClass(Flight.class).isEnabled();
         if (strings == null) {
            if (!enabled) {
               HypixelLocation currentLocation = HypixelLocationManager.getInstance().getCurrentLocation();
               if (ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter) {
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

                  if (lobby) {
                     return true;
                  }
               }

               return false;
            }

            enabled = true;
         }

         return enabled;
      } else {
         return true;
      }
   }

   @Override
   public String getSuffix() {
      return this.mode.getValue().getName();
   }

   public SubMode<?> getMode() {
      return this.mode.getValue();
   }

   static {
      try {
      long var0 = KEY ^ 45513403795768L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[6];
      int var7 = 0;
      String var6 = "\u00ebA\u000f\u00e9G\u0098\u00d2W\bf\u00cb\u00b0\u009ap\u00a5S\u0087\bEK\u0014\u00e8@p\u00f2\u0096 G\u00bf\u0095\u00ee\u0085\u00ee\u00e7\u00dd\u00a6_\u00b1\u00f8 \u00b8\u00d8\u009e\u0018\u00fb\u00b9Bh'\u0097s5\u009a*O\u00f8_\u008d\u008f";
      int var8 = "\u00ebA\u000f\u00e9G\u0098\u00d2W\bf\u00cb\u00b0\u009ap\u00a5S\u0087\bEK\u0014\u00e8@p\u00f2\u0096 G\u00bf\u0095\u00ee\u0085\u00ee\u00e7\u00dd\u00a6_\u00b1\u00f8 \u00b8\u00d8\u009e\u0018\u00fb\u00b9Bh'\u0097s5\u009a*O\u00f8_\u008d\u008f"
         .length();
      char var5 = '\b';
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

                  var6 = "o*]\u00c6W\u00a5/\u008a\u00d5\u00c0\u00abv\u00e1~\u0002\u00d6\b\u0099\u00d1$\u009a\u00e4\u00dd\u0084\u00ed";
                  var8 = "o*]\u00c6W\u00a5/\u008a\u00d5\u00c0\u00abv\u00e1~\u0002\u00d6\b\u0099\u00d1$\u009a\u00e4\u00dd\u0084\u00ed".length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
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
