package fakermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fakermod.ModFile;
import fakermod.util.TexLoader;

public class ManaLinkPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ModFile.makeID("ManaLinkPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AbstractCreature source;
    public int effect;

    public ManaLinkPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final int effect) {
        name = NAME;
        ID = POWER_ID;
        type = PowerType.BUFF;
        isTurnBased = true;

        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.effect = effect;

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
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        updateDescription();
    }

    @Override
    public void onEnergyRecharge() {
        AbstractDungeon.player.gainEnergy(this.effect);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + ". NL #b" + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }

    @Override
    public AbstractPower makeCopy() {
        return new ManaLinkPower(owner, source, amount, effect);
    }
}