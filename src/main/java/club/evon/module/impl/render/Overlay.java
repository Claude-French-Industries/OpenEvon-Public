package club.evon.module.impl.render;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.ColorMode;
import club.evon.data.ColorPair;
import club.evon.data.HudModuleEntry;
import club.evon.data.ModuleDisplayMode;
import club.evon.event.HudRenderEvent;
import club.evon.event.HudRenderPassEvent;
import club.evon.event.JoinWorldEvent;
import club.evon.event.Subscribe;
import club.evon.manager.FontManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.ColorSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.setting.ModeSetting;
import club.evon.setting.ThemeSetting;
import club.evon.ui.ColorTheme;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;
import club.evon.util.math.RandomUtil;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.FontRenderer;
import club.evon.util.render.NvgFont;
import club.evon.util.render.RenderUtil;




public final class Overlay extends BaseModule {
   private final DoubleSetting scale;
   private final ModeSetting watermark;
   private final ModeSetting blockCounter;
   private final EnumSetting<ModuleDisplayMode> moduleMode;
   private final DoubleSetting opacity;
   private final DoubleSetting offset;
   private final BooleanSetting background;
   private final BooleanSetting tags;
   private final BooleanSetting lowerCase;
   private final BooleanSetting noSpaces;
   private final EnumSetting<ColorMode> colorType;
   private final ColorSetting color;
   private final ColorSetting secondColor;
   private final ThemeSetting<ColorTheme> theme;
   private static final FontManager FONT_MANAGER;
   private static final NvgFont FONT_MEDIUM;
   private static final NvgFont FONT_SEMI_BOLD;
   private float hudBottom;
   private List<HudModuleEntry> entries;
   private List<HudModuleEntry> visibleEntries;
   private boolean needsRefresh;
   private static Overlay instance;
   private final FontRenderer fontRenderer;
   public static final ColorPair BUBBLEGUM;
   public static final ColorPair GRAY;
   public static final ColorPair GRAY_2;
   public static final ColorPair GREEN;
   public static final ColorPair RED;
   public static final ColorPair EVON;
   public static final ColorPair PURPLE;
   public static final ColorPair RED_2;
   public static final ColorPair PEARL;
   public static final ColorPair OCEAN_PISS;
   private static String[] externalStrings;
   private static final long KEY = KeyedRandom.create(-3469139698921779912L, -3414952594737710211L, MethodHandles.lookup().lookupClass()).next(236158656571453L);
   private static final String[] STRINGS;

   public Overlay() {
      super((STRINGS)[0], (STRINGS)[25], ModuleCategory.RENDER);

      String[] strings = STRINGS;
      this.scale = new DoubleSetting(strings[3], 1.0, 0.8, 1.3, 0.05);
      this.watermark = new ModeSetting(strings[20], new EvonWatermark(strings[19], this));
      this.blockCounter = new ModeSetting(strings[1], new ScaffoldHud(strings[5], this));
      this.moduleMode = new EnumSetting<>(strings[15], ModuleDisplayMode.ALL);
      this.opacity = new DoubleSetting(strings[6], 0.4, 0.3, 0.6, 0.1);
      this.offset = new DoubleSetting(strings[9], 0.0, 0.0, 20.0, 1.0);
      this.background = new BooleanSetting(strings[10], true);
      this.tags = new BooleanSetting(strings[13], true);
      this.lowerCase = new BooleanSetting(strings[4], false);
      this.noSpaces = new BooleanSetting(strings[24], false);
      this.colorType = new EnumSetting<>(strings[8], ColorMode.THEMED);
      this.color = new ColorSetting(strings[12], EVON.getColorOne());
      this.secondColor = new ColorSetting(strings[11], EVON.getColorTwo());
      this.theme = new ThemeSetting<>(strings[7], ColorTheme.RED);
      this.entries = new ArrayList<>();
      this.visibleEntries = new ArrayList<>();
      this.fontRenderer = EvonClient.getInstance().getFontRenderer();
      this.addSettings(this.scale, this.watermark, this.blockCounter, this.moduleMode, this.background, this.offset, this.opacity, this.lowerCase, this.tags, this.noSpaces, this.colorType, this.color, this.secondColor, this.theme);
      this.color.setVisible(() -> {
         long var1x = KEY ^ 42726439037429L;
         boolean flagUnset = Animations.isFlagUnset();
         Object value = this.colorType.getValue();
         ColorMode tmp = ColorMode.SINGLE;
         if (flagUnset) {
            if (value == ColorMode.SINGLE) {
               return true;
            }

            value = this.colorType.getValue();
            tmp = ColorMode.DOUBLE;
         }

         return value == tmp;
      });
      this.secondColor.setVisible(() -> {
         long var1x = KEY ^ 75339619029965L;
         return this.colorType.getValue() == ColorMode.DOUBLE;
      });
      this.theme.setVisible(() -> {
         long var1x = KEY ^ 40518382694255L;
         return this.colorType.getValue() == ColorMode.THEMED;
      });
      this.setEnabled(true);
      instance = this;
      }

