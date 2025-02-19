package fakermod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.actions.TraceAction;

import static fakermod.ModFile.makeID;

public class Trace extends AbstractEasyCard {
    public final static String ID = makeID("Trace");

    public Trace() { super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE); }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("FakerMod:TraceOnPower")) {
            int powerStack = p.getPower("FakerMod:TraceOnPower").amount;
            this.addToBot(new TraceAction(powerStack));

        } else {
            this.addToBot(new TraceAction());
        }
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }
}