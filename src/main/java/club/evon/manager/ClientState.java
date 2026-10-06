package club.evon.manager;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.login.LoginDisconnectS2CPacket;
import net.minecraft.network.packet.s2c.login.LoginHelloS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.ClientService;
import club.evon.EvonClient;
import club.evon.api.HypixelLocationManager;
import club.evon.api.ServerDetector;
import club.evon.event.AttackEntityEvent;
import club.evon.event.ClientTickEvent;
import club.evon.event.DisconnectEvent;
import club.evon.event.EventBus;
import club.evon.event.IncomingPacketEvent;
import club.evon.event.MovementInputEvent;
import club.evon.event.ServerConnectEvent;
import club.evon.event.Subscribe;
import club.evon.mixins.ClientCommonNetworkHandlerAccessor;
import club.evon.module.impl.misc.JoinClaim;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;




public final class ClientState implements ClientService {
   private final ServerDetector serverDetector = new ServerDetector();
   public int groundTicks;
   public int airTicks;
   public int ticksSinceTeleport;
   public int ticksSinceMovement;
   public int ticksSinceAttack;
   private LivingEntity lastAttackedEntity;
   public static ClientState instance;
   private static String flowGuard;
   private static final long KEY = KeyedRandom.create(70246658414909406L, 1049742762297947762L, MethodHandles.lookup().lookupClass()).next(104896387266450L);

   @Subscribe
   private void onAttackEntity(AttackEntityEvent event) {
      long longValue = KEY ^ 87261168431985L;
      String flowGuard2 = getFlowGuard();
      Entity entity = event.getEntity();
      String text = flowGuard2;
      Entity entity2 = entity;
      if (text == null) {
         if (!(entity instanceof LivingEntity)) {
            return;
         }

         entity2 = entity;
      }

      LivingEntity entity3 = (LivingEntity)entity2;
      this.lastAttackedEntity = entity3;
      this.ticksSinceAttack = 0;
   }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 28918707101426L;
      this.ticksSinceTeleport++;
      String flowGuard2 = getFlowGuard();
      this.ticksSinceMovement++;
      this.ticksSinceAttack++;
      String text = flowGuard2;
      if (this.ticksSinceAttack >= 10) {
      }

      MinecraftClient minecraftClient = ClientContext.MINECRAFT_CLIENT;
      if (text == null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         minecraftClient = ClientContext.MINECRAFT_CLIENT;
      }

      if (text == null) {
         if (minecraftClient.currentScreen != null) {
            return;
         }

         minecraftClient = ClientContext.MINECRAFT_CLIENT;
      }

      if (minecraftClient.getOverlay() == null && PlayerUtil.isKeyPressed(46)) {
         ClientContext.MINECRAFT_CLIENT.setScreen(new ChatScreen(".", false));
      }
   }

   @Subscribe
   public void onServerConnect(ServerConnectEvent event) {
      this.serverDetector.detectServer(event.getAddress());
   }

   @Subscribe
   public void onDisconnect(DisconnectEvent event) {
      long longValue = KEY ^ 132790564199845L;
      String flowGuard2 = getFlowGuard();
      HypixelLocationManager.getInstance().setCurrentLocation(null);
      String text = flowGuard2;
      this.serverDetector.clearServerProfile();
      if (text == null) {
         if (ClientContext.MINECRAFT_CLIENT.getNetworkHandler() != null) {
            ClientCommonNetworkHandlerAccessor clientCommonNetworkHandlerAccessor = (ClientCommonNetworkHandlerAccessor)ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
            clientCommonNetworkHandlerAccessor.getServerCookies().clear();
         }

         EvonClient.getInstance().getModuleManager().getModuleByClass(JoinClaim.class).setServerId(null);
      }
   }

   @Subscribe(h = -10)
   private void onMovementInput(MovementInputEvent event) {
      label45: {
         long longValue = KEY ^ 72392391683300L;
         String flowGuard2 = getFlowGuard();
         float floatValue = event.getForward() - 0.0F;
         boolean tmp = floatValue != 0.0F;
         if (flowGuard2 == null) {
            if (tmp) {
               break label45;
            }

            float floatValue2 = event.getSideways() - 0.0F;
            tmp = floatValue2 != 0.0F;
         }

         if (flowGuard2 == null) {
            if (tmp) {
               break label45;
            }

            tmp = event.isJump();
         }

         if (flowGuard2 == null) {
            if (tmp) {
               break label45;
            }

            tmp = event.isSneak();
         }

         if (!tmp) {
            return;
         }
      }

      this.ticksSinceMovement = 0;
   }

   @Subscribe(h = -10)
   private void onIncomingPacket(IncomingPacketEvent event) {
      long longValue = KEY ^ 27397400794792L;
      String flowGuard2 = getFlowGuard();
      Packet packet = event.getPacket();
      if (flowGuard2 == null) {
         if (packet instanceof PlayerPositionLookS2CPacket) {
            this.ticksSinceTeleport = 0;
            if (flowGuard2 == null) {
               return;
            }
         }

         packet = event.getPacket();
      }

      Packet packet2 = packet;
      boolean flag = packet2 instanceof LoginHelloS2CPacket;
      if (flowGuard2 == null) {
         if (flag) {
            LoginHelloS2CPacket packet3 = (LoginHelloS2CPacket)packet2;
            EvonClient.getInstance().getModuleManager().getModuleByClass(JoinClaim.class).setServerId(packet3.getServerId());
            if (flowGuard2 == null) {
               return;
            }
         }

         flag = event.getPacket() instanceof LoginDisconnectS2CPacket;
      }

      label36: {
         if (flowGuard2 == null) {
            if (flag) {
               break label36;
            }

            flag = event.getPacket() instanceof DisconnectS2CPacket;
         }

         if (!flag) {
            return;
         }
      }

      EvonClient.getInstance().getModuleManager().getModuleByClass(JoinClaim.class).setServerId(null);
   }

   public static void init() {
      instance = new ClientState();
      EventBus.register(instance);
   }

   public ServerDetector getServerDetector() {
      return this.serverDetector;
   }

   public LivingEntity getLastAttackedEntity() {
      return this.lastAttackedEntity;
   }

   public static ClientState getInstance() {
      return instance;
   }

   public static void setFlowGuard(String value) {
      flowGuard = value;
   }

   public static String getFlowGuard() {
      return flowGuard;
   }

   static {
      if (getFlowGuard() != null) {
         setFlowGuard("lfydtc");
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
