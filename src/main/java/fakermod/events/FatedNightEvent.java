package fakermod.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import fakermod.monsters.Assassin;
import fakermod.monsters.Lancer;

import java.util.ArrayList;

import static fakermod.ModFile.*;

public class FatedNightEvent extends PhasedEvent {
    public static final String ID = makeID("FatedNightEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    public static final String IMG = makeEventPath("FatedNightEvent0.png");

    public FatedNightEvent() {
        super(ID, NAME, IMG);
        boolean hasIdeals = AbstractDungeon.player.masterDeck.findCardById("FakerMod:Ideals") != null;

        registerPhase("Intro", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0], (t) -> {
                    this.imageEventText.loadImage(makeEventPath("FatedNightEvent1.png"));
                    AbstractRelic relic = RelicLibrary.getRelic("FakerMod:CommandSeal").makeCopy();
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, relic);
                    transitionKey("[Run]");
                })

                .addOption(new TextPhase.OptionInfo(OPTIONS[1])
                    .enabledCondition(() -> hasIdeals, OPTIONS[3]), ((t) -> {
                        this.imageEventText.loadImage(makeEventPath("FatedNightEvent2.png"));
                        dissolveIdeals();
                        AbstractRelic relic = RelicLibrary.getRelic("FakerMod:ClassCard").makeCopy();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, relic);
                        transitionKey("[Fight]");
                    }))
                .addOption(OPTIONS[2], (t) -> {
                    this.imageEventText.loadImage(makeEventPath("FatedNightEvent3.png"));
                    transitionKey("Leave.");
                })
        );

        registerPhase("[Run]", new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[4], (t) -> {
                    this.imageEventText.loadImage(makeEventPath("FatedNightEvent0.png"));
                    transitionKey("FightLancer");
                })
        );

        registerPhase("[Fight]", new TextPhase(DESCRIPTIONS[2])
                .addOption(OPTIONS[4], (t) -> {
                    this.imageEventText.loadImage(makeEventPath("FatedNightEvent0.png"));
                    transitionKey("FightAssassin");
                })
        );
        registerPhase("FightLancer", new CombatPhase(Lancer.ID)
                .addRewards(true, (room) -> {
                    room.addGoldToRewards(100);
                })
                .setNextKey("Continue.")
        );

        registerPhase("FightAssassin", new CombatPhase(Assassin.ID)
                .addRewards(true, (room) -> {
                    room.addGoldToRewards(100);
                })
                .setNextKey("Continue.")
        );

        registerPhase("Continue.", new TextPhase(DESCRIPTIONS[4])
                .addOption(OPTIONS[2], (t) -> {
                    this.openMap();
                })
        );

        registerPhase("Leave.", new TextPhase(DESCRIPTIONS[3])
                .addOption(OPTIONS[2], (t) -> {
                    this.openMap();
                })
        );

        transitionKey("Intro");
    }

    private void dissolveIdeals() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        int i;
        for (i = 0; i < masterDeck.size(); i++) {
            AbstractCard c = masterDeck.get(i);
            if (c.equals(AbstractDungeon.player.masterDeck.findCardById("FakerMod:Ideals"))) {
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                AbstractDungeon.player.masterDeck.removeCard(c);
            }
        }
    }
}
/*
    public FatedNightEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
        imageEventText.setDialogOption(OPTIONS[0]); // Run - Obtain Command Seals, battle
        if (AbstractDungeon.player.masterDeck.findCardById("FakerMod:Ideals") != null) {
            imageEventText.setDialogOption(OPTIONS[1], new Ideals()); // Fight - Obtain Class Card, battle
        } else {
            imageEventText.setDialogOption(OPTIONS[3], true, new Ideals()); // Locked
        }
        imageEventText.setDialogOption(OPTIONS[2]); // Leave - Nothing
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0: // Run
                        imageEventText.loadImage(makeEventPath("FatedNightEvent1.png"));
                        imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        imageEventText.updateDialogOption(0, OPTIONS[4]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        AbstractRelic addCommandSeal = RelicLibrary.getRelic("FakerMod:CommandSeal").makeCopy();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), addCommandSeal);
                        break;

                    case 1: // Fight
                        imageEventText.loadImage(makeEventPath("FatedNightEvent2.png"));
                        imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        imageEventText.updateDialogOption(0, OPTIONS[4]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 2;

                        dissolveIdeals();
                        AbstractRelic addClassCard = RelicLibrary.getRelic("FakerMod:ClassCard").makeCopy();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), addClassCard);
                        break;

                    case 2: // Leave
                        imageEventText.loadImage(makeEventPath("FatedNightEvent3.png"));
                        imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        imageEventText.updateDialogOption(0, OPTIONS[2]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 3;
                        break;
                }
                break;

            case 1:
                if (i == 0) {
                    screenNum = 3;
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(Lancer.ID);
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().eliteTrigger = true;
                    this.enterCombatFromImage();
                    AbstractDungeon.lastCombatMetricKey = Lancer.ID;
                    return;
                }
                break;

            case 2:
                if (i == 0) {
                    screenNum = 3;
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(Assassin.ID);
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.getCurrRoom().eliteTrigger = true;
                    this.enterCombatFromImage();
                    AbstractDungeon.lastCombatMetricKey = Assassin.ID;
                    return;
                }
                break;

            case 3:
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE; // Mark the room as complete
                AbstractDungeon.overlayMenu.proceedButton.show(); // Ensure the Proceed button is available
                AbstractDungeon.closeCurrentScreen(); // Closes any lingering UI
                openMap();
                break;
        }
    }

    @Override
    public void reopen() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMPLETE) {
            openMap();
        } else {
            super.reopen();
        }
    }
}

 */
