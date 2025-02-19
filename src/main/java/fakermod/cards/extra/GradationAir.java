package fakermod.cards.extra;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.actions.TraceAction;
import fakermod.cards.AbstractEasyCard;

import static fakermod.ModFile.makeID;

public class GradationAir extends AbstractEasyCard {
    public final static String ID = makeID("GradationAir");
 
    public GradationAir() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        isEthereal = true;
        purgeOnUse = true;
        setDisplayRarity(CardRarity.UNCOMMON);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("FakerMod:TraceOnPower")) {
            int powerStack = p.getPower("FakerMod:TraceOnPower").amount;
            this.addToBot(new TraceAction(powerStack));
        } else {
            this.addToBot(new TraceAction());
        }
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public AbstractCard makeCopy() { return new GradationAir(); }

        @Override
    public void upp() { }
}