/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package br.com.shulz.calibracao;


/*
 * PROJETO APLICADO I - SCHULZ
 *
 * CRUD IMPLEMENTADO E TESTADO
 *
 * CRUD representa quatro operações básicas:
 * C - CREATE: Criar registros (INSERT).
 * R - READ: Consultar registros (SELECT).
 * U - UPDATE: Atualizar registros (UPDATE).
 * D - DELETE: Excluir registros (DELETE).
 *
 * As quatro operações foram implementadas e testadas
 * nas classes Tecnico, Equipamento e Medicao.
 *
 * Classe Tecnico:
 * - INSERT: Cadastro de técnicos.
 * - SELECT: Listagem de técnicos.
 * - UPDATE: Atualização de técnicos.
 * - DELETE: Exclusão de técnicos.
 *
 * Classe Equipamento:
 * - INSERT: Cadastro de equipamentos.
 * - SELECT: Listagem de equipamentos.
 * - UPDATE: Atualização de equipamentos.
 * - DELETE: Exclusão de equipamentos.
 *
 * Classe Medicao:
 * - INSERT: Cadastro de medições.
 * - SELECT: Listagem de medições com JOIN.
 * - UPDATE: Atualização de medições.
 * - DELETE: Exclusão de medições.
 *
 * Todos os métodos CRUD foram implementados
 * e testados individualmente com sucesso.
 */

