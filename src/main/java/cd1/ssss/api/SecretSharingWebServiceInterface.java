package cd1.ssss.api;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jordan
 */
@RequestMapping("/")
public interface SecretSharingWebServiceInterface {
    @PutMapping(path = "/integer/share", consumes = "application/json")
    CreateShareResponse createIntegerShares(@RequestBody CreateShareRequest request);

    @PutMapping(path = "/integer/recover", consumes = "application/json")
    RecoverSecretResponse recoverIntegerSecret(@RequestBody RecoverSecretRequest request);

    @PutMapping(path = "/text/share", consumes = "application/json")
    CreateShareResponse createTextShares(@RequestBody CreateTextShareRequest request);

    @PutMapping(path = "/text/recover", consumes = "application/json")
    RecoverTextSecretResponse recoverTextSecret(@RequestBody RecoverSecretRequest request);

    @PutMapping(path = "/file/share", consumes = "multipart/form-data")
    CreateShareResponse createTextShares(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "n") int numberOfShare, @RequestParam(value = "t") int threshold);
}
