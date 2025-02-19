package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.actions.CustomChangeStanceAction;
import fakermod.powers.IndependentActionPower;
import fakermod.stances.MeleeStance;

import static fakermod.ModFile.makeID;

public class IndependentAction extends AbstractEasyCard {
    public final static String ID = makeID("IndependentAction");

    public IndependentAction() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseHPAction(p, p, (p.currentHealth / 2)));
        this.addToBot(new ApplyPowerAction(p, p, new IndependentActionPower(p, magicNumber), magicNumber));
        this.addToBot(new CustomChangeStanceAction(MeleeStance.STANCE_ID));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}