package club.evon.module.impl.render;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import org.joml.Vector4d;
import org.json.JSONException;
import org.lwjgl.nanovg.NanoVG;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.api.ServerProfile;
import club.evon.data.UserProfile;
import club.evon.event.HudRenderEvent;
import club.evon.event.HudRenderPassEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ChatNameManager;
import club.evon.manager.ClientState;
import club.evon.manager.FrustumManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.math.RandomUtil;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.FontRenderer;
import club.evon.util.render.FontWeight;
import club.evon.util.render.FontWeightSwitchMap;
import club.evon.util.render.ProjectionUtil;
import club.evon.util.render.RenderUtil;




public final class ESP extends BaseModule {
   private final BooleanSetting nameTagHealth;
   private final BooleanSetting distance;
   private final BooleanSetting background;
   private final BooleanSetting rounding;
   private final BooleanSetting textShadow;
   private final BooleanSetting nameTagBloom;
   private final BooleanGroupSetting nameTagAddons;
   private final EnumSetting<FontWeight> fontWeight;
   private final BooleanSetting boxHealth;
   private final BooleanSetting boxBloom;
   private final BooleanSetting box;
   private final BooleanGroupSetting boxAddons;
   private final FontRenderer fontRenderer;
   private final BooleanSetting renderOnSelf;
   private final BooleanSetting twoDBox;
   private final BooleanSetting nameTags;
   private final DoubleSetting nameTagSize;
   private final DoubleSetting nameTagBackgroundOpacity;
   private final HashMap<LivingEntity, Vector4d> projectedEntities;
   private static final long KEY = KeyedRandom.create(-4776305754167686912L, 3222778151138412778L, MethodHandles.lookup().lookupClass()).next(211553756519805L);
   private static final String[] STRINGS;
   private static final long ALPHA_MASK;

   public ESP() {
      super((STRINGS)[24], (STRINGS)[4], ModuleCategory.RENDER);

      String[] strings = STRINGS;
      this.nameTagHealth = new BooleanSetting(strings[2], true);
      this.distance = new BooleanSetting(strings[28], false);
      this.background = new BooleanSetting(strings[25], true);
      this.rounding = new BooleanSetting(strings[10], true);
      this.textShadow = new BooleanSetting(strings[6], true);
      this.nameTagBloom = new BooleanSetting(strings[5], true);
      this.nameTagAddons = new BooleanGroupSetting(strings[27], this.nameTagHealth, this.distance, this.background, this.textShadow, this.rounding, this.nameTagBloom);
      this.fontWeight = new EnumSetting<>(strings[11], club.evon.util.render.FontWeight.BOLD);
      this.boxHealth = new BooleanSetting(strings[9], true);
      this.boxBloom = new BooleanSetting(strings[15], true);
      this.box = new BooleanSetting(strings[23], true);
      this.boxAddons = new BooleanGroupSetting(strings[13], this.box, this.boxHealth, this.boxBloom);
      this.fontRenderer = EvonClient.getInstance().getFontRenderer();
      this.renderOnSelf = new BooleanSetting(strings[8], true);
      this.twoDBox = new BooleanSetting(strings[19], true);
      this.nameTags = new BooleanSetting(strings[16], true);
      this.nameTagSize = new DoubleSetting(strings[0], 0.0, 0.0, 1.0, 0.01);
      this.nameTagBackgroundOpacity = new DoubleSetting(strings[1], 150.0, 1.0, 150.0, 1.0);
      this.projectedEntities = new HashMap<>();
      this.nameTagSize.setVisible(this.nameTags::I);
      this.nameTagBackgroundOpacity.setVisible(() -> {
         long var1x = KEY ^ 52667249805202L;
         boolean flagSet = Animations.isFlagSet();
         boolean tmp = this.background.I();
         if (!flagSet) {
            if (!tmp) {
               return false;
            }

            tmp = this.nameTags.I();
         }

         return flagSet ? tmp : tmp;
      });
      this.fontWeight.setVisible(this.nameTags::I);
      this.boxAddons.setVisible(this.twoDBox::I);
      this.nameTagAddons.setVisible(this.nameTags::I);
      this.addSettings(this.nameTags, this.nameTagAddons, this.fontWeight, this.nameTagSize, this.nameTagBackgroundOpacity, this.twoDBox, this.boxAddons, this.renderOnSelf);
      }

