package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.actions.BowAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class Bow extends AbstractEasyCard {
    public final static String ID = makeID("Bow");

    public Bow() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 0;
        baseBlock = 4;
        baseMagicNumber = magicNumber = 0;
        purgeOnUse = true;
        cardsToPreview = new Arrow();
        tags.add(customTag.PROJECTION);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, baseBlock));
        this.addToBot(new BowAction(this.upgraded));
    }

    public AbstractCard makeCopy() {
        return new Bow();
    }

    @Override
    public void upp() {
        cardsToPreview.upgrade();
    }
}