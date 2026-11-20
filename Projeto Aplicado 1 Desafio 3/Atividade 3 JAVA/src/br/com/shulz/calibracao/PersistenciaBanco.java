/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.shulz.calibracao;
// Importações para trabalhar com banco de dados
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author José Altair
 */
public class PersistenciaBanco {
    //Método para cadastrar um técnico no banco
    public void inserirTecnico(Tecnico tecnico) throws SQLException {
        //Comando SQL para inserir os dados do técnico
        String sql = "INSERT INTO tecnico "
                + "(matricula, nome_tecnico, email, setor) "
                + "VALUES(?, ?, ?, ?)";
        //Abre a conexão e prepara o comando SQL
        try(Connection conexao = ConexaoBanco.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {
            
      
             //Preenche os parâmetros do comando SQL
        comando.setString(1, tecnico.getMatricula());
        comando.setString(2, tecnico.getNomeTecnico());
        comando.setString(3, tecnico.getEmail());
        comando.setString(4, tecnico.getSetor());
            // Executar a inserção no banco
        comando.executeUpdate();
        
        
        }   
    }       

    //Método para consultar e listar os técnicos cadastrados
    public void listarTecnicos() throws SQLException {
        //Comando SQL para buscar os Técnicos
        String sql = "SELECT * FROM tecnico";
        //Abre a conexão e prepara a consulta
        try (Connection conexao = ConexaoBanco.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()) {
            //Percorre os registros encontrados
            while(resultado.next()) {
                System.out.println("ID: " + resultado.getInt("id_tecnico"));
                System.out.println("Matrícula: " + resultado.getString("matricula"));
                System.out.println("Nome: " + resultado.getString("nome_tecnico"));
                System.out.println("E-mail: " + resultado.getString("email"));
                System.out.println("Setor: " + resultado.getString("setor"));
                
                System.out.println("---------------------------");
            }
              
        }
    }
    //Método para cadastrar um equipamento no banco
    public void inserirEquipamento(Equipamento equipamento) throws SQLException {
        //Comando SQL para inserir os dados do equipamento
        String sql = "Insert Into equipamento "
                + "(codigo_tag, nome_equipamento, fabricante, modelo) "
                + "VALUES(?, ?, ?, ?)";
        //Abre a conexão e prepara o comando SQL
        try(Connection conexao = ConexaoBanco.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {
            //Preenche os parâmetros do comando SQL
            comando.setString(1, equipamento.getCodigoTag());
            comando.setString(2, equipamento.getNomeEquipamento());
            comando.setString(3, equipamento.getFabricante());
            comando.setString(4, equipamento.getModelo());
            //Executa a inserção no banco
            comando.executeUpdate();            
     
        }
    }
    //Método responsável por consultar e listar os equipamentos cadastrados
    public void listarEquipamentos() throws SQLException {
        //Comando SQL para buscar todos os equipamentos da tabela
        String sql = "SELECT * FROM equipamento";
        //Abre a conexão com o banco e prepara o comando SQL
        //O resultset recebe os registros retornado pela consulta
        try(Connection conexao = ConexaoBanco.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()) {
            //Percorre os registros encontrados no banco de dados
            while(resultado.next()) {
                System.out.println("ID: " + resultado.getString("id_equipamento"));
                System.out.println("Código TAG: " + resultado.getString("codigo_tag"));
                System.out.println("Nome: " + resultado.getString("nome_equipamento"));
                System.out.println("Fabricante: " + resultado.getString("fabricante"));
                System.out.println("Modelo: " + resultado.getString("modelo"));
                
                System.out.println("---------------------------------");
            }
        }
        
    }
    //Método responsável por cadastrar uma medição no banco de dados
    public void inserirMedicao(Medicao medicao) throws SQLException {
        //Comando SQL para inserir uma nova medição na tabela
        String sql = "INSERT INTO medicao "
                + "(id_equipamento, id_tecnico, parametro_medido, "
                + "valor_nominal, tolerancia_min, tolerancia_max, "
                + "valor_obtido, status_resultado, data_medicao) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //Abre a conexão com o banco e prepara o comando SQL
        try(Connection conexao = ConexaoBanco.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, medicao.getIdEquipamento());           
            comando.setInt(2, medicao.getIdTecnico());        
            comando.setString(3, medicao.getParametroMedido());        
            comando.setDouble(4, medicao.getValorNominal());        
            comando.setDouble(5, medicao.getToleranciaMin());        
            comando.setDouble(6, medicao.getToleranciaMax());        
            comando.setDouble(7, medicao.getValorObtido());        
            comando.setString(8, medicao.getStatusResultado());        
            comando.setString(9, medicao.getDataMedicao());
            // Executa a inserção dos dados no banco
            comando.executeUpdate();
            
        }
    }
    //Método responsável por consultar as medições
    //relacionando as tabelas medição, técnico e equipamento
    public void listarMedicoes() throws SQLException {
        //Comando SQL que relaciona as três tabelas
        String sql = "SELECT "
                + "m.id_medicao, "
                + "t.nome_tecnico, "
                + "e.codigo_tag, "
                + "e.nome_equipamento, "
                + "m.parametro_medido, "
                + "m.valor_nominal, "
                + "m.tolerancia_min, "
                + "m.tolerancia_max, "
                + "m.valor_obtido, "
                + "m.status_resultado, "
                + "m.data_medicao "
                + "FROM medicao m "
                + "INNER JOIN tecnico t ON m.id_tecnico = t.id_tecnico "
                + "INNER JOIN equipamento e ON m.id_equipamento = e.id_equipamento";

    // Abre a conexão e prepara a consulta SQL
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql);
         ResultSet resultado = comando.executeQuery()) {
        // Percorre todas as medições encontradas
        while (resultado.next()) {            
            System.out.println("ID da medição: "
                    + resultado.getInt("id_medicao"));            
            System.out.println("Técnico: "
                    + resultado.getString("nome_tecnico"));            
            System.out.println("Código TAG: "
                    + resultado.getString("codigo_tag"));            
            System.out.println("Equipamento: "
                    + resultado.getString("nome_equipamento"));           
            System.out.println("Parâmetro: "
                    + resultado.getString("parametro_medido"));            
            System.out.println("Valor nominal: "
                    + resultado.getDouble("valor_nominal"));
            System.out.println("Tolerância mínima: "
                    + resultado.getDouble("tolerancia_min"));
            System.out.println("Tolerância máxima: "
                    + resultado.getDouble("tolerancia_max"));
            System.out.println("Valor obtido: "
                    + resultado.getDouble("valor_obtido"));            
            System.out.println("Resultado: "
                    + resultado.getString("status_resultado"));            
            System.out.println("Data: "
                    + resultado.getString("data_medicao"));
            
            System.out.println("--------------------------------");
            
            }
                
        }
    }
    // Método responsável por atualizar os dados de um equipamento
    public void atualizarEquipamento(Equipamento equipamento) throws SQLException {

    // Comando SQL para atualizar os dados do equipamento
    String sql = "UPDATE equipamento "
            + "SET codigo_tag = ?, "
            + "nome_equipamento = ?, "
            + "fabricante = ?, "
            + "modelo = ? "
            + "WHERE id_equipamento = ?";

    // Abre a conexão com o banco e prepara o comando SQL
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {        
        comando.setString(1, equipamento.getCodigoTag());        
        comando.setString(2, equipamento.getNomeEquipamento());        
        comando.setString(3, equipamento.getFabricante());        
        comando.setString(4, equipamento.getModelo());        
        comando.setInt(5, equipamento.getIdEquipamento());        
        int linhasAlteradas = comando.executeUpdate();

        // Verifica se algum registro foi alterado
        if (linhasAlteradas > 0) {
            System.out.println("Equipamento atualizado com sucesso!");
        } else {
            System.out.println("Nenhum equipamento encontrado para atualizar.");
        }
    }
}    
    // Método responsável por excluir um equipamento pelo ID
    public void excluirEquipamento(int idEquipamento) throws SQLException {
    // Comando SQL para excluir o equipamento informado
    String sql = "DELETE FROM equipamento "
            + "WHERE id_equipamento = ?";
    // Abre a conexão e prepara o comando SQL
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {       
        comando.setInt(1, idEquipamento);        
        int linhasAlteradas = comando.executeUpdate();        
        if (linhasAlteradas > 0) {
            System.out.println("Equipamento excluído com sucesso!");
        } else {
            System.out.println("Equipamento não encontrado.");
        }
    }
}
   // Método responsável por atualizar os dados de um técnico
    public void atualizarTecnico(Tecnico tecnico) throws SQLException {

    // Comando SQL para atualizar os dados do técnico
    String sql = "UPDATE tecnico "
            + "SET matricula = ?, "
            + "nome_tecnico = ?, "
            + "email = ?, "
            + "setor = ? "
            + "WHERE id_tecnico = ?";
    // Abre a conexão com o banco e prepara o comando SQL
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {        
        comando.setString(1, tecnico.getMatricula());        
        comando.setString(2, tecnico.getNomeTecnico());        
        comando.setString(3, tecnico.getEmail());        
        comando.setString(4, tecnico.getSetor());        
        comando.setInt(5, tecnico.getIdTecnico());        
        int linhasAlteradas = comando.executeUpdate();        
        if (linhasAlteradas > 0) {
            System.out.println("Técnico atualizado com sucesso!");
        } else {
            System.out.println("Nenhum técnico encontrado para atualizar.");
        }
    }
}
    // Método responsável por excluir um técnico pelo ID
    public void excluirTecnico(int idTecnico) throws SQLException {
    // Comando SQL para excluir o técnico informado
    String sql = "DELETE FROM tecnico "
            + "WHERE id_tecnico = ?";
    // Abre a conexão com o banco e prepara o comando SQL
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {        
        comando.setInt(1, idTecnico);        
        int linhasAlteradas = comando.executeUpdate();       
        if (linhasAlteradas > 0) {
            System.out.println("Técnico excluído com sucesso!");
        } else {
            System.out.println("Técnico não encontrado.");
        }
    }
}
    // Método responsável por atualizar uma medição
    public void atualizarMedicao(Medicao medicao) throws SQLException {
    // Comando SQL para atualizar os dados da medição
    String sql = "UPDATE medicao "
            + "SET parametro_medido = ?, "
            + "valor_nominal = ?, "
            + "tolerancia_min = ?, "
            + "tolerancia_max = ?, "
            + "valor_obtido = ?, "
            + "status_resultado = ?, "
            + "data_medicao = ? "
            + "WHERE id_medicao = ?";
    // Abre a conexão e prepara o comando SQL
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {        
        comando.setString(1, medicao.getParametroMedido());
        comando.setDouble(2, medicao.getValorNominal());
        comando.setDouble(3, medicao.getToleranciaMin());
        comando.setDouble(4, medicao.getToleranciaMax());
        comando.setDouble(5, medicao.getValorObtido());        
        comando.setString(6, medicao.getStatusResultado());
        comando.setString(7, medicao.getDataMedicao());       
        comando.setInt(8, medicao.getIdMedicao());
        // Executa a atualização no banco
        int linhasAlteradas = comando.executeUpdate();
        // Verifica se a atualização foi realizada
        if (linhasAlteradas > 0) {
            System.out.println("Medição atualizada com sucesso!");
        } else {
            System.out.println("Medição não encontrada.");
        }
    }
}
    public void excluirMedicao(int idMedicao) throws SQLException {

    String sql = "DELETE FROM medicao WHERE id_medicao = ?";

    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {

        comando.setInt(1, idMedicao);

        int linhasAlteradas = comando.executeUpdate();

        if (linhasAlteradas > 0) {
            System.out.println("Medição excluída com sucesso!");
        } else {
            System.out.println("Medição não encontrada.");
        }
    }
}
    // Método para buscar o ID de um técnico pela matrícula
    public int buscarIdTecnico(String matricula) throws SQLException {
    String sql = "SELECT id_tecnico FROM tecnico WHERE matricula = ?";
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {
        comando.setString(1, matricula);
        try (ResultSet resultado = comando.executeQuery()) {
            if (resultado.next()) {
                return resultado.getInt("id_tecnico");
            } else {
                return -1;
            }
        }
    }
}
    // Método para buscar o ID de um equipamento pelo código TAG
    public int buscarIdEquipamento(String codigoTag) throws SQLException {
    String sql = "SELECT id_equipamento FROM equipamento WHERE codigo_tag = ?";
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {
        comando.setString(1, codigoTag);
        try (ResultSet resultado = comando.executeQuery()) {
            if (resultado.next()) {
                return resultado.getInt("id_equipamento");
            } else {
                return -1;
            }
        }
    }
}
    // Método para verificar se uma medição já existe
    public boolean medicaoExiste(int idEquipamento, int idTecnico,
        String parametro, String data) throws SQLException {
    String sql = "SELECT id_medicao FROM medicao "
            + "WHERE id_equipamento = ? "
            + "AND id_tecnico = ? "
            + "AND parametro_medido = ? "
            + "AND data_medicao = ?";
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement comando = conexao.prepareStatement(sql)) {
        comando.setInt(1, idEquipamento);
        comando.setInt(2, idTecnico);
        comando.setString(3, parametro);
        comando.setString(4, data);
        try (ResultSet resultado = comando.executeQuery()) {
            return resultado.next();
        }
    }
}

    // Método para buscar o ID de uma medição pelos critérios principais
    public int buscarIdMedicao(int idEquipamento, int idTecnico,
            String parametro, String data) throws SQLException {
        String sql = "SELECT id_medicao FROM medicao "
                + "WHERE id_equipamento = ? "
                + "AND id_tecnico = ? "
                + "AND parametro_medido = ? "
                + "AND data_medicao = ?";
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idEquipamento);
            comando.setInt(2, idTecnico);
            comando.setString(3, parametro);
            comando.setString(4, data);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("id_medicao");
                }
                return -1;
            }
        }
    }
}