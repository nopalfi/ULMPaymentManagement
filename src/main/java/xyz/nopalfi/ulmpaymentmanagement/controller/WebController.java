package xyz.nopalfi.ulmpaymentmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import xyz.nopalfi.ulmpaymentmanagement.entity.PayloadData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Controller
public class WebController {

    @GetMapping("/")
    public String dashboard() {
        return "index";
    }

    @GetMapping("/inquiry")
    public String inquiry() {
        return "inquiry";
    }

    @PostMapping(value = "/inquiry", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postInquiry(@RequestBody MultiValueMap<String, String> inquiryData, Model model) throws IOException, JAXBException {

        String kodeBank = inquiryData.get("kodeBank").get(0);
        String kodeChannel = inquiryData.get("kodeChannel").get(0);
        String kodeTerminal = inquiryData.get("kodeTerminal").get(0);
        String nomorPembayaran = inquiryData.get("nomorPembayaran").get(0);
        String tanggalTransaksi = inquiryData.get("tanggalTransaksi").get(0);
        String idTransaksi = inquiryData.get("idTransaksi").get(0);

        String postData = String.format("action=inquiry&kodeBank=%s&kodeChannel=%s&kodeTerminal=%s&nomorPembayaran=%s&tanggalTransaksi=%s&idTransaksi=%s",
                kodeBank,
                kodeChannel,
                kodeTerminal,
                nomorPembayaran,
                tanggalTransaksi,
                idTransaksi);

        URL url = new URL("http://localhost/index.php");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Write form data to connection
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(postData);
        writer.flush();

        // Read XML response from connection
        InputStream inputStream = connection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        String response = scanner.useDelimiter("\\A").next();

        JAXBContext jaxbContext = JAXBContext.newInstance(PayloadData.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(response);
        PayloadData inquiryDataXML = (PayloadData) unmarshaller.unmarshal(reader);
        // Parse XML response
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(response)));
//
//        // Print extracted values
//        System.out.println("Code: " + document.getElementsByTagName("code").item(0).getTextContent());
//        System.out.println("Nomor Pembayaran: " + document.getElementsByTagName("nomorPembayaran").item(0).getTextContent());
//        System.out.println("ID Tagihan: " + document.getElementsByTagName("idTagihan").item(0).getTextContent());
//        System.out.println("Nomor Induk: " + document.getElementsByTagName("nomorInduk").item(0).getTextContent());
//        System.out.println("Kode Transaksi: "+ document.getElementsByTagName("kodeTransaksi").item(0).getTextContent());
//        System.out.println("Deskripsi Pendek: "+ document.getElementsByTagName("deskripsiPendek").item(0).getTextContent());
//        return new ResponseEntity<>(response, HttpStatus.OK);
        model.addAttribute("inquiryDataXML", inquiryDataXML);
        return "inquiry";
    }

    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }


    @PostMapping(value = "/payment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postPayment(@RequestBody MultiValueMap<String, String> inquiryData, Model model) throws IOException, JAXBException, ParserConfigurationException, SAXException {

        String kodeBank = inquiryData.get("kodeBank").get(0);
        String kodeChannel = inquiryData.get("kodeChannel").get(0);
        String kodeTerminal = inquiryData.get("kodeTerminal").get(0);
        String nomorPembayaran = inquiryData.get("nomorPembayaran").get(0);
        String tanggalTransaksi = inquiryData.get("tanggalTransaksi").get(0);
        String idTransaksi = inquiryData.get("idTransaksi").get(0);
        String idTagihan = inquiryData.get("idTagihan").get(0);
        String nomorJurnalPembukuan = inquiryData.get("nomorJurnalPembukuan").get(0);
        String daftarPembayaran = inquiryData.get("daftarPembayaran").get(0);
        String totalNominal = inquiryData.get("totalNominal").get(0);


        String postData = String.format("action=payment&kodeBank=%s&kodeChannel=%s&kodeTerminal=%s&nomorPembayaran=%s&tanggalTransaksi=%s&idTransaksi=%s&idTagihan=%s&nomorJurnalPembukuan=%s&daftarPembayaran=%s&totalNominal=%s",
                kodeBank,
                kodeChannel,
                kodeTerminal,
                nomorPembayaran,
                tanggalTransaksi,
                idTransaksi,
                idTagihan,
                nomorJurnalPembukuan,
                daftarPembayaran,
                totalNominal);

        URL url = new URL("http://localhost/index.php");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Write form data to connection
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(postData);
        writer.flush();

        // Read XML response from connection
        InputStream inputStream = connection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        String response = scanner.useDelimiter("\\A").next();

        JAXBContext jaxbContext = JAXBContext.newInstance(PayloadData.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(response);
        PayloadData inquiryDataXML = (PayloadData) unmarshaller.unmarshal(reader);
//         Parse XML response
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(response)));
//
//        // Print extracted values
//        System.out.println("Code: " + document.getElementsByTagName("code").item(0).getTextContent());
//        System.out.println("Nomor Pembayaran: " + document.getElementsByTagName("nomorPembayaran").item(0).getTextContent());
//        System.out.println("ID Tagihan: " + document.getElementsByTagName("idTagihan").item(0).getTextContent());
//        System.out.println("Nomor Induk: " + document.getElementsByTagName("nomorInduk").item(0).getTextContent());
//        System.out.println("Kode Transaksi: "+ document.getElementsByTagName("kodeTransaksi").item(0).getTextContent());
//        System.out.println("Deskripsi Pendek: "+ document.getElementsByTagName("deskripsiPendek").item(0).getTextContent());
//        return new ResponseEntity<>(response, HttpStatus.OK);
        model.addAttribute("inquiryDataXML", inquiryDataXML);
        return "payment";
    }

    @GetMapping("/reversal")
    public String reversal() {
        return "reversal";
    }


    @PostMapping(value = "/reversal", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postReversal(@RequestBody MultiValueMap<String, String> inquiryData, Model model) throws IOException, JAXBException, ParserConfigurationException, SAXException {

        String kodeBank = inquiryData.get("kodeBank").get(0);
        String kodeChannel = inquiryData.get("kodeChannel").get(0);
        String kodeTerminal = inquiryData.get("kodeTerminal").get(0);
        String nomorPembayaran = inquiryData.get("nomorPembayaran").get(0);
        String tanggalTransaksi = inquiryData.get("tanggalTransaksi").get(0);
        String tanggalTransaksiAsal = inquiryData.get("tanggalTransaksiAsal").get(0);
        String idTransaksi = inquiryData.get("idTransaksi").get(0);
        String idTagihan = inquiryData.get("idTagihan").get(0);
        String nominalTransaksi = inquiryData.get("nominalTransaksi").get(0);


        String postData = String.format("action=reversal&kodeBank=%s&kodeChannel=%s&kodeTerminal=%s&nomorPembayaran=%s&tanggalTransaksi=%s&tanggalTransaksiAsal=%s&idTransaksi=%s&idTagihan=%s&nominalTransaksi=%s",
                kodeBank,
                kodeChannel,
                kodeTerminal,
                nomorPembayaran,
                tanggalTransaksi,
                tanggalTransaksiAsal,
                idTransaksi,
                idTagihan,
                nominalTransaksi);

        URL url = new URL("http://localhost/index.php");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Write form data to connection
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(postData);
        writer.flush();

        // Read XML response from connection
        InputStream inputStream = connection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        String response = scanner.useDelimiter("\\A").next();

        JAXBContext jaxbContext = JAXBContext.newInstance(PayloadData.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(response);
        PayloadData inquiryDataXML = (PayloadData) unmarshaller.unmarshal(reader);
//         Parse XML response
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(response)));
////
////        // Print extracted values
////        System.out.println("Code: " + document.getElementsByTagName("code").item(0).getTextContent());
////        System.out.println("Nomor Pembayaran: " + document.getElementsByTagName("nomorPembayaran").item(0).getTextContent());
////        System.out.println("ID Tagihan: " + document.getElementsByTagName("idTagihan").item(0).getTextContent());
////        System.out.println("Nomor Induk: " + document.getElementsByTagName("nomorInduk").item(0).getTextContent());
////        System.out.println("Kode Transaksi: "+ document.getElementsByTagName("kodeTransaksi").item(0).getTextContent());
////        System.out.println("Deskripsi Pendek: "+ document.getElementsByTagName("deskripsiPendek").item(0).getTextContent());
////        return new ResponseEntity<>(response, HttpStatus.OK);
        model.addAttribute("inquiryDataXML", inquiryDataXML);
        return "reversal";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals("anonymousUser")) return "redirect:/";
        model.addAttribute("authName", auth.getName());
        return "login";
    }



}
