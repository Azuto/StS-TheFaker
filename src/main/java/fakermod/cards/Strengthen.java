package fakermod.cards;

import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static fakermod.ModFile.makeID;

public class Strengthen extends AbstractEasyCard {
    public final static String ID = makeID("Strengthen");
 
    public Strengthen() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseDamage = 0;
        baseBlock = 0;
        baseMagicNumber = magicNumber = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ArmamentsAction(upgraded));
    }

    @Override
    public void upp() { }
}