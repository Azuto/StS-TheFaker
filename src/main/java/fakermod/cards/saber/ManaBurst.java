package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.orbs.AP;
import fakermod.powers.ManaBurstPower;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class ManaBurst extends AbstractEasyCard {
    public final static String ID = makeID("ManaBurst");

    public ManaBurst() {
        super(ID, 0, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERS_COLOR);
        baseMagicNumber = magicNumber = 1;
        secondMagic = baseSecondMagic = 3;
        exhaust = true;
        tags.add(customTag.SABER);
        setDisplayRarity(CardRarity.UNCOMMON);
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
        this.addToTop(new ApplyPowerAction(p, p, new ManaBurstPower(p, p, secondMagic, magicNumber)));
        this.addToTop(new IncreaseMaxOrbAction(magicNumber));
        for (int i = 0; i < magicNumber; i++) {
            p.channelOrb(new AP());
        }
    }

    public AbstractCard makeCopy() {
        return new ManaBurst();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}