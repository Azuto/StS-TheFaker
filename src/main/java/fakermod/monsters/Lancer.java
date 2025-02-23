package fakermod.monsters;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static fakermod.ModFile.makeImagePath;

public class Lancer extends AbstractMonster {
    public static final String ID = "FakerMod:Lancer";
    public static final String NAME = "Lancer";
    private static final String IMG = makeImagePath("boss/Lancer.png");

    private static final int HP_MIN = 100;
    private static final int HP_MAX = 120;
    private static final int A2_HP_MIN = 110;
    private static final int A2_HP_MAX = 130;

    private static final int SPEAR_THRUST_DAMAGE = 10;
    private static final int MULTI_THRUST_TIMES = 5;
    private static final int MULTI_THRUST_DAMAGE = 3;
    private static final int ENRAGE_TIMER = 10;
    private static final int ENRAGE_DAMAGE = 99999;

    private int turnCount = 0;

    private static final byte SPEAR_THRUST = 1;
    private static final byte MULTI_THRUST = 2;
    private static final byte CHARGE = 3;
    private static final byte GUARD_STANCE = 4;
    private static final byte ENRAGE_BUFF = 5;
    private static final byte ENRAGE = 6;

    private ArrayList<Byte> moveSequence = new ArrayList<>();
    private int moveIndex = 0;

    public Lancer(float x, float y) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0, 0, 500, 380, IMG, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(A2_HP_MIN, A2_HP_MAX);

        } else {
            setHp(HP_MIN, HP_MAX);
        }

        damage.add(new DamageInfo(this, SPEAR_THRUST_DAMAGE));
        damage.add(new DamageInfo(this, MULTI_THRUST_DAMAGE));
        damage.add(new DamageInfo(this, ENRAGE_DAMAGE, DamageInfo.DamageType.THORNS));
    }

    @Override
    public void usePreBattleAction() {
        this.addToBot(new TalkAction(this, "Nothing personal kid, but I've got orders to follow."));
        this.addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 8)));
    }

    @Override
    protected void getMove(int num) {
        if (turnCount == ENRAGE_TIMER - 1) {
            setMove(ENRAGE_BUFF, Intent.DEFEND);
            return;

        } else if (turnCount == ENRAGE_TIMER) {
            setMove(ENRAGE, Intent.ATTACK, this.damage.get(2).base);
            return;
        }

        if (moveIndex >= moveSequence.size()) {
            moveSequence.clear();
            moveSequence.add(SPEAR_THRUST);
            moveSequence.add(MULTI_THRUST);
            moveSequence.add(CHARGE);
            moveSequence.add(GUARD_STANCE);

            Collections.shuffle(moveSequence, new Random(AbstractDungeon.monsterRng.randomLong()));
            moveIndex = 0;
        }

        byte chosenMove = moveSequence.get(moveIndex);
        moveIndex++;

        switch (chosenMove) {
            case SPEAR_THRUST:
                setMove(SPEAR_THRUST, Intent.ATTACK, SPEAR_THRUST_DAMAGE);
                break;

            case MULTI_THRUST:
                setMove(MULTI_THRUST, Intent.ATTACK, MULTI_THRUST_DAMAGE, MULTI_THRUST_TIMES, true);
                break;

            case CHARGE:
                setMove(CHARGE, Intent.BUFF);
                break;

            case GUARD_STANCE:
                setMove(GUARD_STANCE, Intent.DEFEND_BUFF);
                break;
        }
    }

    @Override
    public void takeTurn() {
        turnCount++;

        if (turnCount == 5) {
            this.addToBot(new TalkAction(this, "Not bad, kid."));
        }

        if (turnCount == 10) {
            this.addToBot(new TalkAction(this, "That one hurt, damn it."));
        }

        if (turnCount == ENRAGE_TIMER - 1) {
            this.addToBot(new TalkAction(this, "Time's up. This is the end."));
        }

        switch (this.nextMove) {
            case SPEAR_THRUST:
                this.addToBot(new TalkAction(this, "Dodge this!"));
                this.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0)));
                break;

            case MULTI_THRUST:
                this.addToBot(new TalkAction(this, "Too slow"));
                for (int i = 0; i < MULTI_THRUST_TIMES; i++) {
                    this.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1)));
                }
                break;

            case CHARGE:
                this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2)));
                break;

            case GUARD_STANCE:
                this.addToBot(new GainBlockAction(this, 10));
                this.addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5)));
                break;

            case ENRAGE_BUFF:
                this.addToBot(new GainBlockAction(this, 10));
                break;

            case ENRAGE:
                this.addToBot(new TalkAction(this, "This will pierce your heart! Gae Bolg!"));
                this.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(2)));
                break;
        }

        this.addToBot(new RollMoveAction(this));
    }

    @Override
    public void die() {
        super.die();
        this.addToBot(new TalkAction(this, "This won't be the end."));
    }
}