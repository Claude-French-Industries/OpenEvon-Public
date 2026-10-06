package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ProjectileItem;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.ItemUseCooldownEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.KeyedRandom;




public final class FastPlace extends BaseModule {
   private final DoubleSetting cooldown;
   private final BooleanGroupSetting items;
   private static final long KEY = KeyedRandom.create(7371572217725923052L, 3525567367814759008L, MethodHandles.lookup().lookupClass()).next(172172167401164L);
   private static final String[] STRINGS;

   public FastPlace() {
      super((STRINGS)[7], (STRINGS)[4], ModuleCategory.PLAYER);

      String[] strings = STRINGS;
      this.cooldown = new DoubleSetting(strings[3], 0.0, 0.0, 4.0, 1.0);
      this.items = new BooleanGroupSetting(strings[0], new BooleanSetting(strings[6], true), new BooleanSetting(strings[5], true));
      this.addSettings(this.cooldown, this.items);
      }

   @Subscribe
   private void onItemUseCooldown(ItemUseCooldownEvent event) {
      boolean flag;
      Item item;
      boolean flag2;
      label68: {
         label71: {
            long longValue = KEY ^ 33157162517344L;
            flag2 = Breaker.A();
            item = ClientContext.MINECRAFT_CLIENT.player.getMainHandStack().getItem();
            flag = flag2;
            flag2 = this.items.getByName(STRINGS[2]).I();
            if (!flag) {
               if (!flag2) {
                  break label71;
               }

               flag2 = item instanceof BlockItem;
            }

            if (flag) {
               break label68;
            }

            if (flag2) {
               flag2 = true;
               break label68;
            }
         }

         flag2 = false;
      }

      boolean flag3;
      label57: {
         label72: {
            flag3 = flag2;
            flag2 = this.items.getByName(STRINGS[1]).I();
            if (!flag) {
               if (!flag2) {
                  break label72;
               }

               flag2 = item instanceof ProjectileItem;
            }

            if (flag) {
               break label57;
            }

            if (flag2) {
               flag2 = true;
               break label57;
            }
         }

         flag2 = false;
      }

      label45: {
         boolean flag4 = flag2;
         flag2 = flag3;
         if (!flag) {
            if (flag3) {
               break label45;
            }

            flag2 = flag4;
         }

         if (!flag2) {
            return;
         }
      }

      event.setCooldown(this.cooldown.J().intValue());
   }

   static {
      try {
      long var0 = KEY ^ 51234296764963L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[8];
      int var7 = 0;
      String var6 = "r\u0081\u00ce\u00b3$\u00b0V\u00bb\u0010\u00bc\u00e5\u0019\u00ecQ\u0011Zx+\u0088V\u0082\u00bc\u00a6]\u00a5\bPG\u0002nWnZ\u0001\u0010\u00e5\u00eb=\u0012\u00b0\u00ba\u00cd\u000b\u0003\u00dd\u0015\u00b1\u00f3\\/S\u0018LJMn\u00dfKA\u00ff\u0004\u00d0\u00ba\u00fa\u00a3(cBD\u00a9)+\u00a0\u0015\u00a0\"\u0010\u00bc\u00e5\u0019\u00ecQ\u0011Zx+\u0088V\u0082\u00bc\u00a6]\u00a5";
      int var8 = "r\u0081\u00ce\u00b3$\u00b0V\u00bb\u0010\u00bc\u00e5\u0019\u00ecQ\u0011Zx+\u0088V\u0082\u00bc\u00a6]\u00a5\bPG\u0002nWnZ\u0001\u0010\u00e5\u00eb=\u0012\u00b0\u00ba\u00cd\u000b\u0003\u00dd\u0015\u00b1\u00f3\\/S\u0018LJMn\u00dfKA\u00ff\u0004\u00d0\u00ba\u00fa\u00a3(cBD\u00a9)+\u00a0\u0015\u00a0\"\u0010\u00bc\u00e5\u0019\u00ecQ\u0011Zx+\u0088V\u0082\u00bc\u00a6]\u00a5"
         .length();
      char var5 = '\b';
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeString(var10).intern();
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

                  var6 = "PG\u0002nWnZ\u0001\u0010*\u00b1\u00cb\u00ea\u00e8*kR\u00be\u00b7Ow\u00bf0\u00db\u00ab";
                  var8 = "PG\u0002nWnZ\u0001\u0010*\u00b1\u00cb\u00ea\u00e8*kR\u00be\u00b7Ow\u00bf0\u00db\u00ab".length();
                  var5 = '\b';
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

   private static JSONException identity(JSONException exception) {
      return exception;
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
}
