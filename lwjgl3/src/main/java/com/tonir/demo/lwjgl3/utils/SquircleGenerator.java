package com.tonir.demo.lwjgl3.utils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class SquircleGenerator extends ApplicationAdapter {

    private final int borderThickness;
    private final int borderRadius;
    private final String outputPath;
    private final boolean roundTopLeft;
    private final boolean roundTopRight;
    private final boolean roundBottomLeft;
    private final boolean roundBottomRight;

    public static void generateSquircleNinePatch (final int borderRadius, final int border, final String outputPath,
                                                  final boolean roundTopLeft, final boolean roundTopRight,
                                                  final boolean roundBottomLeft, final boolean roundBottomRight) {
        final int size = 2 * borderRadius + 2; // overall size including borders

        // generate the squircle
        final Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        try {
            pixmap.setColor(Color.CLEAR);
            pixmap.fill();
            pixmap.setColor(Color.WHITE);
            fillRoundedRect(pixmap, 0, 0, size, size, borderRadius, border,
                    roundTopLeft, roundTopRight, roundBottomLeft, roundBottomRight);

            // convert to .9.png
            final Pixmap ninePatchPixmap = addNinePatchBorders(borderRadius, pixmap);
            try {
                final String ninePatchPath = outputPath + ".9.png";
                savePixmapToFile(ninePatchPixmap, ninePatchPath);
                System.out.println(".9.png squircle saved to: " + ninePatchPath);
            } finally {
                ninePatchPixmap.dispose();
            }
        } finally {
            pixmap.dispose();
        }
    }

    // draw a selectively rounded rectangle
    private static void fillRoundedRect (final Pixmap pixmap, final int x, final int y, final int width, final int height,
                                         final int outerRadius, final int borderThickness,
                                         final boolean roundTopLeft, final boolean roundTopRight,
                                         final boolean roundBottomLeft, final boolean roundBottomRight) {
        // disable blending for transparency to ensure clear cuts
        pixmap.setBlending(Pixmap.Blending.None);

        // draw the outer filled squircle
        drawRoundedCorners(pixmap, x, y, width, height, outerRadius, roundTopLeft, roundTopRight, roundBottomLeft, roundBottomRight);
        drawStraightEdges(pixmap, x, y, width, height, outerRadius, roundTopLeft, roundTopRight, roundBottomLeft, roundBottomRight);

        // cut out the inner hollow squircle
        if (borderThickness > 0) {
            pixmap.setColor(Color.CLEAR);
            final int innerRadius = outerRadius - borderThickness;
            final int innerX = x + borderThickness;
            final int innerY = y + borderThickness;
            final int innerWidth = width - 2 * borderThickness;
            final int innerHeight = height - 2 * borderThickness;

            drawRoundedCorners(pixmap, innerX, innerY, innerWidth, innerHeight, innerRadius,
                    roundTopLeft, roundTopRight, roundBottomLeft, roundBottomRight);
            drawStraightEdges(pixmap, innerX, innerY, innerWidth, innerHeight, innerRadius,
                    roundTopLeft, roundTopRight, roundBottomLeft, roundBottomRight);
        }

        // re-enable blending
        pixmap.setBlending(Pixmap.Blending.SourceOver);
    }

    private static void drawRoundedCorners (final Pixmap pixmap, final int x, final int y, final int width, final int height,
                                            final int radius, final boolean topLeft, final boolean topRight,
                                            final boolean bottomLeft, final boolean bottomRight) {
        if (bottomLeft) {
            pixmap.fillCircle(x + radius, y + radius, radius);
        }
        if (bottomRight) {
            pixmap.fillCircle(x + width - radius - 1, y + radius, radius);
        }
        if (topLeft) {
            pixmap.fillCircle(x + radius, y + height - radius - 1, radius);
        }
        if (topRight) {
            pixmap.fillCircle(x + width - radius - 1, y + height - radius - 1, radius);
        }
    }

    public static void drawStraightEdges(final Pixmap pixmap, final int x, final int y, final int width, final int height,
                                         final int radius, final boolean topLeft, final boolean topRight,
                                         final boolean bottomLeft, final boolean bottomRight) {
        // Top edge (visually top)
        if (!bottomLeft) {
            pixmap.fillRectangle(x, y + height - radius, radius, radius); // top-left edge
        }
        if (!bottomRight) {
            pixmap.fillRectangle(x + width - radius, y + height - radius, radius, radius); // top-right edge
        }
        pixmap.fillRectangle(x + radius, y + height - radius, width - 2 * radius, radius); // top center

        // Bottom edge (visually bottom)
        if (!topLeft) {
            pixmap.fillRectangle(x, y, radius, radius); // bottom-left edge
        }
        if (!topRight) {
            pixmap.fillRectangle(x + width - radius, y, radius, radius); // bottom-right edge
        }
        pixmap.fillRectangle(x + radius, y, width - 2 * radius, radius); // bottom center

        // Left and right edges
        pixmap.fillRectangle(x, y + radius, radius, height - 2 * radius); // left edge
        pixmap.fillRectangle(x + width - radius, y + radius, radius, height - 2 * radius); // right edge

        // Center area
        pixmap.fillRectangle(x + radius, y + radius, width - 2 * radius, height - 2 * radius);
    }

    private static Pixmap addNinePatchBorders (final int borderRadius, final Pixmap pixmap) {
        final int width = pixmap.getWidth() + 2;
        final int height = pixmap.getHeight() + 2;

        final Pixmap ninePatch = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        ninePatch.setColor(Color.CLEAR);
        ninePatch.fill();

        ninePatch.drawPixmap(pixmap, 1, 1);

        ninePatch.setColor(Color.BLACK);
        ninePatch.drawLine(borderRadius, 0, width - borderRadius - 1, 0); // bottom stretch
        ninePatch.drawLine(0, borderRadius, 0, height - borderRadius - 1); // left stretch
        ninePatch.drawLine(1, height - 1, width - 2, height - 1); // top content
        ninePatch.drawLine(width - 1, 1, width - 1, height - 2); // right content

        return ninePatch;
    }

    private static void savePixmapToFile (final Pixmap pixmap, final String outputPath) {
        final FileHandle file = Gdx.files.local(outputPath);
        PixmapIO.writePNG(file, pixmap);
        System.out.println("pixmap saved to: " + file.path());
    }

    public SquircleGenerator (final String[] args) {
        if (args.length < 7) {
            throw new IllegalArgumentException("Usage: SquircleGenerator <borderRadius> <borderThickness> <outputPath> <roundTopLeft> <roundTopRight> <roundBottomLeft> <roundBottomRight>");
        }
        this.borderThickness = Integer.parseInt(args[1]);
        this.borderRadius = Integer.parseInt(args[0]);
        this.outputPath = args[2];
        this.roundTopLeft = Boolean.parseBoolean(args[3]);
        this.roundTopRight = Boolean.parseBoolean(args[4]);
        this.roundBottomLeft = Boolean.parseBoolean(args[5]);
        this.roundBottomRight = Boolean.parseBoolean(args[6]);
    }

    @Override
    public void create () {
        super.create();
        generateSquircleNinePatch(borderRadius, borderThickness, outputPath, roundTopLeft, roundTopRight, roundBottomLeft, roundBottomRight);
        Gdx.app.exit();
    }

    public static void main (String[] args) {
        final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        final SquircleGenerator squircleGenerator = new SquircleGenerator(args);
        new Lwjgl3Application(squircleGenerator, config);
    }
}
