// 
// Decompiled by Procyon v0.6.0
// 

package club.evon.util.render;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import javax.crypto.SecretKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.Key;
import javax.crypto.spec.IvParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.Cipher;
import java.util.HashMap;
import java.lang.invoke.MethodHandles;
import java.nio.FloatBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.nanovg.NVGColor;
import java.io.InputStream;
import org.lwjgl.nanovg.NanoVG;
import java.util.Map;
import java.nio.ByteBuffer;
import club.evon.module.BaseModule;
import club.evon.module.impl.misc.Streamer;
import club.evon.runtime.IndyRuntime;
import club.evon.util.IoUtil;
import club.evon.util.KeyedRandom;




public final class NvgFont
{
    private final long nvgContext;
    private String fontName;
    private ByteBuffer fontData;
    public static boolean suppressText;
    private static String stateString;
    private static final long KEY;
    private static final long[] encryptedValues;
    private static final Integer[] decryptedValues;
    private static final Map cipherCache;
    
    public NvgFont(final String name, final String filePath) {
        final long n = NvgFont.KEY ^ 0x42E67EB3FD5EL;
        final String r = getStateString();
        this.nvgContext = RenderUtil.NVG_CONTEXT;
        final String s = r;
        final InputStream resourceAsStream = NvgFont.class.getResourceAsStream(filePath);
        if (s != null) {
            if (resourceAsStream == null) {
                throw new RuntimeException();
            }
            this.fontName = name;
            this.fontData = IoUtil.readToBuffer(resourceAsStream);
            NanoVG.nvgCreateFontMem(this.nvgContext, this.fontName, this.fontData, false);
        }
    }
    
    public void drawText(final String text, final float x, final float y, final float size, final int color) {
        this.drawText(text, x, y, size, color, false, 0.0f, 9);
    }
    
    public void drawText(final String text, final float x, final float y, final float size, final int color, final boolean shadow) {
        this.drawText(text, x, y, size, color, shadow, 0.0f, 9);
    }
    
    public void drawText(final String text, final float x, final float y, final float size, final int color, final boolean shadow, final int alignment) {
        this.drawText(text, x, y, size, color, shadow, 0.0f, alignment);
    }
    