   @Subscribe
   public void onHudRenderPass(HudRenderPassEvent event) {
      boolean flag;
      ESP eSP = null;
      label80: {
         long longValue = KEY ^ 12969762388717L;
         boolean flagSet = Animations.isFlagSet();
         this.projectedEntities.clear();
         flag = flagSet;
         flagSet = this.twoDBox.I();
         if (!flag) {
            if (flagSet) {
               break label80;
            }

            flagSet = this.nameTags.I();
         }

         if (!flagSet) {
            return;
         }
      }

      Frustum frustum = FrustumManager.getFrustum();

      for (Entity entity : ClientContext.MINECRAFT_CLIENT.world.getEntities()) {
         label70: {
            Entity entity2 = entity;
            if (!flag) {
               if (!(entity instanceof PlayerEntity)) {
                  break label70;
               }

               entity2 = entity;
            }

            PlayerEntity player = (PlayerEntity)entity2;
            if (this.isValidTarget(player)) {
               Vector4d entityBounds = ProjectionUtil.getEntityBounds(player);
               label64:
               if (entityBounds != null) {
                  label84: {
                     boolean visible = frustum.isVisible(player.getBoundingBox());
                     if (!flag) {
                        if (!visible) {
                           break label64;
                        }

                        this.projectedEntities.put(player, entityBounds);
                        eSP = this;
                        if (flag) {
                           break label84;
                        }

                        visible = this.boxBloom.I();
                     }

                     if (!visible) {
                        eSP = this;
                        if (flag) {
                           break label84;
                        }

                        if (!this.nameTagBloom.I()) {
                           break label64;
                        }
                     }

                     eSP = this;
                  }

                  eSP.renderEntityOverlay(entityBounds, player, true, this.boxBloom.I(), this.nameTagBloom.I());
               }
            }
         }

         if (flag) {
            break;
         }
      }
   }

   @Subscribe
   public void onHudRender(HudRenderEvent event) {
      long longValue = KEY ^ 1897056614194L;
      boolean flagSet = Animations.isFlagSet();
      HashMap<LivingEntity, Vector4d> tmp = this.projectedEntities;
      if (!flagSet) {
         if (this.projectedEntities.isEmpty()) {
            return;
         }

         tmp = this.projectedEntities;
      }

      for (Entry entry : tmp.entrySet()) {
         LivingEntity entity = (LivingEntity)entry.getKey();
         Vector4d vector4d = (Vector4d)entry.getValue();
         this.renderEntityOverlay(vector4d, entity, false, false, false);
         if (flagSet) {
            break;
         }
      }
   }

