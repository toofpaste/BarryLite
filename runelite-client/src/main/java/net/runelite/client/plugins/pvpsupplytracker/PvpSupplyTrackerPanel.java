package net.runelite.client.plugins.pvpsupplytracker;

import java.util.ArrayList;
import net.runelite.client.game.AsyncBufferedImage;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.loottracker.LootTrackerPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.StackFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

// Maybe create separate trackers for different characters (i.e. log ins)?
class PvpSupplyTrackerPanel extends PluginPanel
{
    private PvpSupplyTrackerPlugin plugin;
    private ItemManager itemManager;
    private boolean hideIgnoredItems = true;

    private final JPanel actionPanel = new JPanel(new BorderLayout());
    private final JPanel summaryPanel = new JPanel(new BorderLayout());
    private final JPanel suppliesPanel = new JPanel();
    private final PluginErrorPanel errorPanel = new PluginErrorPanel();

    // Summary panel components
    private JLabel cashStackIcon = new JLabel();
    private JLabel overallGpLabel = new JLabel();

	private JLabel viewHiddenBtn = new JLabel();

    private static final int ITEMS_PER_ROW = 5;
    private static final ImageIcon VISIBLE_ICON;
    private static final ImageIcon VISIBLE_ICON_HOVER;
    private static final ImageIcon INVISIBLE_ICON;
    private static final ImageIcon INVISIBLE_ICON_HOVER;
    private static final ImageIcon COINS_ICON;
    private static final String HTML_LABEL_TEMPLATE = "<html><body style='color:%s'>%s<span style='color:white'>%s</span></body></html>";

    static
    {
        final BufferedImage visibleImg 	= ImageUtil.getResourceStreamFromClass(LootTrackerPlugin.class, "visible_icon.png");
        final BufferedImage invisibleImg = ImageUtil.getResourceStreamFromClass(LootTrackerPlugin.class, "invisible_icon.png");
        final BufferedImage coinsImg = ImageUtil.getResourceStreamFromClass(PvpSupplyTrackerPlugin.class, "panel_icon.png");

        VISIBLE_ICON = new ImageIcon(visibleImg);
        VISIBLE_ICON_HOVER = new ImageIcon(ImageUtil.alphaOffset(visibleImg, -220));

        INVISIBLE_ICON = new ImageIcon(invisibleImg);
        INVISIBLE_ICON_HOVER = new ImageIcon(ImageUtil.alphaOffset(invisibleImg, -220));

        COINS_ICON = new ImageIcon(coinsImg);
    }

    PvpSupplyTrackerPanel(final PvpSupplyTrackerPlugin plugin, final ItemManager itemManager)
    {
        this.itemManager = itemManager;
        this.plugin = plugin;

        // Set-up background
        setBorder(new EmptyBorder(6, 6, 6, 6));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());
        setVisible(true);

        // Main container panel that holds other panels
        final JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        add(panelContainer, BorderLayout.NORTH);

        // Create ignore eye panel
        setUpActionPanel(actionPanel);

        // Create overall session data panel
        setUpSummaryPanel(summaryPanel);

        // Add panels to the panelContainer
        panelContainer.add(actionPanel);
        panelContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        panelContainer.add(summaryPanel);
        panelContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        panelContainer.add(suppliesPanel); // Added dynamically later on

