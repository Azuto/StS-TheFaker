package fakermod.cards.extra;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import fakermod.CharacterFile;
import fakermod.ModFile;
import fakermod.actions.ChangeImageAction;
import fakermod.actions.PurgeCardAction;
import fakermod.actions.TraceFractalAction;
import fakermod.cards.AbstractEasyCard;
import fakermod.cards.Incantation;
import fakermod.cards.projection.Katana;
import fakermod.cards.projection.Sword;
import fakermod.powers.IncantationPower;
import fakermod.powers.TraceOnPower;

import static fakermod.ModFile.makeID;

public class BraveShine extends AbstractEasyCard {
    public final static String ID = makeID("BraveShine");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String[] VERSE =  new String[] {
            "I am the bone of my sword.",
            "Steel is my body and fire is my blood.",
            "I have created over a thousand blades."
    };
 
    public BraveShine() {
        super(ID, -2, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        baseDamage = damage = 1; //Increasing
        baseBlock = block = 1; //Stays at 1
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 3;
        cardsToPreview = new Incantation();
        setDisplayRarity(CardRarity.RARE);
        setBannerTexture(ModFile.BANNER_MYTHIC, ModFile.BANNER_MYTHIC_PORTRAIT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public void triggerWhenDrawn() {
        flash();
        this.addToTop(new ChangeImageAction());
        this.addToBot(new GainEnergyAction(secondMagic));
        this.applyIncantationOrTraceOn();
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
                rawDescription = UPGRADE_DESCRIPTION;

            } else if (powerStack == 2) {
                this.addToBot(new TalkAction(true, VERSE[2], 6.0f, 4.0f));
                this.addToBot(new MakeTempCardInHandAction(new Sword(), 1));
                this.addToBot(new MakeTempCardInHandAction(new Katana(), 1));
                this.addToBot(new ApplyPowerAction(p, p, new IncantationPower(p, p, block), block));
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

    public boolean canUpgrade() { return false; }

    public AbstractCard makeCopy() { return new BraveShine(); }

        @Override
    public void upp() { }
}