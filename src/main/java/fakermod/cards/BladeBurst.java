package fakermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.stances.RangedStance;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class BladeBurst extends AbstractEasyCard {
    public final static String ID = makeID("BladeBurst");

    public static boolean isProjection(AbstractCard c) { return c.hasTag(customTag.PROJECTION); }
 
    public BladeBurst() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage= damage = 6;
        baseMagicNumber = magicNumber = 3;
        tags.add(customTag.RANGED);
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

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public boolean hasEnoughEnergy() {
        return AbstractDungeon.player.stance.ID.equals(RangedStance.STANCE_ID) || super.hasEnoughEnergy();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int hpCost = this.costForTurn * 2;
        if (p.stance.ID.equals(RangedStance.STANCE_ID) && p.currentHealth <= hpCost) {
            cantUseMessage = "This would kill me.";
            return false;

        } else {
            return super.canUse(p, m);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    public AbstractCard makeCopy() {
        return new BladeBurst();
    }

    @Override
    public void upp() { upgradeBaseCost(1); }
}