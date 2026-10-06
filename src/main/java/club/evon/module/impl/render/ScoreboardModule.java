package club.evon.module.impl.render;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.number.NumberFormat;
import net.minecraft.scoreboard.number.StyledNumberFormat;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.HypixelTargetFilter;
import club.evon.data.ScoreboardLine;
import club.evon.event.ClientTickEvent;
import club.evon.event.HudRenderEvent;
import club.evon.event.HudRenderPassEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.impl.misc.Streamer;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.TextUtil;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.RenderUtil;




public final class ScoreboardModule extends BaseModule {
   private final BooleanSetting R;
   private final BooleanSetting textShadow;
   private final BooleanSetting shaders;
   private final DoubleSetting rounding;
   private final DoubleSetting scale;
   private static final Comparator<ScoreboardEntry> ENTRY_COMPARATOR;
   private ScoreboardObjective y;
   private ScoreboardLine[] lines;
   private Vec2f anchor;
   private Vec2f panelSize;
   private static final long KEY = KeyedRandom.create(254643477045131536L, 578546475601618593L, MethodHandles.lookup().lookupClass()).next(72472964463624L);
   private static final String[] STRINGS;
   private static final long MAX_ENTRIES;

   public ScoreboardModule() {
      super((STRINGS)[4], (STRINGS)[5], ModuleCategory.RENDER);

      String[] strings = STRINGS;
      this.R = new BooleanSetting(strings[2], true);
      this.textShadow = new BooleanSetting(strings[6], false);
      this.shaders = new BooleanSetting(strings[0], false);
      this.rounding = new DoubleSetting(strings[1], 4.0, 0.0, 8.0, 1.0);
      this.scale = new DoubleSetting(strings[7], 1.0, 0.8, 1.3, 0.05);
      this.anchor = new Vec2f(0.0F, 0.0F);
      this.panelSize = new Vec2f(0.0F, 0.0F);
      this.addSettings(this.R, this.textShadow, this.shaders, this.rounding, this.scale);
      this.rounding.setVisible(this.shaders::I);
      }

   @Subscribe
   private void onHudRenderPass(HudRenderPassEvent event) {
      long longValue = KEY ^ 31425210334489L;
      boolean flagUnset = Animations.isFlagUnset();
      boolean tmp = this.shaders.I();
      if (flagUnset) {
         if (!tmp) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.options.hudHidden;
      }

      if (!tmp) {
         this.render(event.getDrawContext(), true);
      }
   }

