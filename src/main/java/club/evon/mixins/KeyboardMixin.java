package club.evon.mixins;
import java.util.Optional;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.util.FileDialogUtil;
import club.evon.api.MicrosoftLoginManager;
import club.evon.data.AccountData;
import club.evon.event.EventBus;
import club.evon.event.KeyPressEvent;
import club.evon.manager.ChatNameManager;
import club.evon.ui.AltManagerPlaceholderScreen;
import club.evon.ui.ClickGuiScreen;
import club.evon.ui.ScreenManager;




@Mixin(Keyboard.class)
public final class KeyboardMixin {
   @Shadow
   @Final
   private MinecraftClient client;
   private static final String[] a;

   @Inject(method = "onKey(JILnet/minecraft/client/input/KeyInput;)V", at = @At("HEAD"), cancellable = true)
   public void injectKey(long window, int action, KeyInput input, CallbackInfo ci) {
      if (this.client != null && EvonClient.getInstance() != null) {
         int value = input.key();
         int value2 = input.modifiers();
         KeyPressEvent keyPressEvent = new KeyPressEvent(action, value);
         EventBus.post(keyPressEvent);
         if (keyPressEvent.isCanceled()) {
            ci.cancel();
         }

         if (this.client.world != null && this.client.player != null && value == 344 && action == 1) {
            ClickGuiScreen clickGuiScreen = EvonClient.getInstance().getClickGuiScreen();
            if (this.client.currentScreen != clickGuiScreen) {
               if (ClientContext.MINECRAFT_CLIENT.currentScreen == null) {
                  this.client.setScreen(clickGuiScreen);
               }
            } else {
               clickGuiScreen.keyPressed(new KeyInput(256, 0, 0));
            }
         }

         if (ScreenManager.getCurrentScreen() instanceof AltManagerPlaceholderScreen && action == 1) {
            if (this.client.player == null && value == 73) {
               try {
                  MicrosoftLoginManager.startMicrosoftLogin(microsoftAuthResult -> {});
               } catch (Exception exception) {
               }
            } else if (value == 79) {
               try {
                  MinecraftClientAccessor minecraftClientAccessor = (MinecraftClientAccessor)ClientContext.MINECRAFT_CLIENT;
                  AccountData importAccountFromFile = MicrosoftLoginManager.importAccountFromFile();
                  if (importAccountFromFile != null) {
                     EvonClient.getExecutor().execute(() -> {
                        String[] strings = a;
                        FileDialogUtil.showMessage(strings[1], String.format(strings[0], importAccountFromFile.getUsername()), 1);
                     });
                     minecraftClientAccessor.setSession(new Session(importAccountFromFile.getUsername(), MicrosoftLoginManager.parseUuid(importAccountFromFile.getUuid()), importAccountFromFile.getToken(), Optional.empty(), Optional.empty()));
                     ChatNameManager.getInstance().y(ClientContext.MINECRAFT_CLIENT.getSession().getUsername());
                  }
               } catch (Exception exception2) {
                  exception2.fillInStackTrace();
               }
            } else if (value == 80) {
               MicrosoftLoginManager.loginWithClipboardToken();
            }
         }
      }
   }

   private static Exception a(Exception exception) {
      return exception;
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[2];
      int var3 = 0;
      String var2 = "^zUmg?\u001e{{Fe\"~M<\u0010Wc]d\"\u000fQyp\\*N4Y{{";
      int var4 = "^zUmg?\u001e{{Fe\"~M<\u0010Wc]d\"\u000fQyp\\*N4Y{{".length();
      char var1 = 15;
      int var0 = -1;

      while (true) {
         char[] var10002;
         label38: {
            char[] var10003 = var2.substring(++var0, var0 + var1).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 80;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 80;
               var10006 = var6;
            } else {
               var13 = 80;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label38;
               }

               var10005 = var10003;
               var16 = 80;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 66;
                  case 1 -> 69;
                  case 2 -> 98;
                  case 3 -> 90;
                  case 4 -> 82;
                  case 5 -> 11;
                  default -> 110;
               });
               var6++;
               if (var13 == 0) {
                  var10006 = var13;
                  var10005 = var10002;
                  var16 = var13;
               } else {
                  if (var8 <= var6) {
                     break;
                  }

                  var10005 = var10002;
                  var16 = var13;
                  var10006 = var6;
               }
            }
         }

         String var15 = new String(var10002).intern();
         byte var10 = -1;
         var5[var3++] = var15;
         if ((var0 += var1) >= var4) {
            a = var5;
            break;
         }

         var1 = var2.charAt(var0);
      }
   }
}
