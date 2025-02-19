package fakermod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.actions.TraceAlterAction;

import static fakermod.ModFile.makeID;

public class TraceAlter extends AbstractEasyCard {
    public final static String ID = makeID("TraceAlter");

    public TraceAlter() { super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE); }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("FakerMod:TraceOnPower")) {
            int powerStack = p.getPower("FakerMod:TraceOnPower").amount;
            this.addToBot(new TraceAlterAction(powerStack));

        } else {
            this.addToBot(new TraceAlterAction());
        }
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }
}