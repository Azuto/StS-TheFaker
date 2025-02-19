package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.cards.projection.BladeDance;
import fakermod.powers.SwordBarrelPower;

import static fakermod.ModFile.makeID;

public class SwordBarrel extends AbstractEasyCard {
    public final static String ID = makeID("SwordBarrel");

    public SwordBarrel() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new BladeDance();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean isUpgraded = upgraded;
        this.addToBot(new ApplyPowerAction(p, p, new SwordBarrelPower(p, p, magicNumber, isUpgraded), magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        cardsToPreview.upgrade();
    }
}