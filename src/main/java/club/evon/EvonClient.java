package club.evon;
import net.minecraft.client.MinecraftClient;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import club.evon.api.HypixelBanChecker;
import club.evon.command.BigTextCommand;
import club.evon.command.BindCommand;
import club.evon.command.ClientNameCommand;
import club.evon.command.Command;
import club.evon.command.ConfigCommand;
import club.evon.command.FriendCommand;
import club.evon.command.HelpCommand;
import club.evon.command.IrcChatCommand;
import club.evon.command.NameCommand;
import club.evon.command.OnlineCommand;
import club.evon.command.PanicCommand;
import club.evon.command.UnbindCommand;
import club.evon.config.AccountConfig;
import club.evon.config.ConfigManager;
import club.evon.data.EvonUser;
import club.evon.data.UserRank;
import club.evon.launch.LaunchProperties;
import club.evon.manager.BanManager;
import club.evon.manager.ChatNameManager;
import club.evon.manager.ClickScheduler;
import club.evon.manager.ClientState;
import club.evon.manager.CommandManager;
import club.evon.manager.FontManager;
import club.evon.manager.FriendManager;
import club.evon.manager.HudDragManager;
import club.evon.manager.KeybindManager;
import club.evon.manager.ModuleManager;
import club.evon.manager.NotificationManager;
import club.evon.manager.SilentSlotManager;
import club.evon.manager.TimerManager;
import club.evon.module.BaseModule;
import club.evon.module.impl.combat.AimAssist;
import club.evon.module.impl.combat.AntiFireball;
import club.evon.module.impl.combat.AutoBlock;
import club.evon.module.impl.combat.AutoClicker;
import club.evon.module.impl.combat.CriticalHit;
import club.evon.module.impl.combat.HypixelTpAura;
import club.evon.module.impl.combat.InfiniteAura;
import club.evon.module.impl.combat.KillAura;
import club.evon.module.impl.combat.LagRange;
import club.evon.module.impl.combat.Reach;
import club.evon.module.impl.combat.TriggerBot;
import club.evon.module.impl.combat.Velocity;
import club.evon.module.impl.misc.AntiAFK;
import club.evon.module.impl.misc.Disabler;
import club.evon.module.impl.misc.HypixelAddons;
import club.evon.module.impl.misc.JoinClaim;
import club.evon.module.impl.misc.MiddleClickFriend;
import club.evon.module.impl.misc.PartySpammer;
import club.evon.module.impl.misc.StaffDetector;
import club.evon.module.impl.misc.Streamer;
import club.evon.module.impl.misc.Timer;
import club.evon.module.impl.motion.Flight;
import club.evon.module.impl.motion.InventoryMove;
import club.evon.module.impl.motion.LongJump;
import club.evon.module.impl.motion.MoveFix;
import club.evon.module.impl.motion.NoSlow;
import club.evon.module.impl.motion.Scaffold;
import club.evon.module.impl.motion.Speed;
import club.evon.module.impl.motion.Sprint;
import club.evon.module.impl.motion.Stuck;
import club.evon.module.impl.motion.TargetStrafe;
import club.evon.module.impl.player.AntiVoid;
import club.evon.module.impl.player.AutoSelect;
import club.evon.module.impl.player.Breaker;
import club.evon.module.impl.player.BridgeAssist;
import club.evon.module.impl.player.ChestStealer;
import club.evon.module.impl.player.FastPlace;
import club.evon.module.impl.player.InventorySorter;
import club.evon.module.impl.player.NoFall;
import club.evon.module.impl.player.NoJumpDelay;
import club.evon.module.impl.render.Animations;
import club.evon.module.impl.render.Chams;
import club.evon.module.impl.render.ESP;
import club.evon.module.impl.render.NoGuiClose;
import club.evon.module.impl.render.OreEsp;
import club.evon.module.impl.render.Overlay;
import club.evon.module.impl.render.PlayerEffects;
import club.evon.module.impl.render.ScoreboardModule;
import club.evon.module.impl.render.TargetDisplay;
import club.evon.module.impl.render.WorldEditor;
import club.evon.ui.ClickGuiScreen;
import club.evon.util.ChatUtil;
import club.evon.util.TargetSelector;
import club.evon.util.render.FontRenderer;




public final class EvonClient {
   private static EvonClient instance;
   private static EvonUser user;
   /** Brand name shown on the watermark, in the chat tag and on the main menu. */
   private static String clientName = LaunchProperties.NAME;
   private static final Executor EXECUTOR;
   private static final Semaphore SEMAPHORE;
   private FontManager fontManager;
   private ModuleManager moduleManager;
   private CommandManager commandManager;
   private ConfigManager configManager;
   private ClickGuiScreen clickGuiScreen;
   private NotificationManager notificationManager;
   private FontRenderer fontRenderer;
   private BanManager banManager;
   private AccountConfig accountConfig;
   private static String state;
   private static final long KEY;
   private static final String[] STRINGS;
   private static final long c;

