package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.PackageDto;
import com.kivimango.nimhub.data.PackageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;

import static org.springframework.http.ResponseEntity.created;

@RestController
@PreAuthorize("hasRole('USER')")
final class PackageController {

    private final PackageService packages;

    PackageController(PackageService packages) {
        this.packages = packages;
    }

    @PostMapping(value = "/packages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<PackageDto> uploadPackage(@Valid PackageUploadForm form, MultipartFile file, Principal principal) throws IOException {
        PackageDto saved = packages.save(form, file.getBytes(), principal.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + saved.getName()).buildAndExpand().toUri();
        return created(location).body(saved);
    }
}
