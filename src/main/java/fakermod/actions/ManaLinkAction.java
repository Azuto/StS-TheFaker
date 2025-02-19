package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import fakermod.powers.ManaLinkPower;

public class ManaLinkAction extends AbstractGameAction {
    private int energyOnUse;
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int turns;

    public ManaLinkAction(AbstractPlayer p, int energyOnUse, int turns, boolean freeToPlayOnce) {
        this.p = p;
        this.energyOnUse = energyOnUse;
        this.turns = turns;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            if (!this.freeToPlayOnce) {
                this.addToBot(new ApplyPowerAction(p, p, new ManaLinkPower(p, p, turns, effect)));
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}