   private EvonClient() {
      this.bootstrap();
   }

   /**
    * Client bootstrap.
    *
    * <p>In the original build this method (<code>d()</code>) was a {@code native} method
    * implemented by the obfuscated runtime library, together with the class' static
    * initialiser. It did two things: it started the authentication/IRC socket using the
    * machine's HWID
    * ({@code lambda$bootstrap$0} built an {@code AuthWebSocket} from
    * {@link LaunchProperties#username} plus a hardware id), and it constructed every
    * client service. The native layer has been removed, so the licence/HWID handshake is
    * gone and the service wiring is done here in plain Java.
    *
    * <p>Ordering matters: several modules read
    * {@code EvonClient.getInstance().getFontRenderer()} from their constructors (for
    * example {@code ESP} and {@code Overlay}/{@code EvonWatermark}), and
    * {@link FontRenderer} itself needs {@link #getFontManager()} to exist first. The
    * singleton managers therefore have to be created before the module list.
    */
   private void bootstrap() {
      instance = this;

      // NOTE: the native bootstrap also installed a FlatLaf look and feel for the Swing dialogs
      // in the account helpers. That was removed along with the dialogs themselves: Minecraft's
      // net.minecraft.client.main.Main sets java.awt.headless=true, so AWT/Wing cannot run here.
      // Login feedback now goes through FileDialogUtil.showMessage (chat + log).

      // Singleton services that the native bootstrap used to construct via each class'
      // static init() factory.
      ChatNameManager.init();
      ClientState.init();
      ClickScheduler.init();
      FriendManager.init();
      // friends.json was written by the shutdown hook but never read back: FriendManager.load()
      // had no call site, so the friend list was lost on every restart.
      FriendManager.getInstance().load();
      HudDragManager.init();
      KeybindManager.init();
      SilentSlotManager.init();
      TimerManager.init();
      HypixelBanChecker.init();
      TargetSelector.init();

      // Rendering stack first: FontRenderer (and every module that grabs it) needs
      // FontManager to be present already.
      this.fontManager = new FontManager();
      this.fontRenderer = new FontRenderer();

      this.notificationManager = new NotificationManager();
      this.accountConfig = new AccountConfig();
      this.banManager = new BanManager();

      this.commandManager = new CommandManager(new Command[]{
         new BigTextCommand(),
         new BindCommand(),
         new ClientNameCommand(),
         new ConfigCommand(),
         new FriendCommand(),
         new HelpCommand(),
         new IrcChatCommand(),
         new NameCommand(),
         new OnlineCommand(),
         new PanicCommand(),
         new UnbindCommand()
      });

      this.moduleManager = new ModuleManager(new BaseModule[]{
         new AimAssist(),
         new AntiFireball(),
         new AutoBlock(),
         new AutoClicker(),
         new CriticalHit(),
         new HypixelTpAura(),
         new InfiniteAura(),
         new KillAura(),
         new LagRange(),
         new Reach(),
         new TriggerBot(),
         new Velocity(),
         new AntiAFK(),
         new Disabler(),
         new HypixelAddons(),
         new JoinClaim(),
         new MiddleClickFriend(),
         new PartySpammer(),
         new StaffDetector(),
         new Streamer(),
         new Timer(),
         new Flight(),
         new InventoryMove(),
         new LongJump(),
         new MoveFix(),
         new NoSlow(),
         new Scaffold(),
         new Speed(),
         new Sprint(),
         new Stuck(),
         new TargetStrafe(),
         new AntiVoid(),
         new AutoSelect(),
         new Breaker(),
         new BridgeAssist(),
         new ChestStealer(),
         new FastPlace(),
         new InventorySorter(),
         new NoFall(),
         new NoJumpDelay(),
         new Animations(),
         new Chams(),
         new ESP(),
         new NoGuiClose(),
         new OreEsp(),
         new Overlay(),
         new PlayerEffects(),
         new ScoreboardModule(),
         new TargetDisplay(),
         new WorldEditor()
      });

      // Configs are applied to the module list, so it must exist beforehand.
      this.configManager = new ConfigManager();
      this.clickGuiScreen = new ClickGuiScreen();

      // The native bootstrap registered a shutdown hook; it persisted the active config on
      // exit. Guarded so a partially built client can never break JVM shutdown.

      // TEMP-DIAG: freeze watchdog.
      //
      // Progress-based rather than stack-based: the render thread also owns the game tick, so if it
      // is stuck the world's tick counter stops advancing. Sampling stack equality missed cyclic
      // loops (each sample lands on a different frame of the cycle), which is why the first version
      // never fired. This trips 3s after the tick counter stops.
      Thread evonFreezeWatchdog = new Thread(() -> {
         long lastTick = Long.MIN_VALUE;
         long lastProgress = System.currentTimeMillis();

         while (true) {
            try {
               Thread.sleep(250L);
            } catch (InterruptedException interrupted) {
               return;
            }

            MinecraftClient client = ClientContext.MINECRAFT_CLIENT;
            if (client == null || client.world == null || client.player == null) {
               lastTick = Long.MIN_VALUE;
               lastProgress = System.currentTimeMillis();
               continue;
            }

            long tick = client.world.getTime();
            if (tick != lastTick) {
               lastTick = tick;
               lastProgress = System.currentTimeMillis();
               continue;
            }

            if (System.currentTimeMillis() - lastProgress < 3000L) {
               continue;
            }

            lastProgress = System.currentTimeMillis();
            StringBuilder dump = new StringBuilder("[EVON-FREEZE] game tick stalled for 3s\n");
            for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
               dump.append('"').append(entry.getKey().getName()).append("\" ")
                   .append(entry.getKey().getState()).append('\n');
               for (StackTraceElement element : entry.getValue()) {
                  dump.append("    at ").append(element).append('\n');
               }
            }

            try {
               java.nio.file.Files.writeString(java.nio.file.Path.of("evon-freeze.log"), dump.toString());
            } catch (Throwable ignored) {
            }
         }
      }, "Evon-FreezeWatchdog");
      evonFreezeWatchdog.setDaemon(true);
      evonFreezeWatchdog.start();

      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
         try {
            if (instance != null && instance.configManager != null) {
               instance.save();
            }
         } catch (Throwable throwable) {
            // Never propagate out of a shutdown hook.
         }
      }, "Evon-Shutdown"));
   }

   public void save() {
      long longValue = KEY ^ 35765058982584L;
      boolean notInitialized = ClientContext.isNotInitialized();
      // Saves whichever config is active (the one that was loaded, or the default when none
      // ever was) instead of unconditionally overwriting the default config.
      this.configManager.saveActiveConfig();
      boolean flag = notInitialized;
      FriendManager.getInstance().save();
      if (flag) {
         BaseModule.setStateArray(new BaseModule[3]);
      }
   }

   public static synchronized EvonClient getInstance() {
      long longValue = KEY ^ 40468979936592L;
      if (instance == null) {
         new EvonClient();
      }

      return instance;
   }

   public FontManager getFontManager() {
      return this.fontManager;
   }

   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public CommandManager getCommandManager() {
      return this.commandManager;
   }

   public ConfigManager getConfigManager() {
      return this.configManager;
   }

   public ClickGuiScreen getClickGuiScreen() {
      return this.clickGuiScreen;
   }

   public NotificationManager getNotificationManager() {
      return this.notificationManager;
   }

   public FontRenderer getFontRenderer() {
      return this.fontRenderer;
   }

   public BanManager getBanManager() {
      return this.banManager;
   }

   public AccountConfig getAccountConfig() {
      return this.accountConfig;
   }

   public static EvonUser getUser() {
      return user;
   }

   public static void setUser(EvonUser value) {
      user = value;
   }

   public static Executor getExecutor() {
      return EXECUTOR;
   }

   public static Semaphore getSemaphore() {
      return SEMAPHORE;
   }

   /**
    * The client's brand name, shown on the watermark, in the chat tag and on the main menu.
    */
   public static String getClientName() {
      return clientName;
   }

   /**
    * Sets the client's brand name (".clientname"). A null or blank value restores the default
    * from {@link LaunchProperties#NAME}.
    */
   public static void setClientName(String name) {
      clientName = (name == null || name.isBlank()) ? LaunchProperties.NAME : name.trim();
      ChatUtil.refreshPrefix();
   }

   /**
    * Sets the local display name shown on the watermark (".name"). The client has no
    * authentication backend any more, so this runs under a local identity instead of the
    * account the licence server used to report.
    */
   public static void setUserName(String name) {
      if (name == null || name.isBlank()) {
         return;
      }

      String trimmed = name.trim();
      LaunchProperties.username = trimmed;
      EvonUser current = user;
      user = new EvonUser(
         trimmed,
         current != null ? current.getUid() : "local",
         current != null ? current.getRole() : UserRank.USER
      );
   }

   static {
      // The native runtime used to supply these. With the licence layer gone there is no
      // remote account, so the client runs under a local placeholder identity: this keeps
      // EvonClient.getUser() non-null for the config writer and the watermark renderer.
      user = new EvonUser(LaunchProperties.username, "local", UserRank.USER);
      // Obfuscator flow-guard state. It must be non-null: every `EvonClient.E() != null`
      // test in the UI is the live branch. With it null the ClickGui breaks visibly -
      // CategoryPanel's constructor stops after the first module of each category and
      // getContentHeight() measures a single row, so the panels render empty/undersized.
      state = "evon";
      EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
         Thread thread = new Thread(runnable, "Evon-Worker");
         thread.setDaemon(true);
         return thread;
      });
      SEMAPHORE = new Semaphore(1);
      KEY = 0L;
      STRINGS = null;
      c = 0L;
   }

   public static void f(String value) {
      state = value;
   }

   public static String E() {
      return state;
   }
}
