// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package ru.ifmo.se.pokemon;

public class StatusMove extends Move {
   public StatusMove() {
   }

   public StatusMove(Type var1, double var2, double var4) {
      super(var1, var2, var4);
   }

   public StatusMove(Type var1, double var2, double var4, int var6, int var7) {
      super(var1, var2, var4, var6, var7);
   }

   public final void attack(Pokemon var1, Pokemon var2) {
      for(int var3 = 0; var3 < this.hits; ++var3) {
         if (this.checkAccuracy(var1, var2)) {
            System.out.println(var1 + " " + this.describe() + ". ");
            if (this.type.getEffect(var2.getTypes()) > 0.0) {
               this.applyOppEffects(var2);
            } else {
               System.out.println(var2 + " " + Messages.get("noeffect") + " " + this.type);
            }

            if (this.type.getEffect(var1.getTypes()) > 0.0) {
               this.applySelfEffects(var1);
            }
         } else {
            System.out.println(var1 + " " + Messages.get("miss"));
         }
      }

   }

   protected void applySelfEffects(Pokemon var1) {
   }

   protected void applyOppEffects(Pokemon var1) {
   }
}
