/**
 * 
 */
package io.github.surfdudeboy.badgebook.command;

import java.util.ArrayList;
import java.util.Set;

import org.spongepowered.api.Game;
import org.spongepowered.api.GameDictionary;
import org.spongepowered.api.GameDictionary.Entry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilderPopulators;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult.Type;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.TextAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.action.HoverAction.ShowText;
import org.spongepowered.api.text.format.TextColors;

import io.github.surfdudeboy.badgebook.BadgeBook;

/**
 * @author Robert Pettit rnpettit
 *
 */
public class BadgesCommand implements CommandExecutor {
	
	

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		src.sendMessage(Text.of("Badge command!"));

		if(src instanceof Player){
			Player p = null;
			p = (Player) src;
			showBadgeBook(p);
		}
		return CommandResult.success();

	}

	private void showBadgeBook(Player p){
		ArrayList<Text> pages = new ArrayList<Text>();
		pages.add(Text.builder("Hello").color(TextColors.GOLD).build());
		Game game = BadgeBook.getGame();
		GameDictionary dict = game.getGameDictionary();
		ItemType badgeType = game.getRegistry().getType(ItemType.class, "pixelmon:cascade_badge").get();
		
//		for(String key : dict.getAll().keySet()){
//			System.out.println(key);
//		}
//		Set<Entry> entrySet = dict.get("pixelmon:cascade_badge");
//		p.sendMessage(Text.of(entrySet.size() + " entries"));
//		Entry[] entryArray = entrySet.toArray(new Entry[0]);
//		GameDictionary.Entry entry = entryArray[0];
		//ItemType badgeType = entry.getType();
		ItemStackSnapshot badge = badgeType.getTemplate();
		//ItemStackSnapshot badge = ItemStack.builder().itemType(badgeType).build().createSnapshot();
		Text page2 = Text.builder("Derp").onHover(TextActions.showItem(badge)).build();
		pages.add(page2);
		//BookView book = BookView.builder().title(Text.of("Title")).author(Text.of("AuthorName")).addPage(page1).build();
		//ItemStack bookStack = ItemStack.builder().build(book.toContainer()).orElse(null);
		Text playerDisplayName = null;
		if(p.getDisplayNameData().displayName().exists()){
			playerDisplayName = p.getDisplayNameData().displayName().get();
		}
		else {
			playerDisplayName = Text.of(p.getName());
		}
		Text title = Text.builder()
				.append(playerDisplayName,
						Text.of("'s Badges"))
				.color(TextColors.GOLD)
				.build();
		ItemStack bookStack = ItemStack.builder()
				.itemType(ItemTypes.WRITTEN_BOOK)
				.add(Keys.DISPLAY_NAME, title)						
				.add(Keys.BOOK_AUTHOR, Text.of("AuthorName"))
				.add(Keys.BOOK_PAGES, pages)
				.build();
		Inventory inv = p.getInventory();
		if(inv == null){
			p.sendMessage(Text.of("inv is null"));
		}
		if(bookStack == null){
			p.sendMessage(Text.of("BookStack is null"));
		}
		Type type = inv.offer(bookStack).getType();
		if(type.equals(Type.FAILURE)){
			p.sendMessage(Text.of("Your inventory is full!"));
		}
		else if(type.equals(Type.CANCELLED) || type.equals(Type.ERROR) || type.equals(Type.UNDEFINED)){
			p.sendMessage(Text.of("Not sure what happened there."));
		}		
	}

}
