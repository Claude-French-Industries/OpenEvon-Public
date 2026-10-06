package club.evon.antibot;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.scoreboard.Team;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.HypixelTargetFilter;
import club.evon.api.ServerProfile;
import club.evon.event.JoinWorldEvent;
import club.evon.event.Subscribe;
import club.evon.util.KeyedRandom;




public final class MinemenAntibot extends ServerProfile {
   private final List<LivingEntity> trackedEntities = new ArrayList<>();
   private static final long KEY = KeyedRandom.create(1350053700175325417L, -7632852286434832895L, MethodHandles.lookup().lookupClass()).next(185989725561251L);
   private static final String SERVER_NAME;

   public MinemenAntibot() {
      super(SERVER_NAME);
   }

   @Subscribe
   private void onJoinWorld(JoinWorldEvent event) {
      this.trackedEntities.clear();
   }

   @Override
   public boolean isValidTarget(LivingEntity livingEntity) {
      long longValue = KEY ^ 105876499842146L;
      int value = HypixelTargetFilter.M();
      LivingEntity tmp = livingEntity;
      if (value == 0) {
         if (livingEntity instanceof ArmorStandEntity) {
            return false;
         }

         tmp = livingEntity;
      }

      boolean flag = false;
      label125: {
         String string = tmp.getName().getString();
         String text = string;
         if (value == 0) {
            if (string == null) {
               return true;
            }

            text = string;
         }

         boolean empty = text.isEmpty();
         if (value != 0) {
            return empty;
         }

         if (!empty) {
            flag = string.contains("-");
            if (value != 0) {
               break label125;
            }

            if (!flag) {
               flag = livingEntity instanceof OtherClientPlayerEntity;
               break label125;
            }
         }

         return true;
      }

      if (value == 0) {
         if (flag) {
            OtherClientPlayerEntity player = (OtherClientPlayerEntity)livingEntity;
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

            boolean equals = team.getName().equals(player.getName().getString());
            if (value != 0) {
               return equals;
            }

            if (!equals) {
               return true;
            }

            return false;
         }

         flag = true;
      }

      return flag;
   }

   static {
      try {
      long var0 = KEY ^ 11098971828494L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var4 = var2.doFinal("\u009b\u00f1hM\u00dbo\u00e5T".getBytes("ISO-8859-1"));
      String var5 = decodeUtf8(var4).intern();
      byte var10001 = -1;
      SERVER_NAME = var5;
      } catch (Exception var10004) {
         throw new RuntimeException("club/evon/antibot/MinemenAntibot", var10004);
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
