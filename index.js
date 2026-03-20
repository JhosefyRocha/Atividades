const express = require('express');
const app = express();

app.use(express.json());

let filmes = [];
let id = 1;

//  GET todos
app.get('/filmes', (req, res) => {
  res.json(filmes);
});

//  GET por ID
app.get('/filmes/:id', (req, res) => {
  const filme = filmes.find(f => f.id == req.params.id);

  if (!filme) {
    return res.status(404).json({ erro: 'Filme não encontrado' });
  }

  res.json(filme);
});

//  POST (com validações COMPLETAS)
app.post('/filmes', (req, res) => {
  const { titulo, ano, genero, nota } = req.body;

  //  VALIDAÇÕES COMPLETAS
  if (!titulo || typeof titulo !== 'string' || titulo.trim() === '') {
    return res.status(400).json({ erro: 'Título é obrigatório e deve ser texto' });
  }

  if (!ano || typeof ano !== 'number' || ano < 1888 || ano > 2100) {
    return res.status(400).json({ erro: 'Ano inválido' });
  }

  if (!genero || typeof genero !== 'string' || genero.trim() === '') {
    return res.status(400).json({ erro: 'Gênero é obrigatório' });
  }

  if (nota === undefined || typeof nota !== 'number' || nota < 0 || nota > 10) {
    return res.status(400).json({ erro: 'Nota deve ser entre 0 e 10' });
  }

  const novoFilme = {
    id: id++,
    titulo: titulo.trim(),
    ano,
    genero: genero.trim(),
    nota
  };

  filmes.push(novoFilme);

  res.status(201).json(novoFilme);
});

//  PUT
app.put('/filmes/:id', (req, res) => {
  const filme = filmes.find(f => f.id == req.params.id);

  if (!filme) {
    return res.status(404).json({ erro: 'Filme não encontrado' });
  }

  const { titulo, ano, genero, nota } = req.body;

  if (titulo && typeof titulo !== 'string') {
    return res.status(400).json({ erro: 'Título inválido' });
  }

  if (ano && (typeof ano !== 'number' || ano < 1888 || ano > 2100)) {
    return res.status(400).json({ erro: 'Ano inválido' });
  }

  if (genero && typeof genero !== 'string') {
    return res.status(400).json({ erro: 'Gênero inválido' });
  }

  if (nota !== undefined && (typeof nota !== 'number' || nota < 0 || nota > 10)) {
    return res.status(400).json({ erro: 'Nota inválida' });
  }

  if (titulo) filme.titulo = titulo;
  if (ano) filme.ano = ano;
  if (genero) filme.genero = genero;
  if (nota !== undefined) filme.nota = nota;

  res.json(filme);
});

//  DELETE
app.delete('/filmes/:id', (req, res) => {
  const index = filmes.findIndex(f => f.id == req.params.id);

  if (index === -1) {
    return res.status(404).json({ erro: 'Filme não encontrado' });
  }

  filmes.splice(index, 1);

  res.json({ mensagem: 'Filme removido com sucesso' });
});

app.listen(3000, () => {
  console.log('Servidor rodando em http://localhost:3000');
});