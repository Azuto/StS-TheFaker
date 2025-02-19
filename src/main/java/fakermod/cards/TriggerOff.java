package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.powers.TriggerOffPower;

import static fakermod.ModFile.makeID;

public class TriggerOff extends AbstractEasyCard {
    public final static String ID = makeID("TriggerOff");

    public TriggerOff() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new TraceOn();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new TriggerOffPower(p, p, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }
}