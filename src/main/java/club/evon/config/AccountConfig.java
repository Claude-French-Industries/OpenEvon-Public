package club.evon.config;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import com.mojang.logging.LogUtils;
import net.minecraft.client.session.Session;
import org.slf4j.Logger;
import club.evon.ClientContext;
import club.evon.api.LoginType;
import club.evon.module.BaseModule;
import club.evon.ui.AltManagerScreen;
import club.evon.util.AesEncryption;
import club.evon.util.KeyedRandom;




public class AccountConfig {
   private final JsonObject accounts;
   private static final File ACCOUNTS_FILE;
   private static String[] stateArray;
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final long STRING_SEED = KeyedRandom.create(6539205575775434221L, -1725987794627875826L, MethodHandles.lookup().lookupClass()).next(234683605392071L);
   private static final String[] STRINGS_TABLE;

   public AccountConfig() {
      super();

      long longValue = STRING_SEED ^ 99941820891313L;
      // $VF: rewritten. The decompiled constructor threw on any IO error and only ever tried to
      // decrypt the file - while addAccount() wrote it as *plaintext*, so loading threw, the
      // exception was swallowed and every restart came up with an empty alt list. Loading is now
      // failure tolerant and accepts both the encrypted and the legacy plaintext layout.
      this.accounts = loadAccounts();
   }

   /** Reads the alt store; never throws, returns an empty store when the file is unusable. */
   private static JsonObject loadAccounts() {
      JsonObject accounts = new JsonObject();
      if (!ACCOUNTS_FILE.exists()) {
         return accounts;
      }

      String text;
      try {
         StringBuilder builder = new StringBuilder();
         try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
               builder.append(line);
            }
         }