   private void renderEntityOverlay(Vector4d bounds, LivingEntity entity, boolean renderBox, boolean flag, boolean flag2) {
      long longValue = KEY ^ 14914140564508L;
      boolean flagSet = Animations.isFlagSet();
      boolean flag3 = false;
      Object object = null;
      boolean flag4 = false;
      double doubleValue = 0.0;
      Vector4d tmp = bounds;
      if (!flagSet) {
         if (bounds == null) {
            return;
         }

         tmp = bounds;
      }

      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      label244: {
         floatValue = (float)tmp.x;
         floatValue2 = (float)bounds.y;
         floatValue3 = (float)(bounds.z - bounds.x);
         float floatValue5 = (float)(bounds.w - bounds.y);
         NanoVG.nvgShapeAntiAlias(RenderUtil.NVG_CONTEXT, false);
         floatValue4 = entity.getHealth() / entity.getMaxHealth();
         flag3 = this.twoDBox.I();
         label237:
         if (!flagSet) {
            if (flag3) {
               float floatValue6 = 0.5F;
               float floatValue7 = 1.0F;
               Color primaryColor = Overlay.getPrimaryColor();
               Color secondaryColor = Overlay.getSecondaryColor();
               flag3 = this.box.I();
               label219:
               if (!flagSet) {
                  label231:
                  if (flag3) {
                     label248: {
                        flag3 = renderBox;
                        if (!flagSet) {
                           if (!renderBox) {
                              break label248;
                           }

                           flag3 = flag;
                        }

                        if (flagSet) {
                           break label219;
                        }

                        if (!flag3) {
                           break label231;
                        }

                        RenderUtil.fillRectWithBlur(floatValue, floatValue2, floatValue3, 1.5F, Color.BLACK);
                        RenderUtil.fillRectWithBlur(floatValue, floatValue2, 1.5F, floatValue5 + 0.5F + 1.0F, Color.BLACK);
                        RenderUtil.fillRectWithBlur((float)bounds.z, floatValue2, 1.5F, floatValue5 + 0.5F + 1.0F, Color.BLACK);
                        RenderUtil.fillRectWithBlur(floatValue, (float)bounds.w, floatValue3, 1.5F, Color.BLACK);
                        if (!flagSet) {
                           break label231;
                        }
                     }

                     RenderUtil.fillRectArgb(floatValue, floatValue2, floatValue3, 1.5F, Color.BLACK.getRGB());
                     RenderUtil.fillRectArgb(floatValue, floatValue2, 1.5F, floatValue5 + 0.5F + 1.0F, Color.BLACK.getRGB());
                     RenderUtil.fillRectArgb((float)bounds.z, floatValue2, 1.5F, floatValue5 + 0.5F + 1.0F, Color.BLACK.getRGB());
                     RenderUtil.fillRectArgb(floatValue, (float)bounds.w, floatValue3, 1.5F, Color.BLACK.getRGB());
                     RenderUtil.fillGradientRect(floatValue + 0.5F, floatValue2 + 0.5F, floatValue3, 0.5F, secondaryColor.getRGB(), primaryColor.getRGB(), true);
                     RenderUtil.fillGradientRect(floatValue + 0.5F, floatValue2 + 0.5F, 0.5F, floatValue5 + 0.5F, secondaryColor.getRGB(), primaryColor.getRGB(), false);
                     RenderUtil.fillGradientRect((float)bounds.z + 0.5F, floatValue2 + 0.5F, 0.5F, floatValue5 + 0.5F, primaryColor.getRGB(), secondaryColor.getRGB(), false);
                     RenderUtil.fillGradientRect(floatValue + 0.5F, (float)bounds.w + 0.5F, floatValue3, 0.5F, primaryColor.getRGB(), secondaryColor.getRGB(), true);
                  }

                  flag3 = this.boxHealth.I();
               }

               if (flagSet) {
                  break label237;
               }

               label215:
               if (flag3) {
                  label249: {
                     flag3 = renderBox;
                     if (!flagSet) {
                        if (!renderBox) {
                           break label249;
                        }

                        flag3 = flag;
                     }

                     if (flagSet) {
                        break label237;
                     }

                     if (!flag3) {
                        break label215;
                     }

                     RenderUtil.fillRectWithBlur((float)(bounds.x - 2.0 - 0.5), floatValue2, 1.5F, floatValue5 + 1.5F, Color.BLACK);
                     RenderUtil.fillRectWithBlur((float)(bounds.x - 2.0), (float)(bounds.y + 0.5 + (floatValue5 - floatValue5 * floatValue4)), 0.5F, (floatValue5 + 0.5F) * floatValue4, Color.BLACK);
                     if (!flagSet) {
                        break label215;
                     }
                  }

                  RenderUtil.fillRectArgb((float)(bounds.x - 2.0 - 0.5), floatValue2, 1.5F, floatValue5 + 1.5F, Color.BLACK.getRGB());
                  Color color = new Color(ColorUtil.getHealthColor(floatValue4));
                  Color brighter = color.brighter();
                  Color darker = color.darker();
                  RenderUtil.fillGradientRect(
                     (float)(bounds.x - 2.0),
                     (float)(bounds.y + 0.5 + (floatValue5 - floatValue5 * floatValue4)),
                     0.5F,
                     (floatValue5 + 0.5F) * floatValue4,
                     brighter.getRGB(),
                     darker.getRGB(),
                     false
                  );
               }
            }

            NanoVG.nvgShapeAntiAlias(RenderUtil.NVG_CONTEXT, true);
            flag3 = this.nameTags.I();
            if (flagSet) {
               break label244;
            }

         }

         if (!flag3) {
            return;
         }

         object = this.fontWeight.getValue();
      }

      FontWeight fontWeight2 = (FontWeight)object;
      String text = "";
      boolean friend = club.evon.manager.FriendManager.getInstance().isFriend(entity.getName().getString());
      if (!flagSet) {
         if (friend) {
            String valueOf = String.valueOf(Formatting.GREEN);
            String[] strings = STRINGS;
            text = text + valueOf + strings[20];
         }

         friend = entity.hasStatusEffect(StatusEffects.STRENGTH);
      }

      if (friend) {
      }

      if (entity != ClientContext.MINECRAFT_CLIENT.player && this.distance.I()) {
         String valueOf2 = String.valueOf(Formatting.GRAY);
         String valueOf3 = String.valueOf(Formatting.GRAY);
         int value = Math.round(ClientContext.MINECRAFT_CLIENT.player.distanceTo(entity));
         String valueOf4 = String.valueOf(Formatting.GRAY);
         int value2 = value;
         String text2 = valueOf3;
         String text3 = valueOf2;
         String[] strings2 = STRINGS;
         text = text + text3 + "(" + text2 + value2 + "m" + valueOf4 + strings2[7];
      }

      String text4 = Formatting.WHITE + PlayerUtil.getDisplayName(entity);
      int value3 = -1;
      UserProfile profileByMinecraftUsername = ChatNameManager.getInstance().getProfileByMinecraftUsername(entity.getName().getString());
      if (!flagSet) {
         if (profileByMinecraftUsername != null) {
            String valueOf5 = String.valueOf(Formatting.GRAY);
            String valueOf6 = String.valueOf(Formatting.RESET);
            String username = profileByMinecraftUsername.getUsername();
            String valueOf7 = String.valueOf(Formatting.GRAY);
            String text5 = username;
            String text6 = valueOf6;
            String text7 = valueOf5;
            String[] strings3 = STRINGS;
            text4 = text4 + text7 + strings3[12] + text6 + text5 + valueOf7 + ")";
            value3 = profileByMinecraftUsername.getRank().getColor().getRGB();
         }

         text = text + text4;
      }

      if (this.nameTagHealth.I()) {
         label185: {
            if (entity == ClientContext.MINECRAFT_CLIENT.player) {
               flag4 = ClientContext.MINECRAFT_CLIENT.player.isCreative();
               if (flagSet) {
                  break label185;
               }

               if (flag4) {
                  flag4 = true;
                  break label185;
               }
            }

            flag4 = false;
         }

         boolean flag5;
         Formatting formatting;
         label177: {
            label176: {
               flag5 = flag4;
               formatting = Formatting.GREEN;
               double doubleValue2 = flag5 ? 1.0 : floatValue4;
               double doubleValue3;
               int value4 = (doubleValue3 = doubleValue2 - 0.3333333333333333) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
               if (!flagSet) {
                  if (value4 < 0) {
                     formatting = Formatting.RED;
                     if (!flagSet) {
                        break label176;
                     }
                  }

                  doubleValue = doubleValue2;
                  if (flagSet) {
                     break label177;
                  }

                  double doubleValue4;
                  value4 = (doubleValue4 = doubleValue2 - 0.6666666666666666) == 0.0 ? 0 : (doubleValue4 < 0.0 ? -1 : 1);
               }

               if (value4 < 0) {
                  formatting = Formatting.YELLOW;
               }
            }

            doubleValue = RandomUtil.round(entity.getHealth(), 1);
         }

         String valueOf8 = String.valueOf(doubleValue);
         boolean endsWith = valueOf8.endsWith(STRINGS[3]);
         if (!flagSet) {
            if (endsWith) {
               String[] strings4 = STRINGS;
               valueOf8 = valueOf8.replace(strings4[17], "");
            }

            endsWith = flag5;
         }

         if (endsWith) {
            String[] strings5 = STRINGS;
            valueOf8 = strings5[21];
         }

         String valueOf9 = String.valueOf(Formatting.GRAY);
         String valueOf10 = String.valueOf(formatting);
         String valueOf11 = String.valueOf(Formatting.GRAY);
         String text8 = valueOf8;
         String text9 = valueOf10;
         String text10 = valueOf9;
         String[] strings6 = STRINGS;
         text = text + text10 + strings6[14] + text9 + text8 + strings6[26] + valueOf11 + ")";
      }

      float floatValue8;
      float floatValue9;
      float floatValue10;
      floatValue9 = 6.0F + this.nameTagSize.J().floatValue();
      String[] strings7 = STRINGS;
      float floatValue11 = this.fontRenderer.getStringWidth(strings7[18], fontWeight2, text, floatValue9);
      float floatValue12 = floatValue + floatValue3 / 2.0F;
      floatValue8 = floatValue12 - floatValue11 / 2.0F;
      floatValue10 = floatValue2 - 12.0F;
      float floatValue13 = this.fontRenderer.getStringHeight(strings7[22], fontWeight2, text, floatValue9);
      int value5 = new Color(0, 0, 0, this.nameTagBackgroundOpacity.J().intValue()).getRGB();
      boolean var65b = this.background.I();
      int value6 = var65b ? 1 : 0;
      label163:
      if (!flagSet) {
         if (var65b) {
            float floatValue14 = 2.3F;
            var65b = this.rounding.I();
            if (flagSet) {
               break label163;
            }

            if (var65b) {
               float floatValue15 = 2.5F;
               RenderUtil.fillRoundedRectPaint(floatValue8 - 2.3F, floatValue10 - 2.3F, floatValue11 + 4.6F, floatValue9 + 4.6F, 2.5F, RenderUtil.gameBlurPaint);
               RenderUtil.fillRoundedRectArgb(floatValue8 - 2.3F, floatValue10 - 2.3F, floatValue11 + 4.6F, floatValue9 + 4.6F, 2.5F, value5);
            }
         }

         value6 = FontWeightSwitchMap.SWITCH_MAP[fontWeight2.ordinal()];
      }

      switch (value6) {
         case 1:
            floatValue10 += 0.7F;
            if (!flagSet) {
               break;
            }
         case 2:
            floatValue10 += 0.6F;
            if (!flagSet) {
               break;
            }
         case 3:
         case 4:
            floatValue10 += 0.3F;
      }

      this.fontRenderer.drawStringWithShadow(STRINGS[22], fontWeight2, text, floatValue8, floatValue10, floatValue9, value3, this.textShadow.I());
   }

