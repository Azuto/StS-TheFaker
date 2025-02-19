package fakermod.cards.extra;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import fakermod.CharacterFile;
import fakermod.actions.RemoveCardFromMasterDeckAction;
import fakermod.cards.AbstractEasyCard;

import static fakermod.ModFile.makeID;

public class Resolution extends AbstractEasyCard {
    public final static String ID = makeID("Resolution");

    public Resolution() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        baseMagicNumber = magicNumber = 2;
        setDisplayRarity(CardRarity.RARE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber), magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, magicNumber), magicNumber));
        if (this.upgraded) { alterResolution(1); }
    }

    @Override
    public void triggerOnExhaust() {
        if (this.upgraded) { alterResolution(2); }
    }

    private void alterResolution(int key){
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals(ID)) {
                this.addToBot(new RemoveCardFromMasterDeckAction(c));
                break;
            }
        }

        if (key == 1) {
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new Arrival(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

        } else if (key == 2 && AbstractDungeon.player.hasRelic("FakerMod:RedPendant")) {
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new BraveShine(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

        } else if (key == 2) {
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new LastStardust(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        }
    }

    public AbstractCard makeCopy() { return new Resolution(); }

        @Override
    public void upp() {
        upgradeMagicNumber(1);
        cardsToPreview = new Arrival();
        purgeOnUse = true;
    }
}