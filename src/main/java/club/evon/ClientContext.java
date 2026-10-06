/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  net.minecraft.client.MinecraftClient
 *  org.json.JSONException
 */
package club.evon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import net.minecraft.client.MinecraftClient;
import org.json.JSONException;




public final class ClientContext {
    public static final MinecraftClient MINECRAFT_CLIENT;
    public static final File CLIENT_DIR;
    public static final Gson GSON;
    private static boolean initialized;

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {
        char[] cArray;
        block13: {
            int n;
            char[] cArray2;
            int n2;
            int n3;
            block12: {
                if (!ClientContext.isInitialized()) {
                    ClientContext.setInitialized(true);
                }
                char[] cArray3 = "\b2o\n\u0004,".toCharArray();
                int n4 = cArray3.length;
                n3 = 0;
                n2 = 114;
                cArray2 = cArray3;
                n = n4;
                if (n4 <= 1) break block12;
                cArray = cArray2;
                n = n;
                if (n <= n3) break block13;
            }
            do {
                int n5 = n2;
                cArray2 = cArray2;
                char[] cArray4 = cArray2;
                int n6 = n2;
                int n7 = n3;
                while (true) {
                    cArray4[n7] = (char)(cArray4[n7] ^ (n6 ^ (switch (n3 % 7) {
                        case 0 -> 85;
                        case 1 -> 5;
                        case 2 -> 107;
                        case 3 -> 23;
                        case 4 -> 24;
                        case 5 -> 113;
                        default -> 83;
                    })));
                    ++n3;
                    n2 = n5;
                    if (n5 != 0) break;
                    n5 = n2;
                    cArray2 = cArray2;
                    n7 = n2;
                    cArray4 = cArray2;
                    n6 = n2;
                }
                cArray = cArray2;
                n = n;
            } while (n > n3);
        }
        String string = new String(cArray).intern();
        MINECRAFT_CLIENT = MinecraftClient.getInstance();
        CLIENT_DIR = new File(ClientContext.MINECRAFT_CLIENT.runDirectory, string);
        GSON = new GsonBuilder().setPrettyPrinting().generateNonExecutableJson().create();
    }

    public static void setInitialized(boolean bl) {
        initialized = bl;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static boolean isNotInitialized() {
        boolean bl = ClientContext.isInitialized();
        return !bl;
    }

    private static JSONException a(JSONException jSONException) {
        return jSONException;
    }
}
