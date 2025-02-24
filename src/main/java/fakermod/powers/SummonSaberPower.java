package fakermod.powers;

import basemod.devcommands.draw.Draw;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fakermod.ModFile;
import fakermod.actions.ChangeImageAction;
import fakermod.orbs.AP;
import fakermod.tags.customTag;
import fakermod.util.TexLoader;

import java.util.ArrayList;

public class SummonSaberPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ModFile.makeID("SummonSaberPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public AbstractCreature source;

    public SummonSaberPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        Texture normalTexture = TexLoader.getTexture(ModFile.modID + "Resources/images/powers/" + ID.replaceAll(ModFile.modID + ":", "") + "32.png");
        Texture hiDefImage = TexLoader.getTexture(ModFile.modID + "Resources/images/powers/" + ID.replaceAll(ModFile.modID + ":", "") + "84.png");
        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());

        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }

        updateDescription();
    }

    @Override
    public void onEnergyRecharge() {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.player.maxOrbs == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            removeSaberCards();
            this.addToBot(new ChangeImageAction());
            return;
        }

        this.addToBot(new ChangeImageAction());
        this.addToBot(new DrawCardAction(3));
        for (int i = 0; i < p.maxOrbs; i++){
            if (p.hasEmptyOrb()) {
                p.channelOrb(new AP());
            }
        }
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (info.owner != null
                && !AbstractDungeon.player.hasPower("FakerMod:InvisibleAirPower")
                && info.owner != this.owner
                && info.type != DamageInfo.DamageType.HP_LOSS
                && info.type != DamageInfo.DamageType.THORNS
                && damageAmount > 0) {
            this.flash();
            this.addToBot(new DecreaseMaxOrbAction(1));
        }
    }

    @Override
    public void onVictory() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void removeSaberCards() {
        int i;
        ArrayList<AbstractCard> group = AbstractDungeon.player.drawPile.group;
        AbstractPlayer p = AbstractDungeon.player;

        if (p.hasPower("FakerMod:ChivalryPower")) {
            this.addToBot(new RemoveSpecificPowerAction(p, p, "FakerMod:ChivalryPower"));
        }

        for (i = group.size() - 1; i >= 0; --i) {
            AbstractCard card = group.get(i);
            if (card.tags.contains(customTag.SABER)) {
                AbstractDungeon.player.drawPile.removeCard(card);
            }
        }

        group = AbstractDungeon.player.discardPile.group;
        for (i = group.size() - 1; i >= 0; --i) {
            AbstractCard card = group.get(i);
            if (card.tags.contains(customTag.SABER)) {
                AbstractDungeon.player.discardPile.removeCard(card);
            }
        }

        group = AbstractDungeon.player.hand.group;
        for (i = group.size() - 1; i >= 0; --i) {
            AbstractCard card = group.get(i);
            if (card.tags.contains(customTag.SABER)) {
                AbstractDungeon.player.hand.removeCard(card);
            }
        }
    }

    @Override
    public void onRemove() {
        if (AbstractDungeon.player != null) {
            removeSaberCards();
            this.addToBot(new ChangeImageAction());
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SummonSaberPower(owner, source, amount);
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) { }
}