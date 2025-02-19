package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class ShiningPath extends AbstractEasyCard {
    public final static String ID = makeID("ShiningPath");
    AbstractCard c = new Excalibur().makeCopy();

    public ShiningPath() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERS_COLOR);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
        cardsToPreview = new Excalibur();
        tags.add(customTag.SABER);
        setDisplayRarity(CardRarity.RARE);
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
        this.addToBot(new MakeTempCardInDrawPileAction(c, 1, true, false, false));
        this.addToBot(new IncreaseMaxOrbAction(1));
    }

    public AbstractCard makeCopy() {
        return new ShiningPath();
    }

    @Override
    public void upp() {
        cardsToPreview.upgrade();
        c.upgrade();
    }
}