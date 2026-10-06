package club.evon.data;


public class Triple<A, B, C> {
   private A I;
   private B P;
   private C M;

   public Triple(A first, B second, C third) {
      this.I = (A)first;
      this.P = (B)second;
      this.M = (C)third;
   }

   public A getFirst() {
      return this.I;
   }

   public B getSecond() {
      return this.P;
   }

   public C getThird() {
      return this.M;
   }

   public void setFirst(A first) {
      this.I = (A)first;
   }

   public void setSecond(B second) {
      this.P = (B)second;
   }

   public void setThird(C third) {
      this.M = (C)third;
   }
}
