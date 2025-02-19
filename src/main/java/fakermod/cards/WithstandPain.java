package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.cards.projection.Sword;
import fakermod.powers.WithstandPainPower;

import static fakermod.ModFile.makeID;

public class WithstandPain extends AbstractEasyCard {
    public final static String ID = makeID("WithstandPain");
 
    public WithstandPain() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Sword();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean isUpgraded;
        isUpgraded = upgraded;
        this.addToBot(new ApplyPowerAction(p, p, new WithstandPainPower(p, p, magicNumber, isUpgraded), magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        cardsToPreview.upgrade();
    }
}