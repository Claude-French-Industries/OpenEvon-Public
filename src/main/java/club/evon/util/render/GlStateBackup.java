package club.evon.util.render;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import club.evon.runtime.IndyRuntime;
import club.evon.util.KeyedRandom;




public final class GlStateBackup {
   private static final int[] capabilities;
   private static final int[] pixelStoreParams;
   private static int activeTexture;
   private static int program;
   private static int boundTexture;
   private static int boundSampler;
   private static int boundVertexArray;
   private static int boundArrayBuffer;
   private static final Map<Integer, Boolean> capabilityStates;
   private static final Map<Integer, Integer> pixelStoreValues;
   private static int blendSrcRgb;
   private static int blendDstRgb;
   private static int blendSrcAlpha;
   private static int blendDstAlpha;
   private static int blendEquationRgb;
   private static int blendEquationAlpha;
   private static final int[] viewport;
   private static final int[] scissorBox;
   private static final long KEY = KeyedRandom.create(-8613179827614970658L, -8789608914223750868L, MethodHandles.lookup().lookupClass()).next(96895019453676L);
   private static final long[] encryptedValues;
   private static final Integer[] decryptedValues;
   private static final Map cipherCache = new HashMap(13);

   private GlStateBackup() {
   }

   public static void backup() {
      try {
      long longValue = KEY ^ 38162974200000L;
      long longValue2 = 5946646867354010773L ^ longValue;
      short shortValue = 2437;
      activeTexture = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue), longValue2}
            ))
            .intValue()
      );
      long longValue3 = 4893126753104782286L ^ longValue;
      short shortValue2 = 11999;
      program = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue2), longValue3}
            ))
            .intValue()
      );
      long longValue4 = 7823325491394676873L ^ longValue;
      short shortValue3 = 17801;
      boundTexture = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue3), longValue4}
            ))
            .intValue()
      );
      long longValue5 = 6327013691170121192L ^ longValue;
      short shortValue4 = 10483;
      boundSampler = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue4), longValue5}
            ))
            .intValue()
      );
      long longValue6 = 697729725499211760L ^ longValue;
      short shortValue5 = 13034;
      boundArrayBuffer = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue5), longValue6}
            ))
            .intValue()
      );
      long longValue7 = 130327712949961056L ^ longValue;
      short shortValue6 = 19565;
      boundVertexArray = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue6), longValue7}
            ))
            .intValue()
      );
      Arrays.stream(capabilities).forEach(var0x -> capabilityStates.put(var0x, GL32.glIsEnabled(var0x)));
      Arrays.stream(pixelStoreParams).forEach(var0x -> pixelStoreValues.put(var0x, GL32.glGetInteger(var0x)));
      long longValue8 = 4486533184416235817L ^ longValue;
      short shortValue7 = 3132;
      blendSrcRgb = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue7), longValue8}
            ))
            .intValue()
      );
      long longValue9 = 724211873218598074L ^ longValue;
      short shortValue8 = 22969;
      blendDstRgb = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue8), longValue9}
            ))
            .intValue()
      );
      long longValue10 = 1066406179001952763L ^ longValue;
      short shortValue9 = 9471;
      blendSrcAlpha = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue9), longValue10}
            ))
            .intValue()
      );
      long longValue11 = 7184920994182033404L ^ longValue;
      short shortValue10 = 8947;
      blendDstAlpha = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue10), longValue11}
            ))
            .intValue()
      );
      long longValue12 = 7574657214840441999L ^ longValue;
      short shortValue11 = 16781;
      blendEquationRgb = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue11), longValue12}
            ))
            .intValue()
      );
      long longValue13 = 824579680201398194L ^ longValue;
      short shortValue12 = 6839;
      blendEquationAlpha = GL32.glGetInteger(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue12), longValue13}
            ))
            .intValue()
      );
      GL32.glGetIntegerv(2978, viewport);
      GL32.glGetIntegerv(3088, scissorBox);
      } catch (Throwable throwable) {
         throw sneakyThrow(throwable);
      }
   }

   public static void restore() {
      try {
      long longValue = KEY ^ 119525995281300L;
      GL32.glUseProgram(program);
      GL32.glBindTexture(3553, boundTexture);
      GL33.glBindSampler(0, boundSampler);
      GL32.glActiveTexture(activeTexture);
      GL32.glBindVertexArray(boundVertexArray);
      long longValue2 = 2946919902463885258L ^ longValue;
      short shortValue = 6538;
      GL32.glBindBuffer(
         ((Number)IndyRuntime.invoke(
               "club.evon.util.render.GlStateBackup",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "u",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue), longValue2}
            ))
            .intValue(),
         boundArrayBuffer
      );
      GL32.glBlendEquationSeparate(blendEquationRgb, blendEquationAlpha);
      GL32.glBlendFuncSeparate(blendSrcRgb, blendDstRgb, blendSrcAlpha, blendDstAlpha);
      Arrays.stream(capabilities).forEach(var0x -> {
         long longValue3 = KEY ^ 139251608742719L;
         String[] var3x = RenderUtil.getGuardState();
         boolean get = capabilityStates.get(var0x);
         if (var3x == null) {
            if (get) {
               GL32.glEnable(var0x);
               if (var3x == null) {
                  return;
               }
            }

            GL32.glDisable(var0x);
            return;
         }

         GL32.glDisable(get ? 1 : 0);
      });
      Arrays.stream(pixelStoreParams).forEach(var0x -> GL32.glPixelStorei(var0x, pixelStoreValues.get(var0x)));
      GL32.glViewport(viewport[0], viewport[1], viewport[2], viewport[3]);
      GL32.glScissor(scissorBox[0], scissorBox[1], scissorBox[2], scissorBox[3]);
      } catch (Throwable throwable) {
         throw sneakyThrow(throwable);
      }
   }

   static {
      try {
      long var11 = KEY ^ 101199292061339L;
      Cipher var0;
      Cipher var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var1 = 1; var1 < 8; var1++) {
         var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long[] var6 = new long[27];
      int var3 = 0;
      String var4 = "\u00f9\"Y\u008bMu[\u00da^\u0087${(\u00fd\u00dc\u009d\u000f\u0019\u00b2\u00ae\u00a5(\u0001\u00e7\u00bd!\u0092\u00ff\u00e0\u009c\u00c1\u00c9\u00d0Avx\u00e0(D\u0093\u00c6\u00c0\u00e9\n6\u00fe\u00a0q\u00f2(\u00f3\u00db\u00af\u00d0\u00ad\u001e\u00a6Z\u00dc\u0006\u000e\u00ba\u00113\u00ba\u00b2\u00e0\u00aap\u009a\u00ed\u000bZ\u0015\u0003G\u00c8\u00c4_\u00dfR\u008f\u00ea\u00d5y\u00fc&\u00c9\u000b\u00ac\u008b\u00af\u009dv\u001a5\u00ce\u0082~\u009b9&^\u00a8\u00ed\u00bf\t\u0084W;\u0098\u0004\u009e\u0001\u000ee\u00fb\u001ey-\u00ed\u00efk\u0080?\u00fb\u00a9\u00af\u00b3\u0090\u00e6\u0014;r\u00cd\u0003T\u00d5\u0000L\u00f43\u0086+\u00b9\u00e6\u00eb[\u007f\u00b358S\u0011~6\u001b\u0096\u001a\u00bfe\u00f2B\u00b4\u00191\u0005\u00db\u00fbt\u0001V\u00eed\u00947L7\u00c1\u0011!\u00fe&\u00bf\u00d7\u0095*\u00870\u00a6\u00b2I\u008d\u00f4wu\t\u00c8{\u00b9";
      int var5 = "\u00f9\"Y\u008bMu[\u00da^\u0087${(\u00fd\u00dc\u009d\u000f\u0019\u00b2\u00ae\u00a5(\u0001\u00e7\u00bd!\u0092\u00ff\u00e0\u009c\u00c1\u00c9\u00d0Avx\u00e0(D\u0093\u00c6\u00c0\u00e9\n6\u00fe\u00a0q\u00f2(\u00f3\u00db\u00af\u00d0\u00ad\u001e\u00a6Z\u00dc\u0006\u000e\u00ba\u00113\u00ba\u00b2\u00e0\u00aap\u009a\u00ed\u000bZ\u0015\u0003G\u00c8\u00c4_\u00dfR\u008f\u00ea\u00d5y\u00fc&\u00c9\u000b\u00ac\u008b\u00af\u009dv\u001a5\u00ce\u0082~\u009b9&^\u00a8\u00ed\u00bf\t\u0084W;\u0098\u0004\u009e\u0001\u000ee\u00fb\u001ey-\u00ed\u00efk\u0080?\u00fb\u00a9\u00af\u00b3\u0090\u00e6\u0014;r\u00cd\u0003T\u00d5\u0000L\u00f43\u0086+\u00b9\u00e6\u00eb[\u007f\u00b358S\u0011~6\u001b\u0096\u001a\u00bfe\u00f2B\u00b4\u00191\u0005\u00db\u00fbt\u0001V\u00eed\u00947L7\u00c1\u0011!\u00fe&\u00bf\u00d7\u0095*\u00870\u00a6\u00b2I\u008d\u00f4wu\t\u00c8{\u00b9"
         .length();
      int var2 = 0;

      label23:
      while (true) {
         int var10001 = var2;
         var2 += 8;
         byte[] var7 = var4.substring(var10001, var2).getBytes("ISO-8859-1");
         long[] var42 = var6;
         var10001 = var3++;
         long var47 = (var7[0] & 255L) << 56
            | (var7[1] & 255L) << 48
            | (var7[2] & 255L) << 40
            | (var7[3] & 255L) << 32
            | (var7[4] & 255L) << 24
            | (var7[5] & 255L) << 16
            | (var7[6] & 255L) << 8
            | var7[7] & 255L;
         byte var49 = -1;

         while (true) {
            long var8 = var47;
            byte[] var10 = var0.doFinal(
               new byte[]{
                  (byte)(var8 >>> 56),
                  (byte)(var8 >>> 48),
                  (byte)(var8 >>> 40),
                  (byte)(var8 >>> 32),
                  (byte)(var8 >>> 24),
                  (byte)(var8 >>> 16),
                  (byte)(var8 >>> 8),
                  (byte)var8
               }
            );
            long var51 = (var10[0] & 255L) << 56
               | (var10[1] & 255L) << 48
               | (var10[2] & 255L) << 40
               | (var10[3] & 255L) << 32
               | (var10[4] & 255L) << 24
               | (var10[5] & 255L) << 16
               | (var10[6] & 255L) << 8
               | var10[7] & 255L;
            switch (var49) {
               case 0:
                  var42[var10001] = var51;
                  if (var2 >= var5) {
                     encryptedValues = var6;
                     decryptedValues = new Integer[27];
                     int[] var43 = new int[]{3042, 3058, 2884, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                     long var13 = 4517699572931192362L ^ var11;
                     short var14 = 11623;
                     var43[3] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var14), var13}
                        ))
                        .intValue();
                     var43[4] = 2929;
                     var43[5] = 3024;
                     long var15 = 6450324148583853709L ^ var11;
                     short var16 = 9687;
                     var43[6] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var16), var15}
                        ))
                        .intValue();
                     var43[7] = 2848;
                     long var17 = 1807953430532247225L ^ var11;
                     short var18 = 1531;
                     var43[8] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var18), var17}
                        ))
                        .intValue();
                     long var19 = 5149514706708027781L ^ var11;
                     short var20 = 4809;
                     var43[9] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var20), var19}
                        ))
                        .intValue();
                     var43[10] = 10754;
                     var43[11] = 10753;
                     var43[12] = 2881;
                     long var21 = 637988098861052102L ^ var11;
                     short var22 = 31635;
                     var43[13] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var22), var21}
                        ))
                        .intValue();
                     long var23 = 78585401705665714L ^ var11;
                     short var24 = 24544;
                     var43[14] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var24), var23}
                        ))
                        .intValue();
                     long var25 = 3046518719271491036L ^ var11;
                     short var26 = 7809;
                     var43[15] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var26), var25}
                        ))
                        .intValue();
                     long var27 = 820246747859895735L ^ var11;
                     short var28 = 3819;
                     var43[16] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var28), var27}
                        ))
                        .intValue();
                     long var29 = 7850426335940913493L ^ var11;
                     short var30 = 25093;
                     var43[17] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var30), var29}
                        ))
                        .intValue();
                     long var31 = 5581207742028174857L ^ var11;
                     short var32 = 7514;
                     var43[18] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var32), var31}
                        ))
                        .intValue();
                     var43[19] = 3089;
                     var43[20] = 2960;
                     capabilities = var43;
                     int[] var44 = new int[]{3328, 3329, 3330, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                     long var33 = 4548392654486802106L ^ var11;
                     short var34 = 17899;
                     var44[3] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var34), var33}
                        ))
                        .intValue();
                     var44[4] = 3332;
                     var44[5] = 3331;
                     long var35 = 5300106157861415157L ^ var11;
                     short var36 = 25506;
                     var44[6] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var36), var35}
                        ))
                        .intValue();
                     var44[7] = 3333;
                     var44[8] = 3312;
                     var44[9] = 3313;
                     var44[10] = 3314;
                     long var37 = 757260621016540186L ^ var11;
                     short var38 = 28499;
                     var44[11] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var38), var37}
                        ))
                        .intValue();
                     var44[12] = 3316;
                     var44[13] = 3315;
                     long var39 = 470625371423342773L ^ var11;
                     short var40 = 19453;
                     var44[14] = ((Number)IndyRuntime.invoke(
                           "club.evon.util.render.GlStateBackup",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "u",
                           "(IJ)I",
                           new Object[]{Integer.valueOf(var40), var39}
                        ))
                        .intValue();
                     var44[15] = 3317;
                     pixelStoreParams = var44;
                     activeTexture = 0;
                     program = 0;
                     boundTexture = 0;
                     boundSampler = 0;
                     boundVertexArray = 0;
                     boundArrayBuffer = 0;
                     capabilityStates = new HashMap<>();
                     pixelStoreValues = new HashMap<>();
                     blendSrcRgb = 0;
                     blendDstRgb = 0;
                     blendSrcAlpha = 0;
                     blendDstAlpha = 0;
                     blendEquationRgb = 0;
                     blendEquationAlpha = 0;
                     viewport = new int[4];
                     scissorBox = new int[4];
                     break label23;
                  }
                  break;
               default:
                  var42[var10001] = var51;
                  if (var2 < var5) {
                     continue label23;
                  }

                  var4 = "\u00eb\u0087m\u00b5/I\u009f\u000149o\u00e0\u0019\u009e\u00ae\b";
                  var5 = "\u00eb\u0087m\u00b5/I\u009f\u000149o\u00e0\u0019\u009e\u00ae\b".length();
                  var2 = 0;
            }

            int var46 = var2;
            var2 += 8;
            var7 = var4.substring(var46, var2).getBytes("ISO-8859-1");
            var42 = var6;
            var10001 = var3++;
            var47 = (var7[0] & 255L) << 56
               | (var7[1] & 255L) << 48
               | (var7[2] & 255L) << 40
               | (var7[3] & 255L) << 32
               | (var7[4] & 255L) << 24
               | (var7[5] & 255L) << 16
               | (var7[6] & 255L) << 8
               | var7[7] & 255L;
            var49 = 0;
         }
      }
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }
   }

   private static JSONException rethrow(JSONException exception) {
      return exception;
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }

   private static int decryptInt(int key, long seed) {
      int value = key ^ (int)(seed & 32767L) ^ 25115;
      if (decryptedValues[value] == null) {
         byte[] bytes = new byte[]{
            (byte)(seed >>> 56),
            (byte)(seed >>> 48),
            (byte)(seed >>> 40),
            (byte)(seed >>> 32),
            (byte)(seed >>> 24),
            (byte)(seed >>> 16),
            (byte)(seed >>> 8),
            (byte)seed
         };
         long longValue = encryptedValues[value];
         byte[] bytes2 = new byte[]{
            (byte)(longValue >>> 56),
            (byte)(longValue >>> 48),
            (byte)(longValue >>> 40),
            (byte)(longValue >>> 32),
            (byte)(longValue >>> 24),
            (byte)(longValue >>> 16),
            (byte)(longValue >>> 8),
            (byte)longValue
         };
         Long threadId = Thread.currentThread().threadId();
         Object[] values = (Object[])cipherCache.get(threadId);

         byte[] bytes3;
         try {
            if (values == null) {
               values = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
               cipherCache.put(threadId, values);
            }

            DESKeySpec dESKeySpec = new DESKeySpec(bytes);
            SecretKey generateSecret = ((SecretKeyFactory)values[1]).generateSecret(dESKeySpec);
            Cipher cipher = (Cipher)values[0];
            cipher.init(2, generateSecret, (IvParameterSpec)values[2]);
            bytes3 = cipher.doFinal(bytes2);
         } catch (Exception exception) {
            throw new RuntimeException("club/evon/util/render/GlStateBackup", exception);
         }

         int value2 = (bytes3[4] & 255) << 24 | (bytes3[5] & 255) << 16 | (bytes3[6] & 255) << 8 | bytes3[7] & 255;
         decryptedValues[value] = value2;
      }

      return decryptedValues[value];
   }

   private static int bootstrapIntConstant(Lookup lookup, MutableCallSite callSite, String name, Object[] args) {
      int value = (Integer)args[0];
      long longValue = (Long)args[1];
      int value2 = decryptInt(value, longValue);
      MethodHandle constant = MethodHandles.constant(int.class, value2);
      callSite.setTarget(MethodHandles.dropArguments(constant, 0, int.class, long.class));
      return value2;
   }

   private static CallSite bootstrap(Lookup lookup, String name, MethodType type) {
      MutableCallSite mutableCallSite = new MutableCallSite(type);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(
                  MethodHandles.lookup()
                     .findStatic(
                        GlStateBackup.class,
                        "bootstrapIntConstant",
                        MethodType.methodType(int.class, Lookup.class, MutableCallSite.class, String.class, Object[].class)
                     )
                     .asCollector(Object[].class, type.parameterCount()),
                  0,
                  lookup,
                  mutableCallSite,
                  name
               ),
               type
            )
         );
         return mutableCallSite;
      } catch (Exception exception) {
         throw new RuntimeException("club/evon/util/render/GlStateBackup : " + name + " : " + type.toString(), exception);
      }
   }
}
