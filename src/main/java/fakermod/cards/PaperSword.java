package fakermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import fakermod.actions.PaperSwordAction;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class PaperSword extends AbstractEasyCard {
    public final static String ID = makeID("PaperSword");

    public PaperSword() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;
        exhaust = true;
        tags.add(customTag.MELEE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if (!m.isDeadOrEscaped() && !m.intent.equals(AbstractMonster.Intent.UNKNOWN)) {
            this.addToBot(new PaperSwordAction(m));
        }
    }

    public void upp() {
        upgradeBaseCost(0);
        upgradeDamage(3);
    }
}