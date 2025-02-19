package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static fakermod.ModFile.makeID;

public class MapoTofu extends AbstractEasyCard {
    public final static String ID = makeID("MapoTofu");

    public MapoTofu() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 6;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new HealAction(p, p, magicNumber));
        this.addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false, false));
    }

    public void upp() {
        upgradeBaseCost(0);
        upgradeMagicNumber(3);
    }
}