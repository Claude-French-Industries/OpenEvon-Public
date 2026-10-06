// 
// Decompiled by Procyon v0.6.0
// 

package club.evon.util;
import java.security.spec.AlgorithmParameterSpec;
import java.security.Key;
import javax.crypto.spec.IvParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.Cipher;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.lwjgl.system.MemoryStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.util.math.RandomUtil;




public final class FileDialogUtil
{
    private static final long KEY;
    private static final String[] STRINGS_TABLE;
    
    /**
     * Reports a message to the player.
     *
     * <p>This used to raise a Swing {@code JOptionPane}. That can never work inside Minecraft:
     * {@code net.minecraft.client.main.Main}'s static initialiser sets
     * {@code java.awt.headless=true}, so AWT/Wing constructs throw {@link java.awt.HeadlessException}
     * and used to take the whole game down. The message is now written to the log and, when the
     * client is in a world, to the chat HUD.
     *
     * @param title   short heading, prefixed to the message
     * @param message body text
     * @param type    kept for call-site compatibility; Swing message types no longer apply
     */
    public static void showMessage(final String title, final String message, final int type) {
        final String line = (title == null || title.isEmpty()) ? String.valueOf(message) : title + ": " + message;
        System.out.println("[Evon] " + line);

        try {
            final MinecraftClient client = ClientContext.MINECRAFT_CLIENT;
            if (client != null && client.inGameHud != null) {
                client.inGameHud.getChatHud().addMessage(Text.literal(line));
            }
        } catch (final Throwable throwable) {
            // Never let a notification take the game down.
        }
    }
    
    /**
     * Opens the native "pick a file" dialog and returns the chosen path (or null if cancelled).
     *
     * <p>The decompiled body built the filter buffer inside a {@code while (true)} loop that could
     * only reach the dialog through a guard which is always false in this build
     * ({@code RandomUtil.getStateArray()} is null). Control therefore fell through to
     * {@code mallocPointer.flip(); continue;} and span forever, never opening a dialog at all -
     * it hung the render thread at 100% CPU. Rebuilt as a straight build-then-call.
     */
    public static String openFileDialog(final String title, final String defaultPathAndFile, final String singleFilterDescription, final String... filters) {
        final MemoryStack stack = MemoryStack.stackPush();

        try {
            final PointerBuffer filterBuffer = stack.mallocPointer(filters.length);

            for (int i = 0; i < filters.length; i++) {
                filterBuffer.put(stack.UTF8(FileDialogUtil.STRINGS_TABLE[0] + filters[i]));
            }

            filterBuffer.flip();
            return TinyFileDialogs.tinyfd_openFileDialog(title, defaultPathAndFile, filterBuffer, singleFilterDescription, false);
        }
        finally {
            stack.close();
        }
    }
    
    /** Multi-select variant of {@link #openFileDialog}; same spin-loop fix. */
    public static String[] openMultipleFileDialog(final String title, final String defaultPathAndFile, final String singleFilterDescription, final String... filters) {
        final MemoryStack stack = MemoryStack.stackPush();

        try {
            final PointerBuffer filterBuffer = stack.mallocPointer(filters.length);

            for (int i = 0; i < filters.length; i++) {
                filterBuffer.put(stack.UTF8(FileDialogUtil.STRINGS_TABLE[2] + filters[i]));
            }

            filterBuffer.flip();
            final String selected = TinyFileDialogs.tinyfd_openFileDialog(title, defaultPathAndFile, filterBuffer, singleFilterDescription, true);

            if (selected == null) {
                return null;
            }

            return Arrays.asList(selected.split(FileDialogUtil.STRINGS_TABLE[1])).toArray(new String[0]);
        }
        finally {
            stack.close();
        }
    }
    
    static {
      try {
        KEY = KeyedRandom.create(-135370560636014645L, -8610263533947211224L, (Object)MethodHandles.lookup().lookupClass()).next(35728095726843L);
        final long n = FileDialogUtil.KEY ^ 0x7A1326384344L;
        final Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
        final int opmode = 2;
        final SecretKeyFactory instance2 = SecretKeyFactory.getInstance("DES");
        final byte[] key = new byte[8];
        key[0] = (byte)(n >>> 56);
        for (int i = 1; i < 8; ++i) {
            key[i] = (byte)(n << i * 8 >>> 56);
        }
        instance.init(opmode, instance2.generateSecret(new DESKeySpec(key)), new IvParameterSpec(new byte[8]));
        final String[] b2 = new String[3];
        int n2 = 0;
        final String s;
        final int length = (s = "W <iw|\u00c0\u00fa\b_\u00f43\u00fb\u00eb\u00dcN\u00dd\bW <iw|\u00c0\u00fa").length();
        int char1 = 8;
        int index = -1;
        while (true) {
            ++index;
            final String s2 = s;
            final int beginIndex = index;
            b2[n2++] = decodeUtf8(instance.doFinal(s2.substring(beginIndex, beginIndex + char1).getBytes("ISO-8859-1"))).intern();
            if ((index += char1) >= length) {
                break;
            }
            char1 = s.charAt(index);
        }
        STRINGS_TABLE = b2;
    
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }
    
    private static Throwable rethrow(final Throwable throwable) {
        return throwable;
    }
    
    private static String decodeUtf8(final byte[] bytes) {
        int count = 0;
        final int length;
        final char[] value = new char[length = bytes.length];
        for (int i = 0; i < length; ++i) {
            final int n;
            if ((n = (0xFF & bytes[i])) < 192) {
                value[count++] = (char)n;
            }
            else if (n < 224) {
                value[count++] = (char)((char)((char)(n & 0x1F) << 6) | (char)(bytes[++i] & 0x3F));
            }
            else if (i < length - 2) {
                value[count++] = (char)((char)((char)((char)(n & 0xF) << 12) | (char)(bytes[++i] & 0x3F) << 6) | (char)(bytes[++i] & 0x3F));
            }
        }
        return new String(value, 0, count);
    }
}
