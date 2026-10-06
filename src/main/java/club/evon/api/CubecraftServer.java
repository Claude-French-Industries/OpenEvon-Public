package club.evon.api;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.HypixelTargetFilter;
import club.evon.util.KeyedRandom;




public final class CubecraftServer extends ServerProfile {
   private static final long KEY = KeyedRandom.create(-2288374518554289615L, -9172913715433016032L, MethodHandles.lookup().lookupClass()).next(264286634240776L);
   private static final String SERVER_NAME;

   public CubecraftServer() {
      super(SERVER_NAME);
   }

   @Override
   public boolean isValidTarget(LivingEntity livingEntity) {
      long longValue = KEY ^ 131048157310039L;
      int value = HypixelTargetFilter.M();
      boolean tmp = livingEntity instanceof ArmorStandEntity;
      if (value == 0) {
         if (tmp) {
            return false;
         }

         tmp = livingEntity instanceof PlayerEntity;
      }

      if (value == 0) {
         if (tmp) {
            PlayerEntity player = (PlayerEntity)livingEntity;
            PlayerListEntry playerListEntry = ClientContext.MINECRAFT_CLIENT.getNetworkHandler().getPlayerListEntry(player.getUuid());
            PlayerListEntry player2 = playerListEntry;
            if (value == 0) {
               if (playerListEntry == null) {
                  return false;
               }

               player2 = playerListEntry;
            }

            if (value == 0) {
               if (player2.getProfile() == null) {
                  return false;
               }

               player2 = playerListEntry;
            }

            Team scoreboardTeam = player2.getScoreboardTeam();
            Team team = scoreboardTeam;
            if (value == 0) {
               if (scoreboardTeam == null) {
                  return false;
               }

               team = scoreboardTeam;
            }

            tmp = team.getName().equals(player.getName().getString());
            if (value != 0) {
               return tmp;
            }

            if (!tmp) {
               return true;
            }

            return false;
         }

         tmp = true;
      }

      return tmp;
   }

   static {
      try {
      long var0 = KEY ^ 139505103704478L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var4 = var2.doFinal("\u001b\u00dd\u00d7\u008f\u00a9\u009bx\u00be\u00f9\u0092\u00f5\u00cc\u0091D\u009a\u0018".getBytes("ISO-8859-1"));
      String var5 = decodeUtf8(var4).intern();
      byte var10001 = -1;
      SERVER_NAME = var5;
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
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
