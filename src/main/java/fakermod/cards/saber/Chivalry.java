package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.powers.ChivalryPower;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class Chivalry extends AbstractEasyCard {
    public final static String ID = makeID("Chivalry");

    public Chivalry() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.NONE, CharacterFile.Enums.FAKERS_COLOR);
        baseMagicNumber = magicNumber = 1;
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
        this.addToBot(new ApplyPowerAction(p, p, new ChivalryPower(p, p, magicNumber)));
    }

    public void upp() {
        upgradeMagicNumber(1);
            }
}