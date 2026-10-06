package club.evon.launch;


/**
 * Native runtime loader.
 *
 * <p>The original build shipped an obfuscated native library
 * ({@code /club/evon/launch/x64-windows.dll}) which was extracted to a temp file and
 * {@code System.load}-ed on class initialisation. That library supplied the
 * implementations for every {@code native} method in the client, decrypted the
 * static string/constant tables of the protected classes, and ran the
 * HWID/licence handshake against the author's authentication server.
 *
 * <p>The native layer has been removed: it enforced the licence check, and it also
 * resolved its host classes by their original obfuscated names, so it could never
 * work against this deobfuscated source tree. Everything it used to provide now
 * lives in plain Java.
 *
 * <p>{@link #attachNatives(int, Class)} is kept as a no-op so the existing call
 * sites in the previously protected classes stay source-compatible.
 */
public class Loader {
   /**
    * Formerly registered the native implementations for {@code classValue}.
    *
    * @param value      the original class index used by the native library
    * @param classValue the class whose native methods were being bound
    */
   public static void attachNatives(int value, Class<?> classValue) {
      // Native runtime removed - see the class javadoc.
   }
}
