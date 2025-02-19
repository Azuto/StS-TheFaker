package fakermod.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fakermod.cards.projection.NineLives;

import static fakermod.ModFile.makeID;

public class NineLivesBladeWorks extends AbstractEasyCard {
    public final static String ID = makeID("NineLivesBladeWorks");
    public float getTitleFontSize() {
        return 17.5F;
    }

    AbstractCard c = new NineLives();
 
    public NineLivesBladeWorks() {
        super(ID, 5, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        exhaust = true;
        MultiCardPreview.add(this, c, new TraceOn(), new TriggerOff());
        getTitleFontSize();
    }

    @Override
    public void triggerWhenDrawn() {
        updateCost();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        updateCost();
    }

    private void updateCost() {
        AbstractPower power = AbstractDungeon.player.getPower("FakerMod:TraceOnPower");
        if (power != null) {
            int costReduction = Math.min(power.amount, 5);
            this.setCostForTurn(Math.max(0, this.cost - costReduction));
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = AbstractDungeon.player.hand.size();
        AbstractCard c = new NineLives().makeCopy();
        c.setCostForTurn(0);

        for (int i = 0; i < count; ++i) {
            this.addToTop(new ExhaustAction(1, true, true));
        }

        if (upgraded) {
            c.upgrade();
        }

        this.addToBot(new MakeTempCardInHandAction(c, 1));
    }

    @Override
    public void upp() {
        c.upgrade();
    }
}