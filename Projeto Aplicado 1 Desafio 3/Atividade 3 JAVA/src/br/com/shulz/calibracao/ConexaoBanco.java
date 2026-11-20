/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.shulz.calibracao;
// Importar as classes necessárias para trabalhar com SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author José Altair
 */
public class ConexaoBanco {
    //Caminho do arquivo de banco de dados
    private static final String URL = "jdbc:sqlite:banco/schulz_calibracao.db";
    //Método responsável por abria a conexão
    public static Connection conectar()throws SQLException {
        return  DriverManager.getConnection(URL);
        
    }
    
}
