package fakermod.relics;

import basemod.helpers.CardPowerTip;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import fakermod.CharacterFile;
import fakermod.cards.saber.CommandSealCard;
import fakermod.cards.saber.SummonSaber;

import static fakermod.ModFile.makeID;

public class CommandSeal extends AbstractEasyRelic implements ClickableRelic {
    public static final String ID = makeID("CommandSeal");
    private boolean usedThisTurn = false;
    private boolean isPlayerTurn = false;
    AbstractCard card = new CommandSealCard();

    public CommandSeal() {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL, CharacterFile.Enums.FAKER_COLOR);
        tips.add(new CardPowerTip(card));
        this.counter = 3;
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {
        if (this.isPlayerTurn && !this.usedThisTurn && this.counter != 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            flash();
            stopPulse();
            addToBot(new MakeTempCardInHandAction(new CommandSealCard(), 1));

            this.counter--;
            this.usedThisTurn = true;
            if (this.counter == 0) {
                this.setCounter(-2);
                this.description = this.DESCRIPTIONS[3];
                this.tips.clear();
                this.tips.add(new PowerTip(this.name, this.description));
                this.initializeTips();
            }
        }
    }

    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter <= 0) {
            this.usedUp();
        }
    }

    @Override
    public void atPreBattle() {
        if (this.counter > 0) {
            this.usedThisTurn = false;
        }
    }

    @Override
    public void atBattleStart() {
        if (this.counter > 0) {
            this.usedThisTurn = false;
            this.addToBot(new MakeTempCardInDrawPileAction(new SummonSaber(), 1, true, false));
        }
    }

    @Override
    public void atTurnStart() {
        if (this.counter > 0) {
            this.isPlayerTurn = true;
            beginPulse();
        }
    }

    @Override
    public void onPlayerEndTurn() {
        this.isPlayerTurn = false;
        stopPulse();
    }

    @Override
    public void onVictory() { stopPulse(); }
}