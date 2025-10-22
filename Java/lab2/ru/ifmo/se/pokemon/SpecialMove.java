// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package ru.ifmo.se.pokemon;

public class SpecialMove extends DamageMove {
   public SpecialMove() {
   }

   public SpecialMove(Type var1, double var2, double var4) {
      super(var1, var2, var4);
   }

   public SpecialMove(Type var1, double var2, double var4, int var6, int var7) {
      super(var1, var2, var4, var6, var7);
   }

   public final double calcAttDefFactor(Pokemon var1, Pokemon var2) {
      return var1.getStat(Stat.SPECIAL_ATTACK) / var2.getStat(Stat.SPECIAL_DEFENSE);
   }
}
