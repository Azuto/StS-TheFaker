package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class StrikeAir extends AbstractEasyCard {
    public final static String ID = makeID("StrikeAir");
    private static int AP_COST = 2;

    public StrikeAir() {
        super(ID, AP_COST, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, CharacterFile.Enums.FAKERS_COLOR);
        baseDamage = 18;
        baseMagicNumber = magicNumber = 1;
        tags.add(customTag.SABER);
        setDisplayRarity(CardRarity.UNCOMMON);
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
        int tempCost = Math.min(AP_COST, p.energy.energy);
        if (costForTurn > 0) { p.evokeOrb(); }
        if (costForTurn <= 0) { tempCost = 0; }
        this.addToBot(new DamageAllEnemiesAction(p, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
        }

        for (int i = 0; i < tempCost - 1 ; i++) {
            p.removeNextOrb();
        }

        this.addToTop(new GainEnergyAction(tempCost));
    }

    public AbstractCard makeCopy() {
        return new StrikeAir();
    }

    @Override
    public void upp() {
        upgradeDamage(5);
        upgradeMagicNumber(1);
    }
}