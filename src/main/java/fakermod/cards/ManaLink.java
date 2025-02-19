package fakermod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.actions.ManaLinkAction;

import static fakermod.ModFile.makeID;

public class ManaLink extends AbstractEasyCard {
    public final static String ID = makeID("ManaLink");
 
    public ManaLink() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ManaLinkAction(p, energyOnUse, magicNumber, freeToPlayOnce));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}