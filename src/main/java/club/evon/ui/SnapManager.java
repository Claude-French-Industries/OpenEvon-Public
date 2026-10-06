package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.data.SnapPoint;
import club.evon.module.BaseModule;
import club.evon.setting.PositionSetting;
import club.evon.util.Animator;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.RenderUtil;




public final class SnapManager {
   private final List<SnapPoint> snapPoints = new ArrayList<>();
   private SnapPoint horizontalSnap = null;
   private SnapPoint verticalSnap = null;
   private final Animator horizontalAnimator = new Animator(200, 0.0, Easing.DECELERATE);
   private final Animator verticalAnimator = new Animator(200, 0.0, Easing.DECELERATE);
   private static final int G;
   private static boolean enabled;
   private static final long KEY = KeyedRandom.create(1943702086962889459L, 9099558297037298444L, MethodHandles.lookup().lookupClass()).next(85040886356169L);

   public boolean hasHorizontalSnap() {
      long longValue = KEY ^ 122377758516435L;
      return this.horizontalSnap != null;
   }

   public boolean hasVerticalSnap() {
      long longValue = KEY ^ 81922787672976L;
      return this.verticalSnap != null;
   }

   public void updateSnapPoints(PositionSetting currentDragging, float screenWidth, float screenHeight) {
      long longValue = KEY ^ 113415372778347L;
      this.reset();
      float floatValue = currentDragging.getWidth();
      float floatValue2 = currentDragging.getHeight();
      boolean enabled2 = isEnabled();
      float floatValue3 = floatValue / 2.0F;
      boolean flag = enabled2;
      float floatValue4 = floatValue2 / 2.0F;
      this.addSnaps(
         List.of(
            new SnapPoint(SnapAxis.HORIZONTAL, screenWidth / 2.0F + floatValue3 + 15.0F),
            new SnapPoint(SnapAxis.HORIZONTAL, screenWidth / 2.0F - floatValue3 - 15.0F),
            new SnapPoint(SnapAxis.HORIZONTAL, screenWidth / 2.0F + floatValue3),
            new SnapPoint(SnapAxis.HORIZONTAL, screenWidth / 2.0F - floatValue3),
            new SnapPoint(SnapAxis.VERTICAL, screenHeight / 2.0F + floatValue4 + 15.0F),
            new SnapPoint(SnapAxis.VERTICAL, screenHeight / 2.0F - floatValue4 - 15.0F),
            new SnapPoint(SnapAxis.VERTICAL, screenHeight / 2.0F + floatValue4),
            new SnapPoint(SnapAxis.VERTICAL, screenHeight / 2.0F - floatValue4),
            new SnapPoint(SnapAxis.HORIZONTAL, screenWidth / 2.0F),
            new SnapPoint(SnapAxis.VERTICAL, screenHeight / 2.0F)
         )
      );
      if (flag) {
         BaseModule.setStateArray(new BaseModule[4]);
      }
   }

   public void reset() {
      this.snapPoints.clear();
      this.horizontalSnap = null;
      this.verticalSnap = null;
   }

   public void addSnap(SnapPoint snap) {
      this.snapPoints.add(snap);
   }

   public void addSnaps(List<SnapPoint> snaps) {
      this.snapPoints.addAll(snaps);
   }

