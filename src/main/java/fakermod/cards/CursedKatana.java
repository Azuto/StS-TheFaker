package fakermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class CursedKatana extends AbstractEasyCard {
    public final static String ID = makeID("CursedKatana");
 
    public CursedKatana() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 17;
        baseMagicNumber = magicNumber = 7;
        exhaust = true;
        tags.add(customTag.MELEE);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.currentHealth <= magicNumber) {
            cantUseMessage = "This would kill me.";
            return false;

        } else {
            return super.canUse(p, m);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseHPAction(p, p, magicNumber));
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(-3);
    }
}