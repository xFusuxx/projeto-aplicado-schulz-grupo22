import sqlite3
from typing import Optional, List, Dict, Any

class Tecnico:
    """Entidade que representa o profissional calibrador."""
    def __init__(self, nome_tecnico: str, matricula: str, email: str, setor: str, id_tecnico: Optional[int] = None):
        self.id_tecnico = id_tecnico
        self.nome_tecnico = nome_tecnico
        self.matricula = matricula
        self.email = email
        self.setor = setor

class Equipamento:
    """Entidade que representa o instrumento ou dispositivo calibrado."""
    def __init__(self, codigo_tag: str, nome_equipamento: str, fabricante: str, modelo: str, id_equipamento: Optional[int] = None):
        self.id_equipamento = id_equipamento
        self.codigo_tag = codigo_tag
        self.nome_equipamento = nome_equipamento
        self.fabricante = fabricante
        self.modelo = modelo

class Medicao:
    """Encapsula o ensaio de calibração e a validação de tolerância metrológica."""
    def __init__(self, id_equipamento: int, id_tecnico: int, parametro_medido: str,
                 valor_nominal: float, tolerancia_min: float, tolerancia_max: float,
                 valor_obtido: float, data_medicao: Optional[str] = None,
                 id_medicao: Optional[int] = None):
        self.id_medicao = id_medicao
        self.id_equipamento = id_equipamento
        self.id_tecnico = id_tecnico
        self.parametro_medido = parametro_medido
        self.valor_nominal = valor_nominal
        self.tolerancia_min = tolerancia_min
        self.tolerancia_max = tolerancia_max
        self.valor_obtido = valor_obtido
        self.data_medicao = data_medicao
        self.status_resultado = self.validar_status()

    def validar_status(self) -> str:
        """
        Valida a tolerância metrológica com base no erro obtido (valor_obtido - valor_nominal).
        Retorna 'APROVADO' se estiver entre min e max, caso contrário 'REPROVADO'.
        """
        erro = round(self.valor_obtido - self.valor_nominal, 4)
        if self.tolerancia_min <= erro <= self.tolerancia_max:
            return "APROVADO"
        else:
            return "REPROVADO"

