package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.powers.ArcheryPower;

import static fakermod.ModFile.makeID;

public class Archery extends AbstractEasyCard {
    public final static String ID = makeID("Archery");
 
    public Archery() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new ArcheryPower(p, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}