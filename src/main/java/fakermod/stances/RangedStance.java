package fakermod.stances;

import basemod.devcommands.energy.Energy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import fakermod.ModFile;
import fakermod.tags.customTag;

public class RangedStance extends AbstractStance {

    public static final String STANCE_ID = ModFile.makeID("RangedStance");
    private static final StanceStrings stanceStrings = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public static final String NAME = stanceStrings.NAME;
    public static final String[] DESCRIPTIONS = stanceStrings.DESCRIPTION;
    private static long sfxId = -1;

    public RangedStance() {
        ID = STANCE_ID;
        name = NAME;
        updateDescription();
    }

    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            this.stopIdleSfx();
        }

        CardCrawlGame.sound.play("STANCE_ENTER_CALM");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SKY, true));
        setCost();
    }

    @Override
    public void onExitStance() {
        this.stopIdleSfx();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(2));
    }

    @Override
    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
            sfxId = -1L;
        }
    }

    @Override
    public void atStartOfTurn() {
        setCost();
    }

    public void setCost() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(customTag.RANGED)) {
                c.setCostForTurn(c.cost);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.hasTag(customTag.RANGED)) {
                c.setCostForTurn(c.cost);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.hasTag(customTag.RANGED)) {
                c.setCostForTurn(c.cost);
            }
        }
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (card.hasTag(customTag.RANGED)) {
            int oriCost = card.costForTurn;
            int hpCost = oriCost * 2;
            int tempCost = Math.min(EnergyPanel.totalCount, oriCost);

            if (p.currentHealth > hpCost) {
                p.damage(new DamageInfo(p, hpCost, DamageInfo.DamageType.HP_LOSS));
            }

            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(tempCost));
        }

        setCost();
    }

    public boolean canUse(AbstractCard card) {
        if (card.hasTag(customTag.RANGED)) {
            int hpCost = (card.costForTurn * 2);
            return AbstractDungeon.player.currentHealth >= hpCost;

        } else {
            return false;
        }
    }

    public AbstractStance makeCopy() {
        return new RangedStance();
    }


    @Override
    public void update() {
        super.update();
    }

    @Override
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}