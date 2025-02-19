package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;
import static fakermod.util.Wiz.*;

public class KanshouBakuyaOveredge extends AbstractEasyCard {
    public final static String ID = makeID("KanshouBakuyaOveredge");
    public static boolean hasTag(AbstractCard c) {
        return c.hasTag(customTag.KANSHOUBAKUYA);
    }
    public float getTitleFontSize() { return 20.0F; }

    public static int countCards() {
        int count = 0;
        AbstractCard c;

        for (AbstractCard abstractCard : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            c = abstractCard;
            if (hasTag(c)) {
                ++count;
            }
        }

        return count;
    }

    public KanshouBakuyaOveredge() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 2;
        purgeOnUse = true;
        cardsToPreview = new KanshouBakuya();
        tags.add(customTag.PROJECTION);
        tags.add(customTag.KANSHOUBAKUYA);
        tags.add(customTag.MELEE);
        setDisplayRarity(CardRarity.UNCOMMON);
        getTitleFontSize();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2 ; i++){
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    public AbstractCard makeCopy() {
        return new KanshouBakuyaOveredge();
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}