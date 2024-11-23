package com.qrCodeService.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.virtualNumber.virtualNumber.entites.VirtualPhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
//import java.nio.file.FileSystems;
//import java.nio.file.Path;

@RestController
@RequestMapping("/qr")
public class QRCodeController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@RequestParam String phoneNumber){
        try{
            String data= "tel:"+ phoneNumber;
            byte[] qrCode= createQRCode(data, 300, 300);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCode);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    private byte[] createQRCode(String data, int width, int height) throws WriterException, IOException{
        QRCodeWriter qrCodeWriter= new QRCodeWriter();
        BitMatrix bitMatrix= qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
    }

    @GetMapping(value = "/generate/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long id) {
        try {
            // Fetch phone number from Phone Number Service
            String phoneNumberServiceUrl = "http://localhost:8081/phone-numbers/" + id;
            ResponseEntity<VirtualPhoneNumber> response = restTemplate.getForEntity(phoneNumberServiceUrl, VirtualPhoneNumber.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            String data = "tel:" + response.getBody().getPhoneNumber();
            byte[] qrCode = createQRCode(data, 300, 300);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

