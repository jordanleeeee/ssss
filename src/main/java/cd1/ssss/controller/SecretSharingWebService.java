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
import cd1.ssss.util.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Jordan
 */
@RestController
public class SecretSharingWebService implements SecretSharingWebServiceInterface {
    ConstructShareService constructShareService;
    RecoverSecretService recoverSecretService;

    public SecretSharingWebService(@Autowired ConstructShareService constructShareService, @Autowired RecoverSecretService recoverSecretService) {
        this.constructShareService = constructShareService;
        this.recoverSecretService = recoverSecretService;
    }

    @Override
    public CreateShareResponse createIntegerShares(CreateShareRequest request) {
        if (request.threshold > request.numberOfShares) {
            throw new BadRequestException("number of shares must >= threshold");
        }
        int[] points = constructShareService.constructPoints(request.secret, request.numberOfShares, request.threshold);
        List<String> shares = new ArrayList<>(points.length);
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
        if (request.threshold > request.numberOfShares) {
            throw new BadRequestException("number of shares must >= threshold");
        }
        byte[][] bytes = constructShareService.constructTextShares(request.secret.getBytes(StandardCharsets.UTF_8), request.numberOfShares, request.threshold);
        List<String> shares = new ArrayList<>(bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            shares.add(i + 1 + ";" + Base64.getEncoder().encodeToString(bytes[i]));
        }

        var response = new CreateShareResponse();
        response.shares = shares;
        return response;
    }

    @Override
    public CreateShareResponse createTextShares(MultipartFile file, int numberOfShares, int threshold) {
        if (!"text/plain".equals(file.getContentType())) {
            throw new BadRequestException("file must be with content type text/plain, but received: " + file.getContentType());
        }
        if (threshold > numberOfShares) {
            throw new BadRequestException("number of shares must >= threshold");
        }

        try {
            byte[][] bytes = constructShareService.constructTextShares(file.getBytes(), numberOfShares, threshold);
            List<String> shares = new ArrayList<>(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                shares.add(i + 1 + ";" + Base64.getEncoder().encodeToString(bytes[i]));
            }
            var response = new CreateShareResponse();
            response.shares = shares;
            return response;
        } catch (IOException e) {
            throw new Error("fail on reading input file", e);
        }
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
        response.secret = new String(bytes, StandardCharsets.UTF_8);
        return response;
    }
}
