package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;
import static fakermod.util.Wiz.*;

public class KanshouBakuya extends AbstractEasyCard {
    public final static String ID = makeID("KanshouBakuya");

    public KanshouBakuya() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 3;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        tags.add(customTag.KANSHOUBAKUYA);
        tags.add(customTag.MELEE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2 ; i++){
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    public AbstractCard makeCopy() {
        return new KanshouBakuya();
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }
}