   public float snapX(float x, float elementWidth) {
      long longValue = KEY ^ 71484002993358L;
      boolean enabled2 = isEnabled();
      float floatValue = x + elementWidth / 2.0F;
      float floatValue2 = Float.MAX_VALUE;
      int value;
      float floatValue3;
      SnapPoint snapPoint;
      SnapPoint snapPoint2 = null;
      Iterator iterator = this.snapPoints.iterator();

      while (true) {
         if (iterator.hasNext()) {
            SnapPoint snapPoint3 = (SnapPoint)iterator.next();
            snapPoint = snapPoint3;
            if (enabled2) {
               break;
            }

            label43:
            if (snapPoint3.axis() == SnapAxis.HORIZONTAL) {
               label62: {
                  float floatValue4 = Math.abs(floatValue - snapPoint3.getCoordinate());
                  float floatValue5;
                  value = (floatValue5 = floatValue4 - 10.0F) == 0.0F ? 0 : (floatValue5 < 0.0F ? -1 : 1);
                  if (!enabled2) {
                     if (value > 0) {
                        break label43;
                     }

                     floatValue3 = floatValue4;
                     if (enabled2) {
                        break label62;
                     }

                     float floatValue6;
                     value = (floatValue6 = floatValue4 - floatValue2) == 0.0F ? 0 : (floatValue6 < 0.0F ? -1 : 1);
                  }

                  if (value >= 0) {
                     break label43;
                  }

                  floatValue3 = floatValue4;
               }

               floatValue2 = floatValue3;
               snapPoint2 = snapPoint3;
            }

            if (!enabled2) {
               continue;
            }
         }

         snapPoint = snapPoint2;
         break;
      }

      if (!enabled2) {
         if (snapPoint == null) {
            this.horizontalSnap = null;
            return x;
         }

         this.horizontalSnap = snapPoint2;
         snapPoint = snapPoint2;
      }

      return snapPoint.getCoordinate() - elementWidth / 2.0F;
   }

   public float snapY(float y, float elementHeight) {
      long longValue = KEY ^ 91545181554358L;
      boolean disabled = isDisabled();
      float floatValue = y + elementHeight / 2.0F;
      float floatValue2 = Float.MAX_VALUE;
      int value;
      float floatValue3;
      SnapPoint snapPoint;
      SnapPoint snapPoint2 = null;
      Iterator iterator = this.snapPoints.iterator();

      while (true) {
         if (iterator.hasNext()) {
            SnapPoint snapPoint3 = (SnapPoint)iterator.next();
            snapPoint = snapPoint3;
            if (!disabled) {
               break;
            }

            label43:
            if (snapPoint3.axis() == SnapAxis.VERTICAL) {
               label62: {
                  float floatValue4 = Math.abs(floatValue - snapPoint3.getCoordinate());
                  float floatValue5;
                  value = (floatValue5 = floatValue4 - 10.0F) == 0.0F ? 0 : (floatValue5 < 0.0F ? -1 : 1);
                  if (disabled) {
                     if (value > 0) {
                        break label43;
                     }

                     floatValue3 = floatValue4;
                     if (!disabled) {
                        break label62;
                     }

                     float floatValue6;
                     value = (floatValue6 = floatValue4 - floatValue2) == 0.0F ? 0 : (floatValue6 < 0.0F ? -1 : 1);
                  }

                  if (value >= 0) {
                     break label43;
                  }

                  floatValue3 = floatValue4;
               }

               floatValue2 = floatValue3;
               snapPoint2 = snapPoint3;
            }

            if (disabled) {
               continue;
            }
         }

         snapPoint = snapPoint2;
         break;
      }

      if (disabled) {
         if (snapPoint == null) {
            this.verticalSnap = null;
            return y;
         }

         this.verticalSnap = snapPoint2;
         snapPoint = snapPoint2;
      }

      return snapPoint.getCoordinate() - elementHeight / 2.0F;
   }

