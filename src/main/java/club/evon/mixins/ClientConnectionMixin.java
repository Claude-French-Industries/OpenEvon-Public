package club.evon.mixins;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.RejectedExecutionException;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.OffThreadException;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.event.EventBus;
import club.evon.event.IncomingPacketEvent;
import club.evon.event.OutboundPacketEvent;
import club.evon.event.OutgoingPacketEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.manager.IncomingPacketManager;
import club.evon.manager.OutgoingPacketManager;
import club.evon.util.ClientConnectionAccessor;




@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin implements ClientConnectionAccessor {
   @Shadow
   @Nullable
   private volatile PacketListener packetListener;
   @Shadow
   @Final
   private static Logger LOGGER;
   @Shadow
   private int packetsReceivedCounter;
   @Shadow
   public Channel channel;
   private static final String[] a;

   @Shadow
   private static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener packet2) {
   }

   @Shadow
   public abstract void disconnect(Text text);

   @Shadow
   public abstract void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet);

   @Shadow
   public abstract NetworkSide getSide();

   private ClientConnectionMixin() {
   }

   @Inject(method = "<init>(Lnet/minecraft/network/NetworkSide;)V", at = @At("TAIL"))
   private void init(NetworkSide networkSide, CallbackInfo callbackInfo) {
      IncomingPacketManager.getInstance().clear();
      OutgoingPacketManager.getInstance().clear();
   }

   @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
   private void hookSendPacket_r2(Packet<?> packet, CallbackInfo callbackInfo) {
      OutgoingPacketEvent outgoingPacketEvent = new OutgoingPacketEvent(packet);
      EventBus.post(outgoingPacketEvent);
      if (outgoingPacketEvent.isCanceled() || OutgoingPacketManager.getInstance().tryQueuePacket(packet)) {
         callbackInfo.cancel();
      }
   }

   @Inject(method = "send(Lnet/minecraft/network/packet/Packet;Lio/netty/channel/ChannelFutureListener;)V", at = @At("HEAD"), cancellable = true)
   private void hookSendPacket(Packet<?> packet, @Nullable ChannelFutureListener list, CallbackInfo callbackInfo) {
      OutboundPacketEvent outboundPacketEvent = new OutboundPacketEvent(packet);
      EventBus.post(outboundPacketEvent);
      if (outboundPacketEvent.isCanceled()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = "handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V",
      at = @At("HEAD"),
      cancellable = true,
      require = 1
   )
   private static void hookReceivePacket(Packet<?> packet, PacketListener packet2, CallbackInfo callbackInfo) {
      if (!(packet instanceof BundleS2CPacket packet3)) {
         PacketReceiveEvent packetReceiveEvent = new PacketReceiveEvent(packet);
         EventBus.post(packetReceiveEvent);
         if (packetReceiveEvent.isCanceled()) {
            callbackInfo.cancel();
         }
      } else {
         callbackInfo.cancel();

         for (Packet packet4 : packet3.getPackets()) {
            try {
               handlePacket(packet4, packet2);
            } catch (OffThreadException offThreadException) {
            }
         }
      }
   }

   @Inject(
      method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V",
      at = @At(value = "INVOKE", target = "Lio/netty/channel/Channel;isOpen()Z", remap = false),
      cancellable = true
   )
   private void hookChannelRead(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callbackInfo) {
      if (this.getSide() == NetworkSide.CLIENTBOUND) {
         if (packet instanceof BundleS2CPacket packet2) {
            callbackInfo.cancel();

            for (Packet packet3 : packet2.getPackets()) {
               try {
                  this.channelRead0(channelHandlerContext, packet3);
               } catch (OffThreadException offThreadException) {
               }
            }

            return;
         }

         IncomingPacketEvent incomingPacketEvent = new IncomingPacketEvent(packet);
         EventBus.post(incomingPacketEvent);
         if (incomingPacketEvent.isCanceled() || IncomingPacketManager.getInstance().tryQueuePacket(packet)) {
            callbackInfo.cancel();
         }
      }
   }

   @Unique
   @Override
   public void evon$channelReadSilent(Packet<?> packet) {
      if (this.channel.isOpen()) {
         PacketListener packet2 = this.packetListener;
         if (packet2 == null) {
            throw new IllegalStateException(a[2]);
         }

         if (packet2.accepts(packet)) {
            try {
               handlePacket(packet, packet2);
            } catch (OffThreadException offThreadException) {
            } catch (RejectedExecutionException rejectedExecutionException) {
               String[] strings = a;
               this.disconnect(Text.translatable(strings[1]));
            } catch (ClassCastException classCastException) {
               String[] strings2 = a;
               LOGGER.error(strings2[3], packet.getClass(), classCastException);
               this.disconnect(Text.translatable(strings2[0]));
            }

            this.packetsReceivedCounter++;
         }
      }
   }

   private static RuntimeException a(RuntimeException runtimeException) {
      return runtimeException;
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[4];
      char[] var20;
      int var3 = 0;
      String var2 = "43D\u000e-\u000e\u00048?M\bj\u001a\u0001*%G\u0014*\u001b\u000b-hA\u00142\u001f\u00040\"w\n%\u001d\u0003<2&43D\u000e-\u000e\u00048?M\bj\u001a\u0001*%G\u0014*\u001b\u000b-h[\u001f6\b\r+\u0019[\u00121\n\f61F";
      int var4 = "43D\u000e-\u000e\u00048?M\bj\u001a\u0001*%G\u0014*\u001b\u000b-hA\u00142\u001f\u00040\"w\n%\u001d\u0003<2&43D\u000e-\u000e\u00048?M\bj\u001a\u0001*%G\u0014*\u001b\u000b-h[\u001f6\b\r+\u0019[\u00121\n\f61F"
         .length();
      char var1 = '%';
      int var8 = -1;

      label52:
      while (true) {
         byte var10000 = 66;
         String var10001 = var2.substring(++var8, var8 + var1);
         byte var10002 = -1;

         while (true) {
            label47: {
               char[] var24 = var10001.toCharArray();
               int var10004 = var24.length;
               int var6 = 0;
               byte var27 = var10000;
               var20 = var24;
               int var14 = var10004;
               byte var32;
               char[] var36;
               int var10006;
               if (var10004 <= 1) {
                  var36 = var24;
                  var32 = var10000;
                  var10006 = var6;
               } else {
                  var27 = var10000;
                  var14 = var10004;
                  if (var10004 <= var6) {
                     break label47;
                  }

                  var36 = var24;
                  var32 = var10000;
                  var10006 = var6;
               }

               while (true) {
                  var36[var10006] = (char)(var36[var10006] ^ var32 ^ switch (var6 % 7) {
                     case 0 -> 27;
                     case 1 -> 4;
                     case 2 -> 106;
                     case 3 -> 56;
                     case 4 -> 6;
                     case 5 -> 60;
                     default -> 42;
                  });
                  var6++;
                  if (var27 == 0) {
                     var10006 = var27;
                     var36 = var20;
                     var32 = var27;
                  } else {
                     if (var14 <= var6) {
                        break;
                     }

                     var36 = var20;
                     var32 = var27;
                     var10006 = var6;
                  }
               }
            }

            String var29 = new String(var20).intern();
            switch (var10002) {
               case 0:
                  var5[var3++] = var29;
                  if ((var8 += var1) >= var4) {
                     a = var5;
                     break label52;
                  }

                  var1 = var2.charAt(var8);
                  break;
               default:
                  var5[var3++] = var29;
                  if ((var8 += var1) < var4) {
                     var1 = var2.charAt(var8);
                     continue label52;
                  }

                  var2 = "1\u0019q%\u001727\u0007\\s`\u000e%1\b\u0019f`\u001c!4\f\u000ew`\n,7C\fs#\u0015!&C\u0010{3\n!<\u0006\u000e27\u001f7r\n\u0012{4\u0017%>\n\u0006w$&1\u0019q%\u001727\u0007\\i=^0:\u0002\b2#\u00111>\u0007\u001254^&7C\f`/\u001d!!\u0010\u0019v";
                  var4 = "1\u0019q%\u001727\u0007\\s`\u000e%1\b\u0019f`\u001c!4\f\u000ew`\n,7C\fs#\u0015!&C\u0010{3\n!<\u0006\u000e27\u001f7r\n\u0012{4\u0017%>\n\u0006w$&1\u0019q%\u001727\u0007\\i=^0:\u0002\b2#\u00111>\u0007\u001254^&7C\f`/\u001d!!\u0010\u0019v"
                     .length();
                  var1 = '<';
                  var8 = -1;
            }

            var10000 = 120;
            var10001 = var2.substring(++var8, var8 + var1);
            var10002 = 0;
         }
      }
   }
}
