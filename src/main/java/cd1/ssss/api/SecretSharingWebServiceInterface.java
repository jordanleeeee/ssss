package cd1.ssss.api;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author Jordan
 */
@RequestMapping("/")
public interface SecretSharingWebServiceInterface {
    @PutMapping(path = "/integer/share", consumes = "application/json")
    CreateShareResponse createIntegerShares(@Valid @RequestBody CreateShareRequest request);

    @PutMapping(path = "/integer/recover", consumes = "application/json")
    RecoverSecretResponse recoverIntegerSecret(@Valid @RequestBody RecoverSecretRequest request);

    @PutMapping(path = "/text/share", consumes = "application/json")
    CreateShareResponse createTextShares(@Valid @RequestBody CreateTextShareRequest request);

    @PutMapping(path = "/file/share", consumes = "multipart/form-data")
    CreateShareResponse createTextShares(@RequestParam("file") MultipartFile file, @RequestParam("n") int numberOfShare, @RequestParam("t") int threshold);

    @PutMapping(path = "/text/recover", consumes = "application/json")
    RecoverTextSecretResponse recoverTextSecret(@Valid @RequestBody RecoverSecretRequest request);
}