   public void render(DrawContext drawContext, boolean bloom) {
      long longValue = KEY ^ 87413557246420L;
      boolean flagUnset = Animations.isFlagUnset();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (flagUnset) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (flagUnset) {
         if (tmp.getNetworkHandler() == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (!tmp.options.hudHidden) {
         ScoreboardModule module = this;
         if (flagUnset) {
            if (this.y == null) {
               return;
            }

            module = this;
         }

         module.drawScoreboard(drawContext, this.y, true, false, false, bloom);
      }
   }

   private void updateLayout(Scoreboard scoreboard, ScoreboardObjective scoreboardObjective) {
      long longValue = KEY ^ 52783219666118L;
      TextRenderer textRenderer = ClientContext.MINECRAFT_CLIENT.textRenderer;
      NumberFormat numberFormatOr = scoreboardObjective.getNumberFormatOr(StyledNumberFormat.RED);
      boolean flag = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter;
      ScoreboardLine[] values = scoreboard.getScoreboardEntries(scoreboardObjective).stream().filter(scoreboardEntry -> {
         long var1x = KEY ^ 5433893463007L;
         boolean var3x = Animations.isFlagUnset();
         boolean var10000x = scoreboardEntry.hidden();
         if (var3x) {
            var10000x = !var10000x;
         }

         return var10000x;
      }).sorted(ENTRY_COMPARATOR).limit(MAX_ENTRIES).map(scoreboardEntry2 -> {
         long var5x = KEY ^ 139594204587032L;
         Team var7x = scoreboard.getScoreHolderTeam(scoreboardEntry2.owner());
         Text var8x = scoreboardEntry2.name();
         if (flag) {
            String var9x = TextUtil.toLegacyString(var8x);
            var9x = Streamer.censor(var9x);
            var8x = Text.of(var9x);
         }

         Object var13x = Team.decorateName(var7x, var8x);
         if (flag) {
            String var10x = TextUtil.toLegacyString((Text)var13x);
            var10x = Streamer.censor(var10x);
            var13x = Text.of(var10x);
         }

         MutableText var15x = scoreboardEntry2.formatted(numberFormatOr);
         int var11x = textRenderer.getWidth(var15x);
         return new ScoreboardLine((Text)var13x, var15x, var11x);
      }).toArray(ScoreboardLine[]::new);
      Text displayName = scoreboardObjective.getDisplayName();
      int value = textRenderer.getWidth(displayName);
      int value2 = textRenderer.getWidth(STRINGS[3]);
      boolean flagUnset = Animations.isFlagUnset();
      ScoreboardLine[] values2 = values;
      int length = values2.length;
      boolean flag2 = flagUnset;
      int index = 0;

      while (index < length) {
         ScoreboardLine scoreboardLine = values2[index];
         int value3 = textRenderer.getWidth(scoreboardLine.name());
         int value4 = scoreboardLine.scoreWidth();
         if (flag2) {
            value4 = scoreboardLine.scoreWidth() > 0 ? value2 + scoreboardLine.scoreWidth() : 0;
         }

         value = Math.max(value, value3 + value4);
         index++;
         if (!flag2) {
            break;
         }
      }

      float floatValue;
      label40: {
         Overlay moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Overlay.class);
         Overlay overlay = moduleByClass;
         if (flag2) {
            if (!((BaseModule)moduleByClass).isEnabled()) {
               floatValue = 0.0F;
               break label40;
            }

            overlay = moduleByClass;
         }

         floatValue = overlay.getHudBottom() + 20.0F;
      }

      float floatValue2 = floatValue;
      index = value;
      int value5 = (int)floatValue2;
      int length2 = values.length;
      int value6 = length2 * 9;
      int value7 = ClientContext.MINECRAFT_CLIENT.getWindow().getScaledHeight() / 2 + value6 / 3;
      if (flag2) {
         if (value5 > value7 - value6) {
            value7 += value5 - (value7 - value6);
         }

         this.lines = values;
         this.panelSize = new Vec2f(index, value5);
         this.anchor = new Vec2f(value7, 0.0F);
      }
   }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 98884665640422L;
      boolean flagUnset = Animations.isFlagUnset();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (flagUnset) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      Scoreboard scoreboard = tmp.getNetworkHandler().getScoreboard();
      if (scoreboard != null) {
         ScoreboardObjective scoreboardObjective;
         label40: {
            scoreboardObjective = null;
            Team scoreHolderTeam = scoreboard.getScoreHolderTeam(ClientContext.MINECRAFT_CLIENT.player.getNameForScoreboard());
            Team team = scoreHolderTeam;
            if (flagUnset) {
               if (scoreHolderTeam == null) {
                  break label40;
               }

               team = scoreHolderTeam;
            }

            ScoreboardDisplaySlot fromFormatting = ScoreboardDisplaySlot.fromFormatting(team.getColor());
            if (fromFormatting != null) {
               scoreboardObjective = scoreboard.getObjectiveForSlot(fromFormatting);
            }
         }

         ScoreboardObjective tmp2 = scoreboardObjective;
         if (flagUnset) {
            tmp2 = scoreboardObjective != null ? scoreboardObjective : scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
         }

         this.y = tmp2;
      }
   }

