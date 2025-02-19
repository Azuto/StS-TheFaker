package fakermod.monsters;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CurlUpPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static fakermod.ModFile.makeImagePath;

public class Assassin extends AbstractMonster {
    public static final String ID = "FakerMod:Assassin";
    public static final String NAME = "Assassin";
    private static final String IMG = makeImagePath("boss/Assassin.png");

    private static final int HP_MIN = 100;
    private static final int HP_MAX = 120;
    private static final int A2_HP_MIN = 110;
    private static final int A2_HP_MAX = 130;

    private static final int DAGGER_TOSS_DAMAGE = 9;
    private static final int DAGGER_CUTS_DAMAGE = 14;
    private static final int HEAVY_BLOW_DAMAGE = 23;
    private static final int ENRAGE_TIMER = 10;
    private static final int ENRAGE_DAMAGE = 99999;

    private int turnCount = 0;

    private static final byte DAGGER_TOSS = 1;
    private static final byte DAGGER_CUTS = 2;
    private static final byte HEAVY_BLOW = 3;
    private static final byte SHADOW_STEP = 4;
    private static final byte ENRAGE_DEBUFF = 5;
    private static final byte ENRAGE = 6;

    private ArrayList<Byte> moveSequence = new ArrayList<>();
    private int moveIndex = 0;

    public Assassin(float x, float y) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(HP_MIN, HP_MAX), 0, 0, 500, 380, IMG, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(A2_HP_MIN, A2_HP_MAX);

        } else {
            setHp(HP_MIN, HP_MAX);
        }

        damage.add(new DamageInfo(this, DAGGER_TOSS_DAMAGE));
        damage.add(new DamageInfo(this, DAGGER_CUTS_DAMAGE));
        damage.add(new DamageInfo(this, HEAVY_BLOW_DAMAGE));
        damage.add(new DamageInfo(this, ENRAGE_DAMAGE, DamageInfo.DamageType.HP_LOSS));
    }

    @Override
    public void usePreBattleAction() {
        this.addToBot(new TalkAction(this, "yOUR cARDS ARE ALL trASH!"));
        this.addToBot(new ApplyPowerAction(this, this, new CurlUpPower(this, 15)));
    }

    @Override
    protected void getMove(int num) {
        if (turnCount == ENRAGE_TIMER - 1) {
            setMove(ENRAGE_DEBUFF, Intent.STRONG_DEBUFF);
            return;

        } else if (turnCount == ENRAGE_TIMER) {
            setMove(ENRAGE, Intent.ATTACK, this.damage.get(3).base);
            return;
        }

        if (moveIndex >= moveSequence.size()) {
            moveSequence.clear();
            moveSequence.add(DAGGER_TOSS);
            moveSequence.add(DAGGER_CUTS);
            moveSequence.add(HEAVY_BLOW);
            moveSequence.add(SHADOW_STEP);

            Collections.shuffle(moveSequence, new Random(AbstractDungeon.monsterRng.randomLong()));
            moveIndex = 0;
        }

        byte chosenMove = moveSequence.get(moveIndex);
        moveIndex++;

        switch (chosenMove) {
            case DAGGER_TOSS:
                setMove(DAGGER_TOSS, Intent.ATTACK_DEBUFF, DAGGER_TOSS_DAMAGE);
                break;

            case DAGGER_CUTS:
                setMove(DAGGER_CUTS, Intent.ATTACK_DEBUFF, DAGGER_CUTS_DAMAGE);
                break;

            case HEAVY_BLOW:
                setMove(HEAVY_BLOW, Intent.ATTACK_DEBUFF, HEAVY_BLOW_DAMAGE);
                break;

            case SHADOW_STEP:
                setMove(SHADOW_STEP, Intent.DEFEND_BUFF);
                break;
        }
    }

    @Override
    public void takeTurn() {
        turnCount++;

        if (turnCount == 5) {
            this.addToBot(new TalkAction(this, "yOU hAVE nO chANCE!"));
        }

        if (turnCount == 10) {
            this.addToBot(new TalkAction(this, "AAAEEIIAAAHHHH"));
        }

        if (turnCount == ENRAGE_TIMER - 1) {
            this.addToBot(new TalkAction(this, "ENOUGH! Now, die."));
        }

        switch (this.nextMove) {
            case DAGGER_TOSS:
                this.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.player.discardPile.addToBottom(new Burn());
                break;

            case DAGGER_CUTS:
                this.addToBot(new TalkAction(this, "hAHAHAHAAA"));
                this.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1)));
                AbstractDungeon.player.discardPile.addToBottom(new Wound());
                break;

            case HEAVY_BLOW:
                this.addToBot(new TalkAction(this, "dIE! dIE!"));
                this.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(2)));
                for (int i = 0; i < 3; i++) {
                    AbstractDungeon.player.discardPile.addToBottom(new Dazed());
                }
                break;

            case SHADOW_STEP:
                this.addToBot(new ApplyPowerAction(this, this, new IntangiblePower(this, 1)));
                break;

            case ENRAGE_DEBUFF:
                AbstractDungeon.player.drawPile.addToBottom(new VoidCard());
                break;

            case ENRAGE:
                this.addToBot(new TalkAction(this, "Zabaniya."));
                this.addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(3)));
                break;
        }

        this.addToBot(new RollMoveAction(this));
    }

    @Override
    public void die() {
        super.die();
        this.addToBot(new TalkAction(this, "CURSE YOUUU!"));
    }
}