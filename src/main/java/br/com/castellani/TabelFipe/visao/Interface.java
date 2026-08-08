package br.com.castellani.TabelFipe.visao;

import br.com.castellani.TabelFipe.model.Dados;
import br.com.castellani.TabelFipe.model.Modelos;
import br.com.castellani.TabelFipe.model.Veiculo;
import br.com.castellani.TabelFipe.service.ConsumoApi;
import br.com.castellani.TabelFipe.service.ConverteDados;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class Interface {
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private JFrame frame;

    // O Construtor monta a tela assim que o objeto é instanciado
    public Interface() {
        inicializarTela();
    }

    private void inicializarTela() {
        frame = new JFrame("Consulta Tabela Fipe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout(10, 10));

        JTextArea areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(areaResultados);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"carros", "motos", "caminhoes"});
        JButton btnMarcas = new JButton("1. Buscar Marcas");
        linha1.add(new JLabel("Tipo de Veículo:"));
        linha1.add(comboTipo);
        linha1.add(btnMarcas);

        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoCodigoMarca = new JTextField(5);
        JButton btnModelos = new JButton("2. Buscar Modelos");
        linha2.add(new JLabel("Código da Marca:"));
        linha2.add(campoCodigoMarca);
        linha2.add(btnModelos);

        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoCodigoModelo = new JTextField(5);
        JButton btnVeiculos = new JButton("3. Buscar  Veiculos");
        linha3.add(new JLabel("Código do Modelo:"));
        linha3.add(campoCodigoModelo);
        linha3.add(btnVeiculos);

        painelTopo.add(linha1);
        painelTopo.add(linha2);
        painelTopo.add(linha3);

        // botões
        btnMarcas.addActionListener(e -> {
            areaResultados.setText("Buscando marcas...\n");
            String tipo = comboTipo.getSelectedItem().toString();
            try {
                String json = consumo.obterDados(URL_BASE + tipo + "/marcas");
                var marcas = conversor.obterLista(json, Dados.class);
                StringBuilder sb = new StringBuilder("=== MARCAS ===\n");
                marcas.stream()
                        .sorted(Comparator.comparing(Dados::codigo))
                        .forEach(m -> sb.append("Cód: ").append(m.codigo()).append(" | ").append(m.nome()).append("\n"));
                areaResultados.setText(sb.toString());
            } catch (Exception ex) {
                areaResultados.setText("Erro: " + ex.getMessage());
            }
        });

        btnModelos.addActionListener(e -> {
            String codMarca = campoCodigoMarca.getText();
            if (codMarca.isBlank()) return;
            areaResultados.setText("Buscando modelos...\n");
            String tipo = comboTipo.getSelectedItem().toString();
            try {
                String json = consumo.obterDados(URL_BASE + tipo + "/marcas/" + codMarca + "/modelos");
                var modeloLista = conversor.obterDados(json, Modelos.class);
                StringBuilder sb = new StringBuilder("=== MODELOS ===\n");
                modeloLista.modelos().stream()
                        .sorted(Comparator.comparing(Dados::codigo))
                        .forEach(m -> sb.append("Cód: ").append(m.codigo()).append(" | ").append(m.nome()).append("\n"));
                areaResultados.setText(sb.toString());
            } catch (Exception ex) {
                areaResultados.setText("Erro: " + ex.getMessage());
            }
        });

        btnVeiculos.addActionListener(e -> {
            String codMarca = campoCodigoMarca.getText();
            String codModelo = campoCodigoModelo.getText();
            if (codMarca.isBlank() || codModelo.isBlank()) return;

            areaResultados.setText("Buscando dados... Aguarde.");
            String tipo = comboTipo.getSelectedItem().toString();

            new Thread(() -> {
                try {
                    String urlAnos = URL_BASE + tipo + "/marcas/" + codMarca + "/modelos/" + codModelo + "/anos";
                    String jsonAnos = consumo.obterDados(urlAnos);
                    List<Dados> anos = conversor.obterLista(jsonAnos, Dados.class);
                    StringBuilder sb = new StringBuilder("=== VALORES DO VEÍCULO ===\n");

                    for (Dados ano : anos) {
                        String jsonVeiculo = consumo.obterDados(urlAnos + "/" + ano.codigo());
                        Veiculo veiculo = conversor.obterDados(jsonVeiculo, Veiculo.class);
                        sb.append(veiculo.toString()).append("\n\n");
                    }
                    SwingUtilities.invokeLater(() -> areaResultados.setText(sb.toString()));
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> areaResultados.setText("Erro: " + ex.getMessage()));
                }
            }).start();
        });

        frame.add(painelTopo, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
    }

    public void exibir() {
        frame.setVisible(true);
    }
}