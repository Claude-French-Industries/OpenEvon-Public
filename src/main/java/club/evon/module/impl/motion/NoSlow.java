package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.ItemUseType;
import club.evon.data.SlotSwitchMode;
import club.evon.event.ClientTickEvent;
import club.evon.event.Subscribe;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.ModeSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.UseActionSwitchMap;




public final class NoSlow extends BaseModule {
   private final ModeSetting mode;
   private final BooleanSetting swords;
   private final BooleanSetting bows;
   private final BooleanSetting consumables;
   private final BooleanGroupSetting items;
   private ItemUseType itemUseType;
   private static final long KEY = KeyedRandom.create(-7784866149402973330L, 5827412608833374097L, MethodHandles.lookup().lookupClass()).next(114873740372928L);
   private static final String[] STRINGS;

   public NoSlow() {
      super((STRINGS)[4], (STRINGS)[0], ModuleCategory.MOTION);

      String[] strings = STRINGS;
      this.mode = new ModeSetting(strings[1], new NoSlowMode(strings[7], this));
      this.swords = new BooleanSetting(strings[5], true);
      this.bows = new BooleanSetting(strings[2], true);
      this.consumables = new BooleanSetting(strings[6], true);
      this.items = new BooleanGroupSetting(strings[3], this.swords, this.bows, this.consumables);
      this.itemUseType = ItemUseType.NONE;
      this.addSettings(this.mode);
      }

   public boolean canSprint() {
      return true;
   }

   @Subscribe(h = 2)
   public void onTick(ClientTickEvent event) {
      long longValue = KEY ^ 67919277472119L;
      BaseModule[] values = Scaffold.z();
      if (values == null) {
         ItemStack stack;
         label78:
         if (ClientContext.MINECRAFT_CLIENT.player != null) {
            MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
            if (values == null) {
               if (ClientContext.MINECRAFT_CLIENT.currentScreen != null) {
                  break label78;
               }

               tmp = ClientContext.MINECRAFT_CLIENT;
            }

            if (tmp.getOverlay() == null) {
               label70: {
                  SilentSlotManager instance = SilentSlotManager.getInstance();
                  SilentSlotManager silentSlotManager = instance;
                  if (values == null) {
                     if (instance.getMode() != SlotSwitchMode.FULL) {
                        stack = ClientContext.MINECRAFT_CLIENT.player.getMainHandStack();
                        break label70;
                     }

                     silentSlotManager = instance;
                  }

                  stack = silentSlotManager.getMainHandStack(ClientContext.MINECRAFT_CLIENT.player);
               }

               label63: {
                  label85: {
                     ItemStack stack2;
                     label61: {
                        stack2 = stack;
                        if (values == null) {
                           switch (UseActionSwitchMap.SWITCH_MAP[stack2.getUseAction().ordinal()]) {
                              case 1:
                                 this.itemUseType = ItemUseType.BLOCKABLE;
                                 break;
                              case 2:
                                 break label61;
                              case 3:
                                 break label85;
                              default:
                                 break label63;
                           }
                        }

                        if (values == null) {
                           return;
                        }
                     }

                     this.itemUseType = stack2.isIn(ItemTags.SWORDS) ? ItemUseType.BLOCKABLE : ItemUseType.NONE;
                     if (values == null) {
                        return;
                     }
                  }

                  this.itemUseType = ItemUseType.BOW;
                  if (values == null) {
                     return;
                  }
               }

               this.itemUseType = ItemUseType.USEABLE;
               return;
            }
         }

         this.itemUseType = ItemUseType.NONE;
      }
   }

   public String getModeName() {
      return this.mode.getValue().getName();
   }

   public ModeSetting getMode() {
      return this.mode;
   }

   public BooleanGroupSetting getItemGroup() {
      return this.items;
   }

   public ItemUseType getItemUseType() {
      return this.itemUseType;
   }

   static {
      try {
      long var0 = KEY ^ 82050564094476L;
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
      String var6 = "R\u00d6\u00b1\u00be\u00b2\u00c1u\u0000\u00e7\u0095|\u00bc\u00c8\u00f4\u0017\u0019\b\u0006n\u0084Wq!\u0098\u0093\b\u001bK_,\u00e9J\u00eb\u0005\bi\u00a8\u00c5\u00ab\u0088-G]\b\u00c2r\u00a2V\u00e9\u0016\u00de\u00cc\b\u0097\u0097AA7\u0084'h";
      int var8 = "R\u00d6\u00b1\u00be\u00b2\u00c1u\u0000\u00e7\u0095|\u00bc\u00c8\u00f4\u0017\u0019\b\u0006n\u0084Wq!\u0098\u0093\b\u001bK_,\u00e9J\u00eb\u0005\bi\u00a8\u00c5\u00ab\u0088-G]\b\u00c2r\u00a2V\u00e9\u0016\u00de\u00cc\b\u0097\u0097AA7\u0084'h"
         .length();
      char var5 = 16;
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeUtf8(var10).intern();
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

                  var6 = "5\u00a4\u00ea\u00f2\u007f\u00f2\u00d6\u0098&\u00894\u00fc\u00d14\u001cq\b.\u00d3hh\u0092c\u00b9\u0086";
                  var8 = "5\u00a4\u00ea\u00f2\u007f\u00f2\u00d6\u0098&\u00894\u00fc\u00d14\u001cq\b.\u00d3hh\u0092c\u00b9\u0086".length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var10004) {
         throw new RuntimeException("club/evon/module/impl/motion/NoSlow", var10004);
      }
   }

   private static JSONException rethrow(JSONException exception) {
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
}
