package org.arios.net.packet.in;

import org.arios.game.container.Container;
import org.arios.game.container.impl.BankContainer;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.net.packet.IncomingPacket;
import org.arios.net.packet.IoBuffer;

/**
 * Represents the packet to handle an item slot switch.
 * @author 'Vexia
 */
public class SlotSwitchPacket implements IncomingPacket {

	@Override
	public void decode(Player player, int opcode, IoBuffer buffer) {
		if (buffer.opcode() == 79) {
			int interfaceHash = buffer.getIntB();
			int secondSlot = buffer.getLEShort();
			int interfaceHash2 = buffer.getInt();
			int slot = buffer.getLEShort();
			int interfaceId = interfaceHash >> 16;
			int childId = interfaceHash & 0xFF;
			int withInterfaceId = interfaceHash2 >> 16;
			int withChildId = interfaceHash2 & 0xFF;
			Container container = player.getInventory();
			switch (interfaceId) {
			case 762:
				if (withInterfaceId == 762) {
					if (withChildId == 73) {
						container = player.getBank();
						//TODO: Fix client sided part of switching items inside bank container.
					}
					else {
						int tabIndex = BankContainer.getArrayIndex(withChildId);
		                if (tabIndex > -1) {
		                    secondSlot = tabIndex == 10 ? player.getBank().freeSlot() : player.getBank().getTabStartSlot()[tabIndex] + player.getBank().getItemsInTab(tabIndex);
		                    childId = player.getBank().getTabByItemSlot(slot);
		                    if (secondSlot > slot) {
		                        player.getBank().insert(slot, secondSlot - 1);
		                    } else if (slot > secondSlot) {
		                        player.getBank().insert(slot, secondSlot);
		                    }
		                    player.getBank().increaseTabStartSlots(tabIndex);
		                    player.getBank().decreaseTabStartSlots(childId);
		                    player.getBank().setTabConfigurations();
		                    return;
		                }
					}
					break;
				}
				break;
			default:
				switchItem(slot, secondSlot, container, false);
				break;
			}
			return;
		}
		int slot = buffer.getShort();
		int interfaceHash = buffer.getLEInt();
		int secondSlot = buffer.getShortA();
		boolean insert = buffer.get() == 1;
		int interfaceId = interfaceHash >> 16;
		Container container = interfaceId == 762 ? player.getBank() : (interfaceId == 15 || interfaceId == 149 || interfaceId == 763) ? player.getInventory() : null;
		switchItem(slot, secondSlot, container, insert);
	}
	
	/**
	 * Switches an item.
	 * @param slot The slot.
	 * @param secondSlot The slot to switch slots with.
	 * @param container The container.
	 * @param insert If inserting should happen.
	 */
	public void switchItem(int slot, int secondSlot, Container container, boolean insert) {
		if (container == null) {
			return;
		}
		final Item item = container.get(slot);
		final Item second = container.get(secondSlot);
		if (item == null) {
			return;
		}
		if (!insert) {
			container.replace(second, slot);
			container.replace(item, secondSlot);
			if (item != null) {
				item.setIndex(secondSlot);
			}
			if (second != null) {
				second.setIndex(slot);
			}
		} else {
			container.insert(slot, secondSlot);
		}
	}

}