   public static Color getPrimaryColor() {
      long longValue = KEY ^ 70640668617730L;
      boolean flagUnset = Animations.isFlagUnset();
      Object value = instance.colorType.getValue();
      if (flagUnset) {
         if (value == ColorMode.THEMED) {
            return instance.theme.getValue().getColorPair().getColorOne();
         }

         value = instance.color.getValue();
      }

      return (Color)value;
   }

   public static Color getSecondaryColor() {
      long longValue = KEY ^ 67395880758624L;
      boolean flagSet = Animations.isFlagSet();
      Object value = instance.colorType.getValue();
      ColorMode tmp = ColorMode.THEMED;
      if (!flagSet) {
         if (value == ColorMode.THEMED) {
            return instance.theme.getValue().getColorPair().getColorTwo();
         }

         value = instance.colorType.getValue();
         if (flagSet) {
            return (Color)value;
         }

         tmp = ColorMode.SINGLE;
      }

      return value == tmp ? instance.color.getValue() : instance.secondColor.getValue();
   }

   @Subscribe(h = -20)
   private void onHudRenderPass(HudRenderPassEvent event) {
      long longValue = KEY ^ 121304310316348L;
      boolean flagSet = Animations.isFlagSet();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (!flagSet) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      int value;
      if (flagSet) {
         value = tmp.options.hudHidden ? 1 : 0;
      } else {
         if (tmp.options.hudHidden) {
            return;
         }

         value = Math.round(8.0F * getScale() * 2.0F);
      }

      float floatValue = value / 2.0F;
      this.entries = this.entries.stream().sorted(Comparator.comparingDouble(var2x -> -this.fontRenderer.getStringWidth(STRINGS[14], club.evon.util.render.FontWeight.MEDIUM, this.buildModuleLabel(var2x.getModule()), floatValue))).toList();
      this.visibleEntries = this.entries.stream().filter(var1x -> {
         long var2x = KEY ^ 24862280522640L;
         boolean var4x = Animations.isFlagUnset();
         boolean var10000x = var1x.getModule().isEnabled();
         if (var4x) {
            if (!var10000x) {
               return false;
            }

            var10000x = this.shouldDisplay(var1x.getModule());
         }

         return !var4x ? var10000x : var10000x;
      }).toList();
      float floatValue2 = 2.0F;
      float floatValue3 = Math.max(this.offset.J().floatValue() + 2.0F, 2.0F);
      boolean flag = this.background.I();
      float floatValue4 = floatValue3;
      Iterator iterator = this.entries.iterator();

      while (true) {
         if (iterator.hasNext()) {
            HudModuleEntry module = (HudModuleEntry)iterator.next();
            boolean enabled = module.getModule().isEnabled();
            if (flagSet) {
               break;
            }

            float floatValue5;
            BaseModule tmp2;
            label94: {
               module.getAnimator().setTargetValue(enabled ? 1.0 : 0.0);
               floatValue5 = module.getAnimator().getValue().floatValue();
               boolean finished = module.getAnimator().isFinished();
               label73:
               if (!flagSet) {
                  if (finished) {
                     float floatValue6 = floatValue5 - 0.0F;
                     finished = floatValue6 != 0.0F;
                     if (flagSet) {
                        break label73;
                     }

                     if (!finished) {
                        continue;
                     }
                  }

                  tmp2 = module.getModule();
                  if (flagSet) {
                     break label94;
                  }

                  finished = this.shouldDisplay(tmp2);
               }

               if (!finished && !flagSet) {
                  continue;
               }

               tmp2 = module.getModule();
            }

            String buildModuleLabel = this.buildModuleLabel(tmp2);
            String[] strings = STRINGS;
            float floatValue7 = this.fontRenderer.getStringHeight(strings[14], club.evon.util.render.FontWeight.MEDIUM, buildModuleLabel, floatValue);
            float floatValue8 = floatValue7 + 3.0F;
            float floatValue9 = floatValue4;
            if (!flagSet) {
               if (flag) {
                  float floatValue10 = this.fontRenderer.getStringWidth(STRINGS[14], club.evon.util.render.FontWeight.MEDIUM, buildModuleLabel, floatValue);
                  float floatValue11 = ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth() - (floatValue3 + floatValue10) * floatValue5 + 5.0F * (1.0F - floatValue5);
                  int value2 = new Color(0, 0, 0, 255).getRGB();
                  RenderUtil.fillRectArgb(floatValue11 - 2.0F, floatValue9 - 2.0F, floatValue10 + 4.0F, floatValue8, value2);
                  RenderUtil.fillRectPaint(floatValue11 - 2.0F, floatValue9 - 2.0F, floatValue10 + 4.0F, floatValue8, RenderUtil.hudBlurPaint);
               }

               floatValue4 += module.getAnimator().getValue().floatValue() * floatValue8;
            }

            if (!flagSet) {
               continue;
            }
         }

         this.hudBottom = floatValue4;
         break;
      }
   }

