package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import fakermod.util.CustomStanceRegistry;

public class CustomChangeStanceAction extends AbstractGameAction {
    private final String stanceID;
    private AbstractStance newStance;

    public CustomChangeStanceAction(String stanceID) {
        this.stanceID = stanceID;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hasPower("CannotChangeStancePower")) {
                this.isDone = true;
                return;
            }

            AbstractStance oldStance = AbstractDungeon.player.stance;

            if (!oldStance.ID.equals(this.stanceID)) {
                this.newStance = CustomStanceRegistry.getStance(stanceID);
                if (this.newStance == null) {
                    this.isDone = true;
                    return;
                }

                for (AbstractPower p : AbstractDungeon.player.powers) {
                    p.onChangeStance(oldStance, this.newStance);
                }

                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onChangeStance(oldStance, this.newStance);
                }

                oldStance.onExitStance();

                AbstractDungeon.player.stance = this.newStance;
                this.newStance.onEnterStance();

                AbstractDungeon.player.switchedStance();
                AbstractDungeon.actionManager.uniqueStancesThisCombat.put(
                        this.newStance.ID,
                        AbstractDungeon.actionManager.uniqueStancesThisCombat.getOrDefault(this.newStance.ID, 0) + 1
                );

                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    c.triggerExhaustedCardsOnStanceChange(this.newStance);
                }

                AbstractDungeon.player.onStanceChange(this.stanceID);
                AbstractDungeon.onModifyPower();
            }

            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }
}