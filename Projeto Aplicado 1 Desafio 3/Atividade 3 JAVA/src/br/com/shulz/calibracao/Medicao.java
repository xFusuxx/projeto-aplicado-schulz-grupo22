/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.shulz.calibracao;

/**
 *
 * @author José Altair
 */
public class Medicao {
    //Atributos 
    private int idMedicao;
    private int idTecnico;
    private int idEquipamento;
    private String parametroMedido;
    private double valorNominal;
    private double toleranciaMin;
    private double toleranciaMax;
    private double valorObtido;
    private String statusResultado;
    private String dataMedicao;
    
    //Construtor

        public Medicao(int idMedicao, int idTecnico, int idEquipamento, String parametroMedido, double valorNominal, double toleranciaMin, double toleranciaMax, double valorObtido, String dataMedicao) {
            this.idMedicao = idMedicao;
            this.idTecnico = idTecnico;
            this.idEquipamento = idEquipamento;
            this.parametroMedido = parametroMedido;
            this.valorNominal = valorNominal;
            this.toleranciaMin = toleranciaMin;
            this.toleranciaMax = toleranciaMax;
            this.valorObtido = valorObtido;
            this.dataMedicao = dataMedicao;
            
        calcularStatus();    
    }
    //Calcular o resultado da medição
    private void calcularStatus() {
        //Validar se a tolerância mínima não é maior que a máxima,
        //evitando limites de medição informadas incorretamente
        if(toleranciaMin > toleranciaMax) {
            throw new IllegalArgumentException("A tolerância mínima não pode ser maior que a máxima.");
            
        }
        
        double erro = valorObtido - valorNominal;
        if (erro >= toleranciaMin && erro <= toleranciaMax) {
            statusResultado = "APROVADO";            
        } else {
            statusResultado = "REPROVADO";
        }
    }
    //Usando Método Getters

    public int getIdMedicao() {
        return idMedicao;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public int getIdEquipamento() {
        return idEquipamento;
    }

    public String getParametroMedido() {
        return parametroMedido;
    }

    public double getValorNominal() {
        return valorNominal;
    }

    public double getToleranciaMin() {
        return toleranciaMin;
    }

    public double getToleranciaMax() {
        return toleranciaMax;
    }

    public double getValorObtido() {
        return valorObtido;
    }

    public String getStatusResultado() {
        return statusResultado;
    }

    public String getDataMedicao() {
        return dataMedicao;
    }

    
    
    
    
}
