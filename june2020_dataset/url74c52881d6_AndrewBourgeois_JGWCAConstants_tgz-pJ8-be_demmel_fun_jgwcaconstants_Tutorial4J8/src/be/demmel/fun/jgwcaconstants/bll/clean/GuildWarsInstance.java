package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;

public class GuildWarsInstance {
    private final GWCAOperations gwcaOperations;
    private final HeroPanel heroPanel;
    private final EffectsMonitor effectsMonitor;
    private final InventoryPanel inventoryPanel;
    private final PartyPanel partyPanel;
    private final Player player;
    private final QuestLogPanel questLogPanel;
    private final RemainingCommands remainingCommands;
    private final SkillBar skillBar;
    private final SkillsPanel skillsPanel;
    private final StoragePanel storagePanel;
    private final TraderPanel traderPanel;

    public GuildWarsInstance(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
        this.heroPanel = new HeroPanel(gwcaOperations);
        this.effectsMonitor = new EffectsMonitor(gwcaOperations);
        this.inventoryPanel = new InventoryPanel(gwcaOperations);
        this.partyPanel = new PartyPanel(gwcaOperations);
        this.player = new Player(gwcaOperations);
        this.questLogPanel = new QuestLogPanel(gwcaOperations);
        this.remainingCommands = new RemainingCommands(gwcaOperations);
        this.skillBar = new SkillBar(gwcaOperations);
        this.skillsPanel = new SkillsPanel(gwcaOperations);
        this.storagePanel = new StoragePanel(gwcaOperations);
        this.traderPanel = new TraderPanel(gwcaOperations);
    }

    public EffectsMonitor getEffectsMonitor() {
        return effectsMonitor;
    }

    public GWCAOperations getGwcaOperations() {
        return gwcaOperations;
    }

    public HeroPanel getHeroPanel() {
        return heroPanel;
    }

    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }

    public PartyPanel getPartyPanel() {
        return partyPanel;
    }

    public Player getPlayer() {
        return player;
    }

    public QuestLogPanel getQuestLogPanel() {
        return questLogPanel;
    }

    public RemainingCommands getRemainingCommands() {
        return remainingCommands;
    }

    public SkillBar getSkillBar() {
        return skillBar;
    }

    public SkillsPanel getSkillsPanel() {
        return skillsPanel;
    }

    public StoragePanel getStoragePanel() {
        return storagePanel;
    }

    public TraderPanel getTraderPanel() {
        return traderPanel;
    }
}
