// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package ru.ifmo.se.pokemon;

public final class Effect {
   private int[] mods = new int[Stat.values().length];
   private int turns = 0;
   private double effectChance = 1.0;
   private double attackChance = 1.0;
   private Status condition;

   public Effect() {
      this.condition = Status.NORMAL;
   }

   public final Effect turns(int var1) {
      this.turns = var1;
      return this;
   }

   public final Effect chance(double var1) {
      this.effectChance = var1;
      return this;
   }

   public final Effect attack(double var1) {
      this.attackChance = var1;
      return this;
   }

   public final double attack() {
      return this.attackChance;
   }

   public final Effect condition(Status var1) {
      this.condition = var1;
      return this;
   }

   public final void clear() {
      Stat[] var1 = Stat.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Stat var4 = var1[var3];
         this.mods[var4.ordinal()] = 0;
      }

      this.condition = Status.NORMAL;
      this.turns = 0;
      this.effectChance = 1.0;
      this.attackChance = 1.0;
   }

   public final Status condition() {
      return this.condition;
   }

   public final int stat(Stat var1) {
      return this.mods[var1.ordinal()];
   }

   public final Effect stat(Stat var1, int var2) {
      if (var1 != Stat.HP) {
         if (var2 >= 0 & var2 > 6) {
            var2 = 6;
         }

         if (var2 < 0 & var2 < -6) {
            var2 = -6;
         }
      }

      this.mods[var1.ordinal()] = var2;
      return this;
   }

   public final boolean success() {
      return this.effectChance > Math.random();
   }

   public final boolean immediate() {
      return this.turns == 0;
   }

   public final boolean turn() {
      return --this.turns == 0;
   }

   public static void burn(Pokemon var0) {
      if (!var0.hasType(Type.FIRE)) {
         Effect var1 = (new Effect()).condition(Status.BURN).turns(-1);
         var1.stat(Stat.ATTACK, -2).stat(Stat.HP, (int)var0.getStat(Stat.HP) / 16);
         var0.setCondition(var1);
      }

   }

   public static void paralyze(Pokemon var0) {
      if (!var0.hasType(Type.ELECTRIC)) {
         Effect var1 = (new Effect()).condition(Status.PARALYZE).attack(0.75).turns(-1);
         var1.stat(Stat.SPEED, -2);
         var0.setCondition(var1);
      }

   }

   public static void freeze(Pokemon var0) {
      if (!var0.hasType(Type.ICE)) {
         Effect var1 = (new Effect()).condition(Status.FREEZE).attack(0.0).turns(-1);
         var0.setCondition(var1);
      }

   }

   public static void poison(Pokemon var0) {
      if (!var0.hasType(Type.POISON) && !var0.hasType(Type.STEEL)) {
         Effect var1 = (new Effect()).condition(Status.POISON).turns(-1);
         var1.stat(Stat.HP, (int)var0.getStat(Stat.HP) / 8);
         var0.setCondition(var1);
      }

   }

   public static void sleep(Pokemon var0) {
      Effect var1 = (new Effect()).condition(Status.SLEEP).attack(0.0).turns((int)(Math.random() * 3.0 + 1.0));
      var0.setCondition(var1);
   }

   public static void flinch(Pokemon var0) {
      Effect var1 = (new Effect()).attack(0.0).turns((int)(Math.random() * 4.0 + 1.0));
      var0.addEffect(var1);
   }

   public static void confuse(Pokemon var0) {
      var0.confuse();
   }
}
