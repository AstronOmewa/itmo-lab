package attacks;
import ru.ifmo.se.pokemon.*;
import java.util.*;


/**
 * Класс Attack - универсальный класс-оболочка для удобного хранения всех атак для всех покемонов. Не наследуется.
 * @author Влад
 * @see ru.ifmo.se.pokemon.Move
 */

public final class Attack{

    // Cryogonal's moves
    /**** 
     * Атака Sharpen типа NORMAL: Увеличивает значение поля Stat.ATTACK (атаки) на 2 ступени. 
     */
    public static final class Sharpen extends StatusMove{
        /**
         * Конструктор класса Sharpen без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.StatusMove. Устанавливает тип атаки NORMAL, 0 pow и 100 acc. 
         */
        public Sharpen(){
            super(Type.NORMAL, 0, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод applySelfEffects. Увеличивает Stat.ATTACK (атаку) на 2 ступени.
         * 
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            p.setMod(Stat.ATTACK, 2);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод describe. Выводит сообщение об использовании атаки.
         * 
         */
        @Override
        protected String describe(){
            return "применил Sharpen (увеличил атаку)";
        }
    }

    /****
     * Атака Flash Cannon типа STEEL: С вероятностью 0.1 уменьшает Stat.SPECIAL_DEFENCE (специальную защиту) оппонента на 1 ступень.
     */
    public static final class FlashCannon extends SpecialMove{
        // Поле succeed предназначено для различных выводов при успешности/ не успешности атаки
        private boolean succeed = false;
        /**
         * Конструктор класса FlashCannon без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.StatusMove. Устанавливает тип атаки STEEL, 80 pow и 100 acc. 
         */
        public FlashCannon(){
            super(Type.STEEL, 80, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод applyOppEffects. Уменьшает Stat.SPECIAL_DEFENCE (специальную защиту) на 1 ступень, применяя эффект из класса Effect.
         * 
         */
        @Override 
        public void applyOppEffects(Pokemon p){
            Effect e = new Effect().chance(0.1).stat(Stat.SPECIAL_DEFENSE, -1);
            p.addEffect(e);
            if(e.success()){
                this.succeed = true;
            }
        }
         /**
         * Переопределенный наследованный от суперкласса SpecialMove метод describe. Выводит сообщение об использовании атаки.
         * 
         */
        @Override
        protected String describe(){

            return succeed?"применил Flash Cannon (промах)":"применил Flash Cannon (нанесен урон)";
        }
    }

    /**
     * Атака Recover типа NORMAL: Восстанавливает Stat.HP (здоровье) полностью.
     */

    public static final class Recover extends StatusMove{
        /**
         * Конструктор класса Recover без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.StatusMove. Устанавливает тип атаки NORMAL, 0 pow и 100 acc. 
         */
        public Recover(){
            super(Type.NORMAL, 0, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод applySelfEffects. Восстанавливает здоровье полностью.
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            p.restore(e);
        }
         /**
         * Переопределенный наследованный от суперкласса SpecialMove метод describe. Выводит сообщение об использовании атаки.
         * 
         */
        @Override
        public String describe(){
            return "применил Recover (восполнил HP)";
        }
    }

    /**
     * Атака Rest типа PSYCHIC: Восстанавливает Stat.HP (здоровье) полностью, покемон спит 2 хода.
     */
    public static final class Rest extends StatusMove{
        /**
         * Конструктор класса Rest без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.StatusMove. Устанавливает тип атаки PSYCHIC, 0 pow и 100 acc. 
         */
        public Rest(){
            super(Type.PSYCHIC, 0, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод applySelfEffects. Восстанавливает здоровье полностью, заставляет спать на 2 хода.
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            Effect rest = new Effect().turns(2);
            rest.sleep(p);
            p.restore();
            p.addEffect(rest);
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод describe. Выводит сообщение об использовании атаки.
         * 
         */
        @Override
        public String describe(){
            return "применил Rest";
        }
    }

    // Spinarak's moves
    /**
     * Атака Rest типа GRASS: Восстанавливает Stat.HP (здоровье) на 3 ступени.
     */
    public static final class Absorb extends SpecialMove{
        /**
         * Конструктор класса Absorb без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.SpecialMove. Устанавливает тип атаки GRASS, 20 pow и 100 acc. 
         */
        public Absorb() {
            super(Type.GRASS, 20, 100);

        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод applySelfEffects. Восстанавливает здоровье на 3 ступени.
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            Effect e = new Effect().stat(Stat.HP, -3);
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод describe. Выводит сообщение об использовании атаки.
         * 
         */
        @Override 
        public String describe(){
            return "применил Absorb (восстановлено 50% здоровья, нанесен урон)";
        }
    }

    /**
     * Атака типа BUG: Увеличивает атаку.
     */

    public static final class PinMissile extends PhysicalMove{
        /**
         * Конструктор класса PinMissile без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.PhysicalMove. Устанавливает тип атаки BUG, 25 pow, 95 acc, 1 priority и наносит от 2 до 5 ударов. 
         */
        public PinMissile(){
            super(Type.BUG, 25, 95, 1, Math.ceil((Math.random()*3.0))+2);
            
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод applySelfEffects. 
         * Увеличивает Stat.ATTACK (атаку) на 1-4 ступени.
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            Random generator = new Random();
            Effect e = new Effect();
            double prob = generator.nextDouble(0,1);
            if(prob < 0.375){
                e.stat(Stat.ATTACK, 1);
            }
            else if(generator.nextDouble()<0.75){
                e.stat(Stat.ATTACK, 2);
            }
            else if(prob<(0.75+0.125)){
                e.stat(Stat.ATTACK, 3);
            }
            else{
                e.stat(Stat.ATTACK, 4);
            }
            p.addEffect(e);
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод describe. Выводит сообщение об использовании атаки.
         * 
         */
        @Override 
        public String describe(){
            return "применил Pin Missile (нанесен урон + увеличен наносимый урон)";
        }
    }
    /**
     * Атака Rest типа BUG. Наносит урон.
     */
    public static final class XScissor extends PhysicalMove{
        /**
         * Конструктор класса XScissor без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.PhysicalMove. Устанавливает тип атаки BUG, 25 pow, 95 acc, 1 priority и наносит от 2 до 5 ударов. 
         */
        public XScissor(){
            super(Type.BUG, 80, 100);
        }
        @Override 
        public String describe(){
            return "применил X-Scissor (нанесен урон)";
        }
    }

    // Ariados is inherited from Spinarak. This attack is unique for this pokemon, other attacks is inherited from Spinarak.

    public static final class SwordsDance extends SpecialMove{
        public SwordsDance(){
            super(Type.NORMAL, 0, 100);
        }

        protected void addSelfEffect(Pokemon p){
            p.addEffect(
                new Effect().stat(Stat.ATTACK, 2)
            );
        }
    } 

    // 
}