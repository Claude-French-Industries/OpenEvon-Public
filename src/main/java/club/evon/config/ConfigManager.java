package club.evon.config;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import club.evon.ClientContext;
import club.evon.manager.FriendManager;
import club.evon.module.BaseModule;
import club.evon.runtime.IndyRuntime;
import club.evon.util.AesEncryption;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public final class ConfigManager {
   private final Map<String, Config> configs = new HashMap<>();
   private final File configDirectory;
   private final Config defaultConfig;
   private final Thread syncThread;
   private static boolean X;
   private static final long a = KeyedRandom.create(1611298997410034141L, 6383006156609818726L, MethodHandles.lookup().lookupClass()).next(152064617488377L);
   private static final String[] STRINGS;
   private static final long[] encryptedValues;
   private static final Long[] decryptedValues;
   private static final Map cipherCache;

   // ---------------------------------------------------------------------------------------
   // Persistence state (added; the original client kept this in the native licence layer that
   // is no longer present, so nothing was remembered across restarts).
   // ---------------------------------------------------------------------------------------

   /** Last manager created; lets any setting/module mutate call {@link #markDirty()} safely. */
   private static volatile ConfigManager instance;
   /** Small marker file (next to friends.json) naming the config that is currently active. */
   private static final String ACTIVE_CONFIG_FILE = "active_config.json";
   /** Key used inside that marker file. */
   private static final String ACTIVE_CONFIG_KEY = "active";
   /** Same storage key the config files and friends.json already use. */
   private static final String STORAGE_KEY = "mY9qW2eT8zR4pL1oX7cV6nB3uH5dK0fJ";
   /** How long the state has to stay untouched before the dirty flag is flushed (~2-5s). */
   private static final long AUTOSAVE_DELAY_MS = 3000L;
   private static final Logger LOGGER = LogUtils.getLogger();

   /** Guards {@link Config#load} / {@link Config#save} against running into each other. */
   private final Object stateLock = new Object();
   /** Name of the config that is currently applied to the modules; never null after the ctor. */
   private volatile String activeConfigName;
   /** Set by any state mutation, cleared when it has been written out. */
   private volatile boolean dirty;
   /** Timestamp of the last mutation, used to debounce the autosave. */
   private volatile long lastChangeAt;

   public ConfigManager() {
      String[] strings = STRINGS;
      this.configDirectory = new File(ClientContext.CLIENT_DIR, strings[4]);
      this.defaultConfig = new Config(strings[0]);
      this.configDirectory.mkdirs();
      this.loadConfigs();
      // Publish before the boot load so that everything the loader touches can mark the state
      // dirty through the static hook.
      instance = this;
      // Configs are applied to the module list here; EvonClient creates the ModuleManager
      // before this manager, so the modules exist by now.
      this.loadActiveConfigOnBoot();
      this.syncThread = new Thread(this::syncLoop, strings[1]);
      this.syncThread.setDaemon(true);
      this.syncThread.start();
   }

   private String normalizeName(String text) {
      return text.toLowerCase();
   }

   private void loadConfigs() {
      long longValue = a ^ 51307575602750L;
      File[] values = this.configDirectory.listFiles();
      if (values != null) {
         for (File file : values) {
            String removeExtension = FilenameUtils.removeExtension(file.getName());
            this.configs.put(this.normalizeName(removeExtension), new Config(removeExtension));
         }
      }
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void syncLoop() {
      long longValue = a ^ 118183405275462L;
      boolean flag = B();

      label51:
      while (true) {
         // Debounced autosave. The loop already wakes up twice a second and runs on a daemon
         // thread, so it is the natural place to flush: no extra thread, no disk thrash while a
         // slider or a HUD element is being dragged. The call can never throw.
         this.flushIfDirty();

         File[] values;
         while (true) {
            try {
               values = this.configDirectory.listFiles();
               break;
            } catch (Exception exception) {
            }
         }

         while (true) {
            try {
               if (values != null) {
                  break;
               }

               long longValue2 = 9069138139931275691L ^ longValue;
               short shortValue = 21488;
               Thread.sleep(
                  ((Number)IndyRuntime.invoke(
                        "club.evon.config.ConfigManager",
                        "a_r3",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        "r",
                        "(IJ)J",
                        new Object[]{Integer.valueOf(shortValue), longValue2}
                     ))
                     .longValue()
               );
            } catch (Exception exception2) {
               continue label51;
            } catch (Throwable throwable) {
               throw sneakyThrow(throwable);
            }

            if (flag) {
               if (!flag) {
                  break;
               }
               continue label51;
            }
         }

         try {
            HashSet set = new HashSet();
            File[] values2 = values;
            int length = values2.length;
            int index = 0;

            while (index < length) {
               File file = values2[index];
               String removeExtension = FilenameUtils.removeExtension(file.getName());
               String normalizeName = this.normalizeName(removeExtension);
               set.add(normalizeName);
               this.configs.computeIfAbsent(normalizeName, var1x -> new Config(removeExtension));
               index++;
               if (!flag) {
                  continue label51;
               }

               if (!flag) {
                  break;
               }
            }

            this.configs.keySet().removeIf(string -> {
               long var3x = a ^ 109822252471717L;
               boolean var5x = B();
               boolean contains = set.contains(string);
               if (var5x) {
                  if (contains) {
                     return false;
                  }

                  contains = string.equals(this.normalizeName(STRINGS[2]));
               }

               return !var5x ? contains : !contains;
            });
            long longValue3 = 756221947829287504L ^ longValue;
            short shortValue2 = 22538;
            Thread.sleep(
               ((Number)IndyRuntime.invoke(
                     "club.evon.config.ConfigManager",
                     "a_r3",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "r",
                     "(IJ)J",
                     new Object[]{Integer.valueOf(shortValue2), longValue3}
                  ))
                  .longValue()
            );
         } catch (Exception exception3) {
         } catch (Throwable throwable2) {
            throw sneakyThrow(throwable2);
         }
      }
   }

   public void loadConfig(String name, boolean loadKeybinds) {
      long longValue = a ^ 91813273326711L;
      boolean tmp = O();
      Config get = this.configs.get(this.normalizeName(name));
      boolean flag = tmp;
      Config config = get;
      if (!flag) {
         if (get == null) {
            return;
         }

         config = get;
      }

      synchronized (this.stateLock) {
         config.load(loadKeybinds);
         // Whatever the loader just pushed into the settings is, by definition, already on
         // disk - a load must not trigger an autosave of its own.
         this.dirty = false;
      }

      if (config.getFile().exists()) {
         this.setActiveConfig(config.getName());
      }
   }

   public void saveConfig(String name) {
      long longValue = a ^ 3748390319571L;
      String normalizeName = this.normalizeName(name);
      boolean tmp = O();
      Config computeIfAbsent = this.configs.computeIfAbsent(normalizeName, string -> new Config(name));
      boolean flag = tmp;
      computeIfAbsent.save();
      if (BaseModule.getStateArray() == null) {
         t(!flag);
      }
   }

   public Config getConfig(String name) {
      return this.configs.get(this.normalizeName(name));
   }

   public Config createConfig(String name) {
      long longValue = a ^ 26492802791320L;
      boolean tmp = B();
      String normalizeName = this.normalizeName(name);
      Config config = new Config(name);
      this.configs.put(normalizeName, config);
      boolean flag = tmp;
      if (!flag) {
         BaseModule.setStateArray(new BaseModule[3]);
      }

      return config;
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public void deleteConfig(String name) {
      long longValue = a ^ 126527337195506L;
      boolean tmp = O();
      String normalizeName = this.normalizeName(name);
      boolean flag = tmp;
      Config remove = this.configs.remove(normalizeName);
      Config config = remove;
      if (flag) {
         try {
            Files.deleteIfExists(config.getFile().toPath());
         } catch (Exception exception) {
            ChatUtil.sendError(STRINGS[3] + exception);
         }
      } else if (remove != null) {
         label21:
         try {
            config = remove;
            break label21;
         } catch (Exception exception2) {
            ChatUtil.sendError(STRINGS[3] + exception2);
         }
      }
   }

   public Map<String, Config> getConfigs() {
      return this.configs;
   }

   public File getConfigDirectory() {
      return this.configDirectory;
   }

   public Config getDefaultConfig() {
      return this.defaultConfig;
   }

   public Thread getSyncThread() {
      return this.syncThread;
   }

   // ---------------------------------------------------------------------------------------
   // Active-config tracking + debounced autosave.
   //
   // The decompiled client never recorded which config was in use: the shutdown hook always
   // wrote the *default* config and nothing was read back on boot, so every restart started
   // from a fresh default. The two methods below replace that with an explicit active name
   // (persisted in {@link #ACTIVE_CONFIG_FILE}) plus a dirty flag that the existing 500 ms
   // sync loop flushes once the state has been quiet for {@link #AUTOSAVE_DELAY_MS}.
   // ---------------------------------------------------------------------------------------

   /** Name of the config currently applied to the modules. */
   public String getActiveConfigName() {
      return this.activeConfigName;
   }

   /**
    * Called by every state mutation (module toggles, settings, binds, friends). Cheap and
    * thread safe: it only publishes two volatile fields, the actual write is deferred.
    */
   public static void markDirty() {
      ConfigManager manager = instance;
      if (manager != null) {
         manager.markChanged();
      }
   }

   public void markChanged() {
      this.dirty = true;
      this.lastChangeAt = System.currentTimeMillis();
   }

   public boolean isDirty() {
      return this.dirty;
   }

   /**
    * Writes the active config out. Used by the shutdown hook, and by {@link #flushIfDirty()}
    * from the sync thread. Serialised against {@link #loadConfig(String, boolean)} so a load
    * can never read a half written file.
    */
   public void saveActiveConfig() {
      String name = this.activeConfigName;
      if (name == null) {
         name = this.defaultConfig.getName();
      }

      Config config = this.configFor(name);
      if (config == null) {
         return;
      }

      synchronized (this.stateLock) {
         try {
            config.save();
         } catch (Throwable throwable) {
            LOGGER.error("[Evon] Could not save the active config '{}'", name, throwable);
         }
      }
   }

   /** Flushes the dirty flag once the state has been stable for {@link #AUTOSAVE_DELAY_MS}. */
   private void flushIfDirty() {
      if (!this.dirty) {
         return;
      }

      if (System.currentTimeMillis() - this.lastChangeAt < AUTOSAVE_DELAY_MS) {
         return;
      }

      if (this.activeConfigName == null) {
         this.dirty = false;
         return;
      }

      // Cleared *before* the write: anything that changes while the file is being written
      // simply marks the state dirty again and is picked up by the next tick.
      this.dirty = false;
      this.saveActiveConfig();
      this.saveFriendsQuietly();
   }

   /**
    * Friends are not part of a config file (they live in friends.json), but they are part of the
    * client state, so an autosave takes them with it - otherwise a friend added in a session that
    * never shuts down cleanly would be lost.
    */
   private void saveFriendsQuietly() {
      try {
         FriendManager friends = FriendManager.getInstance();
         if (friends != null) {
            friends.save();
         }
      } catch (Throwable throwable) {
         LOGGER.error("[Evon] Could not save friends.json", throwable);
      }
   }

   /** Remembers {@code name} as the active config and persists the marker file. */
   private void setActiveConfig(String name) {
      if (name == null || name.isBlank()) {
         return;
      }

      if (name.equals(this.activeConfigName)) {
         return;
      }

      this.activeConfigName = name;
      this.persistActiveConfigName();
   }

   /** Resolves a config by name, creating (but not registering) one for an unknown name. */
   private Config configFor(String name) {
      if (name == null || name.isBlank()) {
         return null;
      }

      String key = this.normalizeName(name);
      Config config = this.configs.get(key);
      if (config != null) {
         return config;
      }

      Config created = new Config(name);
      if (created.getFile().exists()) {
         this.configs.put(key, created);
      }

      return created;
   }

   /**
    * Boot path: applies the config that was active when the client was last closed.
    *
    * <p>A missing/corrupt marker, or a config file that no longer exists, degrades gracefully:
    * the default config is tried next, and only if that is missing too does the client start
    * from fresh defaults - it never crashes and never silently drops the user's state.
    */
   private void loadActiveConfigOnBoot() {
      String defaultName = this.defaultConfig.getName();

      try {
         String remembered = this.readActiveConfigName();
         if (remembered == null || remembered.isBlank()) {
            remembered = defaultName;
         }

         String loaded = this.applyConfig(remembered);
         if (loaded == null && !remembered.equalsIgnoreCase(defaultName)) {
            // The remembered file is gone: keep as much state as possible and use the default.
            LOGGER.warn("[Evon] Active config '{}' no longer exists, falling back to '{}'", remembered, defaultName);
            loaded = this.applyConfig(defaultName);
         }

         if (loaded == null) {
            // Nothing on disk at all: remember the default name so the first autosave lands in
            // the same file the previous client build used.
            this.activeConfigName = defaultName;
         } else {
            this.activeConfigName = loaded;
            LOGGER.info("[Evon] Restored active config '{}'", loaded);
         }

         this.persistActiveConfigName();
      } catch (Throwable throwable) {
         LOGGER.error("[Evon] Could not restore the active config; starting from defaults", throwable);
         this.activeConfigName = defaultName;
      }
   }

   /**
    * Applies a named config to the modules (keybinds included). Returns the config's real name,
    * or null when there is no such file.
    */
   private String applyConfig(String name) {
      Config config = this.configFor(name);
      if (config == null || !config.getFile().exists()) {
         return null;
      }

      synchronized (this.stateLock) {
         // Keybinds are part of a config and have to come back too.
         config.load(true);
         this.dirty = false;
      }

      return config.getName();
   }

   /** Reads the remembered active config name; null when there is none (or it is unreadable). */
   private String readActiveConfigName() {
      try {
         File file = new File(ClientContext.CLIENT_DIR, ACTIVE_CONFIG_FILE);
         if (!file.exists()) {
            return null;
         }

         StringBuilder builder = new StringBuilder();
         try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String text;
            while ((text = bufferedReader.readLine()) != null) {
               builder.append(text);
            }
         }

         String raw = builder.toString().trim();
         if (raw.isEmpty()) {
            return null;
         }

         String json = raw;
         try {
            json = AesEncryption.decrypt(STORAGE_KEY, raw);
         } catch (Exception exception) {
            // Not encrypted (hand edited file): fall through and try it as plain JSON.
         }

         JsonObject jsonObject = ClientContext.GSON.fromJson(json, JsonObject.class);
         if (jsonObject == null || !jsonObject.has(ACTIVE_CONFIG_KEY)) {
            return null;
         }

         return jsonObject.get(ACTIVE_CONFIG_KEY).getAsString();
      } catch (Throwable throwable) {
         LOGGER.error("[Evon] Could not read {}", ACTIVE_CONFIG_FILE, throwable);
         return null;
      }
   }

   /** Writes the marker file; failures are logged and ignored (never break the client). */
   private void persistActiveConfigName() {
      String name = this.activeConfigName;
      if (name == null) {
         return;
      }

      try {
         File file = new File(ClientContext.CLIENT_DIR, ACTIVE_CONFIG_FILE);
         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty(ACTIVE_CONFIG_KEY, name);
         String encrypted = AesEncryption.encrypt(STORAGE_KEY, ClientContext.GSON.toJson(jsonObject));

         try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false))) {
            bufferedWriter.write(encrypted);
         }
      } catch (Throwable throwable) {
         LOGGER.error("[Evon] Could not persist the active config name", throwable);
      }
   }

   public static void t(boolean flag) {
      X = flag;
   }

   public static boolean B() {
      return X;
   }

   public static boolean O() {
      boolean flag = B();
      return !flag;
   }

   static {
      try {
      long var11 = a ^ 80394236395766L;
      t(true);
      Cipher var13;
      Cipher var10000 = var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var14 = 1; var14 < 8; var14++) {
         var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var20 = new String[5];
      int var18 = 0;
      String var17 = "W\u0012e\u00d8J\t\u0090\\\u0018r\u00e9\u00f9r\f\u001b\u008d6\u0014\u00d2S\u00a5\u0005\u00cb\u001dsx\n\u00e0\u00b0Rw\u009a\u00a6\b\u008f?s\u00d6\u00b38\u00c2@";
      int var19 = "W\u0012e\u00d8J\t\u0090\\\u0018r\u00e9\u00f9r\f\u001b\u008d6\u0014\u00d2S\u00a5\u0005\u00cb\u001dsx\n\u00e0\u00b0Rw\u009a\u00a6\b\u008f?s\u00d6\u00b38\u00c2@"
         .length();
      char var16 = '\b';
      int var23 = -1;

      label45:
      while (true) {
         String var24 = var17.substring(++var23, var23 + var16);
         int var10001 = -1;

         while (true) {
            byte[] var21 = var13.doFinal(var24.getBytes("ISO-8859-1"));
            String var33 = a_r5(var21).intern();
            switch (var10001) {
               case 0:
                  var20[var18++] = var33;
                  if ((var23 += var16) >= var19) {
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
                     long[] var6 = new long[2];
                     int var3 = 0;
                     String var4 = "{\u00c2)\u00f6\u00fb\u0019\u00b6\u0019m\u00b6(\u00fd\u00b9\u00d63\u0016";
                     int var5 = "{\u00c2)\u00f6\u00fb\u0019\u00b6\u0019m\u00b6(\u00fd\u00b9\u00d63\u0016".length();
                     int var2 = 0;

                     do {
                        var10001 = var2;
                        var2 += 8;
                        byte[] var7 = var4.substring(var10001, var2).getBytes("ISO-8859-1");
                        var10001 = var3++;
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
                        long var10004 = (var10[0] & 255L) << 56
                           | (var10[1] & 255L) << 48
                           | (var10[2] & 255L) << 40
                           | (var10[3] & 255L) << 32
                           | (var10[4] & 255L) << 24
                           | (var10[5] & 255L) << 16
                           | (var10[6] & 255L) << 8
                           | var10[7] & 255L;
                        byte var38 = -1;
                        var6[var10001] = var10004;
                     } while (var2 < var5);

                     encryptedValues = var6;
                     decryptedValues = new Long[2];
                     break label45;
                  }

                  var16 = var17.charAt(var23);
                  break;
               default:
                  var20[var18++] = var33;
                  if ((var23 += var16) < var19) {
                     var16 = var17.charAt(var23);
                     continue label45;
                  }

                  var17 = "\u00bd|+Fg^\u00d4\u009e\u0097\n\u00ea\u00bd\u001ap`DH\u00e5=d\u00e8,\u008f1\b5\u00f5\u00f3@\u00db\u00a3\u00c2\u00f1";
                  var19 = "\u00bd|+Fg^\u00d4\u009e\u0097\n\u00ea\u00bd\u001ap`DH\u00e5=d\u00e8,\u008f1\b5\u00f5\u00f3@\u00db\u00a3\u00c2\u00f1".length();
                  var16 = 24;
                  var23 = -1;
            }

            var24 = var17.substring(++var23, var23 + var16);
            var10001 = 0;
         }
      }
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }
   }

   private static Exception a_r2(Exception exception) {
      return exception;
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }

   private static String a_r5(byte[] bytes) {
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

   private static long a(int value, long longValue) {
      int value2 = value ^ (int)(longValue & 32767L) ^ 23335;
      if (decryptedValues[value2] == null) {
         byte[] bytes = new byte[]{
            (byte)(longValue >>> 56),
            (byte)(longValue >>> 48),
            (byte)(longValue >>> 40),
            (byte)(longValue >>> 32),
            (byte)(longValue >>> 24),
            (byte)(longValue >>> 16),
            (byte)(longValue >>> 8),
            (byte)longValue
         };
         long longValue2 = encryptedValues[value2];
         byte[] bytes2 = new byte[]{
            (byte)(longValue2 >>> 56),
            (byte)(longValue2 >>> 48),
            (byte)(longValue2 >>> 40),
            (byte)(longValue2 >>> 32),
            (byte)(longValue2 >>> 24),
            (byte)(longValue2 >>> 16),
            (byte)(longValue2 >>> 8),
            (byte)longValue2
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
            throw new RuntimeException("club/evon/config/ConfigManager", exception);
         }

         long longValue3 = (bytes3[0] & 255L) << 56
            | (bytes3[1] & 255L) << 48
            | (bytes3[2] & 255L) << 40
            | (bytes3[3] & 255L) << 32
            | (bytes3[4] & 255L) << 24
            | (bytes3[5] & 255L) << 16
            | (bytes3[6] & 255L) << 8
            | bytes3[7] & 255L;
         decryptedValues[value2] = longValue3;
      }

      return decryptedValues[value2];
   }

   private static long a_r4(Lookup lookup, MutableCallSite mutableCallSite, String text, Object[] values) {
      int value = (Integer)values[0];
      long longValue = (Long)values[1];
      long longValue2 = a(value, longValue);
      MethodHandle constant = MethodHandles.constant(long.class, longValue2);
      mutableCallSite.setTarget(MethodHandles.dropArguments(constant, 0, int.class, long.class));
      return longValue2;
   }

   private static CallSite a_r3(Lookup lookup, String text, MethodType methodType) {
      MutableCallSite mutableCallSite = new MutableCallSite(methodType);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(
                  MethodHandles.lookup()
                     .findStatic(ConfigManager.class, "a_r4", MethodType.methodType(long.class, Lookup.class, MutableCallSite.class, String.class, Object[].class))
                     .asCollector(Object[].class, methodType.parameterCount()),
                  0,
                  lookup,
                  mutableCallSite,
                  text
               ),
               methodType
            )
         );
         return mutableCallSite;
      } catch (Exception exception) {
         throw new RuntimeException("club/evon/config/ConfigManager : " + text + " : " + methodType.toString(), exception);
      }
   }
}