   private Formatting getFormattingByColor(int color) {
      long longValue = KEY ^ 28937877551156L;
      Formatting[] values = Formatting.values();
      boolean flagUnset = Animations.isFlagUnset();
      Formatting formatting = null;
      int length = values.length;
      boolean flag = flagUnset;
      int index = 0;

      while (true) {
         if (index < length) {
            Formatting formatting2 = values[index];
            if (flag) {
               if (formatting2.getColorValue() != null) {
                  formatting = formatting2;
                  if (!flag) {
                     break;
                  }

                  if (formatting2.getColorValue().equals(color)) {
                     formatting = formatting2;
                     break;
                  }
               }

               index++;
            }

            if (flag) {
               continue;
            }
         }

         return null;
      }

      return formatting;
   }

   public int getHealthColor(LivingEntity entity) {
      long longValue = KEY ^ 118597930735187L;
      float floatValue = entity.getHealth() / entity.getMaxHealth();
      return Color.HSBtoRGB(floatValue / 3.0F, 1.0F, 1.0F) | (int)ALPHA_MASK;
   }

   public boolean shouldRenderNameTag(Entity entity) {
      long longValue = KEY ^ 46268194088942L;
      boolean flagSet = Animations.isFlagSet();
      boolean enabled = this.isEnabled();
      if (!flagSet) {
         if (!enabled) {
            return false;
         }

         enabled = this.nameTags.I();
      }

      if (!flagSet) {
         if (!enabled) {
            return false;
         }

         enabled = this.isValidTarget(entity);
      }

      return flagSet ? enabled : enabled;
   }

