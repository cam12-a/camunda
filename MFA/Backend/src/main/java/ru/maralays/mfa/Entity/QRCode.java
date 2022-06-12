package ru.maralays.mfa.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "qrcode")


@JsonIgnoreProperties(ignoreUnknown = true)
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;
    @Column(name = "encode_qr_code")
    private String encodeQRCode;
    @Column(name = "date_send")
    @JsonIgnore
    private String dateSend;
    @Column(name = "date_receive")
    @JsonIgnore
    private String dateReceive;


    @Override
    public String toString() {
        return "QRCode{" +
                "id=" + id +
                ", users=" + users +
                ", encodeQRCode='" + encodeQRCode + '\'' +
                ", dateSend='" + dateSend + '\'' +
                ", dateReceive='" + dateReceive + '\'' +
                '}';
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getEncodeQRCode() {
        return encodeQRCode;
    }

    public void setEncodeQRCode(String encodeQRCode) {
        this.encodeQRCode = encodeQRCode;
    }




    public String getDateSend() {
        return dateSend;
    }

    public void setDateSend(String dateSend) {
        this.dateSend = dateSend;
    }

    public String getDateReceive() {
        return dateReceive;
    }

    public void setDateReceive(String dateReceive) {
        this.dateReceive = dateReceive;
    }
}
