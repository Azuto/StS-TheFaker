package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fakermod.powers.TraceOnPower;

import static fakermod.ModFile.makeID;

public class TraceOn extends AbstractEasyCard {
    public final static String ID = makeID("TraceOn");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public TraceOn() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new Trace();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower traceOnPower = p.getPower("FakerMod:TraceOnPower");
        int newAmount = this.magicNumber;

        if (traceOnPower != null) {
            int currentStacks = traceOnPower.amount;
            if (currentStacks >= 5) {
                newAmount = 0;
            } else if (currentStacks + this.magicNumber > 5) {
                newAmount = 5 - currentStacks;
            }
        }
        if (newAmount > 0) {
            this.addToBot(new ApplyPowerAction(p, p, new TraceOnPower(p, newAmount), newAmount));
        }
    }

    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded < 4;
    }

    private static String getDescription(int upgradeLevel) {
        return upgradeLevel >= 1 && upgradeLevel <= 4
                ? EXTENDED_DESCRIPTION[upgradeLevel - 1]
                : DESCRIPTION;
    }

    @Override
    public void upgrade() {
        if (this.timesUpgraded < 4) {
            this.upgradeName();
            this.magicNumber += 1;
            this.rawDescription = getDescription(this.timesUpgraded);
            this.initializeDescription();
        }
    }

    @Override
    public void upp() { }
}