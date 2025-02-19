package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.powers.QuickPowerInactive;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class SaberQuick extends AbstractEasyCard {
    public final static String ID = makeID("SaberQuick");
    private static final int AP_COST = 1;
    private static int nextDamage = 5;

    public SaberQuick() {
        super(ID, AP_COST, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY, CharacterFile.Enums.FAKERS_COLOR);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 1;
        secondMagic = baseSecondMagic = nextDamage;
        tags.add(customTag.SABER);
        tags.add(customTag.COMMAND);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn
                .subList(0, AbstractDungeon.actionManager.cardsPlayedThisTurn.size()).stream()
                .noneMatch(c -> c.hasTag(customTag.COMMAND))) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void modifyCostForCombat(int amt) { }

    @Override
    public void updateCost(int amt) { }

    @Override
    public void applyPowers() {
        int originalCost = this.cost;
        super.applyPowers();
        this.cost = originalCost;
        this.isCostModified = false;
    }

    @Override
    public boolean hasEnoughEnergy() {
        return AbstractDungeon.player.hasPower(makeID("SummonSaberPower")) && costForTurn == 0
                || AbstractDungeon.player.filledOrbCount() >= AP_COST;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(makeID("SummonSaberPower"))) {
            cantUseMessage = "I can't use it without Saber.";
            return false;
        }

        if (costForTurn > 0 && p.filledOrbCount() < AP_COST) {
            cantUseMessage = "I don't have enough AP.";
            return false;
        }

        return true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int tempCost = Math.min(EnergyPanel.totalCount, AP_COST);
        if (costForTurn > 0) { p.evokeOrb(); }
        if (costForTurn <= 0) { tempCost = 0; }
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1 == 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn
                .subList(0, AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1).stream()
                .noneMatch(c -> c.hasTag(customTag.COMMAND))) {
            this.addToBot(new ApplyPowerAction(p, p, new QuickPowerInactive(p, p, magicNumber, nextDamage)));
        }

        this.addToTop(new GainEnergyAction(tempCost));
    }

    public AbstractCard makeCopy() {
        return new SaberQuick();
    }

    @Override
    public void upp() {
        upgradeDamage(7);
    }
}