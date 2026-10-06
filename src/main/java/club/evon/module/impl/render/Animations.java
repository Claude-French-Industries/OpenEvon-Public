package club.evon.module.impl.render;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.json.JSONException;
import club.evon.data.BlockAnimationMode;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.util.KeyedRandom;




public final class Animations extends BaseModule {
   private final BooleanSetting swordBlocking;
   private final EnumSetting<BlockAnimationMode> blockAnimation;
   private final BooleanSetting hideShield;
   private final BooleanSetting oldArmorDamageTint;
   private final BooleanSetting oldSneaking;
   private final DoubleSetting swingSpeedMultiplier;
   private final DoubleSetting offsetX;
   private final DoubleSetting offsetY;
   private final DoubleSetting offsetZ;
   private final BooleanSetting oldCooldown;
   private final BooleanSetting allowSwingOnUse;
   private final BooleanSetting fixSwordDrop;
   private final BooleanSetting equipOffset;
   private static boolean flag;
   private static final long KEY = KeyedRandom.create(4329367893975766615L, -4022575976129799186L, MethodHandles.lookup().lookupClass()).next(153071035264542L);
   private static final String[] STRINGS;

   public Animations() {
      super((STRINGS)[11], (STRINGS)[0], ModuleCategory.RENDER);

      String[] strings = STRINGS;
      this.swordBlocking = new BooleanSetting(strings[13], true);
      this.blockAnimation = new EnumSetting<>(strings[5], BlockAnimationMode.V1_7);
      this.hideShield = new BooleanSetting(strings[10], true);
      this.oldArmorDamageTint = new BooleanSetting(strings[4], true);
      this.oldSneaking = new BooleanSetting(strings[8], false);
      this.swingSpeedMultiplier = new DoubleSetting(strings[9], 1.0, 0.1, 2.0, 0.1);
      this.offsetX = new DoubleSetting(strings[3], 0.0, -2.0, 2.0, 0.05);
      this.offsetY = new DoubleSetting(strings[12], 0.0, -2.0, 2.0, 0.05);
      this.offsetZ = new DoubleSetting(strings[6], 0.0, -2.0, 2.0, 0.05);
      this.oldCooldown = new BooleanSetting(strings[7], true);
      this.allowSwingOnUse = new BooleanSetting(strings[14], true);
      this.fixSwordDrop = new BooleanSetting(strings[1], false);
      this.equipOffset = new BooleanSetting(strings[2], false);
      this.setEnabled(true);
      this.blockAnimation.setVisible(this::isSwordBlocking);
      this.addSettings(this.swordBlocking, this.blockAnimation, this.hideShield, this.oldArmorDamageTint, this.oldSneaking, this.swingSpeedMultiplier, this.offsetX, this.offsetY, this.offsetZ, this.oldCooldown, this.allowSwingOnUse, this.fixSwordDrop, this.equipOffset);
      }

   public boolean isFixSwordDrop() {
      return this.fixSwordDrop.I();
   }

   public boolean isOldSneaking() {
      return this.oldSneaking.I();
   }

   public float getSwingSpeedMultiplier() {
      return this.swingSpeedMultiplier.J().floatValue();
   }

   public boolean isSwordBlocking() {
      return this.swordBlocking.I();
   }

   public boolean isEquipOffset() {
      return this.equipOffset.I();
   }

   public boolean isOldCooldown() {
      return this.oldCooldown.I();
   }

   public boolean isOldArmorDamageTint() {
      return this.oldArmorDamageTint.I();
   }

   public boolean isHideShield() {
      return this.hideShield.I();
   }

   public float getOffsetX() {
      return this.offsetX.J().floatValue();
   }

   public float getOffsetY() {
      return this.offsetY.J().floatValue();
   }

   public float getOffsetZ() {
      return this.offsetZ.J().floatValue();
   }

   public boolean isAllowSwingOnUse() {
      return this.allowSwingOnUse.I();
   }