class BancoDadosDAO:
    """Gerencia a conexão com o SQLite e as operações CRUD."""
    def __init__(self, db_name: str = "schulz_calibracao.db"):
        self.db_name = db_name
        self._criar_tabelas()

    def _get_connection(self):
        conn = sqlite3.connect(self.db_name)
        conn.execute("PRAGMA foreign_keys = ON;")
        return conn

    def _criar_tabelas(self):
        sql_schema = """
        CREATE TABLE IF NOT EXISTS tecnico (
            id_tecnico INTEGER PRIMARY KEY AUTOINCREMENT,
            nome_tecnico TEXT NOT NULL,
            matricula TEXT NOT NULL UNIQUE,
            email TEXT NOT NULL,
            setor TEXT NOT NULL
        );

        CREATE TABLE IF NOT EXISTS equipamento (
            id_equipamento INTEGER PRIMARY KEY AUTOINCREMENT,
            codigo_tag TEXT NOT NULL UNIQUE,
            nome_equipamento TEXT NOT NULL,
            fabricante TEXT NOT NULL,
            modelo TEXT NOT NULL
        );

        CREATE TABLE IF NOT EXISTS medicao (
            id_medicao INTEGER PRIMARY KEY AUTOINCREMENT,
            id_equipamento INTEGER NOT NULL,
            id_tecnico INTEGER NOT NULL,
            parametro_medido TEXT NOT NULL,
            valor_nominal REAL NOT NULL,
            tolerancia_min REAL NOT NULL,
            tolerancia_max REAL NOT NULL,
            valor_obtido REAL NOT NULL,
            status_resultado TEXT NOT NULL CHECK (status_resultado IN ('APROVADO','REPROVADO')),
            data_medicao DATETIME DEFAULT CURRENT_TIMESTAMP,
            CHECK (tolerancia_min <= tolerancia_max),
            FOREIGN KEY (id_equipamento) REFERENCES equipamento(id_equipamento) ON DELETE RESTRICT ON UPDATE CASCADE,
            FOREIGN KEY (id_tecnico) REFERENCES tecnico(id_tecnico) ON DELETE RESTRICT ON UPDATE CASCADE
        );
        """
        with self._get_connection() as conn:
            conn.executescript(sql_schema)

    def salvar_tecnico(self, tecnico: Tecnico) -> int:
        with self._get_connection() as conn:
            cursor = conn.cursor()
            cursor.execute("SELECT id_tecnico FROM tecnico WHERE matricula = ?", (tecnico.matricula,))
            row = cursor.fetchone()
            if row:
                tecnico.id_tecnico = row[0]
                cursor.execute(
                    """UPDATE tecnico 
                       SET nome_tecnico = ?, email = ?, setor = ?
                       WHERE id_tecnico = ?""",
                    (tecnico.nome_tecnico, tecnico.email, tecnico.setor, tecnico.id_tecnico)
                )
                return tecnico.id_tecnico

            cursor.execute(
                "INSERT INTO tecnico (nome_tecnico, matricula, email, setor) VALUES (?, ?, ?, ?)",
                (tecnico.nome_tecnico, tecnico.matricula, tecnico.email, tecnico.setor)
            )
            tecnico.id_tecnico = cursor.lastrowid
            return tecnico.id_tecnico

    def salvar_equipamento(self, equipamento: Equipamento) -> int:
        with self._get_connection() as conn:
            cursor = conn.cursor()
            cursor.execute("SELECT id_equipamento FROM equipamento WHERE codigo_tag = ?", (equipamento.codigo_tag,))
            row = cursor.fetchone()
            if row:
                equipamento.id_equipamento = row[0]
                cursor.execute(
                    """UPDATE equipamento 
                       SET nome_equipamento = ?, fabricante = ?, modelo = ?
                       WHERE id_equipamento = ?""",
                    (equipamento.nome_equipamento, equipamento.fabricante, equipamento.modelo, equipamento.id_equipamento)
                )
                return equipamento.id_equipamento

            cursor.execute(
                "INSERT INTO equipamento (codigo_tag, nome_equipamento, fabricante, modelo) VALUES (?, ?, ?, ?)",
                (equipamento.codigo_tag, equipamento.nome_equipamento, equipamento.fabricante, equipamento.modelo)
            )
            equipamento.id_equipamento = cursor.lastrowid
            return equipamento.id_equipamento

    def salvar_medicao(self, medicao: Medicao) -> int:
        with self._get_connection() as conn:
            cursor = conn.cursor()
            cursor.execute(
                """INSERT INTO medicao 
                   (id_equipamento, id_tecnico, parametro_medido, valor_nominal, tolerancia_min, tolerancia_max, valor_obtido, status_resultado)
                   VALUES (?, ?, ?, ?, ?, ?, ?, ?)""",
                (medicao.id_equipamento, medicao.id_tecnico, medicao.parametro_medido,
                 medicao.valor_nominal, medicao.tolerancia_min, medicao.tolerancia_max,
                 medicao.valor_obtido, medicao.status_resultado)
            )
            medicao.id_medicao = cursor.lastrowid
            return medicao.id_medicao

    def listar_relatorio_calibracao(self) -> List[Dict[str, Any]]:
        query = """
        SELECT 
            m.id_medicao,
            e.codigo_tag,
            e.nome_equipamento,
            t.nome_tecnico,
            t.matricula,
            m.parametro_medido,
            m.valor_nominal,
            m.valor_obtido,
            (m.valor_obtido - m.valor_nominal) AS erro_calculado,
            m.tolerancia_min,
            m.tolerancia_max,
            m.status_resultado,
            m.data_medicao
        FROM medicao m
        JOIN equipamento e ON m.id_equipamento = e.id_equipamento
        JOIN tecnico t ON m.id_tecnico = t.id_tecnico
        ORDER BY m.data_medicao DESC;
        """
        with self._get_connection() as conn:
            conn.row_factory = sqlite3.Row
            cursor = conn.cursor()
            cursor.execute(query)
            rows = cursor.fetchall()
            return [dict(row) for row in rows]