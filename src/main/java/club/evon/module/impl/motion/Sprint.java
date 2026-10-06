package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.ClientTickEvent;
import club.evon.event.KeepSprintEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;




public final class Sprint extends BaseModule {
   private final BooleanSetting omni;
   private final BooleanSetting keepSprint;
   private static final long STRING_KEY = KeyedRandom.create(1830136481877409819L, 8857679384266666165L, MethodHandles.lookup().lookupClass()).next(183689110017772L);
   private static final String[] STRINGS;

   public Sprint() {
      super((STRINGS)[2], (STRINGS)[3], ModuleCategory.MOTION);

      String[] strings = STRINGS;
      this.omni = new BooleanSetting(strings[1], false);
      // Default OFF. With this on, Sprint cancels KeepSprintEvent in onKeepSprint(), which makes
      // PlayerEntityMixin.hookKeepSprintState() return isSprinting() instead of vanilla's
      // reset - so the client kept the sprint flag set while attacking. That is exactly what
      // anticheats flag as "sprinting while hitting", and it is why KillAura looked wrong
      // while Scaffold (which does not attack) looked fine.
      this.keepSprint = new BooleanSetting(strings[0], false);
      this.addSettings(this.omni, this.keepSprint);
      this.setEnabled(true);
      }

   @Subscribe
   private void onKeepSprint(KeepSprintEvent event) {
      long longValue = STRING_KEY ^ 46030087225794L;
      BaseModule[] values = Scaffold.z();
      if (values == null) {
         if (!this.keepSprint.I()) {
            return;
         }

         event.cancel();
      }
   }

   @Subscribe
   private void onTick(ClientTickEvent event) {
      ClientContext.MINECRAFT_CLIENT.options.sprintKey.setPressed(true);
   }

   public boolean isKeepSprint() {
      return this.keepSprint.I();
   }

   public boolean isOmniActive() {
      long longValue = STRING_KEY ^ 9931759043469L;
      BaseModule[] values = Scaffold.z();
      boolean enabled = this.isEnabled();
      if (values == null) {
         if (!enabled) {
            return false;
         }

         enabled = this.omni.I();
      }

      if (values == null) {
         if (!enabled) {
            return false;
         }

         enabled = MovementUtil.isMoving();
      }

      return values != null ? enabled : enabled;
   }

   static {
      try {
      long var0 = STRING_KEY ^ 82173440367094L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[4];
      int var7 = 0;
      String var6 = "\u001b\u0086\u0088\u00b9VD\u0090*x&\u00f0Tg!\u00c6f\b:p\u00dd\u001d*\u00c4l\u00b5";
      int var8 = "\u001b\u0086\u0088\u00b9VD\u0090*x&\u00f0Tg!\u00c6f\b:p\u00dd\u001d*\u00c4l\u00b5".length();
      char var5 = 16;
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

                  var6 = "\u0014u/\u00954\u00e9D\u00d1\u0018\u00c8\u0001~?k\u009fW\u00a0\u00f8\u00fe\t\u00e8\u001f\u00f4\u00d4\u0012\u001a0?\u00e6,u\u008dk";
                  var8 = "\u0014u/\u00954\u00e9D\u00d1\u0018\u00c8\u0001~?k\u009fW\u00a0\u00f8\u00fe\t\u00e8\u001f\u00f4\u00d4\u0012\u001a0?\u00e6,u\u008dk"
                     .length();
                  var5 = '\b';
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

   private static JSONException a(JSONException jSONException) {
      return jSONException;
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
