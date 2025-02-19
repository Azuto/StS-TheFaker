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
import com.megacrit.cardcrawl.powers.ThornsPower;
import fakermod.actions.ChangeImageAction;
import fakermod.actions.TraceFractalAction;
import fakermod.cards.extra.TsumukariMuramasa;
import fakermod.cards.extra.UBW_A;
import fakermod.cards.extra.UBW_B;
import fakermod.cards.extra.UBW_C;
import fakermod.cards.projection.Katana;
import fakermod.cards.projection.Sword;
import fakermod.powers.IncantationPower;
import fakermod.powers.TraceOnPower;
import fakermod.powers.WithstandPainPower;

import static fakermod.ModFile.makeCardPath;
import static fakermod.ModFile.makeID;

public class Incantation extends AbstractEasyCard {
    public final static String ID = makeID("Incantation");
    public static final String IMG_C = makeCardPath("IncantationC.png");
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
        if (AbstractDungeon.player != null &&
                AbstractDungeon.player.masterDeck.contains(AbstractDungeon.player.masterDeck.findCardById("FakerMod:Forge")) &&
                AbstractDungeon.player.masterDeck.contains(AbstractDungeon.player.masterDeck.findCardById("FakerMod:OriginlessCreationOfSwords"))) {

            if (stack == 5) { return DESCRIPTIONS[7]; }
            if (stack == 4) { return DESCRIPTIONS[6]; }
        }