/**
 *
 * @author José Altair
 */
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        // Criar o objeto responsável pela persistência
        PersistenciaBanco banco = new PersistenciaBanco();
        // Criar um objeto Técnico
        Tecnico tecnico = new Tecnico(
                0,
                "MAT003",
                "Pedro Santos",
                "pedro@schulz.com.br",
                "Metrologia"
        );
        // Criar um objeto Equipamento
        Equipamento equipamento = new Equipamento(
                0,
                "EQP003",
                "Paquímetro Digital",
                "Mitutoyo",
                "500-196-30"
        );
        // Verificar se o técnico já está cadastrado
        int idTecnico = banco.buscarIdTecnico("MAT003");

        if (idTecnico == -1) {
            banco.inserirTecnico(tecnico);
            System.out.println("Técnico cadastrado com sucesso!");
            // Buscar o ID após a inserção
            idTecnico = banco.buscarIdTecnico("MAT003");
        } else {
            System.out.println("Técnico já cadastrado!");
        }
        // Verificar se o equipamento já está cadastrado
        int idEquipamento = banco.buscarIdEquipamento("EQP003");
        if (idEquipamento == -1) {
            banco.inserirEquipamento(equipamento);
            System.out.println("Equipamento cadastrado com sucesso!");
            // Buscar o ID após a inserção
            idEquipamento = banco.buscarIdEquipamento("EQP003");
        } else {
            System.out.println("Equipamento já cadastrado!");
        }
        // Criar um objeto Medicao
        Medicao medicao = new Medicao(
                0,
                idTecnico,
                idEquipamento,
                "Dimensão externa",
                50.000,
                -0.025,
                0.025,
                50.018,
                "03/11/2026"
        );
        System.out.println("Medição criada com sucesso!");
        System.out.println("Resultado: " + medicao.getStatusResultado());
        // Verificar se a medição já existe
        boolean existe = banco.medicaoExiste(
                idEquipamento,
                idTecnico,
                "Dimensão externa",
                "03/11/2026"
        );
        if (!existe) {
            banco.inserirMedicao(medicao);
            System.out.println("Medição cadastrada com sucesso!");
        } else {
            System.out.println("Essa medição já está cadastrada!");
        }

        // ------------------------------------------------
        // DEMONSTRAÇÃO DE UPDATE E DELETE (CRUD)
        // Usa registros temporários para não alterar
        // os dados permanentes do relatório de calibração.
        // ------------------------------------------------
        System.out.println("\n================================");
        System.out.println("DEMONSTRAÇÃO UPDATE / DELETE");
        System.out.println("================================");

        // CREATE temporário: técnico e equipamento de demo
        Tecnico tecnicoDemo = new Tecnico(
                0,
                "MAT999",
                "Técnico Demo",
                "demo@schulz.com.br",
                "Metrologia"
        );
        Equipamento equipamentoDemo = new Equipamento(
                0,
                "EQP999",
                "Comparador Demo",
                "Mitutoyo",
                "DEMO-01"
        );

        int idTecnicoDemo = banco.buscarIdTecnico("MAT999");
        if (idTecnicoDemo == -1) {
            banco.inserirTecnico(tecnicoDemo);
            idTecnicoDemo = banco.buscarIdTecnico("MAT999");
            System.out.println("Técnico demo cadastrado (CREATE).");
        }

        int idEquipamentoDemo = banco.buscarIdEquipamento("EQP999");
        if (idEquipamentoDemo == -1) {
            banco.inserirEquipamento(equipamentoDemo);
            idEquipamentoDemo = banco.buscarIdEquipamento("EQP999");
            System.out.println("Equipamento demo cadastrado (CREATE).");
        }

        // UPDATE: atualiza técnico e equipamento demo
        Tecnico tecnicoAtualizado = new Tecnico(
                idTecnicoDemo,
                "MAT999",
                "Técnico Demo Atualizado",
                "demo.atualizado@schulz.com.br",
                "Qualidade"
        );
        banco.atualizarTecnico(tecnicoAtualizado);

        Equipamento equipamentoAtualizado = new Equipamento(
                idEquipamentoDemo,
                "EQP999",
                "Comparador Demo Atualizado",
                "Mitutoyo",
                "DEMO-02"
        );
        banco.atualizarEquipamento(equipamentoAtualizado);

        // CREATE + UPDATE + DELETE de medição demo
        String parametroDemo = "Ensaio demo UPDATE/DELETE";
        String dataDemo = "18/11/2026";

        int idMedicaoDemo = banco.buscarIdMedicao(
                idEquipamentoDemo,
                idTecnicoDemo,
                parametroDemo,
                dataDemo
        );
        if (idMedicaoDemo == -1) {
            Medicao medicaoDemo = new Medicao(
                    0,
                    idTecnicoDemo,
                    idEquipamentoDemo,
                    parametroDemo,
                    10.000,
                    -0.050,
                    0.050,
                    10.020,
                    dataDemo
            );
            banco.inserirMedicao(medicaoDemo);
            idMedicaoDemo = banco.buscarIdMedicao(
                    idEquipamentoDemo,
                    idTecnicoDemo,
                    parametroDemo,
                    dataDemo
            );
            System.out.println("Medição demo cadastrada (CREATE). Resultado: "
                    + medicaoDemo.getStatusResultado());
        }

        // UPDATE: altera o valor obtido (deve reprovar)
        Medicao medicaoAtualizada = new Medicao(
                idMedicaoDemo,
                idTecnicoDemo,
                idEquipamentoDemo,
                parametroDemo,
                10.000,
                -0.050,
                0.050,
                10.200,
                dataDemo
        );
        System.out.println("Novo resultado da medição demo: "
                + medicaoAtualizada.getStatusResultado());
        banco.atualizarMedicao(medicaoAtualizada);

        // DELETE: remove a medição, depois equipamento e técnico demo
        banco.excluirMedicao(idMedicaoDemo);
        banco.excluirEquipamento(idEquipamentoDemo);
        banco.excluirTecnico(idTecnicoDemo);

        // Exibir relatório de calibração
        System.out.println("\n================================");
        System.out.println("RELATÓRIO DE CALIBRAÇÃO");
        System.out.println("================================");

        banco.listarMedicoes();

        System.out.println("SISTEMA DE CALIBRAÇÃO - SCHULZ");
        System.out.println("--------------------------------");

        System.out.println("Objetos criados com sucesso!");
        System.out.println("Técnico: " + tecnico.getNomeTecnico());
        System.out.println("Equipamento: " + equipamento.getNomeEquipamento());
    }
}
