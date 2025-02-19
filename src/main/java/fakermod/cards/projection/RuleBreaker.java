package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;
import static fakermod.util.Wiz.*;

public class RuleBreaker extends AbstractEasyCard {
    public final static String ID = makeID("RuleBreaker");

    public RuleBreaker() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 1;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        tags.add(customTag.MELEE);
        setDisplayRarity(CardRarity.RARE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.type != AbstractMonster.EnemyType.BOSS) {
            this.addToBot(new RemoveAllBlockAction(m, p));
            this.addToBot(new RemoveAllPowersAction(m, false));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public AbstractCard makeCopy() {
        return new RuleBreaker();
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeBaseCost(1);
    }
}