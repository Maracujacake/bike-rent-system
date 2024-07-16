package domain;
import java.time.LocalDateTime;

import utils.DataUtils;


public class Locacao {
    private Long id;
    private String cpfCliente;
    private String cnpjLocadora;
    private LocalDateTime registro;


    // Construtor
    public Locacao(Long id, String cpfCliente, String cnpjLocadora, LocalDateTime registro) {
        this.id = id;
        this.cpfCliente = cpfCliente;
        this.cnpjLocadora = cnpjLocadora;
        setRegistro(registro);
    }

    // Construtor
    public Locacao(String cpfCliente, String cnpjLocadora, LocalDateTime registro) {
        this.cpfCliente = cpfCliente;
        this.cnpjLocadora = cnpjLocadora;
        setRegistro(registro);
    }

    public Long getId() {
        return id;
    }

    // Getters e Setters
    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getCnpjLocadora() {
        return cnpjLocadora;
    }

    public void setCnpjLocadora(String cnpjLocadora) {
        this.cnpjLocadora = cnpjLocadora;
    }

    public String getRegistro() {
        return DataUtils.formatLocalDateTime(registro);
    }

    public LocalDateTime getRegistroAsDateTime() {
        return registro;
    }


    public void setRegistro(LocalDateTime registro) {
        this.registro = registro;
    }

    // Método de ajuste automático
    public static LocalDateTime ajustarParaHoraCheia(LocalDateTime dateTime) {
        if (dateTime.getMinute() != 0 || dateTime.getSecond() != 0 || dateTime.getNano() != 0) {
            dateTime = dateTime.plusHours(1);
        }
        return dateTime.withMinute(0).withSecond(0).withNano(0);

    }
}
