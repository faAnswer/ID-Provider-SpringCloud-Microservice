package org.tecky.paymentservice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @GetMapping("/invoicing")
    public ResponseEntity<?> invoicing(@RequestParam Map<String, String> paymentInfo, HttpServletRequest request, HttpServletResponse response){

        HttpHeaders headers = new HttpHeaders();

        String invoceId = "ABCDEFG";

        headers.setLocation(URI.create("http://47.92.137.0:9998/payment?invoice_id=" + invoceId));

        ResponseEntity<?> responseEntity = new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);


        return responseEntity;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam Map<String, String> paymentInfo, HttpServletRequest request, HttpServletResponse response){




        return "OK";
    }

}
