package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.powers.LastStandPower;

import static fakermod.ModFile.makeID;

public class LastStand extends AbstractEasyCard {
    public final static String ID = makeID("LastStand");

    public LastStand() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 3;
        cardsToPreview = new Strike();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.currentHealth <= secondMagic) {
            cantUseMessage = "This would kill me.";
            return false;

        } else {
            return super.canUse(p, m);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseHPAction(p, p, secondMagic));
        this.addToBot(new MakeTempCardInHandAction(new Strike()));
        this.addToBot(new ApplyPowerAction(p, p, new LastStandPower(p, p, secondMagic, magicNumber)));
    }

    public void upp() {
        upgradeMagicNumber(1);
            }
}