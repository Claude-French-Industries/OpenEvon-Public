package club.evon.manager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.DisconnectionInfo;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.runtime.IndyRuntime;
import club.evon.util.AesEncryption;
import club.evon.util.KeyedRandom;




public class BanManager {
   private final JsonObject banData;
   private static final File BAN_FILE;
   private static final Pattern DURATION_PATTERN;
   private static int state;
   private static final long xorKey = KeyedRandom.create(6844722914452280688L, -2437468772050192657L, MethodHandles.lookup().lookupClass()).next(32924096914683L);
   private static final String[] STRINGS;
   private static final long[] encryptedConstants;
   private static final Long[] decryptedConstants;
   private static final Map cipherCache;

   public BanManager() {
      super();

      long longValue = xorKey ^ 53127157343739L;
      if (!BAN_FILE.exists()) {
         this.banData = new JsonObject();
      } else {
         StringBuilder builder = new StringBuilder();

         String text;
         try (BufferedReader bufferedReader = new BufferedReader(new FileReader(BAN_FILE))) {
            while ((text = bufferedReader.readLine()) != null) {
               builder.append(text);
            }
         } catch (IOException iOException) {
            throw new RuntimeException(iOException);
         }

         String text2;
         try {
            String[] strings = STRINGS;
            text = strings[4];
            text2 = AesEncryption.decrypt(strings[1], builder.toString());
         } catch (Exception exception) {
            this.banData = new JsonObject();
            return;
         }

         this.banData = JsonParser.parseString(text2).getAsJsonObject();
      }
      }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public void handleDisconnect(DisconnectionInfo info) {
      int value;
      label84: {
         String text;
         String text2;
         label85: {
            long longValue = xorKey ^ 126972510774152L;
            int value2 = getStateValue();
            text = info.reason().getString();
            value = value2;
            boolean contains = text.contains(STRINGS[2]);
            if (value == 0) {
               if (!contains) {
                  break label84;
               }

               text2 = text;
               if (value != 0) {
                  break label85;
               }

               contains = text.contains(STRINGS[9]);
            }

            if (!contains) {
               break label84;
            }

            text2 = ClientContext.MINECRAFT_CLIENT.getSession().getUsername();
         }

         String text3 = text2;
         boolean has = this.banData.has(text3);
         if (value == 0) {
            if (has) {
               return;
            }

            this.banData.addProperty(text3, parseBanExpiry(text));
            has = BAN_FILE.exists();
         }

         label68: {
            if (value == 0) {
               if (has) {
                  break label68;
               }

               try {
                  BAN_FILE.createNewFile();
               } catch (IOException iOException) {
                  throw new RuntimeException(iOException);
               }
            }
         }

         try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(BAN_FILE, false))) {
            String[] strings = STRINGS;
            String text4 = strings[1];
            String encrypt = AesEncryption.encrypt(strings[1], this.banData.toString());
            bufferedWriter.write(encrypt);
         } catch (Exception exception) {
            throw new RuntimeException(exception);
         }
      }

      if (BaseModule.getStateArray() == null) {
         setState(++value);
      }
   }

   public String getRemainingBanTime(String username) {
      try {
      long longValue = xorKey ^ 34038226462132L;
      int value = getState();
      BanManager tmp = this;
      if (value != 0) {
         if (!this.isBanned(username)) {
            return null;
         }

         tmp = this;
      }

      long longValue2;
      long longValue3;
      long longValue4;
      long longValue5;
         long longValue6 = tmp.banData.get(username).getAsLong();
         long longValue7 = longValue6 - System.currentTimeMillis();
         long longValue8 = 5850830365826040167L ^ longValue;
         short shortValue = 7909;
         long longValue9 = longValue7
            / ((Number)IndyRuntime.invoke(
                  "club.evon.manager.BanManager",
                  "decryptLongSiteFactory",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue), longValue8}
               ))
               .longValue();
         long longValue10 = 3390605271810714853L ^ longValue;
         short shortValue2 = 29537;
         longValue2 = longValue9
            / ((Number)IndyRuntime.invoke(
                  "club.evon.manager.BanManager",
                  "decryptLongSiteFactory",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue2), longValue10}
               ))
               .longValue();
         long longValue11 = 7560646287633682092L ^ longValue;
         short shortValue3 = 20783;
         long longValue12 = longValue9
            % ((Number)IndyRuntime.invoke(
                  "club.evon.manager.BanManager",
                  "decryptLongSiteFactory",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue3), longValue11}
               ))
               .longValue();
         long longValue13 = 430098068077145555L ^ longValue;
         short shortValue4 = 12884;
         longValue3 = longValue12
            / ((Number)IndyRuntime.invoke(
                  "club.evon.manager.BanManager",
                  "decryptLongSiteFactory",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue4), longValue13}
               ))
               .longValue();
         long longValue14 = 164603077034167905L ^ longValue;
         short shortValue5 = 4577;
         long longValue15 = longValue9
            % ((Number)IndyRuntime.invoke(
                  "club.evon.manager.BanManager",
                  "decryptLongSiteFactory",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue5), longValue14}
               ))
               .longValue();
         long longValue16 = 4895318037455141217L ^ longValue;
         short shortValue6 = 9959;
         longValue4 = longValue15
            / ((Number)IndyRuntime.invoke(
                  "club.evon.manager.BanManager",
                  "decryptLongSiteFactory",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue6), longValue16}
               ))
               .longValue();
         long longValue17 = 2449543783202469870L ^ longValue;
         short shortValue7 = 26731;
         longValue5 = longValue9
            % ((Number)IndyRuntime.invoke(
                  "club.evon.manager.BanManager",
                  "decryptLongSiteFactory",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue7), longValue17}
               ))
               .longValue();
         long longValue18 = longValue2;
         String text;
         if (value == 0 || longValue2 > 0L) {
            String[] strings = STRINGS;
            text = longValue18 + strings[0];
         } else {
            text = "";
         }

         String text2;
         if (value == 0 || longValue3 > 0L) {
            long longValue19 = longValue3;
            String[] strings2 = STRINGS;
            text2 = longValue19 + strings2[3];
         } else {
            text2 = "";
         }

         String text3;
         if (value == 0 || longValue4 > 0L) {
            long longValue20 = longValue4;
            text3 = longValue20 + STRINGS[7];
         } else {
            text3 = "";
         }

         String text4;
         if (value == 0 || longValue5 > 0L || (longValue2 == 0L && longValue3 == 0L && longValue4 == 0L)) {
            text4 = (longValue5 + "s").trim();
         } else {
            text4 = "".trim();
         }

         return text + text2 + text3 + text4;
      } catch (Throwable throwable) {
         throw sneakyThrow(throwable);
      }
   }

   public void showBanScreen(Runnable runnable, Screen parent) {
      long longValue = xorKey ^ 42950665345544L;
      int value = getState();
      String username = ClientContext.MINECRAFT_CLIENT.getSession().getUsername();
      int value2 = value;
      if (this.banData.has(username)) {
      }

      if (value2 == 0) {
         BaseModule.setStateArray(new BaseModule[1]);
      }
   }

   private boolean isBanned(String text) {
      long longValue = xorKey ^ 11698930293486L;
      int value = getState();
      JsonObject tmp = this.banData;
      String tmp2 = text;
      if (value != 0) {
         if (!this.banData.has(text)) {
            return false;
         }

         tmp = this.banData;
         tmp2 = text;
      }

      long longValue2 = tmp.get(tmp2).getAsLong();
      long longValue3;
      int value2 = (longValue3 = System.currentTimeMillis() - longValue2) == 0L ? 0 : (longValue3 < 0L ? -1 : 1);
      if (value != 0) {
         if (value2 >= 0) {
            this.banData.remove(text);
            return false;
         }

         value2 = 1;
      }

      return value2 != 0;
   }

   private static long parseBanExpiry(String duration) {
      try {
      long longValue = xorKey ^ 54412226639936L;
      int value = getStateValue();
      Matcher matcher = DURATION_PATTERN.matcher(duration);
      int value2 = value;
      Matcher matcher2 = matcher;
      if (value2 == 0) {
         if (!matcher.find()) {
            long longValue2 = 6812953069437296398L ^ longValue;
            short shortValue = 23931;
            return ((Number)IndyRuntime.invoke(
                  "club.evon.manager.BanManager",
                  "decryptLongSiteFactory",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue), longValue2}
               ))
               .longValue();
         }

         matcher2 = matcher;
      }

      long longValue3;
      long longValue4;
      long longValue5;
      long longValue6;
      label69: {
         String group = matcher2.group(1);
         if (value2 == 0) {
            if (group == null) {
               longValue3 = 0L;
               break label69;
            }

            group = matcher.group(1);
         }

         longValue3 = Long.parseLong(group);
      }

      long longValue7;
      label63: {
         longValue7 = longValue3;
         String group2 = matcher.group(2);
         if (value2 == 0) {
            if (group2 == null) {
               longValue4 = 0L;
               break label63;
            }

            group2 = matcher.group(2);
         }

         longValue4 = Long.parseLong(group2);
      }

      long longValue8;
      label57: {
         longValue8 = longValue4;
         String group3 = matcher.group(3);
         if (value2 == 0) {
            if (group3 == null) {
               longValue5 = 0L;
               break label57;
            }

            group3 = matcher.group(3);
         }

         longValue5 = Long.parseLong(group3);
      }

      long longValue9;
      label51: {
         longValue9 = longValue5;
         String group4 = matcher.group(4);
         if (value2 == 0) {
            if (group4 == null) {
               longValue6 = 0L;
               break label51;
            }

            group4 = matcher.group(4);
         }

         longValue6 = Long.parseLong(group4);
      }

      long longValue10 = longValue6;
      return System.currentTimeMillis()
         + TimeUnit.DAYS.toMillis(longValue7)
         + TimeUnit.HOURS.toMillis(longValue8)
         + TimeUnit.MINUTES.toMillis(longValue9)
         + TimeUnit.SECONDS.toMillis(longValue10);
      } catch (Throwable throwable) {
         throw sneakyThrow(throwable);
      }
   }

   static {
      try {
      long var11 = xorKey ^ 74769530046430L;
      setState(1);
      Cipher var13;
      Cipher var10000 = var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var14 = 1; var14 < 8; var14++) {
         var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var20 = new String[10];
      int var18 = 0;
      String var17 = "\u00a1\u001c\u00cf\u000et\u008fg\u00f7(\u00f3c\u00b4%vo\u00e9\u0090\u00d7-p\u00d8\u00ea\u0089\u001e\u0001\u00a1\u0097(\u00e6gw\u00aa\u00fbJ`0\u00de\u00da\u00f5\r\u0088\b\u008a0P\u0080\u00e9\u00a1!\u0010_\u00c3\u001f\u0002\u00bf?\u008ek\u009e\u000extw\u00a8\u00eaf\b\u0094\u0014\u00c5\u0081;e\u00fdA(\u00f3c\u00b4%vo\u00e9\u0090\u00d7-p\u00d8\u00ea\u0089\u001e\u0001\u00a1\u0097(\u00e6gw\u00aa\u00fbJ`0\u00de\u00da\u00f5\r\u0088\b\u008a0P\u0080\u00e9\u00a1!\b\u0006U_\u00c8\u00b4+O\u00c9\u00108\u00fe@\u00fc\u0082\u0092\u0086C\u009e\u00a4\u00bd\u0016dH\u0003'\b2%\u00cd\u001aD\u0015K[";
      int var19 = var17.length();
      char var16 = '\b';
      int var25 = -1;

      while (true) {
         String var26 = var17.substring(++var25, var25 + var16);
         byte[] var21 = var13.doFinal(var26.getBytes("ISO-8859-1"));
         String var37 = decodeString(var21).intern();
         var20[var18++] = var37;
         if ((var25 += var16) >= var19) {
            break;
         }

         var16 = var17.charAt(var25);
      }

      var17 = "\u00d5>y\u00efo_\u00e3P\u00c5\u00ce\u00f5\n'\u0007?%B\u00ce\u001e\u00e4\b\u00fb\u00c5A\u00ba\u00d0\u00f5l`QsF\u00b6\u00b4\u00ac\u0007|\u00117\u0018\u00ac\u000f\u00c8\u00d2\u00c2\u0010P\u0002\u00d6O/e4\u0016\u00bdn\u0018\u0098\u0087\u0007R:\u0094\u00c7\u00d45j\u009e\u00c4<\u00f9\u00c7\u0086g\u00bd\u00d9\u00f5y\u00c8\u001d\u00a6";
      var19 = var17.length();
      var16 = '8';
      var25 = -1;

      while (true) {
         String var26 = var17.substring(++var25, var25 + var16);
         byte[] var21 = var13.doFinal(var26.getBytes("ISO-8859-1"));
         String var37 = decodeString(var21).intern();
         var20[var18++] = var37;
         if ((var25 += var16) >= var19) {
            break;
         }

         var16 = var17.charAt(var25);
      }

      STRINGS = var20;
      cipherCache = new HashMap(13);
      Cipher var0;
      var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
      var10002 = SecretKeyFactory.getInstance("DES");
      var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var1 = 1; var1 < 8; var1++) {
         var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long[] var6 = new long[8];
      int var3 = 0;
      String var4 = "\u00de\u0085\u00ba!\u0003M\"P\u00af(H\u00b5\u009a<\u009b\u00a3\u00bag\u0003\u00ech\u00f4\u00df\u0082Q\u00b7\u009cJ\u00d4\u00190\u0005\u00b5m\u00a5\u001b\u00af\u0006\u00eb\u00f7\u00ed\u00c4\u00d4\u00c3\u00ae|T\u00f9";
      int var5 = var4.length();
      int var2 = 0;

      while (var2 < var5) {
         int var35 = var2;
         var2 += 8;
         byte[] var7 = var4.substring(var35, var2).getBytes("ISO-8859-1");
         long var41 = (var7[0] & 255L) << 56
               | (var7[1] & 255L) << 48
               | (var7[2] & 255L) << 40
               | (var7[3] & 255L) << 32
               | (var7[4] & 255L) << 24
               | (var7[5] & 255L) << 16
               | (var7[6] & 255L) << 8
               | var7[7] & 255L;
         byte[] var10 = var0.doFinal(
            new byte[]{
               (byte)(var41 >>> 56),
               (byte)(var41 >>> 48),
               (byte)(var41 >>> 40),
               (byte)(var41 >>> 32),
               (byte)(var41 >>> 24),
               (byte)(var41 >>> 16),
               (byte)(var41 >>> 8),
               (byte)var41
            }
         );
         var6[var3++] = (var10[0] & 255L) << 56
               | (var10[1] & 255L) << 48
               | (var10[2] & 255L) << 40
               | (var10[3] & 255L) << 32
               | (var10[4] & 255L) << 24
               | (var10[5] & 255L) << 16
               | (var10[6] & 255L) << 8
               | var10[7] & 255L;
      }

      var4 = "\u00e0@\u00cf\u00de(\u00fb<\u00ea\u0001\u00bd\u001cz\u00f8f\u00a9\u00b5";
      var5 = var4.length();
      var2 = 0;

      while (var2 < var5) {
         int var35 = var2;
         var2 += 8;
         byte[] var7 = var4.substring(var35, var2).getBytes("ISO-8859-1");
         long var41 = (var7[0] & 255L) << 56
               | (var7[1] & 255L) << 48
               | (var7[2] & 255L) << 40
               | (var7[3] & 255L) << 32
               | (var7[4] & 255L) << 24
               | (var7[5] & 255L) << 16
               | (var7[6] & 255L) << 8
               | var7[7] & 255L;
         byte[] var10 = var0.doFinal(
            new byte[]{
               (byte)(var41 >>> 56),
               (byte)(var41 >>> 48),
               (byte)(var41 >>> 40),
               (byte)(var41 >>> 32),
               (byte)(var41 >>> 24),
               (byte)(var41 >>> 16),
               (byte)(var41 >>> 8),
               (byte)var41
            }
         );
         var6[var3++] = (var10[0] & 255L) << 56
               | (var10[1] & 255L) << 48
               | (var10[2] & 255L) << 40
               | (var10[3] & 255L) << 32
               | (var10[4] & 255L) << 24
               | (var10[5] & 255L) << 16
               | (var10[6] & 255L) << 8
               | var10[7] & 255L;
      }

      encryptedConstants = var6;
      decryptedConstants = new Long[8];
      String[] var23 = STRINGS;
      BAN_FILE = new File(new File(ClientContext.MINECRAFT_CLIENT.runDirectory, var23[5]), var23[6]);
      DURATION_PATTERN = Pattern.compile(var23[8]);
      } catch (Exception var10004) {
         throw sneakyThrow(var10004);
      }
   }

   public static void setState(int value) {
      state = value;
   }

   public static int getState() {
      return state;
   }

   public static int getStateValue() {
      int value = getState();
      return value == 0 ? 110 : 0;
   }

   private static Throwable identity(Throwable throwable) {
      return throwable;
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }

   private static String decodeString(byte[] bytes) {
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

   private static long decryptLong(int index, long key) {
      int value = index ^ (int)(key & 32767L) ^ 1182;
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
            throw new RuntimeException("club/evon/q", exception);
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
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(
                  MethodHandles.lookup()
                     .findStatic(BanManager.class, "decryptLongBootstrap", MethodType.methodType(long.class, Lookup.class, MutableCallSite.class, String.class, Object[].class))
                     .asCollector(Object[].class, type.parameterCount()),
                  0,
                  lookup,
                  mutableCallSite,
                  name
               ),
               type
            )
         );
         return mutableCallSite;
      } catch (Exception exception) {
         throw new RuntimeException("club/evon/q" + " : " + name + " : " + type.toString(), exception);
      }
   }
}
