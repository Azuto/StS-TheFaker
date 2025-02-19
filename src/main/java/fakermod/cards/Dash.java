package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static fakermod.ModFile.makeID;

public class Dash extends AbstractEasyCard {
    public final static String ID = makeID("Dash");
 
    public Dash() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseBlock = block = 4;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Dash();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}