   public void applyBlockAnimation(MatrixStack matrices, float swingProgress) {
      boolean flag2;
      label61: {
         float floatValue;
         float floatValue2;
         label60: {
            label59: {
               label58: {
                  label57: {
                     label56: {
                        long longValue = KEY ^ 78469738935565L;
                        floatValue = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
                        boolean flagUnset = isFlagUnset();
                        floatValue2 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
                        flag2 = flagUnset;
                        if (flag2) {
                           switch ((BlockAnimationMode)this.blockAnimation.getValue()) {
                              case V1_7:
                                 club.evon.util.render.SwordBlockingHelper.applySwingTransform(matrices, swingProgress, floatValue);
                                 club.evon.util.render.SwordBlockingHelper.applyBlockTransform(matrices);
                                 break;
                              case V1_8:
                                 break label56;
                              case DOWNWARDS:
                                 break label58;
                              case SWANG:
                                 break label60;
                              case PUSH:
                                 break label59;
                              case DIVE:
                                 break label57;
                              default:
                                 break label61;
                           }
                        }

                        if (flag2) {
                           break label61;
                        }
                     }

                     club.evon.util.render.SwordBlockingHelper.applyBlockTransform(matrices);
                     if (flag2) {
                        break label61;
                     }
                  }

                  club.evon.util.render.SwordBlockingHelper.applySwingTransform(matrices, swingProgress, floatValue);
                  matrices.translate(-0.15F, 0.16F, 0.15F);
                  matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-24.0F));
                  matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(75.0F));
                  matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
                  if (flag2) {
                     break label61;
                  }
               }

               matrices.translate(-0.0F, -0.0F, 0.0F);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(50.0F + floatValue2 * -5.0F));
               matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue * -20.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue * -60.0F));
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-40.0F));
               club.evon.util.render.SwordBlockingHelper.applyBlockTransform(matrices);
               if (flag2) {
                  break label61;
               }
            }

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F + floatValue2 * -5.0F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue * -20.0F));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue * -40.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-45.0F));
            club.evon.util.render.SwordBlockingHelper.applyBlockTransform(matrices);
            if (flag2) {
               break label61;
            }
         }

         matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(65.0F + floatValue2 * -20.0F));
         matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue * -15.0F));
         matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue * -40.0F));
         matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-55.0F));
         club.evon.util.render.SwordBlockingHelper.applyBlockTransform(matrices);
      }

      if (BaseModule.getStateArray() == null) {
         setFlag(!flag2);
      }
   }

   public static void setFlag(boolean value) {
      flag = value;
   }

   public static boolean isFlagSet() {
      return flag;
   }

   public static boolean isFlagUnset() {
      boolean flagSet = isFlagSet();
      return !flagSet;
   }

   static {
      try {
      long var0 = KEY ^ 45994281528893L;
      setFlag(false);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[15];
      int var7 = 0;
      String var6 = "[\u000b\u0007l\u00a6\u00ae\u00ac\u0082\u00a6\u0099\u00a3=\u0004\u00b6\u0014\u00a7^\u008a\u00fc\u00af3\u00fb\u00bc\u00f0,\u0091\u00a9\u0013'\u00a1\u008f\u0010\u001a]\u00d3#&\r(=\u0010_\u00e6\u009e\u00f2\u00cf\u0005\u0085\u00d8\u008c\u0099,\u00e5\u0081[\u00ca\u00eb\u0010\u00d6\u00a8\u00e7\u00e4\u008d$\u00dd4;\u00d6\u00b2\u00be\u0098zs\u00d0\u0010#\u00cf\u00c9\u0099U\u0096E\u0082Y]=,S\u00b0\u0085\u009d\u0018e\u00b8\u0005\u00ee\u00e8js\u0013h\u00b2\u001e\u0081)\u008d ,_\u00ff\u00ac\u0000i\n\u00bf\u00ef\u0010\u00f9\b\u00b1\u00c7\u00d2\"F\u0013\u0016\u00f7\u0000a\u00f6\u00f2\u00bbo\u0010n\u001f8=]\u0005\u00e3\u0018\u0014\u0090>\u00d10\u00c6\u00f1k\u0010;\u00be\u00fb\u0089\u008ewt?64u#\u00f3\"\u00ff\u00fb\u0010\u00d4r\u00cb-d\u00c8\u00b9\u00c6\u00dcR\u00f9\u00e4\u00b2\u00ac?=\u0018\u0000\u0087\u0084\u00f0\"\u00ea\u00bc\u001e1\u0015\u00ab\u0013\u00c4\u00cd\u00f9n\u008d\u00f9\u00f3g\u00b4\u008cj\u0098\u0010\u00a4\u0095\u00ec\u00f0\u00c2A(]\u008d\u0006P\u00f0\u00e8\u0003\u00bb\u00b5\u0010\u000f\u00a6x\u00bc\u00fc(d\u00fa\u00abDQx\u00f7\u00b1qO\u0010\u0014[\u00ec\u00b2\u00d4\u00c8T\u00d1:\u00c6S\u00bd\u00adGv\u0007";
      int var8 = "[\u000b\u0007l\u00a6\u00ae\u00ac\u0082\u00a6\u0099\u00a3=\u0004\u00b6\u0014\u00a7^\u008a\u00fc\u00af3\u00fb\u00bc\u00f0,\u0091\u00a9\u0013'\u00a1\u008f\u0010\u001a]\u00d3#&\r(=\u0010_\u00e6\u009e\u00f2\u00cf\u0005\u0085\u00d8\u008c\u0099,\u00e5\u0081[\u00ca\u00eb\u0010\u00d6\u00a8\u00e7\u00e4\u008d$\u00dd4;\u00d6\u00b2\u00be\u0098zs\u00d0\u0010#\u00cf\u00c9\u0099U\u0096E\u0082Y]=,S\u00b0\u0085\u009d\u0018e\u00b8\u0005\u00ee\u00e8js\u0013h\u00b2\u001e\u0081)\u008d ,_\u00ff\u00ac\u0000i\n\u00bf\u00ef\u0010\u00f9\b\u00b1\u00c7\u00d2\"F\u0013\u0016\u00f7\u0000a\u00f6\u00f2\u00bbo\u0010n\u001f8=]\u0005\u00e3\u0018\u0014\u0090>\u00d10\u00c6\u00f1k\u0010;\u00be\u00fb\u0089\u008ewt?64u#\u00f3\"\u00ff\u00fb\u0010\u00d4r\u00cb-d\u00c8\u00b9\u00c6\u00dcR\u00f9\u00e4\u00b2\u00ac?=\u0018\u0000\u0087\u0084\u00f0\"\u00ea\u00bc\u001e1\u0015\u00ab\u0013\u00c4\u00cd\u00f9n\u008d\u00f9\u00f3g\u00b4\u008cj\u0098\u0010\u00a4\u0095\u00ec\u00f0\u00c2A(]\u008d\u0006P\u00f0\u00e8\u0003\u00bb\u00b5\u0010\u000f\u00a6x\u00bc\u00fc(d\u00fa\u00abDQx\u00f7\u00b1qO\u0010\u0014[\u00ec\u00b2\u00d4\u00c8T\u00d1:\u00c6S\u00bd\u00adGv\u0007"
         .length();
      char var5 = '(';
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

                  var6 = "\u00c9\u00fa\u00f4R\u00db\u00c5q\u00aco\fn7I1\u00f5\u00e5\u0018\u00c4\u0018\u00b1\u001e\u00a4p\u0089)\u00ac\u00beS\u00ce6\u00cd\u00cc7\u009d\u00f9\u0003\u008e\u00da\u0092^q";
                  var8 = "\u00c9\u00fa\u00f4R\u00db\u00c5q\u00aco\fn7I1\u00f5\u00e5\u0018\u00c4\u0018\u00b1\u001e\u00a4p\u0089)\u00ac\u00beS\u00ce6\u00cd\u00cc7\u009d\u00f9\u0003\u008e\u00da\u0092^q"
                     .length();
                  var5 = 16;
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
