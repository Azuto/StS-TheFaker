package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static fakermod.ModFile.makeID;

public class Lunch extends AbstractEasyCard {
    public final static String ID = makeID("Lunch");
 
    public Lunch() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 4;
        exhaust = true;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new HealAction(p, p, magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Lunch();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(4);
    }
}