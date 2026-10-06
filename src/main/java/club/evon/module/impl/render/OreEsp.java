package club.evon.module.impl.render;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.OreEspEntry;
import club.evon.data.OreType;
import club.evon.event.ClientTickEvent;
import club.evon.event.RenderWorldEvent;
import club.evon.event.Subscribe;
import club.evon.manager.FrustumManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.render.BoxRenderer;
import club.evon.util.render.ColorUtil;




public final class OreEsp extends BaseModule {
   private final DoubleSetting alpha;
   private final DoubleSetting range;
   private final BooleanGroupSetting blocks;
   private final List<OreEspEntry> foundOres;
   private final Map<OreType, BooleanSupplier> enabledOres;
   private boolean needsRescan;
   private static final long KEY = KeyedRandom.create(7200598243761793562L, 4193999264566165878L, MethodHandles.lookup().lookupClass()).next(54171042252681L);
   private static final String[] STRINGS;

   public OreEsp() {
      super((STRINGS)[11], (STRINGS)[6], ModuleCategory.RENDER);

      long longValue = KEY ^ 92203697926540L;
      String[] strings = STRINGS;
      this.alpha = new DoubleSetting(strings[12], 0.5, 0.01, 1.0, 0.01);
      boolean flagSet = Animations.isFlagSet();
      this.range = new DoubleSetting(strings[10], 1.0, 1.0, 50.0, 1.0);
      this.blocks = new BooleanGroupSetting(
         strings[4],
         new BooleanSetting(strings[7], true),
         new BooleanSetting(strings[14], true),
         new BooleanSetting(strings[8], true),
         new BooleanSetting(strings[13], true),
         new BooleanSetting(strings[0], true),
         new BooleanSetting(strings[17], true),
         new BooleanSetting(strings[16], true)
      );
      this.foundOres = new ArrayList<>();
      this.enabledOres = new HashMap<>();
      this.addSettings(this.alpha, this.range, this.blocks);
      this.enabledOres.put(OreType.DIAMOND, this.blocks.getByName(strings[18])::I);
      this.enabledOres.put(OreType.GOLD, this.blocks.getByName(strings[1])::I);
      this.enabledOres.put(OreType.EMERALD, this.blocks.getByName(strings[3])::I);
      this.enabledOres.put(OreType.IRON, this.blocks.getByName(strings[9])::I);
      this.enabledOres.put(OreType.REDSTONE, this.blocks.getByName(strings[5])::I);
      this.enabledOres.put(OreType.LAPIS, this.blocks.getByName(strings[15])::I);
      boolean flag = flagSet;
      this.enabledOres.put(OreType.COAL, this.blocks.getByName(strings[2])::I);
      if (flag) {
         BaseModule.setStateArray(new BaseModule[5]);
      }
      }

