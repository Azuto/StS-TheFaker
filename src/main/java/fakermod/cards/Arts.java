package fakermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.powers.ArtsPower;
import fakermod.stances.RangedStance;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class Arts extends AbstractEasyCard {
    public final static String ID = makeID("Arts");

    public Arts() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 5;
        baseBlock = block = 4;
        baseMagicNumber = magicNumber = 1;
        tags.add(customTag.COMMAND);
        tags.add(customTag.RANGED);
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

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn
                .subList(0, AbstractDungeon.actionManager.cardsPlayedThisTurn.size()).stream()
                .noneMatch(c -> c.hasTag(customTag.COMMAND))) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.addToBot(new GainBlockAction(p, p, block));
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1 == 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn
                .subList(0, AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1).stream()
                .noneMatch(c -> c.hasTag(customTag.COMMAND))) {
            this.addToBot(new ApplyPowerAction(p, p, new ArtsPower(p, p, magicNumber)));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}