   @Subscribe(h = -20)
   private void onHudRender(HudRenderEvent event) {
      long longValue = KEY ^ 63475354898011L;
      boolean flagSet = Animations.isFlagSet();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (!flagSet) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      int value;
      if (flagSet) {
         value = tmp.options.hudHidden ? 1 : 0;
      } else {
         if (tmp.options.hudHidden) {
            return;
         }

         this.hudBottom = 0.0F;
         value = ClientContext.MINECRAFT_CLIENT.getWindow().getScaledHeight();
      }

      int value2 = value;
      boolean flag = this.background.I();
      tmp = ClientContext.MINECRAFT_CLIENT;
      if (!flagSet) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (tmp.world != null) {
         label115: {
            label114:
            if (!flagSet) {
               if (!this.entries.isEmpty()) {
                  if (flagSet) {
                     break label114;
                  }

                  if (!this.needsRefresh) {
                     break label115;
                  }
               }

               this.refreshEntries();
            }

            this.needsRefresh = false;
         }

         Color primaryColor = getPrimaryColor();
         Color secondaryColor = getSecondaryColor();
         float floatValue = 2.0F;
         float floatValue2 = Math.max(this.offset.J().floatValue(), 0.0F) + 2.0F;
         float floatValue3 = Math.round(8.0F * getScale() * 2.0F) / 2.0F;
         float floatValue4 = floatValue2;
         RenderUtil.saveState();
         RenderUtil.translate(-10.0F, -10.0F);
         Iterator iterator = this.entries.iterator();

         while (true) {
            if (iterator.hasNext()) {
               HudModuleEntry module = (HudModuleEntry)iterator.next();
               boolean enabled = module.getModule().isEnabled();
               if (flagSet) {
                  break;
               }

               float floatValue5;
               BaseModule tmp2;
               label137: {
                  module.getAnimator().setTargetValue(enabled ? 1.0 : 0.0);
                  floatValue5 = module.getAnimator().getValue().floatValue();
                  boolean finished = module.getAnimator().isFinished();
                  label98:
                  if (!flagSet) {
                     if (finished) {
                        float floatValue6 = floatValue5 - 0.0F;
                        finished = floatValue6 != 0.0F;
                        if (flagSet) {
                           break label98;
                        }

                        if (!finished) {
                           continue;
                        }
                     }

                     tmp2 = module.getModule();
                     if (flagSet) {
                        break label137;
                     }

                     finished = this.shouldDisplay(tmp2);
                  }

                  if (!finished) {
                     continue;
                  }

                  tmp2 = module.getModule();
               }

               String text;
               float floatValue7;
               float floatValue8;
               float floatValue9;
               int value3;
               label88: {
                  text = this.buildModuleLabel(tmp2);
                  String[] strings = STRINGS;
                  float floatValue10 = this.fontRenderer.getStringWidth(strings[16], club.evon.util.render.FontWeight.MEDIUM, text, floatValue3);
                  float floatValue11 = this.fontRenderer.getStringHeight(strings[14], club.evon.util.render.FontWeight.MEDIUM, text, floatValue3);
                  floatValue7 = floatValue11 + 3.0F;
                  floatValue8 = floatValue4;
                  floatValue9 = ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth() - (floatValue2 + floatValue10) * floatValue5 + 5.0F * (1.0F - floatValue5);
                  value3 = (int)(floatValue5 * 255.0F);
                  int value4 = new Color(0, 0, 0, (int)(this.opacity.J().floatValue() * 255.0F)).getRGB();
                  if (!flagSet) {
                     if (!flag) {
                        break label88;
                     }

                     RenderUtil.fillRectPaint(floatValue9 - 2.0F + 10.0F, floatValue8 - 2.0F + 10.0F, floatValue10 + 4.0F, floatValue7, RenderUtil.gameBlurPaint);
                  }

                  RenderUtil.fillRectArgb(floatValue9 - 2.0F + 10.0F, floatValue8 - 2.0F + 10.0F, floatValue10 + 4.0F, floatValue7, value4);
               }

               Color sinusoidalBlend = ColorUtil.sinusoidalBlend(primaryColor, secondaryColor, 500.0, floatValue4 / 20.0F);
               String[] strings2 = STRINGS;
               this.fontRenderer.drawStringWithShadow(strings2[14], club.evon.util.render.FontWeight.MEDIUM, text, floatValue9 + 10.0F, floatValue8 + 0.5F + 10.0F, floatValue3, ColorUtil.withAlpha(sinusoidalBlend, value3).getRGB(), true);
               floatValue4 += module.getAnimator().getValue().floatValue() * floatValue7;
               if (!module.getAnimator().isFinished()) {
               }

               if (!flagSet) {
                  continue;
               }
            }

            RenderUtil.restoreState();
            this.hudBottom = floatValue4;
            break;
         }

         float floatValue12 = 8.0F * getScale();
         double doubleValue = RandomUtil.round(MovementUtil.getBlocksPerSecond(), 2);
         String[] strings3 = STRINGS;
         String text2 = strings3[2] + doubleValue;
         String text3 = strings3[18] + ClientContext.MINECRAFT_CLIENT.getCurrentFps();
         String text4 = strings3[21];
         String valueOf = String.valueOf(ClientContext.MINECRAFT_CLIENT.getCurrentFps());
         String text5 = strings3[22];
         String valueOf2 = String.valueOf(RandomUtil.round(MovementUtil.getBlocksPerSecond(), 2));
         this.fontRenderer
            .drawGradientString(
               strings3[14],
               club.evon.util.render.FontWeight.SEMI_BOLD,
               text5,
               3.0F,
               value2 - this.fontRenderer.getStringHeight(strings3[14], club.evon.util.render.FontWeight.SEMI_BOLD, text3, floatValue12) - 2.0F - this.fontRenderer.getStringHeight(strings3[14], club.evon.util.render.FontWeight.SEMI_BOLD, text2, floatValue12) - 2.0F,
               floatValue12,
               getPrimaryColor().getRGB(),
               getSecondaryColor().getRGB(),
               true,
               0.0F,
               9
            );
         FONT_MEDIUM.drawText(
            valueOf2,
            3.0F + this.fontRenderer.getStringWidth(strings3[14], club.evon.util.render.FontWeight.MEDIUM, text5, floatValue12),
            value2 - this.fontRenderer.getStringHeight(strings3[14], club.evon.util.render.FontWeight.SEMI_BOLD, text2, floatValue12) - 2.0F - this.fontRenderer.getStringHeight(strings3[14], club.evon.util.render.FontWeight.SEMI_BOLD, text2, floatValue12) - 2.0F,
            floatValue12,
            new Color(255, 255, 255, 179).getRGB(),
            true
         );
         this.fontRenderer.drawGradientString(strings3[14], club.evon.util.render.FontWeight.SEMI_BOLD, text4, 3.0F, value2 - FONT_SEMI_BOLD.getTextHeight(text2, floatValue12) - 2.0F, floatValue12, getPrimaryColor().getRGB(), getSecondaryColor().getRGB(), true, 0.0F, 9);
         FONT_MEDIUM.drawText(
            valueOf,
            3.0F + this.fontRenderer.getStringWidth(strings3[14], club.evon.util.render.FontWeight.MEDIUM, text4, floatValue12),
            value2 - this.fontRenderer.getStringHeight(strings3[14], club.evon.util.render.FontWeight.SEMI_BOLD, text2, floatValue12) - 2.0F,
            floatValue12,
            new Color(255, 255, 255, 179).getRGB(),
            true
         );
      }
   }

