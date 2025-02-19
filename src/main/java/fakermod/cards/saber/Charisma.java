package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.powers.CharismaPower;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class Charisma extends AbstractEasyCard {
    public final static String ID = makeID("Charisma");

    public Charisma() {
        super(ID, 0, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERS_COLOR);
        baseMagicNumber = magicNumber = 2;
        secondMagic = baseSecondMagic = 3;
        exhaust = true;
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
        addToBot(new ApplyPowerAction(p, p, new CharismaPower(p, p, secondMagic, magicNumber)));
    }

    public AbstractCard makeCopy() {
        return new Charisma();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}