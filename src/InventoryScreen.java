import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.LinkedList;

import core.GameConstants;
import core.Occupant;
import core.Slot;
import items.Gear;
import items.Item;
import items.Loot;
import items.Potion;
import props.Chest;

public class InventoryScreen {
	private Player player;
	private TextureCache textureCache;
	private Font font;
	private Occupant context = null;

	private int contextPanelX, playerPanelX, panelY, panelWidth, iconSize = 16;
	private int potionStartY;
	private int statLine = 9;
	private int itemHeight = statLine * 9 + 4;

	public Occupant getContext() {
		return context;
	}

	public void setContext(Occupant context) {
		this.context = context;
	}

	public InventoryScreen(Player player, TextureCache textureCache) {
		this.player = player;
		this.textureCache = textureCache;
		loadFont();
	}

	public void draw(Graphics g) {
	    g.setFont(font);
	    g.setColor(Color.DARK_GRAY);
	    g.fillRect(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
	    panelWidth  = (int)(GameConstants.WINDOW_WIDTH * 0.35);
	    int panelHeight = (int)(GameConstants.WINDOW_HEIGHT * 0.8);
	    panelY = (GameConstants.WINDOW_HEIGHT - panelHeight) / 2;
	    playerPanelX  = 0;
	    contextPanelX = 0;

	    if(context != null) {
	        playerPanelX  = GameConstants.WINDOW_WIDTH/2 + 20;
	        contextPanelX = GameConstants.WINDOW_WIDTH/2 - panelWidth - 20;
	    } else {
	        playerPanelX  = (GameConstants.WINDOW_WIDTH - panelWidth)/2;
	    }

	    if(context != null) {
	        g.setColor(Color.BLACK);
	        g.fillRect(contextPanelX, panelY, panelWidth, panelHeight);

	        g.setColor(Color.GRAY);
	        g.drawString("Container", contextPanelX + 10, panelY + 15);

	        LinkedList<Item> items = ((Chest)context).open();
	        int startY = panelY + 30;
	        int textX = contextPanelX + 10 + iconSize + 6;

	        for(int i = 0; i < items.size(); i++) {
	            Item item = items.get(i);
	            int y = startY + i * itemHeight;
	            g.drawImage(textureCache.getImg(item.getTexture()), contextPanelX + 10, y, iconSize, iconSize, null);
	            g.setColor(Color.WHITE);
	            g.drawString(item.getName(), textX, y + 9);
	            if(item instanceof Loot) {
	                g.setColor(Color.YELLOW);
	                g.drawString("Value: " + item.getValue() + " G", textX, y + 9 + statLine);
	            } else if(item instanceof Potion) {
	                g.setColor(Color.GREEN);
	                g.drawString(((Potion)item).getEffectText(), textX, y + 9 + statLine);
	            } else {
	                core.Stats s = item.getStats();
	                g.setColor(Color.GRAY);
	                g.drawString("HP:" + s.maxHP, textX, y + 9 + statLine);
	                g.drawString("DMG:" + s.damageLow + "-" + s.damageHigh, textX, y + 9 + statLine * 2);
	                g.drawString("ACC:" + s.accuracy, textX, y + 9 + statLine * 3);
	                g.drawString("CRIT:" + s.critChance + "%", textX, y + 9 + statLine * 4);
	                g.drawString("CRITx:" + s.critModifier, textX, y + 9 + statLine * 5);
	                g.drawString("DODGE:" + s.dodgeChance, textX, y + 9 + statLine * 6);
	                g.drawString("GOLD:" + s.goldModifier, textX, y + 9 + statLine * 7);
	            }
	        }
	    }

	    g.setColor(Color.BLACK);
	    g.fillRect(playerPanelX, panelY, panelWidth, panelHeight);

	    int slotSize = panelWidth / 5;
	    int gap = slotSize / 4;
	    int centerX = playerPanelX + (panelWidth - slotSize)/2;
	    int row1Y = panelY + gap * 2;
	    int row2Y = row1Y + slotSize + gap;

	    g.setColor(Color.GRAY);

	    g.drawRect(centerX, row1Y, slotSize, slotSize);
	    g.drawString("Headgear", centerX, row1Y);

	    g.drawRect(centerX - slotSize - gap, row2Y, slotSize, slotSize);
	    g.drawString("Weapon", centerX - slotSize - gap, row2Y);

	    g.drawRect(centerX, row2Y, slotSize, slotSize);
	    g.drawString("Armor", centerX, row2Y);

	    g.drawRect(centerX + slotSize + gap, row2Y, slotSize, slotSize);
	    g.drawString("Charm", centerX + slotSize + gap, row2Y);

    	for(Item gear : player.getEquipped().values()) {
    	    if(gear == null) continue;
    	    switch(gear.getSlot()) {
    	    case HELMET:
    	    	g.drawImage(textureCache.getImg(gear.getTexture()), centerX, row1Y, slotSize, slotSize, null);
    	    	break;
    	    case ARMOR:
    	    	g.drawImage(textureCache.getImg(gear.getTexture()), centerX, row2Y, slotSize, slotSize, null);
    	    	break;
    	    case CHARM:
    	    	g.drawImage(textureCache.getImg(gear.getTexture()), centerX + slotSize + gap, row2Y, slotSize, slotSize, null);
    	    	break;
    	    case WEAPON:
    	    	g.drawImage(textureCache.getImg(gear.getTexture()), centerX - slotSize - gap, row2Y, slotSize, slotSize, null);
    	    	break;
    	    default:
    	    	break;
    	    }
    	}

    	core.Stats eff = player.getEffectiveStats();
    	int statsX = playerPanelX + 10;
    	int statsY = row2Y + slotSize + gap + 10;
    	g.setColor(Color.GRAY);
    	g.drawString("-- Stats --", statsX, statsY);
    	g.setColor(Color.WHITE);
    	g.drawString("HP: " + player.getHitPoints() + "/" + eff.maxHP, statsX, statsY + statLine);
    	g.drawString("DMG: " + eff.damageLow + "-" + eff.damageHigh, statsX, statsY + statLine * 2);
    	g.drawString("ACC: " + eff.accuracy, statsX, statsY + statLine * 3);
    	g.drawString("CRIT: " + eff.critChance + "% x" + eff.critModifier, statsX, statsY + statLine * 4);
    	g.drawString("DODGE: " + eff.dodgeChance, statsX, statsY + statLine * 5);
    	g.drawString("GOLD: x" + eff.goldModifier, statsX, statsY + statLine * 6);

    	int lootValue = 0;
    	for(int i = 0; i < player.getLootBag().size(); i++) {
    		lootValue += player.getLootBag().get(i).getValue();
    	}
    	g.setColor(Color.YELLOW);
    	g.drawString("Loot: " + lootValue + " G (" + player.getLootBag().size() + " items)", statsX, statsY + statLine * 7 + 4);

    	LinkedList<Potion> potions = player.getPotions();
    	potionStartY = statsY + statLine * 9 + 4;
    	g.setColor(Color.GRAY);
    	g.drawString("-- Potions --", statsX, potionStartY);
    	int potionHeight = statLine * 2 + 4;
    	for(int i = 0; i < potions.size(); i++) {
    		Potion p = potions.get(i);
    		int py = potionStartY + statLine + i * potionHeight;
    		g.drawImage(textureCache.getImg(p.getTexture()), statsX, py - 8, iconSize, iconSize, null);
    		if(p.isActive()) {
    			g.setColor(Color.GREEN);
    			g.drawString(p.getName() + " (" + p.getDuration() + ")", statsX + iconSize + 4, py);
    		} else {
    			g.setColor(Color.WHITE);
    			g.drawString(p.getName(), statsX + iconSize + 4, py);
    		}
    		g.setColor(Color.GRAY);
    		g.drawString(p.getEffectText(), statsX + iconSize + 4, py + statLine);
    	}
	}

	public boolean handleInput(Controller controller) {
		if(controller.isKeyTabPressed()) {
			Controller.getInstance().setKeyTabPressed(false);
			return true;
		}

		if(controller.isClicked()) {
			controller.setClicked(false);
			int mx = controller.getMouseX();
			int my = controller.getMouseY();

			if(context != null) {
				LinkedList<Item> items = ((Chest)context).open();
				int startY = panelY + 30;

				for(int i = 0; i < items.size(); i++) {
					int rowTop = startY + i * itemHeight;
					int rowBot = rowTop + itemHeight;
					if(((mx >= contextPanelX) && (mx <= contextPanelX + panelWidth)) && ((my >= rowTop) && (my < rowBot))) {
						Item picked = items.remove(i);
						if(picked instanceof Loot) {
							player.getLootBag().add((Loot)picked);
							Viewer.playSound(GameConstants.SFX_GOLD[GameConstants.getRand(0, GameConstants.SFX_GOLD.length - 1)]);
						} else if(picked instanceof Potion) {
							player.getPotions().add((Potion)picked);
							Viewer.playSound(GameConstants.SFX_POTION);
						} else {
							Slot slot = picked.getSlot();
							Item old = player.getEquipped().get(slot);
							player.getEquipped().put(slot, picked);
							if(old != null) {
								items.add(old);
							}
							Viewer.playSound(GameConstants.SFX_EQUIP[GameConstants.getRand(0, GameConstants.SFX_EQUIP.length - 1)]);
						}
						break;
					}
				}
			}

			LinkedList<Potion> potions = player.getPotions();
			int potionHeight = statLine * 2 + 4;
			for(int i = 0; i < potions.size(); i++) {
				int py = potionStartY + statLine + i * potionHeight;
				int rowTop = py - 8;
				int rowBot = rowTop + potionHeight;
				if(((mx >= playerPanelX) && (mx <= playerPanelX + panelWidth)) && ((my >= rowTop) && (my < rowBot))) {
					Potion p = potions.get(i);
					if(!p.isActive()) {
						if(p.getDuration() == 0) {
							int healAmt = (int)(player.getMaxHP() * 0.3);
							player.heal(healAmt);
							potions.remove(i);
						} else {
							p.activate(player.getMaxHP(), player.getHitPoints());
						}
						Viewer.playSound(GameConstants.SFX_POTION);
					}
					break;
				}
			}
		}

		return false;
	}

    private void loadFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(GameConstants.FONT)).deriveFont(Font.PLAIN, 8);
        } catch (Exception e) {
            e.printStackTrace();
            font = new Font("Monospaced", Font.PLAIN, 14);
        }
    }
}
