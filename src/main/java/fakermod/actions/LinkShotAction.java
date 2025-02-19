package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class LinkShotAction extends AbstractGameAction {
    public static final String TEXT;
    private int debuffDuration;
    private AbstractMonster targetMonster;

    public LinkShotAction(int debuffDuration, AbstractMonster m) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.debuffDuration = debuffDuration;
        this.targetMonster = m;
    }

    @Override
    public void update() {
        if (this.targetMonster != null && !this.targetMonster.isDying && this.targetMonster.getIntentBaseDmg() < 1) {
            this.addToBot(new ApplyPowerAction(targetMonster, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, debuffDuration, targetMonster != null)));
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT, true));
        }

        this.isDone = true;
    }

    static { TEXT = "Clearly, it is about to attack."; }
}