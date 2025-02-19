package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ArcheryAction extends AbstractGameAction {
    private final int healAmount;

    public ArcheryAction(AbstractMonster target, int healAmount) {
        this.target = target;
        this.healAmount = healAmount;
    }

    @Override
    public void update() {
        if (this.target != null && this.target.isDying && !this.target.halfDead) {
            AbstractDungeon.player.heal(healAmount);
        }

        isDone = true;
    }
}