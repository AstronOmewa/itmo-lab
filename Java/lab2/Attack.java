import ru.ifmo.se.pokemon.*;

import java.util.*;


/**
 * Класс Attack - универсальный класс-оболочка для удобного хранения всех атак для всех покемонов. Не наследуется.
 * @author Влад
 * @see ru.ifmo.se.pokemon.Move
 */

public abstract class Attack{

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
         * @param p покемон, к которому применяется эффект
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            p.setMod(Stat.ATTACK, 2);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод describe. Выводит сообщение об использовании атаки.
         * @return строка с описанием атаки
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
         * @param p покемон-оппонент, к которому применяется эффект
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
         * @return строка с описанием атаки
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
         * @param p покемон, к которому применяется эффект
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            p.restore();
        }
         /**
         * Переопределенный наследованный от суперкласса SpecialMove метод describe. Выводит сообщение об использовании атаки.
         * @return строка с описанием атаки
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
         * @param p покемон, к которому применяется эффект
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
         * @return строка с описанием атаки
         */
        @Override
        public String describe(){
            return "применил Rest";
        }
    }

    // Spinarak's moves
    /**
     * Атака Absorb типа GRASS: Восстанавливает Stat.HP (здоровье) на 3 ступени.
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
         * @param p покемон, к которому применяется эффект
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            p.setMod(Stat.HP, 3);
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод describe. Выводит сообщение об использовании атаки.
         * @return строка с описанием атаки
         */
        @Override 
        public String describe(){
            return "применил Absorb (восстановлено 50% здоровья, нанесен урон)";
        }
    }

    /**
     * Атака PinMissile типа BUG: Увеличивает атаку. Наносит от 2 до 5 ударов.
     */

    public static final class PinMissile extends PhysicalMove{
        /**
         * Конструктор класса PinMissile без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.PhysicalMove. Устанавливает тип атаки BUG, 25 pow, 95 acc, 1 priority и наносит от 2 до 5 ударов. 
         */
        public PinMissile(){
            super(Type.BUG, 25, 95, 1, (int)(Math.ceil((Math.random()*3.0))+2));
            
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод applySelfEffects. 
         * Увеличивает Stat.ATTACK (атаку) на 1-4 ступени.
         * @param p покемон, к которому применяется эффект
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
         * @return строка с описанием атаки
         */
        @Override 
        public String describe(){
            return "применил Pin Missile (нанесен урон + увеличен наносимый урон)";
        }
    }
    /**
     * Атака XScissor типа BUG. Наносит урон.
     */
    public static final class XScissor extends PhysicalMove{
        /**
         * Конструктор класса XScissor без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.PhysicalMove. Устанавливает тип атаки BUG, 80 pow и 100 acc. 
         */
        public XScissor(){
            super(Type.BUG, 80, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса PhysicalMove метод describe. Выводит сообщение об использовании атаки.
         * @return строка с описанием атаки
         */
        @Override 
        public String describe(){
            return "применил X-Scissor (нанесен урон)";
        }
    }

    // Ariados is inherited from Spinarak. This attack is unique for this pokemon, other attacks is inherited from Spinarak.

    /**
     * Атака SwordsDance типа NORMAL: Увеличивает значение поля Stat.ATTACK (атаки) на 2 ступени.
     */
    public static final class SwordsDance extends StatusMove{
        /**
         * Конструктор класса SwordsDance без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.StatusMove. Устанавливает тип атаки NORMAL, 0 pow и 100 acc. 
         */
        public SwordsDance(){
            super(Type.NORMAL, 0, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод applySelfEffects. Увеличивает Stat.ATTACK (атаку) на 2 ступени.
         * @param p покемон, к которому применяется эффект
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            p.setMod(Stat.ATTACK, 2);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод describe. Выводит сообщение об использовании атаки.
         * @return строка с описанием атаки
         */
        @Override 
        public String describe(){
            return "применил Swords Dance (увеличена атака)";
        }
    }

    // Bounsweet's attacks
    /**
     * Атака Teeter Dance типа NORMAL. Наследована от суперкласса re.ifmo.se.StatusMove. Вводит ближайших покемонов в состояние растерянности.
     */
    public static final class TeeterDance extends StatusMove{
        /**
         * Конструктор класса TeeterDance без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.StatusMove. Устанавливает тип атаки NORMAL, 0 pow и 100 acc. 
         */
        public TeeterDance(){
            super(Type.NORMAL, 0, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод applyOppEffects. Вводит покемона в состояние растерянности.
         * @param p покемон-оппонент, к которому применяется эффект
         */
        @Override
        protected void applyOppEffects(Pokemon p){
            Effect e = new Effect();
            e.confuse(p);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод describe. Выводит сообщение об использовании атаки.
         * @return строка с описанием атаки
         */
        @Override 
        public String describe(){
            return "применил Teeter Dance (все покемоны поблизости растеряны)";
        }
    }

    /**
     * Атака DoubleTeam типа NORMAL: Увеличивает значение поля Stat.EVASION (уклонения) на 1 ступень.
     */
    public static final class DoubleTeam extends StatusMove{
        /**
         * Конструктор класса DoubleTeam без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.StatusMove. Устанавливает тип атаки NORMAL, 0 pow и 100 acc. 
         */
        public DoubleTeam(){
            super(Type.NORMAL, 0, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод applySelfEffects. Увеличивает Stat.EVASION (уклонение) на 1 ступень.
         * @param p покемон, к которому применяется эффект
         */
        @Override
        protected void applySelfEffects(Pokemon p){
            p.setMod(Stat.EVASION, 1);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод describe. Выводит сообщение об использовании атаки.
         * @return строка с описанием атаки
         */
        @Override 
        public String describe(){
            return "применил Double Team (увеличивает маневренность)";
        }
    }

    // Steenee is inherited from Bounsweet

    /**
     * Атака PlayNice типа NORMAL: Уменьшает значение поля Stat.ATTACK (атаки) оппонента на 1 ступень.
     */
    public static final class PlayNice extends StatusMove{
        /**
         * Конструктор класса PlayNice без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.StatusMove. Устанавливает тип атаки NORMAL, 0 pow и 100 acc. 
         */
        public PlayNice(){
            super(Type.NORMAL, 0, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод applyOppEffects. Уменьшает Stat.ATTACK (атаку) оппонента на 1 ступень.
         * @param p покемон-оппонент, к которому применяется эффект
         */
        @Override
        protected void applyOppEffects(Pokemon p){
            p.setMod(Stat.ATTACK, -1);
        }
        /**
         * Переопределенный наследованный от суперкласса StatusMove метод describe. Выводит сообщение об использовании атаки.
         * @return строка с описанием атаки
         */
        @Override
        public String describe(){
            return "применил Play Nice (уменьшает атаку оппонента)";
        }
    }
    
    // Tsareena is inherited ....

    /**
     * Атака DazzlingGleam типа FAIRY: Наносит урон без дополнительных эффектов.
     */
    public static final class DazzlingGleam extends SpecialMove{
        /**
         * Конструктор класса DazzlingGleam без параметров. Наследован от суперкласса ru.ifmo.se.pokemon.SpecialMove. Устанавливает тип атаки FAIRY, 80 pow и 100 acc. 
         */
        public DazzlingGleam(){
            super(Type.FAIRY, 80, 100);
        }
        /**
         * Переопределенный наследованный от суперкласса SpecialMove метод describe. Выводит сообщение об использовании атаки.
         * @return строка с описанием атаки
         */
        @Override
        public String describe(){
            return "применил Dazzling Gleam (наносит урон)";
        }
    }
        
}