   private String formatModuleName(BaseModule module) {
      long longValue = KEY ^ 127611654100614L;
      boolean flagSet = Animations.isFlagSet();
      String name = module.getName();
      boolean flag = flagSet;
      flagSet = this.noSpaces.I();
      if (!flag) {
         if (flagSet) {
            name = name.replace(" ", "");
         }

         flagSet = this.lowerCase.I();
      }

      if (flagSet) {
         name = name.toLowerCase();
      }

      return name;
   }

   private String formatModuleInfo(BaseModule module) {
      long longValue = KEY ^ 67029172556821L;
      String suffix = module.getSuffix();
      if (suffix == null) {
         return null;
      }

      if (this.lowerCase.I()) {
         suffix = suffix.toLowerCase();
      }

      return suffix;
   }

   private String buildModuleLabel(BaseModule module) {
      long longValue = KEY ^ 1638831923710L;
      boolean flagSet = Animations.isFlagSet();
      String formatModuleName = this.formatModuleName(module);
      String formatModuleInfo = this.formatModuleInfo(module);
      boolean flag = flagSet;
      if (this.tags.I()) {
         String text = formatModuleInfo;
         if (!flag) {
            if (formatModuleInfo == null) {
               return formatModuleName;
            }

            text = formatModuleName + " " + Formatting.GRAY + formatModuleInfo;
         }

         return text;
      } else {
         return formatModuleName;
      }
   }