   private boolean isValidTarget(Entity entity) {
      long longValue = KEY ^ 57669085570108L;
      boolean flagUnset = Animations.isFlagUnset();
      boolean tmp = entity instanceof PlayerEntity;
      if (flagUnset) {
         if (tmp) {
            PlayerEntity player;
            label79: {
               player = (PlayerEntity)entity;
               ServerProfile serverProfile = ClientState.getInstance().getServerDetector().getServerProfile();
               ServerProfile serverProfile2 = serverProfile;
               if (flagUnset) {
                  if (serverProfile == null) {
                     break label79;
                  }

                  serverProfile2 = serverProfile;
               }

               tmp = serverProfile2.isValidTarget(player);
               if (!flagUnset) {
                  return tmp;
               }

               if (!tmp) {
                  return false;
               }
            }

            label58: {
               PlayerEntity player2 = ClientContext.MINECRAFT_CLIENT.player;
               if (flagUnset) {
                  if (ClientContext.MINECRAFT_CLIENT.player == entity) {
                     if (ClientContext.MINECRAFT_CLIENT.options.getPerspective() == Perspective.FIRST_PERSON) {
                        return false;
                     }

                     tmp = this.renderOnSelf.I();
                     if (!flagUnset) {
                        break label58;
                     }

                     if (!tmp) {
                        return false;
                     }
                  }

                  player2 = player;
               }

               tmp = player2.isDead();
            }

            if (!flagUnset) {
               return tmp;
            }

            if (!tmp) {
               return true;
            }

            return false;
         }

         tmp = false;
      }

      return tmp;
   }

