package fakermod.cards.saber;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class ManaTransfer extends AbstractEasyCard {
    public final static String ID = makeID("ManaTransfer");
    private static final int AP_COST = -1;

    public ManaTransfer() {
        super(ID, -1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERS_COLOR);
        baseMagicNumber = magicNumber = 0;
        tags.add(customTag.SABER);
        setDisplayRarity(CardRarity.UNCOMMON);
    }

    @Override
    public void modifyCostForCombat(int amt) { }

    @Override
    public void updateCost(int amt) { }

    @Override
    public boolean hasEnoughEnergy() {
        return AbstractDungeon.player.hasPower(makeID("SummonSaberPower"));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(makeID("SummonSaberPower"))) {
            cantUseMessage = "I can't use it without Saber.";
            return false;
        }

        return true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int tempCost = Math.min(EnergyPanel.totalCount, energyOnUse);
        if (energyOnUse > 0) { p.evokeOrb(); }
        if (energyOnUse <= 0) { tempCost = 0; }
        for (int i = 0; i < energyOnUse + magicNumber; i++){
            this.addToBot(new IncreaseMaxOrbAction(1));
        }
        for (int i = 0; i < tempCost - 1 ; i++) {
            p.removeNextOrb();
        }
    }

    public AbstractCard makeCopy() {
        return new ManaTransfer();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}