   public void renderGuides(int screenWidth, int screenHeight, float elementX, float elementY, float elementWidth, float elementHeight) {
      long longValue = KEY ^ 31370805260041L;
      Color color = new Color(204, 204, 204);
      Color withAlpha = ColorUtil.withAlpha(color, 0.4F);
      float floatValue = 1.0F;
      boolean disabled = isDisabled();
      byte byteValue = 0;
      int value;
      int index = 0;
      byte byteValue2 = 0;
      int index2 = 0;
      float floatValue2 = elementX;
      float floatValue3 = elementY;
      SnapPoint snapPoint = this.horizontalSnap;
      if (disabled) {
         label143:
         if (this.horizontalSnap != null) {
            float floatValue4 = this.horizontalSnap.getCoordinate();
            float floatValue5;
            value = (floatValue5 = Math.abs(floatValue4 - screenWidth / 2.0F) - 1.0F) == 0.0F ? 0 : (floatValue5 < 0.0F ? -1 : 1);
            if (disabled) {
               value = value < 0 ? 1 : 0;
            }

            int value2 = value;
            value = value2;
            if (disabled) {
               if (value2 != 0) {
                  RenderUtil.fillRectArgb(floatValue4 - 1.0F, 0.0F, 1.0F, screenHeight, withAlpha.getRGB());
                  if (disabled) {
                     break label143;
                  }
               }

               float floatValue6;
               value = (floatValue6 = floatValue4 - screenWidth / 2.0F) == 0.0F ? 0 : (floatValue6 < 0.0F ? -1 : 1);
            }

            if (disabled) {
               if (value >= 0) {
                  floatValue2 = floatValue4 - elementWidth / 2.0F;
                  byteValue = 1;
                  if (disabled) {
                     break label143;
                  }
               }

               floatValue2 = floatValue4 - elementWidth / 2.0F;
               value = 1;
            }

            index = value;
         }

         snapPoint = this.verticalSnap;
      }

      label150: {
         if (disabled) {
            if (snapPoint == null) {
               break label150;
            }

            snapPoint = this.verticalSnap;
         }

         float floatValue7 = snapPoint.getCoordinate();
         float floatValue8;
         value = (floatValue8 = Math.abs(floatValue7 - screenHeight / 2.0F) - 1.0F) == 0.0F ? 0 : (floatValue8 < 0.0F ? -1 : 1);
         if (disabled) {
            value = value < 0 ? 1 : 0;
         }

         int value3 = value;
         value = value3;
         if (disabled) {
            if (value3 != 0) {
               RenderUtil.fillRectArgb(0.0F, floatValue7 - 0.5F, screenWidth, 1.0F, withAlpha.getRGB());
               if (disabled) {
                  break label150;
               }
            }

            float floatValue9;
            value = (floatValue9 = floatValue7 - screenHeight / 2.0F) == 0.0F ? 0 : (floatValue9 < 0.0F ? -1 : 1);
         }

         if (disabled) {
            if (value >= 0) {
               byteValue2 = 1;
               if (disabled) {
                  break label150;
               }
            }

            floatValue3 += 0.0F;
            value = 1;
         }

         index2 = value;
      }

      label151: {
         value = byteValue2;
         if (disabled) {
            if (byteValue2 != 0) {
               break label151;
            }

            value = index2;
         }

         if (disabled) {
            if (value != 0) {
               break label151;
            }

            value = byteValue;
         }

         if (disabled) {
            if (value != 0) {
               break label151;
            }

            value = index;
         }

         if (value == 0) {
            return;
         }
      }

      RenderUtil.strokeRectSides(floatValue2, floatValue3, elementWidth, elementHeight, 1.0F, withAlpha.getRGB(), byteValue2 != 0, index2 != 0, byteValue != 0, index != 0);
      if (BaseModule.getStateArray() == null) {
         setEnabled(!disabled);
      }
   }

   public Animator getHorizontalAnimator() {
      return this.horizontalAnimator;
   }

   public Animator getVerticalAnimator() {
      return this.verticalAnimator;
   }

   public static void setEnabled(boolean flag) {
      enabled = flag;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
   }

   static {
      try {
      long var2 = KEY ^ 126092827155608L;
      setEnabled(false);
      Cipher var4;
      Cipher var10000 = var4 = Cipher.getInstance("DES/CBC/NoPadding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var2 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var5 = 1; var5 < 8; var5++) {
         var10003[var5] = (byte)(var2 << var5 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long var6 = 5600156737998839203L;
      byte[] var8 = var4.doFinal(
         new byte[]{
            (byte)(var6 >>> 56),
            (byte)(var6 >>> 48),
            (byte)(var6 >>> 40),
            (byte)(var6 >>> 32),
            (byte)(var6 >>> 24),
            (byte)(var6 >>> 16),
            (byte)(var6 >>> 8),
            (byte)var6
         }
      );
      long var9 = (var8[0] & 255L) << 56
         | (var8[1] & 255L) << 48
         | (var8[2] & 255L) << 40
         | (var8[3] & 255L) << 32
         | (var8[4] & 255L) << 24
         | (var8[5] & 255L) << 16
         | (var8[6] & 255L) << 8
         | var8[7] & 255L;
      byte var10001 = -1;
      long var0 = var9;
      G = (int)var0;
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