    public void drawText(String text, final float x, final float y, final float size, final int color, final boolean shadow, final float bloomAmount, final int alignment) {
        try {
        final long n = NvgFont.KEY ^ 0x238410940B04L;
        final String r = getStateString();
        int h;
        int n3;
        final int n2 = n3 = (h = (NvgFont.suppressText ? 1 : 0));
        if (r != null) {
            if (n2 != 0) {
                return;
            }
            text = Streamer.censor(text);
            NanoVG.nvgFontFace(this.nvgContext, this.fontName);
            NanoVG.nvgFontSize(this.nvgContext, size);
            NanoVG.nvgTextAlign(this.nvgContext, alignment);
            n3 = bloomAmount > 0.0f ? 1 : (bloomAmount == 0.0f ? 0 : -1);
        }
        if (r != null) {
            if (n3 > 0) {
                final NVGColor v = RenderUtil.toNvgColor((color >> 24 & 0xFF) << 24 | (color & ((Number)IndyRuntime.invoke("club.evon.util.render.NvgFont", "bootstrap", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", "i", "(IJ)I", new Object[] { 10650, 0x22CD01378705DA04L ^ n })).intValue()));
                NanoVG.nvgFontBlur(this.nvgContext, bloomAmount);
                NanoVG.nvgFillColor(this.nvgContext, v);
                NanoVG.nvgText(this.nvgContext, x, y, text);
                v.free();
                NanoVG.nvgFontBlur(this.nvgContext, 0.0f);
            }
            h = (shadow ? 1 : 0);
            n3 = (shadow ? 1 : 0);
        }
        if (r != null) {
            if (n3 != 0) {
                final NVGColor v2 = RenderUtil.toNvgColor((color & ((Number)IndyRuntime.invoke("club.evon.util.render.NvgFont", "bootstrap", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", "i", "(IJ)I", new Object[] { 15734, 0x5B8496E0BA714EEAL ^ n })).intValue()) >> 2 | (color & ((Number)IndyRuntime.invoke("club.evon.util.render.NvgFont", "bootstrap", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", "i", "(IJ)I", new Object[] { 10525, 0x3AF1F9B50622DA80L ^ n })).intValue()));
                NanoVG.nvgFillColor(this.nvgContext, v2);
                NanoVG.nvgText(this.nvgContext, x + 0.5f, y + 0.5f, text);
                v2.free();
            }
            h = color;
        }
        final NVGColor v3 = RenderUtil.toNvgColor(h);
        NanoVG.nvgFillColor(this.nvgContext, v3);
        NanoVG.nvgText(this.nvgContext, x, y, text);
        v3.free();
        } catch (Throwable throwable) {
            throw sneakyThrow(throwable);
        }
    }
    
    public float getTextWidth(final String text, final float size, final int alignment) {
        final long n = NvgFont.KEY ^ 0x6355E9911B53L;
        final String r = getStateString();
        NanoVG.nvgFontFace(this.nvgContext, this.fontName);
        NanoVG.nvgFontSize(this.nvgContext, size);
        final String s = r;
        NanoVG.nvgTextAlign(this.nvgContext, alignment);
        final MemoryStack stackPush = MemoryStack.stackPush();
        try {
            final float nvgTextBounds = NanoVG.nvgTextBounds(this.nvgContext, 0.0f, 0.0f, text, stackPush.mallocFloat(4));
            final MemoryStack memoryStack = stackPush;
            if (s == null || memoryStack != null) {
                memoryStack.close();
            }
            return nvgTextBounds;
        }
        catch (final Throwable t) {
            final MemoryStack memoryStack2 = stackPush;
            if (s != null) {
                if (memoryStack2 == null) {
                    throw t;
                }
            }
            try {
                memoryStack2.close();
            }
            catch (final Throwable exception) {
                t.addSuppressed(exception);
            }
            throw t;
        }
    }
    
    public float getTextWidth(final String text, final float size) {
        return this.getTextWidth(text, size, 9);
    }
    
    public float getTextHeight(final String text, final float size, final int alignment) {
        final long n = NvgFont.KEY ^ 0x522780F857BDL;
        final String r = getStateString();
        NanoVG.nvgFontFace(this.nvgContext, this.fontName);
        final String s = r;
        NanoVG.nvgFontSize(this.nvgContext, size);
        NanoVG.nvgTextAlign(this.nvgContext, alignment);
        final MemoryStack stackPush = MemoryStack.stackPush();
        try {
            final FloatBuffer mallocFloat = stackPush.mallocFloat(4);
            NanoVG.nvgTextBounds(this.nvgContext, 0.0f, 0.0f, text, mallocFloat);
            final float n2 = mallocFloat.get(3) - mallocFloat.get(1);
            final MemoryStack memoryStack = stackPush;
            if (s == null || memoryStack != null) {
                memoryStack.close();
            }
            final float n3 = n2;
            if (BaseModule.getStateArray() == null) {
                setStateString("WK509");
            }
            return n3;
        }
        catch (final Throwable t) {
            final MemoryStack memoryStack2 = stackPush;
            if (s != null) {
                if (memoryStack2 == null) {
                    throw t;
                }
            }
            try {
                memoryStack2.close();
            }
            catch (final Throwable exception) {
                t.addSuppressed(exception);
            }
            throw t;
        }
    }
    
    public float getTextHeight(final String text, final float size) {
        final long n = NvgFont.KEY ^ 0x60A93CCCE055L;
        NanoVG.nvgFontFace(this.nvgContext, this.fontName);
        final String r = getStateString();
        NanoVG.nvgFontSize(this.nvgContext, size);
        NanoVG.nvgTextAlign(this.nvgContext, 9);
        final String s = r;
        final MemoryStack stackPush = MemoryStack.stackPush();
        try {
            final FloatBuffer mallocFloat = stackPush.mallocFloat(4);
            NanoVG.nvgTextBounds(this.nvgContext, 0.0f, 0.0f, text, mallocFloat);
            final float n2 = mallocFloat.get(3) - mallocFloat.get(1);
            final MemoryStack memoryStack = stackPush;
            if (s == null || memoryStack != null) {
                memoryStack.close();
            }
            final float n3 = n2;
            if (s == null) {
                BaseModule.setStateArray(new BaseModule[2]);
            }
            return n3;
        }
        catch (final Throwable t) {
            final MemoryStack memoryStack2 = stackPush;
            if (s != null) {
                if (memoryStack2 == null) {
                    throw t;
                }
            }
            try {
                memoryStack2.close();
            }
            catch (final Throwable exception) {
                t.addSuppressed(exception);
            }
            throw t;
        }
    }
    
    public static void setStateString(final String l) {
        NvgFont.stateString = l;
    }
    
    public static String getStateString() {
        return NvgFont.stateString;
    }
    
    static {
      try {
        KEY = KeyedRandom.create(-1684887590496467845L, 8094525656688395030L, (Object)MethodHandles.lookup().lookupClass()).next(205488354545760L);
        final String s = "Zd6Az";
        cipherCache = new HashMap(13);
        final long n = NvgFont.KEY ^ 0x5994CE204A4BL;
        setStateString(s);
        final Cipher instance = Cipher.getInstance("DES/CBC/NoPadding");
        final int opmode = 2;
        final SecretKeyFactory instance2 = SecretKeyFactory.getInstance("DES");
        final byte[] key = new byte[8];
        key[0] = (byte)(n >>> 56);
        for (int i = 1; i < 8; ++i) {
            key[i] = (byte)(n << i * 8 >>> 56);
        }
        instance.init(opmode, instance2.generateSecret(new DESKeySpec(key)), new IvParameterSpec(new byte[8]));
        final long[] c2 = new long[3];
        int n2 = 0;
        final String s2;
        final int length = (s2 = "\u00d4k\u007f\u00c1\u0095T\u00e7]\u00c5\u00ad[]¤\u00edj\u00ec\u001e¥¸¿8\u0014\u00ca\u00fa").length();
        int j = 0;
        do {
            final String s3 = s2;
            final int beginIndex = j;
            j += 8;
            final byte[] bytes = s3.substring(beginIndex, j).getBytes("ISO-8859-1");
            final long[] array = c2;
            final int n3 = n2++;
            final long n4 = ((long)bytes[0] & 0xFFL) << 56 | ((long)bytes[1] & 0xFFL) << 48 | ((long)bytes[2] & 0xFFL) << 40 | ((long)bytes[3] & 0xFFL) << 32 | ((long)bytes[4] & 0xFFL) << 24 | ((long)bytes[5] & 0xFFL) << 16 | ((long)bytes[6] & 0xFFL) << 8 | ((long)bytes[7] & 0xFFL);
            final byte[] doFinal = instance.doFinal(new byte[] { (byte)(n4 >>> 56), (byte)(n4 >>> 48), (byte)(n4 >>> 40), (byte)(n4 >>> 32), (byte)(n4 >>> 24), (byte)(n4 >>> 16), (byte)(n4 >>> 8), (byte)n4 });
            array[n3] = (((long)doFinal[0] & 0xFFL) << 56 | ((long)doFinal[1] & 0xFFL) << 48 | ((long)doFinal[2] & 0xFFL) << 40 | ((long)doFinal[3] & 0xFFL) << 32 | ((long)doFinal[4] & 0xFFL) << 24 | ((long)doFinal[5] & 0xFFL) << 16 | ((long)doFinal[6] & 0xFFL) << 8 | ((long)doFinal[7] & 0xFFL));
        } while (j < length);
        encryptedValues = c2;
        decryptedValues = new Integer[3];
    
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}
    
    private static Throwable rethrow(final Throwable throwable) {
        return throwable;
    }
    
    private static int decryptInt(final int n, final long seed) {
        final int n3 = n ^ (int)(seed & 0x7FFFL) ^ 0x4FA5;
        if (NvgFont.decryptedValues[n3] == null) {
            final byte[] key = { (byte)(seed >>> 56), (byte)(seed >>> 48), (byte)(seed >>> 40), (byte)(seed >>> 32), (byte)(seed >>> 24), (byte)(seed >>> 16), (byte)(seed >>> 8), (byte)seed };
            final long n4 = NvgFont.encryptedValues[n3];
            final byte[] input = { (byte)(n4 >>> 56), (byte)(n4 >>> 48), (byte)(n4 >>> 40), (byte)(n4 >>> 32), (byte)(n4 >>> 24), (byte)(n4 >>> 16), (byte)(n4 >>> 8), (byte)n4 };
            final Long value = Thread.currentThread().threadId();
            Object[] array = (Object[])NvgFont.cipherCache.get(value);
            byte[] doFinal;
            try {
                if (array == null) {
                    array = new Object[] { Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8]) };
                    NvgFont.cipherCache.put(value, array);
                }
                final SecretKey generateSecret = ((SecretKeyFactory)array[1]).generateSecret(new DESKeySpec(key));
                final Cipher cipher = (Cipher)array[0];
                cipher.init(2, generateSecret, (AlgorithmParameterSpec)array[2]);
                doFinal = cipher.doFinal(input);
            }
            catch (final Exception cause) {
                throw new RuntimeException("club/evon/util/render/NvgFont", cause);
            }
            NvgFont.decryptedValues[n3] = ((doFinal[4] & 0xFF) << 24 | (doFinal[5] & 0xFF) << 16 | (doFinal[6] & 0xFF) << 8 | (doFinal[7] & 0xFF));
        }
        return NvgFont.decryptedValues[n3];
    }
    
    private static int bootstrapIntConstant(final MethodHandles.Lookup lookup, final MutableCallSite callSite, final String s, final Object[] args) {
        final int a = decryptInt((int)args[0], (long)args[1]);
        callSite.setTarget(MethodHandles.dropArguments(MethodHandles.constant(Integer.TYPE, a), 0, (Class<?>[])new Class[] { Integer.TYPE, Long.TYPE }));
        return a;
    }
    
    private static CallSite bootstrap(final MethodHandles.Lookup p0, final String p1, final MethodType type) {
        // NOTE: Procyon could not decompile this method (it choked on the MethodHandle
        // constant-pool entry) and left a stub that threw IllegalStateException, which broke
        // every NvgFont.drawText() call. The body below is the same constant-call-site
        // factory the other obfuscated classes in this tree use - it binds the invokedynamic
        // call sites in drawText() to bootstrapIntConstant()/decryptInt() above.
        final MutableCallSite callSite = new MutableCallSite(type);

        try {
            callSite.setTarget(
                MethodHandles.explicitCastArguments(
                    MethodHandles.insertArguments(
                        MethodHandles.lookup()
                            .findStatic(
                                NvgFont.class,
                                "bootstrapIntConstant",
                                MethodType.methodType(
                                    Integer.TYPE, MethodHandles.Lookup.class, MutableCallSite.class, String.class, Object[].class
                                )
                            )
                            .asCollector(Object[].class, type.parameterCount()),
                        0,
                        p0,
                        callSite,
                        p1
                    ),
                    type
                )
            );
            return callSite;
        }
        catch (final Exception cause) {
            throw new RuntimeException("club/evon/util/render/NvgFont : " + p1 + " : " + type.toString(), cause);
        }
    }


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
