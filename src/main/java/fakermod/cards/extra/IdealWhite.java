package fakermod.cards.extra;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import fakermod.CharacterFile;
import fakermod.cards.AbstractEasyCard;
import fakermod.powers.IdealWhitePower;

import static fakermod.ModFile.makeID;

public class IdealWhite extends AbstractEasyCard {
    public final static String ID = makeID("IdealWhite");

    public IdealWhite() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE, CharacterFile.Enums.FAKERE_COLOR);
        baseMagicNumber = magicNumber = 1;
        setDisplayRarity(CardRarity.RARE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new IdealWhitePower(p, magicNumber)));
        if (upgraded) {
            FleetingField.fleeting.set(this, true);
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new MindOfSteel(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        }
    }

    public AbstractCard makeCopy() { return new IdealWhite(); }

    public void upp() {
        cardsToPreview = new MindOfSteel();
    }
}