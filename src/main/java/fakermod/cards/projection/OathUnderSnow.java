package fakermod.cards.projection;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.actions.OathUnderSnowAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class OathUnderSnow extends AbstractEasyCard {
    public final static String ID = makeID("OathUnderSnow");

    public OathUnderSnow() {
        super(ID, 3, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 1;
        retain = true;
        selfRetain = true;
        purgeOnUse = true;
        cardsToPreview = new BladeDance();
        tags.add(customTag.PROJECTION);
        setDisplayRarity(CardRarity.RARE);
    }

    @Override
    public void atTurnStart() {
        this.retain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new OathUnderSnowAction());
    }

    public AbstractCard makeCopy() {
        return new OathUnderSnow();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() { }
}