package fakermod.cards.projection;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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

public class UnlimitedBladeDance extends AbstractEasyCard {
    public final static String ID = makeID("UnlimitedBladeDance");
    public float getTitleFontSize() {
        return 18.0F;
    }


    public UnlimitedBladeDance() {
        super(ID, 3, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 3;
        baseBlock = 0;
        baseMagicNumber = magicNumber = 0;
        retain = true;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        tags.add(customTag.RANGED);
        setDisplayRarity(CardRarity.RARE);
        getTitleFontSize();
    }

    @Override
    public void atTurnStart() {
        this.retain = true;
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
        int randomAmount = AbstractDungeon.cardRandomRng.random(5, 10);

        for (int i = 0; i < randomAmount ; i++){
            this.addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        if (p.hasPower("FakerMod:UBWCPower")) {
            this.addToBot(new RemoveSpecificPowerAction(p, p, "FakerMod:UBWCPower"));
        }
    }

    public AbstractCard makeCopy() {
        return new UnlimitedBladeDance();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() { }
}