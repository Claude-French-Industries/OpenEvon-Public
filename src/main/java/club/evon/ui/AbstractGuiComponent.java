package club.evon.ui;
import club.evon.util.HoverUtil;



public abstract class AbstractGuiComponent implements GuiComponent {
   private float y;
   private float K;
   private float N;
   private float U;
   private static String[] strings;

   @Override
   public void renderBackground(int mouseX, int mouseY) {
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
   }

   @Override
   public void mouseScrolled(float scrollAmount) {
   }

   @Override
   public void keyPressed(int keyCode) {
   }

   @Override
   public void init() {
   }

   @Override
   public void onOpen() {
   }

   @Override
   public void onClose() {
   }

   @Override
   public boolean isClosed() {
      return false;
   }

   @Override
   public boolean isVisible() {
      return true;
   }

   @Override
   public boolean isHovered(double mouseX, double mouseY) {
      return HoverUtil.isHovered(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight());
   }

   @Override
   public float getX() {
      return this.y;
   }

   @Override
   public float getY() {
      return this.K;
   }

   @Override
   public float getWidth() {
      return this.N;
   }

   @Override
   public float getHeight() {
      return this.U;
   }

   @Override
   public void setX(float x) {
      this.y = x;
   }

   @Override
   public void setY(float y) {
      this.K = y;
   }

   @Override
   public void setWidth(float width) {
      this.N = width;
   }

   @Override
   public void setHeight(float height) {
      this.U = height;
   }

   public static void setStrings(String[] strings2) {
      strings = strings2;
   }

   public static String[] getStrings() {
      return strings;
   }

   static {
      if (getStrings() != null) {
         setStrings(new String[1]);
      }
   }
}
