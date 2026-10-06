package club.evon.api;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload.Id;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class HypixelApiManager {
   private static final Logger LOGGER;
   private static BaseModule[] modules;
   private static final long KEY = KeyedRandom.create(-7998963569997440814L, -4990402548939478684L, MethodHandles.lookup().lookupClass()).next(168088139004365L);
   private static final String[] STRINGS;

   public static void initialize() {
      registerPacketTypes();
      installPacketSender();
   }

   public static void registerPacketTypes() {
      long longValue = KEY ^ 120160671646448L;

      for (String text : HypixelModAPI.getInstance().getRegistry().getClientboundIdentifiers()) {
         try {
            registerClientbound(text);
            String[] strings = STRINGS;
            LOGGER.info(strings[1], text);
         } catch (Exception exception) {
            String[] strings2 = STRINGS;
            LOGGER.error(strings2[11], text, exception);
         }
      }

      for (String text2 : HypixelModAPI.getInstance().getRegistry().getServerboundIdentifiers()) {
         try {
            registerServerbound(text2);
            String[] strings3 = STRINGS;
            LOGGER.info(strings3[9], text2);
         } catch (Exception exception2) {
            LOGGER.error(STRINGS[3], text2, exception2);
         }
      }
   }

   private static void installPacketSender() {
      HypixelModAPI.getInstance().setPacketSender(packet -> {
         long longValue = KEY ^ 53987465025694L;
         BaseModule[] values = getModules();
         ServerboundHypixelPayload serverboundHypixelPayload = new ServerboundHypixelPayload(packet);
         if (values == null) {
            return true;
         }

         if (ClientContext.MINECRAFT_CLIENT.getNetworkHandler() != null) {
            ClientPlayNetworking.send(serverboundHypixelPayload);
            return true;
         }

         try {
            ClientConfigurationNetworking.send(serverboundHypixelPayload);
            return true;
         } catch (IllegalStateException illegalStateException) {
            LOGGER.warn(STRINGS[15], packet);
            return false;
         }
      });
   }

   private static void registerClientbound(String identifier) {
      try {
         Id<ClientboundHypixelPayload> id = new Id<>(Identifier.of(identifier));
         PacketCodec createCodec = ClientboundHypixelPayload.createCodec(id);
         PayloadTypeRegistry.playS2C().register(id, createCodec);
         PayloadTypeRegistry.configurationS2C().register(id, createCodec);
         ClientPlayNetworking.registerGlobalReceiver(id, (var1x, var2x) -> {
            LOGGER.debug(STRINGS[14], identifier);
            handlePayload(identifier, var1x);
         });
         ClientConfigurationNetworking.registerGlobalReceiver(id, (var1x, var2x) -> {
            LOGGER.debug(STRINGS[6], identifier);
            handlePayload(identifier, var1x);
         });
      } catch (IllegalArgumentException illegalArgumentException) {
      }
   }

   private static void handlePayload(String identifier, ClientboundHypixelPayload payload) {
      long longValue = KEY ^ 20665013400689L;
      BaseModule[] values = getModules();
      if (values != null) {
         if (!payload.hasPacket()) {
            String[] strings = STRINGS;
            LOGGER.warn(strings[12], identifier, payload.getErrorReason());

            try {
               HypixelModAPI.getInstance().handleError(identifier, payload.getErrorReason());
            } catch (Exception exception) {
               strings = STRINGS;
               LOGGER.error(strings[8], identifier, exception);
            }

            try {
               ((HypixelApiErrorEvent)HypixelApiErrorEvent.EVENT.invoker()).onError(identifier, payload.getErrorReason());
            } catch (Exception exception2) {
               strings = STRINGS;
               LOGGER.error(strings[10], identifier, exception2);
            }

            return;
         }

         try {
            HypixelModAPI.getInstance().handle(payload.getPacket());
         } catch (Exception exception3) {
            String[] strings2 = STRINGS;
            LOGGER.error(strings2[13], identifier, exception3);
         }
      }

      try {
         ((HypixelPacketCallback)HypixelPacketCallback.EVENT.invoker()).onPacket(payload.getPacket());
      } catch (Exception exception4) {
         LOGGER.error(STRINGS[4], identifier, exception4);
      }
   }

   private static void registerServerbound(String identifier) {
      try {
         Id<ServerboundHypixelPayload> id = new Id<>(Identifier.of(identifier));
         PacketCodec createCodec = ServerboundHypixelPayload.createCodec(id);
         PayloadTypeRegistry.playC2S().register(id, createCodec);
         PayloadTypeRegistry.configurationC2S().register(id, createCodec);
      } catch (IllegalArgumentException illegalArgumentException) {
      }
   }

   private static void registerListeners() {
      HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket.class);
      HypixelModAPI.getInstance().createHandler(ClientboundLocationPacket.class, packet -> LOGGER.info(STRINGS[0], packet)).onError(errorReason -> LOGGER.error(STRINGS[5], errorReason));
      HypixelPacketCallback.EVENT.register((HypixelPacketCallback)packet2 -> LOGGER.info(STRINGS[7], packet2));
      HypixelApiErrorEvent.EVENT.register((HypixelApiErrorEvent)(string, errorReason2) -> LOGGER.error(STRINGS[2], string, errorReason2));
   }

   static {
      try {
      BaseModule[] var10000 = new BaseModule[2];
      long var0 = KEY ^ 39810858240462L;
      setModules(var10000);
      Cipher var2;
      Cipher var13 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var13.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[16];
      int var7 = 0;
      String var6 = "\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb%\u00bf\u0087\u00e1\u00c7\u00e7s\u00a0x\u00f1^\u008c<\u009f\u00d7\u00d0x\u00e7\u001fXM\f\u00d3\u001f8\u00c0\u00bc\u0005\u009cHN \u00cdt\u001b\u00d1^M\u001c\u00e5\u00ba\u009a\u0086T\u00bf\u001b\u0091\u00c3-\u00df\u00c6TY-\u0083\u00ca%\u000f\u00fa\u0080\u001eI.\u0089w\u00e6%?\u00ea_\u0016\u0089\u009dWD\"W%\u008a\u00acF0\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u00fboZ\u009e\r\u00c1G\u00bd\u00e1\u00eb\u00fa\u009cJ\u00b6qR\u0001x\u0014\u0014\u0017\u00ba>\u000b\u00e9\u00a98J\u00f8\u009eTB\u0096[``\"\u00e6\u008c\u0003@\u001a\u000f\u001c3\u0014\u009dL\u00a4\u00dce\u00a8\u00bf~\u0018\u00c0\u00b8c\u00e8\u0006\u001f\u0007\u00f4\u00bc\u009c5\u00a9\u00ebj\u00f7?\u009bd\u00e5\u0010V\u00dc+\u00ea\u0098\u00a1\u00857\u00f0\u0005nc{\u00fcA\u008bsn\u001en\u00c6A\u00bf\u001aO\u00acqB\u00e2M04| *\u00ad\u00fe\u00cb\u00e5x\u00ea\u00e0\u00bf'\u00f9t\u0085\u00d4\u000b\u00e0\u0093\u00c1 3\t\r\u00a3A\u00883'H\u00a8\u00a0\u008f\u009aG\u00ea\u00cci~\u001e,h\u001bN\u008f\u0088!0\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u00fboZ\u009e\r\u00c1G\u00bd\u00e1\u00eb\u00fa\u009cJ\u00b6qR\u00dc\rS\u0017O\u00e3p+Z\u00a8\u0098\u00fe\u00cc4F\u00d2\r>\u0010*\u00de\u008a\u000b\u00bb@\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u0099\u00b1y3\u0016\t\u00c1\u00c2Di\u00c9\u00fb\u00e1^h\u00b9~\u00ba\u0089\u00bc\u00c9L\u00d1/\u00b0\u00bd\u00864\u00db\u00fd\u00b8UH4sQ\u008a\u00037\u00e68\u000b\u00df\u00a1\u00ccpf\u0099\rr\u001e8\u00bf0\nz\u0018\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u0099\u00b1y3\u0016\t\u00c1\u00c2X\u00cf\u001a\u00f7h\u00af\u00e8\u0091@4| *\u00ad\u00fe\u00cb\u00e5x\u00ea\u00e0\u00bf'\u00f9t\u0085\u00d4\u000b\u00e0\u0093\u00c1 3\t\r\u00a3A\u00883'H\u00a8\u00ed\u0006/\u00a0\u00b2\u007f\u00f9R\u00e2in0\u00a7\u00f1\u00a1\u0092\u00e3\u00c9\u000e\u00f4Y\u0002\u00c6\u0005\u0006H'\u009bu\u00cb\u00aa\u00c18\u00c0\u00bc\u0005\u009cHN \u00cdT\u0004(\u0081){\u00beI\u007f\t\u008d\u00ebZ\b\u00e5\u00f50\u0006M\u0003~4\u00d3\u00a3\u009f\u0002\u00ec8\u00ab\u00c1Dq\u00fbs\u001c,\u00a3c38S\u00ca`\u0093a\u00d5\u00be @4| *\u00ad\u00fe\u00cb\u00e5x\u00ea\u00e0\u00bf'\u00f9t\u0085\u00d4\u000b\u00e0\u0093\u00c1 3\t\r\u00a3A\u00883'H\u00a8\u00ed\u0006/\u00a0\u00b2\u007f\u00f9R\u00e2in0\u00a7\u00f1\u00a1\u0092\u00e3\u00c9\u000e\u00f4Y\u0002\u00c6\u0005\u0006H'\u009bu\u00cb\u00aa\u00c1@\u001a\u000f\u001c3\u0014\u009dL\u00a4\u00dce\u00a8\u00bf~\u0018\u00c0\u00b8O\u00c2\u009a>\u00d6\u0015\u00b8\u0084\u00b5\u00a2\u008b\u00e5V\u00f0\u00c1t\u00a8c<\u00dd#\u001f\u00c7\u00d2\u00ac\u008c\u00c3:\u0086\u00b7\u00b4\u001eGX\u00a6u#\u0085;Mw\u008b\\\u00e4\u00f1\u00a2b\u00040\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00ebS\u00cc\u00c4M'}\u0098qIG\u00f4\u008a{\u00d3\u0098P}ux\u00ac\u0012\u00d2\u0011\u00cc^\u00a2\u0018\u0092\u00d3\u00ad\u008fEtML\u0094~;\u00eb\u00a404| *\u00ad\u00fe\u00cb\u00e5x\u00ea\u00e0\u00bf'\u00f9t\u0085\u00d4\u000b\u00e0\u0093\u00c1 3\t\r\u00a3A\u00883'H\u00a8\u00a0\u008f\u009aG\u00ea\u00cci~\u001e,h\u001bN\u008f\u0088!";
      int var8 = "\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb%\u00bf\u0087\u00e1\u00c7\u00e7s\u00a0x\u00f1^\u008c<\u009f\u00d7\u00d0x\u00e7\u001fXM\f\u00d3\u001f8\u00c0\u00bc\u0005\u009cHN \u00cdt\u001b\u00d1^M\u001c\u00e5\u00ba\u009a\u0086T\u00bf\u001b\u0091\u00c3-\u00df\u00c6TY-\u0083\u00ca%\u000f\u00fa\u0080\u001eI.\u0089w\u00e6%?\u00ea_\u0016\u0089\u009dWD\"W%\u008a\u00acF0\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u00fboZ\u009e\r\u00c1G\u00bd\u00e1\u00eb\u00fa\u009cJ\u00b6qR\u0001x\u0014\u0014\u0017\u00ba>\u000b\u00e9\u00a98J\u00f8\u009eTB\u0096[``\"\u00e6\u008c\u0003@\u001a\u000f\u001c3\u0014\u009dL\u00a4\u00dce\u00a8\u00bf~\u0018\u00c0\u00b8c\u00e8\u0006\u001f\u0007\u00f4\u00bc\u009c5\u00a9\u00ebj\u00f7?\u009bd\u00e5\u0010V\u00dc+\u00ea\u0098\u00a1\u00857\u00f0\u0005nc{\u00fcA\u008bsn\u001en\u00c6A\u00bf\u001aO\u00acqB\u00e2M04| *\u00ad\u00fe\u00cb\u00e5x\u00ea\u00e0\u00bf'\u00f9t\u0085\u00d4\u000b\u00e0\u0093\u00c1 3\t\r\u00a3A\u00883'H\u00a8\u00a0\u008f\u009aG\u00ea\u00cci~\u001e,h\u001bN\u008f\u0088!0\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u00fboZ\u009e\r\u00c1G\u00bd\u00e1\u00eb\u00fa\u009cJ\u00b6qR\u00dc\rS\u0017O\u00e3p+Z\u00a8\u0098\u00fe\u00cc4F\u00d2\r>\u0010*\u00de\u008a\u000b\u00bb@\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u0099\u00b1y3\u0016\t\u00c1\u00c2Di\u00c9\u00fb\u00e1^h\u00b9~\u00ba\u0089\u00bc\u00c9L\u00d1/\u00b0\u00bd\u00864\u00db\u00fd\u00b8UH4sQ\u008a\u00037\u00e68\u000b\u00df\u00a1\u00ccpf\u0099\rr\u001e8\u00bf0\nz\u0018\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u0099\u00b1y3\u0016\t\u00c1\u00c2X\u00cf\u001a\u00f7h\u00af\u00e8\u0091@4| *\u00ad\u00fe\u00cb\u00e5x\u00ea\u00e0\u00bf'\u00f9t\u0085\u00d4\u000b\u00e0\u0093\u00c1 3\t\r\u00a3A\u00883'H\u00a8\u00ed\u0006/\u00a0\u00b2\u007f\u00f9R\u00e2in0\u00a7\u00f1\u00a1\u0092\u00e3\u00c9\u000e\u00f4Y\u0002\u00c6\u0005\u0006H'\u009bu\u00cb\u00aa\u00c18\u00c0\u00bc\u0005\u009cHN \u00cdT\u0004(\u0081){\u00beI\u007f\t\u008d\u00ebZ\b\u00e5\u00f50\u0006M\u0003~4\u00d3\u00a3\u009f\u0002\u00ec8\u00ab\u00c1Dq\u00fbs\u001c,\u00a3c38S\u00ca`\u0093a\u00d5\u00be @4| *\u00ad\u00fe\u00cb\u00e5x\u00ea\u00e0\u00bf'\u00f9t\u0085\u00d4\u000b\u00e0\u0093\u00c1 3\t\r\u00a3A\u00883'H\u00a8\u00ed\u0006/\u00a0\u00b2\u007f\u00f9R\u00e2in0\u00a7\u00f1\u00a1\u0092\u00e3\u00c9\u000e\u00f4Y\u0002\u00c6\u0005\u0006H'\u009bu\u00cb\u00aa\u00c1@\u001a\u000f\u001c3\u0014\u009dL\u00a4\u00dce\u00a8\u00bf~\u0018\u00c0\u00b8O\u00c2\u009a>\u00d6\u0015\u00b8\u0084\u00b5\u00a2\u008b\u00e5V\u00f0\u00c1t\u00a8c<\u00dd#\u001f\u00c7\u00d2\u00ac\u008c\u00c3:\u0086\u00b7\u00b4\u001eGX\u00a6u#\u0085;Mw\u008b\\\u00e4\u00f1\u00a2b\u00040\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00ebS\u00cc\u00c4M'}\u0098qIG\u00f4\u008a{\u00d3\u0098P}ux\u00ac\u0012\u00d2\u0011\u00cc^\u00a2\u0018\u0092\u00d3\u00ad\u008fEtML\u0094~;\u00eb\u00a404| *\u00ad\u00fe\u00cb\u00e5x\u00ea\u00e0\u00bf'\u00f9t\u0085\u00d4\u000b\u00e0\u0093\u00c1 3\t\r\u00a3A\u00883'H\u00a8\u00a0\u008f\u009aG\u00ea\u00cci~\u001e,h\u001bN\u008f\u0088!"
         .length();
      char var5 = ' ';
      int var12 = -1;

      label27:
      while (true) {
         String var14 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var14.getBytes("ISO-8859-1"));
            String var20 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var20;
                  if ((var12 += var5) >= var8) {
                     STRINGS = var9;
                     LOGGER = LogUtils.getLogger();
                     break label27;
                  }

                  var5 = var6.charAt(var12);
                  break;
               default:
                  var9[var7++] = var20;
                  if ((var12 += var5) < var8) {
                     var5 = var6.charAt(var12);
                     continue label27;
                  }

                  var6 = "\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u0099\u00b1y3\u0016\t\u00c1\u00c2Di\u00c9\u00fb\u00e1^h\u00b9~\u00ba\u0089\u00bc\u00c9L\u00d1/\u00b0\u00bd\u00864\u00db\u00fd\u00b8U\u00fa\u008at*3\u001a_\u0094\u0094\u008e\u008f2\u00c9_\u00aa\u000fH\u001a\u000f\u001c3\u0014\u009dL\u00a4\u00a3`\u0084~86e\u00d0.\u00f8|B\u0003\u0093\u00ed\u00e4\u00ce\u00bb\u00c4\u00d1\u001c\u00b8\u00dfm\r\u001c\u00d5\f\u00cf\u0090\u00d3\u009e\u00f7\u00cd\u00ba\u00ccw\u00e8Zk`\u0018\u0080\u00b9,\u0089,S\u00c0\u009d\u0092r\u009d\u00b2n\u0005\u00c7\r=\u0097K\u008e\u0095I";
                  var8 = "\u00ce\u00a1\u0018`\u00b1\u00b3\u00d9\u00eb\u0099\u00b1y3\u0016\t\u00c1\u00c2Di\u00c9\u00fb\u00e1^h\u00b9~\u00ba\u0089\u00bc\u00c9L\u00d1/\u00b0\u00bd\u00864\u00db\u00fd\u00b8U\u00fa\u008at*3\u001a_\u0094\u0094\u008e\u008f2\u00c9_\u00aa\u000fH\u001a\u000f\u001c3\u0014\u009dL\u00a4\u00a3`\u0084~86e\u00d0.\u00f8|B\u0003\u0093\u00ed\u00e4\u00ce\u00bb\u00c4\u00d1\u001c\u00b8\u00dfm\r\u001c\u00d5\f\u00cf\u0090\u00d3\u009e\u00f7\u00cd\u00ba\u00ccw\u00e8Zk`\u0018\u0080\u00b9,\u0089,S\u00c0\u009d\u0092r\u009d\u00b2n\u0005\u00c7\r=\u0097K\u008e\u0095I"
                     .length();
                  var5 = '8';
                  var12 = -1;
            }

            var14 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }
   }

   public static void setModules(BaseModule[] values) {
      modules = values;
   }

   public static BaseModule[] getModules() {
      return modules;
   }

   private static Exception identity(Exception exception) {
      return exception;
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

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
