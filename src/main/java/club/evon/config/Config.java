// 
// Decompiled by Procyon v0.6.0
// 

package club.evon.config;
import java.lang.invoke.CallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.SwitchBootstraps;
import java.lang.invoke.MethodType;
import java.security.spec.AlgorithmParameterSpec;
import java.security.Key;
import javax.crypto.spec.IvParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.Cipher;
import java.lang.invoke.MethodHandles;
import net.minecraft.util.math.MathHelper;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.awt.Color;
import net.minecraft.util.math.Vec2f;
import java.util.Objects;
import com.google.gson.JsonElement;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import com.google.gson.JsonObject;
import java.io.File;
import java.time.format.DateTimeFormatter;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.UserRank;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.setting.BindMode;
import club.evon.setting.BindSetting;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.ColorSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.setting.ModeSetting;
import club.evon.setting.PositionSetting;
import club.evon.setting.Setting;
import club.evon.setting.StringSetting;
import club.evon.util.AesEncryption;
import club.evon.util.KeyedRandom;




public final class Config
{
    private static final DateTimeFormatter DATE_FORMATTER;
    private String version;
    private String author;
    private String date;
    private int level;
    private final String F;
    private final File file;
    private static final long KEY;
    private static final String[] STRINGS;
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public Config(final String name) {
        final String[] c = Config.STRINGS;
        this.version = c[11];
        this.author = c[6];
        this.date = c[6];
        this.level = UserRank.USER.getLevel();
        this.F = name;
        this.file = new File(new File(ClientContext.MINECRAFT_CLIENT.runDirectory, c[20]), name + c[4]);
    }
    
