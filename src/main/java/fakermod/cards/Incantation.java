package fakermod.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import fakermod.actions.ChangeImageAction;
import fakermod.actions.TraceAction;
import fakermod.actions.TraceFractalAction;
import fakermod.cards.extra.*;
import fakermod.cards.projection.Katana;
import fakermod.cards.projection.Sword;
import fakermod.powers.*;

import static fakermod.ModFile.makeCardPath;
import static fakermod.ModFile.makeID;

public class Incantation extends AbstractEasyCard {
    public final static String ID = makeID("Incantation");
    public static final String IMG_C = makeCardPath("IncantationC.png");
    public static final String IMG_O = makeCardPath("IncantationO.png");
    public static final String IMG_M = makeCardPath("IncantationM.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] VERSE = cardStrings.EXTENDED_DESCRIPTION;
    public static final String[] DESCRIPTIONS = new String[]{
            "fakermod:Chant. NL fakermod:Echo [#EFC851]Trace, *Fractal.",
            "fakermod:Chant. NL fakermod:Echo *Steel *Body.",
            "fakermod:Chant. NL fakermod:Project *Sword and *Katana.",

            "fakermod:Chant. NL fakermod:Echo *Trace *On.",
            "fakermod:Chant. NL fakermod:Echo *Withstand *Pain.",
            "Now, fakermod:alter into NL *Unlimited *Blade *Works.",

            "fakermod:Chant. NL fakermod:Echo *Trigger *Off.",
            "fakermod:Chant. NL fakermod:Echo *Withstand *Pain and *Sword *Barrel.",
            "Now, fakermod:alter into NL *Unlimited *Blade *Works.",

            "fakermod:Chant. NL fakermod:Echo *Resolution.",
            "fakermod:Chant. NL fakermod:Echo [#78F200]Withstand [#78F200]Pain+.",
            "Now, fakermod:alter into NL *Unlimited *Blade *Works.",

            "fakermod:Chant. NL Add [#78F200]Forge+ to your hand.",
            "fakermod:Chant. NL Exhaust your draw pile.",
            "Exhaust your discard pile, then fakermod:alter into NL *Tsumukari *Muramasa."
    };

    public Incantation() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
        cardsToPreview = new TraceFractal();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        incantationVerse(p);
        incantationEffect(p);
        updateAllCards(p.hand, this);
        updateAllCards(p.drawPile, this);
        updateAllCards(p.discardPile, this);
        updateAllCards(p.exhaustPile, this);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        AbstractPower power = AbstractDungeon.player.getPower("FakerMod:IncantationPower");
        int stackAmount = (power != null) ? power.amount : 0;
        this.rawDescription = updateDescription(stackAmount);
        initializeDescription();
    }

    private String updateDescription(int stack) {
        AbstractPlayer p = AbstractDungeon.player;
        boolean hasClassCard = p.hasRelic("FakerMod:ClassCard");
        boolean hasRedPendant = p.hasRelic("FakerMod:RedPendant");
        boolean hasResolution = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Resolution"));
        boolean hasForge = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Forge"));
        boolean hasOriginless = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:OriginlessCreationOfSwords"));

        int descOffset = -1;

        if (hasOriginless && hasForge) {
            descOffset = 9;

        } else if (hasClassCard && hasResolution) {
            descOffset = 6;

        } else if (hasRedPendant && hasResolution) {
            descOffset = 3;

        } else if (hasClassCard) {
            descOffset = 6;

        } else if (hasRedPendant) {
            descOffset = 3;
        }

        if (descOffset != -1 && stack >= 3 && stack <= 5) {
            return DESCRIPTIONS[descOffset + stack];
        }

        return DESCRIPTIONS[Math.min(stack, DESCRIPTIONS.length - 1)];
    }

