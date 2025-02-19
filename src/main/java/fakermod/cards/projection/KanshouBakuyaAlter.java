package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.actions.CustomChangeStanceAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.stances.MeleeStance;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class KanshouBakuyaAlter extends AbstractEasyCard {
    public final static String ID = makeID("KanshouBakuyaAlter");
    public float getTitleFontSize() { return 20.0F; }

    public KanshouBakuyaAlter() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 3;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        tags.add(customTag.KANSHOUBAKUYA);
        tags.add(customTag.MELEE);
        getTitleFontSize();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2 ; i++){
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        this.addToBot(new CustomChangeStanceAction(MeleeStance.STANCE_ID));
    }

    public AbstractCard makeCopy() {
        return new KanshouBakuyaAlter();
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}