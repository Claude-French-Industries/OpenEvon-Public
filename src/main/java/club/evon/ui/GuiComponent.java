package club.evon.ui;


public interface GuiComponent {
   public void renderBackground(int mouseX, int mouseY);

   public void render(int mouseX, int mouseY);

   public void mouseClicked(double mouseX, double mouseY, int button);

   public void mouseReleased(double mouseX, double mouseY, int button);

   public void mouseScrolled(float scrollAmount);

   public void keyPressed(int keyCode);

   public void init();

   public void onOpen();

   public void onClose();

   public boolean isClosed();

   public boolean isVisible();

   public float getX();

   public void setX(float x);

   public float getY();

   public void setY(float y);

   public float getWidth();

   public void setWidth(float width);

   public float getHeight();

   public void setHeight(float height);

   public boolean isHovered(double mouseX, double mouseY);
}
