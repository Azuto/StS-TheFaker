package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class DragonsCore extends AbstractEasyCard {
    public final static String ID = makeID("DragonsCore");
    AbstractCard c = new SaberBuster().makeCopy();

    public DragonsCore() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERS_COLOR);
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
        cardsToPreview = c;
        tags.add(customTag.SABER);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;

        } else if (!p.hasPower("FakerMod:SummonSaberPower")) {
            cantUseMessage = "I can't do this without Saber...";
            return false;

        } else {
            return true;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        c.purgeOnUse = true;
        this.addToBot(new MakeTempCardInHandAction(c, magicNumber));
    }

    public AbstractCard makeCopy() {
        return new DragonsCore();
    }

    @Override
    public void upp() {
        cardsToPreview.upgrade();
        c.upgrade();
    }
}