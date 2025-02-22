package fakermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fakermod.ModFile;
import fakermod.tags.customTag;
import fakermod.util.TexLoader;

public class ArcheryPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ModFile.makeID("ArcheryPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int healAmt;

    public ArcheryPower(final AbstractCreature owner, final int amount, final int healAmt) {
        ID = POWER_ID;
        name = NAME;
        type = PowerType.BUFF;

        this.owner = owner;
        this.amount = amount;
        this.healAmt = healAmt;

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
        this.amount = Math.min(this.amount + stackAmount, 3);
        this.healAmt += stackAmount;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.amount = 3;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(customTag.RANGED)) {
            this.amount--;
        }

        if (this.amount <= 0)  {
            AbstractDungeon.player.heal(this.healAmt);
            this.amount = 3;
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[1] + this.healAmt : DESCRIPTIONS[2] + this.healAmt);

    }

    @Override
    public AbstractPower makeCopy() {
        return new ArcheryPower(owner, amount, healAmt);
    }
}