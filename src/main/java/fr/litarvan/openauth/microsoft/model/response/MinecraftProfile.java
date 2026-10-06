package fr.litarvan.openauth.microsoft.model.response;

public class MinecraftProfile {
   private final String id;
   private final String name;
   private final MinecraftProfile$MinecraftSkin[] skins;

   public MinecraftProfile(String id, String name, MinecraftProfile$MinecraftSkin[] skins) {
      this.id = id;
      this.name = name;
      this.skins = skins;
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public MinecraftProfile$MinecraftSkin[] getSkins() {
      return this.skins;
   }
}
