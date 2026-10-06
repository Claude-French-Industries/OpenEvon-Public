package club.evon.data;


public enum BlockAnimationMode {
   V1_7("1.7"),
   V1_8("1.8"),
   DOWNWARDS("Downwards"),
   SWANG("Swang"),
   PUSH("Push"),
   DIVE("Dive");

   private final String displayName;
   private static final BlockAnimationMode[] H;

   BlockAnimationMode(String text) {
      this.displayName = text;
   }

   @Override
   public String toString() {
      return this.displayName;
   }

   private static BlockAnimationMode[] n() {
      return new BlockAnimationMode[]{V1_7, V1_8, DOWNWARDS, SWANG, PUSH, DIVE};
   }

   static {
      H = n();
   }
}
