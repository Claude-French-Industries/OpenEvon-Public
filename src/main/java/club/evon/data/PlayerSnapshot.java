package club.evon.data;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import club.evon.manager.ClientState;
import club.evon.mixins.ClientPlayerEntityAccessor;
import club.evon.module.BaseModule;
import club.evon.module.impl.player.Breaker;
import club.evon.util.KeyedRandom;




public record PlayerSnapshot(Vec3d position, Vec3d lastPosition, Vec3d velocity, double lastX, double lastY, double lastZ, float lastYaw, float lastPitch, boolean lastOnGround, int ticksSinceLastPositionPacketSent, int w, int o) {
   private static final long a = KeyedRandom.create(4241416541970713664L, -3704403737113295747L, MethodHandles.lookup().lookupClass()).next(55749940609346L);

   public void applyTo(ClientPlayerEntity localPlayer) {
      long longValue = a ^ 137706154497229L;
      ClientPlayerEntityAccessor player = (ClientPlayerEntityAccessor)localPlayer;
      player.setLastXClient(this.lastX);
      boolean tmp = Breaker.A();
      player.setLastYClient(this.lastY);
      player.setLastZClient(this.lastZ);
      player.setLastYawClient(this.lastYaw);
      player.setLastPitchClient(this.lastPitch);
      player.setLastOnGround(this.lastOnGround);
      player.setTicksSinceLastPositionPacketSent(this.ticksSinceLastPositionPacketSent);
      boolean flag = tmp;
      localPlayer.setPosition(this.position);
      localPlayer.lastX = this.lastPosition.getX();
      localPlayer.lastY = this.lastPosition.getY();
      localPlayer.lastZ = this.lastPosition.getZ();
      localPlayer.setVelocity(0.0, this.velocity.getY(), 0.0);
      ClientState.getInstance().airTicks = this.w;
      ClientState.getInstance().groundTicks = this.o;
      if (BaseModule.getStateArray() == null) {
         Breaker.e(!flag);
      }
   }

   public float getLastPitch() {
      return this.lastPitch;
   }

   public int W() {
      return this.w;
   }

   public int O() {
      return this.o;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
