package br.upf.sd.model;


/**
 *
 * @author Fabricio Bedin
 */
public class Carro {
    private Integer codigo;
    private String marca;
    private String modelo;
    private Integer ano;
    private Float potencia;
    private Float carga;
    private String complemento;

    public Carro() {
    }
    
        public Carro(Integer codigo, String marca, String modelo, Integer ano, float potencia, float carga, String complemento) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.potencia = potencia;
        this.carga = carga;
        this.complemento = complemento;
    }

    public Carro(Integer codigo) {
        this.codigo = codigo;
    }

    public Carro(String modelo, Integer ano) {
        this.modelo = modelo;
        this.ano = ano;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public Float getPotencia() {
        return potencia;
    }

    public Float getCarga() {
        return carga;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public void setPotencia(Float potencia) {
        this.potencia = potencia;
    }

    public void setCarga(Float carga) {
        this.carga = carga;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Carro(int codigo, String marca, String modelo, Integer ano, Float potencia, Float carga, String complemento) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.potencia = potencia;
        this.carga = carga;
        this.complemento = complemento;
    }

    
    @Override
    public String toString() {
        return   "Codigo: "+ this.getCodigo() + "\nMarca: " + this.getMarca() + "\nModelo: " + 
                this.getModelo() + "\nAno: " + this.getAno() + "\nCarga: " + this.getCarga() + "\nPotencia: " + this.getPotencia() + "\nComplemento: " + this.getComplemento() + "\n";
    }
    
    
}
