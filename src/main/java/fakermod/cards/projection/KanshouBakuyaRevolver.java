package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.CharacterFile;
import fakermod.actions.CustomChangeStanceAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.stances.RangedStance;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class KanshouBakuyaRevolver extends AbstractEasyCard {
    public final static String ID = makeID("KanshouBakuyaRevolver");
    public float getTitleFontSize() { return 20.0F; }

    public KanshouBakuyaRevolver() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 3;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        tags.add(customTag.KANSHOUBAKUYA);
        tags.add(customTag.RANGED);
        getTitleFontSize();
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();

        if (this.hasTag(customTag.RANGED) && AbstractDungeon.player != null && AbstractDungeon.player.stance instanceof RangedStance) {
            int hpCost = (Math.max(this.costForTurn, 0)) * 2;
            this.keywords.add("[#rHP #rCost: #r" + hpCost);
        }
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
        for (int i = 0; i < 2 ; i++) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

        this.addToBot(new CustomChangeStanceAction(RangedStance.STANCE_ID));
    }

    public void upp() {
        upgradeDamage(2);
    }
}