         text = builder.toString().trim();
      } catch (Exception exception) {
         LOGGER.error("[Evon] Could not read {}", ACCOUNTS_FILE, exception);
         return accounts;
      }

      if (text.isEmpty()) {
         return accounts;
      }

      String decrypted = null;
      try {
         decrypted = AesEncryption.decrypt(STRINGS_TABLE[0], text);
      } catch (Exception exception) {
         // Not encrypted: a pre-fix build wrote the store as plain JSON.
      }

      JsonObject parsed = parseAccounts(decrypted);
      if (parsed == null) {
         parsed = parseAccounts(text);
      }

      if (parsed == null) {
         LOGGER.error("[Evon] {} is not a readable account store; starting with an empty list", ACCOUNTS_FILE);
         return accounts;
      }

      return parsed;
   }

   private static JsonObject parseAccounts(String json) {
      if (json == null || json.isBlank()) {
         return null;
      }

      try {
         JsonElement jsonElement = JsonParser.parseString(json);
         return jsonElement != null && jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;
      } catch (Exception exception) {
         return null;
      }
   }

   public void addAccount(Session session, LoginType loginType) {
      long longValue = STRING_SEED ^ 85927092200120L;
      String[] strings = getStateArray();
      if (!this.accounts.has(session.getUsername())) {
         JsonArray jsonArray = new JsonArray();
         jsonArray.add(session.getUsername());
         jsonArray.add(session.getAccessToken());
         jsonArray.add(session.getUuidOrNull().toString());
         jsonArray.add(loginType.getLevel());
         this.accounts.add(session.getUsername(), jsonArray);
         // Write the store before touching the UI, in the same encrypted format the loader and
         // removeAccount() use, so the account survives even if the screen cannot be built yet.
         this.saveAccounts();

         try {
            AltManagerScreen.getInstance().addAccount(jsonArray);
         } catch (Throwable throwable) {
            LOGGER.error("[Evon] Could not update the alt manager screen", throwable);
         }

         if (BaseModule.getStateArray() == null) {
            setStateArray(new String[5]);
         }
      }
   }

   public void removeAccount(String username) {
      long longValue = STRING_SEED ^ 105968964283520L;
      String[] strings = getStateArray();
      JsonObject tmp = this.accounts;
      String tmp2 = username;
      if (strings != null) {
         if (!this.accounts.has(username)) {
            return;
         }

         tmp = this.accounts;
         tmp2 = username;
      }

      tmp.remove(tmp2);
      this.saveAccounts();

      try {
         AltManagerScreen.getInstance().removeAccount(username);
      } catch (Throwable throwable) {
         LOGGER.error("[Evon] Could not update the alt manager screen", throwable);
      }
   }

   /** Encrypts and writes the whole store; failures are logged, never propagated. */
   private void saveAccounts() {
      try {
         String encrypt = AesEncryption.encrypt(STRINGS_TABLE[0], this.accounts.toString());

         try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, false))) {
            bufferedWriter.write(encrypt);
         }
      } catch (Exception exception) {
         LOGGER.error("[Evon] Could not write {}", ACCOUNTS_FILE, exception);
      }
   }

   public JsonObject getAccounts() {
      return this.accounts;
   }

   static {
      try {
      String[] var10000 = new String[2];
      long var0 = STRING_SEED ^ 6403962651358L;
      setStateArray(var10000);
      Cipher var2;
      Cipher var14 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var14.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[4];
      int var7 = 0;
      String var6 = "K\u009a\u0096n{_X\u001b\u0090\u00ec\u00c9C&\u009f;\u00ce&9(\u00cd\u00d2\u0004\u0080\u00c3\u00a0\u00b85\u0092KB\u00ae\t\n\u0003\u00c7\u00e6\u00fc\u00d90=\u0010\u00bb\u00ec\u00bd\u0013\u00fc\u00b5\u0006pa\u00e4\u0082\\\u009el\u00cc\u00ff";
      int var8 = "K\u009a\u0096n{_X\u001b\u0090\u00ec\u00c9C&\u009f;\u00ce&9(\u00cd\u00d2\u0004\u0080\u00c3\u00a0\u00b85\u0092KB\u00ae\t\n\u0003\u00c7\u00e6\u00fc\u00d90=\u0010\u00bb\u00ec\u00bd\u0013\u00fc\u00b5\u0006pa\u00e4\u0082\\\u009el\u00cc\u00ff"
         .length();
      char var5 = '(';
      int var13 = -1;

      label27:
      while (true) {
         String var15 = var6.substring(++var13, var13 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var15.getBytes("ISO-8859-1"));
            String var21 = decryptString(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var21;
                  if ((var13 += var5) >= var8) {
                     STRINGS_TABLE = var9;
                     String[] var11 = STRINGS_TABLE;
                     ACCOUNTS_FILE = new File(new File(ClientContext.MINECRAFT_CLIENT.runDirectory, var11[3]), var11[1]);
                     break label27;
                  }

                  var5 = var6.charAt(var13);
                  break;
               default:
                  var9[var7++] = var21;
                  if ((var13 += var5) < var8) {
                     var5 = var6.charAt(var13);
                     continue label27;
                  }

                  var6 = "K\u009a\u0096n{_X\u001b\u0090\u00ec\u00c9C&\u009f;\u00ce&9(\u00cd\u00d2\u0004\u0080\u00c3\u00a0\u00b85\u0092KB\u00ae\t\n\u0003\u00c7\u00e6\u00fc\u00d90=\b\n\u00f4\u00d5\u000e\u0088\u0095OD";
                  var8 = "K\u009a\u0096n{_X\u001b\u0090\u00ec\u00c9C&\u009f;\u00ce&9(\u00cd\u00d2\u0004\u0080\u00c3\u00a0\u00b85\u0092KB\u00ae\t\n\u0003\u00c7\u00e6\u00fc\u00d90=\b\n\u00f4\u00d5\u000e\u0088\u0095OD"
                     .length();
                  var5 = '(';
                  var13 = -1;
            }

            var15 = var6.substring(++var13, var13 + var5);
            var10001 = 0;
         }
      }
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   public static void setStateArray(String[] strings) {
      stateArray = strings;
   }

   public static String[] getStateArray() {
      return stateArray;
   }

   private static Throwable passThrough(Throwable exception) {
      return exception;
   }

   private static String decryptString(byte[] data) {
      int index = 0;
      int value;
      char[] chars = new char[value = data.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & data[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = data[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
