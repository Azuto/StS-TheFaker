package fakermod.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fakermod.cards.extra.TsumukariMuramasa;
import fakermod.cards.projection.Katana;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static fakermod.ModFile.makeID;

public class Forge extends AbstractEasyCard {
    public final static String ID = makeID("Forge");

    public static AbstractCard returnRandomKatana() {
        ArrayList<AbstractCard> list = new ArrayList();
        list.add(new Katana());
        list.add(new CursedKatana());
        list.add(new BlessedKatana());
        list.add(new DestructiveKatana());
        list.add(new PurifyingKatana());
        list.add(new MyoujingiriMuramasa());
        list.add(new TsumukariMuramasa());

        return list.get(cardRandomRng.random(list.size() - 1));
    }

    public Forge() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = returnRandomKatana().makeCopy();

        this.addToBot(new ExhaustAction(1, false));
        this.addToBot(new MakeTempCardInHandAction(c, 1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}