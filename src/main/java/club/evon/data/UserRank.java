/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.text.TextColor
 *  org.json.JSONException
 */
package club.evon.data;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import net.minecraft.text.TextColor;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public enum UserRank {
    USER(0, new Color(196, 237, 255)),
    MEDIA(1, new Color(220, 29, 86)),
    DEVELOPER(2, new Color(85, 140, 225));

    private final int y;
    private final Color f;
    private final TextColor textColor;
    private static String state;
    private static final long KEY;

    private UserRank(int n2, Color color) {
        this.y = n2;
        this.f = color;
        this.textColor = TextColor.fromRgb((int)color.getRGB());
    }

    public static UserRank fromLevel(int level) {
        UserRank _H2;
        block5: {
            block4: {
                long l = KEY ^ 0x73484B6EE37EL;
                UserRank[] _HArray = UserRank.values();
                String string = UserRank.getState();
                int n = _HArray.length;
                int n2 = 0;
                while (n2 < n) {
                    UserRank _H3 = _HArray[n2];
                    if (string != null) {
                        _H2 = _H3;
                        if (string == null) break block4;
                        if (_H2.y == level) {
                            return _H3;
                        }
                        ++n2;
                    }
                    if (string != null) continue;
                }
                _H2 = USER;
            }
            if (BaseModule.getStateArray() != null) break block5;
            UserRank.setState("D6nHMb");
        }
        return _H2;
    }

    public static TextColor getTextColorByLevel(int level) {
        return UserRank.fromLevel((int)level).textColor;
    }

    public int getLevel() {
        return this.y;
    }

    public Color getColor() {
        return this.f;
    }

    public TextColor getTextColor() {
        return this.textColor;
    }

    static {
        KEY = KeyedRandom.create(-2823639915918582774L, 6515324796898441485L, MethodHandles.lookup().lookupClass()).next(86013608429096L);
        UserRank.setState("IqBQ3b");
    }

    public static void setState(String value) {
        state = value;
    }

    public static String getState() {
        return state;
    }

    private static JSONException identity(JSONException exception) {
        return exception;
    }

    private static String decodeUtf8(byte[] bytes) {
        int n = 0;
        int n2 = bytes.length;
        char[] cArray = new char[n2];
        for (int i = 0; i < n2; ++i) {
            char c;
            int n3 = 0xFF & bytes[i];
            if (n3 < 192) {
                cArray[n++] = (char)n3;
                continue;
            }
            if (n3 < 224) {
                c = (char)((char)(n3 & 0x1F) << 6);
                n3 = bytes[++i];
                c = (char)(c | (char)(n3 & 0x3F));
                cArray[n++] = c;
                continue;
            }
            if (i >= n2 - 2) continue;
            c = (char)((char)(n3 & 0xF) << 12);
            n3 = bytes[++i];
            c = (char)(c | (char)(n3 & 0x3F) << 6);
            n3 = bytes[++i];
            c = (char)(c | (char)(n3 & 0x3F));
            cArray[n++] = c;
        }
        return new String(cArray, 0, n);
    }
}
