package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;
import static fakermod.util.Wiz.*;

public class NineLives extends AbstractEasyCard {
    public final static String ID = makeID("NineLives");

    public NineLives() {
        super(ID, 5, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 2;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        tags.add(customTag.MELEE);
        setDisplayRarity(CardRarity.RARE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 9/3; i++) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    public AbstractCard makeCopy() {
        return new NineLives();
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }
}