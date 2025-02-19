package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.stances.RangedStance;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class BladeDance extends AbstractEasyCard {
    public final static String ID = makeID("BladeDance");

    public BladeDance() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 3;
        isEthereal = true;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        tags.add(customTag.RANGED);
        setDisplayRarity(CardRarity.UNCOMMON);
    }

    @Override
    public boolean hasEnoughEnergy() {
        return AbstractDungeon.player.stance.ID.equals(RangedStance.STANCE_ID) || super.hasEnoughEnergy();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int hpCost = this.costForTurn * 2;
        if (p.stance.ID.equals(RangedStance.STANCE_ID) && p.currentHealth <= hpCost) {
            cantUseMessage = "This would kill me.";
            return false;

        } else {
            return super.canUse(p, m);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int randomAmount = AbstractDungeon.cardRandomRng.random(1, 3);

        for (int i = 0; i < randomAmount ; i++){
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        this.addToBot(new DrawCardAction(1));
    }

    public AbstractCard makeCopy() {
        return new BladeDance();
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }
}