   static {
      try {
      long var5 = KEY ^ 33130215157920L;
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[29];
      int var12 = 0;
      String var11 = "\u00cd\u00d4>\u000b\u00c7\u00bd)i\n\u00aeYC\b\u00cb\u00bbP\u0018\u00cd\u00d4>\u000b\u00c7\u00bd)i\u008d\u0006\u0018J\u00dbH0S\u00b2\u008b\u0097\u00ef%\u00f5\u00b7f\b\u00ad\u0003\u001d\u00ca\u00d7\u009b7\u00d0\b~\u00d8\u0088\u00fbm\u009ew\u00ff\b\u00f4\u00f2\u00e7\u00f4\u009c!\n\u00f0\bMr\u00155\u00af!`\u0001\u0010@\u00fc\u0098Bg&-\u00f6\u0010,\u00a9<V\u00e5ts\b9\u00b4iV\u00afV^`\u0010G5\u00eeY,{fBj\u00b5\u0004\u00f4\u0003\u00f9~\u001b\b\u00ad\u0003\u001d\u00ca\u00d7\u009b7\u00d0\u0010\u0014E\u0085\f*\u00d9[J\n\u0005\\m@\u0094\u00af*\u0010\u00a0\u00fe\u00fb\u00eaJ\u00e5\u00e1\u00a4\u00f0\u0015\u00db\u00adH\u0084\u00b5a\bK\u00ad\u00bd\"\u00cf{\u00bf\u0085\u0010\u00f2\u00a5N\u00e6\u009d\u00cc_qS\u00bb\u00dfA@\u00a19z\bK\u00ad\u00bd\"\u00cf{\u00bf\u0085\bMr\u00155\u00af!`\u0001\u0010|\u0016XX0I\u00ea\u0082_\u0095_O\u00e3\u0098\u00f49\b~\u00d8\u0088\u00fbm\u009ew\u00ff\b\u00cc\"\u00e4\u00d1\u00bd\u009b\u0002\u009b\b\u00eax\u00b14\u009c|\u00df\u00d3\u0010#\"\u008c\u00b8G\u00dd\u00bb\u00bb6\u00b6\u00faOb\u00ac\u0016,\b\u00d6\u00e9\u0012\u00c8\u00f3u\u00bbN\b\u00cc\"\u00e4\u00d1\u00bd\u009b\u0002\u009b\b\u00b8\u00fa\u00f8\u00f0\u00d72I\u00b5\b\u00f4\u00f2\u00e7\u00f4\u009c!\n\u00f0\u0010o\u00c4}\u0094\u001d\u00c3{\u0002\u00fd\u0004\u00df\u00a1\u00c8V,Q\b\u00c9\u00a1\u001a/\u00d21\u00f2\u00f3";
      int var13 = "\u00cd\u00d4>\u000b\u00c7\u00bd)i\n\u00aeYC\b\u00cb\u00bbP\u0018\u00cd\u00d4>\u000b\u00c7\u00bd)i\u008d\u0006\u0018J\u00dbH0S\u00b2\u008b\u0097\u00ef%\u00f5\u00b7f\b\u00ad\u0003\u001d\u00ca\u00d7\u009b7\u00d0\b~\u00d8\u0088\u00fbm\u009ew\u00ff\b\u00f4\u00f2\u00e7\u00f4\u009c!\n\u00f0\bMr\u00155\u00af!`\u0001\u0010@\u00fc\u0098Bg&-\u00f6\u0010,\u00a9<V\u00e5ts\b9\u00b4iV\u00afV^`\u0010G5\u00eeY,{fBj\u00b5\u0004\u00f4\u0003\u00f9~\u001b\b\u00ad\u0003\u001d\u00ca\u00d7\u009b7\u00d0\u0010\u0014E\u0085\f*\u00d9[J\n\u0005\\m@\u0094\u00af*\u0010\u00a0\u00fe\u00fb\u00eaJ\u00e5\u00e1\u00a4\u00f0\u0015\u00db\u00adH\u0084\u00b5a\bK\u00ad\u00bd\"\u00cf{\u00bf\u0085\u0010\u00f2\u00a5N\u00e6\u009d\u00cc_qS\u00bb\u00dfA@\u00a19z\bK\u00ad\u00bd\"\u00cf{\u00bf\u0085\bMr\u00155\u00af!`\u0001\u0010|\u0016XX0I\u00ea\u0082_\u0095_O\u00e3\u0098\u00f49\b~\u00d8\u0088\u00fbm\u009ew\u00ff\b\u00cc\"\u00e4\u00d1\u00bd\u009b\u0002\u009b\b\u00eax\u00b14\u009c|\u00df\u00d3\u0010#\"\u008c\u00b8G\u00dd\u00bb\u00bb6\u00b6\u00faOb\u00ac\u0016,\b\u00d6\u00e9\u0012\u00c8\u00f3u\u00bbN\b\u00cc\"\u00e4\u00d1\u00bd\u009b\u0002\u009b\b\u00b8\u00fa\u00f8\u00f0\u00d72I\u00b5\b\u00f4\u00f2\u00e7\u00f4\u009c!\n\u00f0\u0010o\u00c4}\u0094\u001d\u00c3{\u0002\u00fd\u0004\u00df\u00a1\u00c8V,Q\b\u00c9\u00a1\u001a/\u00d21\u00f2\u00f3"
         .length();
      char var10 = 16;
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
                     long var2 = 6918641986929700482L;
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
                     ALPHA_MASK = var30;
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

                  var11 = "\u0099Q\u00e2s\u007f`\u009du\u00ee\u008adb\u00e1\u00feMz\u0010\u00ae\u0003\u00f0X\u00ef\u00d7\u00a9B\b2\u00a3\u00ecl\u009a\u00e5V";
                  var13 = "\u0099Q\u00e2s\u007f`\u009du\u00ee\u008adb\u00e1\u00feMz\u0010\u00ae\u0003\u00f0X\u00ef\u00d7\u00a9B\b2\u00a3\u00ecl\u009a\u00e5V"
                     .length();
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
