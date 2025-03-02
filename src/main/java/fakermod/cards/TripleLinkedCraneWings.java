package fakermod.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.cards.projection.KanshouBakuya;
import fakermod.cards.projection.KanshouBakuyaOveredge;

import static fakermod.ModFile.makeID;

public class TripleLinkedCraneWings extends AbstractEasyCard {
    public final static String ID = makeID("TripleLinkedCraneWings");
    public float getTitleFontSize() {
        return 18.0F;
    }
 
    public TripleLinkedCraneWings() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseDamage = damage = 0;
        baseMagicNumber = magicNumber = 2;
        MultiCardPreview.add(this, new KanshouBakuya());
        getTitleFontSize();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInHandAction(new KanshouBakuya(), magicNumber));
        if (upgraded) {
            this.addToBot(new MakeTempCardInHandAction(new KanshouBakuyaOveredge(), magicNumber));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(1);
        MultiCardPreview.add(this, new KanshouBakuyaOveredge());
    }
}