        // Add error pane
        errorPanel.setContent("Supply Tracker", "You have not consumed any supplies.");
        add(errorPanel);
    }

    private void setUpActionPanel(JPanel actionPanel)
    {
        viewHiddenBtn.setIcon(INVISIBLE_ICON);
        viewHiddenBtn.setToolTipText("Show ignored items");
        viewHiddenBtn.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent mouseEvent)
            {
				changeItemHiding(!hideIgnoredItems);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent)
            {
                viewHiddenBtn.setIcon(hideIgnoredItems ? INVISIBLE_ICON : VISIBLE_ICON);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent)
            {
                viewHiddenBtn.setIcon(hideIgnoredItems ? INVISIBLE_ICON_HOVER : VISIBLE_ICON_HOVER);
            }
        });

        actionPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        actionPanel.setBorder(new EmptyBorder(5, 5, 5, 10));
        actionPanel.add(viewHiddenBtn, BorderLayout.EAST);
        actionPanel.setPreferredSize(new Dimension(0, 30));
        actionPanel.setVisible(false);
    }

	/**
	 * Changes item hiding mode of panel
	 *
	 * @param hide if ignored items should be hidden or not
	 */
	private void changeItemHiding(boolean hide)
	{
		hideIgnoredItems = hide;
		rebuild(plugin.getSuppliesConsumed());
		viewHiddenBtn.setIcon(hideIgnoredItems ? INVISIBLE_ICON : VISIBLE_ICON);
	}

    private void setUpSummaryPanel(JPanel summaryPanel)
    {
        // Cash Icon
        cashStackIcon.setIcon(COINS_ICON); // buffered image of cash stack

        // GP Label
        overallGpLabel.setFont(FontManager.getRunescapeSmallFont());
        overallGpLabel.setText(htmlLabel("Total value: ", 0));

        // subPanel configurations
        JPanel gpSubPanel = new JPanel(new BorderLayout());
        gpSubPanel.setBorder(new EmptyBorder(2, 10, 2, 0));
        gpSubPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        gpSubPanel.add(overallGpLabel, BorderLayout.WEST);

        // Reset all menu.
		// Create popup menu
		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));

		final JMenuItem resetAllToggle = new JMenuItem("Reset All");
		resetAllToggle.addActionListener(e ->
		{
			// Remove items consumed list and repaint also reset gp overall shit
			plugin.reset();
			rebuild(plugin.getSuppliesConsumed());
		});

		popupMenu.add(resetAllToggle);

        // summaryPanel configurations
		summaryPanel.setComponentPopupMenu(popupMenu);
        summaryPanel.setBorder(new EmptyBorder(8, 10, 8, 10));
        summaryPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        summaryPanel.add(cashStackIcon, BorderLayout.WEST);
        summaryPanel.add(gpSubPanel, BorderLayout.CENTER);
        summaryPanel.setVisible(false);
    }

    private List<Supply> removeIgnoredSupplies (List<Supply> allSupplies)
	{
		List<Supply> supplies = new ArrayList<>();

		for (Supply supply : allSupplies)
		{
			if (!supply.isIgnored())
			{
				supplies.add(supply);
			}
		}

		return supplies;
	}

    void rebuild(List<Supply> allSupplies)
    {
		List<Supply> supplies = hideIgnoredItems ? removeIgnoredSupplies(allSupplies) : allSupplies;

        remove(errorPanel);
        actionPanel.setVisible(true);
        summaryPanel.setVisible(true);

        // Reset GP value
        long totalValue = 0;

        for (Supply supply : supplies)
        {
			totalValue += supply.getTotalPrice();
        }

        overallGpLabel.setText(htmlLabel("Total value: ", totalValue));

        // Calculates how many rows need to be display to fit all items
        final int rowSize = ((supplies.size() % ITEMS_PER_ROW == 0) ? 0 : 1) + supplies.size() / ITEMS_PER_ROW;

        suppliesPanel.removeAll();
        suppliesPanel.setLayout(new GridLayout(rowSize, ITEMS_PER_ROW, 1, 1));

        for (int i = 0; i < rowSize * ITEMS_PER_ROW; i++)
        {
            final JPanel slotContainer = new JPanel();
            slotContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

            if (i < supplies.size())
            {
                final Supply supply = supplies.get(i);
                final JLabel imageLabel = new JLabel();
                imageLabel.setToolTipText(buildToolTip(supply));
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                AsyncBufferedImage itemImage = itemManager.getImage(supply.getId(), supply.getQuantity(), supply.getQuantity() > 1);
                itemImage.addTo(imageLabel);
                slotContainer.add(imageLabel);

                // Create popup menu
                final JPopupMenu popupMenu = new JPopupMenu();
                popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
                slotContainer.setComponentPopupMenu(popupMenu);

                final JMenuItem ignoreToggle = new JMenuItem("Toggle ignore");
                ignoreToggle.addActionListener(e ->
                {
                    // When clicked, need to remove item from the panel via calling plug in class, changing the list and then recalling rebuild
					supply.setIgnored(!supply.isIgnored());
					plugin.toggleItem(supply.getName(), supply.isIgnored());
                });

				final JMenuItem restartItemToggle = new JMenuItem("Reset item");
				restartItemToggle.addActionListener(e ->
				{
					// When clicked, need to remove item from the panel via calling plug in class, changing the list and then recalling rebuild
					plugin.getSuppliesConsumed().remove(supply);
					rebuild(plugin.getSuppliesConsumed());
				});

                popupMenu.add(restartItemToggle);
				popupMenu.add(ignoreToggle);
            }

            suppliesPanel.add(slotContainer);
        }

        suppliesPanel.revalidate();   // Needed for "dynamic refresh"
        suppliesPanel.repaint();
    }

    private static String buildToolTip(Supply supply)
    {
        final String name = supply.getName();
        final int quantity = supply.getQuantity();
        final long price = supply.getPrice();
        // final String ignoredLabel = item.isIgnored() ? " - Ignored" : "";
        return name + " x " + quantity + " (" + StackFormatter.quantityToStackSize(price) + ") "; //  + ignoredLabel;
    }

    private static String htmlLabel(String key, long value)
    {
        final String valueStr = StackFormatter.quantityToStackSize(value);
        return String.format(HTML_LABEL_TEMPLATE, ColorUtil.toHexColor(ColorScheme.LIGHT_GRAY_COLOR), key, valueStr);
    }
}
