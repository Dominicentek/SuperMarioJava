package com.smj.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.smj.Main;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Calendar;
import java.util.Date;

public class Screenshot implements ClipboardOwner {
    public static void take() {
        if (!Main.options.saveFileScreenshot && !Main.options.saveClipScreenshot) return;
        BufferedImage image = getImage();
        new Thread(() -> {
            if (Main.options.saveFileScreenshot) {
                String path = ensureNotExists(Gdx.files.local("smj_screenshots/" + getTimestamp() + ".png"));
                try {
                    File file = new File(path);
                    file.getParentFile().mkdirs();
                    ImageIO.write(image, "png", file);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (Main.options.saveClipScreenshot) {
                System.err.println("if ur on windows then ignore this mfing error idfk why it happens but the image is in the clipboadr just ignore this i cant try-catch it so yeh");
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new TransferableImage(image), new Screenshot());
            }
        }).start();
    }
    private static BufferedImage getImage() {
        Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, Main.WIDTH, Main.HEIGHT);
        BufferedImage image = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < Main.WIDTH; x++) {
            for (int y = 0; y < Main.HEIGHT; y++) {
                image.setRGB(x, Main.HEIGHT - 1 - y, 0xFF000000 | ((pixmap.getPixel(x, y) >> 8) & 0xFFFFFF));
            }
        }
        pixmap.dispose();
        return image;
    }
    private static String getTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR) + "-" +
            String.format("%1$2s", "" + calendar.get(Calendar.MONTH)).replaceAll(" ", "0") + "-" +
            String.format("%1$2s", "" + calendar.get(Calendar.DAY_OF_MONTH)).replaceAll(" ", "0") + "_" +
            String.format("%1$2s", "" + calendar.get(Calendar.HOUR_OF_DAY)).replaceAll(" ", "0") + "." +
            String.format("%1$2s", "" + calendar.get(Calendar.MINUTE)).replaceAll(" ", "0") + "." +
            String.format("%1$2s", "" + calendar.get(Calendar.SECOND)).replaceAll(" ", "0");
    }
    private static String ensureNotExists(FileHandle handle) {
        int n = 0;
        String name = handle.nameWithoutExtension();
        String extension = (handle.extension().isEmpty() ? "" : "." + handle.extension());
        String currentName = handle.name();
        while (handle.sibling(currentName).exists()) {
            n++;
            currentName = name + "-" + n + extension;
        }
        return handle.sibling(currentName).path();
    }
    public void lostOwnership(Clipboard clipboard, Transferable contents) {}
    public static class TransferableImage implements Transferable {
        public Image image;
        public TransferableImage(Image image) {
            this.image = image;
        }
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (flavor.equals(DataFlavor.imageFlavor) && image != null) {
                return image;
            }
            else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[1];
            flavors[0] = DataFlavor.imageFlavor;
            return flavors;
        }
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for (DataFlavor dataFlavor : flavors) {
                if (flavor.equals(dataFlavor)) {
                    return true;
                }
            }
            return false;
        }
    }
}
