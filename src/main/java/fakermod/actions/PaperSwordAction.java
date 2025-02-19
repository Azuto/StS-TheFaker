package fakermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PaperSwordAction extends AbstractGameAction {
    private final AbstractMonster monster;

    public PaperSwordAction(AbstractMonster monster) {
        this.monster = monster;
    }

    @Override
    public void update() {
        if (monster != null && !monster.isDeadOrEscaped()) {
            monster.rollMove();
            monster.createIntent();
        }
        this.isDone = true;
    }
}