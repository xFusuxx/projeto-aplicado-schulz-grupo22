import os 
from classes import Tecnico, Equipamento, Medicao, BancoDadosDAO

def main():
    print("=" * 90)
    print("      PROJETO APLICADO I - SCHULZ S.A. | SISTEMA DE CALIBRAÇÃO (AV 03)")
    print("=" * 90)

    db_name = "schulz_calibracao.db"
    dao = BancoDadosDAO(db_name)
    print(f"[+] Banco de dados '{db_name}' e tabelas verificados com sucesso.\n")

 
    print("--> Cadastrando Técnicos...")
    t1 = Tecnico(nome_tecnico="José Altair Araujo Camargo", matricula="T-1001", email="jose.camargo@schulz.com.br", setor="Metrologia MMC")
    t2 = Tecnico(nome_tecnico="Guilherme Ceschiatti", matricula="T-1002", email="guilherme.ceschiatti@schulz.com.br", setor="Garantia da Qualidade")
    
    t1_id = dao.salvar_tecnico(t1)
    t2_id = dao.salvar_tecnico(t2)
    print(f"    - Técnico: {t1.nome_tecnico} (ID: {t1_id})")
    print(f"    - Técnico: {t2.nome_tecnico} (ID: {t2_id})\n")

    print("--> Cadastrando Equipamentos...")
    e1 = Equipamento(codigo_tag="MMC-01", nome_equipamento="Máquina MMC", fabricante="Zeiss", modelo="Contura G2")
    e2 = Equipamento(codigo_tag="MIC-02", nome_equipamento="Micrômetro Digital", fabricante="Mitutoyo", modelo="QuantuMike 0-25mm")

    e1_id = dao.salvar_equipamento(e1)
    e2_id = dao.salvar_equipamento(e2)
    print(f"    - Equipamento: {e1.nome_equipamento} (TAG: {e1.codigo_tag}, ID: {e1_id})")
    print(f"    - Equipamento: {e2.nome_equipamento} (TAG: {e2.codigo_tag}, ID: {e2_id})\n")

   
    print("--> Registrando Medições e Executando Regras de Negócio...")
    
    m1 = Medicao(
        id_equipamento=e1_id,
        id_tecnico=t1_id,
        parametro_medido="Diâmetro Interno A",
        valor_nominal=100.000,
        tolerancia_min=-0.005,
        tolerancia_max=0.005,
        valor_obtido=100.002
    )
    

    m2 = Medicao(
        id_equipamento=e2_id,
        id_tecnico=t2_id,
        parametro_medido="Espessura Parede",
        valor_nominal=25.000,
        tolerancia_min=-0.003,
        tolerancia_max=0.003,
        valor_obtido=25.015
    )

    dao.salvar_medicao(m1)
    dao.salvar_medicao(m2)

    print(f"    - Medição 1 [{m1.parametro_medido}]: Erro={m1.valor_obtido - m1.valor_nominal:+.4f} | Status: {m1.status_resultado}")
    print(f"    - Medição 2 [{m2.parametro_medido}]: Erro={m2.valor_obtido - m2.valor_nominal:+.4f} | Status: {m2.status_resultado}\n")

    
    print("=" * 90)
    print("                      RELATÓRIO DE CALIBRAÇÃO (CONSULTA JOIN)")
    print("=" * 90)

    relatorio = dao.listar_relatorio_calibracao()
    header = f"{'TAG':<8} | {'EQUIPAMENTO':<20} | {'TÉCNICO':<22} | {'PARÂMETRO':<18} | {'OBTIDO':<8} | {'STATUS':<9}"
    print(header)
    print("-" * 90)

    for item in relatorio:
        linha = f"{item['codigo_tag']:<8} | {item['nome_equipamento']:<20} | {item['nome_tecnico']:<22} | {item['parametro_medido']:<18} | {item['valor_obtido']:<8.3f} | {item['status_resultado']:<9}"
        print(linha)

    print("=" * 90)
    print("[✔] Demonstração concluída com sucesso.")

if __name__ == "__main__":
    main()