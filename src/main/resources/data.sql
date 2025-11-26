-- Dados de exemplo para desenvolvimento e testes locais
-- Este arquivo é executado automaticamente pelo Spring Boot

-- Inserir doadores de exemplo
INSERT INTO doadores (nome, email, senha, telefone, cpf) VALUES 
('João Silva', 'joao@email.com', 'senha123', '(11) 99999-1111', '123.456.789-01'),
('Maria Santos', 'maria@email.com', 'senha456', '(11) 99999-2222', '987.654.321-02'),
('Pedro Oliveira', 'pedro@email.com', 'senha789', '(11) 99999-3333', '456.789.123-03')
ON CONFLICT DO NOTHING;

-- Inserir associações de exemplo
INSERT INTO associacoes (name, cnpj, email, phone, address) VALUES 
('Associação Verde', '12.345.678/0001-90', 'contato@verde.org', '(11) 8888-1111', 'Av. Sustentável, 456'),
('EcoRecicla', '98.765.432/0001-10', 'info@ecorecicla.com', '(11) 8888-2222', 'Rua da Reciclagem, 789'),
('Planeta Limpo', '11.222.333/0001-44', 'planeta@limpo.org', '(11) 8888-3333', 'Alameda Ecológica, 321')
ON CONFLICT DO NOTHING;

-- Inserir itens de exemplo
INSERT INTO itens (material, peso_em_kg, qtd_volume, url_foto, endereco_retirada, id_doador, status, data_registro) VALUES 
('Plástico', 2.5, 10, null, 'Rua das Flores, 123', 1, 'DISPONIVEL', CURRENT_TIMESTAMP),
('Papel', 1.2, 5, null, 'Av. Central, 456', 1, 'DISPONIVEL', CURRENT_TIMESTAMP),
('Metal', 3.8, 3, null, 'Praça da Paz, 789', 2, 'DISPONIVEL', CURRENT_TIMESTAMP),
('Vidro', 0.8, 2, null, 'Rua Verde, 321', 2, 'COLETADO', CURRENT_TIMESTAMP),
('Eletrônico', 5.0, 1, null, 'Av. Tecnologia, 654', 3, 'DISPONIVEL', CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;