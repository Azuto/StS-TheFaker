package fakermod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.actions.TraceFractalAction;

import static fakermod.ModFile.makeID;

public class TraceFractal extends AbstractEasyCard {
    public final static String ID = makeID("TraceFractal");
 
    public TraceFractal() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("FakerMod:TraceOnPower")) {
            int powerStack = p.getPower("FakerMod:TraceOnPower").amount;
            this.addToBot(new TraceFractalAction(upgraded, powerStack));

        } else {
            this.addToBot(new TraceFractalAction(upgraded));
        }
    }

    @Override
    public void upp() { }
}