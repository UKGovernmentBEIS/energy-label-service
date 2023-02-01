package uk.gov.beis.els.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.BiConsumer;
import javax.imageio.ImageIO;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.jsoup.nodes.Document;

public class RendererUtils {

  /**
   * Collate a list of SVG documents into a single image, using the provided image writer function.
   * All images must be of the same dimension. They will be appended horizontally.
   * Image data is held in memory so care should be taken when calling this with a large number of images.
   * @param documents List of SVG documents
   * @param bufferedImageType The type of BufferedImage to create. E.g. BufferedImage.TYPE_INT_RGB
   * @param imageIoFormatName The name of the ImageIO writer type, E.g. 'png' or 'jpeg'
   * @param writerFunction The writer function. This will be called for each document.
   * @return A ByteArrayOutputStream containing the collated image in the specified format.
   */
  public static ByteArrayOutputStream applyImageCollation(List<Document> documents, int bufferedImageType, String imageIoFormatName, BiConsumer<Document, OutputStream> writerFunction) {
    try (ByteArrayOutputStream containerOs = new ByteArrayOutputStream()) {
      // Get size from first doc
      Document firstDocument = documents.get(0);
      float width = TemplateUtils.getWidth(firstDocument);
      float height = TemplateUtils.getHeight(firstDocument);

      float scaledWidth = mmToPixels(width);
      float scaledHeight = mmToPixels(height);

      // Create a container image wide enough for all documents
      BufferedImage container = new BufferedImage(
          Math.round(scaledWidth * documents.size()),
          Math.round(scaledHeight),
          bufferedImageType
      );

      Graphics containerGraphics = container.getGraphics();
      int idx = 0;

      for (Document doc : documents) {
        try(ByteArrayOutputStream imageOs = new ByteArrayOutputStream()) {
          writerFunction.accept(doc, imageOs);

          BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageOs.toByteArray()));
          containerGraphics.drawImage(image, Math.round(idx * scaledWidth), 0, null);
          idx++;
        }
      }

      containerGraphics.dispose();
      ImageIO.write(container, imageIoFormatName, containerOs);
      return containerOs;
    } catch (Exception e) {
      throw new RuntimeException("Error collating images", e);
    }
  }

  public static void setRenderDimensions(ImageTranscoder transcoder, Document doc) {
    float width = TemplateUtils.getWidth(doc);
    float height = TemplateUtils.getHeight(doc);

    transcoder.addTranscodingHint(org.apache.batik.transcoder.image.ImageTranscoder.KEY_WIDTH, mmToPixels(width));
    transcoder.addTranscodingHint(org.apache.batik.transcoder.image.ImageTranscoder.KEY_HEIGHT, mmToPixels(height));
  }

  private static float mmToInches(float mm) {
    return mm * 0.03937f;
  }

  public static float mmToPixels(float mm) {
    return mmToInches(mm) * 300; // 300 DPI
  }
}