    public void save() {
        final long n = Config.KEY ^ 0x182834312C73L;
        final boolean o = ConfigManager.O();
        try {
            this.file.createNewFile();
            final JsonObject jsonElement = new JsonObject();
            final JsonObject value = new JsonObject();
            final String[] c = Config.STRINGS;
            this.version = c[16];
            this.author = EvonClient.getUser().getUsername();
            this.level = EvonClient.getUser().getRole().getLevel();
            this.date = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            value.addProperty(c[0], this.version);
            value.addProperty(c[21], this.author);
            value.addProperty(c[23], this.level);
            value.addProperty(c[22], this.date);
            jsonElement.add(c[7], value);
            final JsonObject value2 = new JsonObject();
            final Iterator iterator = EvonClient.getInstance().getModuleManager().getModules().iterator();

            while (iterator.hasNext()) {
                final BaseModule gq = (BaseModule)iterator.next();
                final JsonObject value3 = new JsonObject();
                value3.addProperty(Config.STRINGS[2], gq.isEnabled());
                if (o) {
                    break;
                }

                for (final Setting obj : gq.getSettings()) {
                    final Setting b2 = obj;
                    Objects.requireNonNull(obj);
                    final Setting sub = (Setting)obj;
                    final int b3 = 0;
                    switch (ProcyonInvokeDynamicHelper_1.invoke(sub, b3)) {
                        case 0: {
                            final BindSetting l = (BindSetting)sub;
                            final JsonObject value4 = new JsonObject();
                            final String[] c2 = Config.STRINGS;
                            value4.addProperty(c2[25], (Number)l.getValue());
                            value4.addProperty(c2[5], l.getBindMode().name());
                            value3.add(l.getName(), value4);
                            break;
                        }
                        case 1: {
                            value3.addProperty(b2.getName(), ((BooleanSetting)sub).I());
                            break;
                        }
                        case 2: {
                            final BooleanGroupSetting k = (BooleanGroupSetting)sub;
                            final JsonObject value5 = new JsonObject();
                            final BooleanSetting[] array = (BooleanSetting[])k.getValue();
                            for (int i = 0; i < array.length; i++) {
                                final BooleanSetting t_c2 = array[i];
                                value5.addProperty(t_c2.getName(), t_c2.I());
                            }
                            value3.add(b2.getName(), value5);
                            break;
                        }
                        case 3: {
                            value3.addProperty(b2.getName(), (Number)((DoubleSetting)sub).J());
                            break;
                        }
                        case 4: {
                            value3.addProperty(b2.getName(), String.valueOf(((EnumSetting)sub).getValue()));
                            break;
                        }
                        case 5: {
                            final PositionSetting r = (PositionSetting)sub;
                            final JsonObject value6 = new JsonObject();
                            value6.addProperty("x", r.getValue().x);
                            value6.addProperty("y", r.getValue().y);
                            value3.add(r.getName(), value6);
                            break;
                        }
                        case 6: {
                            value3.addProperty(b2.getName(), String.valueOf(((SubMode)((ModeSetting)sub).getValue()).getName()));
                            break;
                        }
                        case 7: {
                            value3.addProperty(b2.getName(), (String)((StringSetting)sub).getValue());
                            break;
                        }
                        case 8: {
                            value3.addProperty(b2.getName(), ((Color)((ColorSetting)sub).getValue()).getRGB());
                            break;
                        }
                        default:
                            break;
                    }
                }
                value2.add(gq.getName(), value3);
            }

            jsonElement.add(Config.STRINGS[18], value2);
            final String[] c3 = Config.STRINGS;
            final String s = c3[10];
            final String q = AesEncryption.encrypt(c3[10], ClientContext.GSON.toJson(jsonElement));
            try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file, false))) {
                bufferedWriter.write(q);
            }
        }
        catch (final Exception ex) {
            // Never let a broken config take the client down; the failure is now visible in the log.
            Config.LOGGER.error("[Evon] Could not save config '{}'", this.F, ex);
        }
    }
    
    public void loadMetadata() {
        final long n = Config.KEY ^ 0x3C354E5954D1L;
        final boolean o = ConfigManager.O();
        if (!this.file.exists()) {
            return;
        }
        try {
            final StringBuilder sb = new StringBuilder();
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
            String j = null;
            Label_0120: {
                try {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                        if (o) {
                            break Label_0120;
                        }
                        if (o) {
                            break;
                        }
                    }
                    bufferedReader.close();
                }
                catch (final Throwable t) {
                    try {
                        bufferedReader.close();
                    }
                    catch (final Throwable exception) {
                        t.addSuppressed(exception);
                    }
                    throw t;
                }
                try {
                    final String[] c = Config.STRINGS;
                    final String s = c[10];
                    j = AesEncryption.decrypt(c[10], sb.toString());
                }
                catch (final Exception ex) {
                    return;
                }
            }
            final JsonObject jsonObject = ClientContext.GSON.fromJson(j, JsonObject.class);
            JsonObject jsonObject3;
            final JsonObject jsonObject2 = jsonObject3 = jsonObject;
            if (!o) {
                if (jsonObject2 == null) {
                    return;
                }
                final JsonObject jsonObject4;
                jsonObject3 = (jsonObject4 = jsonObject);
            }
            final String s2 = Config.STRINGS[7];
            if (!o) {
                if (!jsonObject2.has(s2)) {
                    return;
                }
                jsonObject3 = jsonObject;
                final String s3 = Config.STRINGS[7];
            }
            final JsonObject asJsonObject = jsonObject3.getAsJsonObject(s2);
            boolean b3;
            boolean has;
            boolean b2;
            final boolean b = b2 = (has = (b3 = asJsonObject.has(Config.STRINGS[21])));
            if (!o) {
                if (b) {
                    this.author = asJsonObject.get(Config.STRINGS[21]).getAsString();
                }
                has = (b2 = (b3 = asJsonObject.has(Config.STRINGS[23])));
            }
            if (!o) {
                if (b2) {
                    this.level = asJsonObject.get(Config.STRINGS[23]).getAsInt();
                }
                b3 = (has = asJsonObject.has(Config.STRINGS[0]));
            }
            JsonObject jsonObject5 = null;
            String s4 = null;
            Label_0426: {
                if (!o) {
                    if (has) {
                        this.version = asJsonObject.get(Config.STRINGS[0]).getAsString();
                    }
                    jsonObject5 = asJsonObject;
                    s4 = Config.STRINGS[22];
                    if (o) {
                        break Label_0426;
                    }
                    b3 = jsonObject5.has(s4);
                }
                if (!b3) {
                    return;
                }
                final String s5 = Config.STRINGS[22];
            }
            this.date = ZonedDateTime.parse(jsonObject5.get(s4).getAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME).withZoneSameInstant(ZoneId.systemDefault()).toLocalDate().format(Config.DATE_FORMATTER);
        }
        catch (final Exception ex2) {}
    }
    
    public void load() {
        this.load(false);
    }
    
    public void load(final boolean loadKeybinds) {
        final long n = Config.KEY ^ 0x490C4DBF24ADL;
        if (!this.file.exists()) {
            return;
        }
        try {
            final StringBuilder sb = new StringBuilder();
            try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            }
            String j;
            try {
                final String[] c = Config.STRINGS;
                final String s = c[12];
                j = AesEncryption.decrypt(c[10], sb.toString());
            }
            catch (final Exception ex) {
                // Corrupt or not ours: skip the file and keep the current in-memory state.
                Config.LOGGER.error("[Evon] Config '{}' is unreadable, skipping it", this.F, ex);
                return;
            }
            final JsonObject jsonObject = ClientContext.GSON.fromJson(j, JsonObject.class);
            if (jsonObject == null) {
                return;
            }
            final JsonObject jsonObject2 = jsonObject;
            final String[] c2 = Config.STRINGS;
            if (jsonObject2.has(c2[8])) {
                final JsonObject asJsonObject = jsonObject.getAsJsonObject(c2[7]);
                if (asJsonObject.has(c2[15])) {
                    this.author = asJsonObject.get(c2[21]).getAsString();
                }
                if (asJsonObject.has(Config.STRINGS[9])) {
                    this.level = asJsonObject.get(Config.STRINGS[23]).getAsInt();
                }
                if (asJsonObject.has(Config.STRINGS[24])) {
                    this.version = asJsonObject.get(Config.STRINGS[0]).getAsString();
                }
                final JsonObject jsonObject3 = asJsonObject;
                final String[] c3 = Config.STRINGS;
                if (jsonObject3.has(c3[1])) {
                    this.date = ZonedDateTime.parse(asJsonObject.get(c3[22]).getAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME).withZoneSameInstant(ZoneId.systemDefault()).toLocalDate().format(Config.DATE_FORMATTER);
                }
            }
            final JsonObject jsonObject4 = jsonObject;
            final String[] c4 = Config.STRINGS;
            if (jsonObject4.has(c4[17])) {
                final JsonObject asJsonObject2 = jsonObject.getAsJsonObject(c4[18]);
                for (BaseModule gq : EvonClient.getInstance().getModuleManager().getModules()) {
                    if (!asJsonObject2.has(gq.getName())) {
                        continue;
                    }
                    final JsonObject asJsonObject3 = asJsonObject2.getAsJsonObject(gq.getName());
                    gq.setEnabled(false);
                    for (Setting b : gq.getSettings()) {
                        if (!asJsonObject3.has(b.getName())) {
                            continue;
                        }
                        final Setting obj = b;
                        Objects.requireNonNull(obj);
                        final Setting sub = (Setting)obj;
                        int n2 = 0;
                    Label_1088:
                        while (true) {
                            switch (/* invokedynamic(!) */ProcyonInvokeDynamicHelper_2.invoke(sub, n2)) {
                                case 0: {
                                    final BindSetting l = (BindSetting)sub;
                                    if (!loadKeybinds) {
                                        n2 = 1;
                                        continue;
                                    }
                                    final JsonObject asJsonObject4 = asJsonObject3.getAsJsonObject(l.getName());
                                    if (asJsonObject4.has(Config.STRINGS[14])) {
                                        l.setValue((Integer)asJsonObject4.get(Config.STRINGS[25]).getAsInt());
                                    }
                                    if (asJsonObject4.has(Config.STRINGS[13])) {
                                        this.loadBindSetting((Setting<?>)l, String.valueOf(asJsonObject4.get(Config.STRINGS[5]).getAsString()));
                                    }
                                    break Label_1088;
                                }
                                case 1: {
                                    ((BooleanSetting)sub).setValue((Boolean)asJsonObject3.get(b.getName()).getAsBoolean());
                                    break Label_1088;
                                }
                                case 2: {
                                    final BooleanGroupSetting k = (BooleanGroupSetting)sub;
                                    final JsonObject asJsonObject5 = asJsonObject3.get(b.getName()).getAsJsonObject();
                                    for (final BooleanSetting t_c2 : (BooleanSetting[])k.getValue()) {
                                        t_c2.setValue((Boolean)asJsonObject5.get(t_c2.getName()).getAsBoolean());
                                    }
                                    break Label_1088;
                                }
                                case 3: {
                                    ((DoubleSetting)sub).setValue(Double.valueOf(asJsonObject3.get(b.getName()).getAsDouble()));
                                    break Label_1088;
                                }
                                case 4: {
                                    final EnumSetting i_c2 = (EnumSetting)sub;
                                    this.loadEnumSetting((Setting<?>)i_c2, String.valueOf(asJsonObject3.get(i_c2.getName()).getAsString()));
                                    break Label_1088;
                                }
                                case 5: {
                                    final PositionSetting r = (PositionSetting)sub;
                                    final JsonObject asJsonObject6 = asJsonObject3.getAsJsonObject(r.getName());
                                    if (asJsonObject6.has("x") && asJsonObject6.has("y")) {
                                        r.setValue(new Vec2f(MathHelper.clamp(asJsonObject6.get("x").getAsFloat(), 0.0f, 1.0f), MathHelper.clamp(asJsonObject6.get("y").getAsFloat(), 0.0f, 1.0f)));
                                    }
                                    break Label_1088;
                                }
                                case 6: {
                                    final ModeSetting p = (ModeSetting)sub;
                                    this.loadModeSetting((Setting<?>)p, String.valueOf(asJsonObject3.get(p.getName()).getAsString()));
                                    break Label_1088;
                                }
                                case 7: {
                                    final StringSetting u_c2 = (StringSetting)sub;
                                    u_c2.setValue(asJsonObject3.get(u_c2.getName()).getAsString());
                                    break Label_1088;
                                }
                                case 8: {
                                    final ColorSetting c_c2 = (ColorSetting)sub;
                                    c_c2.setValue(new Color(asJsonObject3.get(c_c2.getName()).getAsInt()));
                                    break Label_1088;
                                }
                                default: {
                                    break Label_1088;
                                }
                            }
                        }
                    }
                    final JsonObject jsonObject5 = asJsonObject3;
                    final String[] c5 = Config.STRINGS;
                    if (!jsonObject5.has(c5[3])) {
                        continue;
                    }
                    try {
                        gq.setEnabled(asJsonObject3.get(c5[2]).getAsBoolean());
                    }
                    catch (final Exception ex2) {}
                }
            }
        }
        catch (final Exception ex3) {
            // A partially applied config is preferable to a crash on boot; log and carry on.
            Config.LOGGER.error("[Evon] Config '{}' could not be applied completely", this.F, ex3);
        }
    }
    
    private <T extends Enum<T>> void loadEnumSetting(final Setting<?> setting, final String obj) {
        final long n = Config.KEY ^ 0x220800C34221L;
        final EnumSetting i_c2 = (EnumSetting)setting;
        for (final Object enum1 : i_c2.getValues()) {
            if (((Enum)enum1).name().equals(String.valueOf(obj))) {
                i_c2.setValue((Object)enum1);
            }
        }
    }
    
    private void loadModeSetting(final Setting<?> setting, final String obj) {
        final long n = Config.KEY ^ 0x72AC1C7E4CFCL;
        final ModeSetting p2 = (ModeSetting)setting;
        for (final SubMode vj : p2.getSubModes()) {
            if (vj.getName().equals(String.valueOf(obj))) {
                p2.setSubMode(vj);
            }
        }
    }
    
    private void loadBindSetting(final Setting<?> setting, final String obj) {
        final long n = Config.KEY ^ 0x4ECDFCB082CDL;
        final BindSetting l = (BindSetting)setting;
        for (final BindMode zq_c2 : BindMode.values()) {
            if (zq_c2.name().equals(String.valueOf(obj))) {
                l.setBindMode(zq_c2);
            }
        }
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public String getName() {
        return this.F;
    }
    
    public File getFile() {
        return this.file;
    }
    
    static {
        KEY = KeyedRandom.create(2930774570581079650L, 8136882244849921743L, (Object)MethodHandles.lookup().lookupClass()).next(24517570845089L);
        STRINGS = new String[]{
            "version",
            "date",
            "toggled",
            "toggled",
            ".json",
            "mode",
            "Unknown",
            "data",
            "data",
            "level",
            "mY9qW2eT8zR4pL1oX7cV6nB3uH5dK0fJ",
            "Unknown",
            "mY9qW2eT8zR4pL1oX7cV6nB3uH5dK0fJ",
            "mode",
            "key",
            "author",
            "1.0",
            "modules",
            "modules",
            "M/d/yyyy",
            "/Evon/Configs/",
            "author",
            "date",
            "level",
            "version",
            "key"
        };
        DATE_FORMATTER = DateTimeFormatter.ofPattern(Config.STRINGS[19]);
    }
    
    private static Throwable a(final Throwable t) {
        return t;
    }
    
    private static String a_r2(final byte[] array) {
        int count = 0;
        final int length;
        final char[] value = new char[length = array.length];
        for (int i = 0; i < length; ++i) {
            final int n;
            if ((n = (0xFF & array[i])) < 192) {
                value[count++] = (char)n;
            }
            else if (n < 224) {
                value[count++] = (char)((char)((char)(n & 0x1F) << 6) | (char)(array[++i] & 0x3F));
            }
            else if (i < length - 2) {
                value[count++] = (char)((char)((char)((char)(n & 0xF) << 12) | (char)(array[++i] & 0x3F) << 6) | (char)(array[++i] & 0x3F));
            }
        }
        return new String(value, 0, count);
    }
    
    // This helper class was generated by Procyon to approximate the behavior of an
    // 'invokedynamic' instruction that it doesn't know how to interpret.
    private static final class ProcyonInvokeDynamicHelper_1
    {
        private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        private static MethodHandle handle;
        private static volatile int fence;
        
        private static MethodHandle handle() {
            final MethodHandle handle = ProcyonInvokeDynamicHelper_1.handle;
            if (handle != null)
                return handle;
            return ProcyonInvokeDynamicHelper_1.ensureHandle();
        }
        
        private static MethodHandle ensureHandle() {
            ProcyonInvokeDynamicHelper_1.fence = 0;
            MethodHandle handle = ProcyonInvokeDynamicHelper_1.handle;
            if (handle == null) {
                MethodHandles.Lookup lookup = ProcyonInvokeDynamicHelper_1.LOOKUP;
                try {
                    handle = ((CallSite)SwitchBootstraps.typeSwitch(lookup, "typeSwitch", MethodType.methodType(int.class, Object.class, int.class), BindSetting.class, BooleanSetting.class, BooleanGroupSetting.class, DoubleSetting.class, EnumSetting.class, PositionSetting.class, ModeSetting.class, StringSetting.class, ColorSetting.class)).dynamicInvoker();
                }
                catch (Throwable t) {
                    throw new UndeclaredThrowableException(t);
                }
                ProcyonInvokeDynamicHelper_1.fence = 1;
                ProcyonInvokeDynamicHelper_1.handle = handle;
                ProcyonInvokeDynamicHelper_1.fence = 0;
            }
            return handle;
        }
        
        private static int invoke(Object p0, int p1) {
            try {
                return (int)ProcyonInvokeDynamicHelper_1.handle().invokeExact(p0, p1);
            }
            catch (Throwable t) {
                throw new UndeclaredThrowableException(t);
            }
        }
    }
    
    // This helper class was generated by Procyon to approximate the behavior of an
    // 'invokedynamic' instruction that it doesn't know how to interpret.
    private static final class ProcyonInvokeDynamicHelper_2
    {
        private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        private static MethodHandle handle;
        private static volatile int fence;
        
        private static MethodHandle handle() {
            final MethodHandle handle = ProcyonInvokeDynamicHelper_2.handle;
            if (handle != null)
                return handle;
            return ProcyonInvokeDynamicHelper_2.ensureHandle();
        }
        
        private static MethodHandle ensureHandle() {
            ProcyonInvokeDynamicHelper_2.fence = 0;
            MethodHandle handle = ProcyonInvokeDynamicHelper_2.handle;
            if (handle == null) {
                MethodHandles.Lookup lookup = ProcyonInvokeDynamicHelper_2.LOOKUP;
                try {
                    handle = ((CallSite)SwitchBootstraps.typeSwitch(lookup, "typeSwitch", MethodType.methodType(int.class, Object.class, int.class), BindSetting.class, BooleanSetting.class, BooleanGroupSetting.class, DoubleSetting.class, EnumSetting.class, PositionSetting.class, ModeSetting.class, StringSetting.class, ColorSetting.class)).dynamicInvoker();
                }
                catch (Throwable t) {
                    throw new UndeclaredThrowableException(t);
                }
                ProcyonInvokeDynamicHelper_2.fence = 1;
                ProcyonInvokeDynamicHelper_2.handle = handle;
                ProcyonInvokeDynamicHelper_2.fence = 0;
            }
            return handle;
        }
        
        private static int invoke(Object p0, int p1) {
            try {
                return (int)ProcyonInvokeDynamicHelper_2.handle().invokeExact(p0, p1);
            }
            catch (Throwable t) {
                throw new UndeclaredThrowableException(t);
            }
        }
    }
}
