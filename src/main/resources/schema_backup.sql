CREATE TABLE USUARIO(
    ID INT NOT NULL AUTO_INCREMENT,
    NOME VARCHAR(100) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    ENDERECO VARCHAR(1000) NOT NULL,
    DATA_NASCIMENTO DATE,
    STATUS VARCHAR(100),
    PRIMARY KEY (ID)
);

INSERT INTO USUARIO (NOME, EMAIL, ENDERECO, DATA_NASCIMENTO, STATUS) VALUES ('João Paulo Fonseca', 'joaopfonseca@gmail.com', '{
  "logradouro": "Rua dos Andradas",
  "numero": 564,
  "cidade": "Gravataí",
  "bairro": "Centro",
  "estado": "RS"
}', '1941-01-21', 'ATIVO');
INSERT INTO USUARIO (NOME, EMAIL, ENDERECO, DATA_NASCIMENTO, STATUS) VALUES ('Vitor Pereira da Costa', 'vitorpcosta@hotmail.com', '{
  "logradouro": "Avenida Burguesia",
  "numero": 111,
  "cidade": "Belém",
  "bairro": "Rio Branco",
  "estado": "PA"
}', '1974-09-25', 'ATIVO');
INSERT INTO USUARIO (NOME, EMAIL, ENDERECO, DATA_NASCIMENTO, STATUS) VALUES ('Regina Maria Silva', 'reginasilva@outlook.com', '{
  "logradouro": "Rua dos Ingleses",
  "numero": 956,
  "cidade": "Rio de Janeiro",
  "bairro": "Copacabana",
  "estado": "RJ",
  "cep": "31014360"
}', '1998-01-01', 'ATIVO');
INSERT INTO USUARIO (NOME, EMAIL, ENDERECO, DATA_NASCIMENTO, STATUS) VALUES ('Maria Joaquina', 'joaquinamaria@gmail.com', '{
  "logradouro": "Alameda de Barros",
  "numero": 54,
  "cidade": "São Paulo",
  "bairro": "Morumbi",
  "estado": "SP",
  "cep": "44045340"
}', '1984-07-30', 'INATIVO');
COMMIT;

CREATE TABLE ARQUIVO(
    ID INT NOT NULL AUTO_INCREMENT,
    NOME VARCHAR(100) NOT NULL,
    ID_USUARIO INT NOT NULL,
    CONTEUDO VARCHAR(1000) NOT NULL,
    PRIMARY KEY (ID)
);

INSERT INTO ARQUIVO(NOME, ID_USUARIO, CONTEUDO) VALUES ('meuArquivo.txt', 1, '{
  "titulo": "Estudo Sobre Microbiologia",
  "dataPublicacao": "2005-03-27",
  "tags": "cientifico;experimento;biologia;validacao;microbiota"
}');
INSERT INTO ARQUIVO(NOME, ID_USUARIO, CONTEUDO) VALUES ('anotacoesAleatorias.txt', 1, '{
  "titulo": "Intervenção Sobre Inspeções Ilegais e Seus Desdobramentos",
  "dataPublicacao": "2009-04-02",
  "tags": "taxas;governo;politica;economia;fiscalizacao"
}');
INSERT INTO ARQUIVO(NOME, ID_USUARIO, CONTEUDO) VALUES ('paper.txt', 2, '{
  "titulo": "Inflação e Impactos na Sociedade",
  "dataPublicacao": "2012-08-22",
  "tags": "economia;inflacao;governo;taxadejuros"
}');
INSERT INTO ARQUIVO(NOME, ID_USUARIO, CONTEUDO) VALUES ('analiseFraude.txt', 3, '{
  "titulo": "Aviões e Acidentes Aéreos",
  "dataPublicacao": "2011-01-15",
  "tags": "aviacao;aviao;acidente;investigacao"
}');
INSERT INTO ARQUIVO(NOME, ID_USUARIO, CONTEUDO) VALUES ('otimizacaoTecnica.txt', 4, '{
  "titulo": "Impacto dos Smartphones no Mundo Corporativo",
  "dataPublicacao": "2018-07-30",
  "tags": "corporativo;tecnologia;celulares;smartphones;ligacoes"
}');
INSERT INTO ARQUIVO(NOME, ID_USUARIO, CONTEUDO) VALUES ('formacaoSQL.txt', 4, '{
  "titulo": "Café e Hormônios, Correlações",
  "dataPublicacao": "2023-06-14",
  "tags": "cafe;hormonios;biologia"
}');
INSERT INTO ARQUIVO(NOME, ID_USUARIO, CONTEUDO) VALUES ('prints.txt', 4, '{
  "titulo": "Monitores e Suas Evoluções Ao Longo das Décadas",
  "dataPublicacao": "2024-09-27",
  "tags": "tecnologia;monitor"
}');