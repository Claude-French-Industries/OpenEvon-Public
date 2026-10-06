package club.evon.manager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import club.evon.ClientContext;
import club.evon.ClientService;
import club.evon.config.ConfigManager;
import club.evon.util.AesEncryption;
import club.evon.util.KeyedRandom;




public final class FriendManager implements ClientService {
   private final List<String> friends = new ArrayList<>();
   public static FriendManager instance;
   private static final long KEY = KeyedRandom.create(-1142650502126622993L, 2087992797001403969L, MethodHandles.lookup().lookupClass()).next(75537303633683L);
   private static final String[] STRINGS;

   public void save() {
      String[] strings = STRINGS;
      File file = new File(ClientContext.CLIENT_DIR, strings[1]);

      try {
         file.createNewFile();
         JsonObject jsonObject = new JsonObject();
         JsonArray jsonArray = new JsonArray();
         this.friends.forEach(jsonArray::add);
         jsonObject.add(strings[2], jsonArray);
         String text = strings[4];
         String encrypt = AesEncryption.encrypt(strings[4], ClientContext.GSON.toJson(jsonObject));

         try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false))) {
            bufferedWriter.write(encrypt);
         }
      } catch (Exception exception) {
         exception.fillInStackTrace();
      }
   }

   public void load() {
      long longValue = KEY ^ 67287861349829L;
      String[] strings = STRINGS;
      File file = new File(ClientContext.CLIENT_DIR, strings[0]);
      if (file.exists()) {
         try {
            StringBuilder builder = new StringBuilder();

            String text;
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
               while ((text = bufferedReader.readLine()) != null) {
                  builder.append(text);
               }
            }

            String text2;
            try {
               strings = STRINGS;
               text = strings[5];
               text2 = AesEncryption.decrypt(strings[4], builder.toString());
            } catch (Exception exception) {
               return;
            }

            JsonObject jsonObject = (JsonObject)ClientContext.GSON.fromJson(text2, JsonObject.class);
            if (jsonObject == null) {
               return;
            }

            jsonObject.get(STRINGS[3]).getAsJsonArray().forEach(var1x -> this.friends.add(var1x.getAsString()));
         } catch (Exception exception2) {
            exception2.fillInStackTrace();
         }
      }
   }

   public void addFriend(String name) {
      long longValue = KEY ^ 16722559194003L;
      String flowGuard = ClientState.getFlowGuard();
      if (flowGuard == null && !this.friends.contains(name.toLowerCase())) {
         this.friends.add(name.toLowerCase());
         // Friends live in their own file, but the debounced autosave flushes them along with
         // the active config so a session that never shuts down cleanly cannot lose them.
         ConfigManager.markDirty();
      }
   }

   public void removeFriend(String string) {
      if (this.friends.remove(string.toLowerCase())) {
         ConfigManager.markDirty();
      }
   }

   /** Used by ".friend clear"; the raw list is also reachable through {@link #getFriends()}. */
   public void clearFriends() {
      if (!this.friends.isEmpty()) {
         this.friends.clear();
         ConfigManager.markDirty();
      }
   }

   public boolean isFriend(String string) {
      return this.friends.contains(string.toLowerCase());
   }

   public static void init() {
      instance = new FriendManager();
   }

   public List<String> getFriends() {
      return this.friends;
   }

   public static FriendManager getInstance() {
      return instance;
   }

   static {
      try {
      long var0 = KEY ^ 45328041178527L;
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
      String var6 = "\u00f1)\u00e8\u00a0iJ\u00f2\u007fF\u00cb\u00f1\fI\u0017P\u00f7\u0010\u00f1)\u00e8\u00a0iJ\u00f2\u007fF\u00cb\u00f1\fI\u0017P\u00f7\bDF\u00f8\u00bf\u00c9\u0097\u0090v\bDF\u00f8\u00bf\u00c9\u0097\u0090v";
      int var8 = "\u00f1)\u00e8\u00a0iJ\u00f2\u007fF\u00cb\u00f1\fI\u0017P\u00f7\u0010\u00f1)\u00e8\u00a0iJ\u00f2\u007fF\u00cb\u00f1\fI\u0017P\u00f7\bDF\u00f8\u00bf\u00c9\u0097\u0090v\bDF\u00f8\u00bf\u00c9\u0097\u0090v"
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

                  var6 = "\u0084z\t;$\u00e5\u00ce\u0097asl[@\u00fau\u00c3\u0083JL\u00db\u00a0M\u00e1\u00e8d4\u0080\u00e7\u0087N\u00b4j\u00ce\u001e\u00b6\u0007\u00a3c\u00aeC(\u0084z\t;$\u00e5\u00ce\u0097asl[@\u00fau\u00c3\u0083JL\u00db\u00a0M\u00e1\u00e8d4\u0080\u00e7\u0087N\u00b4j\u00ce\u001e\u00b6\u0007\u00a3c\u00aeC";
                  var8 = "\u0084z\t;$\u00e5\u00ce\u0097asl[@\u00fau\u00c3\u0083JL\u00db\u00a0M\u00e1\u00e8d4\u0080\u00e7\u0087N\u00b4j\u00ce\u001e\u00b6\u0007\u00a3c\u00aeC(\u0084z\t;$\u00e5\u00ce\u0097asl[@\u00fau\u00c3\u0083JL\u00db\u00a0M\u00e1\u00e8d4\u0080\u00e7\u0087N\u00b4j\u00ce\u001e\u00b6\u0007\u00a3c\u00aeC"
                     .length();
                  var5 = '(';
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

   private static Throwable identity(Throwable throwable) {
      return throwable;
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
