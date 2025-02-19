package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.cards.projection.KanshouBakuya;

import static fakermod.ModFile.makeID;

public class DoubleLinkedCraneWings extends AbstractEasyCard {
    public final static String ID = makeID("DoubleLinkedCraneWings");
    AbstractCard c = new KanshouBakuya().makeCopy();

    public float getTitleFontSize() {
        return 18.0F;
    }
 
    public DoubleLinkedCraneWings() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseDamage = 0;
        baseBlock = 0;
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new KanshouBakuya();
        getTitleFontSize();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInHandAction(c, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}