        return DESCRIPTIONS[Math.min(stack, DESCRIPTIONS.length - 1)];
    }

    private void updateAllCards(CardGroup pile, AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        boolean hasPower = p.hasPower("FakerMod:IncantationPower");
        boolean hasRelic = p.hasRelic("FakerMod:ClassCard");
        boolean hasResolution = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Resolution"));
        boolean hasForge = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Forge"));
        boolean hasOriginless = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:OriginlessCreationOfSwords"));
        int powerStack = hasPower ? Math.min(p.getPower("FakerMod:IncantationPower").amount, 5) : 0;
        AbstractCard newPreview;

        if (powerStack == 5) {
            newPreview = new TraceFractal();

        } else if (powerStack == 4 && hasOriginless && hasForge) {
            newPreview = new TsumukariMuramasa();

        } else if (powerStack == 4 && hasResolution && hasRelic) {
            newPreview = new UBW_B();

        } else if (powerStack == 4 && hasRelic) {
            newPreview = new UBW_B();

        } else if (powerStack == 4 && hasResolution) {
            newPreview = new UBW_C();

        } else if (powerStack == 4) {
            newPreview = new UBW_A();

        } else if (powerStack == 3) {
            newPreview = new WithstandPain();

        } else if (powerStack == 2) {
            newPreview = new TraceOn();

        } else if (powerStack == 1) {
            newPreview = null;

        } else {
            newPreview = new SteelBody();
        }

        for (AbstractCard c : pile.group) {
            if (c.cardID.equals(card.cardID)) {
                if (newPreview != null) {
                    c.cardsToPreview = newPreview.makeCopy();
                } else {
                    c.cardsToPreview = null;
                }

                if (powerStack == 2) {
                    MultiCardPreview.clear(c);

                } else if (powerStack == 1) {
                    MultiCardPreview.add(c, new Sword(), new Katana());
                }

                if (hasOriginless && hasForge) {
                    loadCardImage(IMG_M);

                } else if (hasResolution) {
                    loadCardImage(IMG_C);
                }
            }
        }
    }

    private void incantationEffect(AbstractPlayer p) {
        boolean hasPower = p.hasPower("FakerMod:IncantationPower");
        boolean hasRelic = p.hasRelic("FakerMod:ClassCard");
        boolean hasResolution = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Resolution"));
        boolean hasForge = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Forge"));
        boolean hasOriginless = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:OriginlessCreationOfSwords"));
        AbstractCard o = new TsumukariMuramasa().makeCopy();
        int powerStack = hasPower ? Math.min(p.getPower("FakerMod:IncantationPower").amount, 5) : 0;


        // Muramasa Route - Originless 1, Forge 1
        if (powerStack == 5 && hasOriginless && hasForge) {
            for (int i = 0; i < p.discardPile.group.size(); i++) {
                p.discardPile.moveToExhaustPile(p.discardPile.getTopCard());
            }

            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));
            o.setCostForTurn(0);
            this.addToBot(new MakeTempCardInHandAction(o, 1));
            purgeOnUse = true;

        } else if (powerStack == 4 && hasOriginless && hasForge) {
            for (int i = 0; i < p.drawPile.group.size(); i++) {
                p.drawPile.moveToExhaustPile(p.drawPile.getTopCard());
            }

            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));


        // UBW Route B v2 - Resolution 1, Relic 1
        } else if (powerStack == 5 && hasResolution && hasRelic) {
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));
            this.addToBot(new MakeTempCardInHandAction(new UBW_B(), 1));
            purgeOnUse = true;

        } else if (powerStack == 4 && hasResolution && hasRelic) {
            this.addToBot(new ApplyPowerAction(p, p, new WithstandPainPower(p, p, magicNumber, upgraded), magicNumber));
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));


        // UBW Route B - Resolution 0, Relic 1
        } else if (powerStack == 5 && hasRelic) {
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));
            this.addToBot(new MakeTempCardInHandAction(new UBW_B(), 1));
            purgeOnUse = true;

        } else if (powerStack == 4 && hasRelic) {
            this.addToBot(new ApplyPowerAction(p, p, new WithstandPainPower(p, p, magicNumber, upgraded), magicNumber));
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));


        // UBW Route C - Resolution 1, Relic 0
        } else if (powerStack == 5 && hasResolution) {
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));
            this.addToBot(new MakeTempCardInHandAction(new UBW_C(), 1));
            purgeOnUse = true;

        } else if (powerStack == 4 && hasResolution) {
            this.addToBot(new ApplyPowerAction(p, p, new WithstandPainPower(p, p, magicNumber, upgraded), magicNumber));
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));


        // UBW Route A - Resolution 0, Relic 0
        } else if (powerStack == 5) {
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));
            this.addToBot(new MakeTempCardInHandAction(new UBW_A(), 1));
            purgeOnUse = true;

        } else if (powerStack == 4) {
            this.addToBot(new ApplyPowerAction(p, p, new WithstandPainPower(p, p, magicNumber, upgraded), magicNumber));
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));


        // Base for all routes
        } else if (powerStack == 3) {
            this.addToBot(new ApplyPowerAction(p, p, new TraceOnPower(p, magicNumber), magicNumber));
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));

        } else if (powerStack == 2) {
            this.addToBot(new MakeTempCardInHandAction(new Sword(), 1));
            this.addToBot(new MakeTempCardInHandAction(new Katana(), 1));
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));

        } else if (powerStack == 1) {
            this.addToBot(new GainBlockAction(p, p, 7));
            this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber), magicNumber));
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));

        } else {
            this.addToBot(new TraceFractalAction(false));
            this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, magicNumber), magicNumber));
        }
    }

    private void incantationVerse(AbstractPlayer p) {
        boolean hasPower = p.hasPower("FakerMod:IncantationPower");
        boolean hasRelic = p.hasRelic("FakerMod:ClassCard");
        boolean hasResolution = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Resolution"));
        boolean hasForge = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:Forge"));
        boolean hasOriginless = p.masterDeck.contains(p.masterDeck.findCardById("FakerMod:OriginlessCreationOfSwords"));
        int powerStack = hasPower ? Math.min(p.getPower("FakerMod:IncantationPower").amount, 5) : 0;
        int verseOffset;

        // Muramasa Route - Originless 1, Forge 1
        if (hasOriginless && hasForge) {
            verseOffset = 12;
            this.addToBot(new ChangeImageAction());
            this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));

        } else {

            // UBW Route B v2 - Resolution 1, Relic 1
            if (hasResolution && hasRelic) {
                verseOffset = 3;
                this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));


            // UBW Route C - Resolution 1, Relic 0
            } else if (hasResolution) {
                verseOffset = 6;
                this.addToBot(new ChangeImageAction());
                this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));

            // UBW Route B - Resolution 0, Relic 1
            } else if (hasRelic) {
                verseOffset = 3;
                this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));


            // UBW Route A - Resolution 0, Relic 0
            } else {
                verseOffset = 0;
                this.addToBot(new TalkAction(true, VERSE[powerStack + verseOffset], 6.0f, 4.0f));
            }
        }
    }

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upp() { }
}