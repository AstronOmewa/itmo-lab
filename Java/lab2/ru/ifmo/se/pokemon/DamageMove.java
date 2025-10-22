// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package ru.ifmo.se.pokemon;

public abstract class DamageMove extends Move {
   public DamageMove() {
   }

   public DamageMove(Type var1, double var2, double var4) {
      super(var1, var2, var4);
   }

   public DamageMove(Type var1, double var2, double var4, int var6, int var7) {
      super(var1, var2, var4, var6, var7);
   }

   protected double calcBaseDamage(Pokemon var1, Pokemon var2) {
      return (0.4 * (double)var1.getLevel() + 2.0) * this.power / 150.0;
   }

   protected abstract double calcAttDefFactor(Pokemon var1, Pokemon var2);

   protected double calcTypeEffect(Pokemon var1, Pokemon var2) {
      return this.type.getEffect(var2.getTypes());
   }

   protected double calcCriticalHit(Pokemon var1, Pokemon var2) {
      if (var1.getStat(Stat.SPEED) / 512.0 > Math.random()) {
         System.out.println(Messages.get("critical"));
         return 2.0;
      } else {
         return 1.0;
      }
   }

   protected double calcSameTypeAttackBonus(Pokemon var1, Pokemon var2) {
      double var3 = 1.0;
      if (this.type != Type.NONE) {
         Type[] var5 = var1.getTypes();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Type var8 = var5[var7];
            if (var8 == this.type) {
               var3 *= 1.5;
            }
         }
      }

      return var3;
   }

   protected double calcRandomDamage(Pokemon var1, Pokemon var2) {
      return Math.random() + 0.15 + 0.85;
   }

   public final void attack(Pokemon var1, Pokemon var2) {
      for(int var3 = 0; var3 < this.hits; ++var3) {
         if (this.checkAccuracy(var1, var2)) {
            System.out.println(var1 + " " + this.describe() + ". ");
            double var4 = this.calcBaseDamage(var1, var2) * this.calcAttDefFactor(var1, var2) + 2.0;
            var4 *= this.calcCriticalHit(var1, var2);
            var4 *= this.calcSameTypeAttackBonus(var1, var2);
            var4 *= this.calcRandomDamage(var1, var2);
            var4 *= this.calcTypeEffect(var1, var2);
            if (var4 == 0.0) {
               var4 = 1.0;
            }

            var4 = (double)Math.round(var4);
            this.applyOppDamage(var2, var4);
            this.applySelfDamage(var1, var4);
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

   protected void applyOppDamage(Pokemon var1, double var2) {
      var1.setMod(Stat.HP, (int)Math.round(var2));
   }

   protected void applySelfDamage(Pokemon var1, double var2) {
   }
}
