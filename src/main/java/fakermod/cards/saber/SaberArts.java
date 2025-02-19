package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.powers.ArtsPower;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class SaberArts extends AbstractEasyCard {
    public final static String ID = makeID("SaberArts");
    private static final int AP_COST = 1;

    public SaberArts() {
        super(ID, AP_COST, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY, CharacterFile.Enums.FAKERS_COLOR);
        baseDamage = 7;
        baseBlock = 6;
        baseMagicNumber = magicNumber = 1;
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
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new GainBlockAction(p, p, block));
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1 == 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn
                .subList(0, AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1).stream()
                .noneMatch(c -> c.hasTag(customTag.COMMAND))) {
            this.addToBot(new ApplyPowerAction(p, p, new ArtsPower(p, p, magicNumber)));
        }

        this.addToTop(new GainEnergyAction(tempCost));
    }

    public AbstractCard makeCopy() { return new SaberArts(); }

        @Override
    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}