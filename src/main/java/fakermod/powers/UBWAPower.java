package fakermod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.BladeDance;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fakermod.ModFile;
import fakermod.cards.projection.*;
import fakermod.tags.customTag;
import fakermod.util.TexLoader;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class UBWAPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ModFile.makeID("UBWAPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AbstractCreature source;

    public UBWAPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        type = PowerType.BUFF;

        this.owner = owner;

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

    public static AbstractCard returnRandomProjection() {
        ArrayList<AbstractCard> list = new ArrayList();
        list.add(new KanshouBakuya());
        list.add(new Sword());
        list.add(new Katana());
        list.add(new BladeDance());
        list.add(new KanshouBakuyaOveredge());
        list.add(new RhoAias());
        list.add(new Hrunting());
        list.add(new Caladbolg());
        list.add(new ProjectedCaliburn());
        list.add(new RuleBreaker());
        list.add(new NineLives());

        return list.get(cardRandomRng.random(list.size() - 1));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractCard c = returnRandomProjection().makeCopy();
        if (!card.hasTag(customTag.PROJECTION)
                && card.type == AbstractCard.CardType.ATTACK
                && AbstractDungeon.player.hasPower(POWER_ID)) {
            c.costForTurn = 0;
            this.addToBot(new MakeTempCardInHandAction(c, 1));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new UBWAPower(owner);
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) { }
}