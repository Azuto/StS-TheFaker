package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class UnlimitedBladeBurst extends AbstractEasyCard {
    public final static String ID = makeID("UnlimitedBladeBurst");

    public float getTitleFontSize() {
        return 18.0F;
    }

    public static boolean isProjection(AbstractCard c) {
        return c.hasTag(customTag.PROJECTION);
    }

    public static int countCards() {
        int count = 0;
        AbstractCard c;

        for (AbstractCard abstractCard : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            c = abstractCard;
            if (isProjection(c)) {
                ++count;
            }
        }

        return count;
    }

    public UnlimitedBladeBurst() {
        super(ID, 3, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 10;
        baseMagicNumber = magicNumber = 5;
        retain = true;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        tags.add(customTag.MELEE);
        setDisplayRarity(CardRarity.RARE);
        getTitleFontSize();
    }

    @Override
    public void atTurnStart() {
        this.retain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (p.hasPower("FakerMod:UBWAPower")) {
            this.addToBot(new RemoveSpecificPowerAction(p, p, "FakerMod:UBWAPower"));
        }
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

    public AbstractCard makeCopy() {
        return new UnlimitedBladeBurst();
    }

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upp() { }
}