    private void updateAllCards(CardGroup pile, AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        boolean hasPower = p.hasPower("FakerMod:IncantationPower");
        boolean hasClassCard = p.hasRelic("FakerMod:ClassCard");
        boolean hasRedPendant = p.hasRelic("FakerMod:RedPendant");
        boolean hasResolution = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Resolution"));
        boolean hasBraveShine = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:BraveShine"));
        boolean hasForge = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Forge"));
        boolean hasOriginless = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:OriginlessCreationOfSwords"));
        int powerStack = hasPower ? Math.min(p.getPower("FakerMod:IncantationPower").amount, 5) : 0;
        AbstractCard newPreview = null;
        AbstractCard newPreview2 = null;

        if (powerStack == 5) {
            newPreview = new TraceFractal();

        } else if (powerStack == 4) {
            if (hasOriginless && hasForge) {
                newPreview = new TsumukariMuramasa();

            } else if (hasResolution && hasClassCard) {
                newPreview = new UBW_B();

            } else if (hasRedPendant && (hasResolution || hasBraveShine)) {
                newPreview = new UBW_C();

            } else if (hasClassCard) {
                newPreview = new UBW_B();

            } else {
                newPreview = new UBW_A();
            }

        } else if (powerStack == 3) {
            if (hasOriginless && hasForge) {
                newPreview = null;

            } else if (hasResolution && hasClassCard) {
                newPreview = new WithstandPain();
                newPreview.upgrade();

            } else if (hasRedPendant && (hasResolution || hasBraveShine)) {
                newPreview = new WithstandPain();
                newPreview2 = new SwordBarrel();

            } else if (hasClassCard) {
                newPreview = new WithstandPain();
                newPreview.upgrade();

            } else {
                newPreview = new WithstandPain();
            }

        } else if (powerStack == 2) {
            if (hasOriginless && hasForge) {
                newPreview = new Forge();
                newPreview.upgrade();

            } else if (hasResolution && hasClassCard) {
                newPreview = new Resolution();

            } else if (hasRedPendant && (hasResolution || hasBraveShine)) {
                newPreview = new TriggerOff();

            } else if (hasClassCard) {
                newPreview = new Resolution();

            } else {
                newPreview = new TraceOn();
            }

        } else if (powerStack == 1) {
            newPreview = new Sword();
            newPreview2 = new Katana();

        } else {
            cardsToPreview = null;
            newPreview = new SteelBody();
        }

        for (AbstractCard c : pile.group) {
            if (c.cardID.equals(card.cardID)) {
                MultiCardPreview.clear(c);
                if (newPreview != null && newPreview2 != null) {
                    MultiCardPreview.add(c, newPreview, newPreview2);

                } else if (newPreview != null) {
                    MultiCardPreview.add(c, newPreview);
                }

                if (hasOriginless && hasForge) {
                    loadCardImage(IMG_M);
                    this.addToBot(new ChangeImageAction());

                } else if (hasRedPendant && (hasResolution || hasBraveShine)) {
                    loadCardImage(IMG_C);
                    this.addToBot(new ChangeImageAction());

                } else if (hasClassCard) {
                    loadCardImage(IMG_O);
                    this.addToBot(new ChangeImageAction());
                }
            }
        }
    }

