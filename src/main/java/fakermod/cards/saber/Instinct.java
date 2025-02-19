package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.powers.InstinctPower;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class Instinct extends AbstractEasyCard {
    public final static String ID = makeID("Instinct");
    private static int AP_COST = 1;
    private static int nextDamage = 2;

    public Instinct() {
        super(ID, AP_COST, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERS_COLOR);
        baseMagicNumber = magicNumber = 1;
        secondMagic = baseSecondMagic = nextDamage;
        tags.add(customTag.SABER);
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
        this.addToBot(new ApplyPowerAction(p, p, new InstinctPower(p, p, magicNumber, nextDamage)));
        this.addToTop(new GainEnergyAction(tempCost));
    }

    public AbstractCard makeCopy() {
        return new Instinct();
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
        AP_COST = 0;
    }
}