package fakermod.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import fakermod.cards.extra.Ideals;

import static fakermod.ModFile.makeID;

public class Recollection extends AbstractEasyCard {
    public final static String ID = makeID("Recollection");
 
    public Recollection() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(p, magicNumber));
        if (upgraded) {
            FleetingField.fleeting.set(this, true);
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new Ideals(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        }
    }

    @Override
    public void upp() {
        cardsToPreview = new Ideals();
    }
}