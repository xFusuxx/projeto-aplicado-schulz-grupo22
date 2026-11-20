/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.shulz.calibracao;

/**
 *
 * @author José Altair
 */
public class Tecnico {
    //Atributos do Técnico
    private int idTecnico;
    private String matricula;
    private String nomeTecnico;
    private String email;
    private String setor;
    
    //Construtor
    public Tecnico(int idTecnico, String matricula, String nomeTecnico,
            String email, String setor) {
        this.idTecnico = idTecnico;
        this.matricula = matricula;
        this.nomeTecnico = nomeTecnico;
        this.email = email;
        this.setor = setor;       
      
     
        
    }
    //Usando Métodos Getters para consultar dados

    public int getIdTecnico() {
        return idTecnico;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNomeTecnico() {
        return nomeTecnico;
    }
    
    public String getEmail() {
        return email;
    }

    public String getSetor() {
        return setor;
    }
    
}
