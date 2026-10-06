/*
 * Decompiled with CFR 0.152.
 */
package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.security.Key;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import club.evon.EvonClient;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.util.Animator;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.math.RandomUtil;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgFont;
import club.evon.util.render.RenderUtil;




public final class CategoryPanel
extends AbstractGuiComponent {
    private final ModuleCategory B;
    private final List<GuiComponent> modulePanels;
    private static final Color BACKGROUND_COLOR;
    private static final Color HEADER_COLOR;
    private static final float CORNER_RADIUS = 4.5f;
    private static final NvgFont headerFont;
    private static final NvgFont iconFont;
    private final String icon;
    private final float iconWidth;
    private float scrollOffset;
    private float scrollTarget;
    private static final float CONTENT_PADDING = 4.0f;
    private final Animator openAnimator;
    private static final long KEY;

    public CategoryPanel(ModuleCategory category) {
        long l = KEY ^ 0x5057C1F7D496L;
        String string = EvonClient.E();
        this.modulePanels = new ArrayList<GuiComponent>();
        this.openAnimator = new Animator(100, 0.0, Easing.LINEAR);
        this.B = category;
        String string2 = string;
        this.icon = switch (category) {
            default -> throw new MatchException(null, null);
            case ModuleCategory.COMBAT -> "A";
            case ModuleCategory.MOTION -> "B";
            case ModuleCategory.PLAYER -> "C";
            case ModuleCategory.RENDER -> "E";
            case ModuleCategory.MISC -> "D";
        };
        this.iconWidth = iconFont.getTextWidth(this.icon, 21.5f);
        for (BaseModule gQ2 : EvonClient.getInstance().getModuleManager().getModulesByCategory(category).stream().sorted(Comparator.comparing(BaseModule::getName)).toList()) {
            this.modulePanels.add(new ModuleSettingsPanel(gQ2, this));
            if (string2 != null) continue;
            BaseModule.setStateArray(new BaseModule[5]);
            break;
        }
    }

    @Override
    public void init() {
        block3: {
            block2: {
                long l = KEY ^ 0x509852588252L;
                this.openAnimator.setTargetValue(0.0);
                String string = EvonClient.E();
                this.openAnimator.finish();
                String string2 = string;
                for (GuiComponent zW2 : this.modulePanels) {
                    zW2.init();
                    if (string2 != null) {
                        if (string2 != null) continue;
                    }
                    break block2;
                }
                this.openAnimator.setDuration(150);
            }
            if (BaseModule.getStateArray() != null) break block3;
            EvonClient.f("qsRT5b");
        }
    }

    @Override
    public void onOpen() {
        this.openAnimator.setTargetValue(1.0);
    }

    @Override
    public void onClose() {
        this.openAnimator.setTargetValue(0.0);
    }

    @Override
    public boolean isClosed() {
        long l = KEY ^ 0x2C1BF1F26E66L;
        String string = EvonClient.E();
        if (string == null) {
            return this.openAnimator.getTargetValue() != 0.0;
        }
        if (this.openAnimator.getTargetValue() != 0.0) {
            return false;
        }
        return this.openAnimator.getValue() == 0.0;
    }

    @Override
    public void renderBackground(int mouseX, int mouseY) {
        long l = KEY ^ 0x3C364CF3CAA5L;
        String string = EvonClient.E();
        CategoryPanel _F2 = this;
        if (string != null) {
            if (_F2.openAnimator.getValue() == 0.0) {
                return;
            }
            _F2 = this;
        }
        float f = _F2.getX();
        float f2 = this.getY();
        float f3 = this.getWidth();
        float f4 = this.getHeight();
        RenderUtil.fillRoundedRectWithBlur(f, f2, f3, f4, 4.5f, Color.BLACK);
    }

    public void render(int mouseX, int mouseY) {
        block7: {
            long l = KEY ^ 0x1B63B610F863L;
            String string = EvonClient.E();
            CategoryPanel _F2 = this;
            if (string != null) {
                if (_F2.openAnimator.getValue() == 0.0) {
                    return;
                }
                _F2 = this;
            }
            float f = _F2.getX();
            float f2 = this.getY();
            float f3 = this.getWidth();
            float f4 = this.getHeight();
            RenderUtil.saveState();
            float f5 = 1.1f;
            float f6 = 0.9090909f;
            RenderUtil.scale(1.1f, 1.1f);
            RenderUtil.translate(-30.0f, -30.0f);
            RenderUtil.fillRoundedRectPaint(f * 0.9090909f + 30.0f, f2 * 0.9090909f + 30.0f, f3 * 0.9090909f, f4 * 0.9090909f, 4.090909f, RenderUtil.gameBlurPaint);
            RenderUtil.restoreState();
            float f7 = Math.min(super.getHeight(), f4);
            RenderUtil.pushClip(f, f2, f3, f7);
            RenderUtil.fillRoundedRect(f, f2, f3, f3 + 4.5f, 4.5f, HEADER_COLOR);
            headerFont.drawText(this.B.toString(), f + 4.5f, f2 + 7.0f, 9.0f, -1);
            iconFont.drawText(this.icon, f + (f3 - (this.iconWidth + 4.5f)), f2 - 4.5f, 21.5f, -1);
            RenderUtil.popClip();
            RenderUtil.pushClip(f, f2 + f7, f3, f4 - f7);
            RenderUtil.fillRoundedRect(f, f2, f3, f4, 4.5f, BACKGROUND_COLOR);
            float f8 = this.getContentHeight();
            this.scrollTarget = Math.clamp(this.scrollTarget, 0.0f, Math.max(f8, f4) - f4);
            this.scrollOffset = Math.clamp(RandomUtil.lerp(this.scrollOffset, this.scrollTarget, 0.1f), 0.0f, Math.max(f8, f4) - f4);
            float f9 = f2 + f7 - this.scrollOffset;
            for (GuiComponent zW2 : this.modulePanels) {
                float f10;
                float f11;
                block8: {
                    block9: {
                        zW2.setX(f + 4.0f);
                        zW2.setY(f9 + 4.0f);
                        zW2.setWidth(f3 - 8.0f);
                        zW2.setHeight(f7);
                        if (string == null) break block7;
                        f11 = zW2.getY() - f2;
                        f10 = f4;
                        if (string == null) break block8;
                        if (!(f11 < f10)) break block9;
                        f11 = zW2.getY() + zW2.getHeight();
                        f10 = f9;
                        if (string == null) break block8;
                        if (f11 >= f10) {
                            zW2.render(mouseX, mouseY);
                        }
                    }
                    f11 = f9;
                    f10 = zW2.getHeight() + 4.0f;
                }
                f9 = f11 + f10;
                if (string != null) continue;
            }
            RenderUtil.popClip();
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        long l = KEY ^ 0x16DA165F8E20L;
        Iterator<GuiComponent> iterator = this.modulePanels.iterator();
        String string = EvonClient.E();
        while (iterator.hasNext()) {
            GuiComponent zW2;
            GuiComponent zW3 = zW2 = iterator.next();
            double d = mouseX;
            double d2 = mouseY;
            if (string != null) {
                if (!zW3.isHovered(d, d2) && string != null) continue;
                zW3 = zW2;
                d = mouseX;
                d2 = mouseY;
            }
            zW3.mouseClicked(d, d2, button);
            if (string != null) continue;
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        long l = KEY ^ 0x6B4E3CEA723L;
        Iterator<GuiComponent> iterator = this.modulePanels.iterator();
        String string = EvonClient.E();
        while (iterator.hasNext()) {
            GuiComponent zW2 = iterator.next();
            zW2.mouseReleased(mouseX, mouseY, button);
            if (string != null) continue;
        }
    }

    @Override
    public void mouseScrolled(float scrollAmount) {
        this.scrollTarget -= scrollAmount * 30.0f;
    }

    @Override
    public void keyPressed(int keyCode) {
        long l = KEY ^ 0x2B7B6F83C86BL;
        Iterator<GuiComponent> iterator = this.modulePanels.iterator();
        String string = EvonClient.E();
        while (iterator.hasNext()) {
            GuiComponent zW2 = iterator.next();
            zW2.keyPressed(keyCode);
            if (string != null) continue;
        }
    }

    @Override
    public float getHeight() {
        return Math.clamp(this.getContentHeight(), 0.0f, 325.0f) * this.openAnimator.getValue().floatValue();
    }

    public float getBaseHeight() {
        return super.getHeight();
    }

    private float getContentHeight() {
        float f;
        block2: {
            long l = KEY ^ 0x6B0CA89F66DBL;
            float f2 = super.getHeight();
            Iterator<GuiComponent> iterator = this.modulePanels.iterator();
            String string = EvonClient.E();
            while (iterator.hasNext()) {
                GuiComponent zW2 = iterator.next();
                f = f2 + (zW2.getHeight() + 4.0f);
                if (string != null) {
                    f2 = f;
                    if (string != null) continue;
                }
                break block2;
            }
            f = f2 + 4.0f;
        }
        return f;
    }

    /*
     * Enabled aggressive block sorting
     */
    static {
      try {
        KEY = KeyedRandom.create(1508340029593788877L, 8509198001381598369L, MethodHandles.lookup().lookupClass()).next(147116517028452L);
        long l = KEY ^ 0x1208BE0B735L;
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        byte[] byArray = new byte[8];
        byte[] byArray2 = byArray;
        byArray[0] = (byte)(l >>> 56);
        for (int i = 1; i < 8; ++i) {
            byArray2 = byArray2;
            byArray2[i] = (byte)(l << i * 8 >>> 56);
        }
        cipher.init(2, (Key)secretKeyFactory.generateSecret(new DESKeySpec(byArray2)), new IvParameterSpec(new byte[8]));
        String[] stringArray = new String[2];
        int n = 0;
        String string = "\u00e6\u0000H\u009f\u00e2.\"iT\u00af0\u00ecoH\u008cf\b\u00a3C\u00e9>x2\u00fe\u00f4";
        int n2 = "\u00e6\u0000H\u009f\u00e2.\"iT\u00af0\u00ecoH\u008cf\b\u00a3C\u00e9>x2\u00fe\u00f4".length();
        int n3 = 16;
        int n4 = -1;
        while (true) {
            int n5 = ++n4;
            byte[] byArray3 = cipher.doFinal(string.substring(n5, n5 + n3).getBytes("ISO-8859-1"));
            stringArray[n++] = CategoryPanel.decodeUtf8(byArray3).intern();
            if ((n4 += n3) >= n2) {
                break;
            }
            n3 = string.charAt(n4);
        }
        BACKGROUND_COLOR = ColorUtil.withAlpha(Theme.BACKGROUND, 0.4f);
        HEADER_COLOR = ColorUtil.withAlpha(Theme.BACKGROUND, 0.7f);
        headerFont = Theme.FONT_MANAGER.getFont(stringArray[0]);
        iconFont = Theme.FONT_MANAGER.getFont(stringArray[1]);
    
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}

    private static MatchException a(MatchException matchException) {
        return matchException;
    }

    private static String decodeUtf8(byte[] data) {
        int n = 0;
        int n2 = data.length;
        char[] cArray = new char[n2];
        for (int i = 0; i < n2; ++i) {
            char c;
            int n3 = 0xFF & data[i];
            if (n3 < 192) {
                cArray[n++] = (char)n3;
                continue;
            }
            if (n3 < 224) {
                c = (char)((char)(n3 & 0x1F) << 6);
                n3 = data[++i];
                c = (char)(c | (char)(n3 & 0x3F));
                cArray[n++] = c;
                continue;
            }
            if (i >= n2 - 2) continue;
            c = (char)((char)(n3 & 0xF) << 12);
            n3 = data[++i];
            c = (char)(c | (char)(n3 & 0x3F) << 6);
            n3 = data[++i];
            c = (char)(c | (char)(n3 & 0x3F));
            cArray[n++] = c;
        }
        return new String(cArray, 0, n);
    }


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