   public boolean shouldDisplay(BaseModule module) {
      long longValue = KEY ^ 33066436666874L;
      boolean flagUnset = Animations.isFlagUnset();
      int value = this.moduleMode.getValue().ordinal();
      if (flagUnset) {
         switch (value) {
            case 0:
               return true;
            case 1:
               return module.getCategory() != ModuleCategory.RENDER;
         case 2:
            value = module.getBindSetting().getValue();
            return value != -1;
         default:
            throw new MatchException(null, null);
      }
   }

   return value != 0;
}

   @Override
   public void onEnable() {
      this.needsRefresh = true;
      super.onEnable();
   }

   public void refreshEntries() {
      this.entries = new ArrayList<>();
      EvonClient.getInstance()
         .getModuleManager()
         .getModules()
         .stream()
         .sorted(Comparator.comparingDouble(module -> -this.fontRenderer.getStringWidth(STRINGS[14], club.evon.util.render.FontWeight.MEDIUM, this.buildModuleLabel(module), 16.0F)))
         .forEach(module2 -> this.entries.add(new HudModuleEntry(module2)));
   }

   @Subscribe
   private void onJoinWorld(JoinWorldEvent event) {
      this.needsRefresh = true;
   }

   public static float getScale() {
      long longValue = KEY ^ 122866882778335L;
      boolean flagSet = Animations.isFlagSet();
      Object tmp = instance;
      if (!flagSet) {
         if (instance == null) {
            return 1.0F;
         }

         tmp = instance.scale.J();
      }

      return ((Double)tmp).floatValue();
   }

