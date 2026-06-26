/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Ventana extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Ventana.class.getName());
    private Controlador controlador = new Controlador();
    Auto auto = new Auto();

    public Ventana() {
        initComponents();
        tamanos();
        Date hoy = new Date();
        jdIngreso.setDate(hoy);
        jdIngreso.setMinSelectableDate(hoy);
        jdIngreso.setMaxSelectableDate(hoy);
        setLocationRelativeTo(null);
        txtHoraIngreso.setEditable(false);
        cargarFecha();
        cargarHora();
        txtHoraEgreso.setEditable(false);
        txtCosto.setEditable(false);
        DefaultTableModel modelo = (DefaultTableModel) tblAutos.getModel();
        modelo.setRowCount(0);
        JTextField txtRegistro = (JTextField) cbBuscarRegistro.getEditor().getEditorComponent();
        btnBuscarRegistro.setVisible(false);
        btnBuscarEgreso.setVisible(false);
        btnBuscarVista.setVisible(false);
        txtRegistro.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {

                filtrarPlacas(cbBuscarRegistro, txtRegistro.getText());

            }
        });
        txtRegistro.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (cbBuscarRegistro.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione una placa.");
                    return;
                }

                String placa = cbBuscarRegistro.getSelectedItem().toString();

                Auto auto = controlador.obtenerAuto(placa);

                if (auto == null) {
                    JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
                    return;
                }

                JOptionPane.showMessageDialog(null, "Vehículo encontrado.");
            }
        });
        
        JTextField txtEgreso = (JTextField) cbBuscarEgreso.getEditor().getEditorComponent();

        txtEgreso.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtrarPlacas(cbBuscarEgreso, txtEgreso.getText());

            }

        });
        txtEgreso.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (cbBuscarEgreso.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione una placa.");
                    return;
                }

                String placa = cbBuscarEgreso.getSelectedItem().toString();

                Auto auto = controlador.obtenerAuto(placa);

                if (auto == null) {
                    JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
                    return;
                }

                if (auto.getServicio() == null || !auto.getServicio().isServicioTerminado()) {
                    JOptionPane.showMessageDialog(null, "El servicio aún no ha terminado.");
                    return;
                }

                cargarHoraEgreso();
                txtCosto.setText(String.valueOf(auto.getServicio().getCosto()));

                JOptionPane.showMessageDialog(null, "Vehículo encontrado.");
            }
        });
        
        txtBuscarVista.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarVistaActionPerformed(evt);
            }
        });
        configurarTabla();
        
    }
    
    public void tamanos(){
        GridBagConstraints c = new GridBagConstraints();
        
        GridBagLayout s = new GridBagLayout();
        
        c.fill = GridBagConstraints.BASELINE;
        
        menu.setBounds(WIDTH, HEIGHT,300 , 200);
        tabbed.setBounds(0,0,400,600);
        s.setConstraints(menu, c);
        s.setConstraints(tabbed, c);
        
    }

    private void cargarFecha() {
        Date hoy = new Date();
        jdIngreso.setDate(hoy);
        jdIngreso.setEnabled(false);
    }

    private void cargarHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        txtHoraIngreso.setText(sdf.format(new Date()));
    }

    private void limpiarIngreso() {
        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtTelefono.setText("");
        txtColor.setText("");
        txtPlacas.setText("");
        txtModelo.setText("");
        cbTipoVehiculo.setSelectedIndex(0);
        cargarHora();
    }

    private void limpiarRegistro() {
        cbBuscarRegistro.setSelectedIndex(-1);
        cbTipoServicio.setSelectedIndex(0);
        observaciones.setText("");
        buttonGroup1.clearSelection();
    }

    private void limpiarEgreso() {
        cbBuscarEgreso.setSelectedIndex(-1);
        txtHoraEgreso.setText("");
        txtCosto.setText("");
        txtObservacionesEgreso.setText("");
        cbTipoPago.setSelectedIndex(0);
    }

    private void cargarHoraEgreso() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        txtHoraEgreso.setText(sdf.format(new Date()));
    }

    private void filtrarPlacas(JComboBox<String> combo, String texto) {
        combo.removeAllItems();
        for (String placa : controlador.buscarCoincidencias(texto)) {
            combo.addItem(placa);
        }
        combo.setSelectedItem(texto);
        combo.showPopup();
    }

    private void configurarTabla() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido Paterno");
        modelo.addColumn("Apellido Materno");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Tipo Vehículo");
        modelo.addColumn("Color");
        modelo.addColumn("Placas");
        modelo.addColumn("Modelo");
        modelo.addColumn("Fecha Ingreso");
        modelo.addColumn("Hora Ingreso");
        modelo.addColumn("Tipo Servicio");
        modelo.addColumn("Costo");
        modelo.addColumn("Servicio Terminado");
        modelo.addColumn("Hora Egreso");
        modelo.addColumn("Tipo Pago");
        modelo.addColumn("Observaciones");
        modelo.addColumn("Estado");

        tblAutos.setModel(modelo);

        tblAutos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblAutos.getTableHeader().setReorderingAllowed(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        menu = new javax.swing.JPanel();
        btnIngreso = new javax.swing.JButton();
        btnRegistro = new javax.swing.JButton();
        btnEgreso = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tabbed = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cbBuscarEgreso = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtHoraEgreso = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservacionesEgreso = new javax.swing.JTextArea();
        jLabel27 = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        btonGuardarEgreso = new javax.swing.JButton();
        cbTipoPago = new javax.swing.JComboBox<>();
        btnBuscarEgreso = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        txtBuscarVista = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblAutos = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();
        btnBuscarVista = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnGuardarIngreso = new javax.swing.JButton();
        jdIngreso = new com.toedter.calendar.JDateChooser();
        txtNombre = new javax.swing.JTextField();
        txtApellidoPaterno = new javax.swing.JTextField();
        txtApellidoMaterno = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtPlacas = new javax.swing.JTextField();
        txtHoraIngreso = new javax.swing.JTextField();
        cbTipoVehiculo = new javax.swing.JComboBox<>();
        txtColor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbTipoServicio = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cbBuscarRegistro = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        rbSi = new javax.swing.JRadioButton();
        rbNo = new javax.swing.JRadioButton();
        jLabel22 = new javax.swing.JLabel();
        btnBuscarRegistro = new javax.swing.JButton();
        btnGuardarRegistro = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menu.setBackground(new java.awt.Color(30, 136, 229));

        btnIngreso.setFont(new java.awt.Font("Liberation Serif", 1, 14)); // NOI18N
        btnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        btnIngreso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ingresar24.png"))); // NOI18N
        btnIngreso.setText("Ingresar ");
        btnIngreso.setContentAreaFilled(false);
        btnIngreso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIngreso.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ingresar24.png"))); // NOI18N
        btnIngreso.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ingresar32.png"))); // NOI18N
        btnIngreso.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnIngreso.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnIngreso.addActionListener(this::btnIngresoActionPerformed);

        btnRegistro.setFont(new java.awt.Font("Liberation Serif", 1, 14)); // NOI18N
        btnRegistro.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/registro24.png"))); // NOI18N
        btnRegistro.setText("Registrar");
        btnRegistro.setBorderPainted(false);
        btnRegistro.setContentAreaFilled(false);
        btnRegistro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegistro.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/registro24.png"))); // NOI18N
        btnRegistro.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/registro32.png"))); // NOI18N
        btnRegistro.setVerifyInputWhenFocusTarget(false);
        btnRegistro.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnRegistro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegistro.addActionListener(this::btnRegistroActionPerformed);

        btnEgreso.setFont(new java.awt.Font("Liberation Serif", 1, 14)); // NOI18N
        btnEgreso.setForeground(new java.awt.Color(255, 255, 255));
        btnEgreso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida24.png"))); // NOI18N
        btnEgreso.setText("Egreso\n");
        btnEgreso.setBorderPainted(false);
        btnEgreso.setContentAreaFilled(false);
        btnEgreso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEgreso.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida24.png"))); // NOI18N
        btnEgreso.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida32.png"))); // NOI18N
        btnEgreso.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnEgreso.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEgreso.addActionListener(this::btnEgresoActionPerformed);

        btnBuscar.setFont(new java.awt.Font("Liberation Serif", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar24.png"))); // NOI18N
        btnBuscar.setText("Buscar\n");
        btnBuscar.setToolTipText("");
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar24.png"))); // NOI18N
        btnBuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar32.png"))); // NOI18N
        btnBuscar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        jLabel1.setFont(new java.awt.Font("Liberation Serif", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/car-wash.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Liberation Serif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Lava autos");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel3.setFont(new java.awt.Font("Liberation Serif", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("_______________");

        jLabel4.setFont(new java.awt.Font("Liberation Serif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("PRINCIPAL");

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel2))
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel4))
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnRegistro))
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addGap(2, 2, 2)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(btnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(361, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setMaximumSize(getMaximumSize());

        jPanel3.setBackground(new java.awt.Color(248, 249, 250));

        jLabel23.setFont(new java.awt.Font("URW Bookman", 2, 48)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 153));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Egreso");

        cbBuscarEgreso.setEditable(true);
        cbBuscarEgreso.setFont(new java.awt.Font("URW Bookman", 0, 15)); // NOI18N
        cbBuscarEgreso.setOpaque(true);

        jLabel24.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel24.setText("Buscar vehiculo");

        jLabel25.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel25.setText("Hora de egreso:");

        txtHoraEgreso.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel26.setText("Costo:");

        txtObservacionesEgreso.setColumns(20);
        txtObservacionesEgreso.setRows(5);
        jScrollPane2.setViewportView(txtObservacionesEgreso);

        jLabel27.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel27.setText("Observaciones:");

        txtCosto.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N
        txtCosto.addActionListener(this::txtCostoActionPerformed);

        jLabel28.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel28.setText("Tipo de pago:");

        btonGuardarEgreso.setBackground(new java.awt.Color(0, 188, 212));
        btonGuardarEgreso.setFont(new java.awt.Font("Liberation Serif", 1, 18)); // NOI18N
        btonGuardarEgreso.setForeground(new java.awt.Color(255, 255, 255));
        btonGuardarEgreso.setText("Guardar");
        btonGuardarEgreso.addActionListener(this::btonGuardarEgresoActionPerformed);

        cbTipoPago.setFont(new java.awt.Font("URW Bookman", 0, 16)); // NOI18N
        cbTipoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Efectivo", "Transferencia", "Tarjeta" }));
        cbTipoPago.setOpaque(true);

        btnBuscarEgreso.setText("Buscar");
        btnBuscarEgreso.addActionListener(this::btnBuscarEgresoActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cbBuscarEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(278, 278, 278)
                .addComponent(btnBuscarEgreso))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel25)
                .addGap(30, 30, 30)
                .addComponent(txtHoraEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel27))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel26)
                .addGap(107, 107, 107)
                .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel28)
                .addGap(48, 48, 48)
                .addComponent(cbTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(382, 382, 382)
                .addComponent(btonGuardarEgreso))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel23)
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(cbBuscarEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnBuscarEgreso)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel25))
                    .addComponent(txtHoraEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel28))
                    .addComponent(cbTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btonGuardarEgreso))
        );

        jTabbedPane1.addTab("Egreso", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("URW Bookman", 2, 48)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 153));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Buscar Vehiculo");

        txtBuscarVista.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N

        tblAutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tblAutos);

        btnSalir.setBackground(new java.awt.Color(0, 188, 212));
        btnSalir.setFont(new java.awt.Font("Liberation Serif", 1, 18)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        btnBuscarVista.setText("Buscar");
        btnBuscarVista.addActionListener(this::btnBuscarVistaActionPerformed);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logocar.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(txtBuscarVista, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(btnBuscarVista))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(234, 234, 234)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel29)
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscarVista, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnBuscarVista)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnSalir))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Buscar", jPanel4);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logocar.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel8.setText("Apellido paterno:");

        jLabel10.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel10.setText("Apelldio materno:");

        jLabel11.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel11.setText("Nombre:");

        jLabel12.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel12.setText("Telefono:");

        jLabel13.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel13.setText("Tipo de vehiculo:");

        jLabel14.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel14.setText("Color:");

        jLabel15.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel15.setText("Placas:");

        jLabel16.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel16.setText("Fecha de Ingreso:");

        jLabel17.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel17.setText("Hora de Ingreso:");

        btnGuardarIngreso.setBackground(new java.awt.Color(0, 188, 212));
        btnGuardarIngreso.setFont(new java.awt.Font("Liberation Serif", 1, 18)); // NOI18N
        btnGuardarIngreso.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarIngreso.setText("Guardar");
        btnGuardarIngreso.addActionListener(this::btnGuardarIngresoActionPerformed);

        txtNombre.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N
        txtNombre.addActionListener(this::txtNombreActionPerformed);
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtApellidoPaterno.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N
        txtApellidoPaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoKeyTyped(evt);
            }
        });

        txtApellidoMaterno.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N
        txtApellidoMaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoKeyTyped(evt);
            }
        });

        txtTelefono.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        txtPlacas.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N

        txtHoraIngreso.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N

        cbTipoVehiculo.setFont(new java.awt.Font("URW Bookman", 0, 15)); // NOI18N
        cbTipoVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Motocicleta", "Automóvil", "Camioneta/SUV", "Van" }));
        cbTipoVehiculo.setOpaque(true);

        txtColor.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N
        txtColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtColorKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("URW Bookman", 2, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Ingresar");

        jLabel30.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel30.setText("Modelo:");

        txtModelo.setFont(new java.awt.Font("URW Gothic", 0, 14)); // NOI18N
        txtModelo.addActionListener(this::txtModeloActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(65, 65, 65)
                        .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbTipoVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPlacas, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(137, 137, 137)
                        .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(58, 58, 58)
                        .addComponent(jdIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(69, 69, 69)
                        .addComponent(txtHoraIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel11)
                        .addGap(134, 134, 134)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addComponent(btnGuardarIngreso)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel9)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel12)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel13)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel14)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel15))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbTipoVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(txtPlacas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jdIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtHoraIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addComponent(btnGuardarIngreso)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ingreso", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("URW Bookman", 2, 48)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 153));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Registro");

        jLabel19.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel19.setText("Servicio terminado:");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logocar.png"))); // NOI18N

        cbTipoServicio.setFont(new java.awt.Font("URW Bookman", 0, 16)); // NOI18N
        cbTipoServicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Lavado exterior", "Lavado interior", "Lavado completo", "Encerado", "Pulido", "Aspirado", "Lavado de motor", "Detallado" }));
        cbTipoServicio.setOpaque(true);

        jLabel20.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel20.setText("Buscar vehiculo");

        cbBuscarRegistro.setEditable(true);
        cbBuscarRegistro.setFont(new java.awt.Font("URW Bookman", 0, 15)); // NOI18N
        cbBuscarRegistro.setOpaque(true);
        cbBuscarRegistro.addActionListener(this::cbBuscarRegistroActionPerformed);

        jLabel21.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel21.setText("Tipo de servicio");

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane1.setViewportView(observaciones);

        buttonGroup1.add(rbSi);
        rbSi.setFont(new java.awt.Font("URW Gothic", 0, 15)); // NOI18N
        rbSi.setText("Si");

        buttonGroup1.add(rbNo);
        rbNo.setFont(new java.awt.Font("URW Gothic", 0, 15)); // NOI18N
        rbNo.setText("No");

        jLabel22.setFont(new java.awt.Font("URW Gothic", 2, 18)); // NOI18N
        jLabel22.setText("Observaciones:");

        btnBuscarRegistro.setText("Buscar");
        btnBuscarRegistro.addActionListener(this::btnBuscarRegistroActionPerformed);

        btnGuardarRegistro.setBackground(new java.awt.Color(0, 188, 212));
        btnGuardarRegistro.setFont(new java.awt.Font("Liberation Serif", 1, 18)); // NOI18N
        btnGuardarRegistro.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarRegistro.setText("Guardar");
        btnGuardarRegistro.addActionListener(this::btnGuardarRegistroActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel21)
                        .addGap(21, 21, 21)
                        .addComponent(cbTipoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel22))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(cbBuscarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(btnBuscarRegistro))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel19)
                .addGap(53, 53, 53)
                .addComponent(rbSi)
                .addGap(54, 54, 54)
                .addComponent(rbNo))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(btnGuardarRegistro))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(cbTipoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel22))
                    .addComponent(cbBuscarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarRegistro))
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbSi)
                            .addComponent(rbNo))))
                .addGap(35, 35, 35)
                .addComponent(btnGuardarRegistro))
        );

        jTabbedPane1.addTab("Registro", jPanel2);

        javax.swing.GroupLayout tabbedLayout = new javax.swing.GroupLayout(tabbed);
        tabbed.setLayout(tabbedLayout);
        tabbedLayout.setHorizontalGroup(
            tabbedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        tabbedLayout.setVerticalGroup(
            tabbedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabbedLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        jTabbedPane1.setSelectedIndex(3);
        controlador.llenarTabla((DefaultTableModel) tblAutos.getModel());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed

        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void btnRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroActionPerformed

        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnRegistroActionPerformed

    private void btnEgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEgresoActionPerformed

        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btnEgresoActionPerformed

    private void txtModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtModeloActionPerformed

    private void txtColorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColorKeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtColorKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
        if (txtTelefono.getText().length() >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtApellidoMaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoKeyTyped
        char c = evt.getKeyChar();

        if (!Character.isLetter(c)
            && c != ' '
            && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellidoMaternoKeyTyped

    private void txtApellidoPaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoKeyTyped
        char c = evt.getKeyChar();

        if (!Character.isLetter(c)
            && c != ' '
            && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellidoPaternoKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c = evt.getKeyChar();

        if (!Character.isLetter(c)
            && c != ' '
            && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnGuardarIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarIngresoActionPerformed
<<<<<<< HEAD
=======
        
       if(txtNombre.getText().trim().isEmpty()){
    JOptionPane.showMessageDialog(null,"Ingrese el nombre.");
    txtNombre.requestFocus();
    return;
}
        if(txtNombre.getText().trim().length() < 2){
    JOptionPane.showMessageDialog(null,"El nombre debe tener al menos 2 caracteres.");
    txtNombre.requestFocus();
    return;
}
>>>>>>> 678109e6ce422cc87f36742f45b6af1e44b7f449

        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre.");
            txtNombre.requestFocus();
            return;
        }
        if (!txtNombre.getText().trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
            JOptionPane.showMessageDialog(null, "El nombre solo puede contener letras.");
            txtNombre.requestFocus();
            return;
        }
        if (txtApellidoPaterno.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el apellido paterno.");
            txtApellidoPaterno.requestFocus();
            return;
        }

<<<<<<< HEAD
        if (txtApellidoPaterno.getText().trim().length() < 2) {
            JOptionPane.showMessageDialog(null, "El apellido paterno debe tener al menos 2 caracteres.");
            txtApellidoPaterno.requestFocus();
            return;
        }
        if (!txtApellidoPaterno.getText().trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
            JOptionPane.showMessageDialog(null, "El apellido paterno solo puede contener letras.");
            txtApellidoPaterno.requestFocus();
            return;
        }
        if (txtApellidoMaterno.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el apellido materno.");
            txtApellidoMaterno.requestFocus();
            return;
        }
        if (!txtApellidoMaterno.getText().trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
            JOptionPane.showMessageDialog(null, "El apellido materno solo puede contener letras.");
            txtApellidoMaterno.requestFocus();
            return;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el teléfono.");
            txtTelefono.requestFocus();
            return;
        }
=======
       if(txtApellidoMaterno.getText().trim().isEmpty()){
    JOptionPane.showMessageDialog(null,"Ingrese el apellido materno.");
    txtApellidoMaterno.requestFocus();
    return;
}
        if(txtApellidoMaterno.getText().trim().length() < 2){
    JOptionPane.showMessageDialog(null,"El apellido materno debe tener al menos 2 caracteres.");
    txtApellidoMaterno.requestFocus();
    return;
}
>>>>>>> 678109e6ce422cc87f36742f45b6af1e44b7f449

        if (txtTelefono.getText().trim().length() != 10) {
            JOptionPane.showMessageDialog(null, "El teléfono debe tener 10 dígitos.");
            txtTelefono.requestFocus();
            return;
        }

        if (txtColor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el color.");
            txtColor.requestFocus();
            return;
        }
        if (txtColor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el color.");
            txtColor.requestFocus();
            return;
        }
        if (txtPlacas.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese las placas.");
            txtPlacas.requestFocus();
            return;
        }

        if (txtPlacas.getText().trim().length() < 6 || txtPlacas.getText().trim().length() > 7) {
            JOptionPane.showMessageDialog(null, "Las placas deben tener entre 6 y 7 caracteres.");
            txtPlacas.requestFocus();
            return;
        }

        if (txtModelo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el modelo.");
            txtModelo.requestFocus();
            return;
        }

        if (cbTipoVehiculo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un tipo de vehículo.");
            return;
        }
        String placas = txtPlacas.getText().trim().toUpperCase();
        txtPlacas.setText(placas);
        auto.setPlacas(placas);
        if (controlador.existePlaca(placas)) {
            JOptionPane.showMessageDialog(null, "Las placas ya están registradas.");
            txtPlacas.requestFocus();
            return;
        }

        Auto auto = new Auto();

        auto.setNombre(txtNombre.getText().trim());
        auto.setApellidoPaterno(txtApellidoPaterno.getText().trim());
        auto.setApellidoMaterno(txtApellidoMaterno.getText().trim());
        auto.setTelefono(txtTelefono.getText().trim());
        auto.setColor(txtColor.getText().trim());
        auto.setPlacas(placas);
        auto.setTipoVehiculo(cbTipoVehiculo.getSelectedItem().toString());
        auto.setModelo(txtModelo.getText().trim());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        auto.setFechaIngreso(sdf.format(jdIngreso.getDate()));
        auto.setHoraIngreso(txtHoraIngreso.getText());
        auto.setEstado("Ingresado");

        controlador.guardarAuto(auto);

        cbBuscarRegistro.addItem(auto.getPlacas());
        cbBuscarEgreso.addItem(auto.getPlacas());

        DefaultTableModel modelo = (DefaultTableModel) tblAutos.getModel();
        controlador.llenarTabla(modelo);

        JOptionPane.showMessageDialog(null, "Vehículo registrado correctamente.");

        limpiarIngreso();
        txtNombre.requestFocus();
    }//GEN-LAST:event_btnGuardarIngresoActionPerformed

    private void btnBuscarVistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarVistaActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tblAutos.getModel();
        if (txtBuscarVista.getText().trim().isEmpty()) {
            controlador.llenarTabla(modelo);
        } else {
            controlador.buscarAuto(txtBuscarVista.getText().trim(), modelo);
        }
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
        } else {
            JOptionPane.showMessageDialog(null, "Vehículo encontrado.");
        }
    }//GEN-LAST:event_btnBuscarVistaActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea salir?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnBuscarEgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEgresoActionPerformed

        if (cbBuscarEgreso.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una placa.");
            return;
        }

        String placa = cbBuscarEgreso.getSelectedItem().toString();

        Auto auto = controlador.obtenerAuto(placa);

        if (auto == null) {
            JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
            return;
        }

        if (auto.getServicio() == null || !auto.getServicio().isServicioTerminado()) {
            JOptionPane.showMessageDialog(null, "El servicio aún no ha terminado.");
            return;
        }

        cargarHoraEgreso();

        txtCosto.setText(String.valueOf(auto.getServicio().getCosto()));

        JOptionPane.showMessageDialog(null, "Vehículo encontrado.");
    }//GEN-LAST:event_btnBuscarEgresoActionPerformed

    private void btonGuardarEgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btonGuardarEgresoActionPerformed
        if (cbBuscarEgreso.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un vehículo.");
            return;
        }
        if (txtObservacionesEgreso.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese observaciones.");
            txtObservacionesEgreso.requestFocus();
            return;
        }

        if (cbTipoPago.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un tipo de pago.");
            return;
        }
        String placa = cbBuscarEgreso.getSelectedItem().toString();
        if (!controlador.servicioTerminado(placa)) {
            JOptionPane.showMessageDialog(null, "El servicio aún no ha terminado.");
            return;
        }

        controlador.registrarEgreso(
            placa,
            txtHoraEgreso.getText(),
            txtObservacionesEgreso.getText(),
            cbTipoPago.getSelectedItem().toString()
        );
        JOptionPane.showMessageDialog(null, "Egreso registrado correctamente.");
        DefaultTableModel modelo = (DefaultTableModel) tblAutos.getModel();
        controlador.llenarTabla(modelo);
        Auto autoParaTicket = controlador.obtenerAuto(placa);

        String linkDrive = "https://drive.google.com/file/d/13z-_B-Vvh2gYYmjjg-smebN7t_CtgG6jU/view?usp=drivesdk";

        ticket.generarTicket(autoParaTicket, linkDrive);
        limpiarEgreso();
    }//GEN-LAST:event_btonGuardarEgresoActionPerformed

    private void txtCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoActionPerformed

    private void btnGuardarRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarRegistroActionPerformed
        if (cbBuscarRegistro.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un vehículo.");
            return;
        }
        if (cbTipoServicio.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un tipo de servicio.");
            return;
        }
        if (!rbSi.isSelected() && !rbNo.isSelected()) {
            JOptionPane.showMessageDialog(null, "Seleccione si el servicio terminó.");
            return;
        }
        if (observaciones.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese observaciones.");
            observaciones.requestFocus();
            return;
        }
        String placa = cbBuscarRegistro.getSelectedItem().toString();
        String servicio = cbTipoServicio.getSelectedItem().toString();
        boolean terminado;

        if (rbSi.isSelected()) {
            terminado = true;
        } else {
            terminado = false;
        }

        controlador.actualizarServicio(placa, servicio, terminado, observaciones.getText());
        auto.setObservaciones(observaciones.getText());
        JOptionPane.showMessageDialog(null, "Servicio registrado correctamente.");
        DefaultTableModel modelo = (DefaultTableModel) tblAutos.getModel();
        controlador.llenarTabla(modelo);
        limpiarRegistro();
    }//GEN-LAST:event_btnGuardarRegistroActionPerformed

    private void btnBuscarRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarRegistroActionPerformed
        if (cbBuscarEgreso.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una placa.");
            return;
        }
        String placa = cbBuscarEgreso.getSelectedItem().toString();
        Auto auto = controlador.obtenerAuto(placa);
        if (auto == null) {
            JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
            return;
        }
        if (auto.getServicio() == null ||
            !auto.getServicio().isServicioTerminado()) {

            JOptionPane.showMessageDialog(null,"El servicio aún no ha terminado.");
            return;
        }
        cargarHoraEgreso();
        txtCosto.setText( String.valueOf(auto.getServicio().getCosto())
        );

        JOptionPane.showMessageDialog(null,"Vehículo encontrado.");
    }//GEN-LAST:event_btnBuscarRegistroActionPerformed

    private void cbBuscarRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBuscarRegistroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBuscarRegistroActionPerformed
     
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Ventana().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarEgreso;
    private javax.swing.JButton btnBuscarRegistro;
    private javax.swing.JButton btnBuscarVista;
    private javax.swing.JButton btnEgreso;
    private javax.swing.JButton btnGuardarIngreso;
    private javax.swing.JButton btnGuardarRegistro;
    private javax.swing.JButton btnIngreso;
    private javax.swing.JButton btnRegistro;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btonGuardarEgreso;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbBuscarEgreso;
    private javax.swing.JComboBox<String> cbBuscarRegistro;
    private javax.swing.JComboBox<String> cbTipoPago;
    private javax.swing.JComboBox<String> cbTipoServicio;
    private javax.swing.JComboBox<String> cbTipoVehiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdIngreso;
    private javax.swing.JPanel menu;
    private javax.swing.JTextArea observaciones;
    private javax.swing.JRadioButton rbNo;
    private javax.swing.JRadioButton rbSi;
    private javax.swing.JPanel tabbed;
    private javax.swing.JTable tblAutos;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtBuscarVista;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtHoraEgreso;
    private javax.swing.JTextField txtHoraIngreso;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtObservacionesEgreso;
    private javax.swing.JTextField txtPlacas;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
