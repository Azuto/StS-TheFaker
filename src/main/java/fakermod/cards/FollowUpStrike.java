package fakermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class FollowUpStrike extends AbstractEasyCard {
    public final static String ID = makeID("FollowUpStrike");
 
    public FollowUpStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 1;
        tags.add(CardTags.STRIKE);
        tags.add(customTag.MELEE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber), magicNumber));
        this.addToBot(new DrawCardAction(1));
    }

    @Override
    public void upp() {  
        upgradeDamage(3);
        upgradeMagicNumber(1);
    }
}