package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.PackageDto;
import com.kivimango.nimhub.data.PackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
final class PackageController {

    private final Logger log = LoggerFactory.getLogger(PackageController.class);
    private final PackageService packages;

    PackageController(PackageService packages) {
        this.packages = packages;
    }

    @PostMapping(value = "/packages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<PackageDto> uploadPackage(@Valid PackageUploadForm form, MultipartFile file) throws IOException {
        PackageDto saved = packages.save(form, file.getBytes());
        log.info("New package saved: {}", saved.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/packages")
                .buildAndExpand(saved.getId()).toUri();
        return created(location).body(saved);
    }
}
