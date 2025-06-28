package me.oganesson.extraium.ui;

import me.oganesson.extraium.addon.AddonDesc;
import me.oganesson.extraium.addon.AddonFinder;
import me.oganesson.extraium.core.ExtraiumCorePlugin;
import me.oganesson.extraium.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddonSelectGUI extends JDialog {

    private final JList<AddonDesc> dataList;

    public AddonSelectGUI(File gameDir) {
        super((JFrame) null, "私货选择 ", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = env.getDefaultScreenDevice();
        Rectangle rect = screen.getDefaultConfiguration().getBounds();
        int width = rect.width / 3;
        int height = (int) (width / 1.25f);
        int x = (rect.width - width) / 2;
        int y = (rect.height - height) / 2;
        setLocation(x, y);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        AddonFinder finder = new AddonFinder(new File(gameDir, "addons").getPath());
        Map<String, Map.Entry<File, AddonDesc>> addons = finder.processAddons();

        DefaultListModel<AddonDesc> listModel = new DefaultListModel<>();
        for (Map.Entry<String, Map.Entry<File, AddonDesc>> entry : addons.entrySet()) {
            listModel.addElement(entry.getValue().getValue());
        }

        dataList = new JList<>(listModel);
        dataList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dataList.setCellRenderer(new Renderer());

        JButton confirmButton = new JButton("确认选择");
        confirmButton.addActionListener(e -> {
            List<AddonDesc> selectedItems = dataList.getSelectedValuesList();
            ArrayList<String> addonIds = selectedItems.stream().map(AddonDesc::id).collect(Collectors.toCollection(ArrayList::new));
            for (String id : addonIds) {
                appendAddon(finder.findAddon(id), gameDir);
            }
            this.dispose();
        });

        mainPanel.add(dataList);
        mainPanel.add(confirmButton);
        add(mainPanel);

        setSize(width, height);
        pack();
        setVisible(true);
        setAutoRequestFocus(true);
    }

    private static void appendAddon(File addon, File gameDir) {
        if (addon.isDirectory()) {
            try {
                ExtraiumCorePlugin.LOGGER.info("正在添加私货: {}", addon.getName());
                FileUtils.copyFolder(addon, gameDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Renderer extends JLabel implements ListCellRenderer<AddonDesc> {

        @Override
        public Component getListCellRendererComponent(JList<? extends AddonDesc> list, AddonDesc value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText("<html><b>" + value.name() + "</b> - " + value.version() + "<br/><i>作者:</i> " + value.author() + "<br/>" + value.description() + "</html>");

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            return this;
        }
    }

}
