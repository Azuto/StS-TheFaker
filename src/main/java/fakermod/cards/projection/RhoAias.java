package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class RhoAias extends AbstractEasyCard {
    public final static String ID = makeID("RhoAias");

    public RhoAias() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERP_COLOR);
        baseBlock = 3;
        baseMagicNumber = magicNumber = 3;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        setDisplayRarity(CardRarity.UNCOMMON);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; ++i) {
            this.addToBot(new GainBlockAction(p, p, block));
        }
    }

    public AbstractCard makeCopy() {
        return new RhoAias();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}