/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.shulz.calibracao;

/**
 *
 * @author José Altair
 */
public class Equipamento {
    //Atributos do equipamento
    private int idEquipamento;
    private String codigoTag;
    private String nomeEquipamento;
    private String fabricante;
    private String modelo;
    
    //Construtor
    public Equipamento(int idEquipamento, String codigoTag, String nomeEquipamento,
        String fabricante, String modelo) {
            this.idEquipamento = idEquipamento;
            this.codigoTag = codigoTag;
            this.nomeEquipamento = nomeEquipamento;
            this.fabricante = fabricante;
            this.modelo = modelo;
    }         
        
        //Usando Métodos Getters para consulta de dados

    public int getIdEquipamento() {
        return idEquipamento;
    }

    public String getCodigoTag() {
        return codigoTag;
    }

    public String getNomeEquipamento() {
        return nomeEquipamento;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getModelo() {
        return modelo;
    }
    
}
    
     

   