   public static Color getRainbowColor(double time, double doubleValue, double speed, float saturation, float brightness) {
      float floatValue = (float)((time * speed + doubleValue) % 1.0);
      return Color.getHSBColor(floatValue, saturation, brightness);
   }

   public DoubleSetting getOffset() {
      return this.offset;
   }

   public float getHudBottom() {
      return this.hudBottom;
   }

   public List<HudModuleEntry> getEntries() {
      return this.entries;
   }

   public List<HudModuleEntry> getVisibleEntries() {
      return this.visibleEntries;
   }

   static {
      try {
      String[] var10000 = new String[5];
      long var0 = KEY ^ 15300116647851L;
      setExternalStrings(var10000);
      Cipher var2;
      Cipher var14 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var14.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[26];
      int var7 = 0;
      String var6 = "\u00db\u0098\u00d1\u0006`\u00bf#B\u0010\u0017\u00c3\u0093@Q\nDo\u00ce\u0084Y\u00c4\u001fZC\u00d6\b\u00c6\u00bb\u00da\u009d\u00cb\u00d6\u0096k\b\u00b6K*\u00c5\u0019\tI\u0012\u0010\u00f6\u00ceV\u00ca\u00b2t\u0012Ng|\u00b5\b\u00c7\u00cf\u00b6\u00cd\b\u00c5\u00e3\u00cb\bm\u00c7\u00db\u00fb\b\u00d5\u0089B\u0011\u00ef6\u00cdK\b'a\u00dfR\u00ddV'\u00ca\u0010\u00de>\u00dc.\u00c7i9\u00ca\u0010\u00d2\u00eb\b\u000f\u000f\u0087d\bf\u00e4\n\u00e1\u00f2\u0013N?\u0010\u00bb\u00c7\u00c6f\u00bf\u00f6f,\u00c2|\u00f2g\u0017W\u00e7-\u0010|\u00a6\u00ba\u001cb\u00d6\u0016;j\u00a5*\u00e0CQ3\u001c\b\u00cf\u00f4\u00acJ\u00fb:\u00d4\u00ba\b\u00fe\u00d1ONX\u0018n\u0011\b\u0004\u00e2fI8\u00ea\u00062\u0010G\u00b8X)f\u0086\u00da\u0081W\u00e4\u00ed\u0097*\u001cl\u00a1\b\u0004\u00e2fI8\u00ea\u00062\u0010\u0011\u00f9\u00af:\u00c0s\u0081\u0087\u00cdj\u00fc\u0001A\u00ff4\u00a0\b\u0019\u00f5\u0081b\u007fJHv\b\u00c5\u00e3\u00cb\bm\u00c7\u00db\u00fb\u0010]t\u0080\u0019:\fe\u00e7\u00ac\u00ce\u00ac\u0000\f\u001c\u00b9\u00d7\b\u0019\u00f5\u0081b\u007fJHv\b\u00c6\u00bb\u00da\u009d\u00cb\u00d6\u0096k\u0010-\u0094\u0019>o4\u00cb;\u00fa\u0083s4#\u00b2\u0081\u0004";
      int var8 = "\u00db\u0098\u00d1\u0006`\u00bf#B\u0010\u0017\u00c3\u0093@Q\nDo\u00ce\u0084Y\u00c4\u001fZC\u00d6\b\u00c6\u00bb\u00da\u009d\u00cb\u00d6\u0096k\b\u00b6K*\u00c5\u0019\tI\u0012\u0010\u00f6\u00ceV\u00ca\u00b2t\u0012Ng|\u00b5\b\u00c7\u00cf\u00b6\u00cd\b\u00c5\u00e3\u00cb\bm\u00c7\u00db\u00fb\b\u00d5\u0089B\u0011\u00ef6\u00cdK\b'a\u00dfR\u00ddV'\u00ca\u0010\u00de>\u00dc.\u00c7i9\u00ca\u0010\u00d2\u00eb\b\u000f\u000f\u0087d\bf\u00e4\n\u00e1\u00f2\u0013N?\u0010\u00bb\u00c7\u00c6f\u00bf\u00f6f,\u00c2|\u00f2g\u0017W\u00e7-\u0010|\u00a6\u00ba\u001cb\u00d6\u0016;j\u00a5*\u00e0CQ3\u001c\b\u00cf\u00f4\u00acJ\u00fb:\u00d4\u00ba\b\u00fe\u00d1ONX\u0018n\u0011\b\u0004\u00e2fI8\u00ea\u00062\u0010G\u00b8X)f\u0086\u00da\u0081W\u00e4\u00ed\u0097*\u001cl\u00a1\b\u0004\u00e2fI8\u00ea\u00062\u0010\u0011\u00f9\u00af:\u00c0s\u0081\u0087\u00cdj\u00fc\u0001A\u00ff4\u00a0\b\u0019\u00f5\u0081b\u007fJHv\b\u00c5\u00e3\u00cb\bm\u00c7\u00db\u00fb\u0010]t\u0080\u0019:\fe\u00e7\u00ac\u00ce\u00ac\u0000\f\u001c\u00b9\u00d7\b\u0019\u00f5\u0081b\u007fJHv\b\u00c6\u00bb\u00da\u009d\u00cb\u00d6\u0096k\u0010-\u0094\u0019>o4\u00cb;\u00fa\u0083s4#\u00b2\u0081\u0004"
         .length();
      char var5 = '\b';
      int var13 = -1;

      label27:
      while (true) {
         String var15 = var6.substring(++var13, var13 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var15.getBytes("ISO-8859-1"));
            String var21 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var21;
                  if ((var13 += var5) >= var8) {
                     STRINGS = var9;
                     FONT_MANAGER = EvonClient.getInstance().getFontManager();
                     String[] var11 = STRINGS;
                     FONT_MEDIUM = FONT_MANAGER.getFont(var11[17]);
                     FONT_SEMI_BOLD = FONT_MANAGER.getFont(var11[23]);
                     BUBBLEGUM = new ColorPair(new Color(205, 44, 141), new Color(103, 15, 49));
                     GRAY = new ColorPair(new Color(150, 142, 164), new Color(80, 70, 90));
                     GRAY_2 = new ColorPair(new Color(107, 100, 131), new Color(81, 59, 104));
                     GREEN = new ColorPair(new Color(87, 143, 118), new Color(53, 75, 65));
                     RED = new ColorPair(new Color(188, 15, 67), new Color(87, 5, 34));
                     EVON = new ColorPair(new Color(184, 202, 209), new Color(119, 137, 138));
                     PURPLE = new ColorPair(new Color(106, 40, 168), new Color(72, 35, 182));
                     RED_2 = new ColorPair(new Color(172, 13, 13), new Color(96, 5, 5));
                     PEARL = new ColorPair(new Color(159, 114, 168), new Color(96, 77, 120));
                     OCEAN_PISS = new ColorPair(new Color(112, 150, 255), new Color(186, 234, 8));
                     break label27;
                  }

                  var5 = var6.charAt(var13);
                  break;
               default:
                  var9[var7++] = var21;
                  if ((var13 += var5) < var8) {
                     var5 = var6.charAt(var13);
                     continue label27;
                  }

                  var6 = "\u00bep\u008a\u0090\u00f3\u001d\u00d3\u0017\\\u0084\u00ee\u0085)sh\u0085\u0018\u00d7E/\u008bDqj\u00b2\u00d6\u0006\u00c1\u00e1#i\u00c3R\u00a9\"\u00ae\u00002\u0010\u0017\f";
                  var8 = "\u00bep\u008a\u0090\u00f3\u001d\u00d3\u0017\\\u0084\u00ee\u0085)sh\u0085\u0018\u00d7E/\u008bDqj\u00b2\u00d6\u0006\u00c1\u00e1#i\u00c3R\u00a9\"\u00ae\u00002\u0010\u0017\f"
                     .length();
                  var5 = 16;
                  var13 = -1;
            }

            var15 = var6.substring(++var13, var13 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var10004) {
         throw sneakyThrow(var10004);
      }
   }

   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }

   public static void setExternalStrings(String[] strings) {
      externalStrings = strings;
   }

   public static String[] getExternalStrings() {
      return externalStrings;
   }

   private static MatchException identity(MatchException exception) {
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