    private void incantationEffect(AbstractPlayer p) {
        boolean hasPower = p.hasPower("FakerMod:IncantationPower");
        boolean hasClassCard = p.hasRelic("FakerMod:ClassCard");
        boolean hasRedPendant = p.hasRelic("FakerMod:RedPendant");
        boolean hasResolution = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Resolution"));
        boolean hasBraveShine = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:BraveShine"));
        boolean hasForge = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Forge"));
        boolean hasOriginless = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:OriginlessCreationOfSwords"));
        int powerStack = hasPower ? Math.min(p.getPower("FakerMod:IncantationPower").amount, 5) : 0;

        this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));

        if (powerStack == 5) {
            if (hasOriginless && hasForge) {
                while (!p.discardPile.isEmpty()) {
                    p.discardPile.moveToExhaustPile(p.discardPile.getTopCard());
                }
                AbstractCard o = new TsumukariMuramasa().makeCopy();
                o.setCostForTurn(0);
                this.addToBot(new MakeTempCardInHandAction(o, 1));

            } else if (hasResolution && hasClassCard) {
                this.addToBot(new MakeTempCardInHandAction(new UBW_B(), 1));

            } else if (hasClassCard) {
                this.addToBot(new MakeTempCardInHandAction(new UBW_B(), 1));

            } else if (hasRedPendant && (hasResolution || hasBraveShine)) {
                this.addToBot(new MakeTempCardInHandAction(new UBW_C(), 1));

            } else {
                this.addToBot(new MakeTempCardInHandAction(new UBW_A(), 1));
            }

            purgeOnUse = true;

        } else if (powerStack == 4) {
            if (hasOriginless && hasForge) {
                while (!p.drawPile.isEmpty()) {
                    p.drawPile.moveToExhaustPile(p.drawPile.getTopCard());
                }

            } else if (hasResolution && hasClassCard) {
                this.addToBot(new ApplyPowerAction(p, p, new WithstandPainPower(p, p, 3, true), 3));

            } else if (hasRedPendant && (hasResolution || hasBraveShine)) {
                this.addToBot(new ApplyPowerAction(p, p, new WithstandPainPower(p, p, 2, false), 2));
                this.addToBot(new ApplyPowerAction(p, p, new SwordBarrelPower(p, p, 2, false)));

            } else if (hasClassCard) {
                this.addToBot(new ApplyPowerAction(p, p, new WithstandPainPower(p, p, 3, true), 3));

            } else {
                this.addToBot(new ApplyPowerAction(p, p, new WithstandPainPower(p, p, 2, false), 2));
            }
        } else if (powerStack == 3) {
            if (hasOriginless && hasForge) {
                AbstractCard f = new Forge();
                f.upgrade();
                this.addToBot(new MakeTempCardInHandAction(f, 1));

            } else if (hasResolution && hasClassCard) {
                this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 2), 2));
                this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, 2), 2));
                this.addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, 2), 2));

            } else if (hasRedPendant && (hasResolution || hasBraveShine)) {
                this.addToBot(new ApplyPowerAction(p, p, new TriggerOffPower(p, p, 1)));

            } else if (hasClassCard) {
                this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 2), 2));
                this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, 2), 2));
                this.addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, 2), 2));

            } else {
                this.addToBot(new ApplyPowerAction(p, p, new TraceOnPower(p, 1), 1));
            }

        } else if (powerStack == 2) {
            this.addToBot(new MakeTempCardInHandAction(new Sword(), 1));
            this.addToBot(new MakeTempCardInHandAction(new Katana(), 1));

        } else if (powerStack == 1) {
            this.addToBot(new GainBlockAction(p, p, 7));
            this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, 1), 1));

        } else {
            this.addToBot(new TraceFractalAction(false));
        }
    }

    private void incantationVerse(AbstractPlayer p) {
        boolean hasPower = p.hasPower("FakerMod:IncantationPower");
        boolean hasClassCard = p.hasRelic("FakerMod:ClassCard");
        boolean hasRedPendant = p.hasRelic("FakerMod:RedPendant");
        boolean hasResolution = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Resolution"));
        boolean hasBraveShine = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:BraveShine"));
        boolean hasForge = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Forge"));
        boolean hasOriginless = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:OriginlessCreationOfSwords"));
        int powerStack = hasPower ? Math.min(p.getPower("FakerMod:IncantationPower").amount, 5) : 0;
        int verseOffset;

        // Muramasa Route - Originless 1, Forge 1
        if (hasOriginless && hasForge) {
            verseOffset = 12;
            this.addToBot(new ChangeImageAction());
            this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));

        } else if (powerStack >= 3){
            // UBW Route B v2 - Resolution 1, Class Card 1
            if (hasClassCard && hasResolution) {
                verseOffset = 3;
                this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));


            // UBW Route C - Resolution 1, Red Pendant 0
            } else if (hasRedPendant && (hasResolution || hasBraveShine)) {
                verseOffset = 6;
                this.addToBot(new ChangeImageAction());
                this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));


            // UBW Route B - Resolution 0, Class Card 1
            } else if (hasClassCard) {
                verseOffset = 3;
                this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));
            }

            // UBW Route A
        } else {
            verseOffset = 0;
            this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));
        }
    }

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upp() { }
}