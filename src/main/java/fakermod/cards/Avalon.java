package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.cards.saber.SummonSaber;
import fakermod.powers.AvalonPower;

import static fakermod.ModFile.makeID;

public class Avalon extends AbstractEasyCard {
    public final static String ID = makeID("Avalon");

    public Avalon() {
        super(ID, 4, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = 10;
        exhaust = true;
        cardsToPreview = new SummonSaber();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AvalonPower(p, p, magicNumber)));
        if (p.hasPower("FakerMod:SummonSaberPower")) {
            this.addToBot(new HealAction(p, p, baseSecondMagic));
        }
    }

    @Override
    public void upp() {
        upgradeBaseCost(3);
    }
}