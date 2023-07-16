package com.smj.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FontEditor {
    public static int currentChar = 0;
    public static int height = 8;
    public static int spacing = 1;
    public static GlyphData[] glyphData = new GlyphData['~' - ' ' + 2];
    public static GlyphData copied = null;
    public static boolean drawing = false;
    public static boolean erasing = false;
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        for (int i = 0; i < glyphData.length; i++) {
            glyphData[i] = new GlyphData();
            glyphData[i].data = new boolean[8][height];
        }
        JFrame frame = new JFrame("Bitmap Font Editor");
        frame.setLocation(100, 100);
        frame.getContentPane().setPreferredSize(new Dimension(522, 463));
        frame.pack();
        frame.setDefaultCloseOperation(3);
        frame.setLayout(null);
        frame.setResizable(false);
        JPanel fontPanel = new JPanel() {
            public void paint(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, 522, 458);
                for (int i = 0; i < 16 * 6; i++) {
                    int x = i % 16;
                    int y = i / 16;
                    g.setColor(Color.BLACK);
                    g.drawRect(x * 32 - 1, y * 32 - 1, 32, 32);
                    if (i == currentChar) {
                        g.setColor(Color.RED);
                        g.fillRect(x * 32, y * 32, 31, 31);
                        g.setColor(Color.WHITE);
                    }
                    String str = getString((char)(i + ' '));
                    Rectangle2D fontBounds = g.getFontMetrics().getStringBounds(str, g);
                    g.drawString(str, (int)(x * 32 + 16 - fontBounds.getWidth() / 2), (int)(y * 32 + 16 + fontBounds.getHeight() / 2 - 4));
                }
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, 522, 458);
            }
        };
        JPanel charEditor = new JPanel() {
            public void paint(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, 522, 458);
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, 16 * glyphData[currentChar].data.length - 1, 16 * height - 1);
                for (int x = 0; x < glyphData[currentChar].data.length; x++) {
                    for (int y = 0; y < glyphData[currentChar].data[0].length; y++) {
                        g.drawRect(x * 16 - 1, y * 16 - 1, 16, 16);
                        if (glyphData[currentChar].data[x][y]) g.fillRect(x * 16 - 1, y * 16 - 1, 16, 16);
                    }
                }
            }
        };
        JLabel currentCharLabel = new JLabel("Current Character: [SPC]");
        JLabel glyphWidthLabel = new JLabel("Glyph Width");
        JLabel fontHeightLabel = new JLabel("Font Height");
        JLabel fontSpacingLabel = new JLabel("Font Spacing");
        JButton newButton = new JButton("New");
        JButton importButton = new JButton("Import");
        JButton exportButton = new JButton("Export");
        JButton copyGlyphButton = new JButton("Copy Glyph");
        JButton pasteGlyphButton = new JButton("Paste Glyph");
        JButton copyUppercaseDataToLowercaseButton = new JButton("Copy Uppercase Data to Lowercase");
        currentCharLabel.setHorizontalAlignment(JLabel.CENTER);
        JSpinner glyphWidthSpinner = new JSpinner(new SpinnerNumberModel(8, 1, 16, 1));
        JSpinner fontHeightSpinner = new JSpinner(new SpinnerNumberModel(8, 1, 16, 1));
        JSpinner fontSpacingSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
        currentCharLabel.setBounds(266, 202, 251, 16);
        glyphWidthLabel.setBounds(266, 227, 123, 16);
        fontHeightLabel.setBounds(266, 256, 123, 16);
        fontSpacingLabel.setBounds(266, 285, 123, 16);
        glyphWidthSpinner.setBounds(394, 223, 123, 24);
        fontHeightSpinner.setBounds(394, 252, 123, 24);
        fontSpacingSpinner.setBounds(394, 281, 123, 24);
        copyGlyphButton.setBounds(266, 310, 123, 24);
        pasteGlyphButton.setBounds(394, 310, 123, 24);
        copyUppercaseDataToLowercaseButton.setBounds(266, 339, 251, 24);
        importButton.setBounds(266, 434, 123, 24);
        exportButton.setBounds(394, 434, 123, 24);
        newButton.setBounds(266, 405, 251, 24);
        fontPanel.setBounds(5, 5, 32 * 16, 32 * 6);
        charEditor.setBounds(5, 202, 16 * 16, 16 * 16);
        pasteGlyphButton.setEnabled(false);
        fontPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / 32;
                int y = e.getY() / 32;
                currentChar = y * 16 + x;
                currentCharLabel.setText("Current Character: " + getString((char)(currentChar + ' ')));
                glyphWidthSpinner.setValue(glyphData[currentChar].data.length);
            }
        });
        charEditor.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int x = e.getX() / 16;
                int y = e.getY() / 16;
                if (x < 0 || y < 0 || x >= glyphData[currentChar].data.length || y >= glyphData[currentChar].data[0].length) return;
                if (drawing) glyphData[currentChar].data[x][y] = !erasing;
            }
            public void mouseDragged(MouseEvent e) {
                mouseMoved(e);
            }
        });
        charEditor.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / 16;
                int y = e.getY() / 16;
                if (x < 0 || y < 0 || x >= glyphData[currentChar].data.length || y >= glyphData[currentChar].data[0].length) return;
                drawing = true;
                erasing = glyphData[currentChar].data[x][y];
                glyphData[currentChar].data[x][y] = !erasing;
            }
            public void mouseReleased(MouseEvent e) {
                drawing = false;
            }
        });
        glyphWidthSpinner.addChangeListener((e) -> {
            glyphData[currentChar].resize((int)glyphWidthSpinner.getValue(), height);
        });
        fontHeightSpinner.addChangeListener((e) -> {
            height = (int)fontHeightSpinner.getValue();
            for (GlyphData data : glyphData) {
                data.resize(data.data.length, height);
            }
        });
        fontSpacingSpinner.addChangeListener((e) -> {
            spacing = (int)fontSpacingSpinner.getValue();
        });
        copyGlyphButton.addActionListener((e) -> {
            copied = glyphData[currentChar].copy();
            pasteGlyphButton.setEnabled(true);
        });
        pasteGlyphButton.addActionListener((e) -> {
            glyphData[currentChar] = copied.copy();
        });
        copyUppercaseDataToLowercaseButton.addActionListener((e) -> {
            int min = 'A' - ' ';
            int max = 'Z' - ' ';
            int difference = 'a' - 'A';
            for (int i = min; i <= max; i++) {
                glyphData[i + difference] = glyphData[i].copy();
            }
        });
        newButton.addActionListener((e) -> {
            for (int i = 0; i < glyphData.length; i++) {
                glyphData[i] = new GlyphData();
                glyphData[i].data = new boolean[8][height];
            }
            glyphWidthSpinner.setValue(8);
            fontHeightSpinner.setValue(8);
            fontSpacingSpinner.setValue(1);
        });
        exportButton.addActionListener((e) -> {
            try {
                FileDialog dialog = new FileDialog(frame);
                dialog.setMode(FileDialog.SAVE);
                dialog.setVisible(true);
                if (dialog.getFiles().length != 0) {
                    File file = dialog.getFiles()[0];
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(height);
                    out.write(spacing);
                    for (GlyphData glyph : glyphData) {
                        out.write(glyph.data.length);
                        boolean[] bits = new boolean[glyph.data.length * glyph.data[0].length];
                        for (int x = 0; x < glyph.data.length; x++) {
                            for (int y = 0; y < glyph.data[0].length; y++) {
                                bits[y * glyph.data.length + x] = glyph.data[x][y];
                            }
                        }
                        byte[] bytes = new byte[(int)Math.ceil(bits.length / 8.0)];
                        for (int i = 0; i < bytes.length; i++) {
                            byte value = 0;
                            for (int j = 0; j < 8; j++) {
                                if (i * 8 + j >= bits.length) break;
                                if (bits[i * 8 + j]) value |= (1 << (7 - j));
                            }
                            bytes[i] = value;
                        }
                        out.write(bytes);
                    }
                    out.close();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "There was an error importing the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        importButton.addActionListener((e) -> {
            try {
                FileDialog dialog = new FileDialog(frame);
                dialog.setMode(FileDialog.LOAD);
                dialog.setVisible(true);
                if (dialog.getFiles().length != 0) {
                    File file = dialog.getFiles()[0];
                    FileInputStream in = new FileInputStream(file);
                    height = in.read();
                    spacing = in.read();
                    for (GlyphData data : glyphData) {
                        int width = in.read();
                        data.data = new boolean[width][height];
                        boolean[] bits = new boolean[width * height];
                        for (int i = 0; i < Math.ceil(bits.length / 8.0); i++) {
                            byte value = (byte)in.read();
                            for (int j = 0; j < 8; j++) {
                                if (i * 8 + j >= bits.length) break;
                                bits[i * 8 + j] = ((value >> (7 - j)) & 1) == 1;
                            }
                        }
                        for (int i = 0; i < bits.length; i++) {
                            data.data[i % width][i / width] = bits[i];
                        }
                    }
                    in.close();
                    glyphWidthSpinner.setValue(glyphData[currentChar].data.length);
                    fontHeightSpinner.setValue(height);
                    fontSpacingSpinner.setValue(spacing);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "There was an error importing the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(currentCharLabel);
        frame.add(glyphWidthLabel);
        frame.add(fontHeightLabel);
        frame.add(fontSpacingLabel);
        frame.add(glyphWidthSpinner);
        frame.add(fontHeightSpinner);
        frame.add(fontSpacingSpinner);
        frame.add(copyGlyphButton);
        frame.add(pasteGlyphButton);
        frame.add(copyUppercaseDataToLowercaseButton);
        frame.add(importButton);
        frame.add(exportButton);
        frame.add(newButton);
        frame.add(fontPanel);
        frame.add(charEditor);
        frame.setVisible(true);
        while (true) {
            frame.repaint();
            Thread.sleep(10);
        }
    }
    public static String getString(char character) {
        if (character == 127) return "\uFFFD";
        if (character == ' ') return "[SPC]";
        return "" + character;
    }
    public static class GlyphData {
        public boolean[][] data;
        public void resize(int width, int height) {
            boolean[][] resized = new boolean[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (data.length <= x || data[0].length <= y) resized[x][y] = false;
                    else resized[x][y] = data[x][y];
                }
            }
            data = resized;
        }
        public GlyphData copy() {
            GlyphData copy = new GlyphData();
            copy.data = new boolean[data.length][data[0].length];
            for (int x = 0; x < data.length; x++) {
                for (int y = 0; y < data[0].length; y++) {
                    copy.data[x][y] = data[x][y];
                }
            }
            return copy;
        }
    }
}