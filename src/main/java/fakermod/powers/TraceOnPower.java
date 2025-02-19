package fakermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fakermod.ModFile;
import fakermod.util.TexLoader;

public class TraceOnPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ModFile.makeID("TraceOnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AbstractCreature source;

    public TraceOnPower(final AbstractCreature owner, final int amount) {
        ID = POWER_ID;
        name = NAME;

        this.owner = owner;
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
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount > 5) {
            this.amount = 5;
        }

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount <= 0) { this.amount = 0; }
        if (this.amount >= 5) { this.amount = 5; }
        this.description = DESCRIPTIONS[this.amount-1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TraceOnPower(owner, amount);
    }
}