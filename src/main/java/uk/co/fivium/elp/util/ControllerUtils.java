package uk.co.fivium.elp.util;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ControllerUtils {

  public static ResponseEntity serveResource(Resource resource, String filename) throws IOException {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .contentLength(resource.contentLength())
        .header(
            HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", filename))
        .body(resource);
  }

}
