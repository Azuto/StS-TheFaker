package fakermod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import fakermod.ModFile;
import fakermod.tags.customTag;


public class MeleeStance extends AbstractStance {
    public static final String STANCE_ID = ModFile.makeID("MeleeStance");
    private static final StanceStrings stanceStrings = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public static final String NAME = stanceStrings.NAME;
    public static final String[] DESCRIPTIONS = stanceStrings.DESCRIPTION;
    private static long sfxId = -1L;

    //private float damageDealtInStance = 0;
    private float additionalDamage = 0;
    private float lostHP;

    public MeleeStance() {
        ID = STANCE_ID;
        name = NAME;
        lostHP = 0;
        updateDescription();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void onEnterStance() {
        //damageDealtInStance = 0;
        lostHP = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
        additionalDamage = lostHP * 0.1f;

        if (sfxId != -1L) {
            this.stopIdleSfx();
        }

        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));
        updateDescription();
    }

    @Override
    public void onExitStance() {
        //if (damageDealtInStance > 0) {
        //    AbstractDungeon.player.heal((int) (damageDealtInStance * 0.1f));
        //}

        this.stopIdleSfx();
    }

    @Override
    public void atStartOfTurn() {
        lostHP = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
        additionalDamage = lostHP * 0.1f;
        updateDescription();
    }


    @Override
    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }

    @Override
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }

    }


    @Override
    public void onPlayCard(AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    int damageDealt = 0;

                    for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
                        if (action instanceof DamageAction) {
                            DamageAction dmgAction = (DamageAction) action;
                            if (dmgAction.source == AbstractDungeon.player && dmgAction.target != null) {
                                damageDealt += dmgAction.amount;
                            }
                        }
                    }

                    //damageDealtInStance += damageDealt;
                    updateDescription();
                    isDone = true;
                }
            });
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.hasTag(customTag.MELEE)) {
            return damage + additionalDamage;
        }
        return damage;
    }

    public AbstractStance makeCopy() {
        return new MeleeStance();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (int) additionalDamage
                //+ DESCRIPTIONS[1] + (int)(damageDealtInStance * 0.1F)
                //+ DESCRIPTIONS[2] + (int) damageDealtInStance
        ;
    }
}