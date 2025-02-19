package fakermod.cards.projection;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import fakermod.CharacterFile;
import fakermod.actions.TraceFractalAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.cards.Incantation;
import fakermod.cards.TraceOn;
import fakermod.cards.extra.ULW;
import fakermod.powers.IncantationPower;
import fakermod.powers.OriginBulletPower;
import fakermod.powers.TraceOnPower;
import fakermod.stances.RangedStance;
import fakermod.tags.customTag;

import static fakermod.ModFile.makeID;

public class OriginBullet extends AbstractEasyCard {
    public final static String ID = makeID("OriginBullet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String[] VERSE =  new String[] {
            "I am the bone of my sword.",
            "Steel is my body and fire is my blood.",
            "I have created over a thousand blades."
    };

    AbstractCard c = new ULW();
    AbstractCard i = new Incantation();
    AbstractCard t = new TraceOn();

    public OriginBullet() {
        super(ID, 5, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CharacterFile.Enums.FAKERP_COLOR);
        baseDamage = 1;
        purgeOnUse = true;
        tags.add(customTag.PROJECTION);
        tags.add(customTag.RANGED);
        MultiCardPreview.add(this, i, c);
        setDisplayRarity(CardRarity.RARE);
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

    private void applyIncantationOrTraceOn() {
        AbstractPlayer p = AbstractDungeon.player;
        boolean hasPower = p.hasPower("FakerMod:IncantationPower");
        int powerStack = hasPower ? p.getPower("FakerMod:IncantationPower").amount : 0;

        if (hasPower) {
            if (powerStack > 2) {
                if (p.hasPower("FakerMod:TraceOnPower")) {
                    damage -= p.getPower("FakerMod:TraceOnPower").amount;
                }
                this.addToBot(new ApplyPowerAction(p, p, new TraceOnPower(p, damage), damage));
                MultiCardPreview.clear(this);
                MultiCardPreview.add(this, t, c);
                rawDescription = UPGRADE_DESCRIPTION;

            } else if (powerStack == 2) {
                this.addToBot(new TalkAction(true, VERSE[2], 6.0f, 4.0f));
                this.addToBot(new MakeTempCardInHandAction(new Sword(), 1));
                this.addToBot(new MakeTempCardInHandAction(new KanshouBakuyaAlter(), 1));
                this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, block), block));
                MultiCardPreview.clear(this);
                MultiCardPreview.add(this, t, c);
                rawDescription = UPGRADE_DESCRIPTION;

            } else if (powerStack == 1) {
                this.addToBot(new TalkAction(true, VERSE[1], 6.0f, 4.0f));
                this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
                this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber), magicNumber));
                this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, block), block));
            }
        } else {
            this.addToBot(new TalkAction(true, VERSE[0], 6.0f, 4.0f));
            this.addToBot(new TraceFractalAction(false));
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, block), block));
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        int actualDamage = Math.max(0, damage - m.currentBlock);

        applyIncantationOrTraceOn();
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (actualDamage > 0) {
            this.addToBot(new ApplyPowerAction(m, p, new OriginBulletPower(m, p, 1)));
            this.addToBot(new MakeTempCardInHandAction(c));
        }
    }

    public void upp() {
        upgradeBaseCost(5);
        c.upgrade();
    }
}