package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static fakermod.ModFile.makeID;

public class SteelBody extends AbstractEasyCard {
    public final static String ID = makeID("SteelBody");
 
    public SteelBody() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseBlock = block = 7;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, block));
        this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(4);
        upgradeMagicNumber(1);
    }
}