   @Override
   public void onEnable() {
      this.needsRescan = true;
      super.onEnable();
   }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 73727496310413L;
      boolean flagSet = Animations.isFlagSet();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (!flagSet) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (!flagSet) {
         if (tmp.world == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      int value = tmp.player.age % 10;
      if (!flagSet) {
         if (value == 0) {
            this.requestRescan();
         }

         if (!this.needsRescan) {
            return;
         }
      } else if (value == 0) {
         return;
      }

      this.scanOres();
      this.needsRescan = false;
   }

   @Subscribe
   private void onRenderWorld(RenderWorldEvent event) {
      long longValue = KEY ^ 43124258409010L;
      MatrixStack matrixStack = event.getMatrixStack();
      boolean flagSet = Animations.isFlagSet();
      Frustum frustum = FrustumManager.getFrustum();
      boolean flag = flagSet;

      for (OreEspEntry oreEspEntry : this.foundOres) {
         Box offset = oreEspEntry.state().getCullingShape().getBoundingBox().offset(oreEspEntry.pos());
         if (!flag) {
            if (!frustum.isVisible(offset) && !flag) {
               continue;
            }

            BoxRenderer.renderBox((float)offset.minX, (float)offset.minY, (float)offset.minZ, (float)offset.maxX, (float)offset.maxY, (float)offset.maxZ, matrixStack, oreEspEntry.color().getRGB());
         }

         if (flag) {
            break;
         }
      }
   }

   private void scanOres() {
      long longValue = KEY ^ 56136183769976L;
      boolean flagSet = Animations.isFlagSet();
      this.foundOres.clear();
      int value = this.range.J().intValue();
      int value2 = -value;

      label63:
      while (true) {
         int value3 = value2;

         label61:
         while (value3 <= value) {
            int value4 = -value;

            label58:
            while (true) {
               value3 = value4;

               label56:
               while (true) {
                  if (value3 > value) {
                     break label58;
                  }

                  value3 = -value;
                  if (flagSet) {
                     continue label61;
                  }

                  int value5 = value3;

                  while (value5 <= value) {
                     label50: {
                        BlockPos add = ClientContext.MINECRAFT_CLIENT.player.getBlockPos().add(value2, value4, value5);
                        BlockState blockState = ClientContext.MINECRAFT_CLIENT.world.getBlockState(add);
                        Block block = blockState.getBlock();
                        OreEsp oreEsp = this;
                        Block tmp = block;
                        if (!flagSet) {
                           boolean oreEnabled = this.isOreEnabled(block);
                           if (flagSet) {
                              continue label56;
                           }

                           if (!oreEnabled) {
                              break label50;
                           }

                           oreEsp = this;
                           tmp = block;
                        }

                        Color oreColor = oreEsp.getOreColor(tmp);
                        this.foundOres.add(new OreEspEntry(add, blockState, oreColor));
                     }

                     value5++;
                     if (flagSet) {
                        break;
                     }
                  }

                  value4++;
                  if (flagSet) {
                     break label58;
                  }
                  break;
               }
            }

            value2++;
            if (flagSet) {
               return;
            }
            continue label63;
         }

         return;
      }
   }

   public void requestRescan() {
      this.needsRescan = true;
   }

   private Color getOreColor(Block block) {
      long longValue = KEY ^ 99013748342663L;
      boolean flagSet = Animations.isFlagSet();
      Color orElse = this.enabledOres.keySet().stream().filter(var1x -> var1x.getBlocks().stream().anyMatch(block::equals)).findFirst().map(OreType::getColor).orElse(null);
      boolean flag = flagSet;
      Color color = orElse;
      if (!flag) {
         color = orElse != null ? ColorUtil.withAlpha(orElse, this.alpha.J()) : null;
      }

      return color;
   }

   private boolean isOreEnabled(Block block) {
      return this.enabledOres.entrySet().stream().anyMatch(var1x -> {
         long longValue = KEY ^ 95586344040024L;
         boolean flagSet = Animations.isFlagSet();
         boolean asBoolean = var1x.getValue().getAsBoolean();
         if (!flagSet) {
            if (!asBoolean) {
               return false;
            }

            asBoolean = var1x.getKey().getBlocks().stream().anyMatch(block::equals);
         }

         return flagSet ? asBoolean : asBoolean;
      });
   }

   static {
      try {
      long var0 = KEY ^ 24424738923118L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[19];
      int var7 = 0;
      String var6 = "J\u008a\u00ebvIq\u00dd\u00b82p\u00b8'T\u001e\u009d\u0006\u0010\u00dd\u00d9\u00cdrH\u00c5\u009a\u0092U\u00a7\u00b5\u00b4P\u00105Z\u0010\u00fc^\u00d7y\u00d4\u008a\\\u0086){^\u00ae\u00f1\u0089\u0092w\u0010l\u00c7\u00a4\u000e\u0018\u00ae\u00af\u00f0.\u0085\u00d9T*\u00f1\u0004\u001b\b\u00e4\u00af\u00c7\u0099\u0010[z\b\u0010J\u008a\u00ebvIq\u00dd\u00b82p\u00b8'T\u001e\u009d\u0006\u00105\u0016\u00f1\u00f0j\u00f7\u00fc\u00e7G\u00f2\u00f7\u00d2\u0089\u000ba\u00f6\u0010\u00bfx\u00f6\u00ff\u008b\u00af\u0086w\u00cc\u00a3\u008fdc\u00ab\u00c1\u00fe\u0010l\u00c7\u00a4\u000e\u0018\u00ae\u00af\u00f0.\u0085\u00d9T*\u00f1\u0004\u001b\u0010{\u00f8B\u00e2>G\u0004\u00f1\bwd+\u009c\u001c\u0000\u00d7\b\u00f2x\u00f9\u00c0\u00a3\f\u0081\u00b7\bc7\u00f4q\u0014\u00bb1\u008f\b\u00f7\u00ba$f\u00ee\u00da\u00db\u00e8\u0010{\u00f8B\u00e2>G\u0004\u00f1\bwd+\u009c\u001c\u0000\u00d7\u0010\u00dd\u00d9\u00cdrH\u00c5\u009a\u0092U\u00a7\u00b5\u00b4P\u00105Z\u0010\u00fe\u00a8\u00bepm8i\u0099w{oc\u009bw\u00ee\u00c8\u0010\u00fc^\u00d7y\u00d4\u008a\\\u0086){^\u00ae\u00f1\u0089\u0092w";
      int var8 = "J\u008a\u00ebvIq\u00dd\u00b82p\u00b8'T\u001e\u009d\u0006\u0010\u00dd\u00d9\u00cdrH\u00c5\u009a\u0092U\u00a7\u00b5\u00b4P\u00105Z\u0010\u00fc^\u00d7y\u00d4\u008a\\\u0086){^\u00ae\u00f1\u0089\u0092w\u0010l\u00c7\u00a4\u000e\u0018\u00ae\u00af\u00f0.\u0085\u00d9T*\u00f1\u0004\u001b\b\u00e4\u00af\u00c7\u0099\u0010[z\b\u0010J\u008a\u00ebvIq\u00dd\u00b82p\u00b8'T\u001e\u009d\u0006\u00105\u0016\u00f1\u00f0j\u00f7\u00fc\u00e7G\u00f2\u00f7\u00d2\u0089\u000ba\u00f6\u0010\u00bfx\u00f6\u00ff\u008b\u00af\u0086w\u00cc\u00a3\u008fdc\u00ab\u00c1\u00fe\u0010l\u00c7\u00a4\u000e\u0018\u00ae\u00af\u00f0.\u0085\u00d9T*\u00f1\u0004\u001b\u0010{\u00f8B\u00e2>G\u0004\u00f1\bwd+\u009c\u001c\u0000\u00d7\b\u00f2x\u00f9\u00c0\u00a3\f\u0081\u00b7\bc7\u00f4q\u0014\u00bb1\u008f\b\u00f7\u00ba$f\u00ee\u00da\u00db\u00e8\u0010{\u00f8B\u00e2>G\u0004\u00f1\bwd+\u009c\u001c\u0000\u00d7\u0010\u00dd\u00d9\u00cdrH\u00c5\u009a\u0092U\u00a7\u00b5\u00b4P\u00105Z\u0010\u00fe\u00a8\u00bepm8i\u0099w{oc\u009bw\u00ee\u00c8\u0010\u00fc^\u00d7y\u00d4\u008a\\\u0086){^\u00ae\u00f1\u0089\u0092w"
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

                  var6 = "\u00fe\u00a8\u00bepm8i\u0099w{oc\u009bw\u00ee\u00c8\u0010\u00bfx\u00f6\u00ff\u008b\u00af\u0086w\u00cc\u00a3\u008fdc\u00ab\u00c1\u00fe";
                  var8 = "\u00fe\u00a8\u00bepm8i\u0099w{oc\u009bw\u00ee\u00c8\u0010\u00bfx\u00f6\u00ff\u008b\u00af\u0086w\u00cc\u00a3\u008fdc\u00ab\u00c1\u00fe"
                     .length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var20) {
         throw new RuntimeException(var20);
      }
   }

   private static JSONException identity(JSONException exception) {
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