   @Subscribe
   private void onHudRender(HudRenderEvent event) {
      long longValue = KEY ^ 29973123025355L;
      boolean flagUnset = Animations.isFlagUnset();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (flagUnset) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (flagUnset) {
         if (tmp.getNetworkHandler() == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (!tmp.options.hudHidden) {
         ScoreboardModule module;
         label61: {
            ScoreboardObjective scoreboardObjective = this.y;
            if (flagUnset) {
               if (this.y != null) {
                  Scoreboard scoreboard = ClientContext.MINECRAFT_CLIENT.getNetworkHandler().getScoreboard();
                  if (scoreboard == null) {
                     return;
                  }

                  this.updateLayout(scoreboard, this.y);
               }

               module = this;
               if (!flagUnset) {
                  break label61;
               }

               scoreboardObjective = this.y;
            }

            if (scoreboardObjective == null) {
               return;
            }

            module = this;
         }

         module.drawScoreboard(event.getDrawContext(), this.y, true, this.R.I(), this.shaders.I(), false);
      }
   }

   public void renderDefault(DrawContext context) {
      this.drawScoreboard(context, this.y, false, false, false, false);
   }

   public void drawScoreboard(DrawContext context, ScoreboardObjective objective, boolean override, boolean background, boolean blur, boolean bloom) {
      long longValue = KEY ^ 35212933267079L;
      boolean flagSet = Animations.isFlagSet();
      if (objective != null) {
         ScoreboardModule module;
         label139: {
            Vec2f tmp = this.panelSize;
            if (!flagSet) {
               if (this.panelSize == null) {
                  return;
               }

               module = this;
               if (flagSet) {
                  break label139;
               }

               tmp = this.anchor;
            }

            if (tmp == null) {
               return;
            }

            module = this;
         }

         if (module.lines != null) {
            TextRenderer textRenderer;
            ScoreboardLine[] values;
            int value;
            int value2;
            int value3;
            int value4;
            int value5;
            int value6;
            float floatValue;
            float floatValue2;
            float floatValue3;
            label146: {
               textRenderer = ClientContext.MINECRAFT_CLIENT.textRenderer;
               values = this.lines;
               value = (int)this.anchor.x;
               value2 = context.getScaledWindowWidth() - (int)this.panelSize.x - 3;
               value3 = context.getScaledWindowWidth() - 3 + 2;
               value4 = value - values.length * 9;
               int value7 = value2 - 2;
               value5 = value7;
               value6 = value4 - 9 - 1;
               floatValue = this.scale.J().floatValue();
               floatValue2 = value3 - value5;
               floatValue3 = value - value6;
               boolean flag = override;
               if (!flagSet) {
                  if (!flag) {
                     break label146;
                  }

                  flag = this.rounding.isVisible();
               }

               float floatValue4;
               label116: {
                  if (!flagSet) {
                     if (!flag) {
                        floatValue4 = 0.0F;
                        break label116;
                     }

                     floatValue4 = this.rounding.J().intValue();
                  } else {
                     floatValue4 = flag ? 1.0F : 0.0F;
                  }
               }

               float floatValue5 = floatValue4;
               RenderUtil.saveState();
               RenderUtil.translate(value5 + (floatValue2 - floatValue2 * floatValue), value6 + (floatValue3 - floatValue3 * floatValue) / 2.0F);
               RenderUtil.scale(floatValue, floatValue);
               RenderUtil.translate(-value5, -value6);
               boolean flag2 = bloom;
               if (!flagSet) {
                  if (bloom) {
                     RenderUtil.fillRoundedRectWithBlur(value5, value6, floatValue2, floatValue3, floatValue5, Color.BLACK);
                  }

                  flag2 = blur;
               }

               label108:
               if (!flagSet) {
                  if (!flag2) {
                     flag2 = background;
                     if (flagSet) {
                        break label108;
                     }

                     if (!background) {
                        flag2 = false;
                        break label108;
                     }
                  }

                  flag2 = true;
               }

               boolean flag3 = flag2;
               int value8 = blur ? 1 : 0;
               if (!flagSet) {
                  if (blur) {
                     RenderUtil.saveState();
                     RenderUtil.translate(-10.0F, -10.0F);
                     RenderUtil.fillRoundedRectPaint(value5 + 10, value6 + 10, floatValue2, floatValue3, floatValue5, RenderUtil.gameBlurPaint);
                     RenderUtil.restoreState();
                  }

                  value8 = flag3 ? 1 : 0;
               }

               label99: {
                  if (!flagSet) {
                     if (value8 == 0) {
                        break label99;
                     }

                     value8 = value5;
                  }

                  RenderUtil.fillRoundedRectArgb(value8, value6, floatValue2, floatValue3, floatValue5, ColorUtil.withAlpha(Color.BLACK, 0.4F).getRGB());
               }

               RenderUtil.restoreState();
               if (!flagSet) {
                  return;
               }
            }

            Text displayName = objective.getDisplayName();
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(value5 + (floatValue2 - floatValue2 * floatValue), value6 + (floatValue3 - floatValue3 * floatValue) / 2.0F);
            context.getMatrices().scale(floatValue, floatValue);
            int value9 = value2 + (int)this.panelSize.x / 2 - textRenderer.getWidth(displayName) / 2;
            Objects.requireNonNull(textRenderer);
            context.drawText(textRenderer, displayName, value9 - value5, value4 - 9 - value6, -1, this.textShadow.I());
            int index = 0;

            while (index < values.length) {
               ScoreboardLine scoreboardLine = values[index];
               int value10 = values.length - index;
               Objects.requireNonNull(textRenderer);
               int value11 = value - value10 * 9;
               context.drawText(textRenderer, scoreboardLine.name(), value2 - value5, value11 - value6, -1, this.textShadow.I());
               context.drawText(textRenderer, scoreboardLine.score(), value3 - scoreboardLine.scoreWidth() - value5, value11 - value6, -1, false);
               index++;
               if (flagSet) {
                  return;
               }

               if (flagSet) {
                  break;
               }
            }

            context.getMatrices().popMatrix();
            return;
         }
      }
   }

   static {
      try {
      long var5 = KEY ^ 55453809161849L;
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[8];
      int var12 = 0;
      String var11 = "\u0095ZV\u0094\u0018\u00b2\u00ba\u0086\u0010\u008d\u00f5\u00a4Y\u00e3\u00a1\u00e8\u00a7\u00ea~|\u00cb\u0014\u0089\u001a,\u0010\u00e9\u00ff\u0099\\\u0084#\u00dd\u00cf\u00bd\u0080\u00f0\u00c3\u0012\u0081(y\b\u00b0q\u00a5\u00d9\u00e0\rZ\u001b\u0010?\u0080\u0088\u00158\u007f\r\u00ab\u0080\u00b0\u00e6+q9Q\u00e8\u0010?\u0080\u0088\u00158\u007f\r\u00abF\u0085\u0097\u00d9\u00c8S\u001b\u00c2";
      int var13 = "\u0095ZV\u0094\u0018\u00b2\u00ba\u0086\u0010\u008d\u00f5\u00a4Y\u00e3\u00a1\u00e8\u00a7\u00ea~|\u00cb\u0014\u0089\u001a,\u0010\u00e9\u00ff\u0099\\\u0084#\u00dd\u00cf\u00bd\u0080\u00f0\u00c3\u0012\u0081(y\b\u00b0q\u00a5\u00d9\u00e0\rZ\u001b\u0010?\u0080\u0088\u00158\u007f\r\u00ab\u0080\u00b0\u00e6+q9Q\u00e8\u0010?\u0080\u0088\u00158\u007f\r\u00abF\u0085\u0097\u00d9\u00c8S\u001b\u00c2"
         .length();
      char var10 = '\b';
      int var17 = -1;

      label37:
      while (true) {
         String var18 = var11.substring(++var17, var17 + var10);
         byte var10001 = -1;

         while (true) {
            byte[] var15 = var7.doFinal(var18.getBytes("ISO-8859-1"));
            String var26 = decodeUtf8(var15).intern();
            switch (var10001) {
               case 0:
                  var14[var12++] = var26;
                  if ((var17 += var10) >= var13) {
                     STRINGS = var14;
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long var2 = 2585835801088449318L;
                     byte[] var4 = var0.doFinal(
                        new byte[]{
                           (byte)(var2 >>> 56),
                           (byte)(var2 >>> 48),
                           (byte)(var2 >>> 40),
                           (byte)(var2 >>> 32),
                           (byte)(var2 >>> 24),
                           (byte)(var2 >>> 16),
                           (byte)(var2 >>> 8),
                           (byte)var2
                        }
                     );
                     long var30 = (var4[0] & 255L) << 56
                        | (var4[1] & 255L) << 48
                        | (var4[2] & 255L) << 40
                        | (var4[3] & 255L) << 32
                        | (var4[4] & 255L) << 24
                        | (var4[5] & 255L) << 16
                        | (var4[6] & 255L) << 8
                        | var4[7] & 255L;
                     var10001 = -1;
                     MAX_ENTRIES = var30;
                     ENTRY_COMPARATOR = Comparator.comparing(ScoreboardEntry::value).reversed().thenComparing(ScoreboardEntry::owner, String.CASE_INSENSITIVE_ORDER);
                     break label37;
                  }

                  var10 = var11.charAt(var17);
                  break;
               default:
                  var14[var12++] = var26;
                  if ((var17 += var10) < var13) {
                     var10 = var11.charAt(var17);
                     continue label37;
                  }

                  var11 = "\t\u00f8T\u001e>F\u00f4M\u00f2\u00e4p\u00d0p\u00bb&\u00f2\b@g0\u000e\u00f1-\u00d1\u00f3";
                  var13 = "\t\u00f8T\u001e>F\u00f4M\u00f2\u00e4p\u00d0p\u00bb&\u00f2\b@g0\u000e\u00f1-\u00d1\u00f3".length();
                  var10 = 16;
                  var17 = -1;
            }

            var18 = var11.substring(++var17, var17 + var10);
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
