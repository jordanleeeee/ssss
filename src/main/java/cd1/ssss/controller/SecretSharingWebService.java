package cd1.ssss.controller;

import cd1.ssss.api.CreateShareRequest;
import cd1.ssss.api.CreateShareResponse;
import cd1.ssss.api.CreateTextShareRequest;
import cd1.ssss.api.RecoverSecretRequest;
import cd1.ssss.api.RecoverSecretResponse;
import cd1.ssss.api.RecoverTextSecretResponse;
import cd1.ssss.api.SecretSharingWebServiceInterface;
import cd1.ssss.service.ConstructShareService;
import cd1.ssss.service.RecoverSecretService;
import cd1.ssss.service.TextShare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Jordan
 */
@RestController
public class SecretSharingWebService implements SecretSharingWebServiceInterface {
    private final Logger logger = LoggerFactory.getLogger(SecretSharingWebService.class);
    @Autowired
    ConstructShareService constructShareService;
    @Autowired
    RecoverSecretService recoverSecretService;

    @Override
    public CreateShareResponse createIntegerShares(CreateShareRequest request) {
        int[] points = constructShareService.constructPoints(request.secret, request.numberOfShares, request.threshold);
        List<String> shares = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            shares.add(i + 1 + ";" + points[i]);
        }

        var response = new CreateShareResponse();
        response.shares = shares;
        return response;
    }

    @Override
    public RecoverSecretResponse recoverIntegerSecret(RecoverSecretRequest request) {
        List<int[]> shares = request.shares.stream().map(share -> {
            String[] parts = share.split(";");
            return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
        }).toList();
        int secret = recoverSecretService.recoverSecret(shares);

        var response = new RecoverSecretResponse();
        response.secret = secret;
        return response;
    }

    @Override
    public CreateShareResponse createTextShares(CreateTextShareRequest request) {
        byte[][] bytes = constructShareService.constructTextShares(request.secret.getBytes(), request.numberOfShares, request.threshold);
        List<String> shares = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            shares.add(i + 1 + ";" + Base64.getEncoder().encodeToString(bytes[i]));
        }

        var response = new CreateShareResponse();
        response.shares = shares;
        return response;
    }

    @Override
    public RecoverTextSecretResponse recoverTextSecret(RecoverSecretRequest request) {
        byte[] bytes = recoverSecretService.recoverStringSecret(
                request.shares.stream().map(share -> {
                    String[] parts = share.split(";");

                    var textShare = new TextShare();
                    textShare.x = Integer.parseInt(parts[0]);
                    textShare.bytes = Base64.getDecoder().decode(parts[1]);
                    return textShare;
                }).toList()
        );

        var response = new RecoverTextSecretResponse();
        response.secret = new String(bytes);
        return response;
    }

    @Override
    public CreateShareResponse createTextShares(MultipartFile file, int numberOfShare, int threshold) {
        if (!"text/plain".equals(file.getContentType())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file must be with content type: text/plain");
        }
        try {
            byte[][] bytes = constructShareService.constructTextShares(file.getBytes(), numberOfShare, threshold);
            List<String> shares = new ArrayList<>();
            for (int i = 0; i < bytes.length; i++) {
                shares.add(i + 1 + ";" + Base64.getEncoder().encodeToString(bytes[i]));
            }
            var response = new CreateShareResponse();
            response.shares = shares;
            return response;
        } catch (IOException e) {
            logger.error("read file error", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "fail on reading input file");
        }
    }
}
