package xyz.nopalfi.ulmpaymentmanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.NumberFormat;
import java.util.Locale;

@XmlRootElement(name = "response")
@Getter
@Setter
public class PayloadData {
    private String code;
    private Data data;
    private Details details;
    private String message;
}
@Getter
@Setter
class Data {
    private String nomorPembayaran;
    private String idTagihan;
    private String nomorInduk;
    private String nama;
    private String fakultas;
    private String jurusan;
    private String strata;
    private String periode;
    private String angkatan;
    private String totalNominal;
    private String idTransaksi;
    private String nomorJurnalPembukuan;
    private String daftarPembayaran;

    public void setTotalNominal(String totalNominal) {
        double totalNominalInDouble = Double.parseDouble(totalNominal);
        NumberFormat indonesianCurrency = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        this.totalNominal = indonesianCurrency.format(totalNominalInDouble);
    }
}
@Getter
@Setter
class Details {
    private Item item;
}
@Getter
@Setter
class Item {
    private String kodeTransaksi;
    private String deskripsiPendek;
    private String deskripsiPanjang;
    private String nominalTransaksi;

    public void setNominalTransaksi(String nominalTransaksi) {
        double totalNominalTransaksiInDouble = Double.parseDouble(nominalTransaksi);
        NumberFormat indonesianCurrency = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        this.nominalTransaksi = indonesianCurrency.format(totalNominalTransaksiInDouble);
    }
}

